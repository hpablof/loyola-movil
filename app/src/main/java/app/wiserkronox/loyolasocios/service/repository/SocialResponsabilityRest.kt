package app.wiserkronox.loyolasocios.service.repository

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import app.wiserkronox.loyolasocios.R

class SocialResponsabilityRest(val context: Activity) {

    private fun getPdfURL(): String {
        return "${context.getString(R.string.host_service)}${context.getString(R.string.dir_social_responsibility_files)}"
    }
    fun getPdf(pdfName: String = "") {
        if (isNetworkAvailable(context)) {
            var dowloadid: Long = 0
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    CertificateRest.STORAGE_PERMISSION_CODE
                )
            } else {
                val new = object : BroadcastReceiver() {
                    override fun onReceive(p0: Context?, p1: Intent?) {
                        val id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                        if (id == dowloadid) {
                            Toast.makeText(context, "Descarga completada", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

                val uri = "${getPdfURL()}${pdfName}"
                val request = DownloadManager.Request(Uri.parse(uri))
                    .setTitle("${pdfName}.pdf")
                    .setDescription("Descargando....")
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        "${pdfName}.pdf"
                    )
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setAllowedOverMetered(true)

                val dowloadManager =
                    context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dowloadid = dowloadManager.enqueue(request)
                context.registerReceiver(
                    new,
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
                )
                Toast.makeText(context, "Descargando...", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

}
package app.wiserkronox.loyolasocios.view.ui.home

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat


class PlanePayCreditFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_plane_pay_credit, container, false)

        val args = this.arguments
        val data = args?.get("data")
        val json = JSONObject(data.toString())

        val lnlheader = view.findViewById<LinearLayout>(R.id.lnlheader_plan_pay)
        val lnlheaderinformation = view.findViewById<LinearLayout>(R.id.lnlheaderinformation)

        val bg = GradientDrawable()
        bg.cornerRadii = floatArrayOf(
            0f,0f,
            0f,0f,
            20f,20f,
            20f,20f
        )
        bg.setTint(Color.WHITE)

        lnlheaderinformation.background = bg

        val shape = GradientDrawable()
        shape.setColor(Color.parseColor("#008945"))
        shape.cornerRadius = 20f
        lnlheader.background = shape

        val nrocred = view.findViewById<TextView>(R.id.txtnrocredito)

        val bg_moneda = ResourcesCompat.getDrawable(resources,R.drawable.circle_text_view,context?.theme)
        bg_moneda?.setBounds(0,0,100,100)

        val moneda = view.findViewById<TextView>(R.id.txtmonedacredito)
        moneda.setTextColor(Color.WHITE)
        moneda.setTypeface(null, Typeface.BOLD)
        moneda.background = bg_moneda

        nrocred.text = json.get("credNumero").toString()
        moneda.text = json.get("crediMoneda").toString()


        val user = LoyolaApplication.getInstance()?.user //docu-cage
        val number = json.get("id").toString() //cred-number

        //HEADER PLAN DE PAGOS
        val url: String = "${getString(R.string.host_service)}${getString(R.string.home_aplication)}services/get_credit_plan_pay.php?docu-cage=${user!!.id_member}&cred-number=${number}"
        val queue = Volley.newRequestQueue(activity)

        val planePayCreditRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {jsonObject->

                val status = jsonObject.get("error")
                if(status == false) {

                    val dataPlanePay = jsonObject.getJSONArray("result")
                    //HEADER INFORMATION

                    for (i in 0 until dataPlanePay.length()) {

                        val capital = view.findViewById<TextView>(R.id.txtcapital)
                        val plaso = view.findViewById<TextView>(R.id.txttasa)
                        val formpago = view.findViewById<TextView>(R.id.txtformapago)

                        capital.text = (dataPlanePay.getJSONObject(i).get("credMontoDesem").toString() + " "+ moneda.text +".")
                        plaso.text = (dataPlanePay.getJSONObject(i).get("credTasa").toString() + "%")
                        formpago.text = dataPlanePay.getJSONObject(i).get("credForPago").toString()

                    }
                    //CONSULT TABLE
                    val detail = jsonObject.getJSONArray("detail")
                    val lnlmainbodydetails = view.findViewById<LinearLayout>(R.id.lnlbodydetails)

                    if(detail.length() == 0) {

                        val txtnotdata = TextView(activity)
                        txtnotdata.text = ("No existen planes de pagos asignados")
                        txtnotdata.setTextColor(Color.RED)
                        txtnotdata.textAlignment = View.TEXT_ALIGNMENT_CENTER
                        lnlmainbodydetails.addView(txtnotdata)
                        val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_plane_credit)
                        progressbar.visibility = View.INVISIBLE
                        val body = view.findViewById<LinearLayout>(R.id.lnlmain_body_plan_pay)
                        body.visibility = View.VISIBLE

                    } else {
                        for (i in 0 until detail.length()) {

                            val lnlbodydetails = LinearLayout(activity)
                            lnlbodydetails.orientation = LinearLayout.VERTICAL

                            val paramslnlody = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                340
                            )

                            paramslnlody.setMargins(20,25,20,25)

                            lnlbodydetails.setLayoutParams(paramslnlody)


                            //HEADER
                            val bgheader = GradientDrawable()
                            bgheader.cornerRadii = floatArrayOf(
                                20f,20f,
                                20f,20f,
                                0f,0f,
                                0f,0f
                            )
                            bgheader.setTint(Color.parseColor("#BCBCBC"))

                            val paramsHeader = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                140
                            )

                            val lnlbodyhearder = LinearLayout(activity)
                            lnlbodyhearder.orientation = LinearLayout.VERTICAL
                            lnlbodyhearder.setLayoutParams(paramsHeader)

                            val lnltitle = LinearLayout(activity)
                            lnltitle.orientation = LinearLayout.HORIZONTAL
                            lnltitle.setPadding(10,10,0,0)

                            val lnltitleinformation = LinearLayout(activity)
                            lnltitleinformation.orientation = LinearLayout.HORIZONTAL
                            lnltitleinformation.setPadding(10,10,0,0)

                            val paramslbls = LinearLayout.LayoutParams(
                                620,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )

                            val lblfecha = TextView(activity)
                            lblfecha.text = ("Fecha de Vencimiento").toString()
                            lblfecha.setLayoutParams(paramslbls)
                            lblfecha.setTextColor(Color.WHITE)
                            lblfecha.setTypeface(null, Typeface.BOLD)


                            val lblnrotrans = TextView(activity)
                            lblnrotrans.text = ("N°.Cuota").toString()
                            lblnrotrans.setLayoutParams(paramslbls)
                            lblnrotrans.setTextColor(Color.WHITE)
                            lblnrotrans.setTypeface(null, Typeface.BOLD)

                            lnltitle.addView(lblfecha)
                            lnltitle.addView(lblnrotrans)

                            //Information
                            val txtfecha = TextView(activity)
                            val dateformat = SimpleDateFormat("dd-MMM-yyyy")
                            val dateparse = SimpleDateFormat("yyyy-MM-dd")
                            val date = dateformat.format(dateparse.parse(detail.getJSONObject(i).get("credFecVenci").toString()))
                            txtfecha.text = date.toString()
                            txtfecha.setTextColor(Color.BLACK)
                            txtfecha.setTypeface(null, Typeface.BOLD)
                            txtfecha.setLayoutParams(paramslbls)

                            val txtnrotrans = TextView(activity)
                            txtnrotrans.text = detail.getJSONObject(i).get("credNumCuota").toString()
                            txtnrotrans.setTextColor(Color.BLACK)
                            txtnrotrans.setTypeface(null, Typeface.BOLD)
                            txtnrotrans.setLayoutParams(paramslbls)

                            lnltitleinformation.addView(txtfecha)
                            lnltitleinformation.addView(txtnrotrans)

                            //Create Header
                            lnlbodyhearder.background = bgheader
                            lnlbodyhearder.addView(lnltitle)
                            lnlbodyhearder.addView(lnltitleinformation)

                            val bgdivbody = GradientDrawable()
                            bgdivbody.cornerRadii = floatArrayOf(
                                0f,0f,
                                0f,0f,
                                20f,20f,
                                20f,20f
                            )
                            bgdivbody.setTint(Color.parseColor("#FFFFFF"))

                            val lnldivbody = LinearLayout(activity)
                            lnldivbody.orientation = LinearLayout.HORIZONTAL
                            lnldivbody.background = bgdivbody
                            lnldivbody.setLayoutParams(
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT
                                )
                            )

                            //INFORMATION PARAMS
                            val paramsinformation = LinearLayout.LayoutParams(
                                350,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )

                            val paramstxts = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )

                            paramstxts.setMargins(10,2,10,0)

                            //LNL FOR LABELS
                            val lnllabels = LinearLayout(activity)
                            lnllabels.orientation = LinearLayout.VERTICAL
                            lnllabels.setLayoutParams(paramsinformation)

                            val lbldetailcapital = TextView(activity)
                            lbldetailcapital.text = ("Capital: ").toString()
                            lbldetailcapital.setTypeface(null, Typeface.BOLD)
                            lbldetailcapital.setTextColor(Color.parseColor("#018645"))
                            lbldetailcapital.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                            lbldetailcapital.setLayoutParams(paramstxts)

                            val lbldetailsaldocapital = TextView(activity)
                            lbldetailsaldocapital.text = ("Saldo Credito: ").toString()
                            lbldetailsaldocapital.setTypeface(null, Typeface.BOLD)
                            lbldetailsaldocapital.setTextColor(Color.parseColor("#018645"))
                            lbldetailsaldocapital.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                            lbldetailsaldocapital.setLayoutParams(paramstxts)

                            //Values
                            val lnltexts = LinearLayout(activity)
                            lnltexts.orientation = LinearLayout.VERTICAL
                            lnltexts.setLayoutParams(paramsinformation)

                            val txtdetailCapital = TextView(activity)
                            txtdetailCapital.text = (detail.getJSONObject(i).get("credMontoCapi").toString() + " " + moneda.text +".")
                            txtdetailCapital.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                            txtdetailCapital.setTypeface(null, Typeface.BOLD)
                            txtdetailCapital.setTextColor(Color.BLACK)
                            txtdetailCapital.setLayoutParams(paramstxts)

                            val txtsaldocapitaldetail = TextView(activity)
                            txtsaldocapitaldetail.text = (detail.getJSONObject(i).get("crediSaldoCredi").toString() + " " + moneda.text + ".")
                            txtsaldocapitaldetail.setTypeface(null, Typeface.BOLD)
                            txtsaldocapitaldetail.setTextColor(Color.BLACK)
                            txtsaldocapitaldetail.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)
                            txtsaldocapitaldetail.setLayoutParams(paramstxts)

                            val shapebg = GradientDrawable()
                            shapebg.cornerRadius = 25F
                            shapebg.setColor(Color.parseColor("#00AB45"))

                            val icon_detail = ResourcesCompat.getDrawable(resources,R.drawable.icon_detail,context?.theme)
                            icon_detail?.setBounds(0,0,60,60)
                            icon_detail?.setTint(Color.WHITE)

                            val btnDetalle = Button(activity)
                            btnDetalle.text = ("DETALLES").toString()
                            btnDetalle.background = shape
                            btnDetalle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9F)
                            btnDetalle.setTextColor(Color.parseColor("#FFFFFD"))
                            btnDetalle.setCompoundDrawables(null,icon_detail,null,null)
                            btnDetalle.setLayoutParams(
                                LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                                )
                            )

                            btnDetalle.setOnClickListener {
                                val dialog = DetailPlanePayCreditDialogFragment()
                                val details = detail.getJSONObject(i).toString()
                                val bundle = Bundle()
                                bundle.putString("data",details)
                                bundle.putString("moneda", json.get("crediMoneda").toString())
                                bundle.putString("nrotrans", json.get("credNumero").toString())
                                dialog.arguments = bundle
                                dialog.show(childFragmentManager,"Texto")
                            }

                            lnltexts.addView(txtdetailCapital)
                            lnltexts.addView(txtsaldocapitaldetail)

                            lnllabels.addView(lbldetailcapital)
                            lnllabels.addView(lbldetailsaldocapital)

                            //Add body
                            lnldivbody.addView(lnllabels)
                            lnldivbody.addView(lnltexts)
                            lnldivbody.addView(btnDetalle)
                            //Add header to body
                            lnlbodydetails.addView(lnlbodyhearder,paramsHeader)
                            lnlbodydetails.addView(lnldivbody)
                            //Add Detail to main body
                            lnlmainbodydetails.addView(lnlbodydetails)

                            val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_plane_credit)
                            progressbar.visibility = View.INVISIBLE
                            val body = view.findViewById<LinearLayout>(R.id.lnlmain_body_plan_pay)
                            body.visibility = View.VISIBLE
                        }
                    }

                } else {
                    val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_plane_credit)
                    progressbar.visibility = View.INVISIBLE
                    val body = view.findViewById<LinearLayout>(R.id.lnlmain_body_plan_pay)
                    body.visibility = View.INVISIBLE
                    findNavController().popBackStack()
                    val msg = jsonObject.get("msg").toString()
                    Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
                }

            },
            {volleyError->
                Toast.makeText(activity,volleyError.message, Toast.LENGTH_SHORT).show()
                println(volleyError.message)
                val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_plane_credit)
                progressbar.visibility = View.INVISIBLE
                val body = view.findViewById<LinearLayout>(R.id.lnlmain_body_plan_pay)
                body.visibility = View.VISIBLE
            })

        queue.add(planePayCreditRequest)

        val icon_download = ResourcesCompat.getDrawable(resources,R.drawable.icon_download,context?.theme)
        icon_download?.setBounds(0,0,50,50)
        icon_download?.setTint(Color.WHITE)

        val icon_whatsapp = ResourcesCompat.getDrawable(resources,R.drawable.icon_whatsapp,context?.theme)
        icon_whatsapp?.setBounds(0,0,50,50)
        icon_whatsapp?.setTint(Color.WHITE)

        val paramsButtons = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            150)

        paramsButtons.setMargins(10,10,5,10)

        val btnGenerarPdf = view.findViewById<Button>(R.id.btngenerarpdf)
        btnGenerarPdf.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8F)
        btnGenerarPdf.setTextColor(Color.parseColor("#FFFFFD"))
        btnGenerarPdf.setLayoutParams(paramsButtons)
        btnGenerarPdf.setCompoundDrawables(null,icon_download,null,null)


        val btnContactar = view.findViewById<Button>(R.id.btncontactar)
        btnContactar.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8F)
        btnContactar.setTextColor(Color.parseColor("#FFFFFD"))
        btnContactar.setLayoutParams(paramsButtons)
        btnContactar.setCompoundDrawables(null,icon_whatsapp,null,null)

        val shapeCorner = GradientDrawable()
        shapeCorner.cornerRadius = 25f
        shapeCorner.setColor(Color.parseColor("#00AB45"))

        btnGenerarPdf.background = shapeCorner
        btnContactar.background = shapeCorner

        btnGenerarPdf.setOnClickListener {
            val STORAGE_PERMISSION_CODE = 101

            if (ContextCompat.checkSelfPermission(context as Activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
            } else {

                var dowloadid:Long = 0
                val new = object: BroadcastReceiver() {
                    override fun onReceive(p0: Context?, p1: Intent?) {
                        val id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                        if (id == dowloadid){
                            Toast.makeText(context, "Descarga completada", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                val url_dowload = "${getString(R.string.host_service)}services/get_credit_plan_pay_pdf.php?docu-cage=${user!!.id_member}&cred-number=${number}"
                val cookie = CookieManager.getInstance().getCookie(url_dowload)
                val request = DownloadManager.Request(Uri.parse(url_dowload))
                    .setTitle("crePlanPagos.pdf")
                    .setDescription("Descargando....")
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "crePlanPagos.pdf")
                    .addRequestHeader("cookie", cookie)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setAllowedOverMetered(true)

                val dm = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                dowloadid = dm.enqueue(request)

                activity?.registerReceiver(new, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

                val shape = GradientDrawable()
                shape.setColor(Color.parseColor("#008945"))
                shape.cornerRadius = 20f
                lnlheader.background = shape
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val body = view.findViewById<LinearLayout>(R.id.lnlmain_body_plan_pay)
        val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_plane_credit)
        progressbar.bringToFront()
        progressbar.visibility = View.VISIBLE
        body.visibility = View.INVISIBLE

    }
}
package app.wiserkronox.loyolasocios.view.ui.home

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.service.model.User
import app.wiserkronox.loyolasocios.service.repository.LoyolaService
import app.wiserkronox.loyolasocios.service.repository.UserRest
import app.wiserkronox.loyolasocios.view.ui.HomeActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import org.json.JSONException
import org.json.JSONObject


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private  lateinit var userSelfie: ImageView
    private  lateinit var userName: TextView
    private  lateinit var userIDMember: TextView
    private  lateinit var userStatus: ImageView
    private  lateinit var inActiveStatus: TextView
    private  lateinit var btnReview: Button
    private  lateinit var btn_certificado: LinearLayout
    private  lateinit var btn_credit: LinearLayout
    private  lateinit var btn_course: LinearLayout
    private  lateinit var btn_sucursales: LinearLayout

    private lateinit var ahorraConNostros: ImageView
    private lateinit var elijeCredito: LinearLayout
    private lateinit var cursosBtn:Button
    private lateinit var recomendacionesSeguridad: LinearLayout
    private lateinit var puntoReclamo:LinearLayout




    companion object {
        private const val TAG = "HomeFragment"
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val imageSlider = root.findViewById<ImageSlider>(R.id.homeImageSlider);
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner_home_1))
        imageList.add(SlideModel(R.drawable.banner_home_2))
        imageList.add(SlideModel(R.drawable.banner_home_3))
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)

        //        NAVIGATION
        ahorraConNostros = root.findViewById(R.id.ahoraConNostrosBtn)
        ahorraConNostros.setOnClickListener{
            (activity as HomeActivity).goAhorraConNosostros()
        }

        elijeCredito = root.findViewById(R.id.elijeCreditoBtn)
        elijeCredito.setOnClickListener{
            (activity as HomeActivity).goElijeCredito()
        }

        cursosBtn = root.findViewById(R.id.cursosBtn)
        cursosBtn.setOnClickListener{
            (activity as HomeActivity).goCourse()
        }

        puntoReclamo = root.findViewById(R.id.puntoReclamoBtn)
        puntoReclamo.setOnClickListener{
            (activity as HomeActivity).goPuntoReclamo()
        }

        recomendacionesSeguridad = root.findViewById(R.id.recomendacionesSeguridad)
        recomendacionesSeguridad.setOnClickListener{
            (activity as HomeActivity).goRecomendacionesSeguridad()
        }
//        userName = root.findViewById(R.id.text_user_fullname)
//        userSelfie = root.findViewById(R.id.image_selfie)
//        userIDMember = root.findViewById(R.id.text_user_id_member)
//        userStatus = root.findViewById(R.id.image_status_user)
//        inActiveStatus = root.findViewById(R.id.text_inactive_status)
//        btnReview = root.findViewById(R.id.btn_fix_data)
//        btn_certificado = root.findViewById(R.id.btn_certificados)
//        btn_credit = root.findViewById(R.id.btn_credito)
//        btn_course = root.findViewById(R.id.btn_curso)
//        btn_sucursales = root.findViewById(R.id.btn_sucursales)
//        val user = LoyolaApplication.getInstance()?.user

//        btnReview.setOnClickListener{
//            (activity as HomeActivity).fixData()
//        }
//        btn_certificado.setOnClickListener{
//            (activity as HomeActivity).goCertificate()
//        }
//        btn_credit.setOnClickListener{
//            (activity as HomeActivity).goCredit()
//        }
//        btn_course.setOnClickListener{
//            (activity as HomeActivity).goCourse()
//        }
//        btn_sucursales.setOnClickListener{
//            (activity as HomeActivity).goMaps()
//        }
//        user?.let {
//            userName.text = it.names+" "+it.last_name_1+" "+it.last_name_2
//            userIDMember.text = it.id_member
//            updateStatusIcon( it )
//
//            if( it.selfie.startsWith("file") ) {
//                val src = MediaStore.Images.Media.getBitmap(activity?.getContentResolver(), Uri.parse(it?.selfie))
//                if (src != null) {
//                    val dr = RoundedBitmapDrawableFactory.create(resources, src)
//                    dr.cornerRadius = Math.max(src.width, src.height) / 2.0f
//                    userSelfie.setImageDrawable(dr)
//                }
//            }
//
//            if( (activity as HomeActivity).isOnline() ) {
//                getUserStatusFromServer(it)
//            }
//        }

        return root
    }

    fun getUserStatusFromServer(user: User){
        activity?.let{
            val userRest = UserRest(it)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST, userRest.getUserStatusURL(),
                userRest.getUserLoginJson(user),
                    { response ->
                        Log.d(TAG, "Response is: ${response.toString()}")
                        if (response.getBoolean("success")) {
                            Log.d(TAG, "Exito")
                            processStatusResponse(user, response)
                        } else {
                            (activity as HomeActivity).showMessage(response.getString("reason"))
                        }
                    },
                    { error ->
                        Log.e(TAG, error.toString())
                        error.printStackTrace()
                        (activity as HomeActivity).showMessage("Error de conexión con el servidor")
                    }
            )

            // Add the request to the RequestQueue.
            LoyolaService.getInstance(it).addToRequestQueue(jsonObjectRequest)
        }
    }

    fun processStatusResponse(user: User, jResponse: JSONObject){
        try{
            if( jResponse.has("user_status") ){
                val status = jResponse.getJSONObject("user_status")
                if( user.feedback_date != status.getString("created_at") ){
                    user.feedback_date = status.getString("created_at")
                    if( status.getString("status") == "activo" ){
                        user.state_activation = User.STATE_USER_ACTIVE
                    } else {
                        user.state_activation = User.STATE_USER_REJECT
                    }
                    user.feedback_activation = status.getString("observations")
                    (activity as HomeActivity).backUpdate( user )
                    updateStatusIcon( user )
                }
            } else {
                user.state_activation = User.STATE_USER_INACTIVE
                user.feedback_activation = ""
                (activity as HomeActivity).backUpdate( user )
                updateStatusIcon( user )
            }

        } catch (j_error: JSONException){
            j_error.printStackTrace()
        }
    }

    fun updateStatusIcon(user: User){
        if( user.state_activation == User.STATE_USER_ACTIVE ){
            userStatus.setImageDrawable( activity?.getDrawable(R.drawable.icon_status_user_active))
            inActiveStatus.visibility = TextView.GONE
            btnReview.visibility = Button.GONE
        } else if( user.state_activation == User.STATE_USER_REJECT) {
            userStatus.setImageDrawable( activity?.getDrawable(R.drawable.icon_status_user_inactive))
            inActiveStatus.text = "Observaciones: "+user.feedback_activation
            inActiveStatus.visibility = TextView.VISIBLE
            btnReview.visibility = Button.VISIBLE
        } else {
            userStatus.setImageDrawable( activity?.getDrawable(R.drawable.icon_status_user_inactive))
            inActiveStatus.visibility = TextView.VISIBLE
        }
    }

}
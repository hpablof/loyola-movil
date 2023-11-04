package app.wiserkronox.loyolasocios.view.ui.home

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class CreditFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_credit, container, false)

        val mainBody = view.findViewById<LinearLayout>(R.id.layoutmainbody)
        mainBody.setPadding(30,30,30,30)

        val user = LoyolaApplication.getInstance()?.user
        val url = "${getString(R.string.host_service)}${getString(R.string.home_aplication)}services/get_credit_history.php?docu-cage=${user!!.id_member}"
        val queue = Volley.newRequestQueue(activity)
        val creditRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            {jsonObject->
                val error = jsonObject.get("error")

                if(error == true) {
                    val msg = jsonObject.get("msg").toString()
                    Toast.makeText(activity,msg, Toast.LENGTH_SHORT).show()
                    val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_credit)
                    progressbar.visibility = View.INVISIBLE
                    findNavController().popBackStack()

                } else {
                    val data = jsonObject.getJSONArray("result")

                    for(i in 0 until data.length()) {

                        val lnlBody = LinearLayout(activity)
                        lnlBody.orientation = LinearLayout.HORIZONTAL

                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            280,
                            1f)

                        params.setMargins(0,30,0,20)

                        val lnlleftinformation = LinearLayout(activity)
                        lnlleftinformation.orientation = LinearLayout.VERTICAL

                        val number = TextView(activity)
                        val state = TextView(activity)

                        val bg = GradientDrawable()
                        bg.cornerRadii = floatArrayOf(
                            20f,20f,
                            0f,0f,
                            0f,0f,
                            20f,20f
                        )
                        bg.setColor(Color.parseColor("#008945"))

                        number.text = ("CREDITO\n"+ data.getJSONObject(i).get("credNumero").toString())
                        number.gravity = Gravity.CENTER
                        number.setTypeface(null, Typeface.BOLD)
                        number.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)
                        number.setTextColor(Color.parseColor("#FFFFFD"))
                        number.setLayoutParams(LinearLayout.LayoutParams(
                            220,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ))

                        state.text = ("Estado\n"+ data.getJSONObject(i).get("crediEstado").toString())
                        state.gravity = Gravity.CENTER
                        state.setTypeface(null, Typeface.BOLD)
                        state.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13F)

                        if(data.getJSONObject(i).get("crediEstado").toString() == "Castigado") {
                            state.setTextColor(Color.RED)
                        }
                        if(data.getJSONObject(i).get("crediEstado").toString() == "Retraso") {
                            state.setTextColor(Color.RED)
                        }
                        if(data.getJSONObject(i).get("crediEstado").toString() == "Ejecución") {
                            state.setTextColor(Color.RED)
                        }
                        if(data.getJSONObject(i).get("crediEstado").toString() == "Cancelado") {
                            state.setTextColor(Color.RED)
                        }
                        if(data.getJSONObject(i).get("crediEstado").toString() == "Activo") {
                            state.setTextColor(Color.parseColor("#2ed111"))
                        }
                        state.setLayoutParams(LinearLayout.LayoutParams(
                            220,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        ))

                        val lnlsection = LinearLayout(activity)
                        lnlsection.orientation = LinearLayout.VERTICAL

                        val paramsSection = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                        )

                        val paramsLeft = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                        )

                        //Body left
                        lnlleftinformation.setPadding(5)
                        lnlleftinformation.gravity = Gravity.CENTER
                        lnlleftinformation.background = bg
                        lnlleftinformation.addView(number)
                        lnlleftinformation.addView(state)
                        lnlBody.setLayoutParams(params)
                        lnlBody.addView(lnlleftinformation,paramsLeft)
                        lnlBody.addView(lnlsection,paramsSection)

                        val bgmoneda = GradientDrawable()
                        bgmoneda.cornerRadii = floatArrayOf(
                            0f,0f,
                            20f,20f,
                            0f,0f,
                            0f,0f
                        )
                        bgmoneda.setTint(Color.parseColor("#BCBCBC"))

                        val moneda = TextView(activity)
                        moneda.text = ("Moneda: "+data.getJSONObject(i).get("credMontoDesem").toString()+" "+data.getJSONObject(i).get("crediMoneda").toString()+".")
                        moneda.background = bgmoneda
                        moneda.setPadding(40,0,0,0)
                        moneda.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15F)


                        val bgSection = GradientDrawable()
                        bgSection.cornerRadii = floatArrayOf(
                            0f,0f,
                            0f,0f,
                            20f,20f,
                            0f,0f
                        )
                        bgSection.setTint(Color.parseColor("#EFEFEF"))

                        val lnlSectionInformation = LinearLayout(activity)
                        lnlSectionInformation.orientation = LinearLayout.HORIZONTAL
                        lnlSectionInformation.background = bgSection

                        val paramsButtons = LinearLayout.LayoutParams(
                            208,
                            LinearLayout.LayoutParams.WRAP_CONTENT)

                        paramsButtons.setMargins(15,20,15,20)

                        val shape = GradientDrawable()
                        shape.cornerRadius = 25F
                        shape.setColor(Color.parseColor("#00AB45"))

                        val icon_detail = ResourcesCompat.getDrawable(resources,R.drawable.icon_detail,context?.theme)
                        icon_detail?.setBounds(0,0,63,63)
                        icon_detail?.setTint(Color.WHITE)

                        val btnDetalle = Button(activity)
                        btnDetalle.background = shape
                        btnDetalle.text = ("DETALLE").toString()
                        btnDetalle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9F)
                        btnDetalle.setTextColor(Color.parseColor("#FFFFFD"))
                        btnDetalle.setLayoutParams(paramsButtons)
                        btnDetalle.setCompoundDrawables(null,icon_detail,null,null)

                        btnDetalle.setOnClickListener {
                            val details = data.getJSONObject(i).toString()
                            val bundle = Bundle()
                            bundle.putString("data",details)
                            view.findNavController().navigate(R.id.action_detaill, bundle)
                        }

                        val icon_plane_play = ResourcesCompat.getDrawable(resources,R.drawable.icon_plane_pay,context?.theme)
                        icon_plane_play?.setBounds(0,0,63,63)
                        icon_plane_play?.setTint(Color.WHITE)

                        val btnPlanPagos = Button(activity)
                        btnPlanPagos.background = shape
                        btnPlanPagos.text = ("PLAN DE PAGOS").toString()
                        btnPlanPagos.setTextColor(Color.parseColor("#FFFFFD"))
                        btnPlanPagos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9F)
                        btnPlanPagos.setLayoutParams(paramsButtons)
                        btnPlanPagos.setCompoundDrawables(null,icon_plane_play,null,null)

                        btnPlanPagos.setOnClickListener {
                            val details = data.getJSONObject(i).toString()
                            val bundle = Bundle()
                            bundle.putString("data",details)
                            view.findNavController().navigate(R.id.action_plane_pay_credit, bundle)
                        }

                        val icon_extract = ResourcesCompat.getDrawable(resources,R.drawable.icon_extract,context?.theme)
                        icon_extract?.setBounds(0,0,63,63)
                        icon_extract?.setTint(Color.WHITE)

                        val btnExtracto = Button(activity)
                        btnExtracto.background = shape
                        btnExtracto.text = ("EXTRACTO").toString()
                        btnExtracto.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9F)
                        btnExtracto.setTextColor(Color.parseColor("#FFFFFD"))
                        btnExtracto.setLayoutParams(paramsButtons)
                        btnExtracto.setCompoundDrawables(null,icon_extract,null,null)

                        btnExtracto.setOnClickListener {
                            val details = data.getJSONObject(i).toString()
                            val bundle = Bundle()
                            bundle.putString("data",details)
                            view.findNavController().navigate(R.id.action_extract_credit, bundle)
                        }

                        val check = ImageView(activity)
                        val type = data.getJSONObject(i).get("crediEstado").toString()
                        if(type == "Activo") {
                            check.setBackgroundResource(R.drawable.icon_check)
                        } else {
                            check.setBackgroundResource(R.drawable.icon_no_check)
                        }

                        check.setLayoutParams(LinearLayout.LayoutParams(50,50))

                        //Actions Buttons
                        val paramsInformation = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            265,
                            1f)

                        lnlSectionInformation.setPadding(10)
                        lnlSectionInformation.setLayoutParams(paramsInformation)
                        lnlSectionInformation.addView(btnDetalle)
                        lnlSectionInformation.addView(btnPlanPagos)
                        lnlSectionInformation.addView(btnExtracto)
                        lnlSectionInformation.addView(check)
                        //Body Right
                        lnlsection.addView(moneda)
                        lnlsection.addView(lnlSectionInformation)
                        //Insert row in table
                        mainBody.addView(lnlBody)
                        val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_credit)
                        progressbar.visibility = View.INVISIBLE

                    }
                }

            },
            {volleyError->
                Toast.makeText(activity,volleyError.message, Toast.LENGTH_SHORT).show()
                println(volleyError.message)
                val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_credit)
                progressbar.visibility = View.INVISIBLE
            })

        queue.add(creditRequest)

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressbar = view.findViewById<ProgressBar>(R.id.progressbar_credit)
        progressbar.visibility = View.VISIBLE
    }
}
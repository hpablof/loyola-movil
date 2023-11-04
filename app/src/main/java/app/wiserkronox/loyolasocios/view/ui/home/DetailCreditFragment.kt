package app.wiserkronox.loyolasocios.view.ui.home

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import app.wiserkronox.loyolasocios.R
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DetailCreditFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_credit, container, false)
        val args = this.arguments
        val data = args?.get("data")
        val json = JSONObject(data.toString())
        val credNumero = view.findViewById<TextView>(R.id.crednumber)
        val credFecDesem = view.findViewById<TextView>(R.id.credFecDesem)
        val credMontoDesem = view.findViewById<TextView>(R.id.credMontoDesem)
        val crediMoneda = view.findViewById<TextView>(R.id.crediMoneda)
        val crediSaldo = view.findViewById<TextView>(R.id.crediSaldo)
        val crediEstado = view.findViewById<TextView>(R.id.crediEstado)
        val crediFecCancel = view.findViewById<TextView>(R.id.crediFecCancel)
        //Date format
        val dateformat = SimpleDateFormat("dd-MMM-yyyy")
        val dateparse = SimpleDateFormat("yyyy-MM-dd")
        val datefechdesem = dateformat.format(dateparse.parse(json.get("credFechaDesem").toString()))
        val datefechcancel = dateformat.format(dateparse.parse(json.get("crediFechaCancel").toString()))
        // Change text
        credNumero.text = ("N°CTA: " + json.get("credNumero").toString())
        credFecDesem.text = datefechdesem.toString()
        credMontoDesem.text = json.get("credMontoDesem").toString()
        crediMoneda.text = json.get("crediMoneda").toString()
        crediSaldo.text = json.get("crediSaldo").toString()
        crediEstado.text = json.get("crediEstado").toString()
        crediFecCancel.text = datefechcancel.toString()

        val paramsButtons = LinearLayout.LayoutParams(
            500,
            200)

        paramsButtons.setMargins(15,15,15,15)

        val shape = GradientDrawable()
        shape.cornerRadius = 25F
        shape.setColor(Color.parseColor("#00AB45"))

        val icont_plane_pay = ResourcesCompat.getDrawable(resources,R.drawable.icon_plane_pay,context?.theme)
        icont_plane_pay?.setBounds(0,0,80,80)
        icont_plane_pay?.setTint(Color.WHITE)

        val icon_credit_extract = ResourcesCompat.getDrawable(resources,R.drawable.icon_extract,context?.theme)
        icon_credit_extract?.setBounds(0,0,80,80)
        icon_credit_extract?.setTint(Color.WHITE)

        val btnplanepay = view.findViewById<Button>(R.id.btntocreditplanepay)
        btnplanepay.background = shape
        btnplanepay.setTextColor(Color.parseColor("#FFFFFD"))
        btnplanepay.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11F)
        btnplanepay.setCompoundDrawables(null,icont_plane_pay,null,null)
        btnplanepay.setLayoutParams(paramsButtons)

        btnplanepay.setOnClickListener {
            val details = data.toString()
            val bundle = Bundle()
            bundle.putString("data",details)
            view.findNavController().navigate(R.id.action_plane_pay_credit, bundle)
        }

        val btncreditextract = view.findViewById<Button>(R.id.btntocreditextract)
        btncreditextract.background = shape
        btncreditextract.setTextColor(Color.parseColor("#FFFFFD"))
        btncreditextract.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11F)
        btncreditextract.setCompoundDrawables(null,icon_credit_extract,null,null)
        btncreditextract.setLayoutParams(paramsButtons)

        btncreditextract.setOnClickListener {
            val details = data.toString()
            val bundle = Bundle()
            bundle.putString("data",details)
            view.findNavController().navigate(R.id.action_extract_credit, bundle)
        }

        return view
    }
}
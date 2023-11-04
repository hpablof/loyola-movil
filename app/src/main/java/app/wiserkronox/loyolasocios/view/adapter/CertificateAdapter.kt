package app.wiserkronox.loyolasocios.view.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.model.Certificate
import app.wiserkronox.loyolasocios.service.repository.CertificateRest
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CertificateAdapter(
    private val context: Context,
    private val dataset: List<Certificate>
):RecyclerView.Adapter<CertificateAdapter.CertificateViewHolder>() {


    class CertificateViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) {
        val label_certificate_year: TextView = view.findViewById(R.id.label_year)
        val text_certificate_year: TextView = view.findViewById(R.id.text_year)

        val textlabel_certificate_number: TextView = view.findViewById(R.id.textlabel_number)
        val text_certificate_number: TextView = view.findViewById(R.id.text_number)

        val textlabel_certificate_date: TextView = view.findViewById(R.id.textlabel_date)
        val text_certificate_opening_date: TextView = view.findViewById(R.id.text_opening_date)

        val textlabel_certificate_amount: TextView = view.findViewById(R.id.textlabel_amount)
        val text_certificate_amount: TextView = view.findViewById(R.id.text_amount)

        val textlabel_certificate_cost: TextView = view.findViewById(R.id.textlabel_amount)
        val text_certificate_cost: TextView = view.findViewById(R.id.text_cost)

        val image_certificate_state: ImageView = view.findViewById(R.id.image_status)
        val button_export_pdf: Button = view.findViewById(R.id.button_export_pdf)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CertificateViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.certificate_item, parent, false)

        return CertificateViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CertificateViewHolder, position: Int) {
        val item = dataset[position]
        holder.text_certificate_year.text = item.year.toString()

        holder.text_certificate_number.text = item.number.toString()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.text_certificate_opening_date.text = LocalDate.parse(item.opening_date).format(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
        else {
            holder.text_certificate_opening_date.text = item.opening_date
        }
        holder.text_certificate_amount.text = item.amount.toString()

        val money = DecimalFormat(
            "#,###.00", DecimalFormatSymbols.getInstance(Locale("es", "BOL")))
        money.roundingMode = RoundingMode.CEILING
        holder.text_certificate_cost.text = money.format(item.cost)

        when(item.state) {
            "VIGENTE"-> {
                holder.image_certificate_state.setImageResource(R.drawable.ic_round_enabled)

                holder.label_certificate_year.setTextColor(ContextCompat.getColor(context, R.color.white))
                holder.text_certificate_year.setTextColor(ContextCompat.getColor(context, R.color.white))

                holder.textlabel_certificate_number.setTextColor(ContextCompat.getColor(context, R.color.green_kelp))
                holder.text_certificate_number.setTextColor(ContextCompat.getColor(context, R.color.green_kelp))

                holder.textlabel_certificate_date.setTextColor(ContextCompat.getColor(context, R.color.black))
                holder.text_certificate_opening_date.setTextColor(ContextCompat.getColor(context, R.color.black))

                holder.textlabel_certificate_amount.setTextColor(ContextCompat.getColor(context, R.color.black))
                holder.text_certificate_amount.setTextColor(ContextCompat.getColor(context, R.color.black))

                holder.textlabel_certificate_cost.setTextColor(ContextCompat.getColor(context, R.color.black))
                holder.text_certificate_cost.setTextColor(ContextCompat.getColor(context, R.color.black))

                holder.button_export_pdf.isEnabled = true
                holder.button_export_pdf.setBackgroundColor(ContextCompat.getColor(context, R.color.green_kelp))
                holder.button_export_pdf.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
            "CADUCO" -> {
                // card disabled styles
                holder.image_certificate_state.setImageResource(R.drawable.ic_round_disabled)

                holder.label_certificate_year.setTextColor(Color.parseColor("#FF80C4A5"))
                holder.text_certificate_year.setTextColor(Color.parseColor("#FF80C4A5"))

                holder.textlabel_certificate_number.setTextColor(Color.parseColor("#FF77BB9C"))
                holder.text_certificate_number.setTextColor(Color.parseColor("#FF77BB9C"))

                holder.textlabel_certificate_date.setTextColor(Color.parseColor("#FFA0A0A0"))
                holder.text_certificate_opening_date.setTextColor(Color.parseColor("#FFA0A0A0"))

                holder.textlabel_certificate_amount.setTextColor(Color.parseColor("#FFA0A0A0"))
                holder.text_certificate_amount.setTextColor(Color.parseColor("#FFA0A0A0"))

                holder.textlabel_certificate_cost.setTextColor(Color.parseColor("#FFA0A0A0"))
                holder.text_certificate_cost.setTextColor(Color.parseColor("#FFA0A0A0"))

                holder.button_export_pdf.isEnabled = false
                holder.button_export_pdf.setBackgroundColor(Color.parseColor("#FF77CB9E"))
                holder.button_export_pdf.setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        }

        holder.button_export_pdf.setOnClickListener {
            CertificateRest(context as Activity).getCertificatePdf(item.number.toString())
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}
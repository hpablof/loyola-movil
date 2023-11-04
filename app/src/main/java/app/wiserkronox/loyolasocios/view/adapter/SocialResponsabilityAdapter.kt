package app.wiserkronox.loyolasocios.view.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.model.SocialResponsabilityModel
import app.wiserkronox.loyolasocios.service.repository.SocialResponsabilityRest


class SocialResponsabilityAdapter
    (
        private val context: Context,
        private val dataset: List<SocialResponsabilityModel>
    ):RecyclerView.Adapter<SocialResponsabilityAdapter.SocialResponsabilityViewHolder>() {

        class SocialResponsabilityViewHolder
            ( private val view: View ): RecyclerView.ViewHolder(view) {
                val yearTV: TextView = view.findViewById(R.id.text_year)
                val scoreBtn: Button = view.findViewById(R.id.calificacion_rse_btn)
                val reportBtn: Button = view.findViewById(R.id.informe_rse_btn)
            }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SocialResponsabilityViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.social_responsability_item, parent, false)
        return SocialResponsabilityViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: SocialResponsabilityViewHolder, position: Int) {
        val item =dataset[position]
        holder.yearTV.text = item.year

//        score click
        holder.scoreBtn.setOnClickListener {
            SocialResponsabilityRest(context as Activity).getPdf(item.score_pdf)
        }
//        report click
        holder.reportBtn.setOnClickListener {
            SocialResponsabilityRest(context as Activity).getPdf(item.report_pdf)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}
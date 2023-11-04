package app.wiserkronox.loyolasocios.view.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.model.SocialResponsabilityModel
import app.wiserkronox.loyolasocios.view.adapter.SocialResponsabilityAdapter

class SocialResponsabilityFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_social_responsability, container, false)
        recyclerView = root.findViewById(R.id.social_responsability_recyclerview)
        recyclerView.adapter = SocialResponsabilityAdapter(requireContext(), emptyList())
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val socialResponsibilityItems = ArrayList<SocialResponsabilityModel>()
        val item1 = SocialResponsabilityModel()
        item1.year = "2019"
        item1.score_pdf = "CALIFICACION-RSE-2019.pdf"
        item1.report_pdf = "INFORME-RSE-2019.pdf"
        socialResponsibilityItems.add(item1)

        val item2 = SocialResponsabilityModel()
        item2.year = "2018"
        item2.score_pdf = "CALIFICACION-RSE-2018.pdf"
        item2.report_pdf = "INFORME-RSE-2018.pdf"
        socialResponsibilityItems.add(item2)

        recyclerView.adapter = SocialResponsabilityAdapter(requireContext(), socialResponsibilityItems)
    }
}
package app.wiserkronox.loyolasocios.view.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import app.wiserkronox.loyolasocios.R
import app.wiserkronox.loyolasocios.service.LoyolaApplication
import app.wiserkronox.loyolasocios.service.repository.CertificateRest
import app.wiserkronox.loyolasocios.view.adapter.CertificateAdapter
import app.wiserkronox.loyolasocios.view.ui.HomeActivity

class CertificateFragment : Fragment() {

    private lateinit var progressBar:ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonDowloadListPdf: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_certificate, container, false)
        buttonDowloadListPdf = root.findViewById(R.id.button_certificates_list_topdf)
        buttonDowloadListPdf.setOnClickListener {
            val user = LoyolaApplication.getInstance()?.user
            CertificateRest(requireActivity()).getListCertificatesToPdf(user!!.id_member)
        }
        progressBar = root.findViewById(R.id.progresbar_certificates)
        recyclerView = root.findViewById(R.id.recyclerview_certificates)
        recyclerView.adapter = CertificateAdapter(requireContext(), emptyList())
        return  root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.visibility = View.VISIBLE

        var user = LoyolaApplication.getInstance()?.user
        CertificateRest(requireActivity()).getCertificates(
            user!!.id_member,
            {
                certificates->
                progressBar.visibility = View.INVISIBLE
                if (certificates != null && certificates.isNotEmpty())
                    recyclerView.adapter = CertificateAdapter(requireContext(), certificates)
                else
                    (activity as HomeActivity).goHome()
                buttonDowloadListPdf.visibility = View.VISIBLE
            },
            {
                error, certificates ->
                Toast.makeText(activity, "Error de conexión con el servidor", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
                if (certificates?.size == 0)
                    (activity as HomeActivity).goHome()
                recyclerView.adapter = CertificateAdapter(requireContext(), certificates!!)
                buttonDowloadListPdf.visibility = View.VISIBLE
            }
        )
    }
}

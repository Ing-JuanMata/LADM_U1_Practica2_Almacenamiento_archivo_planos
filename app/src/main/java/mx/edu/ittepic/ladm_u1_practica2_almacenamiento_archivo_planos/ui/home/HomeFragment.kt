package mx.edu.ittepic.ladm_u1_practica2_almacenamiento_archivo_planos.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.ittepic.ladm_u1_practica2_almacenamiento_archivo_planos.CustomAdapter
import mx.edu.ittepic.ladm_u1_practica2_almacenamiento_archivo_planos.databinding.FragmentHomeBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val titles: ArrayList<String> = ArrayList()
        val datas: ArrayList<String> = ArrayList()
        for (pedido in getPedidos()) {
            val textos: List<String> = pedido.split(":")
            titles.add(textos[0])
            datas.add(textos[1].replace(",", "\n"))
        }
        val adapter = CustomAdapter(titles, datas)
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter


        return root
    }

    private fun getPedidos(): List<String> {
        try {
            var archivo = BufferedReader(
                InputStreamReader(
                    this.requireContext().openFileInput
                        ("pedidos.txt")
                )
            )
            val pedidos: List<String> = archivo.readLines()
            archivo.close()
            return pedidos
        } catch (e: Exception) {
            return emptyList()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
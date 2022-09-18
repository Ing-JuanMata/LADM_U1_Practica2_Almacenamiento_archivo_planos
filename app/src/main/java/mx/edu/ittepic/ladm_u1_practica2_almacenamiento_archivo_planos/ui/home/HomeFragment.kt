package mx.edu.ittepic.ladm_u1_practica2_almacenamiento_archivo_planos.ui.home

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.ittepic.ladm_u1_practica2_almacenamiento_archivo_planos.CustomAdapter
import mx.edu.ittepic.ladm_u1_practica2_almacenamiento_archivo_planos.databinding.FragmentHomeBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.Exception

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var titles: ArrayList<String> = ArrayList()
    private var datas: ArrayList<String> = ArrayList()
    private var indice: Int = 0

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

        titles = ArrayList()
        datas = ArrayList()
        for (pedido in getPedidos()) {
            val textos: List<String> = pedido.split(":")
            titles.add(textos[0])
            datas.add(textos[1].replace(",", "\n"))
        }
        iniciarRecyclerView()
        binding.editar.setOnClickListener { editarPedido() }

        return root
    }

    //region Edicion del pedido
    private fun editarPedido() {
        titles.set(indice, binding.titulo.text.toString())
        datas.set(indice, tomarDatos())
        escribirArchivo()
        datas.set(indice, tomarDatos().replace(",", "\n"))
        binding.recyclerView.adapter?.notifyItemChanged(indice)
    }
    //endregion

    //region Tomar datos de la vista
    // Trae los datos puestos en este momento en el formulario
    private fun tomarDatos(): String {
        var mensaje: String = ""
        val hamburguesas: Int = if (binding.cantidadHamburguesa.text.isBlank()) 0 else binding
            .cantidadHamburguesa.text.toString().toInt()
        val tacos: Int = if (binding.cantidadTaco.text.isBlank()) 0 else binding
            .cantidadTaco.text.toString().toInt()
        val quesadillas: Int = if (binding.cantidadQuesadilla.text.isBlank()) 0 else binding
            .cantidadQuesadilla.text.toString().toInt()

        if (hamburguesas > 0 && binding.hamburguesa.isChecked) {
            mensaje += "${hamburguesas}x Hamburguesa,"
        }
        if (tacos > 0 && binding.taco.isChecked) {
            mensaje += "${tacos}x taco,"
        }
        if (quesadillas > 0 && binding.quesadilla.isChecked) {
            mensaje += "${quesadillas}x quesadillas,"
        }
        mensaje = mensaje.substring(0, mensaje.length - 1)
        return mensaje
    }
    //endregion

    //region eliminacion del pedido
    private fun eliminarPedido(i: Int) {
        titles.remove(titles.get(i))
        datas.remove(datas.get(i))
        escribirArchivo()
        binding.recyclerView.adapter?.notifyItemRemoved(i)
    }
    //endregion

    //region Escritura del archivo nuevo
    private fun escribirArchivo() {
        var i: Int = 0
        var pedidos: String = ""
        while (i < titles.size) {
            pedidos += "${titles.get(i)}:${datas.get(i).replace("\n", ",")}\n"
            i++
        }
        try {
            var archivo =
                OutputStreamWriter(
                    this.requireContext().openFileOutput("pedidos.txt", Context.MODE_PRIVATE)
                )
            archivo.write(pedidos)
            archivo.flush()
            archivo.close()
        } catch (e: Exception) {
        }
    }
    //endregion

    //region Llenado de campos(edición)
    private fun llenarCampos(i: Int) {
        indice = i
        binding.titulo.setText(titles.get(i))
        limpiarCampos()
        for (data in datas.get(i).split("\n")) {
            if (data.contains("Hamburguesa")) {
                binding.hamburguesa.isChecked = true
                binding.cantidadHamburguesa.setText(data.split("x")[0])
            }
            if (data.contains("taco")) {
                binding.taco.isChecked = true
                binding.cantidadTaco.setText(data.split("x")[0])
            }
            if (data.contains("quesadillas")) {
                binding.quesadilla.isChecked = true
                binding.cantidadQuesadilla.setText(data.split("x")[0])
            }
        }
    }
    //endregion

    //region Limpiar campos(edición)
    private fun limpiarCampos() {
        binding.cantidadHamburguesa.setText("")
        binding.cantidadTaco.setText("")
        binding.cantidadQuesadilla.setText("")
        binding.taco.isChecked = false
        binding.hamburguesa.isChecked = false
        binding.quesadilla.isChecked = false

    }
    //endregion

    //region Consultar pedidos texto
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
    //endregion

    //region Iniciar RecyclerView
    private fun iniciarRecyclerView() {
        val adapter: CustomAdapter = crearAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter
    }
    //endregion

    //region Creación de nuestro adapter
    private fun crearAdapter(): CustomAdapter {
        return CustomAdapter(titles, datas, object : CustomAdapter.onDataClick {
            override fun onClick(pos: Int) {
                AlertDialog.Builder(this@HomeFragment.requireContext()).setTitle(
                    "Acciones del " +
                            "pedido"
                ).setMessage(
                    "Favor de seleccionar que desea hacer con el " +
                            "pedido"
                ).setNeutralButton("Cancelar") { d, i -> d.dismiss() }
                    .setPositiveButton("Editar") { d, i ->
                        llenarCampos(pos)
                        d.dismiss()
                    }.setNegativeButton("Eliminar") { d, i ->
                        eliminarPedido(pos)
                        d.dismiss()
                    }.show()
            }

        })
    }
    //endregion

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
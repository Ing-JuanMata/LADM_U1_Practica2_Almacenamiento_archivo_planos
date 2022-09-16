package mx.edu.ittepic.ladm_u1_practica2_almacenamiento_archivo_planos.ui.gallery

import android.app.AlertDialog
import android.content.Context.MODE_APPEND
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ittepic.ladm_u1_practica2_almacenamiento_archivo_planos.databinding.FragmentGalleryBinding
import java.io.OutputStreamWriter
import java.lang.Exception

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.pedir.setOnClickListener {
            if (binding.titulo.text.isNotEmpty()) hacerPedido()
            else {
                Toast.makeText(
                    this.requireContext(), "Favor de introducir un " +
                            "titulo", Toast.LENGTH_LONG
                ).show()
            }
        }
        return root
    }

    private fun noHaySeleccion(hamburguesas: Int, tacos: Int, quesadillas: Int): Boolean {
        return (hamburguesas == 0 || !binding.hamburguesa.isChecked) && (tacos == 0 || !binding
            .taco
            .isChecked) &&
                (quesadillas == 0 || !binding.quesadilla.isChecked)
    }

    private fun hacerPedido() {
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
        if (noHaySeleccion(hamburguesas, tacos, quesadillas)) {
            Toast.makeText(
                this.requireContext(), "Favor de seleccionar por lo menos una " +
                        "comida", Toast.LENGTH_LONG
            ).show()
        } else {
            AlertDialog.Builder(this.requireContext()).setTitle(
                binding.titulo.text.toString
                    ()
            ).setMessage(mensaje.replace(",", "\n")).setPositiveButton("Aceptar") { d, i ->
                guardarPedido("${binding.titulo.text.toString()}:$mensaje\n")
                limpiarCampos()
                d.dismiss()
            }.setNegativeButton("Cancelar") { d, i ->
                d.cancel()
                Toast.makeText(this.requireContext(), "Pedido cancelado", Toast.LENGTH_LONG).show()
            }.show()
        }
    }

    private fun guardarPedido(pedido: String) {
        try {
            var archivo =
                OutputStreamWriter(
                    this.requireContext().openFileOutput("pedidos.txt", MODE_APPEND)
                )
            archivo.append(pedido)
            archivo.flush()
            archivo.close()
        } catch (e: Exception) {
        }
        Toast.makeText(this.requireContext(), "Pedido guardado", Toast.LENGTH_LONG).show()
    }

    private fun limpiarCampos() {
        binding.titulo.setText("")
        binding.cantidadHamburguesa.setText("")
        binding.cantidadTaco.setText("")
        binding.cantidadQuesadilla.setText("")
        binding.hamburguesa.isChecked = false
        binding.taco.isChecked = false
        binding.quesadilla.isChecked = false


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
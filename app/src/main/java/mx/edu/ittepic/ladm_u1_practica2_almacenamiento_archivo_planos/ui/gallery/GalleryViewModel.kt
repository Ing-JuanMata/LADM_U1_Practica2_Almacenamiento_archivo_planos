package mx.edu.ittepic.ladm_u1_practica2_almacenamiento_archivo_planos.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Aqui se registran pedidos"
    }
    val text: LiveData<String> = _text
}
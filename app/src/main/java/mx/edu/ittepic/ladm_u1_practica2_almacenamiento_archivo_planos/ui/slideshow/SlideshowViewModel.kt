package mx.edu.ittepic.ladm_u1_practica2_almacenamiento_archivo_planos.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SlideshowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Aqui hablamos de los desarrolladores para esta APP"
    }
    val text: LiveData<String> = _text
}
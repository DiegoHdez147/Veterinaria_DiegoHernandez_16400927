package mx.edu.ittepic.veterinaria_diegohernandez_16400927.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.edu.ittepic.veterinaria_diegohernandez_16400927.Propietario

class SlideshowViewModel : ViewModel() {
    var listaCURPs = ArrayList<String>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is slideshow Fragment"
    }
    val text: LiveData<String> = _text

    fun mostrarPropietario() {
        var listaPropietario = Propietario().mostrarTodos()
        var nombrePropietario = ArrayList<String>()

        listaCURPs.clear()
        (0..listaPropietario.size - 1).forEach {
            val al = listaPropietario.get(it)
            nombrePropietario.add(al.nombre)
            listaCURPs.add(al.curp)
        }
    }
}
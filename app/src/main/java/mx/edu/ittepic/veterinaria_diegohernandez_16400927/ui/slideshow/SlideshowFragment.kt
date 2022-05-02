package mx.edu.ittepic.veterinaria_diegohernandez_16400927.ui.slideshow

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ittepic.veterinaria_diegohernandez_16400927.MainActivity
import mx.edu.ittepic.veterinaria_diegohernandez_16400927.Propietario
import mx.edu.ittepic.veterinaria_diegohernandez_16400927.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    var listaCURPs = ArrayList<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root

        binding.insertar.setOnClickListener {
            var propietario = Propietario(this) //ALUMNO ES CLASE CONTROLADOR = ADMON DE DATOS

            propietario.curp = binding.curppropietario.text.toString()
            propietario.nombre = binding.nombrepropietario.text.toString()
            propietario.telefono = binding.telefonopropietario.text.toString()
            propietario.edad = binding.edadpropietario.text.toString()

            val resultado = propietario.insertar()//PARA MAINACTIVITY LA INSERCION ES ABSTRACTA
            if (resultado) {
                Toast.makeText(this, "SE INSERTO CON EXITO", Toast.LENGTH_LONG)
                    .show()

                mostrarPropietarios()
                binding.curppropietario.setText("")
                binding.nombrepropietario.setText("")
                binding.telefonopropietario.setText("")
                binding.edadpropietario.setText("")
            } else {
                AlertDialog.Builder(this)
                    .setTitle("ERROR")
                    .setMessage("NO SE PUDO INSERTAR")
                    .show()
            }
        }

        fun mostrarPropietarios() {
            var listaPropietarios = Propietario(MainActivity).mostrarTodos()
            var nombrePropietario = ArrayList<String>()

            listaCURPs.clear()
            (0..listaPropietarios.size - 1).forEach {
                val al = listaPropietarios.get(it)
                nombrePropietario.add(al.nombre)
                listaCURPs.add(al.curp)
            }

            binding.listapropietarios.adapter = ArrayAdapter<String>(this,
                R.layout.simple_list_item_1,nombrePropietario)
            binding.listapropietarios.setOnItemClickListener { adapterView, view, indice, l->
                val noControlLista = listaCURPs.get(indice)
                var alumno = Propietario(this).mostrarPropietarios(noControlLista)

                AlertDialog.Builder(this)
                    .setTitle("ATENCION")
                    .setMessage("Que deseas hacer con ${alumno.nombre}, \n Carrera: ${alumno.carrera}?")
                    .setNegativeButton("ELIMINAR"){d,i->
                        alumno.eliminar()
                        mostrarPropietarios()
                    }
                    .setPositiveButton("ACTUALIZAR"){d,i->
                        var otraVentana = Intent(this,MainActivity2::class.java)

                        otraVentana.putExtra("nocontrol",alumno.noControl)
                        startActivity(otraVentana)
                    }
                    .setNeutralButton("Cerrar"){d,i->}
                    .show()

            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }
}
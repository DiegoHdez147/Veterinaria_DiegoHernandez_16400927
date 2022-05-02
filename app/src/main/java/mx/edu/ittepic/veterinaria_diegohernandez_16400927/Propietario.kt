package mx.edu.ittepic.veterinaria_diegohernandez_16400927

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import navigation.BaseDatos

class Propietario (este:Context){
    var curp = ""
    var nombre = ""
    var telefono = ""
    var edad = ""
    private var este = este
    private var err = ""

    fun insertar(): Boolean{
        var baseDatos = BaseDatos(este,"tareaVeterinaria", null, 1)
        err = ""
        try {
            var tabla = baseDatos.writableDatabase
            var datos = ContentValues()

            datos.put("CURP",curp)
            datos.put("NOMBRE",nombre)
            datos.put("CARRERA",telefono)
            datos.put("EDAD",edad)

            var resultado = tabla.insert("ALUMNO",null,datos)
            if (resultado == -1L){
                return false
            }else{
                return false
            }
        }catch (error: SQLiteException){
            this.err = error.message!!
            return false
        }finally {
            baseDatos.close()
        }
        return true
    }

    fun mostrarTodos(): ArrayList<Propietario>{
        var baseDatos = BaseDatos(este,"escuela", null, 1)
        var arreglo = ArrayList<Propietario>()
        err = ""
        try {
            var tabla = baseDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM PROPIETARIO"

            var cursor = tabla.rawQuery(SQL_SELECT,null)
            if (cursor.moveToFirst()){
                do {
                    var propietario = Propietario(este)
                    propietario.curp = cursor.getString(0)
                    propietario.nombre = cursor.getString(1)
                    propietario.telefono = cursor.getString(2)
                    propietario.edad = cursor.getString(3)
                    arreglo.add(propietario)
                }while (cursor.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            baseDatos.close()
        }
        return arreglo
    }

}
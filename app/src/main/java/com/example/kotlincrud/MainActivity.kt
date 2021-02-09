package com.example.kotlincrud

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Alta
        btn_alta.setOnClickListener {
            if (etCodigo.text.isNotEmpty() && etNombre.text.isNotEmpty() && etCantante.text.isNotEmpty()) {
                val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
                val bd = admin.writableDatabase
                val registro = ContentValues()
                registro.put("codigo",etCodigo.text.toString())
                registro.put("nombre",etNombre.text.toString())
                registro.put("cantante",etCantante.text.toString())

                bd.insert("discos",null,registro)
                Toast.makeText(this, "Introducido con exito", Toast.LENGTH_SHORT).show()
                etCodigo.setText("")
                etNombre.setText("")
                etCantante.setText("")
                bd.close()
            }
        }

        // Consulta
        btn_buscar.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
            val bd = admin.readableDatabase
            val fila = bd.rawQuery("select nombre,cantante from discos where codigo=${etCodigo.text.toString()}",null)
            if (fila.moveToFirst()){
                etNombre.setText(fila.getString(0))
                etCantante.setText(fila.getString(1))
            } else {
                Toast.makeText(this, "No existe ningun disco con ese codigo", Toast.LENGTH_SHORT).show()
                etNombre.setText("")
                etCantante.setText("")
            }
            bd.close()
        }

        // Borrar
        btn_baja.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
            val bd = admin.writableDatabase
            val fila = bd.rawQuery("delete from discos where codigo=${etCodigo.text.toString()}",null)
            if (fila.moveToFirst()){
                AlertDialog.Builder(this).apply {
                    setTitle("Eliminar Disco")
                    setMessage("¿Estas seguro de que quieres eliminar ${fila.getString(0)} de ${fila.getString(1)}?")
                    setPositiveButton("Si") {_: DialogInterface, _: Int ->
                        val cant = bd.delete("discos","codigo=${etCodigo.text.toString()}",null)
                        bd.close()
                        if (cant==1){
                            Toast.makeText(this@MainActivity, "El disco se ha borrado con exito", Toast.LENGTH_SHORT).show()
                            etCodigo.setText("")
                        }
                    }
                    setNegativeButton("No"){_: DialogInterface, _: Int ->
                        bd.close()
                    }
                }.show()
            } else {
                Toast.makeText(this@MainActivity, "No existe ningun codigo con ese codigo", Toast.LENGTH_SHORT).show()
            }
        }

        // Modificar
        btn_modificar.setOnClickListener {
            val admin = AdminSQLiteOpenHelper(this,"administracion",null,1)
            val bd = admin.writableDatabase

            val registro = ContentValues()
            registro.put("nombre",etNombre.text.toString())
            registro.put("cantante",etCantante.text.toString())

            val cant = bd.update("discos", registro,"codigo=${etCodigo.text}",null)
            bd.close()

            if (cant==1){
                Toast.makeText(this, "El disco se ha modificado con exito", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this, "No existe ningun codigo con ese codigo", Toast.LENGTH_SHORT).show()
            }
        }

        // Mostrar
        btn_mostrar.setOnClickListener {
            val intent = Intent(this, MostrarActivity::class.java)
            startActivity(intent)
        }

    }
}
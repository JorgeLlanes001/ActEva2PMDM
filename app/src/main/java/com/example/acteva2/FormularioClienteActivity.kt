package com.example.acteva2

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.acteva2.database.DatabaseHelper
import com.example.acteva2.models.Cliente
import com.google.android.material.textfield.TextInputEditText

class FormularioClienteActivity : AppCompatActivity() {

    private lateinit var etNombre: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    private lateinit var btnGuardar: Button
    private lateinit var dbHelper: DatabaseHelper
    private var clienteEditar: Cliente? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_cliente)

        inicializarVistas()
        configurarToolbar()
        cargarDatosCliente()
    }

    private fun inicializarVistas() {
        etNombre = findViewById(R.id.etNombre)
        etEmail = findViewById(R.id.etEmail)
        etTelefono = findViewById(R.id.etTelefono)
        btnGuardar = findViewById(R.id.btnGuardar)
        dbHelper = DatabaseHelper(this)

        btnGuardar.setOnClickListener {
            if (validarFormulario()) {
                guardarCliente()
            }
        }
    }

    private fun configurarToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun cargarDatosCliente() {
        clienteEditar = intent.getParcelableExtra("CLIENTE")

        clienteEditar?.let { cliente ->
            supportActionBar?.title = "Editar Cliente"
            etNombre.setText(cliente.nombre)
            etEmail.setText(cliente.email)
            etTelefono.setText(cliente.telefono)
            btnGuardar.text = "ACTUALIZAR CLIENTE"
        }
    }

    private fun validarFormulario(): Boolean {
        val nombre = etNombre.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()

        // Validar nombre
        if (nombre.isEmpty()) {
            etNombre.error = "El nombre es obligatorio"
            etNombre.requestFocus()
            return false
        }

        // Validar email
        if (email.isEmpty()) {
            etEmail.error = "El correo electrónico es obligatorio"
            etEmail.requestFocus()
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Ingrese un correo electrónico válido"
            etEmail.requestFocus()
            return false
        }

        // Validar teléfono
        if (telefono.isEmpty()) {
            etTelefono.error = "El teléfono es obligatorio"
            etTelefono.requestFocus()
            return false
        }

        if (telefono.length < 9) {
            etTelefono.error = "El teléfono debe tener al menos 9 dígitos"
            etTelefono.requestFocus()
            return false
        }

        return true
    }

    private fun guardarCliente() {
        val nombre = etNombre.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()

        if (clienteEditar != null) {
            // Actualizar cliente existente
            val clienteActualizado = Cliente(
                id = clienteEditar!!.id,
                nombre = nombre,
                email = email,
                telefono = telefono
            )

            val resultado = dbHelper.actualizarCliente(clienteActualizado)
            if (resultado > 0) {
                Toast.makeText(this, "Cliente actualizado exitosamente", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar cliente", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Insertar nuevo cliente
            val nuevoCliente = Cliente(
                nombre = nombre,
                email = email,
                telefono = telefono
            )

            val id = dbHelper.insertarCliente(nuevoCliente)
            if (id > 0) {
                Toast.makeText(this, "Cliente guardado exitosamente", Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Error al guardar cliente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
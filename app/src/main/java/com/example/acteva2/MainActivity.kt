package com.example.acteva2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.acteva2.adapters.ClienteAdapter
import com.example.acteva2.database.DatabaseHelper
import com.example.acteva2.models.Cliente
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ClienteAdapter
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var fabAgregar: FloatingActionButton
    private lateinit var etBuscar: EditText
    private lateinit var tvContador: TextView
    private lateinit var tvMensajeVacio: TextView
    private var listaClientes = mutableListOf<Cliente>()

    companion object {
        const val REQUEST_CODE_AGREGAR = 1
        const val REQUEST_CODE_EDITAR = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarVistas()
        configurarRecyclerView()
        configurarBusqueda()
        cargarClientes()
    }

    private fun inicializarVistas() {
        recyclerView = findViewById(R.id.recyclerViewClientes)
        fabAgregar = findViewById(R.id.fabAgregar)
        etBuscar = findViewById(R.id.etBuscar)
        tvContador = findViewById(R.id.tvContador)
        tvMensajeVacio = findViewById(R.id.tvMensajeVacio)
        dbHelper = DatabaseHelper(this)

        fabAgregar.setOnClickListener {
            val intent = Intent(this, FormularioClienteActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_AGREGAR)
        }
    }

    private fun configurarRecyclerView() {
        adapter = ClienteAdapter(
            clientes = listaClientes,
            onEditClick = { cliente ->
                val intent = Intent(this, FormularioClienteActivity::class.java)
                intent.putExtra("CLIENTE", cliente)
                startActivityForResult(intent, REQUEST_CODE_EDITAR)
            },
            onDeleteClick = { cliente ->
                mostrarDialogoEliminar(cliente)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun configurarBusqueda() {
        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buscarClientes(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun cargarClientes() {
        listaClientes.clear()
        listaClientes.addAll(dbHelper.obtenerTodosClientes())
        adapter.actualizarLista(listaClientes)
        actualizarContador()
        actualizarMensajeVacio()
    }

    private fun buscarClientes(query: String) {
        if (query.isEmpty()) {
            cargarClientes()
        } else {
            val resultados = dbHelper.buscarClientes(query)
            adapter.actualizarLista(resultados)
            actualizarMensajeVacio()
        }
    }

    private fun mostrarDialogoEliminar(cliente: Cliente) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de que deseas eliminar a ${cliente.nombre}?")
            .setPositiveButton("Eliminar") { _, _ ->
                eliminarCliente(cliente)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarCliente(cliente: Cliente) {
        dbHelper.eliminarCliente(cliente.id)
        cargarClientes()
    }

    private fun actualizarContador() {
        val total = dbHelper.contarClientes()
        tvContador.text = "Total: $total"
    }

    private fun actualizarMensajeVacio() {
        if (listaClientes.isEmpty()) {
            tvMensajeVacio.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvMensajeVacio.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            cargarClientes()
        }
    }
}
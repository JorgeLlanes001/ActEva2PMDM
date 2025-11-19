package com.example.acteva2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.acteva2.models.Cliente

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "GestionClientes.db"
        private const val DATABASE_VERSION = 1

        // Tabla clientes
        private const val TABLE_CLIENTES = "clientes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_TELEFONO = "telefono"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_CLIENTES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL,
                $COLUMN_TELEFONO TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTES")
        onCreate(db)
    }

    // Insertar cliente
    fun insertarCliente(cliente: Cliente): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, cliente.nombre)
            put(COLUMN_EMAIL, cliente.email)
            put(COLUMN_TELEFONO, cliente.telefono)
        }
        val id = db.insert(TABLE_CLIENTES, null, values)
        db.close()
        return id
    }

    // Obtener todos los clientes
    fun obtenerTodosClientes(): List<Cliente> {
        val clientes = mutableListOf<Cliente>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_CLIENTES ORDER BY $COLUMN_NOMBRE ASC", null)

        if (cursor.moveToFirst()) {
            do {
                val cliente = Cliente(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    telefono = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONO))
                )
                clientes.add(cliente)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return clientes
    }

    // Actualizar cliente
    fun actualizarCliente(cliente: Cliente): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, cliente.nombre)
            put(COLUMN_EMAIL, cliente.email)
            put(COLUMN_TELEFONO, cliente.telefono)
        }
        val rows = db.update(TABLE_CLIENTES, values, "$COLUMN_ID = ?", arrayOf(cliente.id.toString()))
        db.close()
        return rows
    }

    // Eliminar cliente
    fun eliminarCliente(id: Int): Int {
        val db = writableDatabase
        val rows = db.delete(TABLE_CLIENTES, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return rows
    }

    // Buscar clientes
    fun buscarClientes(query: String): List<Cliente> {
        val clientes = mutableListOf<Cliente>()
        val db = readableDatabase
        val searchQuery = "%$query%"

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_CLIENTES WHERE $COLUMN_NOMBRE LIKE ? OR $COLUMN_EMAIL LIKE ? ORDER BY $COLUMN_NOMBRE ASC",
            arrayOf(searchQuery, searchQuery)
        )

        if (cursor.moveToFirst()) {
            do {
                val cliente = Cliente(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    telefono = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONO))
                )
                clientes.add(cliente)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return clientes
    }

    // Contar clientes
    fun contarClientes(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_CLIENTES", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }
}
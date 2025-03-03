package marceloviana1991.listadechamada.data

import android.content.ContentValues
import marceloviana1991.listadechamada.model.Interno

class InternoDao(private val dbHelper: AppDataBase) {

    fun insert(interno: Interno): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", interno.nome)
            put("faltas", 0)
        }
        return db.insert("interno", null, values)
    }

    fun getAll(): List<Interno> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM interno", null)
        val internos = mutableListOf<Interno>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val faltas = cursor.getInt(cursor.getColumnIndexOrThrow("faltas"))

            internos.add(Interno(id, nome, faltas))
        }
        cursor.close()
        return internos
    }

    fun get(id: Int): Interno {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM interno WHERE id=?", arrayOf(id.toString()))

        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
        val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
        val faltas = cursor.getInt(cursor.getColumnIndexOrThrow("faltas"))
        val interno = Interno(id, nome, faltas)

        cursor.close()
        return interno
    }

    fun update(interno: Interno): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", interno.nome)
            put("faltas", interno.faltas)
        }
        return db.update("interno", values, "id = ?", arrayOf(interno.id.toString()))
    }

    fun delete(internoId: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete("interno", "id = ?", arrayOf(internoId.toString()))
    }
}
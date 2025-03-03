package marceloviana1991.listadechamada.data

import android.content.Context
import marceloviana1991.listadechamada.model.Interno

class InternoRepository(context: Context) {
    private val dbHelper = AppDataBase(context)
    private val internoDao = InternoDao(dbHelper)

    fun addInterno(interno: Interno): Long = internoDao.insert(interno)
    fun getInternos(): List<Interno> = internoDao.getAll()
    fun get(id: Int): Interno = internoDao.get(id)
    fun updateInterno(interno: Interno): Int = internoDao.update(interno)
    fun deleteInterno(internoId: Int): Int = internoDao.delete(internoId)
}

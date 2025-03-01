package marceloviana1991.listadechamada.model

class Chamada(val id: Int = contador) {
    companion object {
        var contador: Int = 0
    }
    init {
        contador++
    }

    override fun toString(): String {
        return "Chamada(id=$id)"
    }

}
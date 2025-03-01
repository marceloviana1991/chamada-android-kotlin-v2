package marceloviana1991.listadechamada.model

class Interno(val nome: String) {
    val id = contador
    companion object {
        var contador: Int = 0
    }
    init {
        contador++
    }

    override fun toString(): String {
        return "Interno(nome='$nome', id=$id)"
    }

}
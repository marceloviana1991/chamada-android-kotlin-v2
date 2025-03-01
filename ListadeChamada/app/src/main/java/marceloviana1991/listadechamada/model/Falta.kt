package marceloviana1991.listadechamada.model

class Falta(val chamada: Chamada, val interno: Interno) {
    override fun toString(): String {
        return "Falta(chamada=$chamada, interno=$interno)"
    }
}
import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
fun main() {
    try {
        val gui = Gui()
        gui.init()
    }catch (e: Exception){
        println("Error en la conexion")
    }



}






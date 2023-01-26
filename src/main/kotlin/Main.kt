import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
fun main() {
    val gestorBBDD=GestorBBDD()
    val direccion=Direccion("Avenida Segunda Aguada",10,11012,"Cadiz")
    val taller=Taller("B11055837","TurboCádiz",direccion)
    val cliente=Cliente("12345678Y", "Antonio","antoniohibernate@gmail.com","Antonio123",direccion, setOf(taller))
    println(gestorBBDD.insertCliente(cliente))
    val pedido=Pedido(null,gestorBBDD.selectClienteByDni("12345678Y"),"Pedido de 20 tuercas")
    val pedido2=Pedido(taller,gestorBBDD.selectClienteByDni("12345678Y"),"Pedido de 2 ruedas")
    gestorBBDD.insertTaller(taller)
    gestorBBDD.insertPedido(pedido)
    gestorBBDD.selectAllPedidosSinTaller()?.forEach { println(it.toString()) }
    gestorBBDD.insertPedido(pedido2)
    gestorBBDD.pedidosAsociados("B11055837").forEach { println(it.toString()) }
    gestorBBDD.clientesAsociados("B11055837").forEach { println(it).toString() }
    gestorBBDD.close()
}





fun listarTalleres(manager:EntityManager) {
    println("*_____Lista Talleres_______*")
    val listaTalleres = manager.createQuery("from Taller")?.resultList as List<Taller>
    listaTalleres.forEach {
        println("${it.nombre} con cif: ${it.cif}.")
    }
    println("*_____FIN_______*")
}

fun insertarPedido(manager: EntityManager,pedido: Pedido){
    manager.transaction.begin()
    manager.persist(pedido)
    manager.transaction.commit()
}

fun listarPedidos(manager: EntityManager){
    println("*_____Lista Pedidos_______*")
    val listaPedidos = manager.createQuery("from Pedido")?.resultList as List<Pedido>
    listaPedidos.forEach {
        println("Pedido del taller ${it.taller?.cif}, para el cliente con dni ${it.cliente?.dni} con id ${it.id}.")
    }
    println("*_____FIN_______*")
}

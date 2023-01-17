import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence
fun main() {
    val emf=Persistence.createEntityManagerFactory("PersistenciaCliente")
    val manager=emf.createEntityManager()

    val direccion=Direccion("Avenida Segunda Aguada",10,11012,"Cadiz")

    val cliente=Cliente("12345678Y", "Antonio","antoniohibernate@gmail.com",24,direccion)
    insertarCliente(manager, cliente)
    listarCliente(manager)

    val taller=Taller("TurboCádiz",direccion)
    insertarTalleres(manager,taller)

    listarTalleres(manager)

    val pedido=Pedido(taller,cliente,"Pedido de 20 tuercas")
    insertarPedido(manager,pedido)
    listarPedidos(manager)

    manager.close()
}


fun insertarCliente(manager: EntityManager, cliente: Cliente) {
    manager.transaction.begin()
    manager.persist(cliente)
    manager.transaction.commit()
}

fun insertarTalleres(manager: EntityManager, taller: Taller) {
    manager.transaction.begin()
    manager.persist(taller)
    manager.transaction.commit()
}

fun listarCliente(manager:EntityManager) {
    println("*_____Lista CLientes_______*")
    val listaClientes = manager.createQuery("from Cliente")?.resultList as List<Cliente>
    listaClientes.forEach {
        println("${it.nombre} con dni: ${it.dni}.")
    }
    println("*_____FIN_______*")
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

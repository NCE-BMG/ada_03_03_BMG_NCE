import jakarta.persistence.EntityManager
import jakarta.persistence.Persistence

class GestorBBDD {

    private val manager:EntityManager = Persistence.createEntityManagerFactory("PersistenciaCliente").createEntityManager()

    fun insertCliente(cliente: Cliente):Boolean{
        return try{
            manager.transaction.begin()
            manager.persist(cliente)
            manager.transaction.commit()
            true
        }catch (e:Exception){
            manager.transaction.rollback()
            false
        }
    }
    fun insertTaller(taller: Taller):Boolean{
        return try{
            manager.transaction.begin()
            manager.persist(taller)
            manager.transaction.commit()
            true
        }catch (e:Exception){
            manager.transaction.rollback()
            false
        }
    }
    fun insertPedido(pedido: Pedido): Boolean {
        return try{
            manager.transaction.begin()
            manager.persist(pedido)
            manager.transaction.commit()
            true
        }catch (e:Exception){
            manager.transaction.rollback()
            false
        }
    }
    fun selectAllClientes():List<Cliente> = manager.createQuery("from Cliente")?.resultList as List<Cliente>
    fun selectClienteByDni(dni: String): Cliente {
        val query = manager.createQuery("from Cliente where dni = :dni", Cliente::class.java)
        query.setParameter("dni", dni)
        return query.singleResult
    }
    fun selectTallerByCif(cif: String): Taller? {
        val query = manager.createQuery("from Taller where cif = :cif", Taller::class.java)
        query.setParameter("cif", cif)
        return query.singleResult
    }
    fun selectAllTalleres():List<Taller> = manager.createQuery("from Taller")?.resultList as List<Taller>
    fun selectAllPedidos(): List<Pedido>? = manager.createQuery("from Pedido")?.resultList as List<Pedido>?
    fun selectAllPedidosSinTaller(): List<Pedido>? = manager.createQuery("from Pedido where taller=null")?.resultList as List<Pedido>



    fun pedidosAsociados(cif: String): MutableList<Pedido> {
        val listaPedidos: MutableList<Pedido> = mutableListOf()
        selectAllPedidos()?.forEach {
            if (it.taller!=null && it.taller!!.cif == cif) {
                listaPedidos.add(it)
            }
        }
        return listaPedidos
    }
    fun clientesAsociados(cif: String):MutableList<Cliente>{
        val listaPedidos: MutableList<Cliente> = mutableListOf()
        selectAllClientes().forEach { cliente ->
            if (cliente.talleres!=null) {
                cliente.talleres!!.forEach {
                    if (it.cif==cif) {
                        listaPedidos.add(cliente)
                    }
                }
            }
        }
        return listaPedidos
    }
    fun pedidosCliente(dni: String): MutableList<Pedido> {
        val listaPedidos= mutableListOf<Pedido>()
        selectAllPedidos()?.forEach {
            if (it.cliente!=null && it.cliente!!.dni == dni){
                listaPedidos.add(it)
            }
        }
        return listaPedidos
    }
    fun talleresCliente(dni: String): MutableList<Taller> {
        val listaTalleres= mutableListOf<Taller>()
        selectAllTalleres().forEach { taller ->
            if (taller.clientes!=null){
                taller.clientes!!.forEach {
                    if (it.dni==dni){
                        listaTalleres.add(taller)
                    }
                }
            }
        }
        return listaTalleres
    }

    fun close()=manager.close()
    fun isOpne() = manager.isOpen

}
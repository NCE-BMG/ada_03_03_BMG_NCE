import jakarta.persistence.*

@Entity
@Table(name = "pedidos")
class Pedido (
    @ManyToOne
    @JoinColumn(name = "cif")
    var taller: Taller? = null,

    @ManyToOne
    @JoinColumn(name = "dni")
    var cliente: Cliente? = null,

    @Column(
        name = "descripcion",
        length = 255
    )
    var descripcion:String,

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?=null
        ) {
    override fun toString(): String {
        if (taller==null) return "Pedido sin taller del cliente: ${cliente?.nombre} con la siguiente descripcion: $descripcion"
        else return "Pedido del cliente: ${cliente?.nombre}, en el taller ${taller!!.nombre} con la siguiente descripcion: $descripcion"
    }
}
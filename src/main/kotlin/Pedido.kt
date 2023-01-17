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
}
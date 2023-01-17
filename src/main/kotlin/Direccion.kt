import jakarta.persistence.*

@Entity
@Table(name = "direcciones")
class Direccion(

    @Column(name = "calle")
    var calle: String,

    @Column(name = "numero")
    var numero: Short,

    @Column(name = "cp")
    var cp: Short,

    @Column(name = "ciudad")
    var ciudad: String,

    @OneToOne(mappedBy = "direccion")
    var taller: Taller?=null,

    @OneToOne(mappedBy = "direccion")
    var cliente: Cliente?=null,

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?=null,


) {
}
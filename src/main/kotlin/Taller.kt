import jakarta.persistence.*
@Entity
@Table(name="talleres")
class Taller (

    @Column(name = "nombre")
    var nombre: String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_direccion")
    var direccion: Direccion,

    @Id
    @Column(name = "cif")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cif: Int?=null,

    @ManyToMany(mappedBy = "talleres")
    var clientes: Set<Cliente>? = null,
    ) {
    }

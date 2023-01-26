import jakarta.persistence.*
@Entity
@Table(name="talleres")
class Taller(
    @Id
    @Column(name = "cif")
    var cif: String ,

    @Column(name = "nombre")
    var nombre: String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_direccion")
    var direccion: Direccion,


    @ManyToMany(mappedBy = "talleres")
    var clientes: Set<Cliente>? = null,
    ) {
    override  fun toString():String ="Taller con cif: $cif, y nombre: $nombre"
    }

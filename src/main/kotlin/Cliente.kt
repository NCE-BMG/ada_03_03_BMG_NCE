import jakarta.persistence.*

@Entity
@Table(name = "clientes")
class Cliente(
    @Id
    @Column(name = "dni")
    var dni: String,

    @Column(name = "nombre")
    var nombre: String,

    @Column(name="email")
    var email:String,

    @Column(name = "contrasenya")
    var contrasenya:String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_direccion")
    var direccion: Direccion?,

    @ManyToMany
    @JoinTable(
        name = "cliente_taller",
        joinColumns = [JoinColumn(name = "dni")],
        inverseJoinColumns = [JoinColumn(name = "cif")]
    )
    var talleres: MutableSet<Taller>? = null,
) {
    override fun toString(): String {
        return "Cliente con dni: $dni, nombre: $nombre, email: $email y direccion: $direccion "
    }
}

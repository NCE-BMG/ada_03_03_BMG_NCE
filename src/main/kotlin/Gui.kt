class Gui {
    private val gestor = GestorBBDD()
    private val coder = EncriptacionContraseña
    fun init() {
        //PRUEBAS
        var taller =
            Taller("123123", "xd", Direccion("s", 1, 1, "d"), contrasenya = coder.encriptarContrasenya("123123"))
        val clientepr = Cliente(
            "x123123",
            "Naim",
            "naim@gmail.com",
            coder.encriptarContrasenya("123123"),
            null,
            talleres = setOf(taller)
        )
        val clientepr2 = Cliente(
            "4",
            "Naim",
            "naim@gmail.com",
            coder.encriptarContrasenya("123123"),
            null
        )
        gestor.insertTaller(taller)
        gestor.insertCliente(clientepr)
        gestor.insertCliente(clientepr2)

        val pedido = Pedido(null, clientepr, "mantecado")
        gestor.insertPedido(pedido)

        //PRUEBAS


        while (gestor.isOpne()) {
            welcome()
        }
    }

    private fun welcome() {
        while (gestor.isOpne()) {
            println("Selecciona una opción:")
            println("1. Loguearse como cliente")
            println("2. Loguearse como taller")
            println("3. Registrarse como Cliente o Taller")
            println("4. Salir")

            var opcion = 0
            while (opcion == 0) {
                try {
                    opcion = readln().toInt()
                } catch (e: Exception) {
                    opcion = 0
                    println("Caracter invalidos, introduzca un numero del 1 al 3")
                }
            }

            when (opcion) {
                1 -> {
                    var dni: String? = null
                    var contraseya: String? = null
                    while (contraseya == null) {
                        println("Menu de inicio de sesión")
                        println("Ingresa tu DNI:")
                        dni = readln()
                        if (dni == "menu") break
                        println("Ingresa tu contraseña:")
                        contraseya = readln()
                        if (contraseya == "menu") break

                    }

                    if (dni == "menu" || contraseya == "menu") {
                        println("Volviendo al Menu...")
                    } else {

                        try {

                            val cliente = dni?.let { gestor.selectClienteByDni(it) }
                            if (cliente != null) {

                                if (contraseya?.let { coder.encriptarContrasenya(it) } == cliente.contrasenya) {
                                    if (dni != null) {
                                        println("w2")
                                        clienteInterfaz(dni)
                                    }

                                } else {
                                    println("Contraseña incorrecta")
                                }
                            }

                        } catch (e: Exception) {
                            println("\\\\ No se ha encontrado ningun usuario con esa informacion // ")
                        }

                    }


                }
                2 -> {
                    println("Menu de inicio de sesion")
                    try {
                        println("Introduzca el CIF de la empresa")
                        val cif = readln()

                        println("Introduzca la contraseña de la empresa")
                        val contraseya = readln()

                        val taller = gestor.selectTallerByCif(cif)
                        if (taller != null) {
                            if (coder.encriptarContrasenya(contraseya) == taller.contrasenya) talleresInterfaz(cif)
                        }


                    } catch (e: Exception) {
                        println("No existe una Empresa con esa informacion")
                    }
                }
                3 -> {
                    println("Menu de registro")
                    try {
                        println("1. Registrarse como cliente")
                        println("2. Registrarse como Taller")
                        var opcion2 = 0
                        while (opcion2 == 0) {
                            try {
                                opcion2 = readln().toInt()
                            } catch (e: Exception) {
                                opcion2 = 0
                                println("Caracter invalidos, introduzca un numero del 1 al 3")
                            }
                        }
                        when (opcion2) {
                            1 -> registroCliente()
                            2 -> registroTaller()
                        }
                    } catch (e: Exception) {
                        println("Error al realizar el registro")
                    }
                }
                4 -> {
                    println("Saliendo...")
                    gestor.close()
                }
                else -> println("Opción inválida.")
            }
        }
    }

    private fun registroTaller() {
        try {
            println("Bienvenido al sistema de registro")
            println("Nombre: ")
            val nombre = readln()
            println("CIF: ")
            val cif = readln()
            println("Contraseña: ")
            val contra = coder.encriptarContrasenya(readln())
            println("Sistema de gestion de direcciones")
            println("Calle: ")
            val calle = readln()
            println("Numero: ")
            val numero = readln().toShort()
            println("Codigo postal: ")
            val cp = readln().toShort()
            println("Ciudad: ")
            val ciudad = readln()


            gestor.insertTaller(Taller(cif,nombre, contrasenya = contra, direccion = Direccion(calle, numero, cp, ciudad)))
        }catch (e: Exception){
            println("Error en la entrada, no ponga caracteres no numericos en NUMERO Y CODIGOPOSTAL")
            registroTaller()
        }
    }

    private fun registroCliente() {
        try {
            println("Bienvenido al sistema de registro")
            println("Nombre: ")
            val nombre = readln()
            println("DNI: ")
            val dni = readln()
            println("email: ")
            val email = readln()

            println("Contraseña")
            val contra = coder.encriptarContrasenya(readln())
            println("Sistema de gestion de direcciones")
            println("Calle: ")
            val calle = readln()
            println("Numero: ")
            val numero = readln().toShort()
            println("Codigo postal: ")
            val cp = readln().toShort()
            println("Ciudad: ")
            val ciudad = readln()

            gestor.insertCliente(Cliente(dni, nombre, email, contra, Direccion(calle, numero, cp, ciudad)))

        }catch (e: Exception){
            println("Error en la entrada, no ponga caracteres no numericos en NUMERO Y CODIGOPOSTAL")
            registroCliente()
        }

    }

    fun login() {
        welcome()

    }

    fun clienteInterfaz(dni: String) {
        println("Acceso Concedido")


        while (true) {
            println("Menu Cliente")
            println("1. Mostrar pedidos")
            println("2. Mostrar talleres")
            println("3. Hacer pedido")
            println("4. Cerrar Sesion")
            println("Ingresa tu opción:")

            var opcion = 0
            while (opcion == 0) {
                try {
                    opcion = readln().toInt()
                } catch (e: Exception) {
                    opcion = 0
                    println("Caracter invalidos, introduzca un numero del 1 al 3")
                }
            }
            when (opcion) {
                1 -> showOrdersClient(dni)
                2 -> showWorkshops(dni)
                3 -> makeOrder(dni)
                4 -> break
                else -> {
                    println("Opción inválida, intente de nuevo.")
                }
            }
        }
        println("Saliendo del sistema...")
    }

    private fun makeOrder(dni: String) {
        val clienteNow = gestor.selectClienteByDni(dni)
        println("Para realizar al pedido:")
        try {
            println("Introduzca el nombre del pedido: ")
            val name = readln()
            val newOrder = Pedido(null, clienteNow, name)
            gestor.insertPedido(newOrder)
        } catch (_: Exception) {
            println("ERROR AL HACER EL PEDIDO, INTENTELO DE NUEVO")
        }
    }

    private fun showWorkshops(dni: String) {
        try {
            println("Tu lista de Talleres: ")
            gestor.talleresCliente(dni)?.forEach { println(it.nombre) }
        } catch (e: Exception) {
            println("Tu lista de Talleres esta vacia. :C")
        }

    }

    private fun showOrdersClient(dni: String) {
        try {
            val pedidos = gestor.pedidosCliente(dni)
            println("Tu lista de pedidos: ")
            pedidos.forEach { println(mapOf(Pair(it.id, it.descripcion))) }

        } catch (e: Exception) {
            println("Tu lista de pedidos esta vacia :C")
        }

    }

    private fun showAssociatedOrders(cif: String) {
        println("Su lista de pedidos asociados: ")
        try {
            val pedidosAs = gestor.pedidosAsociados(cif)
            pedidosAs.forEach { println(it) }
        } catch (e: Exception) {
            println("Su lista de pedidos esta vacia :C")
        }

    }

    private fun showAssociatedClients(cif: String) {
        println("Su lista de clientes asociados: ")
        try {
            val pedidosAs = gestor.clientesAsociados(cif)
            pedidosAs?.forEach { println(it) }
            if (pedidosAs != null) {
                if (pedidosAs.isEmpty()) {
                    println("Su lista de clientes esta vacia :C")
                }
            }
        } catch (e: Exception) {
            println("Su lista de clientes esta vacia :C")
        }

    }

    fun talleresInterfaz(cif: String) {
        println("Acceso Concedido")
        while (true) {
            println("Menu Taller")
            println("1. Mostrar pedidos asociados")
            println("2. Mostrar clientes asociados")
            println("3. Mostrar pedidos sin asociar")
            println("4. Cerrar Sesion")
            println("Ingresa tu opción:")

            var opcion = 0
            while (opcion == 0) {
                try {
                    opcion = readln().toInt()
                } catch (e: Exception) {
                    opcion = 0
                    println("Caracter invalidos, introduzca un numero del 1 al 4")
                }
            }
            when (opcion) {
                1 -> showAssociatedOrders(cif)
                2 -> showAssociatedClients(cif)
                3 -> showNotAssociatedOrders(cif)
                4 -> break
                else -> {
                    println("Opción inválida, intente de nuevo.")
                }
            }
        }
        println("Saliendo del sistema...")

    }

    private fun showNotAssociatedOrders(cif: String) {
        try {
            val taller = gestor.selectTallerByCif(cif)
            val pedidosNotAssociated = gestor.selectAllPedidosSinTaller()
            pedidosNotAssociated?.forEach { println(mapOf(Pair(it.id, it.descripcion))) }

            if (!pedidosNotAssociated.isNullOrEmpty()) {
                println("¿Desea asociarse un pedido? Y/N")
                val respuesta = readln().toUpperCase()
                if (respuesta == "Y" || respuesta == "YES") {
                    println("Ponga el ID del Pedido que desea.")
                    val id = readln().toInt()
                    try {
                        for (element in pedidosNotAssociated) {
                            if (element.id == id) {

                                element.taller = taller
                                gestor.insertPedido(element)
                            }
                        }
                    } catch (e: Exception) {
                        println("Error al apropiarse del Pedido.")
                    }
                }
            }
        } catch (e: Exception) {
            println("En este momento no hay pedidos sin asociar.")
        }
    }


}
class Gui {
    private val gestor = GestorBBDD()
    private val coder = EncriptacionContraseña
    fun init() {
        //PRUEBAS
        var taller = Taller("123123", "xd", Direccion("s", 1, 1, "d"))
        val clientepr = Cliente("x123123", "Naim", "naim@gmail.com", coder.encriptarContrasenya("123123"), null, talleres = mutableSetOf(taller))
        gestor.insertCliente(clientepr)

        gestor.insertTaller(taller)
        var pedido = Pedido(taller, clientepr, "mantecado")
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
            println("3. Salir")

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
                    var dni = "x"
                    var contraseya = "x"
                    while (contraseya == "x") {
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
                        var cliente = Cliente("df", "df", "df", "df", null, null)
                        try {
                            cliente = gestor.selectClienteByDni(dni)
                            if (coder.encriptarContrasenya(contraseya) == cliente.contrasenya) {
                                clienteInterfaz(dni)
                            } else {
                                println("Contraseña incorrecta")
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

                        val taller = gestor.selectTallerByCIF(cif)
                        println(taller.nombre)
                        talleresInterfaz(cif)
                    }catch (e:Exception){
                        println("No existe una Empresa con ese cif")
                    }
                }
                3 -> {
                    println("Saliendo...")
                    gestor.close()
                }
                else -> println("Opción inválida.")
            }
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

    private fun showWorkshops(dni : String) {
        try {
            val tallers = gestor.talleresCliente(dni)
            println("Tu lista de Talleres: ")
            tallers.forEach { println(it.nombre) }
        }catch (e : Exception){
            println("Tu lista de Talleres esta vacia. :C")
        }

    }

    private fun showOrdersClient(dni: String) {
        try {
            val pedidos = gestor.pedidosCliente(dni)
            println("Tu lista de pedidos: ")
            pedidos.forEach { println(mapOf(Pair(it.id, it.descripcion))) }

        }catch (e: Exception){
            println("Tu lista de pedidos esta vacia :C")
        }

    }

    private fun showAssociatedOrders(cif: String){
        println("Su lista de pedidos asociados: ")
        try {
            val pedidosAs = gestor.pedidosAsociados(cif)
            pedidosAs.forEach{ println(it)}
        }catch (e: Exception){
            println("Su lista de pedidos esta vacia :C")
        }

    }

    private fun showAssociatedClients(cif: String){
        println("Su lista de clientes asociados: ")
        try {
            val pedidosAs = gestor.clientesAsociados(cif)
            pedidosAs.forEach{ println(it)}
            if (pedidosAs.isEmpty()){println("Su lista de clientes esta vacia :C")}
        }catch (e: Exception){
            println("Su lista de clientes esta vacia :C")
        }

    }

    fun talleresInterfaz(cif: String) {
        println("Acceso Concedido")
        while (true) {
            println("Menu Taller")
            println("1. Mostrar pedidos asociados")
            println("2. Mostrar clientes asociados")
            println("3. Cerrar Sesion")
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
                3 -> break
                else -> {
                    println("Opción inválida, intente de nuevo.")
                }
            }
        }
        println("Saliendo del sistema...")

    }


}
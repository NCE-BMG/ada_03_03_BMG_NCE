object EncriptacionContraseña {
    fun encriptarContrasenya(contrasenya:String): String {
        var contrasenyaEncriptada=""
        contrasenya.forEach { contrasenyaEncriptada += (it.code + 9).toChar() }
        return contrasenyaEncriptada
    }
    fun desencriptarContrasenya(contrasenyaEncriptada: String): String {
        var contrasenya=""
        contrasenyaEncriptada.forEach { contrasenya += (it.code + 9).toChar() }
        return contrasenya

    }
}
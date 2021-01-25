fun sendMessageToClient(
    client: Client?, message: String?, mailer: Mailer
) {
    if (message == null) return
    if (client?.personalInfo?.email == null) return
    mailer.sendMessage(message, client.personalInfo.email)
}

class Client(val personalInfo: PersonalInfo?)
class PersonalInfo(val email: String?)
interface Mailer {
    fun sendMessage(email: String, message: String)
}
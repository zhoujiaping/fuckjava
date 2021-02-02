package koans
fun sendMessageToClient(
    client: Client?, message: String?, mailer: Mailer
) {
    when{
        message==null->return
        client?.personalInfo?.email == null->return
        else->mailer.sendMessage(client.personalInfo.email,message)
    }
}

class Client(val personalInfo: PersonalInfo?)
class PersonalInfo(val email: String?)
interface Mailer {
    fun sendMessage(email: String, message: String)
}
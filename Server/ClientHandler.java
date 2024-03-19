import java.io.*;
import java.net.*;
import java.util.*;
//esta clase se debe encargar de gestionar los clientes de forma individual
//implementa la interfaz Runnable y en el metodo run valida el nombre de usuario
//agrega el usuario y su canal de comunicacion a la lista de chatters
//permite enviar mensajes a todos los usuarios
class ClientHandler implements Runnable {
    
    private Socket clientSocket; // Socket para la conexión con el cliente
    private BufferedReader in; // Flujo de entrada para leer los mensajes del cliente
    private PrintWriter out; // Flujo de salida para enviar mensajes al cliente
    private String clientName; // Nombre de usuario del cliente
    Chatters clientes; // Objeto que contiene la lista de clientes conectados

    public ClientHandler(Socket socket,Chatters clientes) {
        //asignar los objetos que llegan a su respectivo atributo en la clase
        //crear canales de entrada in y de salida out para la comunicacion
    }

    @Override
    public void run() {
        String message;

        try {
            clientName = in.readLine(); // Solicita un nombre de usuario a un cliente
            while (clientes.personExist(clientName)) { // Verifica que el nombre de usuario del nuevo cliente no exista
                out.println("Nombre de usuario ya existente. Por favor, ingrese otro nombre."); // Solicita de nuevo el nombre si ese nombre ya está en uso
                clientName = in.readLine();
            }
            clientes.addPerson(clientName, out); // Añade al cliente al chatters con su canal de salida out
            clientes.sendMessageToAll(clientName + " se ha unido al chat."); // Notifica a los demás usuarios que hay un nuevo miembro en el chat
            out.println("Nombre aceptado"); // Notifica al cliente que fue aceptado
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

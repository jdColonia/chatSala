import java.io.*;
import java.net.*;
import java.util.*;

class ClientHandler implements Runnable {

    private Socket clientSocket; // Socket para la conexión con el cliente
    private BufferedReader in; // Flujo de entrada para leer los mensajes del cliente
    private PrintWriter out; // Flujo de salida para enviar mensajes al cliente
    private String clientName; // Nombre de usuario del cliente
    Chatters clientes; // Objeto que contiene la lista de clientes conectados

    public ClientHandler(Socket socket, Chatters clientes) {
        // Asignar los objetos que llegan a su respectivo atributo en la clase
        this.clientSocket = socket;
        this.clientes = clientes;

        // Crear canales de entrada in y de salida out para la comunicación
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        } finally {
            // Eliminar al cliente de la lista de chatters
            clientes.deletePerson(clientName);
            clientes.sendMessageToAll(clientName + " ha salido del chat.");
        }

    }


}

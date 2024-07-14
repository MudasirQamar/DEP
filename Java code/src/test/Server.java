package test;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static Set<PrintWriter> clientWriters = new HashSet<>();
    private static Map<String, PrintWriter> userWriters = new HashMap<>();

    public static void main(String[] args) throws Exception {
        System.out.println("Chat server is active...");
        ServerSocket serverSocket = new ServerSocket(59019);
        try {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } finally {
            serverSocket.close();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Prompt and store the username
                out.println("ENTERUSERNAME");
                username = in.readLine();
                synchronized (userWriters) {
                    userWriters.put(username, out);
                }

                synchronized (clientWriters) {
                    clientWriters.add(out);
                }

                // Notify all clients about the new user
                announce(username + " has joined the chat room!");

                String message;
                while ((message = in.readLine()) != null) {
                    announce(username + ": " + message);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
                synchronized (userWriters) {
                    userWriters.remove(username);
                }
                announce(username + " has left the chat room!");
            }
        }

        private void announce(String message) {
            synchronized (clientWriters) {
                for (PrintWriter writer : clientWriters) {
                    writer.println(message);
                }
            }
        }
    }
}

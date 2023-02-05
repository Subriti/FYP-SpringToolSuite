/*
 * package com.example.project.config;
 * 
 * import java.io.BufferedReader;
 * import java.io.DataOutputStream;
 * import java.io.IOException;
 * import java.io.InputStreamReader;
 * import java.net.ServerSocket;
 * import java.net.Socket;
 * import java.util.HashMap;
 * import java.util.Map;
 * 
 * import org.springframework.context.annotation.Configuration;
 * 
 * 
 * @Configuration
 * public class Server {
 * private static final int PORT = 9090;
 * private static Map<String, DataOutputStream> clients = new HashMap<>();
 * 
 * public static void main(String[] args) throws IOException {
 * ServerSocket serverSocket = new ServerSocket(PORT);
 * System.out.println("Server is running on port " + PORT);
 * 
 * while (true) {
 * Socket connectionSocket = serverSocket.accept();
 * BufferedReader inFromClient = new BufferedReader(new
 * InputStreamReader(connectionSocket.getInputStream()));
 * DataOutputStream outToClient = new
 * DataOutputStream(connectionSocket.getOutputStream());
 * 
 * // Read the message from the client
 * String message = inFromClient.readLine();
 * System.out.println("FROM CLIENT: " + message);
 * 
 * // Parse the client ID
 * String clientId = message.split(":")[0];
 * System.out.println("CLIENT ID is: " +clientId);
 * 
 * // Store the client in the map
 * clients.put(clientId, outToClient);
 * 
 * // Send a response to the client
 * outToClient.writeBytes("Server: Message received" + '\n');
 * }
 * }
 * }
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package membot.robot;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roman
 */
public class StarteTcpServer implements Runnable {

    public static String inputLine = "100";
    public static String tempInputLine = "100";
    public static Boolean newValueArrieved = false;
    ICommandServer cs;

    public StarteTcpServer(ICommandServer cs) {
        this.cs = cs;
    }

    @Override
    public void run() {
        while (true) {
            BufferedReader in = null;
            PrintWriter out = null;
            Socket clientSocket = null;
            ServerSocket serverSocket = null;
            try {
                int port = 10008;
                try {
                    serverSocket = new ServerSocket(port);
                } catch (IOException e) {
                    System.err.println("Could not listen on port:  %s"
                            + Integer.toString(port));
                    System.exit(1);
                }
                System.out.println("Waiting for connection.....");
                try {
                    clientSocket = serverSocket.accept();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
                }
                System.out.println("Connection successful");
                System.out.println("Waiting for input.....");
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.
                        getInputStream()));
                while ((tempInputLine = in.readLine()) != null) {
                    inputLine = tempInputLine;
                    System.out.println("Server: " + inputLine);
                    newValueArrieved = true;
                    out.println("Input from Client: " + inputLine);
                    System.out.println(serverSocket.getSoTimeout());
                    cs.setCommandString(inputLine);
                    if (inputLine.equals("Bye.")) {
                        break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(StarteTcpServer.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
            try {
                out.close();
                in.close();
                clientSocket.close();
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(StarteTcpServer.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
            System.out.println("Client hat Verbindung geschlossen");
        }
    }
}

package chat_server;

import java.net.*;
import java.io.*;

public class Chat_server implements Runnable {

    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream streamIn = null;
    private Thread thread = null;

    public Chat_server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Chat server started on port " + port);
            start();
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
    }

    public void run() {
        while (thread != null) {
            try {
                System.out.println("Waiting for a client ...");
                socket = server.accept();
                System.out.println("Client accepted: " + socket);
                open();
                boolean exit = false;
                while (!exit) {
                    try {
                        String line = streamIn.readUTF();
                        System.out.println(line);
                        exit = line.equals("KILL_SERVICE\n");
                    } catch (IOException ex) {
                        exit = true;
                    }
                }
                close();
            } catch (IOException ie) {
                System.out.println("Error: " + ie);
            }
        }
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void open() throws IOException {
        streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }

    public void close() throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (streamIn != null) {
            streamIn.close();
        }
    }

    public static void main(String[] args) {
        Chat_server server = new Chat_server(8001);
    }
}
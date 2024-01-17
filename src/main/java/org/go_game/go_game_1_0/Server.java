package org.go_game.go_game_1_0;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static Set<PrintWriter> writers = new HashSet<>();

    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;

    public static void main(String[] args) throws Exception {
        System.out.println("Server is running...");
//        ServerSocket listener = new ServerSocket(PORT);

//        try {
//            while (true) {
//                new Handler(listener.accept()).start();
//            }
//        } finally {
//            listener.close();
//        }
//    }

//    private static class Handler extends Thread {
//        private Socket socket;
//        private PrintWriter out;
//        private BufferedReader in;
//
//        public Handler(Socket socket) {
//            this.socket = socket;
//        }
//
//        public void run() {
//            try {
//                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                out = new PrintWriter(socket.getOutputStream(), true);
//
//                writers.add(out);
//
//                while (true) {
//                    String input = in.readLine();
//                    if (input == null) {
//                        return;
//                    }
//                    for (PrintWriter writer : writers) {
//                        writer.println("MESSAGE " + input);
//                    }
//                }
//            } catch (IOException e) {
//                System.out.println(e);
//            } finally {
//                if (out != null) {
//                    writers.remove(out);
//                }
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                }
//            }
//        }
    }
}
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

public class ChatClient { // Class name corrected to ChatClient
//    private Socket socket = null;
//    private BufferedReader inputConsole = null;
//    private PrintWriter out = null;
//    private BufferedReader in = null;
//
//    public ChatClient(String address, int port) {
//        try {
//            socket = new Socket(address, port);
//            System.out.println("Connected to the chat server");
//
//            inputConsole = new BufferedReader(new InputStreamReader(System.in));
//            out = new PrintWriter(socket.getOutputStream(), true);
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//            String line = "";
//            while (!line.equals("exit")) {
//                line = inputConsole.readLine();
//                out.println(line);
//                System.out.println(in.readLine());
//            }
//        } catch (UnknownHostException u) {
//            System.out.println("Host unknown: " + u.getMessage());
//        } catch (IOException i) {
//            System.out.println("Unexpected exception: " + i.getMessage());
//        } finally {
//            try {
//                if (socket != null) socket.close();
//                if (inputConsole != null) inputConsole.close();
//                if (out != null) out.close();
//                if (in != null) in.close();
//            } catch (IOException i) {
//                System.out.println("Error closing resources: " + i.getMessage());
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        new ChatClient("127.0.0.1", 5000);
//    }
//}


        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private Consumer<String> messageHandler;

        public ChatClient(String address, int port, Consumer<String> messageHandler) throws IOException {
            this.socket = new Socket(address, port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.messageHandler = messageHandler;
        }

        public void startClient() {
            new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        messageHandler.accept(message);
                    }
                } catch (IOException e) {
                    System.err.println("Error reading from server: " + e.getMessage());
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        System.err.println("Error closing socket: " + e.getMessage());
                    }
                }
            }).start();
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }




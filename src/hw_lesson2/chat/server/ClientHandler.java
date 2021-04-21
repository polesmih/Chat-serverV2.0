package hw_lesson2.chat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class ClientHandler {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ChatServer chatServer;
    private String name;
    private String login;
    private String password;

    public ClientHandler(Socket socket, ChatServer chatServer) {
        this.socket = socket;
        this.chatServer = chatServer;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new ChatServerException("Something went wrong during client establishing.", e);
        }

        new Thread(() -> {

            try {
                socket.setSoTimeout(120000);
            } catch (SocketException e) {
                e.printStackTrace();
            }

            try {
                doAuthentication();
            } catch (IOException e) {
                e.printStackTrace();
            }

            listen();
        })
                .start();
    }


    public String getName() {
        return name;
    }


    private void listen() {
        receiveMessage();
    }


    private void doAuthentication() throws IOException {
        sendMessage("Welcome! \nPlease do authentication - use command: auth your_login your_pass.");
        while (true) {
            try {

                String message = in.readUTF();

                if (message.startsWith("auth")) {
                    String[] credentialsStruct = message.split("\\s");


//start of code change
                        String newName = AuthenticationService.getNameByLoginAndPassword(credentialsStruct[1], credentialsStruct[2]);
                        login = credentialsStruct[1];
                        password = credentialsStruct[2];

                    if (newName != null) {
                        if (!ChatServer.isLoginAuthenticated) {
                            name = newName;
                            sendMessage(name + " authenticated");
                            socket.setSoTimeout(0);
                            chatServer.subscribe(this);
                            System.out.println("user " + name + " connected " + socket.getRemoteSocketAddress());
                            break;
                        } else {
                            sendMessage("User is already logged in");
                        }
                    } else {
                        sendMessage("Incorrect login or password");
                    }
                }


                    if (message.startsWith("reg")) {
                        String[] token = message.split("\\s");
                        if (token.length < 4) {
                            continue;
                        }
                        boolean isRegistered = AuthenticationService.registration(token[1], token[2], token[3]);


                        if (isRegistered) {
                            sendMessage("registration successfully");
                        } else {
                            sendMessage("registration unsuccessfully");
                        }
                    }

//finish of code change


            } catch (SocketTimeoutException e) {
                sendMessage("Your login time has expired. Connected is broken.");
                socket.close();
                break;

            } catch (IOException e) {
                throw new ChatServerException("Something went wrong during client authentication.", e);
            }
        }
    }


    public void receiveMessage() {
        while (true) {
            try {
                String message = in.readUTF();

                if (message.startsWith("/w")) {
                    String[] split = message.split("\\s", 3);
                    chatServer.sendPrivateMessage(this, split[1], split[2]);
                    sendMessage("to [ " + split[1] + " ]: " + split[2]);
                } else {
                    chatServer.broadcast(String.format("%s: %s", name, message));
                }

            } catch (IOException e) {
                throw new ChatServerException("Something went wrong during receiving the message.", e);
            }
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new ChatServerException("Something went wrong during sending the message.", e);
        }
    }


}
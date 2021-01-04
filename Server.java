import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.*;

public class Server {

    ServerSocket server;

    boolean isThereRandClient;
    ClientHandler firstRandClient, tempClient;
    Hashtable<String, ClientHandler> specClients;

    //to create unique game id
    private static final long LIMIT = 10000000000L;
    private static long last = 0;

    public Server(int tcpport) throws IOException {
        // Create a ServerSocket object that listens to port 9000 and waits for clients
        // to connect
        server = new ServerSocket(tcpport);
        System.out.println("Server started at port: " + tcpport);

        isThereRandClient = false;
        specClients = new Hashtable<String, ClientHandler>();

        startServer();
    }

    public void startServer() throws IOException {
        while (true) {
            Socket s = server.accept();
            tempClient = new ClientHandler(s, "Player");

            String gameType = tempClient.in.readLine();

            if (gameType.equals("INTRANDOM")) {
                if (isThereRandClient) {
                    tempClient.setID("Player2");
                    Table newGame = new Table(firstRandClient, tempClient);

                    isThereRandClient = false;
                    firstRandClient = null;
                } else {
                    isThereRandClient = true;
                    firstRandClient = tempClient;
                    firstRandClient.setID("Player1");
                }
            } else if (gameType.equals("INTSPEC")) {
                String gameOption = tempClient.in.readLine();

                if (gameOption.equals("CREATEGAME")) {
                    String gameID = createGameID();
                    tempClient.setID("Player1");
                    specClients.put(gameID, tempClient);

                    tempClient.sendMessage(gameID);
                } else if (gameOption.equals("JOINGAME")) {
                    String gameID = tempClient.in.readLine();

                    if (gameID != null && specClients.containsKey(gameID)) {
                        tempClient.setID("Player2");
                        Table newGame = new Table(specClients.get(gameID), tempClient);
                        specClients.remove(gameID);
                    } else {
                        tempClient.sendMessage("NOTFOUND");
                    }
                }
            }
        }
    }

    // create 10 digit unique id string
    public static String createGameID() {
        long id = System.currentTimeMillis() % LIMIT;
        if (id <= last) {
            id = (last + 1) % LIMIT;
        }
        last = id;

        return String.valueOf(id);
    }
}
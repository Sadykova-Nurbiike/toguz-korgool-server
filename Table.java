import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Table extends Thread {

    ClientHandler player1, player2;
    protected BufferedReader in1, in2;
    protected PrintWriter out1, out2;
    int turn;

    /**
     * this class controls messages sent between the two players.
     * NOTE this class is constructed to work together with the client.
     *
     * @param player1
     * @param player2
     * @throws IOException
     */
    public Table(ClientHandler player1, ClientHandler player2) throws IOException {
        this.player1 = player1;
        this.player2 = player2;

        String start1 = "START1";
        String start2 = "START2";
        player1.sendMessage(start1);
        player2.sendMessage(start2);
        start();
    }

    public void gamePlay() {
        turn = 1;
        try {
            while (true) {
                if (turn == 1) {
                    String received = player1.in.readLine();
                    System.out.println(received);
                    if (received == null) {
                        player2.sendMessage("EXIT");
                        System.out.println("EXIT message has sended to player 2");
                        break;
                    } else {
                        player2.sendMessage(received);
                        turn = 2;
                    }
                } else if (turn == 2) {
                    String received = player2.in.readLine();
                    System.out.println(received);
                    if (received == null) {
                        player1.sendMessage("EXIT");
                        System.out.println("EXIT message has sended to player 1");
                        break;
                    } else {
                        player1.sendMessage(received);
                        turn = 1;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("The game has terminated because of connection problems");
            player1.sendMessage("EXIT");
            player2.sendMessage("EXIT");
        }
    }

    public void run() {
        gamePlay();
    }
}

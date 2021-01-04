import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    protected Socket s;
    public String id;
    public BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket soc, String id) {
        this.id = id;
        this.s = soc;
        // if the socket is not null, then the stream readers and writers are constructed
        try {
            if (s != null) {
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the id of the player; Player1 or Player2
     */
    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    /**
     * This method is used for sending a message to the client that this socket is
     * matched to
     *
     * @param msg
     */
    public synchronized void sendMessage(String msg) {
        if (out != null) {
            out.println(msg);
            out.flush();
        }
    }

    /**
     * run method for this thread
     */
    public void run() {
        try {
            while (true) {
                String message = in.readLine();
            }
        } catch (IOException e) {
        }
    }
}

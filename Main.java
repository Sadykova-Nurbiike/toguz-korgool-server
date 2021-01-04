import java.io.IOException;

public class Main {
    public Main(int tcpport) throws IOException {
        new Server(tcpport);
    }

    public static void main(String[] args) throws IOException {
        new Main(9000);
    }
}
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class ScannerClass extends Thread {

    String name;
    int countyID;

    ScannerClass(String name, int countyID) {
        this.name = name;
        this.countyID = countyID;
    }

    private int randomOneOrZero() {
        double value = Math.random();
        if (value < 0.5)
            return 0;
        return 1;
    }

    @Override
    public void run() {
        int count = 0;
        InetAddress address;
        try {
            address = InetAddress.getByName(null);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }

        try(Socket socket = new Socket(address, this.countyID)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

            while (true) {
                count++;
                int candidateA = this.randomOneOrZero();
                int candidateB = this.randomOneOrZero();
                int candidateC = this.randomOneOrZero();
                System.out.println(count + " - " + this.name + " " + candidateA + " " + candidateB + " " + candidateC);
                out.println(this.name);
                out.println(candidateA);
                out.println(candidateB);
                out.println(candidateC);

                try {
                    Thread.sleep(new Random().nextInt(1000)+3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

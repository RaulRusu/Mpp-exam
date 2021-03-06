package ro.ubb.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountyServer {
    private ExecutorService executorService;
    private String name;
    private int countyID;

    public CountyServer(String name, int countyID) {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.name = name;
        this.countyID = countyID;
    }

    public void startServer() {
        System.out.println("Server started");

        try (ServerSocket serverSocket = new ServerSocket(this.countyID)) {
            while (true) {
                Socket scannerSocket = serverSocket.accept();
                System.out.println("Scanner connected!");
                executorService.submit(new ScannerHandler(scannerSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ScannerHandler implements Runnable{
        Socket scannerSocket;

        public ScannerHandler(Socket scannerSocket) {
            this.scannerSocket = scannerSocket;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(scannerSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(scannerSocket.getOutputStream()),true);

                    String scannerName = in.readLine();
                    String candidateA = in.readLine();
                    String candidateB = in.readLine();
                    String candidateC = in.readLine();

                    System.out.println(scannerName + ", " + candidateA + ", " + candidateB + ", " + candidateC);

                    try {
                        Thread.sleep(new Random().nextInt(1000)+4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

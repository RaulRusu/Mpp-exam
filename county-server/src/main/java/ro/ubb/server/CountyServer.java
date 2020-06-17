package ro.ubb.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import ro.ubb.monitor.core.model.Vote;
import ro.ubb.monitor.web.dto.VoteDto;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CountyServer {
    private ExecutorService executorService;
    private String name;
    private int countyID;
    private ReentrantLock lock;
    private int totalA;
    private int totalB;
    private int totalC;

    public CountyServer(String name, int countyID) {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        this.name = name;
        this.countyID = countyID;
        this.lock = new ReentrantLock();
        totalA = 0;
        totalB = 0;
        totalC = 0;
    }

    public void startServer() {
        System.out.println("Server started");
        executorService.submit(new VoteSender());
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
                    lock.lock();

                    totalA += Integer.parseInt(candidateA);
                    totalB += Integer.parseInt(candidateA);
                    totalC += Integer.parseInt(candidateA);

                    lock.unlock();

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

    private class VoteSender implements Runnable {
        int oldA;
        int oldB;
        int oldC;

        @Autowired
        RestTemplate restTemplate;

        VoteSender() {
            this.oldA = 0;
            this.oldB = 0;
            this.oldC = 0;
        }

        @Override
        public void run() {
            while (true) {
                if(this.oldA != totalA || this.oldB != totalB || this.oldC != totalC) {
                    lock.lock();
                    VoteDto vote = new VoteDto(name, totalA, totalB, totalC, totalA + totalB + totalC);
                    lock.unlock();
                    System.out.println("Sending data to rest api");
                    this.restTemplate.postForEntity("http://localhost:8080/api/voting", vote, vote.getClass());
                    System.out.println(1);
                }
                else {
                    System.out.println("data not changed");
                }


                try {
                    Thread.sleep(new Random().nextInt(1000)+4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

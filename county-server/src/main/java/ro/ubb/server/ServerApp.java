package ro.ubb.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class ServerApp {
    private static CountyServer readCountyServer() {
        Scanner scanner = new Scanner(System.in);

        CountyServer countyServer = null;

        try {
            System.out.println("County name: ");
            String name = scanner.nextLine();
            System.out.println("County id: ");
            int id = Integer.parseInt(scanner.nextLine());

            countyServer = new CountyServer(name, id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return countyServer;
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "ro.ubb.server"
                );
        context.getBean("restTemplate");

        //CountyServer countyServer = readCountyServer();
        CountyServer countyServer = new CountyServer("C1", 1111);
        if (countyServer != null) {
            countyServer.startServer();
        }
    }
}

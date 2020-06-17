import java.util.Scanner;

public class ScannerApp {
    public static ScannerClass readScanner() {
        Scanner scanner = new Scanner(System.in);

        ScannerClass scannerClass = null;

        try {
            System.out.println("Voting station name: ");
            String name = scanner.nextLine();
            System.out.println("County id: ");
            int id = Integer.parseInt(scanner.nextLine());

            scannerClass = new ScannerClass(name, id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return scannerClass;
    }

    public static void main(String[] args) {
        //ScannerClass scannerClass = readScanner();
        ScannerClass scannerClass = new ScannerClass("V1", 1111);
        if (scannerClass != null) {
            scannerClass.start();
        }
    }
}

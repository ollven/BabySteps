import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int cycle = console.nextInt();
        console.nextLine();
        System.out.println();
        for (int n = 0; n < cycle; n++) {
            String fromconsole = console.nextLine();
            Scanner toint = new Scanner(fromconsole);
            int a = toint.nextInt();
            int b = toint.nextInt();
            int c = toint.nextInt();
            int sum = result(a, b, c);
            System.out.print(sum + " ");
            System.out.print(sum + "CSPcheck333")

        }
    }

    public static int result(int a, int b, int c) {
        int result = a * b + c;
        int sum = 0;
        while (result != 0) {
            sum = sum + (result % 10);
            result = result / 10;
        }
        return sum;
    }

}

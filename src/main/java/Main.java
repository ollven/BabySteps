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
            System.out.print(sum + "CSPcheck333");
            System.out.print(sum + "TW-70782");
            System.out.print(sum + "TTW-83609");
          	System.out.print(sum + "CodeReview34566juhlhljhhj");
            System.out.print(sum + "updateCode");
            System.out.print(sum + "updateCode333");
            System.out.print(sum + "updateTC");
            System.out.print(sum + "MultinideOtherVersionDeafult");


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





        /*String line = scanner.nextLine();
        String[] s = line.split(" ");
        int a = Integer.parseInt(s[0]);
        int b = Integer.parseInt(s[1]);
        int c = Integer.parseInt(s[2]);*/
//попробовать превратить в List
//new cfarggfaer


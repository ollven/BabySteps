import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        int cycle = console.nextInt();
        System.out.println();
        for (int n = 0; n <= cycle; n++) {
            String fromconsole = console.nextLine();
            //console.useDelimiter("[ ,\r]");
            Scanner toint = new Scanner(fromconsole);
            while (toint.hasNext()) {
                console.useDelimiter("[ ,\r]");
                int a = toint.nextInt();
                int b = toint.nextInt();
                int c = toint.nextInt();
                int result = a * b + c;
                int sum = 0;
                for (int i = 0; result != 0; i++) {
                    sum = sum + (result % 10);
                    result = result / 10;
                }
                System.out.print(sum + " ");
            }
        }
    }

}





        /*String line = scanner.nextLine();
        String[] s = line.split(" ");
        int a = Integer.parseInt(s[0]);
        int b = Integer.parseInt(s[1]);
        int c = Integer.parseInt(s[2]);*/
//попробовать превратить в List

//System.out.println(a + b + c);
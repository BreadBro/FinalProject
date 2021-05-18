import java.io.*;
import java.nio.file.*;
import java.util.*;
public class helpPPL {
    public static Path path = Paths.get("PoliticalAlignment.java").toAbsolutePath().getParent();
    public static Scanner sc = new Scanner(System.in);
    public static void main(String [] args) throws FileNotFoundException{
        EllisCode();
    }

    public static void MyCode() throws FileNotFoundException {
        System.out.println("a");
        String abc = sc.next();
        Path path = Paths.get("PoliticalAlignment.java").toAbsolutePath().getParent();
        Scanner fileSC = new Scanner(new File(path + "\\src\\politicians.txt"));
        String[] line = new String[4];
        while (fileSC.hasNextLine()) {
            line[0] = fileSC.nextLine();
            if (line[0].contains(abc)) {
                line[1] = line[0];
                line[2] = fileSC.nextLine();
                line[3] = fileSC.nextLine();
            }
        }
        System.out.println(line[1]);
        System.out.println(line[2]);
        System.out.println(line[3]);
    }
    public static void EllisCode () throws FileNotFoundException {
        Scanner input = new Scanner(new File(path + "\\src\\politicians.txt"));
        Scanner scheduler = new Scanner(System.in);
        String name = scheduler.nextLine();
        System.out.println("What Grade are you in?");
        String grade = scheduler.next();
        String grade1;
        String[] lines = new String[7];
        while (input.hasNextLine()) {
            grade1 = input.nextLine();
            if (grade1.contains(grade)) {
                lines[1] = lines[0];
                lines[2] = input.nextLine();
                lines[3] = input.nextLine();

            }
        }
        System.out.println(lines[1]);
        System.out.println(lines[2]);
        System.out.println(lines[3]);
    }
}

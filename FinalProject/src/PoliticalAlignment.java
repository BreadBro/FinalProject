import java.util.*;
import weka.core.UnassignedClassException;
import java.nio.file.*;
import java.io.*;
import java.io.FileWriter;
public class PoliticalAlignment {
    public static void main(String [] args) throws Exception, FileNotFoundException, IOException, UnassignedClassException {
        menu();

    }
    
    public static Scanner sc = new Scanner(System.in);


    public static void menu() throws Exception, FileNotFoundException, IOException, UnassignedClassException {
        int x = 1;
        int answ = 0;
        //basic while loop menu
        System.out.println("This program will find out your political affiliations. Where would you like to begin? You can find information on an American politican, ");
        System.out.println("find what party you fit into, find what politicians fit your beliefs, etc.");
        System.out.println("After every action, you will automatically be returned to the menu. If you want to exit, type 4 into the menu.");
        while (x == 1) {     
            try {
                Thread.sleep(1000);
            }     
            catch (Exception E) {
                System.out.println("This is dumb.");
            }
            System.out.println("\n1. Find Info on Politican");
            System.out.println("2. Find the Party that you fit into");
            System.out.println("3. Find a Politician who aligns with your ideals");
            System.out.println("4. Exit");
            answ = 0;
            answ = sc.nextInt();
            if (answ == 1) {
                FindPolitican();
            }

            if (answ == 2) {
                FindParty();
            }

            if (answ == 3) {
                IdealPolitician();
            }

            if (answ == 4) {
                System.exit(1);
            }
        }
    }

    public static void FindPolitican() throws FileNotFoundException, IOException {
        System.out.print("What politician would you like to find information on? ");
        String name = sc.next();
        String lineWithName = Politics.FindPolitican(name);
        if (!lineWithName.contains(name)) {
            System.out.println("Your politician was not found in the database. Would you like to add them?");
            if (sc.next().toLowerCase().contains("y")) {
                AddPolitician();
            }
            else {
                System.out.println("That is too bad.");
            }
        }
        else {
            System.out.println(Politics.toString(lineWithName, name));
        }
    }

    public static void FindParty() throws Exception, FileNotFoundException, IOException, UnassignedClassException {
        String[] aboutYou = new String[5];
        System.out.print("What is your ethnicity (white, black, asian, hispanic, or native american)? ");
        aboutYou[0] = sc.next().toLowerCase();
        if (aboutYou[0].contains("__/")) {
            aboutYou[0] = "white";
            aboutYou[1] = "70";
            aboutYou[2] = "male";
            aboutYou[3] = "nocollege";
            aboutYou[4] = "between";
            Politics person = new Politics(aboutYou);
            person.FindParty();
        }
        else {
            if (aboutYou[0].toLowerCase().contains("native american") || aboutYou[0].toLowerCase().contains("native")) {
                aboutYou[0] = "native_american";
            }
            System.out.print("What is your age? ");
            int age = sc.nextInt();
            aboutYou[1] = String.valueOf(age);
            System.out.print("What is your gender (male, female, other)? ");
            aboutYou[2] = sc.next().toLowerCase();
            System.out.print("Do you have a college degree? ");
            if (sc.next().toLowerCase().contains("y")) {
                aboutYou[3] = "college";
            }
            else {
                aboutYou[3] = "nocollege";
            }
            System.out.print("What is your household income (under 50k, between 50k-99k, over 100k)? ");
            String income = sc.next().toLowerCase();
            if (income.contains("under")) {
                aboutYou[4] = "under";
            }
            if (income.contains("over")) {
                aboutYou[4] = "over";
            }
            else if (income.contains("between")) {
                aboutYou[4] = "between";
            }
            Politics person = new Politics(aboutYou);
            person.FindParty();
        }
    }

    public static void AddPolitician() throws IOException {
        String full = "\n=";
        Scanner sc = new Scanner(System.in);
        Path path = Paths.get("PoliticalAlignment.java").toAbsolutePath().getParent();;
        FileWriter writer = new FileWriter(path + "\\src\\politicians.txt", true);
        int x = 1;
        String[] questions = new String[7];
        questions[0] = "What is the first and last name of the politician? ";
        questions[1] = "How old is the politician? ";
        questions[2] = "What role does the politican play in government (for example: governor of Texas)? ";
        questions[3] = "How tall is the politician (for example: 6 feet and 2 inches)? ";
        questions[4] = "How heavy is the politician (please specify unit)? ";
        questions[5] = "What gender is the politician? ";
        questions[6] = "What party is the politican a part of (democratic or republican)? ";
        while (x == 1) {
            full = "\n=";
            for (int i = 0; i < questions.length; i++) {
                System.out.print(questions[i]);
                full = full + sc.nextLine() + "=";
            }
            System.out.print("Does this look correct? Equals signs separate each piece of information.");
            System.out.println(full);
            if (sc.next().toLowerCase().contains("y")) {
                writer.append(full);
                writer.close();
                System.out.println("They are now in the database.");
                x = 2;
            }

            else {
                System.out.println("Would you like to try again?");
                if (!sc.next().toLowerCase().contains("y")) {
                    x = 2;
                }
            }
        }
        sc.close();
    }

    public static void IdealPolitician() {
        //first number is abortion (choice = 0, life = 1), second on taxing big corporations (support taxing rich = 0, against = 1)
        //third on global warming (believe in it = 0, think it's fake = 1)
    }
}

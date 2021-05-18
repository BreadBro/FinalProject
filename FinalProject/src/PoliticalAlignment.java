import java.util.*;
import weka.core.UnassignedClassException;
import java.nio.file.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
//If something doesnt work, clean java server workspace
public class PoliticalAlignment {
    public static void main(String [] args) throws Exception, FileNotFoundException, IOException, UnassignedClassException {
        menu();
    }
    public static Scanner sc = new Scanner(System.in);
    public static void menu() throws Exception, FileNotFoundException, IOException, UnassignedClassException, NoSuchAlgorithmException {
        int x = 1;
        int answ = 0;
        //basic while loop menu
        System.out.println("This program will find out your political affiliations. Where would you like to begin? You can find information on an American politican, ");
        System.out.println("find what party you fit into, and find what politicians fit your beliefs.");
        System.out.println("After every action, you will automatically be returned to the menu. If you want to exit, type 4 into the menu.");
        System.out.println("(Disclaimer: If you see any warnings, please ignore them)");
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
            System.out.println("4. View Data");
            System.out.println("5. Exit");
            answ = 0;
            answ = sc.nextInt();
            if (answ == 1) {
                System.out.print("What politician would you like to find information on? ");
                String name = sc.next();
                FindPolitican(name);
            }

            if (answ == 2) {
                FindParty();
            }

            if (answ == 3) {
                IdealPolitician();
            }

            if (answ == 4) {
                ViewData();
            }
            
            if (answ == 5) {
                System.exit(1);
            }
        }
        sc.close();
    }

    public static void FindPolitican(String name) throws FileNotFoundException, IOException {
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
            System.out.println(Politics.toString(lineWithName));
        }
    }

    public static void FindParty() throws Exception, FileNotFoundException, IOException, UnassignedClassException {
        String[] aboutYou = new String[5];
        System.out.print("What is your ethnicity (white, black, asian, hispanic, or native american)? ");
        //gives me the ability to skip through all the questoins for bug testing purposes
        aboutYou[0] = sc.next().toLowerCase();
        if (aboutYou[0].contains("__/")) {
            aboutYou[0] = "white";
            aboutYou[1] = "45";
            aboutYou[2] = "male";
            aboutYou[3] = "nocollege";
            aboutYou[4] = "between";
            Politics person = new Politics(aboutYou);
            person.FindParty();
        }
        else {
            //formats the answer to a proper input for the data
            if (aboutYou[0].toLowerCase().contains("native american") || aboutYou[0].toLowerCase().contains("native")) {
                aboutYou[0] = "native_american";
            }
            System.out.print("What is your age? ");
            aboutYou[1] = sc.next();
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
            System.out.println();
            Politics person = new Politics(aboutYou);
            person.FindParty();
        }
    }

    public static void AddPolitician() throws IOException {
        Path path = Paths.get("PoliticalAlignment.java").toAbsolutePath().getParent();
        FileWriter writer = new FileWriter(path + "\\src\\politicians.txt", true);
        int x = 1;
        String full = "\n";
        String[] questions = new String[11];
        //use loops to be a bit more efficient
        questions[0] = "What is the first and last name of the politician? ";
        questions[1] = "How old is the politician? ";
        questions[2] = "What role does the politican play in government (for example: governor of Texas)? ";
        questions[3] = "How tall is the politician (for example: 6 feet and 2 inches)? ";
        questions[4] = "How heavy is the politician (please specify unit)? ";
        questions[5] = "What gender is the politician? ";
        questions[6] = "What party is the politican a part of (democratic or republican)? ";
        questions[7] = "Does the politician support abortion (yes or no)? ";
        questions[8] = "Do you support taxing rich corporations (yes or no)? ";
        questions[9] = "Do you believe that global warming is a serious issue (yes or no)? ";
        questions[10] = "Do you believe that mask wearing should be mandatory (yes or no)? ";
        while (x == 1) {
            //adds data to file
            //this is required for some reason otherwise it overlaps the questions
            String temp = sc.nextLine();
            for (int i = 0; i < questions.length - 4; i++) {
                System.out.print(questions[i]);
                full = full + sc.nextLine() + "/";
            }
            full = full + "<";
            //have to do a second loops cause the last 4 questions have different answers
            for (int i = 7; i < questions.length; i++) {
                System.out.print(questions[i]);
                if (sc.next().toLowerCase().contains("y")) {
                    full = full + "0";
                }
                else {
                    full = full + "1";
                }
            }
            if (full.contains("president")) {
                full = full.replace("president", "_president");
            }
            full = full + ">";
            //checks if data looks right to user
            System.out.print("Does this look correct? separate each piece of information.");
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
                    writer.close();
                }
            }
        }
    }

    public static void IdealPolitician() throws FileNotFoundException, IOException {
        //first number is abortion (choice = 0, life = 1), second on taxing big corporations (support taxing rich = 0, against = 1)
        //third on global warming (believe in it = 0, think it's fake = 1, fourth on masks (masks are necessary = 0, masks are a choice = 0))
        //using this for both questions and answers so I don't have to use two arrays
        String[] questionsAndAnswers = new String[4];
        String answ, changeTo;
        int checkAndChange;
        int x = 1;
        questionsAndAnswers[0] = "Does you support abortion (yes or no)? ";
        questionsAndAnswers[1] = "Do you support taxing rich corporations (yes or no)? ";
        questionsAndAnswers[2] = "Do you believe that global warming is a serious issue (yes or no)? ";
        questionsAndAnswers[3] = "Do you believe that mask wearing should be mandatory (yes or no)? ";
        for (int i = 0; i < questionsAndAnswers.length; i++) {
            System.out.print(questionsAndAnswers[i]);
            if (sc.next().toLowerCase().contains("y")) {
                questionsAndAnswers[i] = "0";
            }
            else {
                questionsAndAnswers[i] = "1";
            }
        }
        Politics opinions = new Politics(questionsAndAnswers[0], questionsAndAnswers[1], questionsAndAnswers[2], questionsAndAnswers[3]);
        //here is where assessors and mutators are used
        while (x == 1) {
            System.out.print("Do you want to check or change any of your opinions (check, change, or no)? ");
            answ = sc.next().toLowerCase();
            if (answ.contains("che")) {
                System.out.print("Which of the four opinions do you want to check? You can only check one at a time. 0 means that you said yes, and 1 means that you said no. ");
                checkAndChange = sc.nextInt();
                if (checkAndChange == 1) {
                    System.out.println(opinions.getOp1());
                }
                if (checkAndChange == 2) {
                    System.out.println(opinions.getOp2());
                }
                if (checkAndChange == 3) {
                    System.out.println(opinions.getOp3());
                }
                if (checkAndChange == 4) {
                    System.out.println(opinions.getOp4());
                }
            }

            if (answ.contains("cha")) {
                System.out.print("Which of the four opinions do you want to change? You can only change one at a time. ");
                checkAndChange = sc.nextInt();
                if (checkAndChange == 1) {
                    changeTo = opinions.getOp1();
                    if (changeTo.contains("1")) {
                        opinions.setOp1("0");
                    }
                    else {
                        opinions.setOp1("1");
                    }
                    System.out.println("It has been changed.");
                }
                if (checkAndChange == 2) {
                    changeTo = opinions.getOp2();
                    if (changeTo.contains("1")) {
                        opinions.setOp2("0");
                    }
                    else {
                        opinions.setOp2("1");
                    }
                    System.out.println("It has been changed.");
                }
                if (checkAndChange == 3) {
                    changeTo = opinions.getOp2();
                    if (changeTo.contains("1")) {
                        opinions.setOp3("0");
                    }
                    else {
                        opinions.setOp3("1");
                    }
                    System.out.println("It has been changed.");
                }
                if (checkAndChange == 4) {
                    changeTo = opinions.getOp4();
                    if (changeTo.contains("1")) {
                        opinions.setOp4("0");
                    }
                    else {
                        opinions.setOp4("1");
                    }
                    System.out.println("It has been changed.");
                }
            }
            else {
                x = 2;
            }
        }
        String name = opinions.Ideal();
        if (name.contains("__/")) {
            System.out.println("No politician perfectly aligns with your ideals.");
        }
        else {
            System.out.println("A politician that aligns with your beliefs is " + name);
            System.out.print("Would you like to find out more information on this politician? ");
            if (sc.next().toLowerCase().contains("y")) {
                FindPolitican(name);
            }
        }
    }

    public static void ViewData() throws NoSuchAlgorithmException, FileNotFoundException, IOException {
        Path path = Paths.get("PoliticalAlignment.java").toAbsolutePath().getParent();
        BufferedReader breader = new BufferedReader(new FileReader(path + "\\src\\password.txt"));
        Scanner fileSC = new Scanner(new File(path + "\\src\\trainingdata.csv"), "UTF-8");
        String password = breader.readLine();
        breader.close();
        password = password.replaceAll(".txt", "");
        String inputPassword, tempSubstring, dataLine;
        String[] data = new String[6];
        int count = 3;
        while (count > 0) {
            System.out.printf("You have %d attempts remaining.%n", count);
            System.out.print("Please enter your admin password: ");
            inputPassword = sc.next(); 
            if (password.contentEquals(inputPassword)) {
                System.out.println("Access Granted.");
                try {
                    Thread.sleep(400);
                }
                catch (Exception E) {
                    System.out.println("no");
                }
                while (fileSC.hasNextLine()) {
                    dataLine = fileSC.nextLine();
                    if (dataLine.contains(",") && !dataLine.contains("{")) {
                        for (int x = 0; x < data.length; x++) {
                            //sets temp to relevant line
                            tempSubstring = dataLine;
                            //changes the first = to a / so  I can get the index of a unique symbol
                            dataLine = dataLine.replaceFirst(",", "=");
                            //creates temp substring from the beginning to index of /
                            tempSubstring = dataLine.substring(0, dataLine.indexOf("=") + 1);
                            //gets rid of that first part
                            dataLine = dataLine.replace(tempSubstring, "");
                            //sets a part of the array to whatever is before the equal
                            data[x] = tempSubstring.replace("=", "");
                            if (x == 4) {
                                data[5] = dataLine;
                                x = 7;
                            }
                        }
                        System.out.printf("%s, %s, %s, %s, %s, %s%n", data[0], data[1], data[2], data[3], data[4], data[5]);
                    }
                    else {
                        System.out.println("a");
                    }
                }
                count = 0;
            }
            count--;
        }
        fileSC.close();
    }
}
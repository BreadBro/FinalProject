import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.io.FileWriter;
import java.util.*;
import java.io.*;
import weka.classifiers.*;
import weka.core.*;
import weka.classifiers.trees.J48;
public class Politics {

    //variables
    public Path path = Paths.get("PoliticalAlignment.java").toAbsolutePath().getParent();
    public String name, age, party, beliefs, ethnicity, gender, education, salary;
    //short for opinion
    public String[] op = new String[4];
    public static final Scanner sc = new Scanner(System.in);
    
    
    
    //constructors
    public Politics() {
        name = "unknown";
    }

    public Politics(String[] aboutYou) {
        ethnicity = aboutYou[0];
        age = aboutYou[1];
        gender = aboutYou[2];
        education = aboutYou[3];
        salary = aboutYou[4];
    }

    public Politics(String opinion1, String opinion2, String opinion3, String opinion4) {
        op[0] = opinion1;
        op[1] = opinion2;
        op[2] = opinion3;
        op[3] = opinion4;
    }
    
    //assessors
    public String getOp1() {
        return op[0];
    }
    public String getOp2() {
        return op[1];
    }
    public String getOp3() {
        return op[2];
    }
    public String getOp4() {
        return op[3];
    }

    //mutators
    public void setOp1(String op1) {
        op[0] = op1;
    }
    public void setOp2(String op2) {
        op[1] = op2;
    }
    public void setOp3(String op3) {
        op[2] = op3;
    }
    public void setOp4(String op4) {
        op[3] = op4;
    }

    public void FindParty() throws Exception, FileNotFoundException, IOException, UnassignedClassException {
        //printstream but better, false means it overwrites, true means it appends
        FileWriter csvWriter = new FileWriter(path + "\\src\\testingdata.csv", false);
        FileWriter csvWriter2 = new FileWriter(path + "\\src\\trainingdata.csv", true);
        //adds attributes and title to the test data, this has to overwrite because otherwise I would not be able to remove the data line easily
        csvWriter.write("@RELATION testingdata\n\n@ATTRIBUTE ethnicity {white,black,asian,hispanic,native_american}\n@ATTRIBUTE age NUMERIC\n@ATTRIBUTE gender {male,female,other}\n");
        csvWriter.write("@ATTRIBUTE education {college,nocollege}\n@ATTRIBUTE salary {under,between,over}\n@ATTRIBUTE party {dem,rep}\n\n");
        String data = ethnicity + "," + age + "," + gender + "," + education + "," + salary;
        //adds data to file, says that the person is republican which helps me identify if it is true or false
        csvWriter.write("@DATA\n"+ data + ",rep");
        csvWriter.close();
        //how the library reads the files
        BufferedReader testReader = new BufferedReader(new FileReader(path + "\\src\\testingdata.csv"));
        BufferedReader trainReader = new BufferedReader(new FileReader(path + "\\src\\trainingdata.csv"));
        Instances train = new Instances(trainReader);
        Instances test = new Instances(testReader);
        //creates a filter that hasn't had data applied to it
        Classifier cls = new J48();
        //tells it that it has six attributes, 0-5
        train.setClassIndex(5);
        //trains filter 
        cls.buildClassifier(train);
        Evaluation eval = new Evaluation(train);
        test.setClassIndex(5);
        //compares the data
        eval.evaluateModel(cls, test);
        //1 means that you are republican because that means the model got data that was the same as the data that was put into test
        if (eval.correct() == 1) {
            System.out.print("\nI guess that you are a republican. Am I correct? ");
            if (sc.next().toLowerCase().contains("y")) {
                //privacy laws!
                System.out.print("Good! Would you like your data to be added to my database? ");
                if (sc.next().toLowerCase().contains("y")) {
                    //adds the data
                    csvWriter2.write("\n" + data + ",rep");
                    csvWriter2.close();
                }
            }
            else {
                System.out.print("Would you still like your data to be added to my database? ");
                if (sc.next().toLowerCase().contains("y")) {
                    //adds the data
                    csvWriter2.write("\n" + data + ",dem");
                    csvWriter2.close();
                }
            }
        }
        //nothing here that I didn't already explain
        if (eval.correct() == 0) {
            System.out.print("\nI guess that you are a democrat. Am I correct? ");
            if (sc.next().toLowerCase().contains("y")) {
                System.out.print("Good! Would you like your data to be added to my database? ");
                if (sc.next().toLowerCase().contains("y")) {
                    //adds the data
                    csvWriter2.write("\n" + data + ",dem");
                    csvWriter2.close();
                }
                else {
                    System.out.println("Well that's too bad.");
                }
            }
            else {
                System.out.println("Well that's too bad.");
                System.out.print("Would you still like your data to be added to my database? ");
                if (sc.next().toLowerCase().contains("y")) {
                    //adds the data
                    csvWriter2.write("\n" + data + ",rep");
                    csvWriter2.close();
                }
            }
        }
        //bug testing!
        System.out.println(eval.toSummaryString());
    }

    public static String FindPolitican(String name) throws FileNotFoundException {
        Path path = Paths.get("PoliticalAlignment.java").toAbsolutePath().getParent();
        Scanner sc = new Scanner(new File(path + "\\src\\politicians.txt"));
        String line;
        //use this to check if they are in the database or not
        String lineWithName = "__/";
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.contains(name)) {
                lineWithName = line;
            }
        }
        return lineWithName;    
    }

    public static String toString(String lineWithName) {
        String tempSubstring, full;
        String[] data = new String[7];
        for (int x = 0; x < data.length; x++) {
            //sets temp to relevant line
            tempSubstring = lineWithName;
            //changes the first = to a / so  I can get the index of a unique symbol
            lineWithName = lineWithName.replaceFirst("|", "=");
            //creates temp substring from the beginning to index of /
            tempSubstring = lineWithName.substring(0, lineWithName.indexOf("=") + 1);
            //gets rid of that first part
            lineWithName = lineWithName.replaceAll(tempSubstring, "");
            //sets a part of the array to whatever is before the equal
            data[x] = tempSubstring.replaceAll("=", "");
        }
        //these nouns use different articles, put president with a _ since there can also be former president
        if (data[2].contains("_president") || data[2].toLowerCase().contains("mayor") || data[2].toLowerCase().contains("governor")) {
            data[2] = data[2].replace("_", "");
            //                    0                  2   0      1           5              4         3                       6
            full = String.format("%s is the current %s. %s is a %s year old %s who weighs %s, is %s tall, and represents the %s party.", data[0], data[2], data[0], data[1], data[5], data[4], data[3], data[6]);
            return full;
        }
        else {
            full = String.format("%s is a US %s. %s is a %s year old %s who weighs %s, is %s tall, and represents the %s party.", data[0], data[2], data[0], data[1], data[5], data[4], data[3], data[6]);
            return full;
        }
    }
    
    public String Ideal() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path + "\\src\\politicians.txt"));
        String line;
        //use this to check if they are in the database or not
        String beliefs = "" + op[0] + op[1] + op[2] + op[3];
        String lineWithName = "__/";
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line.contains(beliefs)) {
                lineWithName = line;
            }
        }
        if (lineWithName.contains("__/")) {
            return lineWithName;
        }
        else {
            lineWithName = lineWithName.substring(0, lineWithName.indexOf("|"));
            return lineWithName;
        }
        
    }
}
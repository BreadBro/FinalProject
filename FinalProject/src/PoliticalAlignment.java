import java.util.*;

import weka.core.UnassignedClassException;

import java.nio.file.*;
import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.io.FileWriter;
import java.util.*;
import java.io.InputStreamReader;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import weka.classifiers.trees.J48;
public class PoliticalAlignment {
    public static void main(String [] args) throws Exception, FileNotFoundException, IOException, UnassignedClassException {
        int x = 1;
        int answ;
        System.out.println("This program will find out your political affiliations. Where would you like to begin? You can find information on an American politican, ");
            System.out.println("find what party you fit into, find what politicians fit your beliefs, etc.");
        while (x == 1) {
            System.out.println("This program will find out your most likely political affiliations in America. Where would you like to begin? You can find information on a politican, ");
            System.out.println("find what party you fit into, find what politicians fit your beliefs, etc.");
            System.out.println("1. Find Info on Politican");
            System.out.println("2. Find Party");
            System.out.println("3. ");
            answ = sc.nextInt();
            //if (answ == 1) {
                System.out.println("What politican would you like to find");
                //FindPolitican();
            //}
            if (answ == 2) {
                FindParty();
            }
        }

    }
    
    public static Scanner sc = new Scanner(System.in);

    public static void FindPolitican(String name) {

    }

    public static void FindParty() throws Exception, FileNotFoundException, IOException, UnassignedClassException {
        String[] aboutYou = new String[5];
        String answ;
        System.out.print("What is your ethnicity (white, black, asian, hispanic, native american? ");
        aboutYou[0] = sc.next().toLowerCase();
        if (aboutYou[0].contains("__/")) {
            aboutYou[0] = "white";
            aboutYou[1] = "70";
            aboutYou[2] = "male";
            aboutYou[3] = "hs";
            aboutYou[4] = "100000";
            Politics person = new Politics(aboutYou);
            person.FindParty();
        }
        else {
            if (aboutYou[0].toLowerCase().contains("native american")) {
                aboutYou[0] = "native_american";
            }
            System.out.print("What is your age? ");
            aboutYou[1] = String.valueOf(sc.nextInt());
            System.out.print("What is your gender (male, female, other)? ");
            aboutYou[2] = sc.next().toLowerCase();
            System.out.print("Do you have a college degree? ");
            if (sc.next().toLowerCase().contains("y")) {
                aboutYou[3] = "college";
            }
            else {
                aboutYou[3] = "no_college";
            }
            System.out.print("What is your salary (under 50k, between 50k-99k, over 100k)? ");
            aboutYou[4] = sc.next();
            //the chances of someone of other genders being republican is so low that it isn't even worth going through all the other stuff
           // if (aboutYou[2].toLowerCase().contains("other")) {
                //System.out.println("I guess that you are a democrat. Is this Correct?");
               // if (sc.next().contains("y") || sc.next().contains("y")) {

               // }
            //}
            //else {
                Politics person = new Politics(aboutYou);
                person.FindParty();
            //}
        }

        
        
    }
}

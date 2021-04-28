import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.io.FileWriter;
import java.util.*;
import java.io.InputStreamReader;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.*;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.*;
import weka.filters.supervised.instance.Resample;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.clusterers.FilteredClusterer;
import weka.gui.explorer.*;
import weka.classifiers.trees.J48;
public class Politics {

    //variables
    public Path path = Paths.get("PoliticalAlignment.java");
    public Path path1 = path.toAbsolutePath();
    public Path path2 = path1.getParent();
    public File file = new File(path2 + "\\src\\politicians.txt");
    public String name, party, support1, support2, support3, against1, against2, against3, ethnicity, gender, education;
    public int age, salary;
    
    
    
    //constructor
    public Politics(String aName, String aParty, String aSupport1, String aSupport2, String aSupport3, String aAgainst1, String aAgainst2, String aAgainst3) {
        name = aName;
        party = aParty;
        support1 = aSupport1;
        support2 = aSupport2;
        support3 = aSupport3;
        against1 = aAgainst1;
        against2 = aAgainst2;
        against3 = aAgainst3;
    }

    public Politics(String[] aboutYou) {
        ethnicity = aboutYou[0];
        age = Integer.parseInt(aboutYou[1]);
        gender = aboutYou[2];
        education = aboutYou[3];
        salary = Integer.parseInt(aboutYou[4]);
        
    }


    public String FindParty() throws Exception, FileNotFoundException, IOException, UnassignedClassException {
        FileWriter csvWriter = new FileWriter(path2 + "\\src\\testingdata.csv", false);
        csvWriter.write("@RELATION testingdata\n\n@ATTRIBUTE ethnicity {white,black,asian,hispanic,native_american}\n@ATTRIBUTE age NUMERIC\n@ATTRIBUTE gender {male,female,other}\n");
        csvWriter.write("@ATTRIBUTE education {ms, hs, college, bachelor}\n@ATTRIBUTE salary NUMERIC\n@ATTRIBUTE party {dem,rep}\n\n");
        csvWriter.write("@DATA\n"+ ethnicity + "," + age + "," + gender + "," + education + "," + salary + ",dem");
        csvWriter.close();
        BufferedReader testReader = new BufferedReader(new FileReader(path2 + "\\src\\testingdata.csv"));
        BufferedReader trainReader = new BufferedReader(new FileReader(path2 + "\\src\\politicaldata.csv"));
        Instances train = new Instances(trainReader);
        Instances test = new Instances(testReader);
        Classifier cls = new J48();
        train.setClassIndex(5);
        cls.buildClassifier(train);
        //NaiveBayes nb = new NaiveBayes();
        //nb.buildClassifier(train);
        Evaluation eval = new Evaluation(train);
        //eval.crossValidateModel(cls, train, 6, new Random(1));
        test.setClassIndex(5);
        eval.evaluateModel(cls, test);
        System.out.println(eval.toSummaryString("\nResults\n======\n", false));   
        return eval.toSummaryString();
    }

    public String FindPolitican(String name) {
        return name;
    }


}
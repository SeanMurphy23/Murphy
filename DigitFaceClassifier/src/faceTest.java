import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class faceTest {

    static boolean perceptron=false;
    static int percentage=100;

    public static void main(String[] args) throws IOException {

    	Scanner sc= new Scanner(System.in);
    	System.out.println("Enter a percentage(1-100): ");
    	int perc=sc.nextInt();
    	percentage=perc;
    	
    	System.out.println("Enter 1 for Bayes||Enter 0 for perceptron");
    	int percep=sc.nextInt();
    	if (percep==0) {
    		perceptron=true;
    	}
    	
    	System.out.println();
    	System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
    	
    	
    	
    	
        face face1 = new face();
        faceTest(face1);
        System.out.println();
        System.out.println("------------------");
        System.out.println();
        numbers number = new numbers();
        numberTest(number);

    }

    public static void numberTest(numbers number) throws IOException {
        FileReader numberDataTrain = new FileReader("digitdata/trainingimages");
        Scanner numberDataTrainLabel = new Scanner(new File("digitdata/traininglabels"));

        FileReader numberDataTest = new FileReader("digitdata/testimages");
        Scanner numberDataTestLabels = new Scanner(new File("digitdata/testlabels"));


       // FileReader numberDataValidation = new FileReader("digitdata/validationimages");
       // Scanner numberDataValidationLabels = new Scanner(new File("digitdata/validationlabels"));



        //Smooth for Bayes
        int a = 0, b = 0, c = 0;

        while (10 > a) {
            b = 0;
            while (28 > b) {
                c = 0;
                while (28> c) {
                    number.weight[a][b][c] = 1;
                    c++;
                }
                b++;

            }
            a++;

        }


        //Take Out Data we are not using
        //percentage=100-percentage;
        double skip=0;
        skip=(int)((percentage*.01)*(5000));
        skip=skip*28;
        int i=0;
        while (i<skip){
            c=Integer.parseInt(String.valueOf(numberDataTrain.read()));
            if (c==-1){
                System.out.println("eRROR");
                return;
            }
            if (c==10){
                i++;
            }
        }
        skip=skip/28;
        i=0;
        while(i<skip){
            c=numberDataTrainLabel.nextInt();
            i++;
        }
        

        //If perceptron==true then we make our best guess using probability
        //then we test whatever our best guess is
        //and then if right, do nothing  if wrong,adjust simularities of wrongGuess and rightNumber
        //DoThisUntilDone and then run again on test
        int [][][] weight= new int [10][28][28];
        int breakTest=0;
        if (perceptron==true){
        a=0;
        b=0;
        c=0;
        while (a<10){
            number.frequency[a]=.1;
            while(b<28){
                while (c<28){
                    weight[a][b][c]=0;
                    c++;
                }
                b++;
                c=0;
            }
            a++;
            b=0;
            c=0;
        }
        a=0;
        b=0;
        c=0;
        int realNumber = -1;
            while (numberDataTrainLabel.hasNextInt() == true) {
                b=0;
                c=0;
                realNumber=numberDataTrainLabel.nextInt();
                breakTest = 0;
                while (true) {
                    b=0;
                    c=0;
                    while (b < 28) {
                        c = Integer.parseInt(String.valueOf(numberDataTrain.read()));
                        if (c==10){
                            Integer.parseInt(String.valueOf(numberDataTrain.read()));
                        }
                        if (c == -1) {
                            isNumberPerceptron(c,number,realNumber,weight);
                            breakTest = 1;
                            break;
                        }
                        if (c==32){
                            number.pixels[a%28][b]=0;
                        }
                        if (c == 35) {
                            number.pixels[a%28][b] = 1;
                        }
                        if (c == 43) {
                            number.pixels[a%28][b] = 1;
                        }
                        b++;
                    }
                    if (a % 28 == 0) {
                        if (a != 0) {
                            if (numberDataTrainLabel.hasNextInt()==true) {
                                isNumberPerceptron(1, number, realNumber,weight);
                                break;
                            }
                            if (numberDataTrainLabel.hasNextInt()==false) {
                                //isNumberPerceptron(-1, number, realNumber,weight);
                                break;
                            }
                        }
                    }
                    a++;
                    b = 0;
                }
                if (breakTest==1){
                    break;
                }

                a++;
            }



            //Test Perceptron

            System.out.println("NOW TESTING!" +
                    "  " +
                    "  " +
                    "  " +
                    "  " +
                    "NOW TESTING!");
            System.out.println();
          
            
            number.percent=0;
            number.count=0;
            a=0;
            b=0;
            c=0;
            realNumber = -1;
            while (numberDataTestLabels.hasNextInt() == true) {
                b=0;
                c=0;
                realNumber = numberDataTestLabels.nextInt();
                breakTest = 0;
                while (true) {
                    b=0;
                    c=0;
                    while (b < 28) {
                        c = Integer.parseInt(String.valueOf(numberDataTest.read()));
                        if (c==10){
                            Integer.parseInt(String.valueOf(numberDataTest.read()));
                        }
                        if (c == -1) {
                            isNumberPerceptron(c,number,realNumber,weight);
                            breakTest = 1;
                            break;
                        }
                        if (c==32){
                            number.pixels[a%28][b]=0;
                        }
                        if (c == 35) {
                            number.pixels[a%28][b] = 1;
                        }
                        if (c == 43) {
                            number.pixels[a%28][b] = 1;
                        }
                        b++;
                    }
                    if (a % 28 == 0) {
                        if (a != 0) {
                            if (numberDataTestLabels.hasNextInt()==true) {
                                isNumberPerceptron(2, number, realNumber,weight);
                                break;
                            }
                            if (numberDataTestLabels.hasNextInt()==false) {
                                //isNumberPerceptron(-1, number, realNumber,weight);
                                break;
                            }
                        }
                    }
                    a++;
                    b = 0;
                }
                if (breakTest==1){
                    break;
                }

                a++;
            }



            return;
        }///end perceptron



        System.out.println("Digits Bayes!");
        //c = numberDataTrainLabel.nextInt();
        int numberTrain = 0;
        breakTest = 0;
        int totalNumbers = 0;
        while (numberDataTrainLabel.hasNextInt() == true) {
            c = numberDataTrainLabel.nextInt();
            totalNumbers++;
            number.frequency[c]++;
            a = 0;
            b = 0;
            breakTest=0;
            while (a < 28) {
                while (b < 28) {
                    numberTrain = Integer.parseInt(String.valueOf(numberDataTrain.read()));
                    if (numberTrain==10){
                        numberTrain=Integer.parseInt(String.valueOf(numberDataTrain.read()));
                        //System.out.println("WHOAH THRERE  "+a+":a|b:"+b);
                    }
                    if (numberTrain == 43) {
                        number.weight[c][a][b]=number.weight[c][a][b]+1;
                    }
                    if (numberTrain == 35){
                        number.weight[c][a][b]=number.weight[c][a][b]+1;
                    }
                    if (numberTrain == -1) {
                        number.numbers[c]++;
                        breakTest = 1;
                        break;
                    }
                    b++;
                }
                if (breakTest == 1) {
                    break;
                }
                a++;
                b = 0;
            }
        }
        a = 0;
        b = 0;
        int d = 0;
        while (a < 10) {
            while (b < 28) {
                while (d < 28) {
                    number.weight[a][b][d] = number.weight[a][b][d] / (number.frequency[a] + 2);
                    //number.similarity[a][b][d]=number.similarity[a][b][d]/(10);
                    d++;
                }
                b++;
                d = 0;
            }
            number.frequency[a] = (number.frequency[a]) / (totalNumbers);
            a++;
            b = 0;
            d = 0;
        }


        //TEST Bayes
        System.out.println("dataPoints Used: "+totalNumbers);

        a = 0;
        b = 0;
        c = 0;
        int realNumber = -1;
        while (numberDataTestLabels.hasNextInt() == true) {
            b=0;
            c=0;
        realNumber = numberDataTestLabels.nextInt();
            breakTest = 0;
            while (true) {
                b=0;
                c=0;
                while (b < 28) {
                    c = Integer.parseInt(String.valueOf(numberDataTest.read()));
                    if (c==10){
                        Integer.parseInt(String.valueOf(numberDataTest.read()));
                    }
                    if (c == -1) {
                        isNumber(c,number,realNumber);
                        breakTest = 1;
                        break;
                    }
                    if (c==32){
                        number.pixels[a%28][b]=0;
                    }
                    if (c == 35) {
                        number.pixels[a%28][b] = 1;
                    }
                    if (c == 43) {
                        number.pixels[a%28][b] = 1;
                    }
                    b++;
                }
                if (a % 28 == 0) {
                    if (a != 0) {
                        if (numberDataTestLabels.hasNextInt()==true) {
                            isNumber(1, number, realNumber);
                            break;
                        }
                        if (numberDataTestLabels.hasNextInt()==false) {
                        //    isNumber(-1, number, realNumber);
                            break;
                        }
                    }
                }
                a++;
                b = 0;
            }
            if (breakTest==1){
                break;
            }

        a++;
        }
    }
//

    
    
    
    
    
    
    
    
    
    
    
    

    public static void isNumberPerceptron(int x,numbers number1,int realNumber,int [][][]weight){
        int a=0;
        int b=0;
        int c=0;
        int temp=0;
        double [] prob =new double[10];
        while (a<10){
            prob[a]=0;
            a++;
        }
        a=0;
        temp=0;
        while (a<10){
            while (b<28){
                while (c<28){
                    if (number1.pixels[b][c]!=0){
                        prob[a]=prob[a]+weight[a][b][c];
                    }
                   
                    c++;
                }
                b++;
                c=0;
            }

            if (prob[a]>prob[temp]){
                temp=a;
            }
            a++;
            b=0;
            c=0;
        }
        if (temp==realNumber){
            number1.percent++;
        }


        //Make adjustments
        if (temp!=realNumber && x!=2){
            a=0;
            b=0;
            c=0;
                while (b<28){
                    while (c<28){
                        if (number1.pixels[b][c]!=0){
                            weight[temp][b][c]--;
                            weight[realNumber][b][c]++;
                        }
                        c++;
                    }

                    b++;
                    c=0;
                }

        }
        number1.count++;

        if (x==-1){
            System.out.println("digits-finished the Perceptron Stuff!");
            System.out.println("Percent: "+(100*(number1.percent/number1.count)));
            System.out.println("Correct Guesses: "+number1.percent+" /"+number1.count);
        }
        

        return;
    }






    public static void isNumber(int x,numbers number1,int realNumber) throws FileNotFoundException {
        int a = 0;
        int b = 0;
        int c = 0;
        int temp = 0;
        double[] prob = new double[10];
        while (a < 10) {
            prob[a] = 0;
            a++;
        }
        a = 0;
        b=2;
        c=5;
        temp = 0;
        while (a < 10) {
            prob[a] = prob[a] + Math.log(number1.frequency[a]);
            while (b < 26) {
                while (c < 23) {
                    if (number1.pixels[b][c] != 0) {
                        prob[a] = prob[a] + Math.log(number1.weight[a][b][c]);
                    } 
                    else 
                    {
                            prob[a] = prob[a] + Math.log(1 - number1.weight[a][b][c]);
                        }

                        c++;
                    }
                    b++;
                    c = 5;
                }

                if (prob[a] > prob[temp]) {
                    temp = a;
                }
                a++;
                b = 2;
                c = 5;
            }
            if (temp == realNumber) {
                number1.percent++;
            }
            //if (temp!=realNumber){
            //    number1.frequency[temp]=
            //}
            number1.count++;

            if (x == -1) {
                System.out.println("DONE Digits Bayes!");
                System.out.println("Percent: " + (100 * (number1.percent / number1.count)));
                System.out.println("Correct Guesses: " + number1.percent + " /" + number1.count);
            }


            return;
        }




    public static void faceTest(face face1) throws IOException {

        FileReader faceDataTrain = new FileReader("facedata/facedatatrain");
        FileReader faceDataTrainLabel = new FileReader("facedata/facedatatrainlabels");

        FileReader faceDataTest=new FileReader("facedata/facedatatest");
        FileReader faceDataTestLabels = new FileReader("facedata/facedatatestlabels");

        int a = 0;
        int b = 0;
        int c = 0;

        int inputDataTrain = 0;
        int testBreak = 0;
        int trainLabels = 0;
        
      //Take Out Data we are not using
        percentage=100-percentage;
        double skip=0;
        skip=(int)((percentage*.01)*(451));
        skip=skip*70;
        int i=0;
        while (i<skip){
            c=Integer.parseInt(String.valueOf(faceDataTrain.read()));
            if (c==-1){
                System.out.println("eRROR");
                return;
            }
            if (c==10){
                i++;
            }
        }
        skip=skip/70;
        i=0;
        while(i<(2*skip)){
            c=Integer.parseInt(String.valueOf(faceDataTrainLabel.read()));
            i++;
        }
        
        
        //If perceptron==true then we make our best guess using probability
        //then we test whatever our best guess is
        //and then if right, do nothing  if wrong,adjust simularities of wrongGuess and rightNumber
        //DoThisUntilDone and then run again on test
        int [][][] weight= new int [2][70][60];
        int breakTest=0;
        if (perceptron==true){
        a=0;
        b=0;
        c=0;
        while (a<2){
            face1.frequency[a]=.5;
            while(b<70){
                while (c<60){
                    weight[a][b][c]=0;
                    c++;
                }
                b++;
                c=0;
            }
            a++;
            b=0;
            c=0;
        }
        a=0;
        b=0;
        c=0;
        int realNumber = -1;
            while (faceDataTrainLabel.ready() == true) {
                b=0;
                c=0;
                
                
                realNumber=Integer.parseInt(String.valueOf(faceDataTrainLabel.read()));
                if (realNumber==10) {
                realNumber=Integer.parseInt(String.valueOf(faceDataTrainLabel.read()));
                }
                realNumber=realNumber-48;
                
                
                breakTest = 0;
                while (true) {
                    b=0;
                    c=0;
                    while (b < 60) {
                        c = Integer.parseInt(String.valueOf(faceDataTrain.read()));
                        if (c==10){
                            Integer.parseInt(String.valueOf(faceDataTrain.read()));
                        }
                        if (c == -1) {
                            isFacePerceptron(c,face1,realNumber,weight);
                            breakTest = 1;
                            break;
                        }
                        if (c==32){
                            face1.pixels[a%70][b]=0;
                        }
                        if (c == 35) {
                            face1.pixels[a%70][b] = 1;
                        }
                        if (c == 43) {
                            face1.pixels[a%70][b] = 1;
                        }
                        b++;
                    }
                    if (a % 70 == 0) {
                        if (a != 0) {
                            if (faceDataTrainLabel.ready()==true) {
                                isFacePerceptron(1, face1, realNumber,weight);
                                break;
                            }
                            if (faceDataTrainLabel.ready()==false) {
                                //isNumberPerceptron(-1, number, realNumber,weight);
                                break;
                            }
                        }
                    }
                    a++;
                    b = 0;
                }
                if (breakTest==1){
                    break;
                }

                a++;
            }



            //Test Perceptron

            System.out.println("NOW TESTING!" +
                    "  " +
                    "  " +
                    "  " +
                    "  " +
                    "NOW TESTING!");
            System.out.println();
          
            
            face1.percent=0;
            face1.count=0;
            a=0;
            b=0;
            c=0;
            realNumber = -1;
            while (faceDataTestLabels.ready() == true) {
                b=0;
                c=0;
                
                realNumber=Integer.parseInt(String.valueOf(faceDataTestLabels.read()));
                if (realNumber==10) {
                realNumber=Integer.parseInt(String.valueOf(faceDataTestLabels.read()));
                }
                
                realNumber=realNumber-48;
                
                
                breakTest = 0;
                while (true) {
                    b=0;
                    c=0;
                    while (b < 60) {
                        c = Integer.parseInt(String.valueOf(faceDataTest.read()));
                        if (c==10){
                            Integer.parseInt(String.valueOf(faceDataTest.read()));
                        }
                        if (c == -1) {
                            isFacePerceptron(c,face1,realNumber,weight);
                            breakTest = 1;
                            break;
                        }
                        if (c==32){
                            face1.pixels[a%70][b]=0;
                        }
                        if (c == 35) {
                            face1.pixels[a%70][b] = 1;
                        }
                        if (c == 43) {
                            face1.pixels[a%28][b] = 1;
                        }
                        b++;
                    }
                    if (a % 70 == 0) {
                        if (a != 0) {
                            if (faceDataTestLabels.ready()==true) {
                                isFacePerceptron(2, face1, realNumber,weight);
                                break;
                            }
                            if (faceDataTestLabels.ready()==false) {
                                //isNumberPerceptron(-1, number, realNumber,weight);
                                break;
                            }
                        }
                    }
                    a++;
                    b = 0;
                }
                if (breakTest==1){
                    break;
                }

                a++;
            }



            return;
        }///end perceptron
        
        
        
        
        
        
        
        while (face1.weight.length < 2) {
            while (face1.weight[a].length < 70) {
                while (face1.weight[a][b].length < 60) {
                    face1.weight[a][b][c] = 1;
                    c++;
                }
                b++;
                c = 0;
            }
            a++;
            b = 0;
        }

        a = 0;
        while (true) {
            if (a == 0) {
                trainLabels = Integer.parseInt(String.valueOf(faceDataTrainLabel.read()));
                if (trainLabels==10) {
                	trainLabels = Integer.parseInt(String.valueOf(faceDataTrainLabel.read()));
                }
                
                trainLabels = trainLabels - 48;
            }
            b = 0;
            while (b <= 60) {
                inputDataTrain = Integer.parseInt(String.valueOf(faceDataTrain.read()));
                if (inputDataTrain == -1) {
                    face1.numberOfFaces[trainLabels]++;
                    testBreak = 1;
                    break;
                }
                if (inputDataTrain == 35) {
                    face1.weight[trainLabels][a%70][b]++;
                }
                b++;
            }
            if (testBreak == 1) {
                break;
            }

            if (a % 70 == 0) {
                if (a != 0) {
                    trainLabels = Integer.parseInt(String.valueOf(faceDataTrainLabel.read()));
                    if ((trainLabels - 48) != 1 || trainLabels - 48 !=0) {
                        trainLabels = Integer.parseInt(String.valueOf(faceDataTrainLabel.read()));
                    }
                    if (trainLabels == -1) {
                        break;
                    }
                    trainLabels = trainLabels - 48;
                    face1.numberOfFaces[trainLabels] = face1.numberOfFaces[trainLabels] +1;
                }
            }
            a++;
        }

        a = 0;
        while (a < face1.weight.length) {
            face1.frequency[a] = (double) face1.numberOfFaces[a] / (face1.totalNumberOfTrials);
            b = 0;
            while (b < face1.weight[a].length) {
                c = 0;
                while (c < face1.weight[a][b].length) {
                    face1.weight[a][b][c] = face1.weight[a][b][c] / (face1.numberOfFaces[a] + 2);
                    c++;
                }
                b++;
            }
            a++;
        }

        trainLabels=0;
        breakTest = 0;
        a=0;
        while (true){
            if (breakTest==1){
                break;
            }
            if (a%70==0) {
                if (a != 0) {
                    face1.faceYes = Integer.parseInt(String.valueOf(faceDataTestLabels.read()));
                    if (face1.faceYes == -1) {
                        breakTest = 1;
                    }
                    if (face1.faceYes == 10) {
                        face1.faceYes = Integer.parseInt(String.valueOf(faceDataTestLabels.read()));
                        face1.faceYes = face1.faceYes - 48;
                    }
                }
            }

            b = 0;
            while (b <= 60) {
                trainLabels = Integer.parseInt(String.valueOf(faceDataTest.read()));
                if (trainLabels == -1) {
                    isFace(trainLabels, face1);
                    breakTest = 1;
                    break;
                }
                if (trainLabels == 35) {
                    face1.pixels[a % 70][b] = 1;
                }
                if (trainLabels == 32) {
                    face1.pixels[a % 70][b] = 0;
                }
                b++;
            }
            if (a%70==0){
                if (a!=0){
                    isFace(1, face1);
                }
            }
            a++;
        }
    }
    
    
    public static void isFacePerceptron(int x,face number1,int realNumber,int [][][]weight){
        int a=0;
        int b=0;
        int c=0;
        int temp=0;
        double [] prob =new double[10];
        while (a<2){
            prob[a]=0;
            a++;
        }
        a=0;
        temp=0;
        while (a<2){
            while (b<70){
                while (c<60){
                    if (number1.pixels[b][c]!=0){
                        prob[a]=prob[a]+weight[a][b][c];
                    }
                  
                    c++;
                }
                b++;
                c=0;
            }

            if (prob[a]>prob[temp]){
                temp=a;
            }
            a++;
            b=0;
            c=0;
        }
        if (temp==realNumber){
            number1.percent++;
        }


        //Make adjustments
        if (temp!=realNumber && x!=2){
            a=0;
            b=0;
            c=0;
                while (b<70){
                    while (c<60){
                        if (number1.pixels[b][c]!=0){
                            weight[temp][b][c]--;
                            weight[realNumber][b][c]++;
                        }
                        c++;
                    }

                    b++;
                    c=0;
                }

        }
        number1.count++;

        if (x==-1){
            System.out.println("faces-finished the Perceptron Stuff!");
            System.out.println("Percent: "+(100*(number1.percent/number1.count)));
            System.out.println("Correct Guesses: "+number1.percent+" /"+number1.count);
        }


        return;
    }
    
    
    
    

    public static void isFace(int faceBool, face face1){
        double [] prob= new double[2];
        int temp=0;
        int a=0;
        int b=0;
        int c=0;

        while (a<2){
            prob[a]=prob[a]+Math.log(face1.frequency[a]);
            while (b<70) {
                while (c<60){
                    if (face1.pixels[b][c]!=0){
                        prob[a]=prob[a]+Math.log(face1.weight[a][b][c]);
                    }
                    else {
                        prob[a]=prob[a]+Math.log(1-face1.weight[a][b][c]);
                    }
                    c++;
                   
                }
                b++;
                c=0;
            }
            if (prob[a]>prob[temp]){
                temp=a;
            }
            a++;
            b=0;
            c=0;
        }
        if (temp==face1.faceYes){
            face1.percent = face1.percent+1;
        }

        face1.count++;

        if (faceBool==-1){

            System.out.println("DONE Faces Bayes");
            System.out.println("dataPoints Used: "+(face1.numberOfFaces[0]+face1.numberOfFaces[1]));
            System.out.println("Correct Guesses: " +face1.percent+" / "+face1.count);
            System.out.println("Percent: "+ (double)(face1.percent/face1.count)*100);
            System.out.println();
            System.out.println();
        }

        return;
    }

}



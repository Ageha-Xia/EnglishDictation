import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class EnglishDictation{
    static boolean isalpha(char c){
        if('a' <= c && c <= 'z' || 'A' <= c && c <= 'Z')
            return true;
        return false;
    }

    static int[] shuffle(int n){
        Random R = new Random();
        int a[] = new int[n];
        for(int i = 0; i < n; ++i){
            a[i] = i;
        }
        for(int i = n - 1; i >= 0; --i){
            int randomPos = R.nextInt(i + 1);
            int tmp = a[i];
            a[i] = a[randomPos];
            a[randomPos] = tmp;
        }
        return a;
    }

    private static String getFormatLogString(String content, int colour, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", colour, content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", colour, type, content);
        }
    }

    public static void main(String args[]){
        try {
            Scanner scanner = new Scanner(System.in);
            // FileInputStream fis = new FileInputStream("file1.txt");
            // InputStreamReader dis = new InputStreamReader(fis);
            // BufferedReader reader = new BufferedReader(dis);
            File fileDirectory = new File("../words");

            // System.out.println(fileDirectory.isDirectory());
            String fileList[] = fileDirectory.list();
            String orderedFileList[] = fileList;
            Arrays.sort(orderedFileList, 0, orderedFileList.length);
            System.out.println(getFormatLogString("Here are the word lists. Enter the file index to select which to exercise.", 33, 0));
            System.out.println("------------------------------------------------------------------------------");
            for(int index = 0; index < fileList.length; index++){
                System.out.printf("%s\n", getFormatLogString(index+":"+orderedFileList[index], 33, 0) );
            }
            System.out.println("------------------------------------------------------------------------------");
            // System.out.println("");

            int inputFileIndex = 0;
            boolean findRightFile = false;
            for(int counter = 0; findRightFile == false; counter++){
                if(counter > 0){
                    System.out.println(getFormatLogString("You've entered an index that the program can't identify", 31, 0));
                }
                inputFileIndex = scanner.nextInt();
                if(0 <= inputFileIndex && inputFileIndex < fileList.length){
                    findRightFile = true; 
                }
            }


            for(int i = 0; i < fileList.length; i++){
                if(fileList[i].equals(orderedFileList[inputFileIndex])){
                    inputFileIndex = i;
                    break;
                }
            }
            FileInputStream fis = new FileInputStream("../words/"+fileList[inputFileIndex]);
            InputStreamReader dis = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(dis);
            
            String s;
            int wordNumber = 0;
            String words[] = new String[1000], meanings[] = new String[1000];

            for(int i = 0; (s = reader.readLine()) != null; i++ ){

                int tokenizer = 0;
                if(s.equals("End"))break;
                wordNumber++;
                for(int index = 0; index < s.length(); index++){
                    if(s.charAt(index) == ' ' || s.charAt(index) == '	'){
                        tokenizer = index;
                        break;
                    }
                }
                words[i] = (s.substring(0, tokenizer)).trim();
                meanings[i] = (s.substring(tokenizer + 1, s.length())).trim();
                // System.out.printf("%s %s\n", words[i], meanings[i]);

            }
            dis.close();
            // int a[] = shuffle(5);
            // for(int i:a){
            //     System.out.printf("%d ", i);
            // }
            System.out.println(getFormatLogString("please enter the number of the words that you want to exercise.", 33, 0));
            String StringFormNumberOfExercise = scanner.next();
	    int numberOfExercise;
	    if(StringFormNumberOfExercise.equals("ALL"))
            numberOfExercise = wordNumber;
	    else 
	    	numberOfExercise = Integer.valueOf(StringFormNumberOfExercise);
            int order[] = shuffle(wordNumber);

            long startTime = System.currentTimeMillis();
            ArrayList<String> wrongWordsList = new ArrayList<String>();
            ArrayList<String> correspondMeanings = new ArrayList<String>();
            for(int i = 0; i < numberOfExercise; ++i){
                System.out.println(meanings[order[i]]);
                String ans = scanner.next();
                if(!ans.equals(words[order[i]])){
                    System.out.println(getFormatLogString("Wrong:"+words[order[i]], 31, 0 ));
                    wrongWordsList.add(words[order[i]]);
                    correspondMeanings.add(meanings[order[i]]);
                }
            }

            System.out.println("------------------------------------------------------------------------------");
            if(wrongWordsList.size() > 0){
                System.out.println(getFormatLogString("The following are the wrong words during the exercise before.", 31, 0));
                for(int i = 0; i < wrongWordsList.size(); ++i){
                    System.out.printf("%s:%s\n", wrongWordsList.get(i), correspondMeanings.get(i));
                }
                
            }
            else System.out.println(getFormatLogString("You've compelte the exercise without mistake!", 32, 0));
            long endTime = System.currentTimeMillis();
            float timeOfExercise = (endTime - startTime) / 1000;
            System.out.printf(getFormatLogString("The total time of the exercise is " +  timeOfExercise + " seconds\n", 33, 0));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

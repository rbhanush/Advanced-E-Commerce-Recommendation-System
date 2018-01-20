/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NaiveBayes;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * 
 */
public class StopwordRemoval {

    public static ArrayList<String> Stopword = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        LoadStopWord();
        System.out.println(Stopword);
    }

    public static void LoadStopWord() throws IOException {
        String SW = FileUtils.readFileToString(new File("F:\\Final Year Project\\workspace1\\AdvancedRecommendation\\stopwords"));
        String sTopWords[] = SW.split("\\r?\\n");
        for (String word : sTopWords) {
            Stopword.add(word.trim().toLowerCase());
        }
    }

    public static String StopwordRemoval(String Sentences) {
        //String SentenceAfterFilering = "";

            String MainWords[] = Sentences.split("\\s+");
            Sentences = "";
            for (String word : MainWords) {
                if (!Stopword.contains(word.trim().toLowerCase())) {
                    Sentences = Sentences+" "+word;
                }
            }
            //SentenceAfterFilering.add(Sentences);
        
        return Sentences;
    }
}

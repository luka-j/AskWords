/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package askwords;

import static askwords.AskWords.workFolder;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luka
 */
public class Process {
    
    public static final int TOTAL = 820;
    
    private static int i = 0;
    private static long start;
    
    public static void populateWords() throws IOException {
        start = System.currentTimeMillis();
        WordCounter wordBase = new WordCounter("all.words");
        Files.walk(workFolder.toPath(), FileVisitOption.FOLLOW_LINKS).forEach((Path p) -> {
            if (i % 10 == 0 && i!=0) {
                System.out.println(i + ", uk " + (System.currentTimeMillis()-start)/1000.0
                    + ", remaining: " 
                    + ((TOTAL-i)/10.0)*(System.currentTimeMillis()-start)/1000.0/(i/10));
            }
            if (p.toString().endsWith(".askfm")) {
                loadFile(p).forEach((String word) -> {
                    wordBase.putWord(word);
                });
                i++;
            }
        });
        wordBase.saveScores();
    }
    
    private static List<String> loadFile(Path p) {
        final List<String> words = new LinkedList<>();
        try {
            Files.readAllLines(p).forEach((String line) -> {
                if (line.startsWith("Q:")) {
                    String[] noDetails = line.split("@");
                    for (int i = 0; i < noDetails.length - 1; i++) {
                        String[] spaceSeparated = noDetails[i].split(" ");
                        for(String token : spaceSeparated) {
                            if(!token.startsWith("http") && !token.endsWith("/people"))
                                words.addAll(Arrays.asList(token.split("/|,|\\.|:")));
                        }
                    }
                }

            });
        } catch (IOException ex) {
            Logger.getLogger(AskWords.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(p.toAbsolutePath().toString());
            System.exit(1);
        }
        return words;
    }
}

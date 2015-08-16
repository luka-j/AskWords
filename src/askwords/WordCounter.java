package askwords;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luka
 */
public class WordCounter {

    final File scoreFile;
    final Map<String, Integer> scores;
    static final char splitChar = ' ';

    WordCounter(String filename) {
        scoreFile = new File(AskWords.workFolder, filename);
        scores = new HashMap<>();
    }

    public String getName() {
        return scoreFile.getName();
    }

    void loadScores() throws IOException {
        List<String> lines = Files.readAllLines(scoreFile.toPath());
        lines.forEach((String line) -> {
            String[] count = line.split(String.valueOf(splitChar));
            scores.put(count[0], Integer.parseInt(count[1]));
        });
    }

    void putWord(String word) {
        putWords(word, 1);
    }

    void putWords(String word, int amount) {
        if (!word.isEmpty()) {
            if (scores.containsKey(word)) {
                scores.replace(word, scores.get(word) + amount);
            } else {
                scores.put(word, amount);
            }
        }
    }

    static boolean isValid(String word) {
        return !(word.equals("Q") || word.equals("A") || word.equals("L")
                || word.endsWith("/people") || word.startsWith("<div")
                || word.endsWith("jpg") || word.endsWith("&theater")
                || word.endsWith("gif"));
    }

    void saveScores() throws IOException {
        scoreFile.createNewFile();
        BufferedWriter bw
                = Files.newBufferedWriter(scoreFile.toPath(), StandardCharsets.UTF_8);
        getSorted().forEach((Entry<String, Integer> e) -> {
            try {
                bw.append(e.getKey()).append(splitChar).append(e.getValue().toString());
                bw.newLine();
            } catch (IOException ex) {
                Logger.getLogger(WordCounter.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        try {
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(WordCounter.class.getName()).log(Level.SEVERE, "wtf", ex);
        }
    }

    List<Entry<String, Integer>> getSorted() {
        List<Entry<String, Integer>> sorted = new ArrayList<>(scores.entrySet());
        sorted.sort(new WordComparator());
        return sorted;
    }

    public static class WordComparator implements Comparator<Entry<String, Integer>> {

        @Override
        public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
            if (e1.getValue() > e2.getValue()) {
                return -1;
            } else if (e1.getValue() < e2.getValue()) {
                return 1;
            }
            return 0;
        }

    }
}

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    private Map<String, List<PageEntry>> entrys = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        for (File pdf : pdfsDir.listFiles()) {
            if (!pdf.isDirectory()) {
                var doc = new PdfDocument(new PdfReader(pdf));

                for (int i = 1; i <= doc.getNumberOfPages(); i++) {
                    var page = doc.getPage(i);
                    var text = PdfTextExtractor.getTextFromPage(page);
                    var words = text.split("\\P{IsAlphabetic}+");

                    Map<String, Integer> freqs = new HashMap<>();
                    for (var word : words) { // перебираем слова
                        if (word.isEmpty()) {
                            continue;
                        }
                        word = word.toLowerCase();
                        freqs.put(word, freqs.getOrDefault(word, 0) + 1);


                    }

                    for (String word : freqs.keySet()) {
                        if (!entrys.containsKey(word))
                            entrys.put(word, new ArrayList<>());
                        entrys.get(word).add(new PageEntry(pdf.getName(), i, freqs.get(word)));
                    }
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        if (entrys.containsKey(word)) {
            Collections.sort(entrys.get(word));
            return entrys.get(word);
        } else
            return Collections.emptyList();
    }
}

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kanna.pdfread.Master.extractSection;

public class NewExpDecSix {

        public static void main(String[] args) throws IOException {
            File file = new File("C:\\Users\\Vishal\\Downloads\\Prashanth Profile.pdf");
            // File file = new File("C:\\Users\\Vishal\\Downloads\\Profile (1).pdf");
            // File file = new File("C:\\Users\\Vishal\\Downloads\\Vaibhav LinkedIn Profile.pdf");

            PDDocument document = PDDocument.load(file);


            System.out.println("-----------------------");


            PDFTextStripper s = new PDFTextStripper();
            String m = s.getText(document);
            document.close();
          // String resumeText = extractSection(m, "Experience", "Education");
           // System.out.println(resumeText);
             System.out.println(m);

            String[] sentences = extractSentencesWithBulletPoints(m);

            for (String sentence : sentences) {
                System.out.println(sentence);
            }
        }

        private static String[] extractSentencesWithBulletPoints(String text) {
            String regex = "•\\s*(.*?)\n";
            Pattern pattern = Pattern.compile(regex);

            Matcher matcher = pattern.matcher(text);

            List<String> matchedSentences = new ArrayList<>();
            while (matcher.find()) {
                matchedSentences.add(matcher.group(1).trim());
            }

            return matchedSentences.toArray(new String[0]);
        }
    }


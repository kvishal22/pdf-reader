package com.kanna.pdfread;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//printed the experience
//printed the summary section
//printed the contact and top skills
//linked in url
public class Master {
    public static void main(String[] args) throws IOException {
           // PDDocument document = PDDocument.load(new File("C:\\Users\\Vishal\\Downloads\\Profile (1).pdf"));
        File file = new File("C:\\Users\\Vishal\\Downloads\\Prashanth Profile.pdf");
        PDDocument document = PDDocument.load(file);
        findingUrlInThePdf(file);

            PDFTextStripper s = new PDFTextStripper();
            String m = s.getText(document);
            System.out.println(m);

        System.out.println("-----------------------");
        String cont = extractSection(m, "Contact", "Summary");
        System.out.println("Contact and TopSkills \n"+cont);


        System.out.println("-----------------------");
            String summarySection = extractSection(m, "Summary", "Experience");
           System.out.println("Summary \n"+summarySection);

            String exp = extractSection(m, "Experience", "Education");
            System.out.println("-----------------------");
            System.out.println("experience \n"+exp);
            System.out.println("-----------------------");
    }
    private static String extractSection(String text, String sectionStart, String sectionEnd) {
        String regex = Pattern.quote(sectionStart) + "(.*?)" + Pattern.quote(sectionEnd);
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return "";
    }
    static void findingUrlInThePdf(File file) throws IOException {

        PDDocument document = PDDocument.load(file);
        document.getClass();

        Set<String> set = new HashSet<>();

        PDPage pdfPage = document.getPage(0);
        List<PDAnnotation> annotations = pdfPage.getAnnotations();
        System.out.println("Number of annotations: " + annotations.size());
        for (PDAnnotation annot : annotations) {
            if (annot instanceof PDAnnotationLink link) {
                PDAction action = link.getAction();
                if (action instanceof PDActionURI uri) {
                    String urls = uri.getURI();
                    set.add(urls);
                }
            }
        }
        // to get linked in url because we do not need email address
        String s1 = set.stream().reduce((a, b) -> a).get();
        System.out.println(s1);
    }
}


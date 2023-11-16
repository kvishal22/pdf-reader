package com.kanna.pdfread;

import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.pdfbox.io.RandomAccessStreamCache;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

class PdfTest {

    @Test
    void readPdf() throws IOException {
        //PdfReader reader = new PdfReader("C:\\Users\\Vishal\\Desktop\\Vishal Kanna oct 26.pdf");
       // PdfReader reader1 = new PdfReader("C:\\Users\\Vishal\\Downloads\\Vaibhav LinkedIn Profile.pdf");
        PdfReader reader1 = new PdfReader("C:\\Users\\Vishal\\Downloads\\Prashanth Profile.pdf");
        int pages = reader1.getNumberOfPages();
        //System.out.println(pages);

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= pages; i++) {
            sb.append(PdfTextExtractor.getTextFromPage(reader1, i));
        }

        reader1.close();
        String extractedText = sb.toString();
        System.out.println(extractedText);

        System.out.println("___________________________________________");


        String ed = extractInformation(extractedText, "Education", "Experience");
        System.out.println("education: \n" + ed);
        System.out.println("-------------------------");
        String ex = extractInformation(extractedText, "Experience", "Education");
        System.out.println("Experience: \n" + ex);
        System.out.println("-------------------------");

        String topSkills = extractInformation(extractedText, "Skills", "Experience");
        String contact = extractInformation(extractedText, "Contact", "summary");
        String summary = extractInformation(extractedText,"summary","experience");
        System.out.println("Top Skills: \n" + topSkills);
        System.out.println("-------------------------");
        System.out.println("Contact: \n" + contact);
        System.out.println("-------------------------");
        System.out.println("summary: \n"+summary);


    }

    private static String extractInformation(String text, String fieldStart, String fieldEnd) {
        int fieldIndex = text.toLowerCase().indexOf(fieldStart.toLowerCase());
        String fieldEndLower = fieldEnd.toLowerCase();

        if (fieldIndex != -1) {
            int start = fieldIndex + fieldStart.length();
            int end = text.toLowerCase().indexOf(fieldEndLower, start);

            if (end != -1) {
                return text.substring(start, end).trim();
            } else {
                return text.substring(start).trim();
            }
        }
        return null;
    }

}

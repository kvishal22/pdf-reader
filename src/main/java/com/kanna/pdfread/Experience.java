package com.kanna.pdfread;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kanna.pdfread.Master.extractSection;

public class Experience {
    public static void main(String[] args) throws IOException {

        File file = new File("C:\\Users\\Vishal\\Downloads\\Prashanth Profile.pdf");
        // File file = new File("C:\\Users\\Vishal\\Downloads\\Profile (1).pdf");
       //  File file = new File("C:\\Users\\Vishal\\Downloads\\Vaibhav LinkedIn Profile.pdf");

        PDDocument document = PDDocument.load(file);

        PDFTextStripper s = new PDFTextStripper();
        String pdfText = s.getText(document);
        document.close();

        System.out.println(pdfText);
        System.out.println("XXXXXXxxx");


        List<String> experiences = extractExperiences(pdfText, "Experience", "Education");

        for (String experience : experiences) {
            String companyName = extractCompanyName(experience);
            System.out.println("Company Name: " + companyName);

            String experienceDetails = extractExperience(experience);
            System.out.println("Experience: " + experienceDetails);

            String jobDetails = extractJobDetails(experience);
            System.out.println("Job Details: " + jobDetails);

            System.out.println("---------------");
        }
    }

    private static List<String> extractExperiences(String text, String startPattern, String endPattern) {
        List<String> experiences = new ArrayList<>();
        Pattern pattern = Pattern.compile(startPattern + "(.*?)" + endPattern, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            experiences.add(matcher.group(1).trim());
        }

        return experiences;
    }

    private static String extractCompanyName(String resumeText) {
        Pattern pattern = Pattern.compile("([A-Za-z0-9&'’\"!@#$%^*(),.?\":{}|<>]+(?: [A-Za-z0-9&'’\"!@#$%^*(),.?\":{}|<>]+)*)");
        Matcher matcher = pattern.matcher(resumeText);

        if (matcher.find()) {
            return matcher.group(1).trim();
        } else {
            return "Not found";
        }
    }

    private static String extractExperience(String resumeText) {
        Pattern pattern = Pattern.compile("\\b(\\d+ years? \\d+ months?)\\b");
        Matcher matcher = pattern.matcher(resumeText);

        if (matcher.find()) {
            return matcher.group(0).trim();
        } else {
            return "Not found";
        }
    }

    private static String extractJobDetails(String resumeText) {
        Pattern pattern = Pattern.compile("• (.+?)(?:\\r\\n|$)");
        Matcher matcher = pattern.matcher(resumeText);

        StringBuilder details = new StringBuilder();
        while (matcher.find()) {
            details.append(matcher.group(1)).append("\n");
        }

        return details.toString().trim();
    }
}

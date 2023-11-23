package com.kanna.pdfread;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.kanna.pdfread.Master.extractSection;

public class NerExample {

    public static void main(String[] args) throws IOException {

        StanfordCoreNLP stanfordCoreNLP = PipeLine.getPipeline();


       // File file = new File("C:\\Users\\Vishal\\Downloads\\Prashanth Profile.pdf");
       // File file = new File("C:\\Users\\Vishal\\Downloads\\Profile (1).pdf");
        File file = new File("C:\\Users\\Vishal\\Downloads\\Vaibhav LinkedIn Profile.pdf");

            PDDocument document = PDDocument.load(file);


            PDFTextStripper s = new PDFTextStripper();
            String content = s.getText(document);
            String  removedPageNo = content.replaceAll("Page","");
            String expSec = extractSection(removedPageNo, "Experience", "Education");

            document.close();

         CoreDocument coreDocument = new CoreDocument(expSec);
         stanfordCoreNLP.annotate(coreDocument);

            List<CoreLabel> coreLabelList = coreDocument.tokens();

          for (CoreLabel coreLabel : coreLabelList) {
            String ner = coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);
        //  if (ner.equals("ORGANIZATION") || ner.equals("CITY") || ner.equals("COUNTRY") || ner.equals("DATE") || ner.equals("TITLE")) {
            System.out.println(coreLabel.originalText() + " " + ner);
        }
        //}

        List<ExperienceEntry> experienceEntries = new ArrayList<>();

        extractExperienceInformation(coreDocument, experienceEntries);

        for (ExperienceEntry entry : experienceEntries) {
            System.out.println("Company: " + entry.getCompanyName() +
                    ", Designation: " + entry.getDesignation() +
                    ", Date of Joining: " + entry.getDateOfJoining());
        }

    }

    private static void extractExperienceInformation(CoreDocument coreDocument, List<ExperienceEntry> experienceEntries) {
        List<CoreLabel> coreLabelList = coreDocument.tokens();
        ExperienceEntry currentEntry = null;

        for (int i = 0; i < coreLabelList.size(); i++) {
            CoreLabel coreLabel = coreLabelList.get(i);
            String ner = coreLabel.get(CoreAnnotations.NamedEntityTagAnnotation.class);

            if (ner.equals("ORGANIZATION")) {
                currentEntry = new ExperienceEntry();
                currentEntry.setCompanyName(coreLabel.originalText());

                while (i + 1 < coreLabelList.size() &&
                        coreLabelList.get(i + 1).get(CoreAnnotations.NamedEntityTagAnnotation.class).equals("ORGANIZATION")) {
                    currentEntry.setCompanyName(currentEntry.getCompanyName() + " " + coreLabelList.get(i + 1).originalText());
                    i++;
                }
            } else if (ner.equals("TITLE") && currentEntry != null) {
                currentEntry.setDesignation(coreLabel.originalText());

                while (i + 1 < coreLabelList.size() &&
                        coreLabelList.get(i + 1).get(CoreAnnotations.NamedEntityTagAnnotation.class).equals("TITLE")) {
                    currentEntry.setDesignation(currentEntry.getDesignation() + " " + coreLabelList.get(i + 1).originalText());
                    i++;
                }
            } else if (ner.equals("DATE") && currentEntry != null) {
                currentEntry.setDateOfJoining(coreLabel.originalText());
                while (i + 1 < coreLabelList.size() &&
                        coreLabelList.get(i + 1).get(CoreAnnotations.NamedEntityTagAnnotation.class).equals("DATE")) {
                    currentEntry.setDateOfJoining(currentEntry.getDateOfJoining() + " " + coreLabelList.get(i + 1).originalText());
                    i++;
                }
                experienceEntries.add(currentEntry);
                currentEntry = null;
            }
        }
    }
    }


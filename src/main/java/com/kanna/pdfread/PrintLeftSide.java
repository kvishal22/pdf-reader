package com.kanna.pdfread;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.action.PDAction;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.kanna.pdfread.DummyCheckTwo.excludeLinesStartingWith;
import static com.kanna.pdfread.Master.extractSection;

//printed the left side
//printed linked in url
public class PrintLeftSide {

    static StringBuilder extractedText = new StringBuilder();

    public static void main(String[] args) throws IOException {
        try (PDDocument document = PDDocument.load(new File("C:\\Users\\Vishal\\Downloads\\Prashanth Profile.pdf"))) {

            PDPage page = document.getPage(0);

            PDRectangle mediaBox = page.getMediaBox();

            float leftX = mediaBox.getLowerLeftX() / 4;
            float bottomY = mediaBox.getLowerLeftY() / 2;
            float rightX = mediaBox.getUpperRightX() / 3;
            float topY = mediaBox.getUpperRightY() / 2;

            PDFTextStripper textStripper = new PDFTextStripper() {
                @Override
                protected void writeString(String string, List<TextPosition> textPositions){
                    for (TextPosition text : textPositions) {
                        float x = text.getXDirAdj();
                        float y = text.getYDirAdj();
                        if (x >= leftX && x <= rightX && y >= bottomY && y <= topY) {
                            extractedText.append(text.getUnicode());
                        }
                    }
                    }
                };
            textStripper.getText(document);
            String topSkillSection = excludeSection(String.valueOf(extractedText), "Contact", "Top Skills");
            System.out.println("Top Skills Section: \n" + topSkillSection);

        }
    }

    private static String excludeSection(String pageText, String sectionStart, String sectionEnd) {
        int startIndex = pageText.indexOf(sectionStart);
        int endIndex = pageText.indexOf(sectionEnd);

        if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            return pageText.substring(0, startIndex) + pageText.substring(endIndex + sectionEnd.length());
        } else {
            return pageText;
        }
    }
}

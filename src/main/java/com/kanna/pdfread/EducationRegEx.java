package com.kanna.pdfread;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.kanna.pdfread.Master.extractSection;

public class EducationRegEx {
    public static void main(String... a) throws IOException {

      //  String al ="St Kabir Institute of Professional Studies\r\nPostgraduate Degree, Finance & Marketing · (March 2010 - November 2012)\r\nIndira Gandhi National Open University\r\nMA in Economics, Development Economics and International\r\nDevelopment · (January 2023)\r\nIndian Institute of Banking and Finance\r\nDiploma of Education, Diploma in Banking & Finance · (October\r\n2011 - January 2012)\r\nCommerce College  Jaipur\r\nBachelor of Commerce - BCom, Accounting and Finance · (March 2007 - July\r\n2009)\r\nDAV centenary public School\r\nBachelor of Commerce - BCom, Accounting and Finance · (1995 - 2006)\r\n ";
        File file = new File("C:\\Users\\Vishal\\Downloads\\Profile (1).pdf");

        //File file = new File("C:\\Users\\Vishal\\Downloads\\Vaibhav LinkedIn Profile.pdf");

        PDDocument document = PDDocument.load(file);


        int numberOfPages = document.getNumberOfPages();


        PDFTextStripper s = new PDFTextStripper();
        String text = s.getText(document);
        document.close();
       // System.out.println(text);

        Pattern pa = Pattern.compile("\\d{9,10}");
        Matcher matcher = pa.matcher(text);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        Pattern pp= Pattern.compile("www\\.linkedin\\.com/\\w+/[a-zA-Z-0-9]+\n[a-zA-Z0-9]+");
        //https://www.linkedin.com/in/umesh-balakrishnan-b6aab120b
        Matcher maa = pp.matcher(text);
        if(maa.find()){
            System.out.println(maa.group());
        }


        String edu = extractSection(text, "Education", "Page "+numberOfPages+" of "+numberOfPages);


        List<String> list = new ArrayList<>();

        Pattern p = Pattern.compile("([^\\r]+)");
        Matcher m =p.matcher(edu);

        while (m.find()){
                list.add(m.group());
        }


        Map<String,String> map = new LinkedHashMap<>();

            for (int i = 0; i < list.size(); i += 2) {
                String key = list.get(i);
                String value = list.get(i + 1);
                map.put(key, value);
            }

        System.out.println("HashMap: " + map);

        /*System.out.println(list.subList(2,4));
        System.out.println(list.subList(4,6));
        System.out.println(list.subList(6,8));
        System.out.println(list.subList(8,10));*/


        /* while (list.size() >= 2) {
            List<String> firstTwoElements = list.subList(0, 2);
            System.out.println(firstTwoElements);
            list.subList(0, 2).clear();
        }*/


    }

}

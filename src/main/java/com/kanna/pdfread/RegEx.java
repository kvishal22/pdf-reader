package com.kanna.pdfread;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {

    public static void main(String[] args) {

        System.out.println(Pattern.matches("agent \\d\\d\\d ","agent 007 "));
        System.out.println(Pattern.matches("agent \\d{3} ","agent 007 "));
        System.out.println(Pattern.matches("agent \\d{3,} ","agent 089898 "));
        System.out.println(Pattern.matches("agent \\d{3,4} ","agent 0834824 "));
        System.out.println(Pattern.matches("^agent \\d{3,4} ","agent 0834 "));


        Pattern p = Pattern.compile("^agent \\d{3,4}");
        Matcher m = p.matcher("sagent 93032");
        System.out.println(m.find());

        Pattern pa = Pattern.compile("agent \\d{3,4}");
        Matcher ma = pa.matcher("sagent 93032");
        System.out.println(ma.matches());

        Pattern paa = Pattern.compile("agent \\d{3,4}");
        Matcher maa = paa.matcher("sagent 93032");
        System.out.println(maa.find());

        Pattern paaa = Pattern.compile("agent \\d{3,4}$");
        Matcher maaa = paaa.matcher("sagent 9303");
        System.out.println(maaa.find());


        Pattern paaaa = Pattern.compile("agent [0-4]{3,4}$");
        Matcher maaaa = paaaa.matcher("sdasdagent 1303");
        System.out.println(maaaa.find());

        Pattern paaaaa = Pattern.compile("^([a|A])gent (\\d{3,4})$");
        Matcher maaaaa = paaaaa.matcher("agent 007");

        if(maaaaa.find()){
            System.out.println(maaaaa.group());
            System.out.println(maaaaa.group(1));
            System.out.println(maaaaa.group(2));
        }
        Pattern pp = Pattern.compile("\\d{4}-\\d{4}-\\d{2}");
        Matcher mm = pp.matcher("8923-4238-4324-9854");
        String mask = mm.replaceAll("XXXX-XXXX-XX");
        System.out.println(mask);


    }
}

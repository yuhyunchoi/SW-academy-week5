package com.nhnacademy;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
    public static void main(String[] args) {
        
        final Options op = new Options();
        op.addOption(new Option("d", "debug", false, "Turn on debug."));
        op.addOption(new Option("e", "extract", false, "Turn on extract."));
        op.addOption(new Option("o", "option", true, "Turn on option with argument."));

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd =  parser.parse(op, args);
            if(cmd.hasOption("d")){
                System.out.println();
            }if(cmd.hasOption("e")){
                System.out.println(cmd.getOptionValue("e"));
            }if(cmd.hasOption("o")){
                System.out.println(cmd.getOptionValue("o"));
            }
            else{
                System.out.println("없는 옵션입니다.");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
package edu.rpi.csci.sdd.epic.scraper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.StringEscapeUtils;
import edu.rpi.csci.sdd.epic.util.Util;

public class ENIEInterface
{
    private static String ENIE_DIR = "/home/avi/Documents/nlp_fall2014/nlp_project_mcguik2_weinsa/ENIE/";
    private static String[] makeENIEArgsList(File filelist, File ioDir)
    {
        String scriptName = ENIE_DIR + "scripts/enie_Qi_unsentence.sh";
        String arg1 = filelist.getAbsolutePath();
        String arg2_3 = ioDir.getAbsolutePath();
        //System.out.printf("(\"%s\", \"%s\")\n", arg1, arg2_3);
        return new String[] { scriptName, arg1, arg2_3, arg2_3 };
    }

    public static String runIETagger(String text) throws IOException, InterruptedException
    {
        File filelist = File.createTempFile("foo", "");
        File dataFile = File.createTempFile("foo", ".sgm");

        PrintStream flp = new PrintStream(new FileOutputStream(filelist));
        flp.println(dataFile.getName());
        flp.close();

        PrintStream dfp = new PrintStream(new FileOutputStream(dataFile));
        dfp.println("<DOC>");
        dfp.println("<TEXT>");
        dfp.println(StringEscapeUtils.escapeXml11(text));
        dfp.println("</TEXT>");
        dfp.println("</DOC>");
        dfp.close();

        Process p = Runtime.getRuntime().exec(makeENIEArgsList(filelist, dataFile.getParentFile()));
        //asynchStreamCopy(System.in,p.getOutputStream());
        //asynchStreamCopy(p.getInputStream(),System.out);
        //asynchStreamCopy(p.getErrorStream(),System.err);
        p.waitFor();

        File outputFile = new File(dataFile.getAbsolutePath()+".xml");

        return Util.slurpFile(outputFile);
    }
}

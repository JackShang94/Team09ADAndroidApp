package com.example.jack.team09adandroidapp;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by doujohner on 28/1/2018.
 */

public class StackTrace {
    public static String trace(Exception ex) {
        StringWriter outStream = new StringWriter();
        ex.printStackTrace(new PrintWriter(outStream));
        return outStream.toString();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ancode.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author chenzhihui
 */
public class Log {
   
    public static void println(Object msg) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + " : " + msg);
    }
}

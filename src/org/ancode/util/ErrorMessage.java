/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ancode.util;

import javax.swing.JOptionPane;

public class ErrorMessage {
    public static void show(String error) {
        JOptionPane.showMessageDialog(null, error);
    }
}

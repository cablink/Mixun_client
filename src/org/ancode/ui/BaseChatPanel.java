/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ancode.ui;

import org.ancode.model.Message;

public class BaseChatPanel extends javax.swing.JPanel {

    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public StringBuilder getMessages() {
        return null;
    }

    public void setMessages(Message message) {
        
    }
}

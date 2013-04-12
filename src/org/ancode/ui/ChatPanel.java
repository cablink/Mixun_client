/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ancode.ui;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AbstractDocument.Content;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import org.ancode.model.Member;
import org.ancode.model.Message;
import org.ancode.service.MessageService;
import org.ancode.util.Log;
import org.ancode.util.QQImageUtil;
import org.ancode.util.ThreadUtil;


public class ChatPanel extends BaseChatPanel {

    private MainFrame mainFrame = MainFrame.getMainFrame();
    private Map map = mainFrame.getMap();
    private MessageService messageService = MessageService.getIntance();
    private Member member = null;
    private StringBuilder messages = new StringBuilder();
    private HTMLDocument sendText = null;
    

    private JPanel ChatPanelBottom;
    private JPanel ChatPanelTop;
    
    private JButton btnFont;
    private JButton btnImage;
    private JButton btnFace;
    private JButton btnSend;
    private JTextPane chatingTextPane;
    public  JEditorPane editorPane;
    
    private JButton jButton7;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane3;
    
    private JToolBar.Separator jSeparator;
    private JToolBar jToolBar1;
    private JLabel lblFace;
    private JLabel lblLnick;
    private JLabel lblUsername;
    private boolean isAddMXinfo;
    
    public StringBuilder getMessages() {
        return messages;
    }

    public void setMessages(Message message) {

        this.messages.append(message.toString());
        this.chatingTextPane.setText(this.messages.toString());
        this.chatingTextPane.setSelectionEnd(chatingTextPane.getText().length());
        this.validate();
        this.setVisible(true);

    }

    /**
     * Creates new form ChatPanel
     */
    public ChatPanel(Member member) throws Exception {
        initComponents();
        super.setId(member.getUin());
        isAddMXinfo = true;
        this.member = member;
        this.lblFace.setIcon(member.getFace());
        String name = member.getMarkname() == null ? "" : member.getMarkname() + "-";
        name += member.getNickname() == null ? "" : member.getNickname();
        this.lblUsername.setText(name);
        this.lblLnick.setText(member.getLnick());

        this.validate();
        this.setVisible(true);

    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ChatPanelTop = new JPanel();
        lblFace = new JLabel();
        lblUsername = new JLabel();
        lblLnick = new JLabel();
        
        ChatPanelBottom = new JPanel();
        jToolBar1 = new JToolBar();
        jSeparator = new JToolBar.Separator();
        btnFont = new JButton();
        btnFace = new JButton();
        btnImage = new JButton();
        jButton7 = new JButton();
        btnSend = new JButton();
        jScrollPane3 = new JScrollPane();
        chatingTextPane = new JTextPane();
        jLabel1 = new JLabel();
        jScrollPane1 = new JScrollPane();
        editorPane = new JEditorPane();

        lblFace.setIcon(new ImageIcon(getClass().getResource("/res/images/boy.png"))); // NOI18N
        lblUsername.setText("  ");
        lblLnick.setFont(new java.awt.Font("AR PL UMing CN", 0, 14)); // NOI18N
        lblLnick.setText("  ");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.add(jSeparator);

        btnFont.setIcon(new ImageIcon(getClass().getResource("/res/images/icon/w.png"))); // NOI18N
        btnFont.setFocusable(false);
        btnFont.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFont.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(btnFont);

        btnFace.setIcon(new ImageIcon(getClass().getResource("/res/images/icon/boy.png"))); // NOI18N
        btnFace.setFocusable(false);
        btnFace.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFace.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnFace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnFaceActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFace);

        btnImage.setIcon(new ImageIcon(getClass().getResource("/res/images/icon/i.png"))); // NOI18N
        btnImage.setFocusable(false);
        btnImage.setHorizontalTextPosition(SwingConstants.CENTER);
        btnImage.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(btnImage);

        jButton7.setIcon(new ImageIcon(getClass().getResource("/res/images/icon/act.png"))); // NOI18N
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(SwingConstants.BOTTOM);
        jToolBar1.add(jButton7);

        btnSend.setText("发送");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });
        btnSend.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                btnSendKeyPressed(evt);
            }
        });

        chatingTextPane.setBackground(Color.white);
        chatingTextPane.setContentType("text/html");
        chatingTextPane.setEditable(false);
        chatingTextPane.setFocusable(false);
        jScrollPane3.setViewportView(chatingTextPane);

        jLabel1.setFont(new Font("Ubuntu", 0, 10)); // NOI18N
        jLabel1.setText("Ctrl+Enter");

        editorPane.setBackground(java.awt.SystemColor.text);
        editorPane.setContentType("text/html");
        editorPane.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                editorPaneKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(editorPane);
        editorPane.getAccessibleContext().setAccessibleDescription("");

        GroupLayout ChatPanelBottomLayout = new GroupLayout(ChatPanelBottom);
        ChatPanelBottom.setLayout(ChatPanelBottomLayout);
        ChatPanelBottomLayout.setHorizontalGroup(
            ChatPanelBottomLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ChatPanelBottomLayout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addGap(18, 18, 18)
                .addGroup(ChatPanelBottomLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jScrollPane3)
        );
        ChatPanelBottomLayout.setVerticalGroup(
            ChatPanelBottomLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(ChatPanelBottomLayout.createSequentialGroup()
                .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ChatPanelBottomLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(ChatPanelBottomLayout.createSequentialGroup()
                        .addComponent(btnSend)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addContainerGap())
                    .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(ChatPanelTop, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ChatPanelBottom, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ChatPanelTop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(ChatPanelBottom, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        // TODO add your handling code here:
        //this.myChatText.setLineWrap(true);
        if (editorPane.getDocument().getLength() == 0) {
            return;
        }
        //int chatingTextLength = chatingTextPane.getDocument().getLength();
        if (isAddMXinfo) {
            String from = " ";
            try {
                editorPane.getDocument().insertString(editorPane.getDocument().getLength(), from, null);
            } catch (BadLocationException ex) {
                Logger.getLogger(ChatPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            isAddMXinfo = false;
        }
        
        HTMLDocument htmlDoc = new HTMLDocument();
        String text = QQImageUtil.parseHTML(editorPane.getText());
        try {
            htmlDoc.insertString(0, text, null);
        } catch (BadLocationException ex) {
            Logger.getLogger(ChatPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        HTMLDocument chatingHtmlDoc = (HTMLDocument) chatingTextPane.getDocument();
        HTMLEditorKit kit = (HTMLEditorKit) chatingTextPane.getEditorKit();

        
        Member mySelf = (Member) map.get("member");
        Message message = new Message();
        message.setMember(mySelf);
        message.setCreateDate(new Date());
        message.setMessage(htmlDoc);

        this.messages.append(message.toString());

        try {
            //chatingHtmlDoc.insertString(chatingHtmlDoc.getLength(), htmlDoc.getText(0, htmlDoc.getLength()), null);
            kit.insertHTML(chatingHtmlDoc, chatingHtmlDoc.getLength(), message.toString(), 0, 0, null);
        } catch (Exception ex) {
            Logger.getLogger(ChatPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        sendText = (HTMLDocument) this.editorPane.getDocument();
        //this.chatingTextPane.setText(this.messages.toString());
        //this.editorPane.setText("");

        Runnable r = new Runnable() {

            public void run() {
                messageService.sendMsg(member.getUin(), sendText);
            }
        };
        ThreadUtil.submit(r);
        this.chatingTextPane.setSelectionEnd(chatingTextPane.getText().length());
    }//GEN-LAST:event_btnSendActionPerformed

    private void btnSendKeyPressed(KeyEvent evt) {//GEN-FIRST:event_btnSendKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.btnSendActionPerformed(null);
        }
    }//GEN-LAST:event_btnSendKeyPressed

    private void btnFaceActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnQQFaceActionPerformed
        // TODO add your handling code here:
      /*  ChatDialog chatDialog = (ChatDialog) map.get("chatDialog");
        QQFaceDialog qqface = QQFaceDialog.getInstance();
        qqface.setTextEditor(this.editorPane);
        if (!qqface.isVisible()) {
            qqface.setBounds(chatDialog.getX() + ChatPanelBottom.getX() + 5, chatDialog.getY() + ChatPanelBottom.getY() - 40, qqface.getWidth(), qqface.getHeight());
            qqface.setVisible(true);
        } else {
            qqface.setVisible(false);
        }
		*/
    }//GEN-LAST:event_btnQQFaceActionPerformed

    private void editorPaneKeyPressed(KeyEvent evt) {//GEN-FIRST:event_editorPaneKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.btnSendActionPerformed(null);
        }
    }//GEN-LAST:event_editorPaneKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    
    // End of variables declaration//GEN-END:variables

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ancode.ui;

import java.awt.Rectangle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ancode.model.Member;

/**
 *
 * @author chenzhihui
 */
public class FloatInfoDialog extends javax.swing.JDialog {

    Member member  = null;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    /**
     * Creates new form FloatInfoDialog
     */
    public FloatInfoDialog(java.awt.Frame parent, boolean model) {
        super(parent, model);
    }
    public FloatInfoDialog(java.awt.Frame parent, Member member) {
        super(parent, false);
        initComponents();
        this.member = member;
    }
    public FloatInfoDialog(java.awt.Frame parent, Rectangle rectangle) {
        super(parent, false);
        initComponents();
        setBounds(rectangle);
    }
    
    public FloatInfoDialog(java.awt.Frame parent) {
        super(parent, false);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        infoPanel = new javax.swing.JPanel();
        lblNickName = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblGender = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblLnick = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblMarkname = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblLevel = new javax.swing.JLabel();
        btnFace = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusCycleRoot(false);
        setFocusable(false);
        setFocusableWindowState(false);
        setName("������������");
        setResizable(false);
        setUndecorated(true);

        infoPanel.setPreferredSize(new java.awt.Dimension(180, 130));
        infoPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoPanelMouseExited(evt);
            }
        });

        lblNickName.setText("������������");

        jLabel1.setFont(new java.awt.Font("AR PL UMing CN", 0, 14)); // NOI18N
        jLabel1.setText("������:");

        lblGender.setFont(new java.awt.Font("AR PL UMing CN", 0, 14)); // NOI18N
        lblGender.setText(null);

        jLabel3.setFont(new java.awt.Font("AR PL UMing CN", 0, 14)); // NOI18N
        jLabel3.setText("������������:");

        lblLnick.setFont(new java.awt.Font("AR PL UMing CN", 0, 14)); // NOI18N
        lblLnick.setText("���������������������������");

        jLabel5.setFont(new java.awt.Font("AR PL UMing CN", 0, 14)); // NOI18N
        jLabel5.setText("������:");

        lblMarkname.setFont(new java.awt.Font("AR PL UMing CN", 0, 14)); // NOI18N
        lblMarkname.setText(null);

        jLabel7.setFont(new java.awt.Font("AR PL UMing CN", 0, 13)); // NOI18N
        jLabel7.setText("������:");

        lblLevel.setFont(new java.awt.Font("AR PL UMing CN", 0, 13)); // NOI18N
        lblLevel.setText("������");

        btnFace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iqq/res/images/face.png"))); // NOI18N
        btnFace.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                btnFaceMouseMoved(evt);
            }
        });
        btnFace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFaceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLnick, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(4, 4, 4)
                        .addComponent(lblMarkname)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblGender))
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(btnFace)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNickName)
                            .addGroup(infoPanelLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, infoPanelLayout.createSequentialGroup()
                        .addComponent(lblNickName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(lblLevel))
                        .addGap(18, 18, 18))
                    .addGroup(infoPanelLayout.createSequentialGroup()
                        .addComponent(btnFace)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(lblMarkname)
                    .addComponent(lblGender))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblLnick))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(infoPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void infoPanelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoPanelMouseExited
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_infoPanelMouseExited

    private void btnFaceMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFaceMouseMoved
        // TODO add your handling code here:
        this.setVisible(true);
    }//GEN-LAST:event_btnFaceMouseMoved

    private void btnFaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFaceActionPerformed
        // TODO add your handling code here:
        MemberInfoDialog dialogMemberInfo = new MemberInfoDialog(MainFrame.getMainFrame(), false);
        dialogMemberInfo.setVisible(true);
        try {
            dialogMemberInfo.btnFace.setIcon(member.getFace());
        } catch (Exception ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        dialogMemberInfo.textNickname.setText(member.getNickname());
        dialogMemberInfo.textLnick.setText(member.getLnick());
        dialogMemberInfo.textMobile.setText(member.getMobile());
        dialogMemberInfo.textPhone.setText(member.getPhone());
        dialogMemberInfo.textCountry.setText(member.getCountry());
        dialogMemberInfo.textCity.setText(member.getCity());
        dialogMemberInfo.textProvince.setText(member.getProvince());
        dialogMemberInfo.textEmail.setText(member.getEmail());
        dialogMemberInfo.textOccupation.setText(member.getOccupation());
        dialogMemberInfo.textCollege.setText(member.getCollege());
        dialogMemberInfo.textHomepage.setText(member.getHomepage());
        dialogMemberInfo.textPersonal.setText(member.getPersonal());
        dialogMemberInfo.lblLevel.setText(member.getLevel());
        
        String gender = member.getGender();
        if (gender != null && !gender.equals("")) {
            if (gender.equals("male")) {
                dialogMemberInfo.comboGender.setSelectedIndex(1);
            } else if (gender.equals("female")) {
                dialogMemberInfo.comboGender.setSelectedIndex(2);
            }
        } else {
            dialogMemberInfo.comboGender.setSelectedIndex(0);
        }

        int chineseZodiac = member.getChineseZodiac();
        for (int i = 0; i <= 12; i++) {
            if (chineseZodiac == i) {
                dialogMemberInfo.comboChineseZodiac.setSelectedIndex(i);
            }
        }

        int blood = member.getBlood();
        for (int i = 0; i <= 12; i++) {
            if (blood == i) {
                dialogMemberInfo.comboBlood.setSelectedIndex(i);
            }
        }

        int constel = member.getConstel();
        for (int i = 0; i <= 12; i++) {
            if (constel == i) {
                dialogMemberInfo.comboConstel.setSelectedIndex(i);
            }
        }
    }//GEN-LAST:event_btnFaceActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FloatInfoDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FloatInfoDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FloatInfoDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FloatInfoDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                FloatInfoDialog dialog = new FloatInfoDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFace;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    public javax.swing.JLabel lblGender;
    public javax.swing.JLabel lblLevel;
    public javax.swing.JLabel lblLnick;
    public javax.swing.JLabel lblMarkname;
    public javax.swing.JLabel lblNickName;
    // End of variables declaration//GEN-END:variables
}

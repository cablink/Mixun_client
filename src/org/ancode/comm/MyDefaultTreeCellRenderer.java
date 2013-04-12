package org.ancode.comm;

import java.awt.Component;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import org.ancode.model.Member;
import org.ancode.service.MemberService;
import org.ancode.util.ThreadUtil;


public class MyDefaultTreeCellRenderer extends DefaultTreeCellRenderer {

    /**
     * ID
     */
    private static final long serialVersionUID = 1L;
    private Member m = null;

   
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {

            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
                row, hasFocus);

        setText(value.toString());

        if (sel) {
            setForeground(getTextSelectionColor());
        } else {
            setForeground(getTextNonSelectionColor());
        }

        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

        String stringValue = tree.convertValueToText(value, sel, expanded, leaf, row, hasFocus);
        
        if (value instanceof IconTreeNode) {
            Font f = new Font("宋体", Font.PLAIN, 14);
            this.setFont(f);
            setText(stringValue);
            this.setIconTextGap(10);

            IconTreeNode iconTreeNode = (IconTreeNode) value;

            if (iconTreeNode.getMember() != null) {
                m = iconTreeNode.getMember();
                Runnable r = new Runnable() {

                    @Override
                    public void run() {
                        try {
                            m = MemberService.getInstance().getMemberAccount(m);
                            m.setFace(MemberService.getInstance().downloadFace(m));
                        } catch (Exception ex) {
                            Logger.getLogger(MyDefaultTreeCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                };
                if (m != null && !m.isIsDownloadInfo()) {
                    ThreadUtil.submit(r);
                }

                try {
                    setIcon(m.getFace());
                } catch (Exception ex) {
                    Logger.getLogger(MyDefaultTreeCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (getIcon() == null) {
                    if (m != null) {
                        ThreadUtil.submit(r);
                    }
                }
            } else if (iconTreeNode.getGroup() != null) {
                setIcon(iconTreeNode.getGroup().getFace());
            }

        } else {
            Font f = new Font("宋体", Font.BOLD, 14);
            setText(stringValue);
            this.setFont(f);
            Border border = BorderFactory.createEmptyBorder(4, 4, 4, 4);
            JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
                    hasFocus);
            label.setBorder(border);
        }

        return this;
    }
}

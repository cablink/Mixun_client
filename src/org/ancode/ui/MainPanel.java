package org.ancode.ui;

import org.ancode.service.GroupService;
import org.ancode.service.CategoryService;
import org.ancode.service.StackMessageService;
import org.ancode.service.MessageService;
import org.ancode.service.MemberService;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.tree.*;
import org.ancode.comm.Auth;
import org.ancode.comm.IconTreeNode;
import org.ancode.comm.MyDefaultTreeCellRenderer;
import org.ancode.model.Category;
import org.ancode.model.Group;
import org.ancode.model.Member;
import org.ancode.model.Message;
import org.ancode.util.BrowserUtil;
import org.ancode.util.ErrorMessage;
import org.ancode.util.Log;
import org.ancode.util.ThreadUtil;

public class MainPanel extends javax.swing.JPanel {
    
    private MainFrame mainFrame = MainFrame.getMainFrame();
    private Map map = mainFrame.getMap();
    private NewMessageFrame newMessageFrame = null;
    private Member member = null;
    private List<Category> categoryList = null;
    private Map messagePrompt = new HashMap();
    private boolean isNewMessage;
    private JTree contactsTree = null;
    private DefaultTreeModel treeModel = null;
    private FloatInfoDialog floatInfo = null;
    private JTree groupTree = null;
    private JTree recentTree = null;

    
    private JPanel bottomPanel;
    private JButton btnNewMessage;
    private JButton btnAdd;
    private JPanel contactsPanel;
    private JPanel containerPanel;
    private JButton face;
    private JPanel groupPanel;
    private JPanel jPanel1;
    private JScrollPane jScrollPane3;
    private JToolBar.Separator jSeparator1;
    private JTabbedPane jTabbedPane2;
    private JLabel labLevel;
    private JLabel lblLnick;
    private JLabel lblNickname;
    private JToolBar messageToolBar;
    private JPanel recentPanel;
    private JTabbedPane tabbedPane;
   
    private JPanel topPanel;
    private JLabel lblID;
    /**
     * Creates new form MainPanel
     */
    public MainPanel() {
        
        MainFrame.getMainFrame().setIBackground(null);
        initComponents();
        mainFrame.setIconImage(Auth.getMember().getFace().getImage());
        Log.println("mainPanel");
        this.setVisible(true);

        try {
            TrayFactory.getInstance().createTray(mainFrame, Auth.getMember().getFace(), "密讯");
        } catch (Exception ex) {
            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
 
    private void initComponents() {

    	topPanel = new JPanel();
        face = new JButton();
        lblID= new JLabel();
        containerPanel = new JPanel();
        tabbedPane = new JTabbedPane();
        contactsPanel = new JPanel();
        groupPanel = new JPanel();
        recentPanel = new JPanel();
        bottomPanel = new JPanel();
        messageToolBar = new JToolBar();
        btnNewMessage = new JButton();
        jSeparator1 = new JToolBar.Separator();
        btnAdd = new JButton();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(255, 555));
        setLayout(new java.awt.BorderLayout());

        face.setIcon(new ImageIcon(getClass().getResource("/res/images/boy.png")));
        face.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faceActionPerformed(evt);
            }
        });
        topPanel.add(face);

        lblID.setPreferredSize(new Dimension(120, 15));
        topPanel.add(lblID);

        add(topPanel, BorderLayout.PAGE_START);

        containerPanel.setLayout(new java.awt.BorderLayout());

        tabbedPane.setPreferredSize(new Dimension(96, 260));
        tabbedPane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tabbedPaneMouseClicked(evt);
            }
            public void mouseExited(MouseEvent evt) {
                tabbedPaneMouseExited(evt);
            }
        });
        tabbedPane.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                tabbedPaneFocusLost(evt);
            }
        });

        contactsPanel.setAutoscrolls(true);
        contactsPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("联系人", contactsPanel);

        groupPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("群/讨论组", groupPanel);

        recentPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("最近联系人", recentPanel);

        containerPanel.add(tabbedPane, java.awt.BorderLayout.CENTER);

        add(containerPanel, java.awt.BorderLayout.CENTER);

        bottomPanel.setLayout(new java.awt.BorderLayout());

        messageToolBar.setRollover(true);
        messageToolBar.setAutoscrolls(true);
        messageToolBar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        messageToolBar.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);

        btnNewMessage.setIcon(new ImageIcon(getClass().getResource("/res/images/icon/icon.png"))); // NOI18N
        btnNewMessage.setToolTipText("菜单");
        btnNewMessage.setFocusable(false);
        btnNewMessage.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNewMessage.setVerticalTextPosition(SwingConstants.BOTTOM);
        messageToolBar.add(btnNewMessage);
        messageToolBar.add(jSeparator1);

        btnAdd.setText("Btn");
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
        messageToolBar.add(btnAdd);

        bottomPanel.add(messageToolBar, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.PAGE_END);
        topPanel = new JPanel();
        face = new JButton();
        lblID = new JLabel();
        containerPanel = new JPanel();
        tabbedPane = new JTabbedPane();
        contactsPanel = new JPanel();
        groupPanel = new JPanel();
        recentPanel = new JPanel();
        bottomPanel = new JPanel();
        messageToolBar = new JToolBar();
        btnNewMessage = new JButton();
        jSeparator1 = new JToolBar.Separator();
        

        setOpaque(false);
        setPreferredSize(new Dimension(255, 555));
        setLayout(new BorderLayout());

        face.setIcon(new ImageIcon(getClass().getResource("/res/images/boy.png"))); // NOI18N
        face.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faceActionPerformed(evt);
            }
        });
        topPanel.add(face);

        lblID.setPreferredSize(new Dimension(120, 15));
        topPanel.add(lblID);

        add(topPanel, BorderLayout.PAGE_START);

        containerPanel.setLayout(new BorderLayout());

        tabbedPane.setPreferredSize(new Dimension(96, 260));
        tabbedPane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tabbedPaneMouseClicked(evt);
            }
            public void mouseExited(MouseEvent evt) {
                tabbedPaneMouseExited(evt);
            }
        });
        tabbedPane.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                tabbedPaneFocusLost(evt);
            }
        });

        contactsPanel.setAutoscrolls(true);
        contactsPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("联系人", contactsPanel);

        groupPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("群/讨论组", groupPanel);

        recentPanel.setLayout(new java.awt.BorderLayout());
        tabbedPane.addTab("最近联系人", recentPanel);

        containerPanel.add(tabbedPane, java.awt.BorderLayout.CENTER);

        add(containerPanel, java.awt.BorderLayout.CENTER);

        bottomPanel.setLayout(new java.awt.BorderLayout());

        messageToolBar.setRollover(true);
        messageToolBar.setAutoscrolls(true);
        messageToolBar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        messageToolBar.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);

        btnNewMessage.setIcon(new ImageIcon(getClass().getResource("/res/images/icon/icon.png"))); // NOI18N
        btnNewMessage.setToolTipText("菜单");
        btnNewMessage.setFocusable(false);
        btnNewMessage.setHorizontalTextPosition(SwingConstants.CENTER);
        btnNewMessage.setVerticalTextPosition(SwingConstants.BOTTOM);
        messageToolBar.add(btnNewMessage);
        messageToolBar.add(jSeparator1);

        btnAdd.setIcon(new ImageIcon(getClass().getResource("/res/images/icon/qita_add.png")));
        btnAdd.setToolTipText("添加联系人");
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(SwingConstants.BOTTOM);
        messageToolBar.add(btnAdd);

        bottomPanel.add(messageToolBar, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void faceActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_faceActionPerformed
    
    private void tabbedPaneMouseExited(MouseEvent evt) {
        // TODO add your handling code here:
        if (floatInfo != null) {
            floatInfo.setVisible(false);
        }
    }//GEN-LAST:event_tabbedPaneMouseExited
    
    private void tabbedPaneFocusLost(FocusEvent evt) {//GEN-FIRST:event_tabbedPaneFocusLost
        // TODO add your handling code here:
        if (floatInfo != null) {
            floatInfo.setVisible(false);
        }
    }//GEN-LAST:event_tabbedPaneFocusLost
    
    private void tabbedPaneMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tabbedPaneMouseClicked
        // TODO add your handling code here:
        JTabbedPane tabbedPane = (JTabbedPane) evt.getSource();
        if (tabbedPane.getSelectedIndex() == 1) {
            this.initGroupTab();
        } 
        
    }//GEN-LAST:event_tabbedPaneMouseClicked
    
    
    
    private void comboStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboStatusItemStateChanged
        // TODO add your handling code here:
        Runnable r = new Runnable() {
            
            @Override
            public void run() {
                //int loginStatusIndex = comboStatus.getSelectedIndex();
                String loginStatus = "online";
              /*  switch (loginStatusIndex) {
                    case 1: {
                        loginStatus = "callme";
                        break;
                    }
                    case 2: {
                        loginStatus = "busy";
                        break;
                    }
                    case 3: {
                        loginStatus = "hidden";
                        break;
                    }
                    case 4: {
                        loginStatus = "away";
                        break;
                    }
                    case 5: {
                        loginStatus = "silent";
                        break;
                    }
                }
                */
                MemberService.getInstance().changeStatus(loginStatus);
            }
        };
        
        if (r != null) {
            ThreadUtil.submit(r);
        }
        
    }//GEN-LAST:event_comboStatusItemStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    
    // End of variables declaration//GEN-END:variables

    void initMX() throws Exception {
       
    }
    
    public void initMXContacts() throws Exception {
        categoryList = (List<Category>) map.get("categoryList");
        Log.println("categoryList.size() " + categoryList.size());
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Friends");
        DefaultMutableTreeNode online = null;
        DefaultMutableTreeNode stranger = new DefaultMutableTreeNode("陌生人");
        DefaultMutableTreeNode blackList = new DefaultMutableTreeNode("黑名单");
        DefaultMutableTreeNode emptyTreeNode = new DefaultMutableTreeNode(" ");
        DefaultMutableTreeNode emptyTreeNode2 = new DefaultMutableTreeNode(" ");
        DefaultMutableTreeNode emptyTreeNode3 = new DefaultMutableTreeNode(" ");
        
        List<Member> onlineFriends = (List<Member>) map.get("onlineFriends");
        if (onlineFriends != null && onlineFriends.size() > 0) {
            online = new DefaultMutableTreeNode("在线好友 [" + onlineFriends.size() + "]");
            for (Member m : onlineFriends) {
                if (m.getNickname().equals("")) {
                    m.setNickname("未知");
                }
                IconTreeNode itn = new IconTreeNode(m);
                online.add(itn);
            }
        } else {
            online = new DefaultMutableTreeNode("在线好友 [0]");
            online.add(emptyTreeNode);
        }
        root.add(online);
        
        if (categoryList != null) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                category.setIndex(i);
                if (category.getName().equals("")) {
                    category.setName("未知");
                }
                DefaultMutableTreeNode f = new DefaultMutableTreeNode(category.getName() + " [" + categoryList.get(i).getOnlineCount() + "/" + categoryList.get(i).getMemberList().size() + "]");
                
                List<Member> memberList = category.getMemberList();
                for (int j = 0; j < memberList.size(); j++) {
                    Member m = memberList.get(j);
                    if (m.getNickname().equals("")) {
                        m.setNickname("未知");
                    }
                    IconTreeNode itn = new IconTreeNode(m);
                    f.add(itn);
                }
                root.add(f);
            }
        }
        stranger.add(emptyTreeNode2);
        blackList.add(emptyTreeNode3);
        root.add(stranger);
        root.add(blackList);
        
        treeModel = (DefaultTreeModel) map.get("treeModel");
        if (treeModel == null) {
            treeModel = new DefaultTreeModel(root);
        } else {
            treeModel.setRoot(root);
        }
        contactsTree = new JTree(treeModel);
        contactsTree.setRootVisible(true);
        DefaultTreeCellRenderer ceeRender = new MyDefaultTreeCellRenderer();
        contactsTree.setCellRenderer(ceeRender);
        this.contactsPanel.add(new JScrollPane(contactsTree));
        contactsTree.validate();
        contactsPanel.validate();
        mainFrame.validate();
        map.put("treeModel", treeModel);
        Log.println("initContacts");
        
        contactsTree.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                JTree tree = (JTree) e.getSource();
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                if (selPath == null || !(selPath.getLastPathComponent() instanceof IconTreeNode)) {
                    return;
                }
                IconTreeNode node = (IconTreeNode) selPath.getLastPathComponent();
                if (e.getClickCount() == 2 && node != null && node.isLeaf()) {
                    
                    ChatDialog c = (ChatDialog) map.get("chatDialog");
                    if (c == null) {
                        c = new ChatDialog(mainFrame, false);
                        map.put("chatDialog", c);
                    }
                    try {
                        List<Message> MessageList = StackMessageService.getIntance().pop(node.getMember().getUin());
                        if (MessageList != null && !MessageList.isEmpty()) {
                            for (Message msg : MessageList) {
                                c.addChat(msg);
                            }
                            String key = "messagePrompt" + MessageList.get(0).getMember().getUin();
                            JButton btnPrompt = (JButton) messagePrompt.get(key);
                            if (btnPrompt != null) {
                                messageToolBar.remove(btnPrompt);
                                messageToolBar.validate();
                                messageToolBar.repaint();
                                messagePrompt.remove(key);
                                newMessageFrame.removeButton(key);
                            }
                        } else {
                            Message msg = new Message();
                            msg.setMember(node.getMember());
                            c.addChat(msg);
                        }
                        
                    } catch (Exception ex) {
                        Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    c.setVisible(true);
                }
            }
        });
        
        this.contactsTree.addMouseMotionListener(new MouseMotionAdapter() {
            
            private Member member = null;
            
            @Override
            public void mouseMoved(MouseEvent e) {
                int x = (int) e.getPoint().getX();
                int y = (int) e.getPoint().getY();
                TreePath path = contactsTree.getPathForLocation(x, y);
                if (path != null) {
                    Object obj = path.getLastPathComponent();
                    if (obj instanceof IconTreeNode) {
                        IconTreeNode iconTreeNode = (IconTreeNode) obj;
                        member = iconTreeNode.getMember();
                        Runnable r = new Runnable() {
                            
                            @Override
                            public void run() {
                                try {
                                    member.setFace(MemberService.getInstance().downloadFace(member));
                                    if (member.getLevel() == null || member.getLevel().equals("")) {
                                        try {
                                            
                                            member = MemberService.getInstance().getMemberInfo(member);
                                        } catch (Exception ex) {
                                            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                    //Log.println(member.getFace());
                                } catch (Exception ex) {
                                    Logger.getLogger(MyDefaultTreeCellRenderer.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        };
                        
                        if (member != null && !member.isIsDownloadInfo()) {
                            ThreadUtil.submit(r);
                        }
                        
                        treeModel.nodeChanged(iconTreeNode);

                        
                        int showX = mainFrame.getX() - 175;
                        int showY = contactsTree.getY() + y + 220;
                        Rectangle rectangle = new Rectangle(showX, showY, 180, 130);
                        if (floatInfo == null) {
                            floatInfo = new FloatInfoDialog(mainFrame, rectangle);
                        } else {
                            floatInfo.setBounds(rectangle);
                        }

                        
                        floatInfo.lblNickName.setText(member.getNickname());
                        floatInfo.lblMarkname.setText(member.getMarkname());
                        floatInfo.lblGender.setText(member.getGender());
                        floatInfo.lblLnick.setText(member.getLnick());
                        floatInfo.lblLevel.setText(member.getLevel());
                        try {
                            floatInfo.btnFace.setIcon(member.getFace());
                        } catch (Exception ex) {
                            Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        floatInfo.setMember(member);
                        floatInfo.setVisible(true);
                    } else {
                        if (floatInfo != null) {
                            floatInfo.setVisible(false);
                        }
                    }
                }
                
            }
        });

        
        initGroupTab();

        
        MessageService.setIsRun(true);
        ThreadUtil.submit(MessageService.getIntance());
        
        System.gc();
    }
    
    public void initGroupTab() {
        List<Group> grouptList = GroupService.getInstance().downloadGroupList();
        if (grouptList == null || grouptList.isEmpty()) {
            return;
        }
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) map.get("recentNode");
        if (root == null) {
            root = new DefaultMutableTreeNode("Groups");
        }
        IconTreeNode itn = null;
        for (Group g : grouptList) {
            try {
                itn = new IconTreeNode(g);
            } catch (Exception ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            root.add(itn);
        }
        groupTree = (JTree) map.get("groupTreeMap");
        if (groupTree == null) {
            groupTree = new JTree(root);
            map.put("groupTreeMap", groupTree);
        }
        groupTree.setRootVisible(true);
        DefaultTreeCellRenderer ceeRender = new MyDefaultTreeCellRenderer();
        groupTree.setCellRenderer(ceeRender);
        JPanel recent = (JPanel) map.get("groupPanel");
        if (recent == null) {
            this.groupPanel.add(new JScrollPane(groupTree));
            map.put("groupPanel", groupPanel);
        }
        groupTree.validate();
        this.groupPanel.removeAll();
        this.groupPanel.add(new JScrollPane(groupTree));
        
        groupTree.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                JTree tree = (JTree) e.getSource();
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                if (selPath == null || !(selPath.getLastPathComponent() instanceof IconTreeNode)) {
                    return;
                }
                IconTreeNode node = (IconTreeNode) selPath.getLastPathComponent();
                if (e.getClickCount() == 2 && node != null && node.isLeaf()) {
                   
                    ChatDialog c = (ChatDialog) map.get("chatDialog");
                    if (c == null) {
                        c = new ChatDialog(mainFrame, false);
                        map.put("chatDialog", c);
                    }
                    try {
                        List<Message> MessageList = StackMessageService.getIntance().pop(node.getGroup().getId());
                        if (MessageList != null && !MessageList.isEmpty()) {
                            for (Message msg : MessageList) {
                                c.addChat(msg);
                            }
                            String key = "messagePrompt" + MessageList.get(0).getGroup().getId();
                            JButton btnPrompt = (JButton) messagePrompt.get(key);
                            if (btnPrompt != null) {
                                messageToolBar.remove(btnPrompt);
                                messageToolBar.validate();
                                messageToolBar.repaint();
                                messagePrompt.remove(key);
                            }
                        } else {
                            Message msg = new Message();
                            msg.setGroup(node.getGroup());
                            c.addChat(msg);
                        }
                        
                    } catch (Exception ex) {
                        Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    c.setVisible(true);
                }
            }
        });
    }
    
    
    
    public synchronized void changeStatus(Category category) throws Exception {
        int index = category.getIndex() + 1;
        //Log.println("index: " + index);
        TreeNode root =
                (TreeNode) treeModel.getRoot();
       
        DefaultMutableTreeNode f = (DefaultMutableTreeNode) root.getChildAt(index);
        List<Member> memberList = category.getMemberList();
        f.setUserObject(category.getName() + " [" + category.getOnlineCount() + "/" + memberList.size() + "]");
        f.removeAllChildren();
        for (int j = 0; j < memberList.size(); j++) {
            Member m = memberList.get(j);
            if (m.getNickname().equals("")) {
                m.setNickname("未知");
            }
            IconTreeNode itn = new IconTreeNode(m);
            f.add(itn);
        }
        treeModel.reload(f);
       
        root = (TreeNode) treeModel.getRoot();
        DefaultMutableTreeNode online = (DefaultMutableTreeNode) root.getChildAt(0);
        DefaultMutableTreeNode emptyTreeNode = new DefaultMutableTreeNode(" ");
        List<Member> onlineFriends = CategoryService.getInstance().getOnlineFriends();
        //Log.println("********Online : " + onlineFriends.size());
        if (onlineFriends != null && onlineFriends.size() > 0) {
            online.setUserObject("在线好友 [" + onlineFriends.size() + "]");
            online.removeAllChildren();
            for (Member m : onlineFriends) {
                if (m.getNickname().equals("")) {
                    m.setNickname("未知");
                }
                IconTreeNode itn = new IconTreeNode(m);
                online.add(itn);
                //Log.println("addOline:" + m.getAccount());
            }
        } else {
            online.setUserObject("在线好友 [0]");
            online.removeAllChildren();
            online.add(emptyTreeNode);
        }
        
        treeModel.reload(online);
        //treeModel.nodeChanged(online);
    }

   
    public void addMessagePrompt(final List<Message> msgList) throws Exception {
        //Log.println("addMessagePrompt");
        if (msgList == null || msgList.isEmpty()) {
            return;
        }

        
        Runnable r = new Thread(new Runnable() {
            
            @Override
            public void run() {
                if (newMessageFrame == null) {
                    newMessageFrame = new NewMessageFrame();
                }
                try {
                    newMessageFrame.addMessagePrompt(msgList);
                } catch (Exception ex) {
                    Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        ThreadUtil.submit(r);
        
        Message message = msgList.get(0);
        Group group = null;
        long id = -2;
        ImageIcon imageIcon = null;
        String name = null;
        if (message.getMember() != null) {
            member = message.getMember();
            id = member.getUin();
            imageIcon = member.getFace();
            name = member.toString();
        } else if (message.getGroup() != null) {
            group = message.getGroup();
            id = group.getId();
            imageIcon = group.getFace();
            name = group.getName();
        } else {
            return;
        }
        String key = "messagePrompt" + id;
        if (member != null) {
            //download the member infomation and face.
            member.setFace(MemberService.getInstance().downloadFace(member));
            if (member.getStat() <= 0 && member.getClient_type() <= 0) {
                try {
                    
                    member = MemberService.getInstance().getMemberInfo(member);
                } catch (Exception ex) {
                    Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(16, 16, imageIcon.getImage().SCALE_DEFAULT));
        JButton btnPrompt = null;
        btnPrompt = (JButton) messagePrompt.get(key);
        if (btnPrompt != null) {
            btnPrompt.setToolTipText(name + ":" + msgList.size() + " 条新信息");
            return;
        } else {
            btnPrompt = new JButton();
            btnPrompt.setToolTipText(1 + " 条新信息");
            btnPrompt.setIcon(icon);
            messagePrompt.put(key, btnPrompt);
        }
        
        this.messageToolBar.add(btnPrompt);
        
        btnPrompt.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                //Log.println("btnPrompt clicked!");
                ChatDialog c = (ChatDialog) map.get("chatDialog");
                if (c == null) {
                    c = new ChatDialog(mainFrame, false);
                    map.put("chatDialog", c);
                }
                try {
                    for (Message message : msgList) {
                        Member member = null;
                        Group group = null;
                        long id = -2;
                        if (message.getMember() != null) {
                            member = message.getMember();
                            id = member.getUin();
                        } else if (message.getGroup() != null) {
                            group = message.getGroup();
                            id = group.getId();
                        } else {
                            return;
                        }
                        String key = "messagePrompt" + id;
                        JButton btnPrompt = (JButton) messagePrompt.get(key);
                        if (btnPrompt != null) {
                            messageToolBar.remove(btnPrompt);
                            messageToolBar.validate();
                            messageToolBar.repaint();
                            messagePrompt.remove(key);
                            newMessageFrame.removeButton(key);
                        }
                        
                        c.addChat(message);
                    }
                    
                } catch (Exception ex) {
                    Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        newMessage();
    }
    Thread t = null;
    
    private void newMessage() {
        if (t != null) {
            //t.notify();
            return;
        }
        t = new Thread(new Runnable() {
            
            @Override
            public void run() {
                Icon defIcon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(this.getClass().getClassLoader().getResource("/res/images/icon/icon.png")));
                Icon flashIcon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(this.getClass().getClassLoader().getResource("/res/images/icon/flash.gif")));
                Icon[] icons = new Icon[]{defIcon, flashIcon};
                
                int current = 0;
                while (true) {
                    if (messagePrompt.isEmpty()) {
                        stopFlash(this);
                        btnNewMessage.setIcon(icons[0]);
                    }
                    btnNewMessage.setIcon(icons[current]);
                    current++;
                    if (current == icons.length) {
                        current = 0;
                    }
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
        });
        
        if (t != null) {
            //t.start();
            t.start();
        }
    }
    
    private synchronized void stopFlash(Runnable r) {
        if (r != null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MainPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void removeButton(String key) {
        JButton btnPrompt = (JButton) messagePrompt.get(key);
        if (btnPrompt != null) {
            messageToolBar.remove(btnPrompt);
            messageToolBar.validate();
            messageToolBar.repaint();
            messagePrompt.remove(key);
        }
    }
}

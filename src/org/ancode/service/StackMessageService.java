/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ancode.service;

import java.util.*;
import org.ancode.model.Group;
import org.ancode.model.Member;
import org.ancode.model.Message;
import org.ancode.ui.BaseChatPanel;
import org.ancode.ui.ChatDialog;
import org.ancode.ui.MainPanel;
import org.ancode.ui.MainFrame;
import org.ancode.util.AePlayWave;
import org.ancode.util.Log;


public class StackMessageService {

    private static StackMessageService stackMessageService = null;
    private MainFrame mainFrame = MainFrame.getMainFrame();
    private Map map = mainFrame.getMap();
    private Map<String, List<Message>> messageMaps = null;
    private Member member = null;
    private Group group = null;

    public Map<String, List<Message>> getMessageMaps() {
        return messageMaps;
    }

    public void setMessageMaps(Map<String, List<Message>> messageMaps) {
        this.messageMaps = messageMaps;
    }

    private StackMessageService() {
    }

    public static StackMessageService getIntance() {
        if (stackMessageService == null) {
            stackMessageService = new StackMessageService();
        }
        return stackMessageService;
    }

    public synchronized List<Message> pop(long uin) {
        if (messageMaps == null) {
            messageMaps = new HashMap<String, List<Message>>();
        }
        String key = "msg_" + uin;
        List<Message> msgList = messageMaps.get(key);
        messageMaps.remove(key);
        return msgList;
    }

    public synchronized void push(Message message) throws Exception {
        if (messageMaps == null) {
            messageMaps = new HashMap<String, List<Message>>();
        }
        
        long id = -2;
        if(message.getMember() != null) {
            member = message.getMember();
            id = member.getUin();
        } else if(message.getGroup() != null){
            group = message.getGroup();
            id = group.getId();
        }else{
            return;
        }
        
        String key = "msg_" + id;
        List<Message> msgList = messageMaps.get(key);
        if (msgList == null) {
            msgList = new ArrayList<Message>();
            msgList.add(message);
        } else {
            msgList.add(message);
        }
        messageMaps.put(key, msgList);

        
        AePlayWave.play(AePlayWave.AUDIO_MSG);

        
        if (isOpenedTab(message)) {
            return;
        }

        MainPanel mainPanel = (MainPanel) map.get("mainPanel");
        mainPanel.addMessagePrompt(msgList);
    }

    public synchronized boolean isOpenedTab(Message message) throws Exception {
        long id = -2;
        if(message.getMember() != null) {
            member = message.getMember();
            id = member.getUin();
        } else if(message.getGroup() != null){
            group = message.getGroup();
            id = group.getId();
        }else{
            return true;
        }
        
        ChatDialog chatDialog = (ChatDialog) map.get("chatDialog");
        if (chatDialog != null) {
            String key = "tab_" + id;
            BaseChatPanel c = (BaseChatPanel) chatDialog.getTabMaps().get(key);
            if (c != null) {
                chatDialog.addChat(message);
                return true;
            }
        }

        return false;
    }

    public synchronized int getCount(long uin) {
        String key = "msg_" + uin;
        List<Message> msgList = messageMaps.get(key);
        if (msgList == null) {
            return 0;
        }
        return msgList.size();
    }
}

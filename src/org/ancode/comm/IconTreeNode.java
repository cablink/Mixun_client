package org.ancode.comm;


import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import org.ancode.model.Group;
import org.ancode.model.Member;

public class IconTreeNode extends DefaultMutableTreeNode implements Serializable {

    private static final long serialVersionUID = -8044774991176225332L;
    protected Icon icon;
    protected Member member;
    protected Group group;

    public IconTreeNode(Member member) throws Exception {
        this(member, true);
    }

    public IconTreeNode(Member member, boolean allowsChildren) throws Exception {
        super(member, allowsChildren);
        this.member = member;
        this.icon = member.getFace();
    }
    
    public IconTreeNode(Group group) throws Exception {
        this(group, true);
    }

    public IconTreeNode(Group group, boolean allowsChildren) throws Exception {
        super(group, allowsChildren);
        this.group = group;
        this.icon = group.getFace();
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Icon getIcon() {
        return icon;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
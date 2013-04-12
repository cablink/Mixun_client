/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ancode.model;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.ancode.service.MemberService;


public class Member implements Serializable {

    private long uin = -2;
    private String status = "offline";
    private String account;     
    private String password;    
    private long loginDate;     
    private String nickname;     
    private String markname;    
    private String lnick;      
    private ImageIcon face;       
    private String level;          
    private String gender;      
    private Date birthday;      
    private String phone;        
    private String mobile;      
    private String email;       
    private String college;     
    private int regTime;    
    private int constel;     
    private int blood;      
    private String homepage;    
    private int stat;        
    private int vipInfo;   
    private String country;    
    private String province;    
    private String city;        
    private String personal;    
    private String occupation;  
    private int chineseZodiac;  
    private int allow;       
    private int client_type; 
    private int flag;
    private Category category;
    private int cip;
    private boolean isDownloadInfo;

    public Member(String nickname, ImageIcon face) {
        this.nickname = nickname;
        this.face = face;
    }

    public Member(String nickname) {
        this.nickname = nickname;
    }

    public Member() {
    }

    public boolean isIsDownloadInfo() {
        return isDownloadInfo;
    }

    public void setIsDownloadInfo(boolean isDownloadInfo) {
        this.isDownloadInfo = isDownloadInfo;
    }

    public long getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(long loginDate) {
        this.loginDate = loginDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarkname() {
        return markname;
    }

    public void setMarkname(String markname) {
        this.markname = markname;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getLnick() {
        return lnick;
    }

    public void setLnick(String lnick) {
        this.lnick = lnick;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAllow() {
        return allow;
    }

    public void setAllow(int allow) {
        this.allow = allow;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getChineseZodiac() {
        return chineseZodiac;
    }

    public void setChineseZodiac(int chineseZodiac) {
        this.chineseZodiac = chineseZodiac;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getClient_type() {
        return client_type;
    }

    public void setClient_type(int client_type) {
        this.client_type = client_type;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getConstel() {
        return constel;
    }

    public void setConstel(int constel) {
        this.constel = constel;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ImageIcon getFace() {
        if (this != null && this.face == null) {
            try {
                this.face = MemberService.getInstance().loadDefFace(this);
                //this.face = new ImageIcon(this.getClass().getClassLoader().getResource("iqq/res/images/face.png"));
            } catch (Exception ex) {
                Logger.getLogger(Member.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.face;
    }

    public void setFace(ImageIcon face) {
        this.face = face;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getRegTime() {
        return regTime;
    }

    public void setRegTime(int regTime) {
        this.regTime = regTime;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public long getUin() {
        return uin;
    }

    public void setUin(long uin) {
        this.uin = uin;
    }

    public int getVipInfo() {
        return vipInfo;
    }

    public void setVipInfo(int vipInfo) {
        this.vipInfo = vipInfo;
    }

    public int getCip() {
        return cip;
    }

    public void setCip(int cip) {
        this.cip = cip;
    }

    @Override
    public String toString() {
        String name = this.getNickname();
        if (this.getMarkname() != null && !this.getMarkname().equals("")) {
            name = this.getMarkname() + "(" + name + ")";
        }
        String vip = "<html><strong><font color='red'>:name</font></strong></html>";
        
        return name;
    }

    @Override
    public int hashCode() {
        return (int)this.getUin();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(this == obj) {
            return true;
        }
        
        if(obj instanceof Member) {
            Member m = (Member)obj;
            if(m.getUin() == this.getUin()) {
                return true;
            }
        }
        return false;
    }
    
}

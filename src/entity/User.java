package entity;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import utils.Commons;

public class User {
  private String phoneUniqueCode = "000000000000000";       //那个什么码
  private String userName = "18739482844";                  //用户名
  private String password = "123456";                       //密码
  public String getPhoneUniqueCode() {
    return phoneUniqueCode;
  }
  public void setPhoneUniqueCode(String phoneUniqueCode) {
    this.phoneUniqueCode = phoneUniqueCode;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  
  public byte[] toByteArray(){
    String codingName = "UTF-8";
    byte[] bts4 = new byte[0];
    try {
      byte[] bts1 = Arrays.copyOf(this.getPhoneUniqueCode().getBytes(codingName),Commons.phoneUniqueCodeLen);
      byte[] bts2 = Arrays.copyOf(this.getUserName().getBytes(codingName),Commons.userNameLen);
      byte[] bts3 = Arrays.copyOf(this.getPassword().getBytes(codingName),Commons.passwordLen);
      bts4 = new byte[bts1.length+bts2.length+bts3.length];
      System.arraycopy(bts1, 0, bts4, 0, Commons.phoneUniqueCodeLen);
      System.arraycopy(bts2, 0, bts4, Commons.phoneUniqueCodeLen, Commons.userNameLen);
      System.arraycopy(bts3, 0, bts4, (Commons.phoneUniqueCodeLen + Commons.userNameLen), Commons.passwordLen);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return bts4;
  }
  public static  User toUser(byte[] bts){
    System.out.println(bts.length+"========================"+Arrays.toString(bts));
    String codingName = "UTF-8";
    User user = new User();
    try {
      user.setPhoneUniqueCode(new String(Arrays.copyOf(bts,Commons.phoneUniqueCodeLen),codingName).trim());
      user.setUserName(new String(Arrays.copyOfRange(bts, Commons.phoneUniqueCodeLen, Commons.userNameLen+Commons.phoneUniqueCodeLen),codingName).trim());
      user.setPassword(new String(Arrays.copyOfRange(bts, Commons.userNameLen+Commons.phoneUniqueCodeLen, Commons.userNameLen+Commons.phoneUniqueCodeLen+Commons.passwordLen),codingName).trim());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return user;
  }
  @Override
  public String toString() {
    return "User [phoneUniqueCode=" + phoneUniqueCode + ", userName=" + userName + ", password="
        + password + "]";
  }
  
}

package entity;

import java.util.Arrays;

import utils.Tool;

public class TransferBody {

  private int len = 0;          //传输信息长度
  private byte version = 1;     //版本
  private byte command = 1;     //命令
  private byte reserve = 0;     //备注
  private byte[] content = new byte[0];
  
  public byte[] getContent() {
    return content;
  }
  public void setContent(byte[] content) {
    this.len = content.length;
    this.content = content;
  }
  public int getLen() {
    return len;
  }
  public void setLen(int len) {
    this.len = len;
  }
 
  public byte getVersion() {
    return version;
  }
  public void setVersion(byte version) {
    this.version = version;
  }
  public byte getCommand() {
    return command;
  }
  public void setCommand(byte command) {
    this.command = command;
  }
 
  public byte getReserve() {
    return reserve;
  }
  public void setReserve(byte reserve) {
    this.reserve = reserve;
  }
  public byte[] toByteArray() {
    byte[] head = Tool.intToByteArray1(len);
    byte[] header = Arrays.copyOf(head, 7);
    header[4] = this.version;
    header[5] = this.command;
    header[6] = this.reserve;
    return header;
  }
}

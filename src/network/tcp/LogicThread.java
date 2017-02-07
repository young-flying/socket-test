package network.tcp;

import java.io.IOException;
import java.net.Socket;

import entity.TransferBody;
import entity.User;
import utils.Commons;
import utils.Tool;

/**
 * 服务器端逻辑线程
 */
public class LogicThread extends Thread {
  Socket socket;
  public LogicThread(Socket socket) {
    this.socket = socket;
    System.out.println("start--------------");
    start(); // 启动线程
  }

  public void run() {
    try {
      
      while (isConnected(socket)) {
        // 读取数据
        TransferBody body  = Tool.read(socket);
        if(null != body) {
          switch (body.getCommand()) {
            case 1:
              login(body, socket);
              break;
            case 2:
              exit(body, socket);
              break;
            case 3:
              
              break;
            case 4:
              
              break;

            default:
              break;
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      close();
    }
  }

  /**
   * 关闭流和连接
   */
  private void close() {
    try {
      socket.close();
      System.out.println("server close socket ---");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  private boolean isConnected(Socket socket) {
    boolean b = true;
    try{
      socket.sendUrgentData(0xFF);
    }catch(Exception ex){
        b = false;
    }
    return b;
  }
  
  private void login(TransferBody body,Socket socket) {
    User user = User.toUser(body.getContent());
    byte[] content = new byte[1];
    body.setContent(content);
    try {
      if(user == null) {
        content[0] = (byte)255;
        Tool.write(socket, body);
        return;
      }
      String userName = user.getUserName();
      String imeiCode = user.getPhoneUniqueCode();
      String password = user.getPassword();
      if(Commons.registerQueue.contains(userName)) {
        content[0] = 9;
        Tool.write(socket, body);
        return;
      }
      if(!Commons.imeiUserMap.containsKey(imeiCode)) {
        content[0] = (byte)2;
        Tool.write(socket, body);
        return;
      }
      if(!Commons.userMap.containsKey(userName)) {
        content[0] = (byte)3;
        Tool.write(socket, body);
        return ;
      }
      if(!Commons.imeiUserMap.get(imeiCode).equals(userName)) {
        content[0] = (byte)4;
        Tool.write(socket, body);
        return ;
      }
      if(Commons.userMap.get(userName).equals(password)) {
        Commons.registerQueue.add(userName);
        content[0] = (byte)0;
      }else {
        content[0] = (byte)1;
      }
      Tool.write(socket, body);
      return ;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void exit(TransferBody body,Socket socket) {
    User user = User.toUser(body.getContent());
    byte[] content = new byte[1];
    body.setContent(content);
    try {
      content[0] = (byte)255;
      if(user == null) {
        Tool.write(socket, body);
        return;
      }
      String userName = user.getUserName();
      String password = user.getPassword();
      if(Commons.userMap.get(userName).equals(password)) {
        Commons.registerQueue.remove(userName);
        content[0] = (byte)0;
      }else {
        content[0] = (byte)1;
      }
      Tool.write(socket, body);
      return ;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}

package network.tcp;


import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import entity.TransferBody;
import entity.User;
import utils.Commons.ExitResType;
import utils.Commons.RegisterResType;
import utils.Tool;

/**
 * 复用连接的Socket客户端 功能为：发送字符串“Hello”到服务器端，并打印出服务器端的反馈
 */
public class MulSocketClient {
  private static Socket socket;
  static Scanner in = new Scanner(System.in);
  public static void main(String[] args) {
    // 服务器端IP地址
    String serverIP = "127.0.0.1";
    // 服务器端端口号
    int port = 10000;
    // 发送内容
    try {
      // 建立连接
      socket = new Socket(serverIP, port);
      // 初始化流
      // 发送数据
      /*User user = new User();
      user.setUserName("adminadmin");
      user.setPassword("1234567890");
      user.setPhoneUniqueCode("9364635efhd34h5");
      TransferBody body = new TransferBody();
      body.setContent(user.toByteArray());
      Tool.write(socket, body);*/
      work();
      
    } catch (Exception e) {
      e.printStackTrace(); // 打印异常信息
    } finally {
      try {
        // 关闭流和连接
        socket.close();
        System.out.println("close socket");
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    }
  }
  
  public static void work() {
    int res = 0;
    TransferBody body = new TransferBody();
    try {
      i:while(true) {
        System.out.println("1：登陆,2:退出,3:工作模式切换");
        res = Integer.parseInt(in.nextLine());
        switch (res) {
          case 1:
            RegisterResType resType = loginModel(body);
            System.out.println("反馈---"+resType.getName());
            break;
          case 2:
            body.setCommand((byte)2);
            ExitResType eresType = exit(body);
            System.out.println("反馈---"+eresType.getName());
            if(ExitResType.SUCCESS == eresType) {
              break i;
            }
            break;
          case 3:
            
            break;
          default:
            break;
        }
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public static RegisterResType loginModel(TransferBody body) throws IOException {
    System.out.println("请输入IMEI号");
    String phoneUniqueCode = in.nextLine();
    System.out.println("请输入手机号");
    String phoneNum = in.nextLine();
    System.out.println("请输入密码");
    String password = in.nextLine();
    User user = new User();
    user.setUserName(phoneNum);
    user.setPassword(password);
    user.setPhoneUniqueCode(phoneUniqueCode);
    body.setContent(user.toByteArray());
    Tool.write(socket, body);
    // 接收数据
    // 输出反馈数据
    TransferBody resBody = Tool.read(socket);
    RegisterResType resType = RegisterResType.OTHER;
    if(null != resBody && resBody.getContent().length > 0) {
      resType = RegisterResType.of(resBody.getContent()[0]);
    }
    return resType;
  }
  public static ExitResType exit(TransferBody body) throws IOException {
    System.out.println("请输入IMEI号");
    String phoneUniqueCode = in.nextLine();
    System.out.println("请输入手机号");
    String phoneNum = in.nextLine();
    System.out.println("请输入密码");
    String password = in.nextLine();
    User user = new User();
    user.setUserName(phoneNum);
    user.setPassword(password);
    user.setPhoneUniqueCode(phoneUniqueCode);
    body.setContent(user.toByteArray());
    Tool.write(socket, body);
    // 接收数据
    // 输出反馈数据
    TransferBody resBody = Tool.read(socket);
    ExitResType resType = ExitResType.OTHER;
    if(null != resBody && resBody.getContent().length > 0) {
      resType = ExitResType.of(resBody.getContent()[0]);
    }
    return resType;
  }
}

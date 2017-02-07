package utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;

import entity.TransferBody;

public class Tool {
  public static TransferBody read(Socket socket) throws IOException {
    InputStream bis = socket.getInputStream();
    // 包头
    byte[] head = new byte[4];
    bis.read(head);
    int version = bis.read();
    int command = bis.read();
    int reserve = bis.read();
    System.out.println(byteArrayToInt(head)+"-"+version+"-"+command+"-"+reserve);
    byte[] data = new byte[Tool.byteArrayToInt(head)];
    
    // 包体
    bis.read(data);
    TransferBody body = new TransferBody();
    body.setVersion((byte)version);
    body.setCommand((byte)command);
    body.setReserve((byte)reserve);
    body.setContent(data);
    System.out.println("read data:"+Arrays.toString(data));
    return body;

  }

  public static void write(Socket socket,TransferBody body) throws IOException {
    // 包头,固定4个字节,包含包体长度信息
    System.out.println("write data length:"+body.getLen()+"----------------");
    byte[] header = body.toByteArray();
    BufferedOutputStream bis = new BufferedOutputStream(socket.getOutputStream());
    bis.write(header);
    // 包体
    bis.write(body.getContent());
    bis.flush();
  }

  // int 转字节数组
  public static byte[] intToByteArray1(int i) {
    byte[] result = new byte[4];
    result[0] = (byte) ((i >> 24) & 0xFF);
    result[1] = (byte) ((i >> 16) & 0xFF);
    result[2] = (byte) ((i >> 8) & 0xFF);
    result[3] = (byte) (i & 0xFF);
    return result;
  }

  public static byte[] intToByteArray2(int i) throws Exception {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(buf);
    out.writeInt(i);
    byte[] b = buf.toByteArray();
    out.close();
    buf.close();
    return b;
  }

  // 字节数组转int
  public static int byteArrayToInt(byte[] b) {
    int intValue = 0;
    for (int i = 0; i < b.length; i++) {
      intValue += (b[i] & 0xFF) << (8 * (3 - i));
    }
    return intValue;
  }
}

package utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Commons {
  public static int phoneUniqueCodeLen = 15;
  public static int userNameLen = 11;
  public static int passwordLen = 16;
  
  public static Map<String,String> userMap = new HashMap<>();
  public static Map<String,String> imeiUserMap = new HashMap<>();
  public static Set<String> registerQueue = new HashSet<>();
  
  static {
    imeiUserMap.put("1234567890", "18201021234");
    userMap.put("18201021234", "123456");
  }
  
  public static enum RegisterResType{
    SUCCESS((byte)0,"注册成功"),PASSWORDERROR((byte)1,"密码错误"),IMEINOTREGISTER((byte)2,"IMEI未注册"),PHONENOTREGISTER((byte)3,"手机号未注册"),IMEINOTMATCH((byte)4,"IMEI 与手机号不匹配"),REGISTEREXIT((byte)9,"已注册"),OTHER((byte)255,"其它");
    private RegisterResType(byte code,String name){
      this.code = code;
      this.name = name;
    }
    private final byte code;
    private final String name;
    public byte getCode() {
      return code;
    }
    public String getName() {
      return name;
    }
    public static RegisterResType of(byte code) {
      for(RegisterResType t : RegisterResType.values()){
        if(code == t.code){
          return t;
        }
      }
      return OTHER;
    }
    public static String getName(byte code) {
      return of(code).getName();
    }
  }
  public static enum ExitResType{
    SUCCESS((byte)0,"退出成功"),PASSWORDERROR((byte)1,"密码错误"),OTHER((byte)255,"其它");
    private ExitResType(byte code,String name){
      this.code = code;
      this.name = name;
    }
    private final byte code;
    private final String name;
    public byte getCode() {
      return code;
    }
    public String getName() {
      return name;
    }
    public static ExitResType of(byte code) {
      for(ExitResType t : ExitResType.values()){
        if(code == t.code){
          return t;
        }
      }
      return OTHER;
    }
    public String getName(byte code) {
      return of(code).getName();
    }
  }
  public static enum SwitchModelType{
    SUCCESS((byte)0,"切换成功"),PASSWORDERROR((byte)1,"切换失败"),OTHER((byte)255,"其它");
    private SwitchModelType(byte code,String name){
      this.code = code;
      this.name = name;
    }
    private final byte code;
    private final String name;
    public byte getCode() {
      return code;
    }
    public String getName() {
      return name;
    }
    public static SwitchModelType of(byte code) {
      for(SwitchModelType t : SwitchModelType.values()){
        if(code == t.code){
          return t;
        }
      }
      return OTHER;
    }
    public String getName(byte code) {
      return of(code).getName();
    }
  }
}

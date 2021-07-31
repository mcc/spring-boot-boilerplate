package mcc.springbootboilerplate;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class ServerConfig {
    public static String serverName;
    public static String hostName;
    public static String ipAddress;

    static {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            hostName = ip.getHostName();
            ipAddress = ip.getHostAddress();
            Properties properties = System.getProperties();
            for(String propertyName: properties.stringPropertyNames()){
                if (propertyName!=null && propertyName.startsWith("[Server")){
                    try {
                        serverName = propertyName.substring(8, propertyName.length() - 1);
                    } catch (Exception ex) {
                        serverName = propertyName;
                        System.out.println("Invalid Server Name : " + propertyName );
                    }
                    break;
                }
            }
            if (serverName == null){
                serverName = "unknown";
            }
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostName);
            System.out.println("Your current serverName : " + serverName);

        } catch (UnknownHostException e) {

            e.printStackTrace();
        }

    }
}

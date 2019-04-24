package cn.onlov.tx.util;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Properties;

@Component
public class TXConfigProperties {
    // oss
    public static String oss_secretId;
    public static String oss_secretKey;
    public static String oss_bucketName;
    public static String oss_region;

    public static String msg_appid;
    public static String msg_appkey;

    public static String msg_login_id;
    public static String msg_register_id;
    public static String msg_modifypw_id;

    static {
        try {
            Properties props = new Properties();
            props.load(new InputStreamReader(
                    TXConfigProperties.class.getClassLoader().getResourceAsStream("application.properties"),
                    "UTF-8"));
            String profile = props.getProperty("spring.profiles.active");
            String envFile= "tx-config-" + profile + ".properties";

            Properties envProps = new Properties();
            envProps.load(new InputStreamReader(TXConfigProperties.class.getClassLoader().getResourceAsStream(envFile), "UTF-8"));
            Field[] fields = TXConfigProperties.class.getFields();
            for (Field field : fields) {
                String name = field.getName();
                String value = envProps.getProperty("qcloud."+name);
                field.set(name,value);
            }
            System.out.println(props.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


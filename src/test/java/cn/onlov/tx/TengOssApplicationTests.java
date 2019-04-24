package cn.onlov.tx;

import cn.onlov.tx.msg.MsgUtil;
import cn.onlov.tx.oss.OssUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TengOssApplicationTests {

    @Test
    public void contextLoads() {
        String filePath ="e:/2222.xls" ;
        try {
           File file = new File(filePath);
            OssUtil.uploadFile(file,true);
            OssUtil.uploadFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test // 测试下载流或者下载文件到指定路径
    public void  downLoadOss (){
        String key ="home/upload/2222.xls" ;
        //
        InputStream is = OssUtil.downLoadOss(key);

        OssUtil.downLoadOss(key,"d:/a.xls");

    }

    @Test
    public void testMsg(){
        int sendTypeRegister = 865;
        int sendTypeLogin = 866;
        int sendTypeModify = 867;

        String phone = "18500418411";
//        MsgUtil.sendMsg(phone,sendTypeLogin,new String[]{"3333","3"},"86");
//        MsgUtil.sendMsg(phone,sendTypeRegister,new String[]{"444","3"},null);
        MsgUtil.sendMsg(phone,sendTypeModify,new String[]{"3333","3"},null);

    }

}

package cn.onlov.tx.msg;

import cn.onlov.tx.util.TXConfigProperties;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.json.JSONException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MsgUtil {
    private static SmsSingleSender ssender;
    public static final String SMSSIGN = "onlov网";

    public MsgUtil() {
        if (ssender == null) {
            ssender = this.createSsender();
        }

    }

    private SmsSingleSender createSsender() {
        String appid = TXConfigProperties.msg_appid;
        String appkey = TXConfigProperties.msg_appkey;
        return  new SmsSingleSender(Integer.parseInt(appid), appkey);
    }


    /**
     * @param phoneNumber 电话号码 如果不传国家编号 默认为中国 86
     * @param sendType    : //# 865 注册验证码 : // #866 登陆验证码  // # 867修改登陆密码验证码
     * @param params 数组具体的元素个数和模板中变量个数必须一致，例如示例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
     *
     */
    public static void sendMsg(String phoneNumber,int sendType ,String [] params,String nationCode){
        if(nationCode==null){
            nationCode ="86" ;
        }
        int templateId = 0;
        switch (sendType){
            case  865 : //# 注册验证码
                templateId = Integer.parseInt(TXConfigProperties.msg_register_id.trim());
                break;
            case 866: // # 登陆验证码
                templateId = Integer.parseInt(TXConfigProperties.msg_login_id.trim());
                break;
            case 867: // # 修改登陆密码验证码
                templateId = Integer.parseInt(TXConfigProperties.msg_modifypw_id.trim());
                break;
        }
        sendMsg(nationCode,phoneNumber,templateId,params);
    }



    /**
     * @param nationCode 国家编号
     * @param phoneNumber 电话号码
     * @param templateId   这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
     * @param params 数组具体的元素个数和模板中变量个数必须一致，例如示例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
     *
     */
    private static void sendMsg(String nationCode ,String phoneNumber,int templateId,String [] params){
        try {
            SmsSingleSenderResult result = ssender.sendWithParam(nationCode, phoneNumber, templateId, params, SMSSIGN, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信

            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }
    }

}

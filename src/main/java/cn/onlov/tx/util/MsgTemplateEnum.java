package cn.onlov.tx.util;

public enum  MsgTemplateEnum {
    REGISTERCODE(865,"注册验证码 "),
    LOGINCODE(866,"登陆验证码"),
    MODIFYPW(867,"修改登陆密码验证码");

    private Integer code;
    private String codeDescr;


    public void setCode(Integer code) {
        this.code = code;
    }

    public void setCodeDescr(String codeDescr) {
        this.codeDescr = codeDescr;
    }


    public Integer getCode() {
        return code;
    }

    public String getDescr() {
        return codeDescr;
    }


    private MsgTemplateEnum(Integer code, String codeDescr) {
        this.code = code;
        this.codeDescr = codeDescr;
    }

    public static String getInstance(Integer code) {
        for (MsgTemplateEnum o : MsgTemplateEnum.values()) {
            if (o.getCode()==code) {
                return o.getDescr();
            }
        }
        return null;
    }

}

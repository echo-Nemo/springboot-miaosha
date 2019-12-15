package com.echo.miaoshaship.error;

public interface CommomError {
    /*
    该接口中第一errMsg,errCode的方法
     */
    public int getErrCode();

    public String getMsg();

    //自定义错误消息
    public CommomError setErrMsg(String errMsg);
}

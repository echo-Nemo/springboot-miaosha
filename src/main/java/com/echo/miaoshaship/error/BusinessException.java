package com.echo.miaoshaship.error;

public class BusinessException extends Exception implements CommomError {

    private CommomError commomError;

    public BusinessException(CommomError commomError) {
        super();//继承异常类中的信息
        this.commomError = commomError;
    }

    //可以自定义errMsg的方式构造业务异常
    public BusinessException(CommomError commomError, String errMsg) {
        super();
        this.commomError = commomError;
        this.commomError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commomError.getErrCode();
    }

    @Override
    public String getMsg() {
        return this.commomError.getMsg();
    }

    @Override
    public CommomError setErrMsg(String errMsg) {
        this.commomError.setErrMsg(errMsg);
        return this;
    }
}

package com.echo.miaoshaship.response;

/*
返回给前端的通用格式
 */
public class CommonReturnType {
    //请求的返回结果 "success","fail"
    private String status;
    //返回给前端的数据
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    //定义一个通用的创建方法
    public static CommonReturnType create(Object result) {
        return create(result, "success"); //成功默认status为success
    }

    //对方法的重载
    public static CommonReturnType create(Object result, String status) {
        CommonReturnType commonReturnType = new CommonReturnType();
        commonReturnType.setStatus(status);
        commonReturnType.setData(result);
        return commonReturnType;
    }
}

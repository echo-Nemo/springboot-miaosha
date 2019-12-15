package com.echo.miaoshaship.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {
    //校验结果是否有错
    private boolean hasErroes = false;
    //存放错误的信息
    private Map<String, String> errorMsgMap = new HashMap<>();

    //实现通用的格式化字符串星系获取错误
    public String getErrMsg() {
        return StringUtils.join(errorMsgMap.values().toArray(), ",");
    }

    public boolean isHasErroes() {
        return hasErroes;
    }

    public void setHasErroes(boolean hasErroes) {
        this.hasErroes = hasErroes;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }
}

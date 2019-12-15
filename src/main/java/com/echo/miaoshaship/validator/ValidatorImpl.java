package com.echo.miaoshaship.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidatorImpl implements InitializingBean {
    private Validator validator;

    public ValidationResult validate(Object bean) {

        ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);

        //集合大于0 说明该校验的字段有错
        if (constraintViolationSet.size() > 0) {
            validationResult.setHasErroes(true);
            //将map集合中的错误遍历
            constraintViolationSet.forEach(objectConstraintViolation -> {
                String propertyName = objectConstraintViolation.getPropertyPath().toString();
                String errMsg = objectConstraintViolation.getMessage();
                validationResult.getErrorMsgMap().put(propertyName, errMsg);
            });
        }
        return validationResult;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //通过工厂的方式使得validator实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();

    }
}

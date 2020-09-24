package io.stacs.dapp.helloworld.common;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Huang Shengli
 * @date 2020-09-13
 */
@ResponseBody
@ControllerAdvice
public class ControllerExceptionHandler {
    /**
     * controller exception handler
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public DrsResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {

        String retMsg = "";
        if (null != e.getBindingResult() && null != e.getBindingResult().getFieldError()) {
            FieldError fieldError = e.getBindingResult().getFieldError();
            retMsg = fieldError.getField() + " " + fieldError.getDefaultMessage();
        }
        return DrsResponse.fail("error", retMsg);
    }

    /**
     * Spring Assert 校验参数异常检查
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public DrsResponse illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return DrsResponse.fail("error", e.getMessage());
    }

}

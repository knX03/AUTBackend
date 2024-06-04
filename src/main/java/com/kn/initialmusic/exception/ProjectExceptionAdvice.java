package com.kn.initialmusic.exception;


import com.kn.initialmusic.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//必须被SpringMVC容器加载
public class ProjectExceptionAdvice {

    @ExceptionHandler(Exception.class) //想要捕获的异常类
    public Result doException(Exception ex) {
        Result result = new Result();
        result.setCode(500);
        result.setMsg("内部服务器异常！");
        //想要进行的异常处理
        System.out.println(ex);
        return result;
    }

    @ExceptionHandler(NullPointerException.class)
    public Result doNullPointerException(Exception ex) {
        System.out.println(ex);
        Result result = new Result();
        result.setCode(301);
        result.setMsg("关键值为空");
        return result;
    }

}

package org.sjw.sjwrpc.client;

import org.sjw.sjwrpc.client.exception.CommonException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author shijiawei
 * @version SjwRpcClientApp.java, v 0.1
 * @date 2018/8/30
 */
@SpringBootApplication
@ControllerAdvice
public class SjwRpcClientApp {
    public static void main(String[] args) {
        SpringApplication.run(SjwRpcClientApp.class, args);
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @CrossOrigin
    public String defaultExceptionHandler(Exception exception, HttpServletResponse response) throws Exception {
        try {
            throw exception;
        } catch (CommonException e) {
            return e.getMessage();
        }
    }
}

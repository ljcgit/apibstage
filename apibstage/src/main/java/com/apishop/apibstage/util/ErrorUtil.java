package com.apishop.apibstage.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 *  错误信息工具类
 */
public class ErrorUtil {

    private static Logger logger = LoggerFactory.getLogger(ErrorUtil.class);

    public static void printError(BindingResult br){

        List<ObjectError> ls=br.getAllErrors();
        for (int i = 0; i < ls.size(); i++) {
            logger.error("error:"+ls.get(i));
        }
    }
}

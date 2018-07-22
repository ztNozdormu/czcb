package cn.com.czcb.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.alibaba.fastjson.JSON;

import cn.com.czcb.model.session.UserSession;
import cn.com.czcb.pub.AppContext;
import cn.com.czcb.pub.AppException;

/**
 * 基础控制层
 * @author AJiong
 * @version $Id: BaseController.java, v 0.1 2017年11月7日 下午3:45:36 Ajiong Exp $
 */
public class BaseController {
    /**  */
    protected static final String SUCCESS  = "success";
    /**  */
    protected static boolean      logTrace = true;

    /**
     * 通过Spring创建UserSession对象
     * @return CurrentSession
     */
    protected UserSession getCurrentSession() {
        return AppContext.getSpringContext().getBean(UserSession.class);
    }

    /**
     * @return JsonResult
     */
    public static <T> String returnJsonResult(T result) {
        ResultContainer resultContainer = new ResultContainer();
        resultContainer.setResult(result);
        return JSON.toJSONString(resultContainer);
    }

    /**
     * @return JsonResultWithError
     */
    public static <Exp extends Throwable> String returnJsonResultWithError(Exp e) {
        ResultContainer resultContainer = new ResultContainer();
        resultContainer.setExceptionMsg(e.getMessage());
        if (e instanceof AppException) {
            resultContainer.setExceptionCode(((AppException) e).getExceptionCode());
        } else if (e instanceof NullPointerException) {
            resultContainer.setExceptionCode(-1000);
            resultContainer.setExceptionMsg("系统繁忙(-1000)，请稍后重试");
        } else {
            resultContainer.setExceptionCode(1000);
            resultContainer.setExceptionMsg("系统繁忙(1000)，请稍后重试");
        }
        if (logTrace) {
            e.printStackTrace();
        }
        return JSON.toJSONString(resultContainer);
    }

    /**
    *
    */
    public static class ResultContainer {
        /**  */
        private int    exceptionCode;
        /**  */
        private String exceptionMsg;
        /**  */
        private Object result;

        /**
         *
         */
        public ResultContainer() {

        }

        /**
        *
        * @param exceptionCode
        * @param exceptionMsg
        */
        @SuppressWarnings("javadoc")
        public ResultContainer(int exceptionCode, String exceptionMsg) {
            this.exceptionCode = exceptionCode;
            this.exceptionMsg = exceptionMsg;
        }

        /**
         * 
         * @return
         */
        public int getExceptionCode() {
            return exceptionCode;
        }

        public void setExceptionCode(int exceptionCode) {
            this.exceptionCode = exceptionCode;
        }

        /**
        *
        * @return 
        */
        public String getExceptionMsg() {
            return exceptionMsg;
        }

        public void setExceptionMsg(String exceptionMsg) {

            this.exceptionMsg = exceptionMsg;
        }

        public Object getResult() {

            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

    }

    /**
     * @return ResponseEntity
     */
    protected ResponseEntity<byte[]> getResponseEntity(String fileName,
                                                       File file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers,
            HttpStatus.CREATED);
    }
}

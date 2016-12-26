package com.keega.common.interceptor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 *<p>全局异常拦截器</p>
 * @author 刘俊宏
 * @version V1.0
 */
public class ExceptionInterceptor implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse reponse, Object object, Exception ex) {
		
		/** 根据不同错误记录日志信息 **/
		if (ex instanceof NullPointerException) {
			error(object, ex, "【空指针异常】");
		} else if (ex instanceof SQLException) {
			error(object, ex, "【数据库异常】");
		} else if (ex instanceof ClassCastException) {
			error(object, ex, "【类型强制转换错误】");
		} else if (ex instanceof ArrayIndexOutOfBoundsException) {
			error(object, ex, "【数组越界】");
		} else if (ex instanceof StringIndexOutOfBoundsException) {
			error(object, ex, "【字符串索引越界】");
		}else if (ex instanceof IndexOutOfBoundsException) {
			error(object, ex, "【索引越界】");
		} else if (ex instanceof NumberFormatException) {
			error(object, ex, "【字符串转换数字异常】");
		} else if (ex instanceof IOException) {
			error(object, ex, "【输入输出异常】");
		} else if (ex instanceof FileNotFoundException) {
			error(object, ex, "【文件未找到");
		} else if (ex instanceof ArithmeticException) {
			error(object, ex, "算术条件异常。譬如：整数除零等。】");
		} else {
			error(object, ex, "【未知错误】");
		}
		
		/**将异常抛出到前台**/
		String exception = exception2String(ex);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", exception);
		return new ModelAndView("/views/error/500",model);
	}
	
	/**
	 * 将异常转换为字符串，以便前台展示
	 * @param ex
	 * @return 异常字符串
	 */
	private String exception2String(Exception ex){
		StringWriter swrite = new StringWriter();
	    PrintWriter pwrite = new PrintWriter(swrite, true);
	    ex.printStackTrace(pwrite);
	    pwrite.flush();
	    swrite.flush();
	    return swrite.toString();
	}
	
	/**
	 * 日志输出
	 * @param object
	 * @param ex
	 * @param error
	 */
	private void error(Object object,Exception ex,String error){
		Logger logger = LogManager.getLogger(object);
		
		StringBuffer errorMessage = new StringBuffer();
		errorMessage.append("\n");
		errorMessage.append("Request:" + object);
		errorMessage.append("\n");
		errorMessage.append("Exception: " + error);
		errorMessage.append("，异常信息如下：");
		logger.error(errorMessage,ex);
	}

}

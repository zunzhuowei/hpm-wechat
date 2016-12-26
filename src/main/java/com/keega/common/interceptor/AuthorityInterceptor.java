package com.keega.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 *<p>全局用户认证拦截器</p>
 * @author 刘俊宏
 * @version V1.0
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			Authority authPassport = ((HandlerMethod) handler).getMethodAnnotation(Authority.class);
			
			//如果值为false，则直接返回true
			/*if (authPassport == null || authPassport.validateUser() == false){
				return true;
			}*/
			
			//校验用户
			/*User ouser = null;
			if (ouser == null) {
				response.sendRedirect("../login");
				return false;
			}*/
		}
		return true;
		//return false;
	}

}

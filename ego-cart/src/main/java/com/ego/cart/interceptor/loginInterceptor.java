package com.ego.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;

public class loginInterceptor implements HandlerInterceptor{

//  拦截器中软编码无法生效
//	@Value("${passport.url}")
//	private String passportUrl;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		if (null!=token&&!"".equals(token)) {
			String json = HttpClientUtil.doPost("http://localhost:8084/user/token/"+token);
			EgoResult er=JsonUtils.jsonToPojo(json, EgoResult.class);
			if (er.getStatus()==200) {
				return true;
			}
		}
		HandlerMethod method=(HandlerMethod)handler;
		System.out.println(request.getRequestURI());
		System.out.println(request.getRequestURL());
		System.out.println(request.getRemoteAddr());
		System.out.println(request.getLocalAddr());
		String num=request.getParameter("num");
		
		response.sendRedirect("http://localhost:8084/user/showLogin?interurl="+request.getRequestURL()+"%3Fnum="+num);//重定向是Get请求
//		StringBuffer requestURL = request.getRequestURL();
//		System.out.println(requestURL);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}

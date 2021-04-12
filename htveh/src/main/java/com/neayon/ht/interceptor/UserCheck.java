package com.neayon.ht.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.neayon.ht.entity.User;
import com.neayon.ht.manager.UserManager;

import net.sf.json.JSONObject;

/**
 * 登录校验和权限校验
 * 
 * @author kamiyj
 *
 */
public class UserCheck implements HandlerInterceptor {
	@Resource(name = "UserManager")
	private UserManager um;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User user = um.getUserStored();
		if (user != null) {
			String funcpoints = user.getFunctionPoint();
			if (funcpoints == null || funcpoints == "") {
				// 无设置的，认为是均可通行
				return true;
			}

			try {
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				Class<?> controller = handlerMethod.getBeanType();
				if (controller.isAnnotationPresent(RequestMapping.class)) {
					RequestMapping rmc = (RequestMapping) controller.getAnnotation(RequestMapping.class);
					Method method = handlerMethod.getMethod();
					if (method.isAnnotationPresent(RequestMapping.class)) {
						RequestMapping rmm = (RequestMapping) method.getAnnotation(RequestMapping.class);
						String authCode = rmc.value()[0] + "." + rmm.value()[0];
						boolean verify = false;
						String[] auths = funcpoints.split(",");
						for (String auth : auths) {
							if (auth.equals(authCode)) {
								verify = true;
								break;
							}
						}
						um.saveLog(user.getUsername(), authCode, verify + "");
						if (verify) {
							return true;
						} else {
							handleNoAuth(response);
							return false;
						}
					} else {
						// 未设置权限结构的请求块
						return true;
					}
				}
			} catch (ClassCastException e) {
				return true;
			}

		}
		handleNoLogin(response);
		return false;
	}

	private void handleNoLogin(HttpServletResponse response) throws Exception {
//		response.setStatus(405);
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Map<String, String> mes = new HashMap<String, String>();
		mes.put("code", "405");
		mes.put("message", "Please login first");
		response.getWriter().write(JSONObject.fromObject(mes).toString());
		response.getWriter().flush();
	}

	private void handleNoAuth(HttpServletResponse response) throws IOException {
//		response.setStatus(401);
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Map<String, String> mes = new HashMap<String, String>();
		mes.put("code", "401");
		mes.put("message", "Exceeding authority");
		response.getWriter().write(JSONObject.fromObject(mes).toString());
		response.getWriter().flush();
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}

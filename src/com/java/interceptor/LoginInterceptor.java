package com.java.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.java.bean.ErpAccount;


public class LoginInterceptor implements HandlerInterceptor{



	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
			System.out.println("===========ִ��3 after");
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
			System.out.println("===========ִ��2 post");
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
			System.out.println("===========ִ��1 pre");
		
		String uri = request.getRequestURI();
		boolean flag = false;
		if(uri.indexOf("public")>-1){
			flag = true;
			System.out.println("##################### ��public");
		}else if(uri.indexOf("login")>-1||uri.indexOf("Login")>-1){
					flag = true;
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@y�е�¼�Ķ���ִ��");
			  }else {
					ErpAccount erpAccount = (ErpAccount) request.getSession().getAttribute("erpAccount");
					List<String>  doPathList = (List<String>)request.getSession().getAttribute("myDoPathList");
					System.out.println(erpAccount.getUsername()+"<><><><><><><><><><><><>");
					if(erpAccount!=null){
						System.out.println("�������������������������������й���¼  ������");
						System.out.println(uri);
						for(String path:doPathList){
							if(uri.indexOf(path)>-1){
								flag = true;
							}
						}
						
					}else{
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~δ���е�¼ ������");
						request.getRequestDispatcher("/WEB-INF/jsp/public/bigdatalogin.jsp").forward(request, response);
			}
		}
		
		
		return true;
	}

}

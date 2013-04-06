package com.zhm.rabbit.oa.shiro;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class CasCallBackFilter implements Filter {
	
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest)request;
		String url = req.getRequestURI();
		if(url.indexOf("dologin")!=-1)
		{
			chain.doFilter(request, response);
		}
		else
		{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			Object user = session.getAttribute("currUser");
			if(user==null)
			{
				 PrincipalCollection principalCollection = SecurityUtils.getSubject()
				         .getPrincipals();
			     if (principalCollection != null) {    
			    	 List principals = principalCollection.asList();
			    	 // 这里获取到的list有两个元素，
			    	 //一个是cas返回来的用户名，举例是aaa，
			    	 //一个是cas返回的更多属性的map对象，举例是{uid:aaa,username:aaa,email:aaa}
			    	 //通过principals.get(1)来获得属性集合的map对象
			    	 String username =  (String)principals.get(0);
			    	 //根据返回的邮箱/手机号查询用户信息
		    		String cpath = ((HttpServletRequest)request).getContextPath();
		    		session.setAttribute("cpath", cpath);
			     }
			}
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}

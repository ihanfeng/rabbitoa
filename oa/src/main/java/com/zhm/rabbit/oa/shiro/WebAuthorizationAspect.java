package com.zhm.rabbit.oa.shiro;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class WebAuthorizationAspect {
	
    @Before("@target(org.springframework.stereotype.Controller) && @annotation(requiresPermission)")
    public void assertAuthorized(JoinPoint jp, RequiresPermissions requiresPermission) {
        try
		{
			SecurityUtils.getSubject().checkPermissions(requiresPermission.value());
		}
		catch (AuthorizationException e)
		{
			// TODO Auto-generated catch block
			try
			{
				ServletRequestAttributes servletContainer = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		        HttpServletRequest request = servletContainer.getRequest();  
		        HttpServletResponse response =(HttpServletResponse) jp.getArgs()[0];
				response.sendRedirect(request.getContextPath()+"/unauthorized");
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
			}
		}
    }
}
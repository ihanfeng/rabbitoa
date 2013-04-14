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
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.common.collect.Lists;
import com.zhm.rabbit.oa.repositories.DepartmentMenu;
import com.zhm.rabbit.oa.repositories.OaMenu;
import com.zhm.rabbit.oa.repositories.PositionMenu;
import com.zhm.rabbit.oa.repositories.UserInfo;
import com.zhm.rabbit.oa.service.DeptMenuService;
import com.zhm.rabbit.oa.service.OaMenuService;
import com.zhm.rabbit.oa.service.PositionMenuService;
import com.zhm.rabbit.oa.service.UserService;
import com.zhm.rabbit.oa.utils.RepeatMenuConversion;
/**
 * 
 * @author zhmlvft
 * cas登陆成功之后回调的方法，设置用户信息到session中。
 * 
 */
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
			    	 String username =  (String)principals.get(0);
			    	 ApplicationContext ac1 =
			    			 WebApplicationContextUtils.getRequiredWebApplicationContext(req.getSession().getServletContext());
			    	 UserService userService = (UserService)ac1.getBean("userService");
			    	 OaMenuService oaMenuService = (OaMenuService)ac1.getBean("oaMenuService");
			    	 UserInfo dbUser = userService.findByUserId(username);
			    	 if("ROLE_ADMIN".equals(dbUser.getRole()))
			    	 {
			    		 //管理员给全部菜单
			    		 List<OaMenu> menus = oaMenuService.findAll();
			    		 dbUser.setMenus(menus);
			    	 }
			    	 else
			    	 {
			    		 DeptMenuService deptMenuService =(DeptMenuService)ac1.getBean("deptMenuService");
				    	 PositionMenuService positionMenuService = (PositionMenuService)ac1.getBean("positionMenuService");
				    	
				    	 List<DepartmentMenu> deptMenus = deptMenuService.findByDeptid(Integer.parseInt(dbUser.getDeptid()));
				         List<PositionMenu> positionMenus = positionMenuService.findByRoleid(Integer.parseInt(dbUser.getPositionid()));
				         List<Integer> menuids = RepeatMenuConversion.getInstance().conversion(deptMenus, positionMenus, null);
				         List<OaMenu> menus = Lists.newArrayList();
				         for(int menuid:menuids)
				         {
				         	menus.add(oaMenuService.findById(menuid));
				         }
				         dbUser.setMenus(menus);
			    	 }
			    	 session.setAttribute("currUser", dbUser);
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

package com.zhm.rabbit.oa.shiro;

import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zhm.rabbit.oa.repositories.DepartmentMenu;
import com.zhm.rabbit.oa.repositories.OaMenu;
import com.zhm.rabbit.oa.repositories.PositionMenu;
import com.zhm.rabbit.oa.repositories.UserInfo;
import com.zhm.rabbit.oa.service.DeptMenuService;
import com.zhm.rabbit.oa.service.OaMenuService;
import com.zhm.rabbit.oa.service.PositionMenuService;
import com.zhm.rabbit.oa.service.UserService;
import com.zhm.rabbit.oa.utils.RepeatMenuConversion;
public class MyCasRealm extends CasRealm {
	@Autowired
	private UserService userService;
	@Autowired
	private DeptMenuService deptMenuService;
	@Autowired
	private PositionMenuService positionMenuService;
	@Autowired
	private OaMenuService oaMenuService;
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		Subject subject = SecurityUtils.getSubject();
		UserInfo sessionUser = (UserInfo)subject.getSession().getAttribute("currUser");
		if(sessionUser==null)
		{
			//session里面没有值就从数据库查询
			if (principals == null)
			{
	            throw new AuthorizationException("PrincipalCollection was null, which should not happen");
			}
	        if (principals.isEmpty())
	        {
	            return null;
	        }
	        if (principals.fromRealm(getName()).size() <= 0)
	        {
	            return null;
	        }
	        String username = (String)principals.fromRealm(getName()).iterator().next();
	        if (username == null)
	        {
	            return null;
	        }
	        UserInfo user = userService.findByUserId(username);
	        if (user == null){
	        	return null;
	        }
	        List<DepartmentMenu> deptMenus = deptMenuService.findByDeptid(Integer.parseInt(user.getDeptid()));
	        List<PositionMenu> positionMenus = positionMenuService.findByRoleid(Integer.parseInt(user.getPositionid()));
	        List<Integer> menuids = RepeatMenuConversion.getInstance().conversion(deptMenus, positionMenus, null);
	        List<OaMenu> menus = Lists.newArrayList();
	        for(int menuid:menuids)
	        {
	        	menus.add(oaMenuService.findById(menuid));
	        }
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			Set<String> permissions = Sets.newHashSet();
			for(OaMenu tmp:menus)
			{
				permissions.add(tmp.getUrl());
			}
			if(!super.getDefaultRoles().equals(user.getRole()))
			{
				info.setRoles(Sets.newHashSet(super.getDefaultRoles(),user.getRole()));
			}
			else
			{
				info.setRoles(Sets.newHashSet(user.getRole()));
			}
			info.setStringPermissions(permissions);
			return info;
		}
		else
		{
			//有值就直接取
			List<OaMenu> menus = sessionUser.getMenus();
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			Set<String> permissions = Sets.newHashSet();
			for(OaMenu tmp:menus)
			{
				permissions.add(tmp.getUrl());
			}
			if(!super.getDefaultRoles().equals(sessionUser.getRole()))
			{
				info.setRoles(Sets.newHashSet(super.getDefaultRoles(),sessionUser.getRole()));
			}
			else
			{
				info.setRoles(Sets.newHashSet(sessionUser.getRole()));
			}
			info.setStringPermissions(permissions);
			return info;
		}
	}
	
	
}

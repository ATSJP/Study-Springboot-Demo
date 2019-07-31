package com.demo.demoshiro.relam;

import com.demo.demoshiro.tools.UserTools;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashSet;
import java.util.Set;

/**
 * 登陆和授权
 *
 * @author sjp 2018/12/9
 */
public class LoginRelam extends AuthorizingRealm {

	private static final String MONSTER = "monster";

	/**
	 * 登陆
	 *
	 * @param authenticationToken token
	 * @return info
	 * @throws AuthenticationException exception
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		String userName = ((UsernamePasswordToken) authenticationToken).getUsername();
		UserTools userTools = UserTools.getUserByName(userName);
		if (userTools == null) {
			throw new UnknownAccountException("用户不存在！");
		}
		if (MONSTER.equals(userTools.getUserName())) {
			throw new LockedAccountException("用户被锁定");
		}
		// 盐值加密，确保凭证一样加密后字符串不一样
		ByteSource credentialsSalt = ByteSource.Util.bytes(userTools.getUserName());
		return new SimpleAuthenticationInfo(userTools.getUserName(), userTools.getUserPassword(), credentialsSalt,
				getName());
	}

	/**
	 * 授权
	 *
	 * @param principalCollection p
	 * @return a
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		Object principal = principalCollection.getPrimaryPrincipal();
		Set<String> roles = new HashSet<>();
		roles.add("user");
		if ("admin".equals(principal)) {
			roles.add("admin");
		}
		return new SimpleAuthorizationInfo(roles);
	}

}

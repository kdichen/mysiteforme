package com.mysiteforme.admin.base;

import com.mysiteforme.admin.realm.AuthRealm.ShiroUser;
import org.apache.shiro.SecurityUtils;

/**
 * 系统用户
 */
public class MySysUser {
    /**
     * 取出Shiro中的当前用户LoginName.
     */
    public static String icon() {
        return ShiroUser().getIcon();
    }

    public static Long id() {
        return ShiroUser().getId();
    }

    public static String loginName() {
        return ShiroUser().getloginName();
    }

    public static String nickName() {
        return ShiroUser().getNickName();
    }

    public static ShiroUser ShiroUser() {
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return user;
    }
}

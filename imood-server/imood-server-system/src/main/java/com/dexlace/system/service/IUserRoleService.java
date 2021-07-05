package com.dexlace.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dexlace.common.entity.system.UserRole;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/2
 */
public interface IUserRoleService extends IService<UserRole> {

    void deleteUserRolesByRoleId(String[] roleIds);

    void deleteUserRolesByUserId(String[] userIds);
}

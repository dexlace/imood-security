package com.dexlace.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dexlace.common.entity.system.SystemUser;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/1
 */
public interface UserMapper extends BaseMapper<SystemUser> {
    SystemUser findByName(String username);
}
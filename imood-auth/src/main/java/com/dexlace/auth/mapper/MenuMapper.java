package com.dexlace.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dexlace.common.entity.system.Menu;

import java.util.List;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/1
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findUserPermissions(String username);
}

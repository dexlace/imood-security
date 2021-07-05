package com.dexlace.auth.manager;

import com.dexlace.auth.mapper.MenuMapper;
import com.dexlace.auth.mapper.UserMapper;
import com.dexlace.common.entity.system.Menu;
import com.dexlace.common.entity.system.SystemUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/1
 */
@Service
public class UserManager {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    public SystemUser findByName(String username) {
        return userMapper.findByName(username);
    }

    public String findUserPermissions(String username) {
//        List<Menu> userPermissions = menuMapper.findUserPermissions(username);
//
//        /**
//         * 权限表示列表
//         */
//        List<String> perms = new ArrayList<>();
//        for (Menu m: userPermissions){
//            perms.add(m.getPerms());
//        }
//        // 将一个list string转换成string，且用逗号连接
//        return StringUtils.join(perms, ",");

        List<Menu> userPermissions = menuMapper.findUserPermissions(username);
        return userPermissions.stream().map(Menu::getPerms).collect(Collectors.joining(","));
    }
}

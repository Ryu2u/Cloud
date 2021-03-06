package com.ryuzu.server.service;

import com.ryuzu.server.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ryuzu
 * @since 2022-02-14
 */
public interface IMenuService extends IService<Menu> {
    /**
     * 根据用户id查询菜单;列表
     *
     * @return
     */
    List<Menu> getMenuByAdminId();

    /**
     * 根据角色获取用户列表
     * @return
     */
    List<Menu> getMenuByRole();

    /**
     * 查询所有菜单
     * @return
     */
    List<Menu> getAllMenus();
}

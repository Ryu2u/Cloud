package com.ryuzu.server.mapper;

import com.ryuzu.server.domain.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ryuzu
 * @since 2022-02-14
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    Integer updateMenusByRid(Integer rid,Integer[] mids);
}

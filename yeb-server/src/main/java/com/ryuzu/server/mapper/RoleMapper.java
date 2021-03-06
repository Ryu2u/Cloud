package com.ryuzu.server.mapper;

import com.ryuzu.server.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ryuzu
 * @since 2022-02-14
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRolesByAdminId(Integer adminId);



}

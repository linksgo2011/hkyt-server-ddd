package com.thqqqqp.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thqqqqp.infrastructure.persistence.po.RolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色表 Mapper 接口
 */
@Mapper
public interface RoleMapper extends BaseMapper<RolePO> {
}
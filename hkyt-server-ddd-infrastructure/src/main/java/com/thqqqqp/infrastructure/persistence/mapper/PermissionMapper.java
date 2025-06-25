package com.thqqqqp.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thqqqqp.infrastructure.persistence.po.PermissionPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限表 Mapper 接口
 */
@Mapper
public interface PermissionMapper extends BaseMapper<PermissionPO> {
}
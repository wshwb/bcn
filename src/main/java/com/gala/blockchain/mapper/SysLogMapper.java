package com.gala.blockchain.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gala.blockchain.entity.SysLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gala.blockchain.entity.WorksInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gala
 * @since 2020-06-07
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

    @Select("SELECT * " +
            "FROM sys_log a " +
            "ORDER BY a.id DESC")
    List<Map<String ,Object>> dyGetList(Page<SysLog> page);
}

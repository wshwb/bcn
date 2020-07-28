package com.gala.blockchain.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gala.blockchain.entity.SysLog;
import com.gala.blockchain.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author gala
 * @since 2020-06-04
 */
public interface UserMapper extends BaseMapper<UserInfo> {

    @Select("SELECT * " +
            "FROM user_info " +
            "ORDER BY CONVERT(`userName` USING gbk) ASC")
    List<Map<String ,Object>> dyGetList(Page<UserInfo> page);
}

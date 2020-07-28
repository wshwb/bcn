package com.gala.blockchain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gala.blockchain.entity.WorksAssessInfo;
import com.gala.blockchain.entity.WorksHardcopyInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by brayden on 2020/7/14 21:44
 */
public interface WorksHardcopyInfoMapper extends BaseMapper<WorksHardcopyInfo> {
    @Select("SELECT *" +
            "FROM works_hardcopy_info a " +
            "INNER JOIN user_info b " +
            "ON a.user = b.user " +
            "INNER JOIN works_info c " +
            "ON a.worksId = c.worksId " +
            "WHERE a.deal = 0 ORDER BY a.requestTime DESC" )
    List<Map<String ,Object>> dyGetHardcopyList(Page<WorksHardcopyInfo> page);
}

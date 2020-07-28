package com.gala.blockchain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gala.blockchain.entity.WorksAssessInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by brayden on 2020/7/14 21:42
 */
public interface WorksAssessInfoMapper extends BaseMapper<WorksAssessInfo> {
    @Select("SELECT *" +
            "FROM works_assess_info a " +
            "INNER JOIN user_info b " +
            "ON a.user = b.user "  +
            "INNER JOIN works_info c " +
            "ON a.worksId = c.worksId " +
            "WHERE a.deal = 0 ORDER BY a.requestTime DESC" )
    List<Map<String ,Object>> dyGetAssessList(Page<WorksAssessInfo> page);
}

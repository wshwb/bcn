package com.gala.blockchain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gala.blockchain.entity.WorksInfo;
import com.gala.blockchain.entity.WorksVerifyInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by brayden on 2020/7/14 21:41
 */
public interface WorksVerifyInfoMapper extends BaseMapper<WorksVerifyInfo> {
    //获取作品信息
    @Select("SELECT * " +
            "FROM works_verify_info a " +
            "WHERE a.worksId = #{worksId}" )
    List<Map<String ,Object>> dydetailList(String worksId);
}

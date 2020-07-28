package com.gala.blockchain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gala.blockchain.entity.WorksAssessInfo;
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
 * @since 2020-06-04
 */
public interface WorksInfoMapper extends BaseMapper<WorksInfo> {
    //select
    @Select("SELECT *" +
            "FROM works_info a " +
            "INNER JOIN user_info b " +
            "ON a.user = b.user " +
            "ORDER BY a.uploadTime DESC" )
    List<Map<String ,Object>> dygetList(Page<WorksInfo> page);

    @Select("SELECT *" +
            "FROM works_info a " +
            "INNER JOIN user_info b " +
            "ON a.user = b.user " +
            "WHERE a.worksName = #{worksName}" +
            "ORDER BY a.uploadTime DESC" )
    List<Map<String ,Object>> selectWorksNameList(Page<WorksInfo> page,String worksName);

    @Select("SELECT *" +
            "FROM works_info a " +
            "INNER JOIN user_info b " +
            "ON a.user = b.user " +
            "WHERE b.userName = #{userName}" +
            "ORDER BY a.uploadTime DESC" )
    List<Map<String ,Object>> selectUserNameList(Page<WorksInfo> page,String userName);


    //typeselect
    @Select("SELECT *" +
            "FROM works_info a " +
            "INNER JOIN user_info b " +
            "ON a.user = b.user "  +
            "WHERE a.type = #{type} ORDER BY a.uploadTime DESC" )
    List<Map<String ,Object>> dyGetUserNameList(Page<WorksInfo> page,Integer type);

    //tagselect
    @Select("SELECT * " +
            "FROM works_info b " +
            "INNER JOIN user_info c " +
            "ON b.user = c.user "  +
            "WHERE b.type = #{type} ORDER BY b.uploadTime DESC" )
//    List<Map<String ,Object>> allGettagIdList(Page<WorksInfo> page,Integer type);
    List<Map<String ,Object>> allGettagIdList(Integer type);

    @Select("SELECT * " +
           "FROM works_info b " +
            "INNER JOIN user_info c " +
            "ON b.user = c.user "  +
           "WHERE b.tagId = #{tagId} AND b.type = #{type} ORDER BY b.uploadTime DESC" )
//    List<Map<String ,Object>> dyGettagIdList(Page<WorksInfo> page,Integer type,Integer tagId);
    List<Map<String ,Object>> dyGettagIdList(Integer type,Integer tagId);

    //worksrecommend
    @Select("SELECT *" +
            "FROM works_info a " +
            "INNER JOIN user_info b " +
            "ON a.user = b.user "  +
            "WHERE a.recommend = 1 ORDER BY a.uploadTime DESC" )
    List<Map<String ,Object>> dyrecommendList(Page<WorksInfo> page);

    //selectworksId
    @Select("SELECT * " +
            "FROM works_info a " +
            "INNER JOIN user_info b " +
            "ON a.user = b.user " +
            "INNER JOIN tag_info c " +
            "ON a.tagId = c.tagId " +
            "WHERE a.worksId = #{worksId}" )
    List<Map<String ,Object>> deGetInfoList(String worksId);

    //get worksInfo.state==1
    @Select("SELECT * FROM works_info WHERE state = #{state} limit 10")
    List<Map<String,Object>> dyGetStateList(Integer state);

}

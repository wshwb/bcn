package com.gala.blockchain.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gala.blockchain.common.utils.Log;
import com.gala.blockchain.common.utils.Result;
import com.gala.blockchain.entity.ManagerInfo;
import com.gala.blockchain.entity.UserInfo;
import com.gala.blockchain.entity.WorksAssessInfo;
import com.gala.blockchain.entity.WorksVerifyInfo;
import com.gala.blockchain.mapper.WorksAssessInfoMapper;
import com.gala.blockchain.mapper.WorksVerifyInfoMapper;
import com.gala.blockchain.service.IWorksAssessInfoService;
import com.gala.blockchain.service.IWorksVerifyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by Christina
 * @date:2020/7/21
 * @time: 16:43
 */
@RestController
@RequestMapping("/worksassess")
public class WorksAssessController {

    @Autowired
    IWorksAssessInfoService worksAssessService;

    @Autowired
    WorksAssessInfoMapper worksAssessMapper;

    @Log("获取全部待请求评估作品")
    @ResponseBody
    @PostMapping("/all")
    public Result allAssess(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        Page<WorksAssessInfo> page = new Page<WorksAssessInfo>(pageNo, pageSize);
        //联合查询
        List<Map<String ,Object>> list =  worksAssessMapper.dyGetAssessList(page);
        Map<String ,Object> data = new HashMap<>();
        data.put("records",list);
        //分页信息
        data.put("total",page.getTotal());
        data.put("size",page.getSize());
        data.put("current",page.getCurrent());
        data.put("pages",page.getPages());
        return Result.datas(data);
    }

    @Log("处理待请求评估作品")
    @ResponseBody
    @PostMapping("/deal")
    public Result deal(@RequestParam String worksId,@RequestParam Integer deal){
        //根据worksId获取作品
        QueryWrapper<WorksAssessInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("worksId",worksId);
        WorksAssessInfo worksAssessInfo = worksAssessService.getOne(queryWrapper);
        if(worksAssessInfo == null){
            return Result.error("处理失败");
        }else {
            worksAssessInfo.setDeal(deal);
            worksAssessService.update(worksAssessInfo,queryWrapper);
            return Result.build();
        }

    }
}
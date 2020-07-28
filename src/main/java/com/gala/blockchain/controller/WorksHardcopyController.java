package com.gala.blockchain.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gala.blockchain.common.utils.Log;
import com.gala.blockchain.common.utils.Result;
import com.gala.blockchain.entity.WorksAssessInfo;
import com.gala.blockchain.entity.WorksHardcopyInfo;
import com.gala.blockchain.mapper.WorksAssessInfoMapper;
import com.gala.blockchain.mapper.WorksHardcopyInfoMapper;
import com.gala.blockchain.mapper.WorksInfoMapper;
import com.gala.blockchain.service.IWorksHardcopyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by Christina
 * @date:2020/7/21
 * @time: 17:48
 */
@RestController
@RequestMapping("/workshardcopy")
public class WorksHardcopyController {
    @Autowired
    IWorksHardcopyInfoService worksHardcopyService;

    @Autowired
    WorksHardcopyInfoMapper worksHardcopyMapper;

    @Log("获取所有纸质证书待处理的作品")
    @ResponseBody
    @PostMapping("/all")
    public Object allHardCopy (@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<WorksHardcopyInfo> page = new Page<WorksHardcopyInfo>(pageNo, pageSize);
        //联合查询
        List<Map<String ,Object>> list =  worksHardcopyMapper.dyGetHardcopyList(page);
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
        QueryWrapper<WorksHardcopyInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("worksId",worksId);
        WorksHardcopyInfo worksHardcopyInfo = worksHardcopyService.getOne(queryWrapper);
        if(worksHardcopyInfo == null){
            return Result.error("处理失败");
        }else {
            worksHardcopyInfo.setDeal(deal);
            worksHardcopyService.update(worksHardcopyInfo,queryWrapper);
            return Result.build();
        }

    }
}

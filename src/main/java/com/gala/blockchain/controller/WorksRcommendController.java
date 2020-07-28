package com.gala.blockchain.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gala.blockchain.common.utils.Log;
import com.gala.blockchain.common.utils.Result;
import com.gala.blockchain.entity.WorksHardcopyInfo;
import com.gala.blockchain.entity.WorksInfo;
import com.gala.blockchain.entity.WorksVerifyInfo;
import com.gala.blockchain.mapper.WorksInfoMapper;
import com.gala.blockchain.service.IWorksInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by Christina
 * @date:2020/7/23
 * @time: 8:11
 */
@RestController
@RequestMapping("/recommend")
public class WorksRcommendController {

    @Autowired
    IWorksInfoService worksService;

    @Autowired
    private WorksInfoMapper worksMapper;


    @Log("获取全部推荐作品")
    @ResponseBody
    @PostMapping("/all")
    public Result all (@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<WorksInfo> page = new Page<WorksInfo>(pageNo, pageSize);
        //联合查询
        Map<String ,Object> data = new HashMap<>();
        List<Map<String ,Object>> list =  worksMapper.dyrecommendList(page);
        data.put("records",list);
        //分页信息
        data.put("total",page.getTotal());
        data.put("size",page.getSize());
        data.put("current",page.getCurrent());
        data.put("pages",page.getPages());
        return Result.datas(data);
    }

    @Log("推荐作品增加")
    @ResponseBody
    @PostMapping("/add")
    public Result recommend (@RequestParam String worksId) throws ParseException {
        //根据worksId获取作品
        QueryWrapper<WorksInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("worksId",worksId);
        WorksInfo worksInfo = worksService.getOne(queryWrapper);
        if(worksInfo == null){
            return Result.error("无此作品");
        }else {
            Integer recommend = 1;
            worksInfo.setRecommend(recommend);
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取当前时间
            String uploadTime = df.format(new Date());
            Date uploadDate = df.parse(uploadTime);
            worksInfo.setRecommendTime(uploadDate);
            worksService.update(worksInfo,queryWrapper);
            return Result.build();
        }
    }

    @Log("推荐作品取消")
    @ResponseBody
    @PostMapping("/del")
    public Result del (@RequestParam String worksId) throws ParseException {
        //根据worksId获取作品
        QueryWrapper<WorksInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("worksId",worksId);
        WorksInfo worksInfo = worksService.getOne(queryWrapper);
        if(worksInfo == null){
            return Result.error("无此作品");
        }else {
            Integer recommend = 0;
            worksInfo.setRecommend(recommend);
            worksService.update(worksInfo,queryWrapper);
            return Result.build();
        }
    }


}

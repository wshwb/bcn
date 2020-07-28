package com.gala.blockchain.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gala.blockchain.common.utils.Log;
import com.gala.blockchain.common.utils.Result;
import com.gala.blockchain.entity.TagInfo;
import com.gala.blockchain.mapper.TagInfoMapper;
import com.gala.blockchain.service.ITagInfoService;
import com.gala.blockchain.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by Christina
 * @date:2020/7/21
 * @time: 15:08
 */

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    TagInfoMapper tagInfoMapper;

    @Autowired
    ITagInfoService tagInfoService;

    @Log("获取标签")
    @ResponseBody
    @PostMapping("/all")
    public Result allTags(@RequestParam Integer type) {
        QueryWrapper<TagInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("type",type).notLike("tagName","其它");
        List<TagInfo> tagInfoList = tagInfoMapper.selectList(queryWrapper);

        QueryWrapper<TagInfo> queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("type",type).eq("tagName","其它");
        List<TagInfo> tagInfoList1 = tagInfoMapper.selectList(queryWrapper1);

        Map<String ,Object> data = new HashMap<>();
        data.put("tags", tagInfoList);
        data.put("tags_else", tagInfoList1);
        return  Result.datas(data);
    }

    @Log("增加标签")
    @ResponseBody
    @PostMapping("/add")
    public Result add (@RequestParam Integer type,@RequestParam String tagName){
        //判断tagNames是否为空
        if(tagName.isEmpty()){
            return Result.error("请输入标签名称");
        }
        //判断是否存在
        QueryWrapper<TagInfo> queryWrapper = new QueryWrapper<TagInfo>();
        queryWrapper.eq("type",type).eq("tagName",tagName);
        if(queryWrapper != null){
            return Result.error("该标签已存在");
        }
        TagInfo tagInfo = new TagInfo();
        tagInfo.setType(type);
        tagInfo.setTagName(tagName);
        tagInfoService.save(tagInfo);
        return  Result.build();
    }
}

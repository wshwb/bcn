package com.gala.blockchain.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gala.blockchain.common.utils.Log;
import com.gala.blockchain.common.utils.Result;
import com.gala.blockchain.entity.ManagerInfo;
import com.gala.blockchain.entity.PolicyInfo;
import com.gala.blockchain.entity.UserInfo;
import com.gala.blockchain.service.IPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gala
 * @since 2020-06-04
 */
@RestController
@RequestMapping("/news")
public class PolicyController {
    @Autowired
    IPolicyService policyService;

    @Log("获取全部政策新闻")
    @ResponseBody
    @PostMapping("/all")
    public Object allNews(@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<PolicyInfo> page = new Page<PolicyInfo>(pageNo, pageSize);
        QueryWrapper<PolicyInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("id").orderByDesc("uploadTime");
        return policyService.page(page, queryWrapper);
    }


    @Log("添加政策新闻")
    @ResponseBody
    @PostMapping("/add")
    public Result add(@RequestParam String policyTitle,
                      @RequestParam String expireDate,
                      @RequestParam String policyUrl) throws ParseException {
        PolicyInfo policyInfo = new PolicyInfo();
        policyInfo.setPolicyTitle(policyTitle);
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expireDate1 = df.parse(expireDate);
        policyInfo.setExpireDate(expireDate1);
        policyInfo.setPolicyUrl(policyUrl);
        //获取当前时间
        String uploadTime = df.format(new Date());
        Date uploadDate = df.parse(uploadTime);
        policyInfo.setUploadTime(uploadDate);

        policyService.save(policyInfo);
        return  Result.build();
    }

    @Log("删除政策新闻")
    @ResponseBody
    @PostMapping("/del")
    public Result del(@RequestParam Integer id){
        QueryWrapper<PolicyInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        PolicyInfo checkManager = policyService.getOne(queryWrapper);
        if(checkManager == null){
            return Result.error("删除失败");
        }else {
            policyService.removeById(id);
            return Result.info("删除成功");
        }
    }
}

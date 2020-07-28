package com.gala.blockchain.controller;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gala.blockchain.common.utils.Log;
import com.gala.blockchain.common.utils.Result;
import com.gala.blockchain.entity.*;
import com.gala.blockchain.mapper.WorksVerifyInfoMapper;
import com.gala.blockchain.service.IPolicyService;
import com.gala.blockchain.service.IUserService;
import com.gala.blockchain.service.IWorksInfoService;
import com.gala.blockchain.service.IWorksVerifyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by Christina
 * @date:2020/7/21
 * @time: 14:50
 */
@RestController
@RequestMapping("/worksverify")
public class WorksVerifyController {

    @Autowired
    IWorksInfoService worksService;

    @Autowired
    IUserService userService;

    @Autowired
    IWorksVerifyInfoService worksVerifyService;

    @Autowired
    WorksVerifyInfoMapper worksVerifyMapper;

    @Log("获取全部待审核作品")
    @ResponseBody
    @PostMapping("/all")
    public Object allVerify(@RequestParam Integer pageNo, @RequestParam Integer pageSize){
        Page<WorksVerifyInfo> page = new Page<WorksVerifyInfo>(pageNo, pageSize);
        QueryWrapper<WorksVerifyInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("uploadTime");
        return worksVerifyService.page(page, queryWrapper);
    }

    @Log("上传展示图")
    @ResponseBody
    @PostMapping("/pass")
    public Result pass(@RequestParam String worksId,
                       @RequestParam("image_file") MultipartFile photo) throws ParseException {
        //审核表获取
        QueryWrapper<WorksVerifyInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("worksId",worksId);
        WorksVerifyInfo worksVerifyInfo = worksVerifyService.getOne(queryWrapper);
        //传入works_info
        WorksInfo worksInfo = new WorksInfo();
        //获取作者信息
        String user = worksVerifyInfo.getUser();
        QueryWrapper<UserInfo> queryWrapper_user = new QueryWrapper();
        queryWrapper_user.eq("user", user);
        UserInfo userInfo = userService.getOne(queryWrapper_user);

        if (userInfo == null) {
            return Result.error("作者不存在");
        }else {
            //确权状态
            Integer state = 1;
            //传展示图
            //图片输入规则
            if (photo == null) {
                return Result.error("选择要上传的文件！");
            }
            if (photo.getSize() > 1024 * 1024 * 10) {
                return Result.error("文件大小不能超过10M！");
            }

            //保存路径/works/YYYYMMDD/
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            String nyr = dateFormat.format(new Date());
            String savePath = "/www/wwwroot/works/" + nyr + "/";
            File savePathFile = new File(savePath);
            if (!savePathFile.exists()) {
                //若不存在该目录，则创建目录
                savePathFile.mkdirs();
            }

            //获取文件后缀
            String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
            if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
                return Result.error("请选择jpg,jpeg,gif,png格式的图片！");
            }
            //展示图名称：xxxxxx_s.jpg
            String filename = worksId + "_s" + "." + suffix;
            try {
                //将文件保存指定目录
                photo.transferTo(new File(savePath + filename));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("保存展示图异常！");
            }

            //获取审核时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String verifyTime = df.format(new Date());
            Date verifyDate = df.parse(verifyTime);
            //worksinfo添加
            worksInfo.setUser(worksVerifyInfo.getUser());
            worksInfo.setWorksName(worksVerifyInfo.getWorksName());
            worksInfo.setWorkAbstract(worksVerifyInfo.getWorkAbstract());
            worksInfo.setType(worksVerifyInfo.getType());
            worksInfo.setTagId(worksVerifyInfo.getTagId());
            worksInfo.setWorksId(worksId);
            worksInfo.setState(state);
            worksInfo.setWorksPathOrigin(worksVerifyInfo.getWorksPathOrigin());
            worksInfo.setWorksPath("https://bch.bjjuzhen.com/works/" + nyr + "/" + filename);
            worksInfo.setUploadTime(worksVerifyInfo.getUploadTime());
            worksInfo.setVerifyTime(verifyDate);
            //空数据
            Date date = df.parse("0001-01-01 00:00:00");
            worksInfo.setBchTime(date);
            worksInfo.setManager("");
            worksInfo.setBchHash("");
            worksService.save(worksInfo);
            //删除待审核作品
            worksVerifyMapper.delete(new QueryWrapper<WorksVerifyInfo>().eq("worksId", worksId));
            //userinfo更新
            QueryWrapper<WorksInfo> queryWrapper_works = new QueryWrapper<>();
            queryWrapper_works.eq("user",user);
            Integer total = worksService.count(queryWrapper_works);
            userInfo.setWorksTotal(total);
            userService.update(userInfo, queryWrapper_user);
            return Result.build();
        }
    }

    @Log("拒绝审核")
    @ResponseBody
    @PostMapping("/del")
    public Result del (@RequestParam String worksId) {
        QueryWrapper<WorksVerifyInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worksId", worksId);
        WorksVerifyInfo checkManager = worksVerifyService.getOne(queryWrapper);
        if (checkManager == null) {
            return Result.error("删除失败");
        } else {
            worksVerifyMapper.delete(queryWrapper);
            return Result.info("删除成功");
        }
    }

    @Log("作品详情")
    @ResponseBody
    @PostMapping("/info")
    public Result info (@RequestParam String worksId){
        QueryWrapper<WorksVerifyInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worksId",worksId);
        WorksVerifyInfo checkManager = worksVerifyService.getOne(queryWrapper);
        if(checkManager == null){
            return Result.error("查看失败");
        }else {
            List<WorksVerifyInfo> list = worksVerifyMapper.selectList(queryWrapper);
            Map<String ,Object> data = new HashMap<>();
            data.put("records",list);
            return Result.datas(data);
        }
    }
}

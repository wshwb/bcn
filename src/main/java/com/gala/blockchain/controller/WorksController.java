package com.gala.blockchain.controller;


import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gala.blockchain.common.utils.Log;
import com.gala.blockchain.common.utils.Result;
import com.gala.blockchain.entity.*;

import com.gala.blockchain.mapper.WorksInfoMapper;
import com.gala.blockchain.service.*;
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
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gala
 * @since 2020-06-04
 */
@RestController
@RequestMapping("/works")
public class WorksController {

    @Autowired
    IWorksInfoService worksService;

    @Autowired
    IWorksVerifyInfoService worksVerifyInfoService;

    @Autowired
    IWorksAssessInfoService worksAssessInfoService;

    @Autowired
    IWorksHardcopyInfoService worksHardcopyInfoService;

    @Autowired
    IUserService userService;

    @Autowired
    ITagInfoService tagInfoService;

    @Autowired
    private WorksInfoMapper worksMapper;

    @Log("搜索作品")
    @ResponseBody
    @PostMapping("/all")
    public Result allWorks(@RequestParam (required=false,defaultValue="")String key,
                         @RequestParam (required=false,defaultValue="worksName") String searchType,
                         @RequestParam Integer pageNo,
                         @RequestParam Integer pageSize){
        Page<WorksInfo> page = new Page<WorksInfo>(pageNo, pageSize);
        Map<String ,Object> data = new HashMap<>();
        if (key.isEmpty()) {
            //联合查询
            List<Map<String ,Object>> list1 =  worksMapper.dygetList(page);
            data.put("records",list1);
        } else {
            if (searchType.equals("worksName") ) {
                //联合查询
                List<Map<String ,Object>> list2 =  worksMapper.selectWorksNameList(page,key);
                data.put("records",list2);
            }

            if(searchType.equals("userName")) {
                //联合查询
                List<Map<String ,Object>> list3 =  worksMapper.selectUserNameList(page,key);
                data.put("records",list3);
            }
        }
        //分页信息
        data.put("total",page.getTotal());
        data.put("size",page.getSize());
        data.put("current",page.getCurrent());
        data.put("pages",page.getPages());
        return Result.datas(data);
    }

    @Log("作品分类")
    @ResponseBody
    @PostMapping("/typeselect")
    public Object typeselect (@RequestParam Integer type,
                              @RequestParam Integer pageNo,
                              @RequestParam Integer pageSize){
        Page<WorksInfo> page = new Page<WorksInfo>(pageNo, pageSize);
        //联合查询
        List<Map<String ,Object>> list =  worksMapper.dyGetUserNameList(page,type);
        Map<String ,Object> data = new HashMap<>();
        data.put("records",list);
        //分页信息
        data.put("total",page.getTotal());
        data.put("size",page.getSize());
        data.put("current",page.getCurrent());
        data.put("pages",page.getPages());
        return Result.datas(data);
    }

    @Log("标签筛选")
    @ResponseBody
    @PostMapping("/tagselect")
    public Result tagselect (@RequestParam Integer type,
                             @RequestParam (required=false,defaultValue="")Integer tagId){
        if(tagId == null){
            List<Map<String, Object>> list1 = worksMapper.allGettagIdList(type);
            Map<String ,Object> data1 = new HashMap<>();
            data1.put("records",list1);
            return Result.datas(data1);
        }else {
            List<Map<String, Object>> list2 = worksMapper.dyGettagIdList(type, tagId);
            Map<String ,Object> data2 = new HashMap<>();
            data2.put("records",list2);
            return Result.datas(data2);
        }

    }


    @Log("添加作品")
    @ResponseBody
    @PostMapping("/add")
    public Result add(@RequestParam String user,
                      @RequestParam String worksName,
                      @RequestParam String workAbstract,
                      @RequestParam Integer type,
                      @RequestParam Integer tagId,
                      @RequestParam("image_file_origin")MultipartFile orphoto,
                      @RequestParam("image_file")MultipartFile photo) throws ParseException {
        WorksInfo worksInfo = new WorksInfo();

        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user", user);
        UserInfo userInfo = userService.getOne(queryWrapper);

        if (userInfo == null) {
            return Result.error("作者不存在");
        }else {
            //确权状态
            Integer state = 1;
            //获取一个时间戳
            String dateStr = Long.toString(System.currentTimeMillis() / 1000L);
            //设置作品Id:由用户手机号后4位+时间戳后9位组成
            String worksId = user.substring(0, 4) + dateStr.substring(dateStr.length() - 9);
            //作品名称&简介字数限制
            if (worksName.length() > 20 || workAbstract.length() > 100 ) {
                return Result.error("不满足添加要求");
            }
            //图片输入规则
            if (photo == null || orphoto == null) {
                return Result.error("选择要上传的文件！");
            }
            if (photo.getSize() > 1024 * 1024 * 10 || orphoto.getSize() > 1024 * 1024 * 10) {
                return Result.error("文件大小不能超过10M！");
            }

            //上传时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uploadTime = df.format(new Date());
            Date uploadDate = df.parse(uploadTime);

            //保存路径/works/YYYYMMDD/
            String nyr = dateFormat.format(new Date());
            String savePath = "/www/wwwroot/works/" + nyr + "/";
            File savePathFile = new File(savePath);
            if (!savePathFile.exists()) {
                //若不存在该目录，则创建目录
                savePathFile.mkdirs();
            }

            //原图
            //获取文件后缀
            String orsuffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);
            if (!"jpg,jpeg,gif,png".toUpperCase().contains(orsuffix.toUpperCase())) {
                return Result.error("请选择jpg,jpeg,gif,png格式的图片！");
            }
            //原图名称：xxxxxx.jpg
            String orFilename = worksId + "." + orsuffix;
            try {
                //将文件保存指定目录
                orphoto.transferTo(new File(savePath + orFilename));
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error("保存作品异常！");
            }

            //展示图
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

            //设置时间格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse("0001-01-01 00:00:00");

            //worksinfo添加
            worksInfo.setUser(user);
            worksInfo.setWorksName(worksName);
            worksInfo.setWorkAbstract(workAbstract);
            worksInfo.setType(type);
            worksInfo.setWorksId(worksId);
            worksInfo.setState(state);
            worksInfo.setTagId(tagId);
            //空数据
            worksInfo.setVerifyTime(date);
            worksInfo.setBchTime(date);
            worksInfo.setManager("");
            worksInfo.setBchHash("");

            worksInfo.setWorksPathOrigin("https://bch.bjjuzhen.com/works/" + nyr + "/" + orFilename);
            worksInfo.setWorksPath("https://bch.bjjuzhen.com/works/" + nyr + "/" + filename);
            worksInfo.setUploadTime(uploadDate);
            worksService.save(worksInfo);

            //userinfo更新
            QueryWrapper<WorksInfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user",user);
            Integer total = worksService.count(queryWrapper1);
            userInfo.setWorksTotal(total);
            userService.update(userInfo, queryWrapper);

            return Result.build();
        }

    }

    @Log("删除作品")
    @ResponseBody
    @PostMapping("/del")
    public Result del(@RequestParam String worksId){
        //work
        QueryWrapper<WorksInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worksId",worksId);
        WorksInfo checkManager = worksService.getOne(queryWrapper);
//        //tag
//        QueryWrapper<WorksTagInfo> queryWrapper_tag = new QueryWrapper<>();
//        queryWrapper_tag.eq("worksId",worksId);
        //获取用户手机号
        String user = checkManager.getUser();

        if(checkManager == null){
            return Result.error("删除失败");
        }else {
            worksMapper.delete(queryWrapper);
//            worksTagInfoMapper.delete(queryWrapper_tag);
            //userinfo更新
            //获取作品数量
            QueryWrapper<WorksInfo> queryWrapper_works = new QueryWrapper<>();
            queryWrapper_works.eq("user",user);
            Integer total = worksService.count(queryWrapper_works);
            //作者信息
            QueryWrapper<UserInfo> queryWrapper_user = new QueryWrapper();
            queryWrapper_user.eq("user", user);
            UserInfo userInfo = userService.getOne(queryWrapper_user);
            userInfo.setWorksTotal(total);
            userService.update(userInfo, queryWrapper_user);
            return Result.info("删除成功");
        }
    }

    @Log("首页信息")
    @ResponseBody
    @PostMapping("/indexData")
    public Result indexData(){
        int all = worksService.count();

        int verify = worksVerifyInfoService.count();

        QueryWrapper<WorksInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("state",2);
        int confirm = worksService.count(queryWrapper1);

        QueryWrapper<WorksAssessInfo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("deal",0);
        int assess = worksAssessInfoService.count(queryWrapper2);

        QueryWrapper<WorksHardcopyInfo> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("deal",0);
        int hardcopy = worksHardcopyInfoService.count(queryWrapper3);

        Map<String ,Object> data = new HashMap<>();
        data.put("all",all);
        data.put("confirm",confirm);
        data.put("verify",verify);
        data.put("assess",assess);
        data.put("hardcopy",hardcopy);
        return  Result.datas(data);
    }

    @Log("作品详情")
    @ResponseBody
    @PostMapping("/info")
    public Result info (@RequestParam String worksId){
        QueryWrapper<WorksInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("worksId",worksId);
        WorksInfo checkManager = worksService.getOne(queryWrapper);
        if(checkManager == null){
            return Result.error("查看失败");
        }else {
            List<Map<String, Object>> list = worksMapper.deGetInfoList(worksId);

            Map<String ,Object> data = new HashMap<>();
            data.put("records",list);

            return Result.datas(data);
        }
    }

    //无用
    @Log("上传图片")
    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadPhoto(@RequestParam("image-file")MultipartFile photo) {
        Map<String, String> ret = new HashMap<String, String>();
        if (photo == null) {
            ret.put("type", "error");
            ret.put("msg", "选择要上传的文件！");
            return ret;
        }
        if (photo.getSize() > 1024 * 1024 * 10) {
            ret.put("type", "error");
            ret.put("msg", "文件大小不能超过10M！");
            return ret;
        }
        //获取文件后缀
        String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1, photo.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            ret.put("type", "error");
            ret.put("msg", "请选择jpg,jpeg,gif,png格式的图片！");
            return ret;
        }

        //todo 更换为前端项目的img路径下
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
        String nyr = dateFormat.format(new Date());
        String savePath = "/www/wwwroot/works/"+nyr+"/";
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdirs();
        }

        String filename = new Date().getTime() + "." + suffix;
        try {
            //将文件保存指定目录
            photo.transferTo(new File(savePath + filename));
        } catch (Exception e) {
            ret.put("type", "error");
            ret.put("msg", "保存文件异常！");
            e.printStackTrace();
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "上传图片成功！");
        ret.put("filepath", savePath + filename);
        ret.put("filename", filename);
        return ret;
    }

    @ResponseBody
    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id){
        WorksInfo worksInfo = worksService.getById(id);
        Map<String,Object> data = new HashMap<>();
        data.put("works", worksInfo);
        return  Result.datas(data);
    }

    @Log("审核作品")
    @ResponseBody
    @PostMapping("/audit")
    public Result Audit(@RequestParam Integer id,@RequestParam Integer audit){
        WorksInfo worksInfo = worksService.getById(id);
//        worksInfo.setIsAudit(audit);
        worksService.updateById(worksInfo);
        return  Result.build();
    }


}

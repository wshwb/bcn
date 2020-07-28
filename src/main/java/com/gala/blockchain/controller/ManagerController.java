package com.gala.blockchain.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gala.blockchain.common.utils.Log;
import com.gala.blockchain.common.utils.Result;
import com.gala.blockchain.entity.ManagerInfo;
import com.gala.blockchain.entity.UserInfo;
import com.gala.blockchain.entity.WorksInfo;

import com.gala.blockchain.mapper.ManagerMapper;
import com.gala.blockchain.mapper.UserMapper;
import com.gala.blockchain.service.IManagerService;
import lombok.var;
import org.apache.catalina.Manager;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.message.callback.PrivateKeyCallback;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gala
 * @since 2020-06-04
 */
@RestController
@RequestMapping("/manager")
@WebListener
public class ManagerController {

    @Autowired
    IManagerService managerService;

    @Log("登录")
    @ResponseBody
    @PostMapping("/login")
    public Result login(@RequestParam String userAccount, @RequestParam String passWord){
        //登录验证
        QueryWrapper<ManagerInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("manager",userAccount);
        queryWrapper.eq("passWord",passWord);
        ManagerInfo managerInfo = managerService.getOne(queryWrapper);
        //获取时间-sessionId
        String dateStr = Long.toString(System.currentTimeMillis()/1000L);
        Integer session = Integer.parseInt(dateStr);
        if(managerInfo == null){
            return Result.error("密码错误");
        }else {
            managerInfo.setSession(dateStr);
            managerInfo.setSessionExpire(session + 300); //登录更新时间设置
            managerService.update(managerInfo,queryWrapper);
            return Result.data("sessionId",session);
        }
    }

    @Log("验证")
    @ResponseBody
    @PostMapping("/session")
    public Result session(@RequestParam String userAccount, @RequestParam String sessionId) {
        //获取当前时间
        String dateStr = Long.toString(System.currentTimeMillis()/1000L);
        Integer time = Integer.parseInt(dateStr);
        //判断用户
        QueryWrapper<ManagerInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("manager",userAccount);
        queryWrapper.eq("session",sessionId);
        ManagerInfo checkManager = managerService.getOne(queryWrapper);
        if(checkManager == null){
            return Result.error("请重新登录");
        }
        //获取过期时间
            Integer checkTime = checkManager.getSessionExpire();
            if (time < checkTime) {
                checkManager.setSessionExpire(checkTime + 300);//登录更新时间设置
                managerService.update(checkManager, queryWrapper);
                return Result.build();
            } else {
                return Result.error("请重新登录");
            }

    }


    @Log("修改密码")
    @ResponseBody
    @PostMapping("/updatePwd")
    public Result updatePwd(@RequestParam String manager, @RequestParam String userPwd,@RequestParam String newPwd){
        QueryWrapper<ManagerInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("manager",manager);
        queryWrapper.eq("passWord",userPwd);
        ManagerInfo managerInfo = managerService.getOne(queryWrapper);
        if(managerInfo == null){
            return Result.error("密码错误");
        }else {
            managerInfo.setPassWord(newPwd);
            managerService.updateById(managerInfo);
            return Result.build();
        }
    }
}

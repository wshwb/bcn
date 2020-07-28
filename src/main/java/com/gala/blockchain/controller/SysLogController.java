package com.gala.blockchain.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gala.blockchain.common.utils.Result;
import com.gala.blockchain.entity.SysLog;
import com.gala.blockchain.entity.UserInfo;
import com.gala.blockchain.entity.WorksInfo;
import com.gala.blockchain.mapper.SysLogMapper;
import com.gala.blockchain.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gala
 * @since 2020-06-07
 */
@RestController
@RequestMapping("/sysLog")
public class SysLogController {


    @Autowired
    ISysLogService sysLogService;

    @Autowired
    SysLogMapper sysLogMapper;

    @ResponseBody
    @PostMapping("/all")
    public Object allLog( @RequestParam Integer pageNo,
                          @RequestParam Integer pageSize)  {
//        QueryWrapper<SysLog> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByDesc("id");
        //分页
        Page<SysLog> page = new Page<SysLog>(pageNo, pageSize);
//        Integer pageNum = (pageNo-1)*pageSize;
//        return sysLogService.page(page, queryWrapper);

        Map<String ,Object> data = new HashMap<>();
        //联合查询
        List<Map<String ,Object>> list =  sysLogMapper.dyGetList(page);
        data.put("records",list);
        //分页信息
        data.put("total",page.getTotal());
        data.put("size",page.getSize());
        data.put("current",page.getCurrent());
        data.put("pages",10);
        return Result.datas(data);
    }
}

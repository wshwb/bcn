package com.gala.blockchain.job;

import cn.hutool.core.date.DateUtil;
import cn.tdchain.Trans;
import com.gala.blockchain.base.BaseDemo;
import com.gala.blockchain.mapper.WorksInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by brayden on 2020/7/28 15:11
 */
@Component
@Slf4j
public class TaskJob extends BaseDemo {

    @Autowired
    WorksInfoMapper worksInfoMapper;
    /**
     * 按照标准时间来算，每隔 10s 执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void job1() {

        List<Map<String, Object>> maps = worksInfoMapper.dyGetStateList(1);
        Trans trans = new Trans();
        Iterator<Map<String, Object>> iterator = maps.iterator();
//        while (iterator.hasNext()){
//
//        }
        log.info("【job1】开始执行：{}", DateUtil.formatDateTime(new Date()));
        log.info(maps.toString());
    }

    /**
     * 从启动时间开始，间隔 2s 执行
     * 固定间隔时间
     */
//    @Scheduled(fixedRate = 2000)
//    public void job2() {
//        log.info("【job2】开始执行：{}", DateUtil.formatDateTime(new Date()));
//    }
//
//    /**
//     * 从启动时间开始，延迟 5s 后间隔 4s 执行
//     * 固定等待时间
//     */
//    @Scheduled(fixedDelay = 4000, initialDelay = 5000)
//    public void job3() {
//        log.info("【job3】开始执行：{}", DateUtil.formatDateTime(new Date()));
//    }
}

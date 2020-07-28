package com.gala.blockchain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gala.blockchain.entity.WorksVerifyInfo;
import com.gala.blockchain.mapper.WorksVerifyInfoMapper;
import com.gala.blockchain.service.IWorksVerifyInfoService;
import org.springframework.stereotype.Service;

/**
 * Created by brayden on 2020/7/14 21:59
 */
@Service
public class WorksVerifyInfoServiceImpl extends ServiceImpl<WorksVerifyInfoMapper,WorksVerifyInfo> implements IWorksVerifyInfoService {
}

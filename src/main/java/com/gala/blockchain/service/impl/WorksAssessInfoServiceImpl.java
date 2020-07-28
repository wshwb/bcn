package com.gala.blockchain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gala.blockchain.entity.WorksAssessInfo;
import com.gala.blockchain.mapper.WorksAssessInfoMapper;
import com.gala.blockchain.service.IWorksAssessInfoService;
import org.springframework.stereotype.Service;

/**
 * Created by brayden on 2020/7/14 21:56
 */
@Service
public class WorksAssessInfoServiceImpl extends ServiceImpl<WorksAssessInfoMapper,WorksAssessInfo> implements IWorksAssessInfoService {
}

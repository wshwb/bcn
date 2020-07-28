package com.gala.blockchain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gala.blockchain.entity.WorksHardcopyInfo;
import com.gala.blockchain.mapper.WorksHardcopyInfoMapper;
import com.gala.blockchain.service.IWorksHardcopyInfoService;
import org.springframework.stereotype.Service;

/**
 * Created by brayden on 2020/7/14 21:58
 */
@Service
public class WorksHardcopyInfoServiceImpl extends ServiceImpl<WorksHardcopyInfoMapper,WorksHardcopyInfo> implements IWorksHardcopyInfoService{
}

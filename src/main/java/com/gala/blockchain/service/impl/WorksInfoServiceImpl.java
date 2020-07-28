package com.gala.blockchain.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.gala.blockchain.entity.WorksInfo;
import com.gala.blockchain.mapper.WorksInfoMapper;
import com.gala.blockchain.service.IWorksInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javafx.scene.control.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gala
 * @since 2020-06-04
 */
@Service
public class WorksInfoServiceImpl extends ServiceImpl<WorksInfoMapper, WorksInfo> implements IWorksInfoService {


}

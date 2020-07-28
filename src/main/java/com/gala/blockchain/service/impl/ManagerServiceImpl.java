package com.gala.blockchain.service.impl;

import com.gala.blockchain.entity.ManagerInfo;
import com.gala.blockchain.mapper.ManagerMapper;
import com.gala.blockchain.service.IManagerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gala
 * @since 2020-06-04
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, ManagerInfo> implements IManagerService {

}

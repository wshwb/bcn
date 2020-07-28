package com.gala.blockchain.service.impl;

import com.gala.blockchain.entity.PolicyInfo;
import com.gala.blockchain.mapper.PolicyMapper;
import com.gala.blockchain.service.IPolicyService;
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
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, PolicyInfo> implements IPolicyService {

}

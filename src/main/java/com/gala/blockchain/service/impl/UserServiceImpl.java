package com.gala.blockchain.service.impl;

import com.gala.blockchain.entity.UserInfo;
import com.gala.blockchain.mapper.UserMapper;
import com.gala.blockchain.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements IUserService {

}

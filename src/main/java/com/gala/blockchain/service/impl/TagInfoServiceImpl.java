package com.gala.blockchain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gala.blockchain.entity.TagInfo;
import com.gala.blockchain.mapper.TagInfoMapper;
import com.gala.blockchain.service.ITagInfoService;
import org.springframework.stereotype.Service;

/**
 * @author: create by Christina
 * @date:2020/7/17
 * @time: 21:59
 */

@Service
public class TagInfoServiceImpl extends ServiceImpl<TagInfoMapper, TagInfo> implements ITagInfoService {

}

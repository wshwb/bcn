package com.gala.blockchain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: create by Christina
 * @date:2020/7/15
 * @time: 10:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "tag_info")
public class TagInfo implements Serializable {

    private static final long serialVersionUID = 4897039907479349562L;
    @TableId(value = "tagId")
    private Integer tagId;

    @TableField(value = "type")
    private Integer type;

    @TableField(value = "tagName")
    private String tagName;
}

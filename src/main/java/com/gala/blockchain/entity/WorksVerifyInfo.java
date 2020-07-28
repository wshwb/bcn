package com.gala.blockchain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by brayden on 2020/7/14 21:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "works_verify_info")
public class WorksVerifyInfo implements Serializable {

    private static final long serialVersionUID = 6047085625636768358L;
    @TableId(value = "Id")
    private Integer id;

    @TableField(value = "worksId")
    private String worksId;

    @TableField(value = "worksName")
    private String worksName;

    @TableField(value = "type")
    private Integer type;

    @TableField(value = "tagId")
    private Integer tagId;

    @TableField(value = "user")
    private String user;

    @TableField(value = "worksAbstract")
    private String workAbstract;


    @TableField(value = "uploadTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;

    @TableField(value = "worksPathOrigin")
    private String worksPathOrigin;
}

package com.gala.blockchain.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author gala
 * @since 2020-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "works_info")
public class WorksInfo implements Serializable {


    private static final long serialVersionUID = 824118796418377432L;
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

    @TableField(value = "workAbstract")
    private String workAbstract;

    @TableField(value = "state")
    private Integer state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "uploadTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date uploadTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "verifyTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date verifyTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "bchTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date bchTime;

    @TableField(value = "worksPath")
    private String worksPath;

    @TableField(value = "worksPathOrigin")
    private String worksPathOrigin ;

    @TableField(value = "manager")
    private String manager;

    @TableField(value = "bchHash")
    private String bchHash;

    @TableField(value = "bchHeight")
    private Integer bchHeight;

    @TableField(value = "recommend")
    private Integer recommend;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "recommendTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date recommendTime;


}

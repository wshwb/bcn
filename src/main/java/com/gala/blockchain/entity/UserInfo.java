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
@TableName(value = "user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id")
    private Integer id;

    @TableField(value = "user")
    private String user;

    @TableField(value = "userName")
    private String userName;

    @TableField(value = "gender")
    private Integer gender;

    @TableField(value = "workUnit")
    private String workUnit;

    @TableField(value = "birth")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date birth;

    @TableField(value = "email")
    private String email;

    @TableField(value = "worksTotal")
    private Integer worksTotal;

    @TableField(value = "worksInBch")
    private Integer worksInBch;


}

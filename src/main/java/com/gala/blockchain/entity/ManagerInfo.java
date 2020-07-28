package com.gala.blockchain.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName(value = "manager_info")
public class ManagerInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id")
    private Integer id;
    /**
     * 管理员账号
     */
    @TableField(value = "manager")
    private String manager;

    /**
     * 管理员用户名
     */
    @TableField(value = "managerName")
    private String managerName;

    /**
     * 密码
     */
    @TableField(value = "passWord")
    private String passWord;

    /**
     * sessionId
     */
    @TableField(value = "session")
    private String session;

    /**
     * 过期时间
     */
    @TableField(value = "sessionExpire")
    private Integer sessionExpire;

}

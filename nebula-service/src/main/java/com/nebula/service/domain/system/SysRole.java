package com.nebula.service.domain.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nebula.common.entity.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;



@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends PageDomain {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String roleName;
    private String roleKey;
    private Integer orderNo;
    private Integer roleStatus;
    private Integer isDeleted;
    private Integer createBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String remark;

}

package com.nebula.service.domain.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nebula.common.entity.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends PageDomain {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    private String deptName;
    private Integer parentId;
    private Integer leader;
    @TableField(exist = false)
    private String leaderName;
    private Integer orderNo;
    private Integer deptStatus;
    private Integer isDelete;
    private Integer createBy;
    private Integer updateBy;
    @TableField(exist = false)
    List<SysDept> childList;
}

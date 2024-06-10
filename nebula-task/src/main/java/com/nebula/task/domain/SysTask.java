package com.nebula.task.domain;

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
public class SysTask extends PageDomain {
    @TableId(type = IdType.AUTO)
    private String id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String jobName;
    private String jobHandler;
    private String cron;
    private String jobParams;
    private String jobDesc;
    private Integer createBy;
    private Integer updateBy;
    private Integer isDeleted;
    private Integer isOpen;

    @TableField(exist = false)
    List<String> jobList;
}

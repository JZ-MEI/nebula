package com.nebula.task.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nebula.common.entity.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysJobLog extends PageDomain {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer jobId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date executeTime;
    private String jobHandler;
    private Integer dispatchResult;
    private Integer executeResult;
    private String dispatchErrorReason;
    private String executeErrorReason;
    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;
}

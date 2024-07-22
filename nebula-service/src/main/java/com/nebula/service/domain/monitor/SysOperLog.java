package com.nebula.service.domain.monitor;

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
public class SysOperLog extends PageDomain {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    private String sqlId;

    private String commandType;

    private String sqlParam;

    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;
}

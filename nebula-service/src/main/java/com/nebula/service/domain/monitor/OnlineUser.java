package com.nebula.service.domain.monitor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nebula.common.entity.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class OnlineUser extends PageDomain {
    private String token;
    private String browser;
    private String platform;
    private String engine;
    private String os;
    private String loginIp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;
    private Integer loginDevices;
    private String nickname;
    private Integer loginId;
}

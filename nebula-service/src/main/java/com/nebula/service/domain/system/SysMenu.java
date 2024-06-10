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

@EqualsAndHashCode(callSuper = true)
@Data
public class SysMenu extends PageDomain {

    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private String menuName;

    private Integer menuType;

    private String menuIcon;

    private Integer parentId;

    private String routerPath;

    private String compPath;

    private Integer orderNo;

    private Integer isCache;

    private Integer isThird;

    private Integer isDeleted;

    private Integer isVisiable;

    private String permission;

    private Integer createBy;

    private Integer updateBy;

    @TableField(exist = false)
    private List<SysMenu> sysMenuList;
}

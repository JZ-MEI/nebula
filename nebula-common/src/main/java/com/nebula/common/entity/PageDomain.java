package com.nebula.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class PageDomain {
    @TableField(exist = false)
    private Integer pageNum;
    @TableField(exist = false)
    private Integer pageSize;
}

package com.nebula.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpStatus;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nebula.common.entity.PageDomain;
import com.nebula.common.entity.TableDataInfo;

import java.util.List;

public class PageUtil {

    public static <T extends PageDomain> void startPage(T domain) {
        if (domain.getPageNum() != null && domain.getPageSize() != null) {
            PageHelper.startPage(domain.getPageNum(), domain.getPageSize());
        }
    }

    public static void clear() {
        PageHelper.clearPage();
    }

    public static <T extends PageDomain> TableDataInfo<T> getTableData(List<T> list) {
        TableDataInfo<T> tableDataInfo = new TableDataInfo<>();
        tableDataInfo.setCode(HttpStatus.HTTP_OK);
        tableDataInfo.setMsg("成功");
        tableDataInfo.setTotal(CollUtil.isEmpty(list) ? 0 : new PageInfo<T>(list).getTotal());
        tableDataInfo.setRows(list);
        return tableDataInfo;
    }
}

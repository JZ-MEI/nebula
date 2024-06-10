package com.nebula.common.util;

import cn.dev33.satoken.stp.StpUtil;

public class AdminUtil {
    public static Integer getLoginId(){
        return StpUtil.getLoginIdAsInt();
    }
}

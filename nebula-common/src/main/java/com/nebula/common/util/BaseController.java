package com.nebula.common.util;

import com.nebula.common.common.BaseResult;

public class BaseController {
    public BaseResult<String> toResult(int effectRow) {
        return effectRow > 0 ? BaseResult.success("操作成功") : BaseResult.error("操作失败");
    }
}

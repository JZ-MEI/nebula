package com.nebula.controller.monitor;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.nebula.common.common.BaseResult;
import com.nebula.common.entity.TableDataInfo;
import com.nebula.common.util.BaseController;
import com.nebula.service.domain.monitor.OnlineUser;
import com.nebula.service.service.OnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("online")
public class OnlineUserController extends BaseController {

    @Autowired
    OnlineUserService onlineUserService;

    @GetMapping("getOnlineUser")
    public BaseResult<TableDataInfo<OnlineUser>> getOnlionUser(OnlineUser onlineUser){
        List<OnlineUser> onlineUserList = onlineUserService.getOnlineUserList(onlineUser);
        List<String> allSession = StpUtil.searchSessionId(StrUtil.nullToDefault(onlineUser.getToken(),""), 0, -1, false);
        int total = allSession.size();
        TableDataInfo<OnlineUser> tableDataInfo = new TableDataInfo<>();
        tableDataInfo.setRows(onlineUserList);
        tableDataInfo.setCode(HttpStatus.HTTP_OK);
        tableDataInfo.setMsg("成功");
        tableDataInfo.setTotal(total);
        return BaseResult.success(tableDataInfo);
    }

    @PostMapping("forceOffline")
    public BaseResult<?> forceOffline(@RequestBody Integer userId){
        StpUtil.kickout(userId);
        return BaseResult.success();
    }
}

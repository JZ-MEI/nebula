package com.nebula.service.service;

import com.nebula.service.domain.monitor.OnlineUser;

import java.util.List;

public interface OnlineUserService {

    List<OnlineUser> getOnlineUserList(OnlineUser onlineUser);
}

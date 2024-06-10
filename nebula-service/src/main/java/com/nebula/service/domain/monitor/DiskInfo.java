package com.nebula.service.domain.monitor;

import lombok.Data;

@Data
public class DiskInfo {

    private String diskSize;

    private String diskName;

    private String diskMountPoint;
}

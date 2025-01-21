package org.spc.file.api;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static org.spc.base.common.constant.FileCT.DISK_SIZE;


/**
 * 系统监控API
 */
@Slf4j
public class MonitorApiList {

    /**
     * 获取全局磁盘使用量
     *
     * @return 全局磁盘使用量(百分数
     */
    public Double diskUsageAmount_All() {
        double count;
        List<Integer> usedFATList = getFATOrder();
        if (usedFATList != null) {
            count = ((usedFATList.size() + 1.0) / (double) DISK_SIZE);
        } else {
            count = 0.0;
        }

        double or = count * 100;

        or = (double) Math.round(or * 100) / 100;       //保留两位
        log.debug("全局磁盘使用量 = {} %", or);
        return or;
    }

    public Double getDiskUsageAmount() {

    }
}

package org.spc.base.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "memory", url = "http://localhost:11484")
public interface MemoryClient {

    /**
     * 释放内存
     */
    @PostMapping("/memory/{pcbId}")
    void releaseMemory(@PathVariable int pcbId);

    /**
     * 分配内存
     */
    @PostMapping("/memory/{pcbId}/{s}")
    void allocateMemory(@PathVariable int pcbId, @PathVariable String s);


    /**
     * 展示内存占用情况
     */
    @PostMapping("/memory/showMemory")
    void getSysMemoryUsage();

    /**
     * 展示进程内存状态
     */
    @GetMapping("/memory/displayMemoryStatus")
    List<Integer> displayMemoryStatus();
}

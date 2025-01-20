package org.spc.base.client;

import org.spc.base.entity.process.Process;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ConcurrentHashMap;

@FeignClient(name = "process", url = "http://localhost:11485")
public interface ProcessClient {

    /**
     * 获得进程链表
     */
    @GetMapping("/process/getProcessList/")
    ConcurrentHashMap<Integer, Process> getProcessList();

}

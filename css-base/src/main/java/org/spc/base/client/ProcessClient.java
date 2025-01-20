package org.spc.base.client;

import org.spc.base.entity.process.Process;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.concurrent.ConcurrentHashMap;

@FeignClient(name = "process", url = "http://localhost:11485")
public interface ProcessClient {

    /**
     * 获得进程链表
     */
    @GetMapping("/process/getProcessList/")
    ConcurrentHashMap<Integer, Process> getProcessList();

    /**
     * 获得正在运行的进程
     */
    @GetMapping("/process/getRunningProcess/")
    Process getRunningProcess();

    /**
     * 执行命令
     */
    @PostMapping("/process/commandExecution/{order}")
    void commandExecution(@PathVariable String order);
}

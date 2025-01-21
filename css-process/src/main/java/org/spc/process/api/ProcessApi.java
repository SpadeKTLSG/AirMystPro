package org.spc.process.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spc.base.entity.process.Process;
import org.spc.process.compo.CommandExcuteCompo;
import org.spc.process.compo.ProcessSchedulerCompo;
import org.spc.process.compo.ProcessWorkCompo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 进程Controller
 */
@Slf4j
@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
public class ProcessApi {

    //Components
    @Autowired
    ProcessWorkCompo processWorkCompo;

    @Autowired
    ProcessSchedulerCompo processSchedulerCompo;

    @Autowired
    CommandExcuteCompo commandExcuteCompo;


    /**
     * 获得进程链表
     */
    @GetMapping("/process/getProcessList/")
    ConcurrentHashMap<Integer, Process> getProcessList() {
        return processWorkCompo.getProcessListArtifact().getProcessList();
    }

    /**
     * 获得正在运行的进程
     */
    @GetMapping("/process/getRunningProcess/")
    Process getRunningProcess() {
        return processSchedulerCompo.getRunningProcessArtifact().getRunningProcess();
    }

    /**
     * 执行命令
     */
    @PostMapping("/process/commandExecution/{order}")
    void commandExecution(@PathVariable String order) {
        commandExcuteCompo.commandExecution(order);
    }

    /**
     * 运行进程
     */
    @PostMapping("/process/runProcess/")
    void runProcess(Process process) throws IOException, InterruptedException {
        processWorkCompo.run(process);
    }
}

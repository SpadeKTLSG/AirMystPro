package org.spc.memory.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spc.memory.compo.InteractCompo;
import org.spc.memory.compo.MemoryClearCompo;
import org.spc.memory.compo.MemoryDisplayCompo;
import org.spc.memory.compo.MemoryUseCompo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 内存Controller
 */
@Slf4j
@RestController
@RequestMapping("/memory")
@RequiredArgsConstructor
public class MemoryApi {

    //Components
    @Autowired
    MemoryClearCompo memoryClearCompo;

    @Autowired
    MemoryUseCompo memoryUseCompo;

    @Autowired
    MemoryDisplayCompo memoryDisplayCompo;

    @Autowired
    InteractCompo interactCompo;


    /**
     * 释放内存
     */
    @PostMapping("/memory/{pcbId}")
    void releaseMemory(@PathVariable int pcbId) {
        memoryClearCompo.releaseMemory(pcbId);
    }

    /**
     * 分配内存
     */
    @PostMapping("/memory/{pcbId}/{s}")
    void allocateMemory(@PathVariable int pcbId, @PathVariable String s) {
        memoryUseCompo.allocateMemory(pcbId, s);
    }

    /**
     * 展示内存占用情况
     */
    @PostMapping("/memory/showMemory")
    void getSysMemoryUsage() {
        memoryDisplayCompo.getSysMemoryUsage();
    }

    /**
     * 展示进程内存状态
     */
    @GetMapping("/memory/displayMemoryStatus")
    List<Integer> displayMemoryStatus() {
        return interactCompo.displayStatus();
    }

}

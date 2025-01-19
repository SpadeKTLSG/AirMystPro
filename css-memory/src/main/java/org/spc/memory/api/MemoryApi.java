package org.spc.memory.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/memory")
@RequiredArgsConstructor
public class MemoryApi {


    /**
     * 释放内存
     */
    @PostMapping("/memory/{pcbId}")
    void releaseMemory(@PathVariable int pcbId) {
        //todo
    }

    /**
     * 分配内存
     */
    @PostMapping("/memory/{pcbId}/{s}")
    void allocateMemory(@PathVariable int pcbId, @PathVariable String s) {
        //todo
    }


}

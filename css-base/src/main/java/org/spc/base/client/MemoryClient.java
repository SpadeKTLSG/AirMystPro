package org.spc.base.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "memory", url = "http://localhost:11484")
public interface MemoryClient {


    @PostMapping("/memory/{pcbId}")
    void releaseMemory(@PathVariable int pcbId);

    @PostMapping("/memory/{pcbId}/{s}")
    void allocateMemory(@PathVariable int pcbId, @PathVariable String s);
}

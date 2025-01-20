package org.spc.base.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "file", url = "http://localhost:11482")
public interface FileClient {

    @GetMapping("/file/get/blockStatus")
    List<Integer> queryBlockStatus();

}

package org.spc.base.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "file", url = "http://localhost:11482")
public interface FileClient {

    /**
     * 查询文件系统的块状态
     */
    @GetMapping("/file/get/blockStatus")
    List<Integer> queryBlockStatus();

    /**
     * 获取文件系统的路径
     */
    @GetMapping("/file/getPath2Front")
    String[] givePath2Front();
}

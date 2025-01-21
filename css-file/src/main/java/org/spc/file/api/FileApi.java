package org.spc.file.api;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spc.file.compo.WithFrontCompo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文件Controller
 */
@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileApi {

    @Autowired
    WithFrontCompo withFrontCompo;

    /**
     * 查询文件系统的块状态
     */
    @GetMapping("/file/get/blockStatus")
    List<Integer> queryBlockStatus() {
        return withFrontCompo.giveBlockStatus2Front();
    }

    /**
     * 获取文件系统的路径
     */
    @GetMapping("/file/getPath2Front")
    String[] givePath2Front() {
        return withFrontCompo.givePath2Front();

    }
}

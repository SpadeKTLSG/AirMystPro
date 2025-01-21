package org.spc.base.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "front", url = "http://localhost:11483")
public interface FrontClient {


    @PostMapping("/front/sendException")
    void sendException(String message);


}

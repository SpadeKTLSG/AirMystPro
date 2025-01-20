package org.spc.base.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "file", url = "http://localhost:11482")
public interface FileClient {

    @PostMapping("/file/get/blockStatus")
    List<Integer> queryBlockStatus();

    //User
//    @GetMapping("/guest/user/remote/User/getById/{id}")
//    User getById(@PathVariable Long id);
//
//
//    @PostMapping("/guest/user/remote/User/updateById")
//    void updateById(@RequestBody User user);
//
//    //UserFunc
//    @GetMapping("/guest/user/remote/UserFunc/getById/{id}")
//    UserFunc getById_UserFunc(@PathVariable Long id);
//
//
//    @PostMapping("/guest/user/remote/UserFunc/updateById")
//    void updateById_UserFunc(@RequestBody UserFunc userFunc);


}

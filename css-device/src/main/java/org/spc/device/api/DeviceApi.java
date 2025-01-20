package org.spc.device.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spc.device.compo.DeviceManageCompo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备Controller
 */
@Slf4j
@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class DeviceApi {

    //Components
    @Autowired
    DeviceManageCompo deviceManageCompo;


}

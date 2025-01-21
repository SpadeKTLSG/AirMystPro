package org.spc.front.api;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spc.front.compo.PopupDialog;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端Controller
 */
@Slf4j
@RestController
@RequestMapping("/memory")
@RequiredArgsConstructor
public class FrontApi {


    /**
     * 发送(异常)消息到前端展示
     */
    @PostMapping("/front/sendException")
    void sendException(String content) {
        log.debug("前端接收到信息: " + content);
        //调用弹窗组件
        PopupDialog dialog = new PopupDialog(null);
        dialog.setText(content);
        dialog.setVisible(true);
    }


}

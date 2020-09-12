package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangShengli
 * @ClassName DemoController
 * @Description 用户可以体验发送已提供的各种报文
 * 当用户启动helloworld服务后，可以使用curl、postman等工具来尝试发送报文
 * @since 2020/9/12
 */

@Slf4j
@RestController
@RequestMapping("/smt/demo")
public class DemoController {

    /**
     * 为了
     *
     * @return
     */
    @PostMapping("smta-dc-dc-issue-1-v1")
    @ResponseBody
    public DrsResponse issueDigitalCurrency() {
        return null;
    }
}

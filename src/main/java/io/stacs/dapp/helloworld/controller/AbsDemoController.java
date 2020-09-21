package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.AbsBDRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author HuangShengli
 * @ClassName DemoController
 * @Description 用户可以体验发送已提供的各种报文
 * 当用户启动helloworld服务后，可以使用curl、postman、swagger等工具来尝试发送报文
 * @since 2020/9/12
 */
@Api(tags = "SMT报文体验入口")
@Slf4j
@RestController
@RequestMapping("/smt/demo/abs")
public class AbsDemoController {

    @Autowired
    Map<String, SmtDemoService> smtDemoService;

    /**
     * @return
     */
    @ApiOperation(value = "发行ABS得BD报文")
    @PostMapping("issueAbsBd")
    @ResponseBody
    public DrsResponse issueBD(@Validated @RequestBody AbsBDRequest request) {

        return smtDemoService.get("smtbd-abs-abs-issue-1-v1").doDemo(request);
    }

}

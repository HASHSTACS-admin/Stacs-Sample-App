package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.crypto.RsaKeyGeneratorUtil;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HuangShengli
 * @ClassName UtilsController
 * @Description 提供一些系统工具，比如生成RSA密钥对
 * @since 2020/9/18
 */
@Api(tags = "一些系统工具")
@Slf4j
@RestController
@RequestMapping("/system/utils")
public class UtilsController {


    @ApiOperation(value = "生成RSA密钥对", notes = "生成RSA密钥对")
    @PostMapping("/getRsaKeyPair")
    @ResponseBody
    public DrsResponse getRsaKeyPair() {

        try {
            return DrsResponse.success(RsaKeyGeneratorUtil.generateKeyPairString());
        } catch (Exception e) {
            return DrsResponse.fail("error", e.getMessage());
        }
    }
}

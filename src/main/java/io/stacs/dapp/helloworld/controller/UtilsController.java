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
 * @Description Provide an alternative way to create RSA-based asymmetric keys for registration
 * @since 2020/9/18
 */
@Api(tags = "Utility")
@Slf4j
@RestController
@RequestMapping("/system/utils")
public class UtilsController {


    @ApiOperation(value = "create RSA keypair", notes = "Create RSA keypair")
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

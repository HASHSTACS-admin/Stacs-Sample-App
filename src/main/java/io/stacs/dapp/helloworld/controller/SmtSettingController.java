package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.constant.IdentityType;
import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.IdentityRequest;
import io.stacs.dapp.helloworld.vo.demo.InstitutionBDRequest;
import io.stacs.dapp.helloworld.vo.demo.PermissionRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 设置类报文体验，设置bd，权限，identity等等
 * 当用户启动helloworld服务后，可以使用curl、postman、swagger等工具来尝试发送报文
 *
 * @author Su Wenbo
 * @since 2020/9/22
 */
@Api(tags = "SMT报文体验入口")
@Slf4j
@RestController
@RequestMapping("/smt/demo/")
public class SmtSettingController {

    @Autowired
    Map<String, SmtDemoService> smtDemoService;

    /**
     * 列表格式的权限创建报文API
     *
     * @param request the request
     * @return the permission
     */
    @ApiOperation(value = "设置列表格式的权限报文")
    @PostMapping("/setPermission")
    public DrsResponse setPermission(@Validated @RequestBody PermissionRequest request) {
        return smtDemoService.get("smtpm-fix-permission-set-1-v1").doDemo(request);
    }

    /**
     * 机构业务类的BD创建报文API
     *
     * @param request the request
     * @return the permission
     */
    @ApiOperation(value = "设置机构业务类的BD报文")
    @PostMapping("/setInstitutionBD")
    public DrsResponse setInstitutionBD(@Validated @RequestBody InstitutionBDRequest request) {
        return smtDemoService.get("smtbd-institution-business-special-1-v1").doDemo(request);
    }

    /**
     * 设置地址身份信息的报文API
     *
     * @param request      the request
     * @param identityType the identity type
     * @return the permission
     */
    @ApiOperation(value = "设置地址身份信息的报文")
    @PostMapping("/setIdentity")
    public DrsResponse setIdentity(@Validated @RequestBody IdentityRequest request,
                                   @RequestParam IdentityType identityType) {
        request.setIdentityType(identityType);
        return smtDemoService.get("smti-individual-identity-set-1-v1").doDemo(request);
    }

}

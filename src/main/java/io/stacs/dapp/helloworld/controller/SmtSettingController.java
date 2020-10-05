package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.service.SmtDemoService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.IndividualIdentityRequest;
import io.stacs.dapp.helloworld.vo.demo.InstitutionBDRequest;
import io.stacs.dapp.helloworld.vo.demo.InstitutionIdentityRequest;
import io.stacs.dapp.helloworld.vo.demo.PermissionRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * SMT Format Configuration for BD, Permission, Identity
 * After starting up this app, user can use curl, postman, swagger and other tools to try out the available APIs
 *
 * @author Su Wenbo
 * @since 2020/9/22
 */
@Api(tags = "SMT Message Endpoints")
@Slf4j
@RestController
@RequestMapping("/smt/demo/")
public class SmtSettingController {

    @Autowired
    Map<String, SmtDemoService> smtDemoService;

    /**
     * Use SMT Format to create Permission on the chain
     *
     * @param request the request
     * @return the permission
     */
    @ApiOperation(value = "/endpoint - setup permission using SMT format:smtpm-fix-permission-set-1-v1")
    @PostMapping("/setPermission")
    public DrsResponse setPermission(@Validated @RequestBody PermissionRequest request) {
        return smtDemoService.get("smtpm-fix-permission-set-1-v1").doDemo(request);
    }

    /**
     * Set BD for the Institution
     *
     * @param request the request
     * @return the permission
     */
    @ApiOperation(value = "/endpoint - Setup Institution BD:smtbd-institution-business-special-1-v1")
    @PostMapping("/setInstitutionBD")
    public DrsResponse setInstitutionBD(@Validated @RequestBody InstitutionBDRequest request) {
        return smtDemoService.get("smtbd-institution-business-special-1-v1").doDemo(request);
    }

    /**
     * Setup Identity for Individual Type using SMT format
     *
     * @param request the request
     * @return the permission
     */
    @ApiOperation(value = "/endpoint - Setup Identity for Individual Type using SMT format:smti-individual-identity-set-1-v1")
    @PostMapping("/setIndividualIdentity")
    public DrsResponse setIndividualIdentity(@Validated @RequestBody IndividualIdentityRequest request) {
        Assert.hasLength(request.getHeader().getSmtCode(), "Smt code cannot be empty");
        return smtDemoService.get("smti-individual-identity-set-1-v1").doDemo(request);
    }

    /**
     * Setup Identity for Institution Type using SMT format
     *
     * @param request the request
     * @return the permission
     */
    @ApiOperation(value = "/endpoint - Setup Identity for Institution Type using SMT format:smti-institution-identity-set-1-v1")
    @PostMapping("/setInstitutionIdentity")
    public DrsResponse setInstitutionIdentity(@Validated @RequestBody InstitutionIdentityRequest request) {
        Assert.hasLength(request.getHeader().getSmtCode(), "Smt code cannot be empty");
        return smtDemoService.get("smti-institution-identity-set-1-v1").doDemo(request);
    }

}

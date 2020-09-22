package io.stacs.dapp.helloworld.controller;

import io.stacs.dapp.helloworld.service.BizQueryService;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: yezaiyong
 * @create: 2020-09-21 16:37
 **/
@Api(tags = "SMT BIZ 查询入口")
@Slf4j
@RestController
@RequestMapping("/smt/biz/query")
public class BizQueryController {

    @Autowired
    private BizQueryService bizQueryService;
    /**
     *根据商户号查询Abs
     * @return
     */
    @ApiOperation(value = "根据商户号查询Abs")
    @GetMapping("absList")
    @ResponseBody
    public DrsResponse<List<AssetAbsDemoVO>> absList(@ApiParam(value = "商户号") @Valid String identifierId) {
        return DrsResponse.success(bizQueryService.absList(identifierId));
    }


    /**
     *根据商户号查询BD
     * @return
     */
    @ApiOperation(value = "根据商户号查询BD")
    @GetMapping("bdList")
    @ResponseBody
    public DrsResponse<List<SmtBdDemoVO>> bdList(@ApiParam(value = "商户号") @Valid String identifierId) {
        return DrsResponse.success(bizQueryService.bdList(identifierId));
    }

    /**
     *根据商户号查询permission
     * @return
     */
    @ApiOperation(value = "根据商户号查询Permission")
    @GetMapping("permissionList")
    @ResponseBody
    public DrsResponse<List<SmtPermissionDemoVO>> permissionList(@ApiParam(value = "商户号") @Valid String identifierId) {
        return DrsResponse.success(bizQueryService.permissionList(identifierId));
    }


    /**
     * 根据bdId查询bd function
     * @return
     */
    @ApiOperation(value = "根据bdId查询bd function")
    @GetMapping("functionList")
    @ResponseBody
    public DrsResponse<List<BdFunctionPermissionRelationDemoVO>> functionList(@ApiParam(value = "bdId") @Valid String bdId) {
        return DrsResponse.success(bizQueryService.functionList(bdId));
    }
}
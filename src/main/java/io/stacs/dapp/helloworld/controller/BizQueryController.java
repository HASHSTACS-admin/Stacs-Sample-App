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
     * Uses Merchant Id to query Asset Backed Securities (ABS)
     * @return
     */
    @ApiOperation(value = "Use Merchant Id to query ABS")
    @GetMapping("absList")
    @ResponseBody
    public DrsResponse<List<AssetAbsDemoVO>> absList(@ApiParam(value = "MerchantId") @Valid String identifierId) {
        return DrsResponse.success(bizQueryService.absList(identifierId));
    }


    /**
     *Uses Merchant Id to query BD
     * @return
     */
    @ApiOperation(value = "Uses Merchant Id to query BD")
    @GetMapping("bdList")
    @ResponseBody
    public DrsResponse<List<SmtBdDemoVO>> bdList(@ApiParam(value = "MerchantId") @Valid String identifierId) {
        return DrsResponse.success(bizQueryService.bdList(identifierId));
    }

    /**
     *Uses Merchant Id to query Permission
     * @return
     */
    @ApiOperation(value = "Users Merchant Id to query Permission")
    @GetMapping("permissionList")
    @ResponseBody
    public DrsResponse<List<SmtPermissionDemoVO>> permissionList(@ApiParam(value = "MerchantId") @Valid String identifierId) {
        return DrsResponse.success(bizQueryService.permissionList(identifierId));
    }


    /**
     * Uses ID of BD to query status of BD on the blockchain
     * @return
     */
    @ApiOperation(value = "uses ID of BD to query status of BD on the blockchain")
    @GetMapping("functionList")
    @ResponseBody
    public DrsResponse<List<BdFunctionPermissionRelationDemoVO>> functionList(@ApiParam(value = "bdId") @Valid String bdId) {
        return DrsResponse.success(bizQueryService.functionList(bdId));
    }
}
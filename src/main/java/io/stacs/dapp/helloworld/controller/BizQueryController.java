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
@Api(tags = "SMT Business Query endpoint")
@Slf4j
@RestController
@RequestMapping("/smt/biz/query")
public class BizQueryController {

    @Autowired
    private BizQueryService bizQueryService;
    /**
     * Query of ABS by merchant id
     * @return
     */
    @ApiOperation(value = "Query of ABS by merchant id")
    @GetMapping("absList")
    @ResponseBody
    public DrsResponse<List<AssetAbsDemoVO>> absList(@ApiParam(value = "merchant id") @RequestParam String identifierId) {
        return DrsResponse.success(bizQueryService.absList(identifierId));
    }


    /**
     *Query BD by merchant id
     * @return
     */
    @ApiOperation(value = "Query BD by merchant id")
    @GetMapping("bdList")
    @ResponseBody
    public DrsResponse<List<SmtBdDemoVO>> bdList(@ApiParam(value = "merchant id") @RequestParam String identifierId) {
        return DrsResponse.success(bizQueryService.bdList(identifierId));
    }

    /**
     *Query Permission by merchant id
     * @return
     */
    @ApiOperation(value = "Query Permission by merchant id")
    @GetMapping("permissionList")
    @ResponseBody
    public DrsResponse<List<SmtPermissionDemoVO>> permissionList(@ApiParam(value = "merchant id") @RequestParam String identifierId) {
        return DrsResponse.success(bizQueryService.permissionList(identifierId));
    }


    /**
     * Query BD by BdId
     * @return
     */
    @ApiOperation(value = "Query BD by BdId")
    @GetMapping("functionList")
    @ResponseBody
    public DrsResponse<List<BdFunctionPermissionRelationDemoVO>> functionList(@ApiParam(value = "bdId") @RequestParam String bdId) {
        return DrsResponse.success(bizQueryService.functionList(bdId));
    }
}
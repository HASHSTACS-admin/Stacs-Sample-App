package io.stacs.dapp.helloworld.service;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.*;

import java.util.List;

/**
 * 业务查询
 */
public interface BizQueryService {

    /**
     * 查询abs
     *
     * @param identifierId
     * @return
     */
    List<AssetAbsDemoVO> absList(String identifierId);


    /**
     * 查询bd
     *
     * @param identifierId
     * @return
     */
    List<SmtBdDemoVO> bdList(String identifierId);

    /**
     * 查询permission
     *
     * @param identifierId
     * @return
     */
    List<SmtPermissionDemoVO> permissionList(String identifierId);

    /**
     * 查询function
     *
     * @param bdId
     * @return
     */
    List<BdFunctionPermissionRelationDemoVO> functionList(String bdId);


}

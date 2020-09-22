package io.stacs.dapp.helloworld.service;

import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.demo.*;

import java.util.List;

/**
 * Query API
 */
public interface BizQueryService {

    /**
     * Query ABS by Merchant Id
     *
     * @param identifierId
     * @return
     */
    List<AssetAbsDemoVO> absList(String identifierId);


    /**
     *  Query BD by Merchant Id
     *
     * @param identifierId
     * @return
     */
    List<SmtBdDemoVO> bdList(String identifierId);

    /**
     * Query Permission by Merchant Id
     *
     * @param identifierId
     * @return
     */
    List<SmtPermissionDemoVO> permissionList(String identifierId);

    /**
     * Query by BD ID
     *
     * @param bdId
     * @return
     */
    List<BdFunctionPermissionRelationDemoVO> functionList(String bdId);


}

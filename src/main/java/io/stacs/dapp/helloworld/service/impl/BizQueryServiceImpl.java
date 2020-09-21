package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSON;
import io.stacs.dapp.helloworld.dao.*;
import io.stacs.dapp.helloworld.dao.po.*;
import io.stacs.dapp.helloworld.service.BizQueryService;
import io.stacs.dapp.helloworld.service.SmtNotifyService;
import io.stacs.dapp.helloworld.vo.*;
import io.stacs.dapp.helloworld.vo.demo.AssetAbsDemoVO;
import io.stacs.dapp.helloworld.vo.demo.BdFunctionPermissionRelationDemoVO;
import io.stacs.dapp.helloworld.vo.demo.SmtBdDemoVO;
import io.stacs.dapp.helloworld.vo.demo.SmtPermissionDemoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 业务查询
 */
@Slf4j
@Service
public class BizQueryServiceImpl implements BizQueryService {

    @Autowired
    private AssetAbsDao assetAbsDao;
    @Autowired
    private SmtBdDao smtBdDao;
    @Autowired
    private SmtPermissionDao smtPermissionDao;
    @Autowired
    private BdFunctionPermissionRelationDao bdFunctionPermissionRelationDao;
    /**
     * 查询abs
     *
     * @param identifierId
     * @return
     */
    @Override
    public List<AssetAbsDemoVO> absList(String identifierId) {
        List<AssetAbs> absList=assetAbsDao.findByIdentifierId(identifierId);
        if (CollectionUtils.isEmpty(absList)){
            return null;
        }
        List<AssetAbsDemoVO>  demoVOS=new ArrayList<>();
        for (AssetAbs assetAbs:absList){
            AssetAbsDemoVO vo=AssetAbsDemoVO.parsePO(assetAbs);
            demoVOS.add(vo);
        }
        return demoVOS;
    }

    /**
     * 查询bd
     *
     * @param identifierId
     * @return
     */
    @Override
    public List<SmtBdDemoVO> bdList(String identifierId) {
        List<SmtBd> bdList=smtBdDao.findByIdentifierId(identifierId);
        if (CollectionUtils.isEmpty(bdList)){
            return null;
        }
        List<SmtBdDemoVO> demoVOS=new ArrayList<>();
        for (SmtBd smtBd:bdList){
            SmtBdDemoVO vo=SmtBdDemoVO.parsePO(smtBd);
            demoVOS.add(vo);
        }
        return demoVOS;
    }

    /**
     * 查询permission
     *
     * @param identifierId
     * @return
     */
    @Override
    public List<SmtPermissionDemoVO> permissionList(String identifierId) {
        List<SmtPermission> permissionList=smtPermissionDao.findByIdentifierId(identifierId);
        if (CollectionUtils.isEmpty(permissionList)){
            return null;
        }
        List<SmtPermissionDemoVO> demoVOS=new ArrayList<>();
        for (SmtPermission permission:permissionList){
            SmtPermissionDemoVO vo=SmtPermissionDemoVO.parsePO(permission);
            demoVOS.add(vo);
        }
        return demoVOS;
    }

    /**
     * 查询function
     *
     * @param bdId
     * @return
     */
    @Override
    public List<BdFunctionPermissionRelationDemoVO> functionList(String bdId) {
        List<BdFunctionPermissionRelation> functionList=bdFunctionPermissionRelationDao.findByBdId(bdId);
        if (CollectionUtils.isEmpty(functionList)){
            return null;
        }
        List<BdFunctionPermissionRelationDemoVO> demoVOS=new ArrayList<>();
        for (BdFunctionPermissionRelation relation:functionList){
            BdFunctionPermissionRelationDemoVO vo=BdFunctionPermissionRelationDemoVO.parsePO(relation);
            demoVOS.add(vo);
        }
        return demoVOS;
    }
}

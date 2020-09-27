package io.stacs.dapp.helloworld.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.stacs.dapp.helloworld.config.DrsConfig;
import io.stacs.dapp.helloworld.constant.AssetOperationType;
import io.stacs.dapp.helloworld.constant.PageConstant;
import io.stacs.dapp.helloworld.constant.StatusEnum;
import io.stacs.dapp.helloworld.dao.AssetOperationDao;
import io.stacs.dapp.helloworld.dao.po.AssetOperation;
import io.stacs.dapp.helloworld.httpclient.DrsClient;
import io.stacs.dapp.helloworld.service.SmtQueryService;
import io.stacs.dapp.helloworld.utils.CommonUtil;
import io.stacs.dapp.helloworld.vo.DrsAssetHoldersResult;
import io.stacs.dapp.helloworld.vo.DrsResponse;
import io.stacs.dapp.helloworld.vo.DrsSmtMessage;
import io.stacs.dapp.helloworld.vo.demo.AssetHoldersRequest;
import io.stacs.dapp.helloworld.vo.demo.BalanceOfRequest;
import io.stacs.dapp.helloworld.vo.demo.QuerySmtResultRequest;
import io.stacs.dapp.helloworld.vo.drs.AddressCreateRequest;
import io.stacs.dapp.helloworld.vo.drs.AssetHoldersQueryRequest;
import io.stacs.dapp.helloworld.vo.drs.BalanceQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Huang Shengli
 * @date 2020-09-13
 */

@Service
public class SmtQueryServiceImpl implements SmtQueryService {

    @Autowired
    private DrsConfig drsConfig;

    @Autowired
    private AssetOperationDao assetOperationDao;

    /**
     * 获取地址
     *
     * @return
     */
    @Override
    public DrsResponse getAddress() {

        AddressCreateRequest createRequest = AddressCreateRequest.builder()
                //生成uuid
                .uuid(CommonUtil.uuid())
                //设置商户号
                .identifierId(drsConfig.getMyIdentifierId())
                .build();
        JSONObject response = DrsClient.post(drsConfig.getCreateAddressUrl(), createRequest);

        return JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<String>>() {
        });
    }

    /**
     * 余额查询
     *
     * @param request
     * @return
     */
    @Override
    public DrsResponse balanceOf(BalanceOfRequest request) {
        BalanceQueryRequest drsBalanceQueryRequest = BalanceQueryRequest.builder()
                //查询地址
                .address(request.getAddress())
                //查询的资产ID
                .assetId(request.getAssetId())
                //商户号
                .identifierId(drsConfig.getMyIdentifierId())
                .build();
        JSONObject response = DrsClient.post(drsConfig.getBalanceOfUrl(), drsBalanceQueryRequest);
        return JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<BigDecimal>>() {
        });
    }

    /**
     * 根据商户号和uuid查询
     *
     * @param request
     * @return
     */
    @Override
    public DrsResponse querySmtResult(QuerySmtResultRequest request) {
        JSONObject response = DrsClient.post(drsConfig.getQuerySmtByUuidUrl(), request);
        return JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<DrsSmtMessage>>() {
        });
    }

    /**
     * 查询资产的所有地址和余额
     *
     * @param request the request
     * @return drs response
     */
    @Override
    public DrsResponse assetHolders(AssetHoldersRequest request) {
        String assetId = request.getAssetId();
        Long operationId = request.getOperationId();

        //判断operation时候存在，是否上链完成, 得到blockHeight
        String blockHeight = null;
        if (Objects.nonNull(operationId)) {
            AssetOperation assetOperation = assetOperationDao.findByOperationId(operationId);
            if (Objects.isNull(assetOperation)) {
                return DrsResponse.fail("error", "Operation doesn't exist!");
            }
            if (!StatusEnum.ChainStatus.SUCCESS.getCode().equals(assetOperation.getStatus())) {
                return DrsResponse.fail("error", "Operation incomplete or failed!");
            }
            if (AssetOperationType.BUYBACK_FREEZE.getCode().equals(assetOperation.getType())
                    && !assetId.equals(assetOperation.getAssetId())) {
                return DrsResponse.fail("error", "The frozen asset id does not match the parameter asset id!");
            }
            blockHeight = assetOperation.getBlockHeight();
        }

        //调用DRS查询资产持有者，初始值第0条，每页200
        DrsResponse<DrsAssetHoldersResult> drsResponse = doQueryAssetHolders(assetId, blockHeight,
                PageConstant.DEFAULT_PAGE_NO, PageConstant.DEFAULT_PAGE_SIZE);

        //如何调用失败，返回
        if (!drsResponse.success()) {
            return drsResponse;
        }

        //调用成功，组装返回数据
        DrsAssetHoldersResult result = DrsAssetHoldersResult.builder()
                .blockHeight(drsResponse.getData().getBlockHeight())
                .totalElements(drsResponse.getData().getTotalElements())
                .list(drsResponse.getData().getList())
                .build();

        //如果当前页不是最后一页，循环拉取数据
        blockHeight = drsResponse.getData().getBlockHeight();
        int pageNo = drsResponse.getData().getNumber();
        int totalPages = drsResponse.getData().getTotalPages();
        while (++pageNo < totalPages) {
            DrsResponse<DrsAssetHoldersResult> loopDrsResponse = doQueryAssetHolders(assetId, blockHeight, pageNo, PageConstant.DEFAULT_PAGE_SIZE);
            //如何调用失败，返回
            if (!loopDrsResponse.success()) {
                return loopDrsResponse;
            }
            result.getList().addAll(loopDrsResponse.getData().getList());
        }
        return DrsResponse.success(result);
    }

    private DrsResponse<DrsAssetHoldersResult> doQueryAssetHolders(String assetId, String blockHeight, int page, int size) {
        //调用DRS查询资产持有者，初始值第0条，每页200
        AssetHoldersQueryRequest assetHoldersQueryRequest = AssetHoldersQueryRequest.builder()
                .assetId(assetId)
                .blockHeight(blockHeight)
                .page(page)
                .size(size)
                .build();
        JSONObject response = DrsClient.post(drsConfig.getAssetHolderUrl(), assetHoldersQueryRequest);
        return JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<DrsAssetHoldersResult>>() {
        });
    }

}

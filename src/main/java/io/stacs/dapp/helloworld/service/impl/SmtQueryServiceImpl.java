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
     * Get wallet address
     *
     * @return
     */
    @Override
    public DrsResponse getAddress() {

        AddressCreateRequest createRequest = AddressCreateRequest.builder()
                //Create uuid
                .uuid(CommonUtil.uuid())
                //merchant id
                .identifierId(drsConfig.getMyIdentifierId())
                .build();
        JSONObject response = DrsClient.post(drsConfig.getCreateAddressUrl(), createRequest);

        return JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<String>>() {
        });
    }

    /**
     * query balance of wallet address for an asset
     *
     * @param request
     * @return
     */
    @Override
    public DrsResponse balanceOf(BalanceOfRequest request) {
        BalanceQueryRequest drsBalanceQueryRequest = BalanceQueryRequest.builder()
                //address to be queried
                .address(request.getAddress())
                //asset id
                .assetId(request.getAssetId())
                //merchant id
                .identifierId(drsConfig.getMyIdentifierId())
                .build();
        JSONObject response = DrsClient.post(drsConfig.getBalanceOfUrl(), drsBalanceQueryRequest);
        return JSONObject.parseObject(response.toJSONString(), new TypeReference<DrsResponse<BigDecimal>>() {
        });
    }

    /**
     * query tx outcomes based on merchant id and uuid
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
     * Query Holders and their balance for an asset
     *
     * @param request the request
     * @return drs response
     */
    @Override
    public DrsResponse assetHolders(AssetHoldersRequest request) {
        String assetId = request.getAssetId();
        Long operationId = request.getOperationId();

        //Check if tx on the chain has completed via block height
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

        //Send Query Request to DRS, set pagination to page 0 and 200 rows to be returned
        DrsResponse<DrsAssetHoldersResult> drsResponse = doQueryAssetHolders(assetId, blockHeight,
                PageConstant.DEFAULT_PAGE_NO, PageConstant.DEFAULT_PAGE_SIZE);

        //if query to DRS fails, return response
        if (!drsResponse.success()) {
            return drsResponse;
        }

        //Successful query to the DRS, construct the response
        DrsAssetHoldersResult result = DrsAssetHoldersResult.builder()
                .blockHeight(drsResponse.getData().getBlockHeight())
                .totalElements(drsResponse.getData().getTotalElements())
                .list(drsResponse.getData().getList())
                .build();

        //if current page is not the last page, loop through
        blockHeight = drsResponse.getData().getBlockHeight();
        int pageNo = drsResponse.getData().getNumber();
        int totalPages = drsResponse.getData().getTotalPages();
        while (++pageNo < totalPages) {
            DrsResponse<DrsAssetHoldersResult> loopDrsResponse = doQueryAssetHolders(assetId, blockHeight, pageNo, PageConstant.DEFAULT_PAGE_SIZE);
            //unsuccessful response
            if (!loopDrsResponse.success()) {
                return loopDrsResponse;
            }
            result.getList().addAll(loopDrsResponse.getData().getList());
        }
        return DrsResponse.success(result);
    }

    private DrsResponse<DrsAssetHoldersResult> doQueryAssetHolders(String assetId, String blockHeight, int page, int size) {
        //Call DRS to get asset holders and their balance
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

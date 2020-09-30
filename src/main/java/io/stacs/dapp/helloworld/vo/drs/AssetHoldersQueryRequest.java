package io.stacs.dapp.helloworld.vo.drs;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

/**
 * Asset Holder
 *
 * @author Su Wenbo
 * @since 2020/9/21
 */
@Data
@Builder
public class AssetHoldersQueryRequest implements Serializable {

    /**
     * assetId
     */
    @NonNull
    private String assetId;

    /**
     * block Height
     */
    private String blockHeight;

    /**
     * page no
     */
    @NonNull
    private Integer page;

    /**
     * page size
     */
    @NonNull
    private Integer size;

}

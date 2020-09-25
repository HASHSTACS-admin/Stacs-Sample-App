package io.stacs.dapp.helloworld.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Su Wenbo
 * @since 2020/9/25
 */
@Data
@Builder
public class DrsAssetHoldersResult implements Serializable {

    private List<DrsAssetAddressBalance> list;

    private Long totalElements;

    @JsonIgnore
    private Integer totalPages;

    @JsonIgnore
    private Integer size;

    @JsonIgnore
    private Integer number;

    private String blockHeight;

}

package com.github.catalpaflat.paypal.model.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * 附加信息
 *
 * @author CatalpaFlat
 */
@Getter
@Setter
public class OptionsBO {
    private String customField;
    private String description;
    private Boolean storeInVaultOnSuccess;
}

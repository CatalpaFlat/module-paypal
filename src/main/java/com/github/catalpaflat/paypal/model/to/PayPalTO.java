package com.github.catalpaflat.paypal.model.to;

import lombok.Getter;
import lombok.Setter;

/**
 * @author CatalpaFlat
 */
@Getter
@Setter
public class PayPalTO {
    private String sandbox_access_token;
    private String product_access_token;
    private String environment;
}

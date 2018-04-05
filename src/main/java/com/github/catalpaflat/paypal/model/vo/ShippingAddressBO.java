package com.github.catalpaflat.paypal.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 送货地址
 *
 * @author CatalpaFlat
 */
@Getter
@Setter
public class ShippingAddressBO {
    /**
     * 姓名 Jen
     */
    private String firstName;
    /**
     * 姓氏 Smith
     */
    private String lastName;
    /**
     * 国家 Braintree
     */
    private String company;
    /**
     * 街道 1 E 1st St
     */
    private String streetAddress;
    /**
     * 详细地址 Suite 403
     */
    private String extendedAddress;
    /**
     * 位置 Bartlett
     */
    private String locality;
    /**
     * 地区 IL
     */
    private String region;
    /**
     * 邮政编码 60103
     */
    private String postalCode;
    /**
     * 国家代码 US
     */
    private String countryCodeAlpha2;
}

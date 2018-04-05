package com.github.catalpaflat.paypal.model.vo;

import com.github.catalpaflat.paypal.model.bo.OptionsBO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author CatalpaFlat
 */
public class PayPalVO {
    @Getter
    @Setter
    private String paymentMethodNonce;
    @Getter
    @Setter
    private String orderNum;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private BigDecimal amount;
    @Getter
    @Setter
    private String merchantAccountId;
    @Getter
    @Setter
    private Boolean isOptions;
    @Getter
    @Setter
    private Boolean isShippingAddress;
    @Getter
    @Setter
    private OptionsBO optionsVO;
    @Getter
    @Setter
    private ShippingAddressBO shippingAddressVO;

    private PayPalVO(String paymentMethodNonce, String ordernum,
                     BigDecimal amount, String merchantAccountId) {
        this.paymentMethodNonce = paymentMethodNonce;
        this.orderNum = ordernum;
        this.amount = amount;
        this.merchantAccountId = merchantAccountId;
    }

    public class PayPalVOBuider {
        private PayPalVO payPalVO;

        public PayPalVOBuider(String paymentMethodNonce, String ordernum,
                              BigDecimal amount, String merchantAccountId) {
            this.payPalVO = new PayPalVO(paymentMethodNonce, ordernum, amount, merchantAccountId);
        }

        private PayPalVO buildPerson() {
            payPalVO.isOptions = false;
            payPalVO.isShippingAddress = false;
            return payPalVO;
        }

        public PayPalVO setOptionsBO(OptionsBO optionsVO) {
            payPalVO.setOptionsVO(optionsVO);
            return this.buildPerson();
        }


        public PayPalVO setName(String name) {
            payPalVO.setName(name);
            return this.buildPerson();
        }

        public PayPalVO setShippingAddressBO(ShippingAddressBO shippingAddressVO) {
            payPalVO.setShippingAddressVO(shippingAddressVO);
            return this.buildPerson();
        }

        public PayPalVO isOptions(Boolean options) {
            payPalVO.isOptions = options;
            return this.buildPerson();
        }


        public PayPalVO isShippingAddress(Boolean shippingaddress) {
            payPalVO.isShippingAddress = shippingaddress;
            return this.buildPerson();
        }
    }
}

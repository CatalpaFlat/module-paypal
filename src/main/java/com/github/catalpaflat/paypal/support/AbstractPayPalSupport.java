package com.github.catalpaflat.paypal.support;

import com.braintreegateway.BraintreeGateway;
import com.github.catalpaflat.paypal.constant.PayPalConstant;
import com.github.catalpaflat.paypal.exception.PayPalException;
import com.github.catalpaflat.paypal.model.to.PayPalTO;
import com.github.catalpaflat.paypal.properties.PayPalProperties;
import org.apache.commons.lang.StringUtils;

/**
 * @author CatalpaFlat
 */
abstract class AbstractPayPalSupport {
    private PayPalProperties payPalConstant;
    private String sandboxAccessToken;
    private String productAccessToken;
    private String environment;

    AbstractPayPalSupport(PayPalProperties payPalConstant) {
        this.payPalConstant = payPalConstant;
        initPayPal();
    }

    private void initPayPal() {

        PayPalTO paypal = payPalConstant.getPaypal();

        if (paypal == null) {
            throw new PayPalException("PayPal is empty");
        }
        this.sandboxAccessToken = paypal.getSandbox_access_token();
        this.productAccessToken = paypal.getProduct_access_token();
        this.environment = paypal.getEnvironment();
    }

    String obtainAccessTokenByEnvironment() {
        if (StringUtils.isBlank(environment)) {
            throw new PayPalException("environment is blank");
        }

        if (environment.trim().compareToIgnoreCase(PayPalConstant.PAYPAL_ENVIRONMENT_PRODUCT.trim()) == 0) {
            return productAccessToken;
        }

        if (environment.trim().compareToIgnoreCase(PayPalConstant.PAYPAL_ENVIRONMENT_SANBOX.trim()) == 0) {
            return sandboxAccessToken;
        }

        throw new PayPalException("environment is error");

    }

    BraintreeGateway obtainBraintreeGateway() {
        return new BraintreeGateway(obtainAccessTokenByEnvironment());
    }
}

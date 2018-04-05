package com.github.catalpaflat.paypal.support;

import com.braintreegateway.BraintreeGateway;
import com.github.catalpaflat.paypal.constant.PayPalConstant;
import com.github.catalpaflat.paypal.properties.PayPalProperties;
import com.github.catalpaflat.paypal.model.to.PayPalTO;
import com.sun.javafx.tools.packager.PackagerException;
import org.apache.commons.lang.StringUtils;

/**
 * @author CatalpaFlat
 */
abstract class AbstractPayPalSupport {
    private PayPalProperties payPalConstant;
    private String sandboxAccessToken;
    private String productAccessToken;
    private String environment;

    AbstractPayPalSupport(PayPalProperties payPalConstant) throws PackagerException {
        this.payPalConstant = payPalConstant;
        initPayPal();
    }

    private void initPayPal() throws PackagerException {

        PayPalTO paypal = payPalConstant.getPaypal();

        if (paypal == null) {
            throw new PackagerException("PayPal is empty");
        }
        this.sandboxAccessToken = paypal.getSandbox_access_token();
        this.productAccessToken = paypal.getProduct_access_token();
        this.environment = paypal.getEnvironment();
    }

    String obtainAccessTokenByEnvironment() throws PackagerException {
        if (StringUtils.isBlank(environment)) {
            throw new PackagerException("environment is blank");
        }

        if (environment.trim().compareToIgnoreCase(PayPalConstant.PAYPAL_ENVIRONMENT_PRODUCT.trim()) == 0) {
            return productAccessToken;
        }

        if (environment.trim().compareToIgnoreCase(PayPalConstant.PAYPAL_ENVIRONMENT_SANBOX.trim()) == 0) {
            return sandboxAccessToken;
        }

        throw new PackagerException("environment is error");

    }

    BraintreeGateway obtainBraintreeGateway() throws PackagerException {
        return new BraintreeGateway(obtainAccessTokenByEnvironment());
    }
}

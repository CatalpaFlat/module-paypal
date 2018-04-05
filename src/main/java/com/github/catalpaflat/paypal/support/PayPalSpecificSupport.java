package com.github.catalpaflat.paypal.support;

import com.braintreegateway.*;
import com.github.catalpaflat.paypal.exception.PayPalException;
import com.github.catalpaflat.paypal.model.bo.OptionsBO;
import com.github.catalpaflat.paypal.model.vo.PayPalVO;
import com.github.catalpaflat.paypal.model.vo.ReturnVO;
import com.github.catalpaflat.paypal.model.vo.ShippingAddressBO;
import com.github.catalpaflat.paypal.properties.PayPalProperties;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author CatalpaFlat
 */
public class PayPalSpecificSupport extends AbstractPayPalSupport {
    private String accessToken;

    public PayPalSpecificSupport(PayPalProperties payPalConstant) {
        super(payPalConstant);
        accessToken = obtainAccessTokenByEnvironment();
    }

    public String obtainPublicAccessToken() {
        BraintreeGateway gateway = new BraintreeGateway(accessToken);
        return gateway.clientToken().generate();
    }

    public Result<Transaction> placeOrder(PayPalVO payPalVO) {
        TransactionRequest request = new TransactionRequest()
                //付款金额
                .amount(payPalVO.getAmount())
                //指定货币
                .merchantAccountId(payPalVO.getMerchantAccountId())
                //付款方式随机数
                .paymentMethodNonce(payPalVO.getPaymentMethodNonce())
                //付款订单
                .orderId(payPalVO.getOrderNum())
                .descriptor()
                //订单描述
                .name(payPalVO.getName())
                .done();

        Boolean isShippingAddress = payPalVO.getIsShippingAddress();
        if (isShippingAddress) {
            ShippingAddressBO shippingAddressVO = payPalVO.getShippingAddressVO();
            if (shippingAddressVO == null) {
                throw new PayPalException("shippingAddress is empty");
            }
            request.shippingAddress()
                    .firstName(shippingAddressVO.getFirstName())
                    .lastName(shippingAddressVO.getLastName())
                    //国家
                    .company(shippingAddressVO.getCompany())
                    //街道
                    .streetAddress(shippingAddressVO.getStreetAddress())
                    //详细地址
                    .extendedAddress(shippingAddressVO.getExtendedAddress())
                    //未知
                    .locality(shippingAddressVO.getLocality())
                    //地区
                    .region(shippingAddressVO.getRegion())
                    //邮政编码
                    .postalCode(shippingAddressVO.getPostalCode())
                    //国家代码
                    .countryCodeAlpha2(shippingAddressVO.getCountryCodeAlpha2())
                    .done();
        }

        Boolean isOptions = payPalVO.getIsOptions();
        if (isOptions) {
            OptionsBO optionsVO = payPalVO.getOptionsVO();
            if (optionsVO == null) {
                throw new PayPalException("options is empty");
            }
            //附加信息
            request.options().
                    paypal().
                    customField(optionsVO.getCustomField()).
                    description(optionsVO.getDescription()).
                    done().
                    storeInVaultOnSuccess(optionsVO.getStoreInVaultOnSuccess()).
                    done();
        }

        return obtainBraintreeGateway().transaction().sale(request);
    }

    public StringBuilder verifyErrorResult(ValidationErrors errors) {
        StringBuilder errorResult = new StringBuilder();
        //校验结果
        List<ValidationError> allDeepValidationErrors = errors.getAllDeepValidationErrors();
        int size = 0;
        int errorSize = allDeepValidationErrors.size();
        for (ValidationError error : allDeepValidationErrors) {
            if (size == errorSize) {
                errorResult.append(error.getMessage());
            } else {
                errorResult.append(error.getMessage()).append(",");
            }
            size++;
        }
        return errorResult;
    }

    public Result<Transaction> refund(ReturnVO returnVO) {
        BigDecimal amount = returnVO.getAmount();
        String orderId = returnVO.getOrderId();
        String transactionId = returnVO.getTransactionId();
        if (StringUtils.isBlank(orderId) && amount == null) {
            return obtainBraintreeGateway().transaction().refund(transactionId);
        }
        if (StringUtils.isBlank(orderId) && amount != null) {
            return obtainBraintreeGateway().transaction().refund(transactionId, amount);
        }

        if (StringUtils.isNotBlank(orderId) && amount != null) {
            TransactionRefundRequest transactionRefundRequest = new TransactionRefundRequest()
                    .amount(amount)
                    .orderId(orderId);
            return obtainBraintreeGateway().transaction().refund(transactionId, transactionRefundRequest);
        }

        if (StringUtils.isNotBlank(orderId) && amount == null) {
            TransactionRefundRequest transactionRefundRequest = new TransactionRefundRequest()
                    .orderId(orderId);
            return obtainBraintreeGateway().transaction().refund(transactionId, transactionRefundRequest);
        }

        throw new PayPalException("refund error");
    }
}

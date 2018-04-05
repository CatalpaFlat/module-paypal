package com.github.catalpaflat.paypal.model.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author CatalpaFlat
 */
@Getter
@Setter
public class ReturnVO {
    private String transactionId;
    private BigDecimal amount;
    private String orderId;

    private ReturnVO(String transactionId) {
        this.transactionId = transactionId;
    }

    public class ReturnBuilder {
        private ReturnVO returnVO;

        public ReturnVO buildReturn() {
            return this.returnVO;
        }

        public ReturnBuilder(String transactionId) {
            this.returnVO = new ReturnVO(transactionId);
        }

        public ReturnVO setAmount(BigDecimal amount) {
            this.returnVO.amount = amount;
            return buildReturn();
        }

        public ReturnVO setOrderId(String orderId) {
            this.returnVO.orderId = orderId;
            return buildReturn();
        }
    }
}

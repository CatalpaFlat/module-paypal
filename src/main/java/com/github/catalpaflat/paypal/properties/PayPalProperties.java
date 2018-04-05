package com.github.catalpaflat.paypal.properties;

import com.github.catalpaflat.paypal.model.to.PayPalTO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author CatalpaFlat
 */
@Component
@ConfigurationProperties("catalpaflat")
public class PayPalProperties {
    @Getter
    @Setter
    private PayPalTO paypal;

    public PayPalProperties() {
    }
}

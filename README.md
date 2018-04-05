# JavaEE 全球支付之PayPal

## 1. 导入依赖

```xml
<dependency>
  <groupId>com.github.catalpaflat</groupId>
  <artifactId>module-paypal</artifactId>
  <version>1.0.0</version>
</dependency>
```
## 2. yml配置

```yaml
catalpaflat:
  paypal:
    sandbox_access_token: access_token$sandbox$catalpaflat1234567ec3dcc77213f4
    product_access_token: access_token$production$catalpaflat123456782bb3ec3dcc77213f4
    environment: sandbox
```

## 3. 注入依赖

```java
@Configuration
public class PayPalResourceConfig {
    @Bean
    public PayPalSpecificSupport payPalSpecificSupport(PayPalProperties payPalProperties) throws PackagerException {
        return new PayPalSpecificSupport(payPalProperties);
    }
}
```

## 4. 简单调用

```java

@RestController
@RequestMapping
public class PayPalIdal {
    @Resource
    private PayPalSpecificSupport payPalSpecificSupport;

    @GetMapping("public_access_token")
    public String get() throws PackagerException {
        return payPalSpecificSupport.obtainPublicAccessToken();
    }

    @PostMapping("place_order")
    public Result<Transaction> placeOrder(@RequestBody @Valid PayPalVO payPalVO) throws PackagerException {
        return payPalSpecificSupport.placeOrder(payPalVO);
    }

    @PutMapping("return")
    public Result<Transaction> returnOrder(@RequestBody @Valid ReturnVO returnVO) throws PackagerException {
        return payPalSpecificSupport.refund(returnVO);
    }
}
```


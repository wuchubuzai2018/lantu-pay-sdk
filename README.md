# 蓝兔支付SDK：lantu-pay-sdk

# 一、项目介绍

支持个人用户签约使用的蓝兔支付平台的Java SDK项目。

# 二、项目内容

计划针对蓝兔支付平台的接口，封装支付SDK，SDK设计思想采用开源项目wxjava的设计思想，相关的类设计参考其进行设计。

# 三、工程结构

docs：工程的相关文档

Demo工程：lantu-sdk-demo

SDK-JAVA工程：lantu-sdk-java

Spring Boot Starter工程：lantu-sdk-spring-boot-starter


# 四、功能列表

1、目前正在develop分支进行开发，定期合并到主分支

2、开发进度如下：

- 20231218：已提交第1节基本结构代码：Lantu SDK Spring Starter
- 20231229：已提交第2节基本结构代码：HTTP请求框架与存储层配置
- 20231231：已提交第3节基本结构代码：PC下单支付请求流程

3、目前实现功能如下：

- 二维码下单请求接口

# 五、使用步骤

1、在业务项目工程中，引入maven依赖，目前需要手动导入，后续会上传到maven仓库:

```
<dependency>
    <groupId>cn.ltzf</groupId>
    <artifactId>lantu-sdk-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

2、在配置文件中，配置需要的商户号、回调地址、秘钥等信息：

```
ltzf:
  wx:
    mch-id: ""
    secret-key: ""
    notify-url: ""
```

3、在业务代码中，注入Service类，并定义PC端下单生成接口：

```java
@RestController
@RequestMapping("/demo")
public class DemoController {
    
    /**
    * 注入蓝兔支付Service
    */
    @Autowired
    private LantuWxPayService lantuWxPayService;
    
    /**
     * 测试蓝兔支付 PC端扫描请求
     *
     * @return
     */
    @GetMapping("/native")
    public LantuWxPayNativeOrderResult nativeOrder() {
        // 定义蓝兔支付二维码生成请求
        LantuWxPayNativeOrderRequest request = new LantuWxPayNativeOrderRequest();
        String tradeNo = "2023" + System.currentTimeMillis();
        request.setOutTradeNo(tradeNo);
        request.setTotalFee("0.01");
        request.setBody("测试商品");
        LantuWxPayNativeOrderResult result = lantuWxPayService.createNativeOrder(request);
        return result;
    }
    
}
```

# 六、参与贡献

1、加入知识星球，与作者沟通想要开发的内容，然后开发并提交PR。

2、工程中采用checkstyle.xml文件作为代码风格的统一与质量检测。

# 七、知识星球


![我的知识星球](https://github.com/wuchubuzai2018/lantu-pay-sdk/assets/42382506/6bbc7f10-55c7-4507-9d20-1141c2b81355)

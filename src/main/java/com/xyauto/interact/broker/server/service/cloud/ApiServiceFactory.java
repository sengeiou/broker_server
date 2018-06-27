package com.xyauto.interact.broker.server.service.cloud;

import com.google.common.collect.Lists;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.util.HttpClient4Utils;
import com.xyauto.interact.broker.server.util.ILogger;
import com.xyauto.interact.broker.server.util.Result;
import feign.FeignException;
import feign.Request.Options;
import feign.Response;
import feign.Retryer;
import feign.form.FormEncoder;
import feign.hystrix.FallbackFactory;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiServiceFactory implements ILogger {

    @Autowired
    private EurekaClient client;

    @Autowired
    private PushService pushService;

    private static CloseableHttpClient httpClient = HttpClient4Utils.createHttpClient(100, 20, 3000, 4000, 10000);

    public InstanceInfo getServerInstance(String serverName) throws ResultException {
        List<InstanceInfo> instanceInfoList = Lists.newCopyOnWriteArrayList(client.getInstancesByVipAddress(serverName, false));
        Collections.sort(instanceInfoList, (InstanceInfo ins1, InstanceInfo ins2) -> (new Random()).nextInt() % 2 == 0 ? 1 : -1);
        for (InstanceInfo instance : instanceInfoList) {
            CloseableHttpResponse response = null;
            try {
                HttpGet httpGet = new HttpGet(instance.getHomePageUrl() + "info");
                response = (CloseableHttpResponse) httpClient.execute(httpGet);
                int code = response.getStatusLine().getStatusCode();
                if (code != HttpStatus.SC_OK || code == HttpStatus.SC_UNAUTHORIZED) {
                    continue;
                }
                return instance;
            } catch (IOException ex) {
                this.error(serverName + instance.getIPAddr() + "服务不可用,切换下一服务器");
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        throw new ResultException(ResultCode.ServiceDown, serverName + "服务暂时不可用");
    }

    public <T> T getService(Class<T> cls, String serverName) throws ResultException {
        FallbackFactory<T> fallbackFactory = cause -> {
            Result result = new Result();
            T t = (T) result.format(ResultCode.ServiceDown, serverName + "服务熔断处理");
            return t;
        };
        return feign.hystrix.HystrixFeign.builder()
                .requestInterceptor(new ApiServiceInterceptor())
                .decoder(new JacksonDecoder())
                .encoder(new FormEncoder())
                .errorDecoder((String methodKey, Response response) -> {
                    return feign.FeignException.errorStatus(methodKey, response);
                })
                .options(new Options(4000, 10000))
                .retryer(Retryer.NEVER_RETRY)
                .target(cls, this.getServerInstance(serverName).getHomePageUrl(), fallbackFactory);
    }

    public RClueService clueService() throws ResultException {
        return this.getService(RClueService.class, "qcdq-clue-api-server");
    }

    public RClueHandleService clueHandleService() throws ResultException {
        return this.getService(RClueHandleService.class, "qcdq-opportunity-build-self-server");
    }

    public RDataCenterService dataCenterService() throws ResultException {
        return this.getService(RDataCenterService.class, "qcdq-datacenter-server");
    }

    public RMissionService missionService() throws ResultException {
        return this.getService(RMissionService.class, "qcdq-broker-assist");
    }

    public RBrokerLogService brokerLogService() throws ResultException {
        return this.getService(RBrokerLogService.class, "qcdq-interact-broker-bi");
    }

    public RXyhPlatformService platformService() throws ResultException {
        return this.getService(RXyhPlatformService.class, "XYH-PLATFORM-SERVER");
    }

    public BaseCarService baseCarService() throws ResultException {
        return this.getService(BaseCarService.class, "qcdq-car-server");
    }

    public BaseCarImageService baseCarImageService() throws ResultException {
        return this.getService(BaseCarImageService.class, "qcdq-pic-server");
    }

    public BrokerTaskService BrokerTaskService() throws ResultException {
        return this.getService(BrokerTaskService.class, "qcdq-broker-assist");
    }

    public BaseDealerService BaseDealerService() throws ResultException {
        return this.getService(BaseDealerService.class, "qcdq-dealer-server-new");
    }

    public BrokerAssistService BrokerAssistService() throws ResultException {
        return this.getService(BrokerAssistService.class, "qcdq-broker-assist");
    }

    public InfomationService InfomationService() throws ResultException {
        return this.getService(InfomationService.class, "qcdq-information-server");
    }

    public DealerPriceService DealerPriceService() throws ResultException {
        return this.getService(DealerPriceService.class, "qcdq-dealer-price-server");
    }

    public PushService pushService() {
        return pushService;
    }

    public CallCenterService callCenter() throws ResultException {
        return this.getService(CallCenterService.class, "QCDQ-CALL-CENTER-SERVER");
    }

}

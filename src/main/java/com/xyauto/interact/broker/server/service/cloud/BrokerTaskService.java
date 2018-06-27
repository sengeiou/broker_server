package com.xyauto.interact.broker.server.service.cloud;


import com.xyauto.interact.broker.server.util.Result;
import feign.Param;
import feign.RequestLine;
import org.springframework.stereotype.Component;

@Component
public interface BrokerTaskService {

    /**
     * 完善用户信息
     * @param
     * @return
     */
    @RequestLine("GET /task/apply/userinfo?uid={broker_id}")
    Result updateBrokerInfo(@Param("broker_id") long broker_id);

    /**
     * 建卡  == 创建客户
     * @param broker_id
     * @param target_id 客户id
     * @return
     */
    @RequestLine("GET /task/apply/addCard?uid={broker_id}&target_id={target_id}")
    Result addCustomer(@Param("broker_id") long broker_id,@Param("target_id") long target_id);


    /**
     * 上传购车发票
     * @param broker_id
     * @param target_id  发票id
     * @return
     */
    @RequestLine("GET /task/apply/upTicket?uid={broker_id}&target_id={target_id}")
    Result upTicket(@Param("broker_id") long broker_id,@Param("target_id") long target_id);


    /**
     * 线索详情   ---  慧销宝 查看线索加积分
     * @param broker_id
     * @param target_id  当前线索id
     * @return
     */
    @RequestLine("GET /task/apply/viewBusiness?uid={broker_id}&target_id={target_id}")
    Result clueDetail(@Param("broker_id") long broker_id,@Param("target_id") long target_id);


    /**
     * 改意向阶段或更改跟进状态
     * @param broker_id
     * @param target_id  客户id
     * @return
     */
    @RequestLine("GET /task/apply/viewCustomer?uid={broker_id}&target_id={target_id}")
    Result updateCustomerInfo(@Param("broker_id") long broker_id,@Param("target_id") long target_id);


}

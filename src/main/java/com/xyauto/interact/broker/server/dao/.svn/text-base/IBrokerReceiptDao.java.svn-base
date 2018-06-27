package com.xyauto.interact.broker.server.dao;


import com.netflix.ribbon.proxy.annotation.ClientProperties;
import com.xyauto.interact.broker.server.model.vo.BrokerReceipt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sun.awt.SunHints;

import java.util.List;

@Mapper
public interface IBrokerReceiptDao {

    Integer upLoadInvoice(@Param(value = "customer_id") long customerId,
                          @Param(value = "broker_id") long brokerId,
                          @Param(value = "broker_customer_cars_will_id") long carsWillId,
                          @Param(value = "file_path") String filePath);

    Integer AllotCustomerToBtoker(@Param(value = "allotBrokerCustomer") long allotBrokerCustomer,
                                  @Param(value = "brokerId") long brokerId,
                                  @Param(value = "customerId") long customerId);

    BrokerReceipt getByCustomerIdAndBrokerId(@Param(value = "customer_id") long customerId,
                                             @Param(value = "broker_id") long brokerId,
                                             @Param(value="broker_customer_cars_will_id") long broker_customer_cars_will_id);

    List<BrokerReceipt> getInfoByBrokerId(@Param(value = "broker_id") long brokerId, @Param(value = "begin") String begin, @Param(value = "end")String end);

    BrokerReceipt getByPrimaryKey(@Param(value = "brokerReceiptId") long brokerReceiptId);

    Integer insert(BrokerReceipt record);
}

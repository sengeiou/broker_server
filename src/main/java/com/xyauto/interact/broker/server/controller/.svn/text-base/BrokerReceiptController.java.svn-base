package com.xyauto.interact.broker.server.controller;

import com.mcp.validate.annotation.Check;
import com.xyauto.interact.broker.server.enums.BrokerReceiptStatusEnum;
import com.xyauto.interact.broker.server.enums.ResultCode;
import com.xyauto.interact.broker.server.exceptions.ResultException;
import com.xyauto.interact.broker.server.model.vo.*;
import com.xyauto.interact.broker.server.service.*;
import com.xyauto.interact.broker.server.service.cloud.ApiServiceFactory;
import com.xyauto.interact.broker.server.util.DateUtil;
import com.xyauto.interact.broker.server.util.Result;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

@RestController
@RequestMapping("/broker/receipt")
public class BrokerReceiptController extends BaseController {

    @Autowired
    BrokerReceiptService brokerReceiptService;

    @Autowired
    BrokerCustomerCarsWillService brokerCustomerCarsWillService;

    @Autowired
    CarService carService;

    @Autowired
    SeriesService seriesService;

    @Autowired
    BrokerCustomerService brokerCustomerService;

    @Autowired
    ApiServiceFactory apiServiceFactory;


    /*
     * 上传发票
     *
     * @param customerId
     * @param brokerReceiptId
     * @param brokerId
     * @param carsWillId
     * @param filePath
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upLoad(
            @Check(value = "customer_id", required = true) long customerId,
            @Check(value = "receipt_id", required = false, defaultValue = "0") long brokerReceiptId,
            @Check(value = "broker_id", required = false, defaultValue = "0") long brokerId,
            @Check(value = "cars_will_id", required = false, defaultValue = "0") long carsWillId,
            @Check(value = "file_path", required=true) String filePath
    ) throws ResultException, IOException {
        BrokerReceipt brokerReceipt = new BrokerReceipt();
        brokerReceipt.setBrokerCustomerId(customerId);
        brokerReceipt.setBrokerId(brokerId);
        brokerReceipt.setBrokerCustomerCarsWillId(carsWillId);
        brokerReceipt.setImages(filePath);
        brokerReceipt.setBrokerReceiptId(brokerReceiptId);
        BrokerCustomer customer = brokerCustomerService.get(customerId,true);
        if(brokerId > 0 && customer.getBrokerId() != brokerId){
            return result.format(ResultCode.NoPermission);
        }
        int ret = brokerReceiptService.Create(brokerReceipt);
        if (ret > 0) {
            return result.format(ResultCode.Success, brokerReceipt);
        }
        return result.format(ResultCode.UnKnownError);
    }

    /**
     * 获取发票信息 14
     *
     * @param customerId
     * @param brokerId
     * @param brokerCustomerCarsWillId
     * @return
     * @throws ResultException
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Result get(
            @Check(value = "customer_id", required = true) long customerId,
            @Check(value = "broker_id", required = true) long brokerId,
            @Check(value = "customer_cars_will_id", required = true) long brokerCustomerCarsWillId
    ) throws ResultException {
        BrokerReceipt model = brokerReceiptService.getByCustomerIdAndBrokerId(customerId, brokerId, brokerCustomerCarsWillId);
        return result.format(ResultCode.Success, model);
    }


    /**
     * 获取成交历史数据 （根据发票）  --- H5使用
     * @param brokerId
     * @param begin
     * @param end
     * @return
     */
    @RequestMapping(value = "/getbrokerlistinfo", method = RequestMethod.GET)
    public Result receiptCarsInfo(@Check(value = "broker_id") long brokerId,
                                  @Check(value = "begin", required = false, defaultValue = "") String begin,
                                  @Check(value = "end", required = false, defaultValue = "") String end) {
        if (begin.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = calendar.getTime();
            begin = dateFormat.format(DateUtil.addYear(date, -1));
            end = dateFormat.format(date);
        }
        List<BrokerReceipt> brokerReceiptList = brokerReceiptService.getInfoByBrokerId(brokerId, begin, end);
        List<BrokerReceiptExt> brokerReceiptExtList = new ArrayList<>();
        if (brokerReceiptList != null && brokerReceiptList.size() > 0) {
            for (BrokerReceipt brokerReceipt : brokerReceiptList) {
                //brokerReceipt.setImages(seriesService.getSeriesImage(brokerReceipt.getImages()));
                BrokerCustomerCarsWill willCar = brokerCustomerCarsWillService.getCustomerCarsByID(brokerReceipt.getBrokerCustomerCarsWillId(), brokerReceipt.getBrokerCustomerId());
                if (willCar != null) {
                    brokerReceipt.setBrokerCustomerCarsWill(willCar);
                    Car car = new Car();
                    if (willCar.getCarId() != 0) {
                        car = carService.getCar(willCar.getCarId());
                    }
                    if (car != null) {
                        brokerReceipt.setCar(carService.getCar(car.getCarId()));
                        Series series = seriesService.getSeries(car.getSeriesId());
                        if (series != null) {
                            if(series.getImage()!=null) {
                                series.setImage(seriesService.getSeriesImage(series.getImage()));
                            }
                            brokerReceipt.setSeries(series);
                        }
                    }
                    brokerReceipt.setBrokerCustomer(brokerCustomerService.get(brokerReceipt.getBrokerCustomerId(), false));
                    boolean isadd = true;
                    int currYear = DateUtil.getYear(brokerReceipt.getCreateTime());
                    int currMonth = DateUtil.getMonth(brokerReceipt.getCreateTime());

                    for (BrokerReceiptExt tempModel : brokerReceiptExtList) {
                        if (tempModel.year == currYear && tempModel.month == currMonth) {
                            tempModel.getBrokerReceipts().add(brokerReceipt);
                            isadd = false;
                        }
                    }
                    if (isadd) {
                        brokerReceiptExtList.add(new BrokerReceiptExt(currYear, currMonth, brokerReceipt));
                    }
                }
            }
        }
        return result.format(ResultCode.Success, brokerReceiptExtList);

    }


    /**
     * 购车发票完成
     * @param recipitId
     * @return
     */
    @RequestMapping(value = "/recepitfinsh", method = RequestMethod.POST)
    public Result recepitFinsh ( @Check(value = "recipit_id") long recipitId ){
        BrokerReceipt brokerReceipt = brokerReceiptService.getByPrimaryKey(recipitId);
        if (brokerReceipt != null && brokerReceipt.getStatus() == BrokerReceiptStatusEnum.Finsh.getValue()) {
            int suc = brokerCustomerCarsWillService.BuyCarFinsh(brokerReceipt.getBrokerCustomerCarsWillId());
            if (suc > 0) {
                try {
                    apiServiceFactory.BrokerTaskService().upTicket(brokerReceipt.getBrokerId(), brokerReceipt.getBrokerReceiptId());
                } catch (Exception e) {
                }
                return result.format(ResultCode.Success);
            }
        }
        return result.format(ResultCode.UnKnownError);
    }



}

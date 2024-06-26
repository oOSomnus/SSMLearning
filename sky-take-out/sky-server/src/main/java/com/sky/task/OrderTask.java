package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 * * * * ? ")
    public void processTimeoutOrder() {
        log.info("Process timeout order: {}", LocalDateTime.now());
        List<Orders> list = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, LocalDateTime.now().plusMinutes(-15));

        if (list != null && list.size() > 0) {
            for (Orders order : list) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("Auto cancelled");
                order.setCancelTime(LocalDateTime.now());
//                orderMapper.update(orders);
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * ? *")
    public void processDelieveredOrder() {
        log.info("process delivered order");
        List<Orders> list = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now().plusHours(-1));
        if (list != null && list.size() > 0) {
            for (Orders order : list) {
                order.setStatus(Orders.COMPLETED);
//                orderMapper.update(orders);
            }
        }

    }
}

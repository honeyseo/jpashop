package jpabook.jpashop.respository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;//주문상태 (ORDER, CANCEL)
}

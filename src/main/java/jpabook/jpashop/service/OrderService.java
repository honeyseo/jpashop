package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.respository.ItemRepository;
import jpabook.jpashop.respository.MemberRepository;
import jpabook.jpashop.respository.OrderRepository;
import jpabook.jpashop.respository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성 :
        //orderitem을 create가 아니라 new OrderItem()/set bla()~~ 하면 소스코드 관리가 어려움.
        //new OrderItem을 막으려면 생성자를 protected로 만들면됨.
        OrderItem orderItem = OrderItem.createOrderIte(item, item.getPrice(), count);

        //주문생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문저장
        orderRepository.save(order); //cascade 옵션때문에 orderitem과 delivery는 자동으로 persist 된다

        return order.getId();
    }

    /**
     * 주문취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //엔터티조회
        Order order = orderRepository.findOne(orderId);
        //주문취소
        order.cancel();
    }

    //검색


    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }

}

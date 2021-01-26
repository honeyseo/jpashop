package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.respository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember("회원1");
        Book book = createBook("책이름1", 10, 10000);


        int orderCount=2;
        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);


        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * orderCount, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야한다", 8, book.getStockQuantity());

    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember("member1");
        Book book = createBook("testbook", 10, 10000);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문취소시, 상태는 CANCEL 이어야한다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품의 재고 원상복구", 10, book.getStockQuantity());
    }
    
    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember("회원1");
        Book book1 = createBook("book1", 10, 10000);
        int orderCont = 11;
        //when
        orderService.order(member.getId(), book1.getId(), orderCont);
        //then
        fail("재고수량 부족 예외 안터져부럿다");
    }

    private Book createBook(String bookName, int stock, int price) {
        Book book = new Book();
        book.setName(bookName);
        book.setPrice(price);
        book.setStockQuantity(stock);
        em.persist(book);
        return book;
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(new Address("서울", "어딘가", "11-111"));
        em.persist(member);
        return member;
    }
}
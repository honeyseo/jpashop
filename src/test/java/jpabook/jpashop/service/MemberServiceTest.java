package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.respository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given 주어지면
        Member member = new Member();
        member.setName("seo2");

        //when 실행하면
        Long savedId = memberService.join(member);
        em.flush();

        //then 결과는
        assertEquals(member, memberRepository.findOne(savedId));//insert 한게 존재하는지 확인
        //Assert.assertThat().~~쭐인게 assertEqual()
        
    }
    
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("seo1");

        Member member2 = new Member();
        member2.setName("seo1");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외가 발생해야한다.");//Assert.fail();


    }
}
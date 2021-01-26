package jpabook.jpashop.domain;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order oder;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; //READY, COMP
}

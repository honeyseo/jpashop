package jpabook.jpashop.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class MemberForm {
    @NotEmpty(message = "회원 이름은 필수입니다.")
    private String name;
    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String password;

    private String city;
    private String street;
    private String zipcode;
}

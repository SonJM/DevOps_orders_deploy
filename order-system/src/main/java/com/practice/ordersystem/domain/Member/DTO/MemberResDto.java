package com.practice.ordersystem.domain.Member.DTO;

import com.practice.ordersystem.domain.Member.Address;
import com.practice.ordersystem.domain.Member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberResDto {
    private Long id;
    private String name;
    private String email;
    private String city;
    private String street;
    private String zipcode;
    private int orderCount;

    public static MemberResDto toMemberResponseDto(Member member){
        MemberResDtoBuilder builder = MemberResDto.builder();
        builder.id(member.getId());
        builder.name(member.getName());
        builder.email(member.getEmail());
        builder.orderCount(member.getOrders().size());
        Address address = member.getAddress();
        if(address != null){
            builder.city(address.getCity());
            builder.street(address.getStreet());
            builder.zipcode(address.getZipcode());
        }
        return builder.build();
    }
}

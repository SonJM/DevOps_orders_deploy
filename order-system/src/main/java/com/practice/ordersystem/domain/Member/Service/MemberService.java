package com.practice.ordersystem.domain.Member.Service;

import com.practice.ordersystem.domain.Member.Address;
import com.practice.ordersystem.domain.Member.DTO.*;
import com.practice.ordersystem.domain.Member.Member;
import com.practice.ordersystem.domain.Ordering.Ordering;
import com.practice.ordersystem.domain.Ordering.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.practice.ordersystem.domain.Member.Repository.MemberRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.practice.ordersystem.domain.Member.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Member save(MemberCreateReqDto memberCreateReqDto) throws IllegalArgumentException{
        Optional<Member> memberOptional = memberRepository.findByEmail(memberCreateReqDto.getEmail());
        if(memberOptional.isPresent()){
            throw new IllegalArgumentException("중복 이메일입니다.");
        }
        memberCreateReqDto.setPassword(passwordEncoder.encode(memberCreateReqDto.getPassword()));
        Member member = Member.toEntity(memberCreateReqDto);
        return memberRepository.save(member);
    }

    public List<MemberResDto> findAllStream(){
        List<Member> memberResDtos = memberRepository.findAll();
        return memberResDtos.stream().map(m->MemberResDto.toMemberResponseDto(m)).collect(Collectors.toList());
    }

    public List<MemberListResDto> findAll() throws NullPointerException{
        List<Member> members = memberRepository.findAll();
        List<MemberListResDto> memberListResDtoList = new ArrayList<>();
        for(Member member : members){
            String role = "";
            if(member.getRole().equals(Role.ADMIN)) role = "관리자";
            else role = "유저";
            MemberListResDto memberListResDto = MemberListResDto.builder()
                    .name(member.getName())
                    .createdTime(member.getCreatedTime())
                    .role(role)
                    .build();
            try{
                memberListResDtoList.add(memberListResDto);
            } catch (NullPointerException e){
                throw new NullPointerException("멤버가 없습니다.");
            }
        }
        return memberListResDtoList;
    }

    public List<MemberOrderListResDto> findById(Long id) throws EntityNotFoundException{
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        List<Ordering> orderingList = orderRepository.findAllByMemberId(id);
        List<MemberOrderListResDto> memberDetailResDtos = new ArrayList<>();
        for(Ordering ordering : orderingList){
            MemberOrderListResDto memberDetailResDto = MemberOrderListResDto.builder()
                    .name(member.getName())
                    .orderId(ordering.getId())
                    .orderStatus(ordering.getStatus().name())
                    .build();
            memberDetailResDtos.add(memberDetailResDto);
        }
        return memberDetailResDtos;
    }

    public MemberResDto findMyInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        return MemberResDto.toMemberResponseDto(member);
    }

    public Member login(LoginReqDto loginReqDto) throws IllegalArgumentException{
        // email 존재 여부
        Member member = memberRepository.findByEmail(loginReqDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        // password 일치여부
        if(!passwordEncoder.matches(loginReqDto.getPassword(), member.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return member;
    }
}

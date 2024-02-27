package com.practice.ordersystem.domain.Member.Controller;

import com.practice.ordersystem.domain.Member.DTO.*;
import com.practice.ordersystem.domain.Member.Member;
import com.practice.ordersystem.domain.Ordering.DTO.OrderResDto;
import com.practice.ordersystem.domain.Ordering.Service.OrderService;
import com.practice.ordersystem.domain.common.ResponseDto;
import com.practice.ordersystem.domain.securities.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.practice.ordersystem.domain.Member.Service.MemberService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final OrderService orderService;

    @Autowired
    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider, OrderService orderService) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.orderService = orderService;
    }

    @PostMapping("/member/new")
    @CrossOrigin(originPatterns = "*")
    public ResponseEntity<ResponseDto> createMember(@RequestBody MemberCreateReqDto memberCreateReqDto){
        Member member = memberService.save(memberCreateReqDto);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.CREATED, "mamber successfully created", member), HttpStatus.CREATED);
    }

    @GetMapping("/member/myorders")
    public List<OrderResDto> findMyOrders(){
        return orderService.findMyOrders();
    }

    // 관리자가 회원목록조회 -> 주문횟수 -> 해당 회원의 주문 목록
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/member/{id}/orders")
    public List<OrderResDto> findMemberOrders(@PathVariable Long id){
        return orderService.findByMember(id);
    }

    @GetMapping("/member/myInfo")
    public MemberResDto memberInfo(){
        return memberService.findMyInfo();
    }

    @GetMapping("/members")
    @PreAuthorize("hasRole('ADMIN')")
    public List<MemberResDto> memberList(){
        return memberService.findAllStream();
    }

    @PostMapping("/doLogin")
    public ResponseEntity<ResponseDto> memberLogin(@Valid @RequestBody LoginReqDto loginReqDto) {
        Member member = memberService.login(loginReqDto);

        // 토큰 생성
        String jwtToken = jwtTokenProvider.createToken(loginReqDto.getEmail(), member.getRole().toString());

        Map<String, Object> member_info = new HashMap<>();
        member_info.put("id", member.getId());
        member_info.put("token", jwtToken);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK, "member successfully logined", member_info), HttpStatus.OK);
    }
}

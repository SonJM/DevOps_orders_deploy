## 요구사항

<span style="color: red">변경 가능</span>
<span style="color: blue">FK</span>
- - -
### 도메인
  - Member
    - Long id
    - String name
    - String email
      - unique
    - String password
    - String address
    - Enum role 
      - ADMIN 
      - USER
    - LocalDateTime createdTime
      - default: CURRENT_TIMESTAMP
    - LocalDateTime updatedTime 
      - Extra: ON UPDATE CURRENT_TIMESTAMP 
      - default: CURRENT_TIMESTAMP
    - <span style="color: blue">orderings</span>
      - Ordering과 OneToMany관계
  - Ordering
    - Long id
    - <span style="color: blue">member</span>
      - Member와 ManyToOne관계
      - NotNull
    - Enum OrderStatus
      - ORDERED 
      - CANCELED
    - LocalDateTime createdTime
    - LocalDateTime updatedTime
  - Item
    - Long id
    - String name
    - int price
    - int stockQuantity
    - String imagePath
    - LocalDateTime createdTime
    - LocalDateTime updatedTime
  - OrderItem(아이템별주문 / 중간 테이블)
    - Long id
    - int quantity
    - <span style="color: blue">item</span>
      - Item과 ManyToOne
      - NotNull
    - <span style="color: blue">ordering</span>
      - Ordering과 ManyToOne
      - NotNull
    - LocalDateTime createdTime
    - LocalDateTime updatedTime
- - -
### 기능 (RestAPI)
  - Member
    - 회원가입(/member/new)
    - 회원목록조회(/members)
    - 회원별주문목록조회(/member/{id}/orders)
  - Ordering
    - 주문목록조회(/orders)
    - 주문하기(/order/new)
      - reqDto 
        - <span style="color: red">Long</span> memberId
        - List\<Long\> itemId
        - List\<Long\> count
        - 주문시 ordering테이블 1건 insert 및 상태값 ORDERED 세팅
      - 주문시 order_item테이블에 item과 수량별로 rows insert
      - item별로 수량만큼 item테이블의 stockQuantity 감소
    - 주문취소(/order/{id}/cancle)
      - order테이블의 상태값 CANCELED로 변경
      - 해당 order의 order_item 테이블에서 대상건을 찾아 item별로 수량만큼 item테이블의 stockQuantity 증가
  - OrderItem
    - orderitem목록조회(/orderitems/{id}) 
      - order_id를 입력받아 orderitems데이터 return


### Vue 연결
npm 
package com.zayden.coffeeorderserviceuserservice.jpa;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name = "userpoints")
public class UserPointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String userId;

    @Column(nullable = false)
    private Integer amount;

    private LocalDateTime createAt;

    public boolean payCancel(Integer amount){
        if(this.amount < amount){
            throw new IllegalStateException("가지고 계신 포인트가 결제하신 포인트 보다 적어 취소가 불가능합니다.");
        }
        return true;
    }
}

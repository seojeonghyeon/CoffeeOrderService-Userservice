package com.zayden.coffeeorderserviceuserservice.jpa;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor @NoArgsConstructor
@Table(name = "paypoints")
public class PaypointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String payId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String payStatus;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value="CURRENT_TIMESTAMP")
    private Date createAt;
}

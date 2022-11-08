package com.zayden.coffeeorderserviceuserservice.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zayden.coffeeorderserviceuserservice.dto.PayPointStatus;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "logpaypoints")
public class LogPaypointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String payId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private int amount;

    @Enumerated(EnumType.STRING)
    private PayPointStatus payPointStatus;

    @Column(nullable = false, updatable = false, insertable = false)
    @ColumnDefault(value="CURRENT_TIMESTAMP")
    private Date createAt;

}

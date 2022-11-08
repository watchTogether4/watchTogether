package com.watchtogether.server.users.domain.entitiy;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.envers.AuditOverride;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AuditOverride(forClass = BaseEntity.class)
@Entity(name = "transaction")
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne
    private User user;

    @NotNull
    private Long partyId;

    @NotNull
    private String transactionType;

    @NotNull
    private String transactionResultType;

    @NotNull
    private Long amount;

    @NotNull
    private Long balanceSnapshot;

    private String traderNickname;

    @NotNull
    private LocalDateTime transactionDt;

}

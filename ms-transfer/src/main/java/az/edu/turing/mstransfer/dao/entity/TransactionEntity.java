package az.edu.turing.mstransfer.dao.entity;

import az.edu.turing.mstransfer.model.enums.Direction;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "TRANSACTIONS")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    Direction direction;

    BigDecimal amount;

    String message;

    String comission;

    Long accountId;

    @CreationTimestamp
    LocalDateTime createdAt;
}

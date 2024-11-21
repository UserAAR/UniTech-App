package az.edu.turing.mstransfer.dao.entity;

import az.edu.turing.mstransfer.model.enums.AccountStatus;
import az.edu.turing.mstransfer.model.enums.Bank;
import az.edu.turing.mstransfer.model.enums.Currency;
import az.edu.turing.mstransfer.util.IbanGenerator;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "ACCOUNTS")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    Bank bank;

    String accountNumber;

    String iban;

    BigDecimal balance;

    @Enumerated(EnumType.STRING)
    Currency currency;

    String password;

    @Enumerated(EnumType.STRING)
    AccountStatus status;

    Long userId;

    @Version
    Long version;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @PrePersist
    private void defaultValues() {

        if (this.accountNumber == null) {
            this.accountNumber = IbanGenerator.getAccountNumber();
        }

        if (this.iban == null) {
            this.iban = IbanGenerator.generateIban(bank.getBankCode(), accountNumber);
        }
    }
}

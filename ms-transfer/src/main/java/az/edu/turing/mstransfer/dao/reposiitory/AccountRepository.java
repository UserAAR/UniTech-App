package az.edu.turing.mstransfer.dao.reposiitory;


import az.edu.turing.mstransfer.dao.entity.AccountEntity;
import az.edu.turing.mstransfer.model.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    List<AccountEntity> findByUserId(Long userId);

    Optional<AccountEntity> findByIbanAndAccountNumber(String iban, String accountNumber);

    Optional<AccountEntity> findByIban(String iban);

    boolean existsByUserIdAndPassword(Long userId, String password);

    Optional<AccountEntity> findByIdAndUserId(Long id, Long userId);

    Optional<AccountEntity> findByUserIdAndIdAndStatus(Long userId, Long id, AccountStatus status);
}

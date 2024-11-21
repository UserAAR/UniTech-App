package az.edu.turing.mstransfer.dao.reposiitory;

import az.edu.turing.mstransfer.dao.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransferRepository extends JpaRepository<TransactionEntity, Long> {

    void deleteAllByAccountId(long accountId);
}

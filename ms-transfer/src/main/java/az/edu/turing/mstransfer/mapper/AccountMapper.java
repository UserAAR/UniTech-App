package az.edu.turing.mstransfer.mapper;

import az.edu.turing.mstransfer.dao.entity.AccountEntity;
import az.edu.turing.mstransfer.model.response.RetrieveAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    RetrieveAccountResponse mapToDto(AccountEntity accountEntity);
}

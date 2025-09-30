package none.romank.backend.api.Repositories;

import org.springframework.data.repository.CrudRepository;

import none.romank.backend.api.Domain.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction,Long>{
    
}

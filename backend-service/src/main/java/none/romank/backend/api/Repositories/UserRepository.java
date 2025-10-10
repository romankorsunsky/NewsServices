package none.romank.backend.api.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import none.romank.backend.api.Domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

    @Query(value = "SELECT u FROM User u WHERE u.id =:id")
    public Optional<User> findUserById(@Param("id") Long id);

}

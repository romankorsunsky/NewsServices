package none.romank.backend.api.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import none.romank.backend.api.Domain.User;

public interface UserDetailsRepository extends CrudRepository<User, Long>{
    @Query(value="select * from Account where username =:username",nativeQuery=true)
    public Optional<User> findByUserName(String username);

    @Query(value="select * from Account",nativeQuery=true)
    public List<User> findAuthors();

}

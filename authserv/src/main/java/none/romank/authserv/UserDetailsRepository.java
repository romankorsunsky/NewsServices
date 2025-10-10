package none.romank.authserv;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserDetailsRepository extends MongoRepository<User,String>{

    public Optional<User> findByUsername(String username);
}

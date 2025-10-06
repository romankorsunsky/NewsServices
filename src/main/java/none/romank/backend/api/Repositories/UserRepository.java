package none.romank.backend.api.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import none.romank.backend.api.Domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    @Query(value = "select * from User where id =: id",nativeQuery=true)
    public Optional<User> findAuthorById(@Param("id") Long authorId);

    @Query(value = "select (full_name) from User order by userName asc limit :count",nativeQuery=true)
    public List<User> findAuthorsTopX(@Param("count") Long count);
}

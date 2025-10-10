package none.romank.backend.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import none.romank.backend.api.Domain.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>{
    @Query(value="select * from Authors",nativeQuery=true)
    public List<Author> findAllAuthors();
}

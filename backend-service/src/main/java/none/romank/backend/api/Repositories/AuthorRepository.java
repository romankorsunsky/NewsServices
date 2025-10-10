package none.romank.backend.api.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import none.romank.backend.api.Domain.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>{
    @Query(value="SELECT a from Authors a")
    public List<Author> findAllAuthors();
}

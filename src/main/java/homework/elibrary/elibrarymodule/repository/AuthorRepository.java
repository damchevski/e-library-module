package homework.elibrary.elibrarymodule.repository;

import homework.elibrary.elibrarymodule.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}

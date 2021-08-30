package Testing;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorRepository {
    private List<Author> authors;

    public AuthorRepository() {
        this.authors = new ArrayList<>();
    }

    public List<Author> getAll() {
        return authors;
    }

    public Optional<Author> findAuthor(Long id) {
        return authors.stream().filter(author -> author.getId().equals(id)).findFirst();
    }

    public Author save(Author author) {
        authors.removeIf(b -> b.getId().equals(author.getId()));
        authors.add(author);
        return author;
    }

    public boolean delete(Author author) {
        return this.authors.removeIf(a -> a.getId().equals(author.getId()));
    }

    public void clear(){
        this.authors = new ArrayList<>();
    }
}

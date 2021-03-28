package homework.elibrary.elibrarymodule.service.impl;

import homework.elibrary.elibrarymodule.model.Author;
import homework.elibrary.elibrarymodule.repository.AuthorRepository;
import homework.elibrary.elibrarymodule.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return this.authorRepository.findAll();
    }
}

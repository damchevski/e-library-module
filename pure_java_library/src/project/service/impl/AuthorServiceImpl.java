package project.service.impl;


import project.model.Author;
import project.repository.AuthorRepository;
import project.service.AuthorService;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return this.authorRepository.getAll();
    }
}

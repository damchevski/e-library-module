package homework.elibrary.elibrarymodule.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    @ManyToOne
    private Country country;

    public Author(){}
}

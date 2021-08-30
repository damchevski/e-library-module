package Testing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryRepository {
    private List<Country> countries;

    public CountryRepository() {
        this.countries = new ArrayList<>();
    }

    public List<Country> getAll() {
        return countries;
    }

    public Optional<Country> findCountry(Long id) {
        return countries.stream().filter(country -> country.getId().equals(id)).findFirst();
    }

    public Country save(Country country) {
        countries.removeIf(c -> c.getId().equals(country.getId()));
        countries.add(country);
        return country;
    }

    public void clear(){
        this.countries = new ArrayList<>();
    }
}

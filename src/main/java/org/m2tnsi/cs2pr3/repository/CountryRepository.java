package org.m2tnsi.cs2pr3.repository;

import org.m2tnsi.cs2pr3.classes.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {

    List<Country> findAllByDateMaj(Timestamp dateMaj);
    Optional<Country> findFirstByCountryCodeOrderByDateMajDesc(String countryCode);
}

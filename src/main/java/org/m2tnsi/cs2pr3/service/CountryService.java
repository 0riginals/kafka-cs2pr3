package org.m2tnsi.cs2pr3.service;


import org.m2tnsi.cs2pr3.classes.Country;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


public interface CountryService {
    List<Country> findAllByDateMaj(Timestamp dateMaj);
    Optional<Country> findFirstByCountryCodeOrderByDateMajDesc(String countryCode);
}

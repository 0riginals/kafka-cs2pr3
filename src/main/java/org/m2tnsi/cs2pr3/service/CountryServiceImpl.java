package org.m2tnsi.cs2pr3.service;

import org.m2tnsi.cs2pr3.classes.Country;
import org.m2tnsi.cs2pr3.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<Country> findAllByDateMaj(Timestamp dateMaj) {
        return countryRepository.findAllByDateMaj(dateMaj);
    }

    @Override
    public Optional<Country> findFirstByCountryCodeOrderByDateMajDesc(String countryCode) {
        return countryRepository.findFirstByCountryCodeOrderByDateMajDesc(countryCode);
    }
}

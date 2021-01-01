package org.m2tnsi.cs2pr3.service;

import org.m2tnsi.cs2pr3.classes.Global;
import org.m2tnsi.cs2pr3.repository.GlobalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class GlobalServiceImpl implements GlobalService {

    @Autowired
    private GlobalRepository globalRepository;

    @Override
    public Optional<Global> findFirstByOrderByDateMajDesc() {
        return globalRepository.findFirstByOrderByDateMajDesc();
    }

}

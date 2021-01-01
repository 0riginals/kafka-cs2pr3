package org.m2tnsi.cs2pr3.repository;

import org.m2tnsi.cs2pr3.classes.Global;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface GlobalRepository extends CrudRepository<Global, Long> {
    Optional<Global> findFirstByOrderByDateMajDesc();
}

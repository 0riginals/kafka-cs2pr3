package org.m2tnsi.cs2pr3.service;


import org.m2tnsi.cs2pr3.classes.Global;

import java.util.Optional;

public interface GlobalService {
    Optional<Global> findFirstByOrderByDateMajDesc();
}
package com.hizik.repository;

import com.hizik.domain.Moto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotoRepository extends JpaRepository<Moto, Integer> {

    Moto findById(long id);
    Moto findByLat(float lat);
    Moto findByLon(float lon);


    List<Moto> findAll();

    void deleteById(long id);
}

package com.hizik.service;

import com.hizik.domain.Moto;

import java.util.List;

public interface MotoService {

    Moto insert(Moto moto);

    void deleteMoto(Long id);

    List<Moto> getAll();

    Moto getById(Long id);

    Moto updateMoto(Long id,
                    Float lat,
                    Float lon);
}

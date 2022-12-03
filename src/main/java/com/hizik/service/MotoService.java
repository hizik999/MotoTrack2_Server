package com.hizik.service;

import com.hizik.domain.Moto;

import java.util.List;

public interface MotoService {

    Moto insert(Moto moto);

    void deleteMoto(long id);

    List<Moto> getAll();

    Moto getById(long id);

    Moto updateMoto(long id,
                    float lat,
                    float lon);
}

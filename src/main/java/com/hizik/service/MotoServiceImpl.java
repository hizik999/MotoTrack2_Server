package com.hizik.service;


import com.hizik.domain.Moto;
import com.hizik.repository.MotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MotoServiceImpl implements MotoService{

    private final MotoRepository motoRepository;

    @Transactional
    @Override
    public Moto insert(Moto moto) {
        return motoRepository.save(moto);
    }

    @Transactional
    @Override
    public void deleteMoto(long id) {
        motoRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<Moto> getAll() {
        return motoRepository.findAll();
    }

    @Transactional
    @Override
    public Moto getById(long id) {
        return motoRepository.findById(id);
    }

    @Transactional
    @Override
    public Moto updateMoto(long id, float lat, float lon) {
        Moto moto = Moto.builder()
                .id(id)
                .lat(lat)
                .lon(lon)
                .build();

        return motoRepository.save(moto);
    }


}

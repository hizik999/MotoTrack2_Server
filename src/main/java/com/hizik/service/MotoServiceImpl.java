package com.hizik.service;


import com.hizik.domain.Moto;
import com.hizik.repository.MotoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MotoServiceImpl implements MotoService {

    private final MotoRepository motoRepository;

    public MotoServiceImpl(MotoRepository motoRepository) {
        this.motoRepository = motoRepository;
    }

    @Transactional
    @Override
    public Moto insert(Moto moto) {
        return motoRepository.save(moto);
    }

    @Transactional
    @Override
    public void deleteMoto(Long id) {
        motoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Moto> getAll() {
        return motoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Moto getById(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Moto with id " + id + " was not found"));
    }

    @Transactional
    @Override
    public Moto updateMoto(Long id, Float lat, Float lon) {
        Moto moto = getById(id);
        moto.setLat(lat);
        moto.setLon(lon);

        return motoRepository.save(moto);
    }
}

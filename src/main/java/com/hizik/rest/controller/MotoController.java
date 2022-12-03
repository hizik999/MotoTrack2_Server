package com.hizik.rest.controller;


import com.hizik.domain.Moto;
import com.hizik.rest.dto.MotoDto;
import com.hizik.service.MotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MotoController {

    private final MotoService motoService;
    private static final Logger log = Logger.getLogger(MotoController.class.getName());

    @GetMapping("/moto")
    public List<MotoDto> getAllMoto() {
        List<MotoDto> list = motoService.getAll().stream().map(MotoDto::toDto).collect(Collectors.toList());
        log.info(log.getName() + " getAllMoto(): " + Arrays.toString(list.toArray()));
        return list;

    }

    @PostMapping(path = "/moto")
    public MotoDto insertMoto(MotoDto motoDto){
        Moto moto = motoService.insert(MotoDto.toDomainObject(motoDto));
        log.info(log.getName() + " insertMoto(): " + moto.toString());
        return MotoDto.toDto(moto);
    }

    @PutMapping("/moto/{id}")
    public MotoDto updateMoto(@PathVariable String id, @RequestParam String lat, @RequestParam String lon){

        // костыль
        long id1 = Long.parseLong(id);
        float lat1 = Float.parseFloat(lat);
        float lon1 = Float.parseFloat(lon);

        Moto moto = motoService.updateMoto(id1, lat1, lon1);
        log.info(log.getName() + " updateMoto(): " + moto.toString());
        return MotoDto.toDto(moto);
    }

    @GetMapping("/moto/{id}")
    public MotoDto getMotoById(@PathVariable long id){
        Moto moto = motoService.getById(id);
        log.info(log.getName() + " getMotoById(): " + moto.toString());
        return MotoDto.toDto(moto);
    }

    @DeleteMapping("/moto/{id}")
    public void deleteMotoDyId(@PathVariable String id){

        //костыль
        long id1 = Long.parseLong(id);

        motoService.deleteMoto(id1);
        log.info(log.getName() + " deleteMotoById(): " + ": " + id);

    }
}

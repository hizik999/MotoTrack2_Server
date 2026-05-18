package com.hizik.rest.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hizik.domain.Moto;
import com.hizik.rest.dto.MotoDto;
import com.hizik.service.MotoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/moto")
@Validated
public class MotoController {

    private static final Logger log = LoggerFactory.getLogger(MotoController.class);

    private final MotoService motoService;
    private final ObjectMapper objectMapper;

    public MotoController(MotoService motoService, ObjectMapper objectMapper) {
        this.motoService = motoService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public List<MotoDto> getAllMoto() {
        List<MotoDto> motoList = motoService.getAll()
                .stream()
                .map(MotoDto::toDto)
                .toList();
        log.info("Loaded {} moto points", motoList.size());
        return motoList;

    }

    @PostMapping
    public MotoDto insertMoto(@RequestParam(required = false) Long id,
                              @RequestParam(required = false) Float lat,
                              @RequestParam(required = false) Float lon,
                              @RequestBody(required = false) String body) {
        MotoDto motoDto = resolveMotoDto(id, lat, lon, body);
        Moto moto = motoService.insert(MotoDto.toDomainObject(motoDto));
        log.info("Inserted moto point with id {}", moto.getId());
        return MotoDto.toDto(moto);
    }

    @PutMapping("/{id}")
    public MotoDto updateMoto(@PathVariable Long id,
                              @RequestParam(required = false) Float lat,
                              @RequestParam(required = false) Float lon,
                              @RequestBody(required = false) String body) {
        MotoDto motoDto = resolveMotoDto(id, lat, lon, body);
        Moto moto = motoService.updateMoto(id, motoDto.lat(), motoDto.lon());
        log.info("Updated moto point with id {}", moto.getId());
        return MotoDto.toDto(moto);
    }

    @GetMapping("/{id}")
    public MotoDto getMotoById(@PathVariable Long id) {
        Moto moto = motoService.getById(id);
        log.info("Loaded moto point with id {}", moto.getId());
        return MotoDto.toDto(moto);
    }

    @DeleteMapping("/{id}")
    public void deleteMotoById(@PathVariable Long id) {
        motoService.deleteMoto(id);
        log.info("Deleted moto point with id {}", id);
    }

    private MotoDto resolveMotoDto(Long id, Float lat, Float lon, String body) {
        if (lat != null && lon != null) {
            return new MotoDto(id, lat, lon);
        }

        if (body == null || body.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lat and lon are required");
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(body);
            Long resolvedId = firstLong(jsonNode, id, "id");
            Float resolvedLat = firstFloat(jsonNode, lat, "lat", "latitude");
            Float resolvedLon = firstFloat(jsonNode, lon, "lon", "lng", "longitude");

            if (resolvedLat == null || resolvedLon == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lat and lon are required");
            }

            return new MotoDto(resolvedId, resolvedLat, resolvedLon);
        } catch (JsonProcessingException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body must be valid JSON", exception);
        }
    }

    private static Long firstLong(JsonNode jsonNode, Long fallback, String... fieldNames) {
        if (fallback != null) {
            return fallback;
        }

        for (String fieldName : fieldNames) {
            JsonNode field = jsonNode.get(fieldName);
            if (field != null && field.canConvertToLong()) {
                return field.asLong();
            }
        }

        return null;
    }

    private static Float firstFloat(JsonNode jsonNode, Float fallback, String... fieldNames) {
        if (fallback != null) {
            return fallback;
        }

        for (String fieldName : fieldNames) {
            JsonNode field = jsonNode.get(fieldName);
            if (field != null && field.isNumber()) {
                return (float) field.asDouble();
            }
        }

        return null;
    }
}

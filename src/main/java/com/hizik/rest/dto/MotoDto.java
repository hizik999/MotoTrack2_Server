package com.hizik.rest.dto;


import com.hizik.domain.Moto;

import jakarta.validation.constraints.NotNull;

public record MotoDto(
        Long id,
        @NotNull Float lat,
        @NotNull Float lon
) {

    public static MotoDto toDto(Moto moto) {
        return new MotoDto(
                moto.getId(),
                moto.getLat(),
                moto.getLon()
        );
    }

    public static Moto toDomainObject(MotoDto motoDto) {
        return new Moto(
                motoDto.id(),
                motoDto.lat(),
                motoDto.lon()
        );
    }
}

package com.hizik.rest.dto;


import com.hizik.domain.Moto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MotoDto {

    private long id;
    private float lat;
    private float lon;

    public static MotoDto toDto(Moto moto){

        return new MotoDto(moto.getId(), moto.getLat(), moto.getLon());
    }

    public static Moto toDomainObject(MotoDto motoDto){

        return new Moto(motoDto.getId(), motoDto.getLat(), motoDto.getLon());
    }
}

package com.dnd.gooding.record.command.dto;

import com.dnd.gooding.record.infra.dto.response.Document;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordPlace {

    private String placeName;
    private String addressName;
    private Double placeLatitude;
    private Double placeLongitude;

    public static RecordPlace from(Document document) {
        return RecordPlace.builder()
                .placeName(document.getPlaceName())
                .addressName(document.getAddressName())
                .placeLatitude(Double.parseDouble(document.getX()))
                .placeLongitude(Double.parseDouble(document.getY()))
                .build();
    }
}

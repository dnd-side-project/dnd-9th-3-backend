package com.dnd.gooding.record.infra.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPlaceResponse {

    private Meta meta;
    private List<Document> documents;
}

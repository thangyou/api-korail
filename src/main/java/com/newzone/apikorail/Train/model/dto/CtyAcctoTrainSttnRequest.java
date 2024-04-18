package com.newzone.apikorail.Train.model.dto;

import lombok.Setter;

@Setter
public class CtyAcctoTrainSttnRequest {
    public String serviceKey; // 인증키
    public int numOfRows; // 한 페이지 결과 수
    public int pageNo; // 페이지 번호
    public String cityCode; // 시/도 ID
}

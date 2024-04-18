package com.newzone.apikorail.Train.model.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@RequiredArgsConstructor
@NoArgsConstructor
@Setter
public class TrainRequest {
    public String serviceKey; // 인증키
    public int numOfRows; // 한 페이지 결과 수
    public int pageNo; // 페이지 번호
    public String dataType; // 데이터 타입(xml, json)
    public String departurePlaceId; // 출발지 ID
    public String arrivalPlaceId; // 도착지 ID
    public String departureDate; // 출발일(YYYYMMDD)
    public String trainGradeCode; // 차량 종류 코드

    @Builder
    public TrainRequest(String serviceKey, int numOfRows, String dataType,
                        String departurePlaceId, String arrivalPlaceId,
                        String departureDate, String trainGradeCode) {
        this.serviceKey = serviceKey;
        this.numOfRows = numOfRows;
        this.dataType = dataType;
        this.departurePlaceId = departurePlaceId;
        this.arrivalPlaceId = arrivalPlaceId;
        this.departureDate = departureDate;
        this.trainGradeCode = trainGradeCode;
    }
}

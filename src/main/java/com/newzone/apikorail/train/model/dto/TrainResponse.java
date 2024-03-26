package com.newzone.apikorail.train.model.dto;

import com.newzone.apikorail.train.model.Train;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainResponse {
    public Long trainNo; // 열차 번호
    public String totalCount; // 데이터 총 개수
    public String trainGradeName; // 차량 종류명
    public String depTime; // 출발 시간
    public String arrTime; // 도착 시간
    public String depName; // 출발지
    public String arrName; // 도착지
    public Integer fare; // 운임(단위:원)

//    public TrainResponse(Train train) {
//        this.trainNo = train.getTrainNo();
//        this.trainGradeName = train.getTrainGradeName();
//        this.depTime = train.getDepTime();
//        this.arrTime = train.getArrTime();
//        this.depName = train.getDepName();
//        this.arrName = train.getArrName();
//        this.fare = train.getFare();
//    }
}

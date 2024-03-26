package com.newzone.apikorail.train.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Train {
    /**
     *  열차 정보
     *  1. 열차 번호
     *  2. 열차 종류
     *      1. KTX의 좌석
     *      2. 일반 열차의 좌석 • 입석(자유석)
     *          ITX-마음/청춘/새마을, 새마을, 무궁화, 누리로)
     *  3. 출발지
     *  4. 출발 시간
     *  5. 도착지
     *  6. 도착 시간
     *  7. 좌석 정보
     *      1. 종류 (일반식, 특/우등)
     *      2. 요금
     *      3. 좌석 번호
      */

    private Long trainNo; // 열차 번호
    private String trainGradeName; // 차량 종류명
    private String arrName; // 도착지
    private String arrTime; // 도착 시간
    private String depName; // 출발지
    private String depTime; // 출발 시간
    private Integer fare; // 운임

}

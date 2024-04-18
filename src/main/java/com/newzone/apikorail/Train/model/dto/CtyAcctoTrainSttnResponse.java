package com.newzone.apikorail.Train.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CtyAcctoTrainSttnResponse {
//    public int numOfRows; // 한 페이지 결과 수
//    public int pageNo; // 페이지 번호
//    public int totalCount; // 전체 결과 수
    public String nodeid; // 기차역 ID
    public String nodename; // 기차역명
}

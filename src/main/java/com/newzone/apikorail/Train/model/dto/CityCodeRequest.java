package com.newzone.apikorail.Train.model.dto;

import lombok.Setter;

@Setter
public class CityCodeRequest {
    public String serviceKey; // 인증키
    public String citycode; // 도시 코드
    public String cityname; // 도시명
}

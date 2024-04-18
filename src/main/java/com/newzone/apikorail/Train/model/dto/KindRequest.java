package com.newzone.apikorail.Train.model.dto;

import lombok.Setter;

@Setter
public class KindRequest {
    public String serviceKey; // 인증키
//    public String dataType; // 데이터 타입(xml, json)
    public String vehiclekndid;
    public String vehiclekndnm;
}

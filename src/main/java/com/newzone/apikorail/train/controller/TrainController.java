package com.newzone.apikorail.train.controller;

import com.newzone.apikorail.train.model.dto.TrainRequest;
import com.newzone.apikorail.train.model.dto.TrainResponse;
import com.newzone.apikorail.train.service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
@Controller
@Tag(name = "코레일 열차 정보 API", description = "열차 정보 조회")
@RestController
@RequestMapping("/trainInfo")
//@RestController // HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class TrainController
{
    /**
     * 조회 - get
     * 검색 - search(Controller & Service) / find(Repository)
     * 생성 - save
     * 수정 - update
     * 삭제 - delete
     */

    @Autowired
    public TrainService trainService;

    @Operation(summary = "출/도착지 기반 열차 정보 조회", description = "열차의 출발역, 도착역 정보를 조회")
//    @Parameter(name = "TrainRequest", description = "")
    @GetMapping("/get-trainInfo-of-station")
    public List<TrainResponse> getTrainInfo(@RequestParam("depPlaceId") String departurePlaceId,
                                            @RequestParam("arrPlaceId") String arrivalPlaceId,
                                            @RequestParam("depPlandTime") String departureDate,
                                            @RequestParam("trainGradeCode") String trainGradeCode,
                                            @RequestParam(value = "numOfRows", defaultValue = "10") int numOfRows,
                                            @RequestParam(value = "_type", defaultValue = "json") String dataType) throws IOException {
        TrainRequest trainRequest = new TrainRequest();
        trainRequest.setDeparturePlaceId(departurePlaceId);
        trainRequest.setArrivalPlaceId(arrivalPlaceId);
        trainRequest.setDepartureDate(departureDate);
        trainRequest.setTrainGradeCode(trainGradeCode);
        trainRequest.setNumOfRows(numOfRows);
        trainRequest.setDataType(dataType);

        return trainService.getTrainInfo(trainRequest);
    }

}

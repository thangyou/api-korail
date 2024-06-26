package com.newzone.apikorail.Train.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newzone.apikorail.Train.model.TrainKind;
import com.newzone.apikorail.Train.model.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class TrainService {

//    private final TrainRepository trainRepository;
    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${serviceKey}")
    private String serviceKey;


    // ================================================================================================================
    public String callExternalApi(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb); // 로그 확인용
        return sb.toString();
    }


    /**
     * 출/도착지 기반 열차 정보 조회
     */
    public List<TrainResponse> getTrainInfo(TrainRequest trainRequest) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/TrainInfoService/getStrtpntAlocFndTrainInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(String.valueOf(trainRequest.numOfRows), "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode(trainRequest.dataType, "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("depPlaceId","UTF-8") + "=" + URLEncoder.encode(trainRequest.departurePlaceId, "UTF-8")); /*출발기차역ID [상세기능3. 시/도별 기차역 목록조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("arrPlaceId","UTF-8") + "=" + URLEncoder.encode(trainRequest.arrivalPlaceId, "UTF-8")); /*도착기차역ID [상세기능3. 시/도별 기차역 목록조회]에서 조회 가능*/
        urlBuilder.append("&" + URLEncoder.encode("depPlandTime","UTF-8") + "=" + URLEncoder.encode(trainRequest.departureDate, "UTF-8")); /*출발일(YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("trainGradeCode","UTF-8") + "=" + URLEncoder.encode(trainRequest.trainGradeCode, "UTF-8")); /*차량종류코드*/

        String apiResponse = callExternalApi(urlBuilder.toString());

        List<TrainResponse> trainResponseList = new ArrayList<>();
        JsonNode rootNode = objectMapper.readTree(apiResponse);
        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
        for (JsonNode itemNode : itemsNode) {
            TrainResponse trainResponse = new TrainResponse();
            trainResponse.setTrainNo(Long.valueOf(itemNode.path("trainno").asText()));
            trainResponse.setTotalCount(rootNode.path("response").path("body").path("totalCount").asText());
            trainResponse.setTrainGradeName(itemNode.path("traingradename").asText());
            trainResponse.setDepTime(itemNode.path("depplandtime").asText());
            trainResponse.setArrTime(itemNode.path("arrplandtime").asText());
            trainResponse.setDepName(itemNode.path("depplacename").asText());
            trainResponse.setArrName(itemNode.path("arrplacename").asText());
            trainResponse.setFare(itemNode.path("adultcharge").asInt());
            trainResponseList.add(trainResponse);
        }
        return trainResponseList;
    }

    // ================================================================================================================

    /**
     * 차량 종류 목록 조회
     */
    public List<KindResponse> getKindOfTrain() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/TrainInfoService/getVhcleKndList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/

        String apiResponse = callExternalApi(urlBuilder.toString());


        List<KindResponse> kindResponseList = new ArrayList<>();
        JsonNode rootNode = objectMapper.readTree(apiResponse);
        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
            for (JsonNode itemNode : itemsNode) {
                KindResponse kindResponse = new KindResponse();
                kindResponse.setVehiclekndid(itemNode.path("vehiclekndid").asText());
                kindResponse.setVehiclekndnm(itemNode.path("vehiclekndnm").asText());
                kindResponseList.add(kindResponse);
            }
            return kindResponseList;
    }

    public String getKindIdOfTrain(KindRequest kindRequest) throws IOException {
        System.out.println("kindRequest.vehiclekndid  = " + kindRequest.vehiclekndid);

        KindResponse kindResponse = new KindResponse();
        kindResponse.setVehiclekndid(kindRequest.vehiclekndid);

        List<KindResponse> kindResponseList = getKindOfTrain();
        for (KindResponse kindName : kindResponseList) {
            if(kindName.getVehiclekndid().equals(kindRequest.vehiclekndid)) {
                System.out.println(">> kindName.getVehiclekndid().equals(kindRequest.vehiclekndid <<");
                kindResponse.setVehiclekndnm(kindName.getVehiclekndnm());
                System.out.println("kindResponse.getVehiclekndnm() = " + kindResponse.getVehiclekndnm());
                break;
            }
        }

        return kindResponse.getVehiclekndnm();
    }

    public String getKindNameOfTrain(KindRequest kindRequest) throws IOException {
        System.out.println("kindRequest.vehiclekndnm  = " + kindRequest.vehiclekndnm);

        KindResponse kindResponse = new KindResponse();
        kindResponse.setVehiclekndnm(kindRequest.vehiclekndnm);

        List<KindResponse> kindResponseList = getKindOfTrain();
        for (KindResponse kindName : kindResponseList) {
            if (kindName.getVehiclekndnm().equalsIgnoreCase(kindRequest.vehiclekndnm)) {
                System.out.println(">> kindName.getVehiclekndnm().equals(kindRequest.vehiclekndnm <<");
                kindResponse.setVehiclekndid(kindName.getVehiclekndid());
                System.out.println("kindResponse.getVehiclekndid() = " + kindResponse.getVehiclekndid());
                break;
            }
        }
        return kindResponse.getVehiclekndid();
    }

    // ================================================================================================================

    /**
     * 도시코드 목록 조회
     */
    public List<CityCodeResponse> getCityCodeList() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/TrainInfoService/getCtyCodeList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/

        String apiResponse = callExternalApi(urlBuilder.toString());

        List<CityCodeResponse> cityCodeList = new ArrayList<>();
        JsonNode rootNode = objectMapper.readTree(apiResponse);
        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
        for (JsonNode itemNode : itemsNode) {
            CityCodeResponse cityCodeResponse = new CityCodeResponse();
            cityCodeResponse.setCitycode(itemNode.path("citycode").asText());
            cityCodeResponse.setCityname(itemNode.path("cityname").asText());
            cityCodeList.add(cityCodeResponse);
        }
        return cityCodeList;
    }

//        // 응답 결과를 KindResponse 객체로 변환
//        TrainKind trainKind = new TrainKind();
//        JsonNode rootNode = objectMapper.readTree(sb.toString());
//        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
//        for (JsonNode itemNode : itemsNode) {
//            System.out.println("kindRequest.vehiclekndid  = " + kindRequest.vehiclekndid);
//            trainKind.setKindId(itemNode.path("vehiclekndid").asText());
//            System.out.println("trainKind.getKindId()  = " + trainKind.getKindId());
//            if (trainKind.getKindId().equals(kindRequest.vehiclekndid))
//                System.out.println("------------------------------------------------");
//                trainKind.setKindId(itemNode.path("vehiclekndid").asText());
//                trainKind.setKindName(itemNode.path("vehiclekndnm").asText());
//                System.out.println("trainKind.getKindId()  = " + trainKind.getKindId());
//                System.out.println("trainKind.getKindName()  = " + trainKind.getKindName());
//                break;
//        }
//        KindResponse kindResponse = new KindResponse();
//        kindResponse.setVehiclekndid(trainKind.getKindId());
//        kindResponse.setVehiclekndnm(trainKind.getKindName());
//        return kindResponse;
//    }


    // ================================================================================================================
    /**
     * 시/도별 기차역 목록조회
     */
    public List<CtyAcctoTrainSttnResponse> getCtyAcctoTrainSttnList(CtyAcctoTrainSttnRequest request) throws IOException  {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1613000/TrainInfoService/getCtyAcctoTrainSttnList"); /*URL*/urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(String.valueOf(request.pageNo), "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(String.valueOf(request.numOfRows), "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*데이터 타입(xml, json)*/
        urlBuilder.append("&" + URLEncoder.encode("cityCode","UTF-8") + "=" + URLEncoder.encode(request.cityCode, "UTF-8")); /*시/도 ID*/

        String apiResponse = callExternalApi(urlBuilder.toString());

        List<CtyAcctoTrainSttnResponse> CtyAcctoTrainSttnList = new ArrayList<>();
        JsonNode rootNode = objectMapper.readTree(apiResponse);
        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
        for (JsonNode itemNode : itemsNode) {
            CtyAcctoTrainSttnResponse ctyAcctoTrainSttnResponse = new CtyAcctoTrainSttnResponse();
            ctyAcctoTrainSttnResponse.setNodeid(itemNode.path("nodeid").asText());
            ctyAcctoTrainSttnResponse.setNodename(itemNode.path("nodename").asText());
            CtyAcctoTrainSttnList.add(ctyAcctoTrainSttnResponse);
        }
        return CtyAcctoTrainSttnList;
    }
}
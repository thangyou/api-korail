**API-Korail**
----

출발/도착지를 기준으로 열차(KTX 포함)의 운행 시간표 정보를 조회하는 열차 정보 서비스이다. 

[국토교통부(TAGO)의 열차 정보](https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15098552) 오픈 API를 활용한다.

## 출/도착지 기반 열차 정보 조회

### Request

* **URL**

  /TrainInfoService/getStrtpntAlocFndTrainInfo?

* **Method:**

  `GET` 

*  **URL Params**

   **Required:**

   `servicekey=[URL-Encode]`

   `depPlaceId=[String]`

   `arrPlaceId=[String]`

   `depPlandTime=[String]`

    **Optional:**

   `numOfRows=[integer]`

   `pageNo=[integer]`

   `_type=[String]`

   `trainGradeCode=[String]`

* **Data Params**

  <_If making a post request, what should the body payload look like? URL Params rules apply here too._>

### Response

* **Success Response:**

  <_What should the status code be on success and is there any returned data? This is useful when people need to to know what their callbacks should expect!_>

    * **Code:** 200 <br />
      **Content:** `OK`

* **Error Response:**

  <_Most endpoints will have many ways they can fail. From unauthorized access, to wrongful parameters etc. All of those should be liste d here. It might seem repetitive, but it helps prevent assumptions from being made where they should be._>

    * **Code:** 401 UNAUTHORIZED <br />
      **Content:** `{ error : "Log in" }`

  OR

    * **Code:** 422 UNPROCESSABLE ENTRY <br />
      **Content:** `{ error : "Email Invalid" }`

* **Example Call | Schema:**
```json
    [
      {
        "trainNo": 0,
        "totalCount": "string",
        "trainGradeName": "string",
        "depTime": "string",
        "arrTime": "string",
        "depName": "string",
        "arrName": "string",
        "fare": 0
      }
    ]
  ```
* **Notes:**

  <_This is where all uncertainties, commentary, discussion etc. can go. I recommend timestamping and identifying oneself when leaving comments here._>

## 차량 종류 목록 조회

### Request

* **URL**

  /TrainInfoService/getVhcleKndList?

* **Method:**

  `GET`

*  **URL Params**

   **Required:**

   `servicekey=[URL-Encode]`

   **Optional:**

   `_type=[String]`

### Response

* **Success Response:**

    * **Code:** 200 <br />
      **Content:** `OK`


* **Example Call | Schema:**
```json
    [
      {
        "vehiclekndid": "string",
        "vehiclekndnm": "string"
      }
    ]
  ```

## 시/도별 기차역 목록 조회

### Request

* **URL**

  /TrainInfoService/getCtyAcctoTrainSttnList?

* **Method:**

  `GET`

*  **URL Params**

   **Required:**

   `cityCode=[String]`

   **Optional:**

   `pageNo=[integer]`

   `numOfRows=[integer]`

### Response

* **Success Response:**

  <_What should the status code be on success and is there any returned data? This is useful when people need to to know what their callbacks should expect!_>

    * **Code:** 200 <br />
      **Content:** `OK`

* **Example Call | Schema:**
```json
    [
      {
        "nodeid": "string",
        "nodename": "string"
      }
    ]
  ```

## 도시 코드 목록 조회

### Request

* **URL**

  /TrainInfoService/getCtyCodeList

* **Method:**

  `GET`

### Response

* **Success Response:**

    * **Code:** 200 <br />
      **Content:** `OK`


* **Example Call | Schema:**
```json
    [
      {
        "citycode": "string",
        "cityname": "string"
      }
    ]
  ```

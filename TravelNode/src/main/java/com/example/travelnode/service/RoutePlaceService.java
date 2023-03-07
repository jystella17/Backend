package com.example.travelnode.service;

import com.example.travelnode.dto.PlaceRegisterRequestDto;
import com.example.travelnode.dto.RoutePlaceDto;
import com.example.travelnode.dto.SpotInfoDto;
import com.example.travelnode.entity.RoutePlace;
import com.example.travelnode.entity.SpotInfo;
import com.example.travelnode.repository.RoutePlaceRepository;
import com.example.travelnode.repository.SpotInfoRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RoutePlaceService {

    @Value("${app.kakao.rest-api-key}")
    private String kakaoApiKey;
    private final SpotInfoRepository spotInfoRepository;
    private final RoutePlaceRepository routePlaceRepository;

    public List<SpotInfoDto> keywordToLocationInfo(String loc, Double longitude, Double latitude) throws ParseException {
        List<SpotInfoDto> placeInfoList = new ArrayList<>();
        SpotInfo spot = spotInfoRepository.findSpotInfoBySpotName(loc.replaceAll(" ", ""));

        if(isSamePlace(loc, spot)) { // 해당 장소가 이미 DB에 저장되어 있는지 확인하고, 저장된 장소라면 값을 불러옴
            placeInfoList.add(SpotInfoDto.builder().spotName(spot.getSpotName()).address(spot.getAddress())
                    .longitude(spot.getLongitude()).latitude(spot.getLatitude()).build());

            return placeInfoList;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK " + kakaoApiKey);

        String kakaoMapUrl = "https://dapi.kakao.com/v2/local/search/keyword.json";
        String requestUrl = UriComponentsBuilder.fromHttpUrl(kakaoMapUrl)
                .queryParam("query", loc).build().toUriString();

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> placeInfos = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(placeInfos.getBody());
        JSONArray jsonArray = (JSONArray) jsonObject.get("documents");

        if(jsonArray.size() == 0) { // API에서 검색되는 장소 정보가 없는 경우
            // 단순히 사용자가 입력한 장소 이름, 현재 좌표만 저장, 주소는 empty string
            List<SpotInfoDto> noInfo = new ArrayList<>();
            noInfo.add(new SpotInfoDto(loc, "", longitude, latitude));
            return noInfo;
        }

        List<JSONObject> placeLists = new ArrayList<>(); // 현재 좌표를 기준으로 검색된 장소들을 가까운 거리 순으로 정렬
        for (Object o : jsonArray) {
            placeLists.add((JSONObject) o);
        }

        placeLists.sort((a, b) -> {
            double aLong = Double.parseDouble((String) a.get("x"));
            double aLat = Double.parseDouble((String) a.get("y"));
            double bLong = Double.parseDouble((String) b.get("x"));
            double bLat = Double.parseDouble((String) b.get("y"));

            double longDiffA = Math.toRadians(longitude - aLong);
            double latDiffA = Math.toRadians(latitude - aLat);
            double longDiffB = Math.toRadians(longitude - bLong);
            double latDiffB = Math.toRadians(latitude - bLat);

            double sinA = Math.sin(latDiffA / 2) * Math.sin(latDiffA / 2)
                    + Math.cos(Math.toRadians(aLat)) * Math.cos(Math.toRadians(latitude))
                    * Math.sin(longDiffA / 2) * Math.sin(longDiffA / 2);
            double sinB = Math.sin(latDiffB / 2) * Math.sin(latDiffB / 2)
                    + Math.cos(Math.toRadians(bLat)) * Math.cos(Math.toRadians(latitude))
                    * Math.sin(longDiffB / 2) * Math.sin(longDiffB / 2);

            double tanA = 2 * Math.atan2(Math.sqrt(sinA), Math.sqrt(1 - sinA));
            double tanB = 2 * Math.atan2(Math.sqrt(sinB), Math.sqrt(1 - sinB));

            // 지구 반지름을 6371km로 가정하고 거리 계산
            double distanceA = 6371 * tanA * 1000;
            double distanceB = 6371 * tanB * 1000;

            return Double.compare(distanceA, distanceB);
        });

        /**
        // 사용자가 입력한 장소 이름과 지도 API에서 받아온 placeName이 정확히 일치하지 않는 경우
        // 사용자가 입력한 loc과 지도 API에서 받아온 placeName이 다른 것으로 판단되면 loc이 장소 이름으로 저장되도록 함
        String placeName = (String) jsonObject.get("place_name");
        if(!placeName.equals(loc) && !isSamePlace(loc, placeName)) {
            System.out.println(placeName + " / " + loc);

            if(jsonObject.get("road_address_name").equals("")) {
                String address = (String) jsonObject.get("address_name");
                return new SpotInfoDto(loc, address.replaceAll("\\d", "")
                        .replaceAll("-", "").trim(), longitude, latitude);
            }

            String address = (String) jsonObject.get("road_address_name");
            return new SpotInfoDto(loc, address.replaceAll("\\d", "")
                    .replaceAll("-", "").trim(), longitude, latitude);
        }

        jsonObject = (JSONObject) sortByDistance.get(0);
        if(jsonObject.get("road_address_name").equals(""))
            return new SpotInfoDto((String) jsonObject.get("place_name"), (String) jsonObject.get("address_name"),
                    Double.parseDouble((String) jsonObject.get("x")), Double.parseDouble((String) jsonObject.get("y")));

        return new SpotInfoDto((String) jsonObject.get("place_name"), (String) jsonObject.get("road_address_name"),
                Double.parseDouble((String) jsonObject.get("x")), Double.parseDouble((String) jsonObject.get("y")));
         **/

        for (JSONObject object : placeLists) {
            if (object.get("road_address_name").equals("")) {
                placeInfoList.add(new SpotInfoDto((String) object.get("place_name"), (String) object.get("address_name"),
                        Double.parseDouble((String) object.get("x")), Double.parseDouble((String) object.get("y"))));
            } else {
                placeInfoList.add(new SpotInfoDto((String) object.get("place_name"), (String) object.get("road_address_name"),
                        Double.parseDouble((String) object.get("x")), Double.parseDouble((String) object.get("y"))));
            }
        }
        return placeInfoList;
    }

    @Transactional
    public RoutePlace registerRoutePlace(PlaceRegisterRequestDto placeRegisterRequestDto) throws ParseException {
        String routeName = placeRegisterRequestDto.getRouteName();
        /**
        Route route = routeRepository.findRouteByRouteName(placeRegisterRequestDto.getRouteName());
        if (route == null) {
            throw new NullPointerException("No such route exists.");
        }
         **/
        String spotName = placeRegisterRequestDto.getSpotName();
        SpotInfo spot = spotInfoRepository.findSpotInfoBySpotName(spotName);
        if(spot == null) { // 이전에 등록된 적 없는 장소인 경우 SpotInfo 테이블에 정보 저장
            SpotInfoDto spotInfoDto = SpotInfoDto.builder().spotName(spotName).address(placeRegisterRequestDto.getAddress())
                    .longitude(placeRegisterRequestDto.getLongitude()).latitude(placeRegisterRequestDto.getLatitude()).build();

            spot = spotInfoRepository.save(spotInfoDto.toEntity());
        }

        RoutePlaceDto routePlaceDto = RoutePlaceDto.builder().spot(spot).placeName(spotName).routeName(routeName)
                .priority(placeRegisterRequestDto.getPriority()).visitTime(LocalDateTime.now()).build();

        return routePlaceRepository.save(routePlaceDto.toEntity());
    }

    public Boolean isSamePlace(String loc, SpotInfo spot) {
        if(spot == null) return false;
        // 오타를 고려하여 사용자가 입력한 값과 지도 API로 검색된 값이 2글자 이상 다를 경우 같은 장소가 아닌 것으로 판단
        // -> DB에 저장된 장소 정보를 쓰지 않고 지도 API에서 새로 장소 정보 검색
        loc = loc.replaceAll(" ", "").trim();
        String dbLoc = spot.getSpotName().replaceAll(" ", "").trim();

        return loc.equals(dbLoc);
    }

    @Transactional
    public RoutePlace updatePlaceName(String prevName, String placeName) {
        RoutePlace routePlace = routePlaceRepository.findBySpotName(prevName);
        if(routePlace == null) {
            throw new NullPointerException("Cannot find place");
        }

        routePlace.update(placeName);
        return routePlace;
    }

    @Transactional
    public void deletePlace(Long placeId, String routeName, Integer priority) {
        routePlaceRepository.deleteById(placeId);

        // 장소가 삭제된 후, 같은 루트에 포함된 다른 장소들의 priority 재설정
        for(int i=priority+1; i<=5; i++) {
            RoutePlace routePlace = routePlaceRepository.findByRouteNameAndPriority(routeName, i);
            if(routePlace == null)
                break;

            routePlace.changePriority(routePlace.getPriority()-1);
        }
    }

    public List<RoutePlace> allPlacesInRoute(String routeName) {

        return routePlaceRepository.findAllByRouteName(routeName);
    }
}

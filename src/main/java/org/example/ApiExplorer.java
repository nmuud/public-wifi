package org.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApiExplorer {
    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088";
    private static final String API_KEY = "70586e79766e6d753934436c4b4d69"; // 인증 키
    private static final String RESPONSE_FORMAT = "json";
    private static final String SERVICE_NAME = "TbPublicWifiInfo";

    private String url;

    /**
     * API 요청 URL 생성
     *
     * @param startIdx 요청 시작 위치
     * @param endIdx   요청 종료 위치
     * @throws Exception URL 인코딩 오류 또는 기타 예외 발생 가능성
     */
    public void getURL(int startIdx, int endIdx) throws Exception {
        this.url = String.format("%s/%s/%s/%s/%d/%d",
                BASE_URL,
                URLEncoder.encode(API_KEY, "UTF-8"),
                URLEncoder.encode(RESPONSE_FORMAT, "UTF-8"),
                URLEncoder.encode(SERVICE_NAME, "UTF-8"),
                startIdx,
                endIdx
        );
    }

    /**
     * API 로부터 JSON 데이터 수신
     *
     * @return JSON 형식의 응답 데이터를 포함한 StringBuilder 객체
     * @throws IOException 데이터 수신 오류 발생 가능성
     */
    public StringBuilder getJson() throws IOException {
        if (url == null) {
            throw new IllegalStateException("URL이 설정되지 않았습니다. 먼저 getURL 메서드를 호출하십시오.");
        }

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300
                        ? conn.getInputStream()
                        : conn.getErrorStream()))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb;
        } finally {
            conn.disconnect();
        }
    }

    /**
     * API 에서 제공하는 전체 데이터 개수 조회
     *
     * @return 전체 데이터 개수
     * @throws Exception URL 생성 오류 또는 JSON 파싱 오류 발생 가능성
     */
    public int getTotalCount() throws Exception {
        getURL(1, 1);
        StringBuilder response = getJson();

        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonObject data = jsonObject.getAsJsonObject("TbPublicWifiInfo");
        JsonElement totalCnt = data.get("list_total_count");

        return totalCnt.getAsInt();
    }
}

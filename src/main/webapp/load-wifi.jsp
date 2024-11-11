<%@ page import="org.example.ApiExplorer" %>
<%@ page import="org.example.service.WifiService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    ApiExplorer apiExplorer = new ApiExplorer();
    WifiService wifiService = new WifiService();
    int totalCnt = 0;
    boolean isDataSaved = false;

    try {
        totalCnt = apiExplorer.getTotalCount(); // API로부터 총 WIFI 정보 수 불러오기
        isDataSaved = wifiService.saveWifiData(); // 데이터 저장 상태 확인
    } catch (Exception e) {
        e.printStackTrace(); // 로그 기록
    }
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
</head>
<body>
    <div style="text-align: center">
        <h1>
            <% if (isDataSaved) { %>
                <%= totalCnt %>개의 WIFI 정보를 정상적으로 저장하였습니다.
            <% } else { %>
                와이파이 정보가 이미 저장되어 있습니다.
            <% } %>
        </h1>
        <a href="/zero-wifi/">홈으로 가기</a>
    </div>
</body>
</html>

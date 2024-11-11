<%@ page import="org.example.service.WifiService" %>
<%@ page import="org.example.dto.WifiDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.Pos" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    WifiService wifiService = new WifiService();

    // 위도와 경도 파라미터가 존재하는지 확인 후 처리
    String latParam = request.getParameter("lat");
    String lntParam = request.getParameter("lnt");

    if (latParam != null && lntParam != null) {
        double lat = Double.parseDouble(latParam);
        double lnt = Double.parseDouble(lntParam);

        // 위치 정보를 기반으로 와이파이 리스트 조회
        Pos pos = new Pos(lat, lnt);
        ArrayList<WifiDTO> list = wifiService.getWifiList(pos);

        // 조회된 리스트를 세션에 설정하고 리다이렉트
        session.setAttribute("list", list);
        response.sendRedirect("/zero-wifi/?lat=" + lat + "&lnt=" + lnt);
    } else {
        // 필요한 파라미터가 없는 경우의 예외 처리
        response.sendRedirect("/zero-wifi/");
    }
%>

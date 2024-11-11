<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.dto.WifiDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String latParam = request.getParameter("lat");
    String lntParam = request.getParameter("lnt");
    double lat = latParam == null ? 0.0 : Double.parseDouble(latParam);
    double lnt = lntParam == null ? 0.0 : Double.parseDouble(lntParam);

    ArrayList<WifiDTO> list = (ArrayList<WifiDTO>) session.getAttribute("list");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" href="css/style.css"/>
    <script>
        function getPosition() {
            const lat = document.getElementById("lat");
            const lnt = document.getElementById("lnt");

            navigator.geolocation.getCurrentPosition((position) => {
                lat.value = position.coords.latitude.toFixed(6);
                lnt.value = position.coords.longitude.toFixed(6);
            });
        }

        function validateForm() {
            const lat = document.getElementById("lat").value.trim();
            const lnt = document.getElementById("lnt").value.trim();

            if (lat === "" || lnt === "" || isNaN(lat) || isNaN(lnt)) {
                alert("LAT와 LNT 값을 올바르게 입력해 주세요.");
                return false;
            }
            return true;
        }
    </script>
    <style>
        .form-container {
            text-align: center;
            margin-bottom: 20px;
        }

        .form-container input[type="text"],
        .form-container input[type="button"],
        .form-container input[type="submit"] {
            margin: 5px;
            padding: 8px 12px;
            border-radius: 5px;
            border: 1px solid #ccc;
            font-size: 1em;
        }

        .form-container input[type="button"],
        .form-container input[type="submit"] {
            background-color: #3498db;
            color: white;
            border: none;
            cursor: pointer;
        }

        .form-container input[type="button"]:hover,
        .form-container input[type="submit"]:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
    <h1 style="text-align: center;">와이파이 정보 구하기</h1>
    <div class="buttons" style="text-align: center; margin-bottom: 20px;">
        <a href="/zero-wifi/">홈</a>
        <a href="history.jsp">위치 히스토리 목록</a>
        <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
        <a href="bookmark-list.jsp">북마크 보기</a>
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </div>

    <!-- Form Container to Center Align Form Elements -->
    <div class="form-container">
        <form action="getWifi.jsp" method="post" onsubmit="return validateForm()">
            <label for="lat">
                LAT: <input type="text" id="lat" name="lat" value="<%= lat %>"/>
            </label>
            <label for="lnt">
                LNT: <input type="text" id="lnt" name="lnt" value="<%= lnt %>"/>
            </label>
            <input type="button" value="내 위치 가져오기" onclick="getPosition()">
            <input type="submit" value="근처 WIFI 정보 보기">
        </form>
    </div>

    <table>
        <thead>
            <tr>
                <th>거리(Km)</th>
                <th>관리번호</th>
                <th>자치구</th>
                <th>와이파이명</th>
                <th>도로명주소</th>
                <th>상세주소</th>
                <th>설치위치(층)</th>
                <th>설치유형</th>
                <th>설치기관</th>
                <th>서비스구분</th>
                <th>망종류</th>
                <th>설치년도</th>
                <th>실내외구분</th>
                <th>WIFI접속환경</th>
                <th>X 좌표</th>
                <th>Y 좌표</th>
                <th>작업 일자</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (request.getParameter("lat") == null && request.getParameter("lnt") == null) {
            %>
            <tr>
                <td colspan="17" class="table_blank">위치 정보를 입력한 후에 조회해 주세요.</td>
            </tr>
            <%
                } else if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
            %>
            <tr>
                <td><%= list.get(i).getDistance() %></td>
                <td><%= list.get(i).getX_SWIFI_MGR_NO() %></td>
                <td><%= list.get(i).getX_SWIFI_WRDOFC() %></td>
                <td>
                    <a href="detail.jsp?distance=<%= list.get(i).getDistance() %>&mgrNo=<%= list.get(i).getX_SWIFI_MGR_NO() %>">
                        <%= list.get(i).getX_SWIFI_MAIN_NM() %>
                    </a>
                </td>
                <td><%= list.get(i).getX_SWIFI_ADRES1() %></td>
                <td><%= list.get(i).getX_SWIFI_ADRES2() %></td>
                <td><%= list.get(i).getX_SWIFI_INSTL_FLOOR() %></td>
                <td><%= list.get(i).getX_SWIFI_INSTL_TY() %></td>
                <td><%= list.get(i).getX_SWIFI_INSTL_MBY() %></td>
                <td><%= list.get(i).getX_SWIFI_SVC_SE() %></td>
                <td><%= list.get(i).getX_SWIFI_CMCWR() %></td>
                <td><%= list.get(i).getX_SWIFI_CNSTC_YEAR() %></td>
                <td><%= list.get(i).getX_SWIFI_INOUT_DOOR() %></td>
                <td><%= list.get(i).getX_SWIFI_REMARS3() %></td>
                <td><%= list.get(i).getLNT() %></td>
                <td><%= list.get(i).getLAT() %></td>
                <td><%= list.get(i).getWORK_DTTM() %></td>
            </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>

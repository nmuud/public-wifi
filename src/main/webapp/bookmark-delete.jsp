<%@ page import="org.example.service.BookmarkService" %>
<%@ page import="org.example.dto.BookmarkDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    BookmarkService bookmarkService = new BookmarkService();
    int id = Integer.parseInt(request.getParameter("id"));
    BookmarkDTO bookmarkDTO = bookmarkService.getBookmarkInfo(id);
%>

<!DOCTYPE html>
<html>
    <head>
        <title>와이파이 정보 구하기</title>
        <link rel="stylesheet" href="css/style.css"/>
    </head>
    <body>
        <h1>북마크 삭제</h1>
        <div class="buttons">
            <a href="/zero-wifi/">홈</a>
            <a href="history.jsp">위치 히스토리 목록</a>
            <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
            <a href="bookmark-list.jsp">북마크 보기</a>
            <a href="bookmark-group.jsp">북마크 그룹 관리</a>
        </div>

        <p>북마크를 삭제하시겠습니까?</p>
        <form action="bookmark-delete-submit.jsp" method="post">
            <input type="hidden" name="id" value="<%=id%>">
            <table>
                <tr>
                    <th>북마크 이름</th>
                    <td><%=bookmarkDTO.getBookmarkGroupName()%></td>
                </tr>
                <tr>
                    <th>와이파이 명</th>
                    <td><%=bookmarkDTO.getWifiName()%></td>
                </tr>
                <tr>
                    <th>등록일자</th>
                    <td><%=bookmarkDTO.getRegDate()%></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center">
                        <a href="bookmark-list.jsp" class="styled-button">돌아가기</a>
                        <input type="submit" value="삭제" class="styled-button">
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>

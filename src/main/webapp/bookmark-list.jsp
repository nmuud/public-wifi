<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.dto.BookmarkDTO" %>
<%@ page import="org.example.service.BookmarkService" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    BookmarkService bookmarkService = new BookmarkService();
    ArrayList<BookmarkDTO> bookmarkList = bookmarkService.getBookmarkList();
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
    <h1>북마크 보기</h1>
    <div class="buttons">
        <a href="/zero-wifi/">홈</a>
        <a href="history.jsp">위치 히스토리 목록</a>
        <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
        <a href="bookmark-list.jsp">북마크 보기</a>
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </div>

    <a href="bookmark-group-add.jsp" class="bookmark-submit">
        북마크 그룹 이름 추가
    </a>

    <table>
        <tr>
            <th>ID</th>
            <th>북마크 이름</th>
            <th>와이파이명</th>
            <th>등록일자</th>
            <th>비고</th>
        </tr>
        <% if (bookmarkList.isEmpty()) { %>
            <tr>
                <td colspan="6" class="table_blank">정보가 존재하지 않습니다.</td>
            </tr>
        <% } else {
            for (BookmarkDTO bookmark : bookmarkList) {
        %>
            <tr>
                <td><%= bookmark.getBookmarkId() %></td>
                <td><%= bookmark.getBookmarkGroupName() %></td>
                <td><%= bookmark.getWifiName() %></td>
                <td><%= bookmark.getRegDate() %></td>
                <td style="text-align: center">
                    <a href="bookmark-delete.jsp?id=<%= bookmark.getBookmarkId() %>" class="styled-button">삭제</a>
                </td>
            </tr>
        <% }
        } %>
    </table>
</body>
</html>

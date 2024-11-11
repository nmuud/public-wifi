<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.entity.BookmarkGroup" %>
<%@ page import="org.example.service.BookmarkService" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    BookmarkService bookmarkService = new BookmarkService();
    ArrayList<BookmarkGroup> bookmarkGroupList = bookmarkService.getBookmarkGroupList();
%>

<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
    <h1>북마크 그룹 관리</h1>
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
            <th>순서</th>
            <th>등록일자</th>
            <th>수정일자</th>
            <th>비고</th>
        </tr>

        <% if (bookmarkGroupList.isEmpty()) { %>
            <tr>
                <td colspan="6" class="table_blank">정보가 존재하지 않습니다.</td>
            </tr>
        <% } else {
            for (BookmarkGroup group : bookmarkGroupList) {
        %>
            <tr>
                <td><%= group.getBookmarkGroupId() %></td>
                <td><%= group.getBookmarkGroupName() %></td>
                <td><%= group.getBookmarkGroupSeq() %></td>
                <td><%= group.getRegDate() %></td>

                <% if (group.getUpdateDate().getYear() == 1900) { %>
                    <td></td>
                <% } else { %>
                    <td><%= group.getUpdateDate() %></td>
                <% } %>

                <td style="text-align: center">
                    <a href="bookmark-group-edit.jsp?id=<%= group.getBookmarkGroupId() %>" class="styled-button">수정</a>
                    <a href="bookmark-group-delete.jsp?id=<%= group.getBookmarkGroupId() %>" class="styled-button">삭제</a>
                </td>
            </tr>
        <% }
        } %>
    </table>
</body>
</html>

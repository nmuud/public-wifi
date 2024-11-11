<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.service.BookmarkService" %>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    BookmarkService bookmarkService = new BookmarkService();
    bookmarkService.deleteBookmarkGroup(id);  // 삭제 수행
%>

<script>
    alert('북마크 그룹 정보를 삭제하였습니다.');
    location.href = "bookmark-group.jsp";
</script>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.service.BookmarkService" %>

<%
    request.setCharacterEncoding("UTF-8");

    int id = Integer.parseInt(request.getParameter("id"));
    String bookmarkGroupName = request.getParameter("bookmark_group_name");
    int bookmarkGroupSeq = Integer.parseInt(request.getParameter("bookmark_group_seq"));

    BookmarkService bookmarkService = new BookmarkService();
    bookmarkService.updateBookmarkGroup(bookmarkGroupName, bookmarkGroupSeq, id); // 수정 수행
%>

<script>
    alert('북마크 그룹 정보를 수정하였습니다.');
    location.href = "bookmark-group.jsp";
</script>

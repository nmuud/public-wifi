<%@ page import="org.example.service.BookmarkService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.example.entity.BookmarkGroup" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    request.setCharacterEncoding("UTF-8");
    BookmarkService bookmarkService = new BookmarkService();
    String bookmarkGroupIdParam = request.getParameter("bookmark_group_id");
    String mgrNo = request.getParameter("mgrNo");

    if (bookmarkGroupIdParam == null || bookmarkGroupIdParam.isEmpty()) {
        ArrayList<BookmarkGroup> bookmarkGroups = bookmarkService.getBookmarkGroupList();

        if (bookmarkGroups.isEmpty()) {
%>
            <script>
                alert('북마크 그룹을 먼저 생성해주세요.');
                location.href = 'bookmark-group.jsp';
            </script>
<%
        } else {
%>
            <script>
                alert("북마크 그룹을 선택해주세요.");
                history.go(-1);
            </script>
<%
        }
    } else {
        int bookmarkGroupId = Integer.parseInt(bookmarkGroupIdParam);

        if (bookmarkService.createBookmark(bookmarkGroupId, mgrNo)) {
%>
            <script>
                alert("북마크 정보를 추가하였습니다.");
                location.href = "bookmark-list.jsp";
            </script>
<%
        }
    }
%>

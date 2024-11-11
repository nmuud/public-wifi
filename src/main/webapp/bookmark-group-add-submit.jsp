<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.service.BookmarkService" %>

<%
    request.setCharacterEncoding("UTF-8");
    BookmarkService bookmarkService = new BookmarkService();

    // 북마크 이름과 순서 파라미터 가져오기
    String bookmarkSeqParam = request.getParameter("bookmark_seq");
    String bookmarkName = request.getParameter("bookmark_name");

    String message;
    String redirectUrl = "bookmark-group.jsp";

    try {
        // bookmark_seq가 숫자인지 확인하고 그룹 추가 처리
        int bookmarkSeq = Integer.parseInt(bookmarkSeqParam);

        if (bookmarkService.createBookmarkGroup(bookmarkName, bookmarkSeq)) {
            message = "북마크 그룹 정보를 추가하였습니다.";
        } else {
            message = "북마크 그룹 추가에 실패하였습니다.";
            redirectUrl = "javascript:history.back()"; // 실패 시 이전 페이지로 돌아가기
        }
    } catch (NumberFormatException e) {
        message = "순서는 숫자만 입력 가능합니다.";
        redirectUrl = "javascript:history.back()";
    }
%>

<script>
    alert('<%= message %>');
    location.href = '<%= redirectUrl %>';
</script>

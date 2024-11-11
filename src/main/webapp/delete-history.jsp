<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.service.WifiService" %>

<%
    request.setCharacterEncoding("UTF-8");
    WifiService wifiService = new WifiService();
    int id = Integer.parseInt(request.getParameter("id"));
    wifiService.deleteHistory(id);  // 삭제 작업 수행
%>

<script>
    if (confirm('삭제하시겠습니까?')) {
        alert("삭제 완료되었습니다.");
        location.href = "history.jsp";
    } else {
        history.back();
    }
</script>

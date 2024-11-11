<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>와이파이 정보 구하기</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
    <h1>북마크 그룹 추가</h1>
    <div class="buttons">
        <a href="/zero-wifi/">홈</a>
        <a href="history.jsp">위치 히스토리 목록</a>
        <a href="load-wifi.jsp">Open API 와이파이 정보 가져오기</a>
        <a href="bookmark-list.jsp">북마크 보기</a>
        <a href="bookmark-group.jsp">북마크 그룹 관리</a>
    </div>

    <form action="bookmark-group-add-submit.jsp" method="post" onsubmit="return validateForm()">
        <table>
            <tr>
                <th><label for="bookmark_name">북마크 이름</label></th>
                <td><input type="text" name="bookmark_name" id="bookmark_name" required></td>
            </tr>
            <tr>
                <th><label for="bookmark_seq">순서</label></th>
                <td><input type="number" name="bookmark_seq" id="bookmark_seq" required min="1"></td>
            </tr>
            <tr>
                <td colspan="2" style="text-align: center">
                    <input type="submit" value="추가">
                </td>
            </tr>
        </table>
    </form>

    <script>
        function validateForm() {
            const name = document.getElementById("bookmark_name").value.trim();
            const seq = document.getElementById("bookmark_seq").value.trim();

            if (name === "") {
                alert("북마크 이름을 입력해주세요.");
                return false;
            }

            if (!/^\d+$/.test(seq) || parseInt(seq) < 1) {
                alert("순서는 1 이상의 숫자여야 합니다.");
                return false;
            }
            return true;
        }
    </script>
</body>
</html>

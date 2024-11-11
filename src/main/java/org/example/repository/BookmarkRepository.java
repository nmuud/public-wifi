package org.example.repository;

import org.example.DBConnection;
import org.example.dto.BookmarkDTO;
import org.example.dto.BookmarkGroupDTO;
import org.example.entity.BookmarkGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BookmarkRepository {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    // 데이터베이스 연결 해제
    private void disconnect() {
        try {
            if (rs != null && !rs.isClosed()) rs.close();
            if (pstmt != null && !pstmt.isClosed()) pstmt.close();
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 현재 시간을 "yyyy-MM-dd HH:mm:ss" 형식의 문자열로 반환
    private String formatLocalDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    // 문자열 형식의 날짜를 LocalDateTime 객체로 변환
    private LocalDateTime parseLocalDateTime(String datetimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(datetimeString, formatter);
    }

    // 북마크 추가
    public boolean createBookmark(int id, String mgrNo) {
        conn = DBConnection.DBConnect();
        String sql = "INSERT INTO BOOKMARK (BOOKMARK_GROUP_ID, X_SWIFI_MGR_NO, REG_DATE) VALUES (?, ?, ?)";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, mgrNo);
            pstmt.setString(3, formatLocalDateTime());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            disconnect();
        }
    }

    // 전체 북마크 조회
    public ArrayList<BookmarkDTO> getBookmarkList() {
        conn = DBConnection.DBConnect();
        ArrayList<BookmarkDTO> list = new ArrayList<>();
        String sql = "SELECT b.BOOKMARK_ID, bg.BOOKMARK_GROUP_NAME, w.X_SWIFI_MAIN_NM, b.REG_DATE" +
                " FROM BOOKMARK b JOIN BOOKMARK_GROUP bg ON b.BOOKMARK_GROUP_ID = bg.BOOKMARK_GROUP_ID" +
                " JOIN WIFI w ON b.X_SWIFI_MGR_NO = w.X_SWIFI_MGR_NO";

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookmarkDTO bookmark = new BookmarkDTO();
                bookmark.setBookmarkId(rs.getInt("bookmark_id"));
                bookmark.setBookmarkGroupName(rs.getString("bookmark_group_name"));
                bookmark.setWifiName(rs.getString("x_swifi_main_nm"));
                bookmark.setRegDate(parseLocalDateTime(rs.getString("reg_date")));
                list.add(bookmark);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }

        return list;
    }

    // 북마크 조회
    public BookmarkDTO getBookmarkInfo(int id) {
        conn = DBConnection.DBConnect();
        String sql = "SELECT b.BOOKMARK_ID, bg.BOOKMARK_GROUP_NAME, w.X_SWIFI_MAIN_NM, b.REG_DATE" +
                " FROM BOOKMARK b JOIN BOOKMARK_GROUP bg ON b.BOOKMARK_GROUP_ID = bg.BOOKMARK_GROUP_ID" +
                " JOIN WIFI w ON b.X_SWIFI_MGR_NO = w.X_SWIFI_MGR_NO WHERE BOOKMARK_ID = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                BookmarkDTO bookmarkDTO = new BookmarkDTO();
                bookmarkDTO.setBookmarkId(rs.getInt("bookmark_id"));
                bookmarkDTO.setBookmarkGroupName(rs.getString("bookmark_group_name"));
                bookmarkDTO.setWifiName(rs.getString("x_swifi_main_nm"));
                bookmarkDTO.setRegDate(parseLocalDateTime(rs.getString("reg_date")));
                return bookmarkDTO;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
        return null;
    }

    // 북마크 삭제
    public boolean deleteBookmark(int id) {
        conn = DBConnection.DBConnect();
        String sql = "DELETE FROM BOOKMARK WHERE BOOKMARK_ID = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    // 북마크 그룹 추가
    public boolean createBookmarkGroup(String name, int sequence) {
        conn = DBConnection.DBConnect();
        String sql = "INSERT INTO BOOKMARK_GROUP (BOOKMARK_GROUP_NAME, BOOKMARK_GROUP_SEQ, REG_DATE, UPDATE_DATE) VALUES (?, ?, ?, ?)";
        String formattedDateTime = formatLocalDateTime();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2, sequence);
            pstmt.setString(3, formattedDateTime);

            // 초기 수정일자는 1900년으로 설정
            LocalDateTime tempDate = LocalDateTime.of(1900, 1, 1, 0, 0, 0);
            pstmt.setString(4, tempDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    // 전체 북마크 그룹 조회
    public ArrayList<BookmarkGroup> getBookmarkGroupList() {
        conn = DBConnection.DBConnect();
        ArrayList<BookmarkGroup> list = new ArrayList<>();
        String sql = "SELECT * FROM BOOKMARK_GROUP ORDER BY BOOKMARK_GROUP_SEQ";

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                BookmarkGroup bookmarkGroup = new BookmarkGroup();
                bookmarkGroup.setBookmarkGroupId(rs.getInt("bookmark_group_id"));
                bookmarkGroup.setBookmarkGroupName(rs.getString("bookmark_group_name"));
                bookmarkGroup.setBookmarkGroupSeq(rs.getInt("bookmark_group_seq"));
                bookmarkGroup.setRegDate(parseLocalDateTime(rs.getString("reg_date")));
                bookmarkGroup.setUpdateDate(parseLocalDateTime(rs.getString("update_date")));
                list.add(bookmarkGroup);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }

        return list;
    }

    // 북마크 그룹 조회
    public BookmarkGroupDTO getBookmarkGroupInfo(int id) {
        conn = DBConnection.DBConnect();
        BookmarkGroupDTO bookmarkGroup = new BookmarkGroupDTO();
        String sql = "SELECT BOOKMARK_GROUP_NAME, BOOKMARK_GROUP_SEQ FROM BOOKMARK_GROUP WHERE BOOKMARK_GROUP_ID = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                bookmarkGroup.setBookmarkGroupName(rs.getString("bookmark_group_name"));
                bookmarkGroup.setBookmarkGroupSeq(rs.getInt("bookmark_group_seq"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }

        return bookmarkGroup;
    }

    // 북마크 그룹 수정
    public boolean updateBookmarkGroup(String bookmarkGroupName, int bookmarkGroupSeq, int id) {
        conn = DBConnection.DBConnect();
        String sql = "UPDATE BOOKMARK_GROUP SET BOOKMARK_GROUP_NAME = ?, BOOKMARK_GROUP_SEQ = ?, UPDATE_DATE = ? WHERE BOOKMARK_GROUP_ID = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookmarkGroupName);
            pstmt.setInt(2, bookmarkGroupSeq);
            pstmt.setString(3, formatLocalDateTime());
            pstmt.setInt(4, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    // 북마크 그룹 삭제
    public boolean deleteBookmarkGroup(int id) {
        conn = DBConnection.DBConnect();
        String sql = "DELETE FROM BOOKMARK_GROUP WHERE BOOKMARK_GROUP_ID = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }
}

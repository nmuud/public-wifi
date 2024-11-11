package org.example.repository;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.ApiExplorer;
import org.example.DBConnection;
import org.example.Pos;
import org.example.dto.WifiDTO;
import org.example.entity.PositionHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WifiRepository {
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private final ApiExplorer apiExplorer = new ApiExplorer();

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

    // 와이파이 데이터 저장
    public boolean saveWifiData() {
        conn = DBConnection.DBConnect();
        int start, end;

        try {
            int total = apiExplorer.getTotalCount();
            int lastPage = total / 1000;
            int lastPageRemain = total % 1000;

            for (int i = 0; i <= lastPage; i++) {
                start = 1000 * i + 1;
                end = (i == lastPage) ? start + lastPageRemain - 1 : 1000 * (i + 1);

                apiExplorer.getURL(start, end);
                StringBuilder sb = apiExplorer.getJson();

                JsonObject jsonObject = (JsonObject) JsonParser.parseString(sb.toString());
                JsonObject data = (JsonObject) jsonObject.get("TbPublicWifiInfo");
                JsonArray jsonArray = data.getAsJsonArray("row");

                for (int j = 0; j < jsonArray.size(); j++) {
                    JsonObject temp = (JsonObject) jsonArray.get(j);
                    String sql = "INSERT INTO WIFI VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, temp.get("X_SWIFI_MGR_NO").getAsString());
                    pstmt.setString(2, temp.get("X_SWIFI_WRDOFC").getAsString());
                    pstmt.setString(3, temp.get("X_SWIFI_MAIN_NM").getAsString());
                    pstmt.setString(4, temp.get("X_SWIFI_ADRES1").getAsString());
                    pstmt.setString(5, temp.get("X_SWIFI_ADRES2").getAsString());
                    pstmt.setString(6, temp.get("X_SWIFI_INSTL_FLOOR").getAsString());
                    pstmt.setString(7, temp.get("X_SWIFI_INSTL_TY").getAsString());
                    pstmt.setString(8, temp.get("X_SWIFI_INSTL_MBY").getAsString());
                    pstmt.setString(9, temp.get("X_SWIFI_SVC_SE").getAsString());
                    pstmt.setString(10, temp.get("X_SWIFI_CMCWR").getAsString());
                    pstmt.setString(11, temp.get("X_SWIFI_CNSTC_YEAR").getAsString());
                    pstmt.setString(12, temp.get("X_SWIFI_INOUT_DOOR").getAsString());
                    pstmt.setString(13, temp.get("X_SWIFI_REMARS3").getAsString());
                    pstmt.setDouble(14, temp.get("LAT").getAsDouble());
                    pstmt.setDouble(15, temp.get("LNT").getAsDouble());
                    pstmt.setString(16, temp.get("WORK_DTTM").getAsString());

                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            return false;
        } finally {
            disconnect();
        }
        return true;
    }

    // 특정 위치에서 가까운 와이파이 목록 조회
    public ArrayList<WifiDTO> getWifiList(Pos pos) {
        conn = DBConnection.DBConnect();
        ArrayList<WifiDTO> list = new ArrayList<>();

        saveHistory(pos); // 조회 시 위치 정보를 히스토리에 저장

        // SQL 쿼리: 가까운 순서로 거리 계산
        String sql = "SELECT *, " +
                "ROUND((6371 * acos(cos(radians(?)) * cos(radians(lat)) * cos(radians(lnt) - radians(?)) " +
                "+ sin(radians(?)) * sin(radians(lat)))), 4) AS distance " +
                "FROM WIFI " +
                "ORDER BY distance " +
                "LIMIT 20;";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, pos.lat); // 사용자의 위도 값
            pstmt.setDouble(2, pos.lnt); // 사용자의 경도 값
            pstmt.setDouble(3, pos.lat); // 사용자의 위도 값
            rs = pstmt.executeQuery();

            while (rs.next()) {
                WifiDTO wifi = new WifiDTO();
                wifi.setDistance(rs.getDouble("distance"));
                wifi.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
                wifi.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
                wifi.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
                wifi.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
                wifi.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
                wifi.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
                wifi.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
                wifi.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
                wifi.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
                wifi.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
                wifi.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
                wifi.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
                wifi.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
                wifi.setLAT(rs.getDouble("LAT"));
                wifi.setLNT(rs.getDouble("LNT"));
                wifi.setWORK_DTTM(rs.getString("WORK_DTTM"));

                list.add(wifi);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }

        return list;
    }

    // 와이파이 정보 조회
    public WifiDTO getWifi(String id) {
        conn = DBConnection.DBConnect();
        WifiDTO wifi = null;
        String sql = "SELECT * FROM WIFI WHERE X_SWIFI_MGR_NO = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                wifi = new WifiDTO();
                wifi.setX_SWIFI_MGR_NO(rs.getString("X_SWIFI_MGR_NO"));
                wifi.setX_SWIFI_WRDOFC(rs.getString("X_SWIFI_WRDOFC"));
                wifi.setX_SWIFI_MAIN_NM(rs.getString("X_SWIFI_MAIN_NM"));
                wifi.setX_SWIFI_ADRES1(rs.getString("X_SWIFI_ADRES1"));
                wifi.setX_SWIFI_ADRES2(rs.getString("X_SWIFI_ADRES2"));
                wifi.setX_SWIFI_INSTL_FLOOR(rs.getString("X_SWIFI_INSTL_FLOOR"));
                wifi.setX_SWIFI_INSTL_TY(rs.getString("X_SWIFI_INSTL_TY"));
                wifi.setX_SWIFI_INSTL_MBY(rs.getString("X_SWIFI_INSTL_MBY"));
                wifi.setX_SWIFI_SVC_SE(rs.getString("X_SWIFI_SVC_SE"));
                wifi.setX_SWIFI_CMCWR(rs.getString("X_SWIFI_CMCWR"));
                wifi.setX_SWIFI_CNSTC_YEAR(rs.getString("X_SWIFI_CNSTC_YEAR"));
                wifi.setX_SWIFI_INOUT_DOOR(rs.getString("X_SWIFI_INOUT_DOOR"));
                wifi.setX_SWIFI_REMARS3(rs.getString("X_SWIFI_REMARS3"));
                wifi.setLAT(rs.getDouble("LAT"));
                wifi.setLNT(rs.getDouble("LNT"));
                wifi.setWORK_DTTM(rs.getString("WORK_DTTM"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }

        return wifi;
    }

    // 위치 정보를 히스토리에 저장
    public boolean saveHistory(Pos pos) {
        conn = DBConnection.DBConnect();
        String sql = "INSERT INTO POSITION_HISTORY (LNT, LAT, SEARCH_DATE) VALUES (?, ?, ?)";
        String formattedDateTime = formatLocalDateTime();

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, pos.lnt);
            pstmt.setDouble(2, pos.lat);
            pstmt.setString(3, formattedDateTime);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 위치 히스토리 목록 조회
    public ArrayList<PositionHistory> getHistory() {
        conn = DBConnection.DBConnect();
        ArrayList<PositionHistory> list = new ArrayList<>();
        String sql = "SELECT * FROM POSITION_HISTORY ORDER BY HISTORY_ID DESC";

        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PositionHistory history = new PositionHistory();
                history.setHistoryId(rs.getInt("history_id"));
                history.setLnt(rs.getDouble("lnt"));
                history.setLat(rs.getDouble("lat"));
                history.setSearchDate(parseLocalDateTime(rs.getString("search_date")));
                list.add(history);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // 위치 히스토리 삭제
    public boolean deleteHistory(int id) {
        conn = DBConnection.DBConnect();
        String sql = "DELETE FROM POSITION_HISTORY WHERE HISTORY_ID = ?";

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

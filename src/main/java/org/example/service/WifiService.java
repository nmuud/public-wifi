package org.example.service;

import org.example.Pos;
import org.example.dto.WifiDTO;
import org.example.entity.PositionHistory;
import org.example.repository.WifiRepository;

import java.util.ArrayList;

public class WifiService {
    WifiRepository wifiRepository = new WifiRepository();

    /**
     * 와이파이 데이터 저장
     *
     * @return 저장 성공 여부
     */
    public boolean saveWifiData() {
        return wifiRepository.saveWifiData();
    }

    /**
     * 특정 위치에서 가까운 와이파이 목록 조회
     *
     * @param pos 현재 위치
     * @return 가장 가까운 와이파이 목록
     */
    public ArrayList<WifiDTO> getWifiList(Pos pos) {
        return wifiRepository.getWifiList(pos);
    }

    /**
     * 와이파이 정보 조회
     *
     * @param id 와이파이 ID
     * @return 와이파이 정보
     */
    public WifiDTO getWifi(String id) {
        return wifiRepository.getWifi(id);
    }

    /**
     * 위치 히스토리 목록 조회
     *
     * @return 위치 히스토리 목록
     */
    public ArrayList<PositionHistory> getHistory() {
        return wifiRepository.getHistory();
    }

    /**
     * 위치 히스토리 삭제
     *
     * @param id 히스토리 ID
     * @return 삭제 성공 여부
     */
    public boolean deleteHistory(int id) {
        return wifiRepository.deleteHistory(id);
    }
}

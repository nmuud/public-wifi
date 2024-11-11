package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PositionHistory {
    private int historyId; // 히스토리 번호
    private double lat; // Y좌표(위도)
    private double lnt; // X좌표(경도)
    private LocalDateTime searchDate; // 조회 일자
}

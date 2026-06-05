package org.yourcompany.yourproject.dto.request;

import lombok.Data;

@Data
public class UpdatePtCountReqDto {
    private String memberId;    // PT 횟수를 변경할 회원 ID
    private int ptCount;        // 새로 변경될 잔여 PT 횟수 값
}
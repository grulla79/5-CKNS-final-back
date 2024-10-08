package com.example.travelday.global.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // 인스턴스화 방지
public class ResponseText {

    // common
    public static final String OK = "OK";
    public static final String DUPLICATE = "DUPLICATE";
    public static final String BE_IN_USE = "BE IN USE";

    // wish
    public static final String SUCCESS_ADD_WISH = "위시 추가 성공";
    public static final String SUCCESS_DELETE_WISH = "위시 삭제 성공";

    // travelroom
    public static final String SUCCESS_CREATE_TRAVELROOM = "여행방 생성 성공";
    public static final String SUCCESS_UPDATE_TRAVELROOM = "여행방 수정 성공";
    public static final String SUCCESS_DELETE_TRAVELROOM = "여행 일정 목록 삭제 성공";

    // member
    public static final String SUCCESS_LOGOUT = "로그아웃 성공";
    public static final String SUCCESS_UPDATE_NICKNAME = "닉네임 수정 성공";

    // travelplan
    public static final String SUCCESS_CREATE_TRAVELPLAN = "여행 일정 생성 성공";
    public static final String SUCCESS_UPDATE_TRAVELPLAN = "여행 일정 갱신 성공";
    public static final String SUCCESS_CREATE_TRAVELPLANLIST = "여행 일정 목록 생성 성공";
    public static final String SUCCESS_DELETE_TRAVELPLAN = "여행일정 삭제 성공";
}

package com.example.travelday.domain.travelroom.service;

import com.example.travelday.domain.auth.entity.Member;
import com.example.travelday.domain.auth.repository.MemberRepository;
import com.example.travelday.domain.travelroom.dto.request.TravelRoomReqDto;
import com.example.travelday.domain.travelroom.dto.response.TravelRoomMembersResDto;
import com.example.travelday.domain.travelroom.dto.response.TravelRoomResDto;
import com.example.travelday.domain.travelroom.entity.TravelRoom;
import com.example.travelday.domain.travelroom.entity.UserTravelRoom;
import com.example.travelday.domain.travelroom.repository.TravelRoomRepository;
import com.example.travelday.domain.travelroom.repository.UserTravelRoomRepository;
import com.example.travelday.global.exception.CustomException;
import com.example.travelday.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TravelRoomService {

    private final TravelRoomRepository travelRoomRepository;

    private final UserTravelRoomRepository userTravelRoomRepository;

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<TravelRoomMembersResDto> getAllTravelRoom(String userId) {
        Member member = memberRepository.findByUserId(userId)
                            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        List<UserTravelRoom> userTravelRooms = userTravelRoomRepository.findByMember(member)
                                                    .orElseThrow(() -> new CustomException(ErrorCode.USER_DOES_NOT_JOIN_TRAVEL_ROOM));

        return userTravelRooms
                    .stream()
                    .map(userTravelRoom -> {
                            TravelRoom travelRoom = userTravelRoom.getTravelRoom();

                            List<UserTravelRoom> userTravelRoomInTravelRoom = userTravelRoomRepository.findByTravelRoom(travelRoom);

                            List<String> memberUserNames = userTravelRoomInTravelRoom.stream()
                                    .map(utr -> utr.getMember().getNickname())
                                    .collect(Collectors.toList());

                            int memberCount = userTravelRoomInTravelRoom.size();
                            return TravelRoomMembersResDto.fromEntity(travelRoom, memberCount, memberUserNames);
                    })
                    .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TravelRoomResDto getTravelRoomById(Long travelRoomId, String userId) {
        Member member = memberRepository.findByUserId(userId)
                            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        UserTravelRoom userTravelRoom = userTravelRoomRepository.findByMemberAndTravelRoomId(member, travelRoomId)
                                            .orElseThrow(() -> new CustomException(ErrorCode.USER_DOES_NOT_JOIN_TRAVEL_ROOM));

        TravelRoom travelRoom = userTravelRoom.getTravelRoom();

        return TravelRoomResDto.fromEntity(travelRoom);
    }

    @Transactional
    public void createTravelRoom(TravelRoomReqDto requestDto, String userId) {
        TravelRoom travelRoom = TravelRoom.addOf(requestDto);
        TravelRoom savedTravelRoom = travelRoomRepository.save(travelRoom);

        Member member = memberRepository.findByUserId(userId)
                            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        UserTravelRoom userTravelRoom = UserTravelRoom.create(savedTravelRoom, member);
        userTravelRoomRepository.save(userTravelRoom);
    }

    @Transactional
    public void updateTravelRoom (Long travelRoomId, TravelRoomReqDto requestDto, String userId) {
        Member member = memberRepository.findByUserId(userId)
                            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        UserTravelRoom userTravelRoom = userTravelRoomRepository.findByMemberAndTravelRoomId(member, travelRoomId)
                                            .orElseThrow(() -> new CustomException(ErrorCode.TRAVEL_ROOM_NOT_FOUND));

        TravelRoom travelRoom = userTravelRoom.getTravelRoom();

        travelRoom.update(requestDto);
        travelRoomRepository.save(travelRoom);
    }

    @Transactional
    public void deleteTravelRoom(Long travelRoomId, String userId) {
        Member member = memberRepository.findByUserId(userId)
                            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        UserTravelRoom userTravelRoom = userTravelRoomRepository.findByMemberAndTravelRoomId(member, travelRoomId)
                                            .orElseThrow(() -> new CustomException(ErrorCode.TRAVEL_ROOM_NOT_FOUND));

        TravelRoom travelRoom = userTravelRoom.getTravelRoom();

        travelRoomRepository.delete(travelRoom);
    }
}
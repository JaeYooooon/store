package com.zerobase.store.domain.reserve.service;

import com.zerobase.store.domain.reserve.dto.ReserveDTO;
import com.zerobase.store.domain.reserve.entity.Reserve;
import com.zerobase.store.domain.reserve.entity.status.ReserveStatus;
import com.zerobase.store.domain.reserve.repository.ReserveRepository;
import com.zerobase.store.domain.shop.entity.Shop;
import com.zerobase.store.domain.shop.repository.ShopRepository;
import com.zerobase.store.domain.user.entity.User;
import com.zerobase.store.domain.user.repository.UserRepository;
import com.zerobase.store.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.zerobase.store.domain.reserve.entity.status.CheckStatus.CHECKED_IN;
import static com.zerobase.store.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReserveService {
    private final ReserveRepository reserveRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createReserve(ReserveDTO reserveDTO, Principal principal){
        Shop shop = shopRepository.findById(reserveDTO.getShopId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_SHOP));

        User user = userRepository.findByUserName(principal.getName())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));


        // 지금보다 이전의 시간을 예약하려하는경우
        if (reserveDTO.getReservedTime().isBefore(LocalDateTime.now())){
            throw new CustomException(INVALID_RESERVE_TIME);
        }

        Reserve reserve = new Reserve();
        reserve.setShop(shop);
        reserve.setUser(user);
        reserve.setReservedTime(reserveDTO.getReservedTime());
        reserve.setStatus(reserve.getStatus());

        reserveRepository.save(reserve);
    }

    @Transactional
    public void cancelReserve(Long reserveId, Principal principal){
        String userName = principal.getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        Reserve reserve = reserveRepository.findById(reserveId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVE));

        // 로그인한 유저와 예약을 한 유저가 다르면
        if(!user.getUsername().equals(reserve.getUser().getUsername())){
            throw new CustomException(NO_PERMISSION);
        }

        reserveRepository.delete(reserve);
    }

    @Transactional
    public void updateReserve(Long reserveId, LocalDateTime time, Principal principal){
        String userName = principal.getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        Reserve reserve = reserveRepository.findById(reserveId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVE));

        // 로그인한 유저와 예약을 한 유저가 다르면
        if(!user.getUsername().equals(reserve.getUser().getUsername())){
            throw new CustomException(NO_PERMISSION);
        }
        // 현재 시간보다 이전시간으로 설정하면
        if(time.isBefore(LocalDateTime.now())){
            throw new CustomException(INVALID_RESERVE_TIME);
        }

        reserve.setReservedTime(time);
        reserveRepository.save(reserve);
    }

    // 본인 예약 내역
    public List<ReserveDTO> getReserveListByUser(Principal principal) {
        User user = userRepository.findByUserName(principal.getName())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        List<Reserve> reserveList = reserveRepository.findByUser(user);

        return reserveList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 예약 10분전
    @Transactional
    public void checkIn(Long reserveId, Principal principal) {
        String userName = principal.getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        Reserve reserve = reserveRepository.findById(reserveId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVE));
        // 로그인한 유저와 예약을 한 유저가 다르면
        if (!user.getUsername().equals(reserve.getUser().getUsername())) {
            throw new CustomException(NO_PERMISSION);
        }
        // 예약이 승인된 상태가 아니라면
        if(!reserve.getStatus().equals(ReserveStatus.APPROVED)){
            throw new CustomException(CHECK_RESERVE);
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationTime = reserve.getReservedTime();
        LocalDateTime checkInTime = reservationTime.minusMinutes(10);

        // 10분보다 더 빨리 체크인을 하려하면
        // 필요없는 예외처리인지
        if (now.isBefore(checkInTime)) {
            throw new CustomException(EARLY_CHECK_IN);
        }

        // 예약시간보다 늦게 체크인 하려하면
        if (now.isAfter(reservationTime)) {
            throw new CustomException(LATE_CHECK_IN);
        }

        reserve.setCheckStatus(CHECKED_IN);
        reserveRepository.save(reserve);
    }



    // 파트너
    @Transactional
    public void updateReservationStatus(Long reserveId, ReserveStatus status, Principal principal){
        Reserve reserve = reserveRepository.findById(reserveId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVE));
        String userName = principal.getName();
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        Shop shop = reserve.getShop();

        // 파트너 권한이 없다면
        if(!user.getRoles().contains("PARTNER")){
            throw new CustomException(ONLY_PARTNER);
        }

        // 내가 등록한 상점의 예약건만 상태 변경 가능
        if(!userName.equals(shop.getUser().getUsername())){
            throw new CustomException(NO_PERMISSION);
        }


        reserve.setStatus(status);
        reserveRepository.save(reserve);
    }

    public List<ReserveDTO> getReserveList(Long shopId, Principal principal){

        String userName = principal.getName();

        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_SHOP));

        // 파트너 권한이 없다면
        if(!user.getRoles().contains("PARTNER")){
            throw new CustomException(ONLY_PARTNER);
        }

        // 내가 등록한 상점의 예약 목록만 조회 가능
        if(!userName.equals(shop.getUser().getUsername())){
            throw new CustomException(NO_PERMISSION);
        }

        List<Reserve> reservedBookings = reserveRepository.findByShop(shop);

        return reservedBookings.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private ReserveDTO convertToDTO(Reserve reserve) {
        ReserveDTO reserveDTO = new ReserveDTO();
        reserveDTO.setId(reserve.getId());
        reserveDTO.setShopId(reserve.getShop().getId());
        reserveDTO.setShopName(reserve.getShop().getName());
        reserveDTO.setUserId(reserve.getUser().getId());
        reserveDTO.setReservedTime(reserve.getReservedTime());
        reserveDTO.setStatus(reserve.getStatus());
        reserveDTO.setCheckStatus(reserve.getCheckStatus());
        return reserveDTO;
    }
}

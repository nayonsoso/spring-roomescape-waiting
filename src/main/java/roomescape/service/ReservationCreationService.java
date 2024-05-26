package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.*;
import roomescape.dto.request.AdminReservationRequest;
import roomescape.dto.request.MemberReservationRequest;
import roomescape.dto.request.ReservationCreationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.service.exception.OperationNotAllowedCustomException;
import roomescape.service.exception.ResourceNotFoundCustomException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ReservationCreationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final ThemeRepository themeRepository;
    private final MemberRepository memberRepository;

    public ReservationCreationService(
            ReservationRepository reservationRepository,
            ReservationTimeRepository reservationTimeRepository,
            ThemeRepository themeRepository,
            MemberRepository memberRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.themeRepository = themeRepository;
        this.memberRepository = memberRepository;
    }

    public ReservationResponse addReservationByAdmin(AdminReservationRequest request) {
        Member member = findValidatedMember(request.memberId());

        return addReservationForMember(request, member);
    }

    public ReservationResponse addReservationByCustomer(MemberReservationRequest request, Member member) {
        return addReservationForMember(request, member);
    }

    private ReservationResponse addReservationForMember(ReservationCreationRequest request, Member member) {
        ReservationTime reservationTime = findValidatedReservationTime(request.getTimeId());
        validateNotPast(request.getDate(), reservationTime.getStartAt());
        Theme theme = findValidatedTheme(request.getThemeId());
        ReservationStatus reservationStatus = determineReservationStatus(request.getDate(), request.getTimeId(), request.getThemeId());

        Reservation reservation = Reservation.builder()
                .member(member)
                .date(request.getDate())
                .reservationTime(reservationTime)
                .theme(theme)
                .reservationStatus(reservationStatus)
                .build();
        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationResponse.from(savedReservation);
    }

    private void validateNotPast(LocalDate date, LocalTime time) {
        LocalDateTime reservationDateTime = date.atTime(time);
        if (reservationDateTime.isBefore(LocalDateTime.now())) {
            throw new OperationNotAllowedCustomException("지나간 시간에 대한 예약은 할 수 없습니다.");
        }
    }

    private ReservationTime findValidatedReservationTime(Long id) {
        return reservationTimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundCustomException("아이디에 해당하는 예약 시간을 찾을 수 없습니다."));
    }

    private Theme findValidatedTheme(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundCustomException("아이디에 해당하는 테마를 찾을 수 없습니다."));
    }

    private Member findValidatedMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundCustomException("아이디에 해당하는 회원을 찾을 수 없습니다."));
    }

    private ReservationStatus determineReservationStatus(LocalDate date, Long timeId, Long themeId) {
        if (reservationRepository.existsByDateAndReservationTimeIdAndThemeId(date, timeId, themeId)) {
            return ReservationStatus.WAITING;
        }

        return ReservationStatus.RESERVED;
    }
}
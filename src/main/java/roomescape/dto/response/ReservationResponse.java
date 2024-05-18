package roomescape.dto.response;

import roomescape.domain.Reservation;

import java.time.LocalDate;

public record ReservationResponse(Long id, MemberPreviewResponse member, LocalDate date, ReservationTimeResponse time,
                                  ThemeResponse theme) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                MemberPreviewResponse.from(reservation.getMember()),
                reservation.getDate(),
                ReservationTimeResponse.from(reservation.getReservationTime()),
                ThemeResponse.from(reservation.getTheme())
        );
    }
}
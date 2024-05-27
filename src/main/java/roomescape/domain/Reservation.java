package roomescape.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReservationTime reservationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private Theme theme;

    protected Reservation() {
    }

    @Builder
    public Reservation(Member member, LocalDate date, ReservationTime reservationTime, Theme theme, ReservationStatus reservationStatus) {
        this(null, member, date, reservationTime, theme, reservationStatus);
    }

    public Reservation(Long id, Member member, LocalDate date, ReservationTime reservationTime, Theme theme, ReservationStatus reservationStatus) {
        this.id = id;
        this.member = member;
        this.date = date;
        this.reservationTime = reservationTime;
        this.theme = theme;
        this.reservationStatus = reservationStatus;
    }

    public void setStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

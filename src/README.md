# 🗝️방탈출 예약 관리 기능 명세서🗝️

---

## 사용자 페이지

### 메인 페이지

- `/`으로 접속할 수 있다.
- 테마 순위를 볼 수 있다.
  - 최근 일주일을 기준으로 예약이 많은 10개 테마를 볼 수 있다.
  - 최근 일주일은 오늘을 포함하지 않는다.
  - e.g. 오늘이 4월 8일인 경우, 4월 1일부터 4월 7일까지의 예약만 반영

### 예약 페이지

- `/reservation`으로 접속할 수 있다.
- 날짜, 테마, 시간을 선택해서 예약할 수 있다.
  - 날짜와 테마를 선택하면 예약 가능한 시간을 확인할 수 있다.

### 로그인 페이지

- `/login`으로 접속할 수 있다.
- 아이디와 비밀번호를 입력하고 제출하면 로그인을 할 수 있다.

### 예약 목록 조회 페이지

- `/reservation-mine`으로 접속할 수 있다.
- 예약 목록을 확인할 수 있다.

<br>

## 관리자 페이지

- 관리자로 로그인한 사람만 접속할 수 있다.

### 메인 페이지

- `/admin` 으로 접속할 수 있다.
- 관리자 페이지를 볼 수 있다.
- 네비게이션 바의 Reservation을 누르면 관리자 예약 페이지로 이동한다.

### 예약 관리 페이지

- `/admin/reservation` 으로 접속할 수 있다.
- 예약 목록을 볼 수 있다.
  - 예약 번호, 이름, 날짜, 시간을 볼 수 있다.
- 예약을 추가할 수 있다.
  - 이름, 날짜를 입력하고, 시간을 선택하여 추가한다.
  - 이름, 날짜, 시간에 빈 값을 입력할 수 없다.
  - 지나간 날짜와 시간에 대한 예약을 추가할 수 없다.
  - 이미 존재하는 예약과 같은 시간에 예약을 추가할 수 없다.
- 예약을 필터링해 검색할 수 있다.
  - 사용자, 테마, 시작 날짜, 끝 날짜를 기준으로 검색할 수 있다.

### 예약 시간 관리 페이지

- `/admin/time` 으로 접속할 수 있다.
- 예약 시간 목록을 볼 수 있다.
- 예약 시간을 추가할 수 있다.
  - 시간을 입력하여 추가한다.
  - 시간에 빈 값을 입력할 수 없다.
- 예약 시간을 삭제할 수 있다.
  - 예약 시간에 예약한 사람이 한명이라도 있는 경우, 삭제하지 못한다.

### 테마 관리 페이지

- `/admin/theme` 으로 접속할 수 있다.
- 테마 목록을 볼 수 있다.
- 테마를 추가할 수 있다.
  - 이름, 설명, 이미지를 입력하여 추가한다.
  - 이름, 설명, 이미지에 빈 값을 입력할 수 없다.
- 테마를 삭제할 수 있다.
  - 테마에 예약한 사람이 한명이라도 있는 경우, 삭제하지 못한다.

## API 명세서

### 페이지

#### 관리자

| HTTP Method | URI                  | Description  |
|-------------|----------------------|--------------|
| GET         | `/admin`             | 관리자 메인 페이지   | 
| GET         | `/admin/reservation` | 예약 관리 페이지    |
| GET         | `/admin/time`        | 예약 시간 관리 페이지 |
| GET         | `/admin/theme`       | 테마 관리 페이지    |

#### 그 외

| HTTP Method | URI                 | Description  |
|-------------|---------------------|--------------|
| GET         | `/`                 | 사용자 메인 페이지   |
| GET         | `/reservation`      | 예약 페이지       |
| GET         | `/login`            | 로그인 페이지      |
| GET         | `/reservation-mine` | 예약 목록 조회 페이지 |

### API

#### 관리자

| HTTP Method | URI                          | Description    |
|-------------|------------------------------|----------------|
| GET         | `/admin/reservations`        | 예약 목록 조회       |
| POST        | `/admin/reservations`        | 관리자 예약 추가      |
| DELETE      | `/admin/reservations/{id}`   | 예약 삭제          |
| GET         | `/admin/reservations/filter` | 예약 목록 필터링      |
| POST        | `/admin/times`               | 예약 시간 추가       |
| DELETE      | `/admin/times/{id}`          | 예약 시간 삭제       |
| POST        | `/admin/themes`              | 테마 추가          |
| DELETE      | `/admin/themes/{id}`         | 테마 삭제          |
| GET         | `/admin/members`             | 간소화된 사용자 목록 조회 |

#### 그 외

| HTTP Method | URI                     | Description       |
|-------------|-------------------------|-------------------|
| GET         | `/reservations`          | 사용자가 예약한 예약 목록 조회 |
| POST        | `/reservations`         | 예약 추가             |
| GET         | `/times`                | 예약 시간 목록 조회       |
| GET         | `/themes`               | 테마 목록 조회          |
| POST        | `/login`                | 로그인 요청            |
| GET         | `/login/check`          | 로그인 정보 조회         |
| POST        | `/logout`               | 로그아웃 요청           |

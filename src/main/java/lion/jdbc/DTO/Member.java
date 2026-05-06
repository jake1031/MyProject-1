package lion.jdbc.DTO;

import java.time.LocalDateTime;

public class Member {
    private Long memberId;      // PK
    private String loginId;     // 로그인 아이디
    private String password;    // 비밀번호
    private String name;        // 이름
    private LocalDateTime createdAt;

    // 기본 생성자 : 안씀
    public Member() {}

    // 회원 생성용으로 쓰는 생성자
    public Member(String loginId, String password, String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }

    // update용으로 쓰는 생성자(memberId 필요)
    public Member(Long memberId, String loginId, String password, String name) {
        this.memberId = memberId;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }




    public Long getMemberId() { return memberId; }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
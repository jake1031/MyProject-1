package lion.jdbc.common;

import lion.jdbc.DTO.Member;

public class Session {
    public Member loginUser = null;

    public Session() {}

    public void login(Member member) {
        loginUser = member;
        System.out.println(member.getName() + "님, 반갑습니다!");
    }

    public void logout() {
        this.loginUser = null;
        System.out.println("정상적으로 로그아웃 되었습니다.");
    }

    // 로그인 여부 확인용
    public boolean isLogin() {
        return loginUser != null;
    }
}

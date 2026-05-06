package lion.jdbc.Service;

import lion.jdbc.DAO.MemberDAO;
import lion.jdbc.DTO.Member;
import lion.jdbc.common.DBUtil;
import lion.jdbc.common.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MemberService {
    private final Scanner sc; // 재사용
    private final Session session;
    private final MemberDAO memberDAO = new MemberDAO();

    public MemberService(Scanner sc, Session session) {
        this.sc = sc;
        this.session = session;
    }

    void insertMember() {
        try {
            System.out.println("\n--- 판매자 신규 등록 ---");
            String id = inputString("아이디");

            if (id.trim().isEmpty()) {
                System.out.println("오류: 아이디는 필수 입력 사항입니다.");
                return;
            }

            String pw = inputString("비밀번호: ");
            String name = inputString("이름");

            Member member = new Member(id, pw, name);
            long member_id = memberDAO.insert(member);
            if (member_id > 0) {
                System.out.println("판매자 등록이 완료되었습니다.");
                System.out.println("해당 판매자의 고유 아이디는 " + member_id + "입니다.");
            } else {
                System.out.println("판매자 등록이 실패했습니다..");
            }
        } catch (Exception e) {
            System.out.println("등록 실패: 이미 존재하는 아이디거나 DB 통신에 문제가 있습니다.");
        }
    }

    void updateMember() {
        try {
            String loginId = inputString("업데이트하려는 회원의 로그인 아이디를 입력해주십시오");
            Member member = memberDAO.getMember(loginId);
            if (member != null) {
                String password = inputString("변경하려는 아이디의 비밀번호를 입력해주세요");
                String name = inputString("변경하려는 회원의 이름을 입력해주세요");

                member.setPassword(password);
                member.setName(name);

                if (memberDAO.update(member)) {
                    System.out.println("회원 정보의 업데이트가 완료되었습니다.");
                } else {
                    System.out.println("회원 정보의 업데이트가 실패했습니다..");
                }
            } else {
                System.out.println("찾으시는 회원 아이디는 존재하지 않습니다..");
            }
        } catch (Exception e) {
            System.out.println("회원 정보의 업데이트가 실패했습니다..");
        }
    }

    void deleteMember() {
        try {
            String loginId = inputString("삭제하려는 회원의 아이디를 입력해주십시오");
            if (memberDAO.delete(loginId)) {
                System.out.println(loginId + " 사용자 삭제 완료!");
            } else {
                System.out.println("삭제 실패: 존재하지 않는 ID입니다.");
            }
        } catch (Exception e) {
            System.out.println("삭제 실패: 등록된 상품이 있는 판매자입니다.");
        }
    }

    // 로그인 아이디로 고유번호 찾기
    public Long getMemberIdByLoginId(String loginId) {
        try {
            Member member = memberDAO.getMember(loginId);
            return (member != null) ? member.getMemberId() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public void login() {
        if (session.isLogin()) {
            System.out.println("이미 " + session.loginUser.getName() + "님으로 로그인되어 있습니다.");
            return;
        }

        String loginId = inputString("아이디");
        String password = inputString("비밀번호");
        String sql = "SELECT * FROM member WHERE login_id = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loginId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Member member = new Member(
                        rs.getLong("member_id"),
                        rs.getString("login_id"),
                        rs.getString("password"),
                        rs.getString("name"));

                session.login(member);
            } else {
                System.out.println("해당하는 사용자가 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            System.out.println("❌ DB 연결 중 오류가 발생했습니다.");
        } catch (Exception e) {
            System.out.println("❌ 알 수 없는 시스템 오류가 발생했습니다.");
        }
    }

    private String inputString(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine();
    }
}

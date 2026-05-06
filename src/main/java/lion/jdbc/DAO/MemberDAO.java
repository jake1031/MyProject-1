package lion.jdbc.DAO;

import lion.jdbc.DTO.Member;
import lion.jdbc.common.DBUtil;

import java.sql.*;

public class MemberDAO implements DAO<Member, String> {

    @Override
    public Long insert(Member member) throws Exception {
        String sql = "insert into member (login_id, password, name) values(?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, member.getLoginId());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());

            int count = ps.executeUpdate();


            if (count > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getLong(1); // DB가 방금 생성한 PK값을 바로 반환
                    }
                }
            }
        }
        return 0L;
    }

    public Member getMember(String loginId) throws Exception {
        String sql = "select * from member where login_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loginId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Member(
                        rs.getLong("member_id"), // PK
                        rs.getString("login_id"),  // 로그인 아이디
                        rs.getString("password"),  // 비밀번호
                        rs.getString("name")       // 이름
                );
            }
            return null;
        }
    }

    @Override
    public boolean update(Member member) throws Exception {
        String sql = "update member set password=?, name=? where member_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getPassword());
            ps.setString(2, member.getName());
            ps.setLong(3, member.getMemberId());

            int count = ps.executeUpdate();
            return count > 0;
        }
    }

    @Override
    public boolean delete(String loginId) throws Exception {
        String sql = "delete from member where login_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, loginId);
            int count = ps.executeUpdate();
            return count > 0;
        }
    }
}

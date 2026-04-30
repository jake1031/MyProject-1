package lion.jdbc.DAO;

import lion.jdbc.DTO.Member;
import lion.jdbc.common.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {
    public long insertMember(Member member) throws Exception {
        String sql = "insert into member (login_id, password, name) values(?,?,?)";

        try (Connection conn = DBUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, member.getLoginId());
            ps.setString(2, member.getPassword());
            ps.setString(3, member.getName());

            int count = ps.executeUpdate();


            if (count > 0) {
                sql = "select member_id from member where login_id = ?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, member.getLoginId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getLong(1);
                } else {
                    System.out.println("사용자가 제대로 등록되지 않았습니다..");
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public boolean updateMember(Member member) throws Exception {
        String sql = "update member set login_id=?, name=? where id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getLoginId());
            ps.setString(2, member.getName());
            ps.setString(3, member.getMemberId());

            int count = ps.executeUpdate();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteMember(Long memberId) throws Exception {
        String sql = "delete from member where member_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(memberId));
            int count = ps.executeUpdate();
            return count > 0;
        } catch (Exception e) {
            System.out.println("해당 고유번호의 사용자가 없거나 판매자로 등록된 상품이 있습니다.");
        }
        return false;
    }
}

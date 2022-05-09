package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TopicDAO {
	private JdbcTemplate jdbcTemplate;
	private int numberOfTopic=0;
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	public TopicDAO() {
		jdbcTemplate = JdbcTemplate.getInstance();
	}
	
	/*주제 목록 보기*/
	public List<TopicVO> selectAll(){

		List<TopicVO> ls = new ArrayList<>();
		ResultSet rs = null;
		String sql = "SELECT * FROM TOPIC ORDER BY NUM ASC";
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); //쿼리 전송!
			
			while(rs.next()) {
				TopicVO tmp = new TopicVO(
					rs.getInt(1),
					rs.getNString(2));
				ls.add(tmp);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return (ls.size() == 0) ? null : ls;
	}
	
	/*주제 만들기*/
	public boolean createTopic(String topic) {
		String sql = "INSERT INTO TOPIC VALUES(\"S_TOPIC\".NEXTVAL,?)";
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, topic);
			pstmt.executeQuery(); 
			numberOfTopic++;
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/* Item 테이블에서 참조키 도메인을위한 정수형 */
	public int getNOT() {
		return this.numberOfTopic;
	}
}


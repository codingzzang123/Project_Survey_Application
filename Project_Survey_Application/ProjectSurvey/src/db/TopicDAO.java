



package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TopicDAO {
	private JdbcTemplate jdbcTemplate;
	private int numberOfTopic;
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	public TopicDAO() {
		jdbcTemplate = JdbcTemplate.getInstance();
		this.numberOfTopic=setNumberOfTopic();
	}
	
	/* numberOfTopic �ʱ�ȭ */
	private int setNumberOfTopic() {
		String sql="SELECT COUNT(*) FROM TOPIC";
		ResultSet rs;
		int re=1;
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery(); 
			while(rs.next()) {
				re = rs.getInt(1);
			}
			return re;
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
		return re;
	}
	
	/*���� ��� ����*/
	public List<TopicVO> selectAll(){

		List<TopicVO> ls = new ArrayList<>();
		ResultSet rs = null;
		String sql = "SELECT * FROM TOPIC ORDER BY NUM ASC";
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); //���� ����!
			
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
	
	/*���� �����*/
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

	/* Item ���̺��� ����Ű ������������ ������ */
	public int getNOT() {
		return this.numberOfTopic;
	}
}


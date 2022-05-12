package Database;

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
	
	/* numberOfTopic 초기화 */
	private int setNumberOfTopic() {
		String sql="SELECT COUNT(*) FROM TOPIC";
		ResultSet rs=null;
		int result=1;
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs=pstmt.executeQuery(); 
			while(rs.next()) {
				result = rs.getInt(1);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcTemplate.close(pstmt);
			jdbcTemplate.close(conn);
			jdbcTemplate.close(rs);
		}
		return result;
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
			jdbcTemplate.close(pstmt);
			jdbcTemplate.close(conn);
			jdbcTemplate.close(rs);
		}
		return (ls.size() == 0) ? null : ls;
	}
	
	/* 주제 만들기 (create) */
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
			jdbcTemplate.close(pstmt);
			jdbcTemplate.close(conn);
		}
		return false;
	}

	/* Item 테이블에서 참조키 도메인을위한 정수형 */
	public int getNOT() {
		return this.numberOfTopic;
	}

	/* 초기화 함수 */
	public String[] initSql() {
		String[] sqls= {
				"DROP SEQUENCE \"S_ITEM\"","DROP SEQUENCE \"S_TOPIC\"",
				"TRUNCATE TABLE ITEM","TRUNCATE TABLE TOPIC",
				"CREATE SEQUENCE \"S_ITEM\" NOCACHE","CREATE SEQUENCE \"S_TOPIC\" NOCACHE","COMMIT"
		};
		return sqls;
	}
	public void initialization() {
		String[] sql=initSql();
		try {
			conn = jdbcTemplate.getConnection();
			for(int i=0; i<sql.length; i++) {
				pstmt = conn.prepareStatement(sql[i]);
				pstmt.executeQuery(); 
			}	
			this.numberOfTopic=0;
			System.out.println("초기화 완료!\n");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			jdbcTemplate.close(pstmt);
			jdbcTemplate.close(conn);
		}
	}

	/*토픽 제목 가져오기 */
	public String selectTopic(int option) {
		List<TopicVO> ls = selectAll();
		for(TopicVO tv : ls) {
			if(option == tv.getNum())
				return tv.getTopic();
		}
		return "";
	}

	/* 중복값 검사 */
	public boolean checkTopic(String topic) {
		List<TopicVO> ls = selectAll();
		if(ls==null)
			return false;
		for(TopicVO tv : ls) {
			if(tv.getTopic().equals(topic)) {
				return true;
			}
		}
		return false;
	}
}


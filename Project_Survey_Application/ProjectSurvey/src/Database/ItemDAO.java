package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAO {
	private JdbcTemplate jdbcTemplate;
	private int addOption;
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	public ItemDAO() {
		jdbcTemplate = JdbcTemplate.getInstance();
	}
	
	/* 목록보기*/
	public ArrayList<Object[]> selectItem(int option) {
		ArrayList<Object[]> tmp = new ArrayList<>();
		ResultSet rs = null;
		String sql = "SELECT \"ITEMS\",\"COUNT\" FROM ITEM WHERE TOPIC_NUM = ?";
	
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, option);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Object[] tmp2= new Object[3];
				tmp2[0] = rs.getString(1);
				tmp2[1] = rs.getInt(2);
				tmp.add(tmp2);
			}
			this.addOption = tmp.size();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			jdbcTemplate.close(pstmt);
			jdbcTemplate.close(conn);
			jdbcTemplate.close(rs);
		}
		return tmp.size()==0? null:tmp;
	}
	
	/* 주제에 대한 항목 생성 (insert) */
	public void createOption(int nums, String option ,int options,int count) {
		String sql = "INSERT INTO ITEM(\"NUM\",\"TOPIC_NUM\",\"ITEMS\",\"OPTION\",\"COUNT\") "
				+ "VALUES (\"S_ITEM\".NEXTVAL,?,?,?,?)";
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, nums);
			pstmt.setString(2, option); pstmt.setInt(3, options);
			pstmt.setInt(4, count);
			pstmt.executeQuery(); 
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			jdbcTemplate.close(pstmt);
			jdbcTemplate.close(conn);
		}
	}

	/* 주제 갯수 반환 */
	public int getAddOption() {
		return this.addOption;
	}

	
	/* "선택" 값 +1 증가(update) */
	public void choice(int num,int fk) {
		String sql="UPDATE ITEM SET \"COUNT\"=COUNT+1 WHERE \"TOPIC_NUM\"=? AND \"OPTION\"=?";
		
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fk);
			pstmt.setInt(2, num);
			pstmt.executeQuery();
				
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			jdbcTemplate.close(pstmt);
			jdbcTemplate.close(conn);
		}
	}
	
	/* "기타" 선택시 항목 추가(insert) */
	public void choiceOther(int topicNum, String item, int option) {
		String sql = "INSERT INTO ITEM VALUES(\"S_ITEM\".NEXTVAL, ? , ? , 1, ?)";
	
		try {
			conn = jdbcTemplate.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, topicNum);
			pstmt.setString(2, item); 
			pstmt.setInt(3, option);
			pstmt.executeQuery(); 
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			jdbcTemplate.close(pstmt);
			jdbcTemplate.close(conn);
		}
	}

	/* 주제에 대한 항목수 합계 반환 */
	public int totalcount(int num) {
		ArrayList<Object[]> tmp = selectItem(num);
		
		int sum=0;
		for(Object[] o : tmp) 
			sum+=(Integer)o[1];
		
		return sum;
	}
	
	/* 중복값 체크 */
	public boolean checkOption(String option, int input) {
		ArrayList<Object[]> ls = selectItem(input);
		if(ls==null)
			return false;
		for(Object[] tv : ls) {
			if(tv[0].equals(option)) {
				return true;
			}
		}
		return false;
	}
}
package spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/*
 * JdbcTemplate을 통해 DataSource, Connection, Statement,ResultSet을 직접 사용하지 않고
 * 편리하게 쿼리를 실행할 수 있다.
 * select쿼리 실행을 위한 query()메서드를 제공한다. sql 파라미터로 전달받은 쿼리를 실행하고
 * RowMapper를 이용해서 ResultSet의 한 행의 결과를 자바 객체로 변환한다.
 * sql파라미터가 인덱스 기반 파라미터 쿼리(?를 갖는)이면 args파라미터를 이용해 각 인덱스 파라미터값을 지정한다.
 */

public class MemberDao {
	
	private JdbcTemplate jdbcTemplate;
	
	/*
	 * DataSource를 주입받도록 생성자를 이용해 구현
	 */
	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Member selectByEmail(String email) {
		List<Member> results = jdbcTemplate.query(
				"select * from MEMBER where EMAIL = ?",
				new RowMapper<Member>() { //임의 클래스대신 람다를 이용할 수도 있음
					/*
					 * RowMapper는 ResultSet에서 데이터를 읽어와 Member객체로 변환해주는 기능을 제공하므로
					 * RowMapper의 타입 파라미터로 Member를 사용 -> mapRow에서 파라미터로 전달받은 ResultSet에서
					 * 데이터를 읽어와 Member 객체를 생성해 리턴하도록 구현
					 */
					@Override
					public Member mapRow(ResultSet rs, int rowNum) throws SQLException{
						Member member = new Member(
								rs.getString("EMAIL"),
								rs.getString("PASSWORD"),
								rs.getString("NAME"),
								rs.getTimestamp("REGDATE").toLocalDateTime());
						member.setId(rs.getLong("ID"));
						return member;
					}
				},
				email);
		return results.isEmpty() ? null : results.get(0);
	}
	
	public List<Member> selectAll(){
		List<Member> results = jdbcTemplate.query("select * from MEMBER", new RowMapper<Member>() {
			@Override
			public Member mapRow(ResultSet rs, int rowmNum) throws SQLException{
				Member member = new Member(
						rs.getString("EMAIL"),
						rs.getString("PASSWORD"),
						rs.getString("NAME"),
						rs.getTimestamp("REGDATE").toLocalDateTime());
				member.setId(rs.getLong("ID"));
				return member;
			}
		});
		return results;
	}
	/*
	 * count(*)결과가 1행뿐이니 쿼리 결과를 List보다 Integer로 받으면 편할 것이다.
	 * 이 때 queryForObject()를 사용한다.
	 * 두번째 파라미터는 칼럼을 읽어올때 사용할 타입을 지정한다.
	 * 결과 칼럼이 두 개 이상인 경우 rowMapper를 파라미터로 전달해서 위의 query()와 같이 사용할 수 있지만
	 * 차이점은 리턴 타입이 List가 아니라 RowMapper로 변환해주는 타입 이라는 것이다.(count()의 경우 Member)
	 */
	public int count() {
		Integer count = jdbcTemplate.queryForObject(
				"select count(*) from MEMBER", Integer.class);
		return count;
	}

	/*
	 * insert가 auto_increment로 실행되므로
	 * insert 후 생성된 키값을 알고 싶을 때 KeyHolder를 사용한다. 
	 * GeneratedKeyHolder는 자동 생성된 키값을 구해주는 KeyHolder구현 클래스
	 */
	public void insert(final Member member) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			/*
			 * PreparedStatementCreater 임의 클래스를 이용해서 PreparedStatement객체를 직접 생성.
			 * 두번째 파라미터 String[]는 자동 생성되는 키 칼럼 목록 지정에 사용->ID칼럼이 자동 생성 칼럼이므로 "ID"로 지정
			 */
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
						"insert into MEMBER (EMAIL,PASSWORD,NAME,REGDATE) values(?,?,?,?)",
						new String[] {"ID"});
				pstmt.setString(1, member.getEmail());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getName());
				pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
				return pstmt;
			}
			/*
			 * PreparedStatement실행 후 자동 생성된 키 값을 KeyHolder에 보관하므로
			 * getKey()를 이용해 키 값을 구한다. -> java.lang.Number를 리턴한다.
			 */
		},keyHolder);
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue()); //원하는 타입으로 변환했다.
	}

	/*
	 * @Transactional이 없지만 JdbcTemplate덕에 트랜잭션 범위에서 쿼리를 실행할 수 있다.
	 * ChangePassword()에 진행중인 트랜잭션이 존재하므로 해당 트랜잭션의 범위에서 쿼리를 실행한다. 
	 */
	public void update(Member member) {
		jdbcTemplate.update(
				"update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
				member.getName(), member.getPassword(), member.getEmail()
				);
	}

}

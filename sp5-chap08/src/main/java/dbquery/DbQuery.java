package dbquery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.tomcat.jdbc.pool.DataSource;

public class DbQuery {

	private DataSource dataSource;

	public DbQuery(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int count() {
		Connection conn = null;
		try {
			/*
			 * DataSource에서 커넥션을 구할 때 풀에서 커넥션을 가져온다.
			 */
			conn = dataSource.getConnection();
			try (Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery("select count(*) from MEMBER")) {
				rs.next();
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null)
				try {
					conn.close(); 
					/*
					 *  풀에 반환(실제 연결이 끊어지는 것은 아님)
					 *  풀에 반환된 커넥션은 유휴상태가 된다.
					 */
				} catch (SQLException e) {
				}
		}
	}
}

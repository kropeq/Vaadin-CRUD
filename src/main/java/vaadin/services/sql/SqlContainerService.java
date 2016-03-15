package vaadin.services.sql;

import java.sql.SQLException;

//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientURI;
//import com.mongodb.client.MongoDatabase;
import com.vaadin.data.Container;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

import vaadin.services.ContainerService;

public class SqlContainerService implements ContainerService {
	
	private final TableQuery usersTable;
	private final TableQuery contestantsTable;
	
	public SqlContainerService() throws SQLException {
		JDBCConnectionPool pool = new SimpleJDBCConnectionPool(
				"org.postgresql.Driver",
				"jdbc:postgresql://localhost:5432/projekt",
				"postgres",
				"vaadin");
		this.usersTable = new TableQuery("users",pool);
		this.contestantsTable = new TableQuery("contestants",pool);
	}
	
	private SQLContainer constructContainer(TableQuery query){
		try { 
			SQLContainer container = new SQLContainer(query);
			container.setAutoCommit(true);
			return container;
		} catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public SQLContainer getUsersContainer() {
		return this.constructContainer(this.usersTable);
	}

	@Override
	public SQLContainer getContestantsContainer() {
		return this.constructContainer(this.contestantsTable);
	}

}

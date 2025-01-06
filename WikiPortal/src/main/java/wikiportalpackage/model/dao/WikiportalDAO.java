package wikiportalpackage.model.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import wikiportalpackage.model.bean.Article;
import wikiportalpackage.model.bean.Category;
import java.sql.Date;

public class WikiportalDAO {

//Define instance variables
	private String DBURL = "jdbc:mysql://localhost:3306/ARTICLESDB?serverTimezone=Australia/Melbourne";
	private String DBUsername = "root";
	private String DBPassword = "bit235mysql";

	private String SELECTALLARTICLES = "SELECT * FROM Articles WHERE Display = TRUE ORDER BY DATE DESC";
	private String SELECTALLARTICLESADMIN = "SELECT * FROM Articles WHERE Display = TRUE ";
	private String SELECTHIDDENARTICLES = "SELECT * FROM Articles WHERE Display = FALSE ORDER BY DATE";
	private String SELECTSINGLEARTICLE = "SELECT a.*, c.Name As catName FROM Articles a, Categories c where a.CategoryID= c.CategoryID and a.ArticleID =?";

	private String SELECTALLCATEGORIES = "select * from Categories WHERE CategoryID != 'C001'";
	private String SELECTARTICLESBYCATEGORY = "SELECT * FROM Articles WHERE CategoryID=? AND Display = TRUE";
	private String SEARCHARTICLES = "SELECT * FROM Articles WHERE (LOWER(Name) LIKE LOWER(?) OR LOWER(Description) LIKE LOWER(?)) AND Display = TRUE";

	private String DELETEARTICLE = "delete from Articles where ArticleID = ?";
	private String UPDATEARTICLE = "UPDATE Articles set Name = ?, Description = ?, CategoryID = ?, Date = STR_TO_DATE(?, '%Y-%m-%d') where ArticleID = ?";
	private String INSERTARTICLE = "INSERT INTO Articles (ArticleID, Name, Description, Date, Display, CategoryID) VALUES (?, ?, ?, ?, ?, (SELECT CategoryID FROM Categories where Name = ?));";
	private String HIDEARTICLE = "UPDATE Articles SET Display = FALSE WHERE ArticleID = ?";
	private String SHOWARTICLE = "UPDATE Articles SET Display = TRUE WHERE ArticleID = ?";

	private String INSERTCATEGORY = "INSERT INTO Categories (CategoryID, Name) VALUES (?, ?);";
	private String UPDATECATEGORY = "UPDATE Articles SET CategoryID = 'C001' WHERE CategoryID = ?";
	private String DELETECATEGORY = "DELETE FROM Categories WHERE CategoryID = ?";

	// public static Boolean isLoggedIn = false;

	// constructor
	public WikiportalDAO() {
	}

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DBURL, DBUsername, DBPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public String getLastArticleID() throws SQLException {
		String lastID = null;
		String query = "SELECT ArticleID FROM Articles ORDER BY ArticleID DESC LIMIT 1";

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			if (rs.next()) {
				lastID = rs.getString("ArticleID");
			}
		}
		return lastID;
	}

	public String getNextArticleID() throws SQLException {
		String lastID = getLastArticleID();
		if (lastID == null) {
			return "A001";
		}

		int artID = Integer.parseInt(lastID.substring(1));
		artID++;

		return String.format("A%03d", artID);
	}

	public String getLastCategoryID() throws SQLException {
		String lastCatID = null;
		String query = "SELECT CategoryID FROM Categories ORDER BY CategoryID DESC LIMIT 1";

		try (Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			if (rs.next()) {
				lastCatID = rs.getString("CategoryID");
			}
		}
		return lastCatID;
	}

	public String getNextCategoryID() throws SQLException {
		String lastCatID = getLastCategoryID();
		if (lastCatID == null) {
			return "C001";
		}

		int catID = Integer.parseInt(lastCatID.substring(1));
		catID++;

		return String.format("C%03d", catID);
	}
// public Boolean isLoggedIn() {
// }

	public List<Article> selectAllArticles() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Article> article = new ArrayList<>();
		// Step 1: Establishing a Connection
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SELECTALLARTICLES);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String ArticleID = rs.getString("ArticleID");
				String Name = rs.getString("Name");
				String Description = rs.getString("Description");
				Date Date = rs.getDate("Date");
				Boolean Display = rs.getBoolean("Display");
				String CategoryID = rs.getString("CategoryID");

				article.add(new Article(ArticleID, Name, Description, Date.toString(), Display, CategoryID));
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
		return article;
	}

	public List<Article> selectAllArticlesAdmin() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Article> article = new ArrayList<>();
		// Step 1: Establishing a Connection
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SELECTALLARTICLESADMIN);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String ArticleID = rs.getString("ArticleID");
				String Name = rs.getString("Name");
				String Description = rs.getString("Description");
				Date Date = rs.getDate("Date");
				Boolean Display = rs.getBoolean("Display");
				String CategoryID = rs.getString("CategoryID");

				article.add(new Article(ArticleID, Name, Description, Date.toString(), Display, CategoryID));
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
		return article;
	}

	public List<Article> selectHiddenArticles() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Article> article = new ArrayList<>();
		// Step 1: Establishing a Connection
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SELECTHIDDENARTICLES);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String ArticleID = rs.getString("ArticleID");
				String Name = rs.getString("Name");
				String Description = rs.getString("Description");
				Date Date = rs.getDate("Date");
				Boolean Display = rs.getBoolean("Display");
				String CategoryID = rs.getString("CategoryID");

				article.add(new Article(ArticleID, Name, Description, Date.toString(), Display, CategoryID));
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
		return article;
	}

	public Article selectArticle(String ArticleID) {
		Article article = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		// Step 1: Establishing a Connection
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SELECTSINGLEARTICLE);
			preparedStatement.setString(1, ArticleID);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String ArtID = rs.getString("ArticleID");
				String Name = rs.getString("Name");
				String Description = rs.getString("Description");
				Date Date = rs.getDate("Date");
				Boolean Display = rs.getBoolean("Display");
				String CategoryID = rs.getString("catName");

				article = new Article(ArtID, Name, Description, Date.toString(), Display, CategoryID);
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
		return article;
	}

	public List<Category> selectAllCategories() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		// using try-with-resources to avoid closing resources (boiler plate code)
		List<Category> category = new ArrayList<>();
		// Step 1: Establishing a Connection
		try {
			connection = getConnection();
			// Step 2:Create a statement using connection object
			preparedStatement = connection.prepareStatement(SELECTALLCATEGORIES);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				String CategoryID = rs.getString("CategoryID");
				String Name = rs.getString("Name");

				category.add(new Category(CategoryID, Name));
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
		return category;
	}

	public List<Article> selectArticlesByCategory(String categoryID) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Article> articles = new ArrayList<>();
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SELECTARTICLESBYCATEGORY);
			preparedStatement.setString(1, categoryID);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String ArticleID = rs.getString("ArticleID");
				String Name = rs.getString("Name");
				String Description = rs.getString("Description");
				Date Date = rs.getDate("Date");
				Boolean Display = rs.getBoolean("Display");
				String CategoryID = rs.getString("CategoryID");
				articles.add(new Article(ArticleID, Name, Description, Date.toString(), Display, CategoryID));
				System.out.println(preparedStatement);
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
		return articles;
	}

	public List<Article> searchArticles(String searchQuery) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Article> articles = new ArrayList<>();
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(SEARCHARTICLES);
			preparedStatement.setString(1, "%" + searchQuery + "%");
			preparedStatement.setString(2, "%" + searchQuery + "%");
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String articleID = rs.getString("ArticleID");
				String name = rs.getString("Name");
				String description = rs.getString("Description");
				Date date = rs.getDate("Date");
				Boolean display = rs.getBoolean("Display");
				String categoryID = rs.getString("CategoryID");

				Article article = new Article(articleID, name, description, date.toString(), display, categoryID);
				System.out.println(preparedStatement);
				articles.add(article);
			}
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, rs);
		}
		return articles;
	}

	public boolean deleteArticle(String id) throws SQLException {
		boolean articleDeleted = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		// System.out.println(id);

		try {

			connection = getConnection();
			preparedStatement = connection.prepareStatement(DELETEARTICLE);
			preparedStatement.setString(1, id);
			articleDeleted = preparedStatement.executeUpdate() > 0 ? true : false;
			// System.out.println(articleDeleted);

		} finally {
			finallySQLException(connection, preparedStatement, null);
		}
		return articleDeleted;
	}

	public boolean setCategory(String id) throws SQLException {
		boolean categorySet = false;
		boolean categoryDeleted = false;
		Connection connection = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		System.out.println(id);

		try {

			connection = getConnection();
			preparedStatement1 = connection.prepareStatement(UPDATECATEGORY);
			preparedStatement1.setString(1, id);

			preparedStatement2 = connection.prepareStatement(DELETECATEGORY);
			preparedStatement2.setString(1, id);

			categorySet = preparedStatement1.executeUpdate() > 0 ? true : false;
			categoryDeleted = preparedStatement2.executeUpdate() > 0 ? true : false;
			System.out.println(preparedStatement1);
			System.out.println(preparedStatement2);
			System.out.println(categorySet);

		} finally {
			finallySQLException(connection, preparedStatement1, null);
			// finallySQLException(connection,preparedStatement2,null);
		}
		// return categorySet;
		return categoryDeleted;
	}

//public boolean deleteCategory(String id) throws SQLException {
//   // boolean categorySet = false;
//    boolean categoryDeleted = false;
//    Connection connection = null; 
//  	//PreparedStatement preparedStatement1 = null;
//  	PreparedStatement preparedStatement = null;
//  	//System.out.println(id);
//  	
//  	try {
//  		
//    	 connection = getConnection(); 
//    	
//    	 preparedStatement = connection.prepareStatement(DELETECATEGORY);
//    	 preparedStatement.setString(1,id);
//    	 //categorySet = preparedStatement1.executeUpdate() > 0 ? true:false;
//    	 categoryDeleted = preparedStatement.executeUpdate() > 0 ? true:false;
//    	 System.out.println( preparedStatement);
//    	 System.out.println(categoryDeleted);
//    	 //System.out.println( preparedStatement2);
//    	 
//    }
//    finally {
//    	//finallySQLException(connection,preparedStatement,null);
//    	finallySQLException(connection,preparedStatement,null);
//    }
//    return categoryDeleted;
//    //return categoryDeleted;
//}

	public void insertArticle(Article art) throws SQLException {
		String newArticleID = getNextArticleID();
		System.out.println(INSERTARTICLE);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		// try-with-resource statement will auto close the connection.
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(INSERTARTICLE);
			preparedStatement.setString(1, newArticleID);
			preparedStatement.setString(2, art.getName());
			preparedStatement.setString(3, art.getDescription());
			preparedStatement.setString(4, art.getDate());
			preparedStatement.setBoolean(5, art.isDisplay());
			preparedStatement.setString(6, art.getCategoryID());

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, null);
		}
	}

	public void insertCategory(Category cat) throws SQLException {
		String newCategoryID = getNextCategoryID();
		System.out.println(INSERTCATEGORY);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		// try-with-resource statement will auto close the connection.
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(INSERTCATEGORY);
			preparedStatement.setString(1, newCategoryID);
			preparedStatement.setString(2, cat.getName());

			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, null);
		}
	}

	public boolean updateArticle(Article art) throws SQLException {
		boolean artUpdated = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(UPDATEARTICLE);
			preparedStatement.setString(1, art.getName());
			preparedStatement.setString(2, art.getDescription());
			preparedStatement.setString(3, art.getCategoryID());
			preparedStatement.setString(4, art.getDate());
			preparedStatement.setString(5, art.getArticleID());

			artUpdated = preparedStatement.executeUpdate() > 0 ? true : false;
			System.out.println(preparedStatement);
			System.out.println(artUpdated);
		} catch (SQLException e) {
			printSQLException(e);
		} finally {
			finallySQLException(connection, preparedStatement, null);
		}
		return artUpdated;
	}

	public boolean hideArticle(String id) throws SQLException {
		boolean articleHidden = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		// System.out.println(id);

		try {

			connection = getConnection();
			preparedStatement = connection.prepareStatement(HIDEARTICLE);
			preparedStatement.setString(1, id);
			articleHidden = preparedStatement.executeUpdate() > 0 ? true : false;
			// System.out.println(articleDeleted);

		} finally {
			finallySQLException(connection, preparedStatement, null);
		}
		return articleHidden;
	}

	public boolean showArticle(String id) throws SQLException {
		boolean articleHidden = false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		// System.out.println(id);

		try {

			connection = getConnection();
			preparedStatement = connection.prepareStatement(SHOWARTICLE);
			preparedStatement.setString(1, id);
			articleHidden = preparedStatement.executeUpdate() > 0 ? true : false;
			// System.out.println(articleDeleted);

		} finally {
			finallySQLException(connection, preparedStatement, null);
		}
		return articleHidden;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	private void finallySQLException(Connection c, PreparedStatement p, ResultSet r) {
		if (r != null) {
			try {
				r.close();
			} catch (Exception e) {
			}
			r = null;
		}

		if (p != null) {
			try {
				p.close();
			} catch (Exception e) {
			}
			p = null;
		}

		if (c != null) {
			try {
				c.close();
			} catch (Exception e) {
				c = null;
			}

		}
	}
}

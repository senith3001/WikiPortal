package wikiportalpackage.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wikiportalpackage.model.bean.*;
import wikiportalpackage.model.dao.*;

/**
 * Servlet implementation class WikiPortalServlet
 */
@WebServlet("/WikiPortalServlet")
public class WikiPortalServlet extends HttpServlet {
	private WikiportalDAO WikiDAO; // Define as instance variable

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WikiPortalServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() {
		WikiDAO = new WikiportalDAO();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean loggedIn = LoginStatus.isLoggedIn();
		String action = request.getParameter("action");
		if (action == null) {
			action = "No action";
		}
		try {
			switch (action) {
			case "readmore":
				showArticle(request, response);
				break;
			case "artnames":
				listArticleNames(request, response);
				break;
			case "catnames":
				listCategory(request, response);
				break;
			case "articlesByCategory":
				listArticlesByCategory(request, response);
				break;

			case "search":
				searchArticles(request, response);
				break;
				
				
				
			case "insert":
				insertArticle(request, response);
				break;
			case "delete":
				deleteArticle(request, response);
				break;
			case "hide":
				hideArticle(request, response);
				break;
				
			case "allart":
				// isLoggedIn = true;
				allArticles(request, response);
				break;
			
			
			case "edit":
				editArticle(request, response);
				break;
			case "showedit":
				showEditArticle(request, response);
				break;
			case "insertnewcat":
				insertCategory(request, response);
				break;
			
			case "show":
				showToPublic(request, response);
				break;
			case "hidden":
				hiddenArticleList(request, response);
				break;
			case "admincatnames":
				listAdminCategory(request, response);
				break;
			case "deletecat":
				deleteCategory(request, response);
				break;
			case "logout":
				logOut(request, response);
				break;
			default:
				listArticle(request, response);
				break;
			}

		} catch (Exception ex) {
			throw new ServletException(ex);
		}
	}

	private void listArticle(HttpServletRequest request, HttpServletResponse response)  //get a list of the articles to home page
			throws SQLException, IOException, ServletException {

		List<Article> listArticle = WikiDAO.selectAllArticles();
		request.setAttribute("listArticle", listArticle);
		RequestDispatcher dispatcher = request.getRequestDispatcher("HomePage.jsp");
		dispatcher.forward(request, response);

	}

	private void showArticle(HttpServletRequest request, HttpServletResponse response)    //when clicked readmore show the entire article
			throws SQLException, ServletException, IOException {
		String id = request.getParameter("id");
		Article existingArticle = WikiDAO.selectArticle(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Article.jsp");
		request.setAttribute("article", existingArticle);
		dispatcher.forward(request, response);
	}

	private void listArticleNames(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Article> listArticle = WikiDAO.selectAllArticles();
		request.setAttribute("listArticle", listArticle);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Articles.jsp");
		dispatcher.forward(request, response);
	}

	private void listCategory(HttpServletRequest request, HttpServletResponse response)				//listout the categories
			throws SQLException, IOException, ServletException {
		List<Category> listCategory = WikiDAO.selectAllCategories();
		request.setAttribute("listCategory", listCategory);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Category.jsp");
		dispatcher.forward(request, response);
	}

	private void listArticlesByCategory(HttpServletRequest request, HttpServletResponse response)				
			throws SQLException, IOException, ServletException {
		String categoryID = request.getParameter("categoryID");
		// System.out.println(categoryID);
		List<Article> articlesByCategory = WikiDAO.selectArticlesByCategory(categoryID);
		System.out.println(categoryID);

		request.setAttribute("articlesByCategory", articlesByCategory);
		RequestDispatcher dispatcher = request.getRequestDispatcher("Articlelist.jsp");
		dispatcher.forward(request, response);
	}

	private void searchArticles(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		String searchQuery = request.getParameter("searchQuery");
		System.out.println(searchQuery);
		List<Article> searchResults = WikiDAO.searchArticles(searchQuery);
		request.setAttribute("searchResults", searchResults);

		RequestDispatcher dispatcher = request.getRequestDispatcher("SearchResults.jsp");
		dispatcher.forward(request, response);
	}

	private void deleteArticle(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		String id = request.getParameter("id");

		WikiDAO.deleteArticle(id);

		response.sendRedirect(request.getContextPath() + "/WikiPortalServlet?action=allart");
	}

	private void allArticles(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		// System.out.println(isLoggedIn + "jj");
		boolean loggedIn = LoginStatus.isLoggedIn();
		System.out.println(loggedIn);
		if (loggedIn == true) {
			List<Article> listArticle = WikiDAO.selectAllArticlesAdmin();
			request.setAttribute("listArticle", listArticle);
			RequestDispatcher dispatcher = request.getRequestDispatcher("AllArticles.jsp");
			dispatcher.forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/WikiPortalServlet");
		}
	}

	private void showEditArticle(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		String id = (request.getParameter("id"));
		Article existingArticle = WikiDAO.selectArticle(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("EditArticle.jsp");
		request.setAttribute("article", existingArticle);
		dispatcher.forward(request, response);
	}

	private void editArticle(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		System.out.println("kk");
		String id = (request.getParameter("id"));
		String name = (request.getParameter("name"));
		String description = (request.getParameter("description"));
		String category = request.getParameter("catname");
		String date = (request.getParameter("date"));
		System.out.println(date);
		Article e = new Article(id, name, description, category, date);
		WikiDAO.updateArticle(e);
		response.sendRedirect(request.getContextPath() + "/WikiPortalServlet?action=allart");
	}

	private void insertArticle(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		String name = request.getParameter("name");
		String description = (request.getParameter("description"));
		String date = (request.getParameter("date"));
		String category = request.getParameter("catname");
		boolean display = request.getParameter("display") != null && request.getParameter("display").equals("true");

		Article art = new Article(name, description, date, display, category);
		WikiDAO.insertArticle(art);
		response.sendRedirect(request.getContextPath() + "/WikiPortalServlet?action=allart");
	}

	private void insertCategory(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		String name = request.getParameter("name");

		Category cat = new Category(name);
		WikiDAO.insertCategory(cat);
		response.sendRedirect(request.getContextPath() + "/WikiPortalServlet?action=allart");
	}

	private void hideArticle(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		// System.out.println("kk");
		String id = request.getParameter("id");
		WikiDAO.hideArticle(id);
		response.sendRedirect(request.getContextPath() + "/WikiPortalServlet?action=allart");
	}

	private void showToPublic(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		// System.out.println("kk");
		String id = request.getParameter("id");
		WikiDAO.showArticle(id);
		response.sendRedirect(request.getContextPath() + "/WikiPortalServlet?action=allart");
	}

	private void hiddenArticleList(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Article> listArticle = WikiDAO.selectHiddenArticles();
		request.setAttribute("listArticle", listArticle);
		RequestDispatcher dispatcher = request.getRequestDispatcher("HiddenArticleList.jsp");
		dispatcher.forward(request, response);
	}

	private void listAdminCategory(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Category> listCategory = WikiDAO.selectAllCategories();
		request.setAttribute("listCategory", listCategory);
		RequestDispatcher dispatcher = request.getRequestDispatcher("DeleteCategory.jsp");
		dispatcher.forward(request, response);
	}

	private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		String id = request.getParameter("id");
		WikiDAO.setCategory(id);

		System.out.println("kk");

		response.sendRedirect(request.getContextPath() + "/WikiPortalServlet?action=admincatnames");
	}

	private void logOut(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
//		request.getSession().invalidate();
		LoginStatus.setLoggedIn(false);
//		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//		response.setHeader("Pragma", "no-cache");
//		response.setHeader("Expires", "0");
		response.sendRedirect(request.getRequestURI());
		//response.sendRedirect(request.getContextPath() + "/WikiPortalServlet");

	}

}

package servlet07.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servlet07.dao.UserDao;
import servlet07.dto.User;


@WebServlet("/")
public class UserController extends HttpServlet {
	
	private UserDao userDao = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		userDao = new UserDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doHandler(request, response);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doHandler(request, response);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
	
	public void doHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		String action = request.getServletPath();
		
		RequestDispatcher dispatcher = null;
		
		switch(action) {
		case "/new":
			 dispatcher = request.getRequestDispatcher("user-form.jsp");
			dispatcher.forward(request, response);
			break;
			
		case "/insert":
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			String passwd = request.getParameter("passwd");
			
			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setPasswd(passwd);
			
			try {
				userDao.userInsert(user);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			response.sendRedirect("list");
			
			break;
			
		default:
			List<User> users = userDao.userList();
			request.setAttribute("users", users);
			
			dispatcher = request.getRequestDispatcher("user-list.jsp");
			dispatcher.forward(request, response);
			break;
		}
	}

}

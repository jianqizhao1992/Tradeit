package com.tradeit.actions.bussiness;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import com.tradeit.utility.database.*;
import com.tradeit.utility.image.*;

@WebServlet(name="deletepost",
	urlPatterns={"/deletepost.do"})
public class DeletePost extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = getServletContext();
		try {
			/* get current session */
			HttpSession session = request.getSession();
			
			/* responds with CSRF error if csrf_token doesn't match */
			if(!session.getAttribute("csrf_token").equals(request.getParameter("csrf_token"))) {
				session.invalidate();
				response.sendRedirect("/Tradeit/csrfthreat.html");
      			return;
			}
			
			/* get user id */
			String username = (String)session.getAttribute("username");
			
			/* get post id */
			String postIdentifier = request.getParameter("postid");
			int postid = Integer.parseInt(postIdentifier);
			
			PostOperate.deletePost(postid, username);
			
			RequestDispatcher postCreateSuccess = request.getRequestDispatcher("/viewmypost.jsp");
			postCreateSuccess.forward(request, response);
		
			
		} catch (Exception e) {
			context.log(e.toString());
		}
		
		
	}
}
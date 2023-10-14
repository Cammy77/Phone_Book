package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PhoneBookDetails;

/**
 * Servlet implementation class PhoneBookNavServlet
 */
@WebServlet("/phoneBookNavServlet")
public class PhoneBookNavServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhoneBookNavServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PhoneBookDetailsHelper pbdh = new PhoneBookDetailsHelper();
		String act = request.getParameter("doThisToPhoneBook");
		
		if (act == null) {
			//no button selected
			getServletContext().getRequestDispatcher("viewAllPhoneBooksServlet").forward(request, response);			
		}else if (act.equals("delete")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				PhoneBookDetails listToDelete = pbdh.searchForDetailsById(tempId);
				pbdh.deleteList(listToDelete);
			}catch (NumberFormatException e) {
				System.out.println("Forgot to click a button");
			}finally {
				getServletContext().getRequestDispatcher("/viewAllPhoneBooksServlet").forward(request, response);
			}
		}else if (act.equals("edit")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				PhoneBookDetails pbDetailsToEdit = pbdh.searchForDetailsById(tempId);
				request.setAttribute("pbDetailsToEdit", pbDetailsToEdit);
				
				ContactHelper ch = new ContactHelper();
				
				request.setAttribute("allContacts", ch.showAllContacts());
				
				if(ch.showAllContacts().isEmpty()) {
					request.setAttribute("allContacts", " ");
				}
				
				getServletContext().getRequestDispatcher("/edit-list.jsp").forward(request,response);
				
			}catch (NumberFormatException e) {
				getServletContext().getRequestDispatcher("/viewAllPhoneBooksServlet").forward(request,response);
			}
		}else if (act.equals("add")) {
			getServletContext().getRequestDispatcher("/new-phone-list.jsp").forward(request,response);
		}
	}

}

package uk.org.jaggard.test;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

/**
 * 
 * @author JaggardM
 */
@Log
public abstract class ServletUtil extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	public static final int FORCE_LOGGEDOUT = -1;
	public static final int FORCE_LOGGEDIN = 1;
	public static final int FORCE_NONE = 0;
	protected String uri = null;

	private void doRequestInit(HttpServletRequest req)
	{
		Object uriO = req.getAttribute("javax.servlet.forward.request_uri");
		if (uriO == null)
		{
			uri = req.getRequestURI();
		}
		else
		{
			uri = uriO.toString();
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException
	{
		doRequestInit(req);
		addLoginCookie(resp);
		resp.setCharacterEncoding("UTF-8");
		doRequest(req, resp);
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException
	{
		doRequestInit(req);
		addLoginCookie(resp);
		resp.setCharacterEncoding("UTF-8");
		doRequest(req, resp);
	}

	public abstract void doRequest(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException;

	public void addLoginCookie(HttpServletResponse resp)
	{
		UserService us = UserServiceFactory.getUserService();
		Cookie loggedIn = new Cookie("openid_loggedin", (us.getCurrentUser() != null ? us.getCurrentUser().getNickname() : "n"));
		loggedIn.setMaxAge(604800); //60 * 60 * 24 * 7 = 1 week in seconds.
		loggedIn.setPath("/");
		resp.addCookie(loggedIn);
	}
}

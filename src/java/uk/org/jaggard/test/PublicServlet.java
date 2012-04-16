package uk.org.jaggard.test;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import uk.org.jaggard.test.openid.LoginPage;
import uk.org.jaggard.test.openid.OpenIDProviders;
import uk.org.jaggard.test.util.UnsafeString;

/**
 *
 * @author JaggardM
 */
@Log
public class PublicServlet extends ServletUtil
{

	public static final String LOGIN_REQUIRED = "login_required";
	public static final String LOGOUT = "openid_logout";
	public static final String LOGIN_TYPE = "openid_type";
	public static final String LOGIN_USERNAME = "openid_username";
	public static final String NEXT_PAGE_PARAM = "continue";
	public static final String INTERIM_PAGE = "loggedin_interim";
	public static final String CHECK_USER_ID = "check_user_id";
	private static final long serialVersionUID = 1L;

	@Override
	public void doRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException
	{
		if (uri.contains(LOGOUT))
		{
			UserService us = UserServiceFactory.getUserService();
			if (us.isUserLoggedIn())
			{
				//addLoginCookie(resp, ServletUtil.FORCE_LOGGEDOUT);
				resp.sendRedirect(us.createLogoutURL("/loggedout.html"));
				return;
			}
			else
			{
				resp.sendRedirect(us.createLogoutURL("/notloggedin.html"));
				return;
			}
		}
		else if (uri.contains(LOGIN_REQUIRED))
		{
			UnsafeString contURL = new UnsafeString(req.getParameter(NEXT_PAGE_PARAM));
			if (contURL.isNull())
			{
				contURL = new UnsafeString("/");
			}
			UserService us = UserServiceFactory.getUserService();
			if (req.getParameter(LOGIN_TYPE) != null)
			{
				UnsafeString loginType = new UnsafeString(req.getParameter(LOGIN_TYPE));
				OpenIDProviders oidp = null;
				try
				{
					oidp = OpenIDProviders.valueOf(loginType.toUnsafeString());
				}
				catch (IllegalArgumentException iae)
				{
					log.log(Level.SEVERE, "Login type not valid: " + loginType, iae);
					resp.sendRedirect("/");
					return;
				}
				String url = oidp.getUrl();
				String username = req.getParameter(LOGIN_USERNAME);
				if (url == null)
				{
					url = username;
				}
				else if (oidp.isRequiringUsername())
				{
					url = url.replace("{username}", username);
				}
				//addLoginCookie(resp, ServletUtil.FORCE_LOGGEDIN);
				UnsafeString contcontURL = new UnsafeString("/public/" + INTERIM_PAGE + '?' + NEXT_PAGE_PARAM + '=' + contURL.encodeForURL());
				resp.sendRedirect(us.createLoginURL(contcontURL.toUnsafeString(), null, url, null));
				//Rely on the UserService to make this safe.
				return;
			}
			else if (us.isUserLoggedIn())
			{
				log.log(Level.SEVERE, "Already logged in.");
				req.getRequestDispatcher("/alreadyloggedin.html").include(req, resp);
				return;
			}
			else
			{
				resp.setContentType("text/html");
				PrintWriter out = resp.getWriter();
				//out.print(header.CONTENT);
				out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"http://yui.yahooapis.com/combo?2.9.0/build/reset-fonts/reset-fonts.css&2.9.0/build/base/base-min.css&2.9.0/build/assets/skins/sam/skin.css\">");
				out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"/stylesheets/openid-shadow.css\">");
				out.print("<link rel=\"stylesheet\" type=\"text/css\" href=\"/stylesheets/site.css\">");
				out.print(LoginPage.createLoginPage(contURL));
				//out.print(footer.CONTENT);
				out.print("<div id=\"loggedin\"></div>");
				out.print("<script src=\"http://yui.yahooapis.com/combo?2.9.0/build/yahoo-dom-event/yahoo-dom-event.js&2.9.0/build/element/element-min.js&2.9.0/build/container/container_core-min.js&2.9.0/build/menu/menu-min.js&2.9.0/build/button/button-min.js&2.9.0/build/editor/simpleeditor-min.js\"></script>");
				out.print("<script src=\"/javascript/loggedin.js\"></script>");
				out.print("<script src=\"/javascript/openid-mat.js\"></script>");
				out.print("<script>");
				out.print("if (checkIfLoggedIn) checkIfLoggedIn();");
				out.print("</script>");
				return;
			}
		}
		else if (uri.contains(INTERIM_PAGE))
		{
			UserService us = UserServiceFactory.getUserService();
			//userLoggingIn();
			String nextPage = req.getParameter(NEXT_PAGE_PARAM);
			/*
			if (us.isUserAdmin())
			{
				resp.sendRedirect(Constants.BASE_URL.toString() + Constants.HOME.toString());
				return;
			}
			else*/
			if (nextPage != null)
			{
				resp.sendRedirect(nextPage);
				return;
			}
			else
			{
				resp.sendRedirect("/");
				return;
			}
		}
		else if (uri.contains(CHECK_USER_ID))
		{
			UserService us = UserServiceFactory.getUserService();
			resp.setContentType("text/plain");
			PrintWriter out = resp.getWriter();
			User user = us.getCurrentUser();
			if (user == null)
				out.println("User is null");
			else
			{
				out.print("The UserID is ");
				out.println(user.getUserId());
				DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
				Entity toPut = new Entity("User", 1);
				toPut.setUnindexedProperty("User", user);
				Key k = ds.put(toPut);
				try
				{
					Entity got = ds.get(k);
					if (got == null)
					{
						out.println("got is null");
						return;
					}
					Object newUserO = got.getProperty("User");
					if (newUserO == null)
					{
						out.println("property 'User' is null");
						return;
					}
					if (!(newUserO instanceof User))
					{
						out.println("property 'User' is not an instance of User");
						return;
					}
					User newUser = (User) newUserO;
					out.print("The UserID from the datastore is ");
					out.println(newUser.getUserId());
				}
				catch (EntityNotFoundException ex)
				{
					log.log(Level.WARNING, "Trying to put and immediately get a user object, we've failed to get");
					out.println("Trying to put and immediately get a user object, we've failed to get");
				}
			}
		}
		else
		{
			resp.sendError(404);
		}
	}
}

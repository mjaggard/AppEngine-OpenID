package uk.org.jaggard.test.openid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import uk.org.jaggard.test.util.UnsafeString;

/**
 *
 * @author mjaggard
 */
public class LoginPage
{

	public static final String IMAGE_URL = "/images/openid-providers-en.png";
	public static final String LOG_IN_WITH = "Log in with "; //TODO: Localisation
	public static final int LARGE_HEIGHT = 60;
	public static final int LARGE_WIDTH = 100;
	public static final int SMALL_WIDTH = 24;

	public static String createLoginPage(UnsafeString baseURL)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>Log-in</h1>");
		sb.append("<br/>");
		sb.append("<form action=\"\" method=\"get\" id=\"openid_form\">");
		sb.append("<fieldset>");
		sb.append("<div id=\"openid_choice\">");
		sb.append("<p>Please click your account provider:</p>");
		sb.append("<div id=\"openid_btns\">");
		int x = 0;
		int y = 0;
		List<OpenIDProviders> large_then_all = new ArrayList<OpenIDProviders>();
		//large_then_all starts with just the large items.
		for (OpenIDProviders oidp : OpenIDProviders.values())
		{
			if (oidp.isLarge())
			{
				large_then_all.add(oidp);
			}
		}
		large_then_all.add(null);
		//We check for this null later so we know when we've finished doing the large ones.
		large_then_all.addAll(Arrays.asList(OpenIDProviders.values()));
		boolean showLarge = true;
		for (OpenIDProviders oidp : large_then_all)
		{
			if (oidp == null) //when we get null, we know we're done with large ones.
			{
				x = 0;
				y = LARGE_HEIGHT;
				showLarge = false;
				sb.append("<br/>");
				continue;
			}
			if (showLarge == false && oidp.isLarge())
			{
				x += SMALL_WIDTH;
				continue;
			}
			sb.append("<div><input data-no-username=\"");
			sb.append(!oidp.isRequiringUsername());
			sb.append("\" type=\"radio\" name=\"openid_type\" value=\"");
			sb.append(oidp.toString());
			sb.append("\" id=\"");
			sb.append(oidp.toString());
			String function;
			if (oidp.isRequiringUsername())
			{
				//We only highlight when clicked/changed if a username is needed
				function = "highlight";
			}
			else
			{
				//If no username is needed, we go ahead and load the provider when clicked/changed
				function = "openid_load";
			}
			sb.append("\" onchange=\"");
			sb.append(function);
			sb.append("('");
			sb.append(oidp.toString());
			sb.append("'); return false;\" onclick=\"");
			sb.append(function);
			sb.append("('");
			sb.append(oidp.toString());
			sb.append("'); return false;\" />");
			sb.append("<label for=\"");
			sb.append(oidp.toString());
			sb.append("\" style=\"background: url('");
			sb.append(IMAGE_URL);
			sb.append("') no-repeat scroll -");
			sb.append(x);
			sb.append("px -");
			sb.append(y);
			sb.append("px rgb(255, 255, 255);\" class=\"openid_");
			sb.append((oidp.isLarge()) ? "large" : "small");
			sb.append("_btn\" title=\"");
			sb.append(LOG_IN_WITH);
			sb.append(oidp.getName());
			sb.append("\"><span>");
			sb.append(oidp.getName());
			sb.append("</span></label>");
			/*
			if (oidp.getPrompt() != null)
			{
			sb.append("<label class=\"openid_prompt\" for=\"username_");
			sb.append(oidp.toString());
			sb.append("\">");
			sb.append(oidp.getPrompt());
			sb.append("</label>");
			sb.append("<input type=\"text\" name=\"username_");
			sb.append(oidp.toString());
			sb.append("\" id=\"username_");
			sb.append(oidp.toString());
			sb.append("\" />");
			}
			 */
			sb.append("</div>");
			x += (oidp.isLarge()) ? LARGE_WIDTH : SMALL_WIDTH;
		}
		sb.append("</div>");
		sb.append("<br/>");
		sb.append("<label class=\"openid_prompt\" id=\"username_label\" for=\"openid_username\">Username: </label>");
		sb.append("<input type=\"text\" name=\"openid_username\" id=\"username\" />");
		sb.append("<input type=\"submit\" id=\"openid_submit\" value=\"Sign in\"/>");
		sb.append("<input type=\"hidden\" name=\"continue\" value=\"");
		sb.append(baseURL.encodeForHTML());
		sb.append("\"/>");
		sb.append("</div>");
		sb.append("</fieldset>");
		sb.append("</form>");
		sb.append("<script>start();</script>");
		return sb.toString();
	}
}

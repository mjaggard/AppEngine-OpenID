package uk.org.jaggard.test;

//import cambridge.Template;
import java.util.Calendar;
import lombok.AllArgsConstructor;

/**
 *
 * @author jaggardm
 */
@AllArgsConstructor
public enum Constants implements CharSequence
{

	BASE_URL("/admin/"),
	HOME("home"),
	CREATE_INDEX_PAGE("showCreateIndex"),
	EDIT_INDEX_PAGE("editIndex"),
	CREATE_INDEX_ACTION("createIndexNow"),
	UPDATE_INDEX_ACTION("updateIndexNow"),
	CREATE_PAGE_PAGE("showCreatePage"),
	CREATE_PAGE_ACTION("createPageNow"),
	CREATE_PAGE_PARAMETER("createPageParam"),
	CREATE_PAGE_VALUE("createPageValue"),
	EDIT_PAGE("showEditPage"),
	EDIT_PAGE_ACTION("editPageNow"),
	EDIT_PAGE_PARAMETER("editPageParam"),
	VIEW_RENDERED_PAGE("viewPage"),
	ALLOWED_USERS_PARAM("allowedUsers"),
	NO_QUEUE_PARAM("noQueueTick"),
	NO_QUEUE_NOW_PARAM("noQueueNowTick"),
	CAPTCHA_FOR_QUEUE_PARAM("captchaForQueueTick"),
	CAPTCHA_FOR_ENTRY_PARAM("captchaForEntryTick"),
	USERS_USED_TO_BE_ALLOWED("oldAllowedUsers"),
	MORE_TABS("<!--MORETABSHERE-->"),
	PAGE_EDITOR_ID("Page_Editor"),
	EMPTY_MEMCACHE("emptyMemcache"),
	VIEW_DATASTORE("viewAllDatastore"),
	REDIRECT_URL("redirectURL"),
	USERS_PER_MIN("usersPerMin"),
	YEAR(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
	private final String val;

	@Override
	public String toString()
	{
		return val;
	}

	/*
	public static void setTemplateProperties(Template template)
	{
		for (Constants con : Constants.values())
		{
			template.setProperty(con.name(), con.toString());
		}
	}
	*/

	@Override
	public int length()
	{
		return toString().length();
	}

	@Override
	public char charAt(int index)
	{
		return toString().charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end)
	{
		return toString().subSequence(start, end);
	}
}

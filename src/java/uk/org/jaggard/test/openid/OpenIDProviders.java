package uk.org.jaggard.test.openid;

import lombok.AllArgsConstructor;
import lombok.Getter;

//toString() MUST RETURN A STRING OF NORMAL CHARACTERS WITH NO SPACES.
/**
 *
 * @author mjaggard
 */
@AllArgsConstructor
public enum OpenIDProviders
{
	//TODO: Localisation
	GOOGLE("Google", "https://www.google.com/accounts/o8/id", null, true),
	YAHOO("Yahoo", "http://me.yahoo.com/", null, true),
	AOL("AOL", "http://openid.aol.com/{username}", "Enter your AOL screenname.", true),
	MYOPENID("MyOpenID", "http://{username}.myopenid.com/", "Enter your MyOpenID username.", true),
	OPENID("OpenID", null, "Enter your OpenID.", true),

	
	LIVEJOURNAL("LiveJournal", "http://{username}.livejournal.com/", "Enter your Livejournal username.", false),
	FLICKR("Flickr", "http://flickr.com/{username}/", "Enter your Flickr username.", false),
	//TECHNNORATI("Technorati", "http://technorati.com/people/technorati/{username}/", "Enter your Technorati username.", false),
	WORDPRESS("Wordpress", "http://{username}.wordpress.com/", "Enter your Wordpress.com username.", false),
	BLOGGER("Blogger", "http://{username}.blogspot.com/", "Your Blogger account", false),
	VERISIGN("Verisign", "http://{username}.pip.verisignlabs.com/", "Your Verisign username", false),
	//VIDOOP("Vidoop", "http://{username}.myvidoop.com/", "Your Vidoop username", false),
	LAUNCHPAD("Launchpad", "https://launchpad.net/~{username}", "Your Launchpad username", false),
	CLAIMID("ClaimID", "http://claimid.com/{username}", "Your ClaimID username", false),
	CLICKPASS("ClickPass", "http://clickpass.com/public/{username}", "Enter your ClickPass username", false);
	//GOOGLEPROFILE("Google Profile", "http://www.google.com/profiles/{username}", "Enter your Google Profile username", false);
	
	@Getter
	private final String name;
	@Getter
	private final String url;
	@Getter
	private final String prompt;
	@Getter
	private final boolean large;

	public boolean isRequiringUsername()
	{
		return prompt != null;
	}
}

function checkIfLoggedIn()
{
	var loggedInMessage = document.getElementById("loggedin");
	if (loggedInMessage)
	{
		var loggedInAs = loggedIn();
		if (loggedInAs == "n")
		{
			loggedInMessage.innerHTML = 'You are not logged in. <a href="/public/login_required">Log in now</a>';
		}
		else if (loggedInAs)
		{
			if (loggedInAs != "y")
				loggedInAs = ' as: ' + loggedInAs;
			else
				loggedInAs = "";
			loggedInMessage.innerHTML = 'Logged in' + loggedInAs + ' <a href="/public/openid_logout">Log out</a>';
			var div = document.getElementById("loginout_div")
			if (div)
				div.innerHTML = 'Log out';
			var a = document.getElementById("loginout_a")
			if (a)
				a.href = '/public/openid_logout';
		}
		else
		{
			loggedInMessage.innerHTML = '<a href="/public/openid_logout">Log out</a>';
		}
	}
}

function loggedIn()
{
	var cookies = document.cookie.split(";");
	for (var i = 0; i < cookies.length; i++)
	{
		var x = cookies[i].substr(0, cookies[i].indexOf("="));
		var y = cookies[i].substr(cookies[i].indexOf("=") + 1);
		x = x.replace(/^\s+|\s+$/g, "");
		if (x == "openid_loggedin")
		{
			return unescape(y);
		}
	}
	return false;
}


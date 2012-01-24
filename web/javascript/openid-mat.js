function start()
{
	var typeButtons = document.getElementsByName("openid_type");
	for (var i = 0; i < typeButtons.length; i++)
	{
		try
		{
			typeButtons[i].style.position = "absolute";
			typeButtons[i].style.top = "-1000px";
			typeButtons[i].style.left = "-1000px";
		}
		catch (e)
		{
		//Ignore and try the next one.
		}
	}
	var cookieBox = readCookie();
	if (cookieBox)
	{
		var select = document.getElementById(cookieBox);
		select.checked = "checked";
		highlight(cookieBox);
	}
}
function openid_load(box_id)
{
	highlight(box_id);
	var form = document.getElementById("openid_form");
	if (form && form.submit)
		form.submit();
}

function highlight(box_id)
{
	//console.log(box_id);
	var highlight = document.getElementById("openid_highlight");
	if (highlight)
		highlight.id = "";
	var toHighlight = document.getElementById(box_id);
	toHighlight.parentNode.id = "openid_highlight";
	//console.log("Setting cookie... " + box_id);
	setCookie(box_id);
	var noUsername = toHighlight.getAttribute("data-no-username");
	//console.log(noUsername);
	var disp;
	if (noUsername == "true")
		disp = "none";
	else
		disp = "";
	var toHide = document.getElementById("username_label");
	if (toHide)
	{
		toHide.style.display = disp;
		if (box_id == "OPENID")
		{
			toHide.innerHTML = "Open ID: ";
		}
		else
		{
			toHide.innerHTML = "Username: ";
		}
	}
	toHide = document.getElementById("username");
	if (toHide)
		toHide.style.display = disp;
	toHide = document.getElementById("openid_submit");
	if (toHide)
		toHide.style.display = disp;
}

function setCookie(value)
{
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + (6 * 30)); //Expiry = 6 months
	document.cookie = "openid_provider=" + escape(value) + ";expires=" + exdate.toUTCString();
	//console.log(document.cookie);
}

function readCookie()
{
	//console.log("Reading cookie");
	var cookies = document.cookie.split(";");
	//console.log(cookies);
	for (var i = 0; i < cookies.length; i++)
	{
		var x = cookies[i].substr(0, cookies[i].indexOf("="));
		var y = cookies[i].substr(cookies[i].indexOf("=") + 1);
		x = x.replace(/^\s+|\s+$/g, "");
		if (x == "openid_provider")
		{
			//console.log("returning cookie " + y);
			return unescape(y);
		}
	}
	return "";
}
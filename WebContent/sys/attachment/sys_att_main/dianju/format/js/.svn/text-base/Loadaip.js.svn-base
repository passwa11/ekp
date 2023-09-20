var s = "";
if(String(navigator.platform).indexOf("Linux") > -1){
	//alert("guochan");
	s = "<object name='webaip' id='HWPostil1' type='application/dj' width='100%' height='100%'>"
		+ "<param name='Enabled' value='1'/>"
		+ "</object>";
}else{
	if(navigator.userAgent.indexOf("Firefox")>0 || navigator.userAgent.indexOf("Chrome")>0){
		//alert("Windows Firefox");
		s = "<object id='HWPostil1' type='application/x-itst-activex' align='baseline' border='0'"
			+ "style='LEFT: 0px; WIDTH: 100%; TOP: 0px; HEIGHT: 680px'" 
			+ "clsid='{FF1FE7A0-0578-4FEE-A34E-FB21B277D561}'"
			+ "event_NotifyBeforeAction='NotifyBeforeAction'>"
			+ "</object>";	
	}else{
		//alert("Windows IE");
		s = "<object id='HWPostil1' align='middle' style='LEFT: 0px; WIDTH: 100%; TOP: 0px; HEIGHT: 100%'"
			+ "classid='clsid:FF1FE7A0-0578-4FEE-A34E-FB21B277D561'>"
			+ "</object>";
	}
}
document.write(s)
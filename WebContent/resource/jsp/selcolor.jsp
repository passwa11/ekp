<HTML>
<HEAD>
<TITLE></TITLE>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ include file="/resource/jsp/common.jsp"%>
<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<SCRIPT LANGUAGE=JavaScript>

function getText(obj){
	var text;
	if("textContent" in obj)
		text = 	obj.textContent;
	else
		text = obj.innerText ;
	return text;
}

function setText(obj, text){
  if("textContent" in obj)
    obj.textContent = text;
  else
    obj.innerText = text;
}

var SelRGB = '#000000';
var DrRGB = '';
var SelGRAY = '120';

var hexch = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F');

function ToHex(n) {
  var h, l;

  n = Math.round(n);
  l = n % 16;
  h = Math.floor((n / 16)) % 16;
  return (hexch[h] + hexch[l]);
}

function DoColor(c, l) {
  var r, g, b;

  r = '0x' + c.substring(1, 3);
  g = '0x' + c.substring(3, 5);
  b = '0x' + c.substring(5, 7);

  if (l > 120) {
    l = l - 120;

    r = (r * (120 - l) + 255 * l) / 120;
    g = (g * (120 - l) + 255 * l) / 120;
    b = (b * (120 - l) + 255 * l) / 120;
  } else {
    r = (r * l) / 120;
    g = (g * l) / 120;
    b = (b * l) / 120;
  }

  return '#' + ToHex(r) + ToHex(g) + ToHex(b);
}

function EndColor() {
  var i;

  if (DrRGB != SelRGB) {
    DrRGB = SelRGB;
    for (i = 0; i <= 30; i++){
    	document.getElementById('GrayTable').rows[i].bgColor = DoColor(SelRGB, 240 - i * 8);
    }
    
  }
  document.getElementById('SelColor').value = DoColor(getText(document.getElementById('RGB')), getText(document.getElementById('GRAY')));
  document.getElementById('ShowColor').bgColor = SelColor.value;
}

// 兼容firefox

function getET(evt) {
  evt = evt || event;
  return evt.srcElement ? evt.srcElement : evt.target;
}

var pool = {
  'ColorTable': {
    'onclick': function(evt) {
      SelRGB = getET(evt).bgColor;
      EndColor();
    },
    'onmouseover': function(evt) {
      setText(document.getElementById('RGB'),getET(evt).bgColor.toUpperCase());
      EndColor();
    },
    'onmouseout': function(evt) {
      setText(document.getElementById('RGB'),SelRGB);
      EndColor();
    }
  },
  'GrayTable': {
    'onclick': function(evt) {
      SelGRAY = getET(evt).title;
      EndColor();
    },
    'onmouseover': function(evt) {
      setText(document.getElementById('GRAY'),getET(evt).title);
      EndColor();
    },
    'onmouseout': function(evt) {
      setText(document.getElementById('GRAY'),SelGRAY);
      EndColor();
    }
  },
  'Ok': {
    'onclick': function() {
      top.returnValue = SelColor.value;
      top.close();
    }
  },
  'cancel': {
    'onclick': function() {
      top.close();
    }
  }
}
window.onload = function() {
  for (var i in pool) {
    var p = pool[i];
    for (var j in p) {
      document.getElementById(i)[j] = p[j];
    }
  }
}
</SCRIPT>

</HEAD>
<BODY>
<div align="center"><center><table border="0" cellspacing="10" cellpadding="0"><tr><td>
<TABLE ID=ColorTable BORDER=0 CELLSPACING=0 CELLPADDING=0 style='cursor:pointer'>
<SCRIPT LANGUAGE=JavaScript>
function wc(r, g, b, n)
{
	r = ((r * 16 + r) * 3 * (15 - n) + 0x80 * n) / 15;
	g = ((g * 16 + g) * 3 * (15 - n) + 0x80 * n) / 15;
	b = ((b * 16 + b) * 3 * (15 - n) + 0x80 * n) / 15;

	document.write('<TD BGCOLOR=#' + ToHex(r) + ToHex(g) + ToHex(b) + ' height=8 width=8></TD>');
}

var cnum = new Array(1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0);

  for(i = 0; i < 16; i ++)
  {
     document.write('<TR>');
     for(j = 0; j < 30; j ++)
     {
     	n1 = j % 5;
     	n2 = Math.floor(j / 5) * 3;
     	n3 = n2 + 3;

     	wc((cnum[n3] * n1 + cnum[n2] * (5 - n1)),
     		(cnum[n3 + 1] * n1 + cnum[n2 + 1] * (5 - n1)),
     		(cnum[n3 + 2] * n1 + cnum[n2 + 2] * (5 - n1)), i);
     }

     document.writeln('</TR>');
  }
</SCRIPT>
</TABLE></td><td>
<TABLE ID=GrayTable BORDER=0 CELLSPACING=0 CELLPADDING=0 style='cursor:hand'>
<SCRIPT LANGUAGE=JavaScript>
  for(i = 255; i >= 0; i -= 8.5)
     document.write('<TR BGCOLOR=#' + ToHex(i) + ToHex(i) + ToHex(i) + '><TD TITLE=' + Math.floor(i * 16 / 17) + ' height=4 width=20></TD></TR>');
</SCRIPT>
</TABLE>
</td></tr>
</table>
</center></div>

<div align="center"><center>
<table border="0" cellspacing="10" cellpadding="0" width="100%">
<tr><td rowspan="2" align="center" width=70>
<table ID=ShowColor bgcolor="#000000" border="1" width="50" height="40" cellspacing="0" cellpadding="0">
<tr><td></td></tr>
</table>
</td>
<td rowspan="2"><bean:message key="selcolor.base.color" /><SPAN ID=RGB>#000000</SPAN><BR>
<bean:message key="selcolor.light" /><SPAN ID=GRAY>120</SPAN><BR>
<bean:message key="selcolor.code" /><INPUT TYPE=TEXT SIZE=7 ID=SelColor value="#000000"></td>
<td width=50><BUTTON id=Ok type=submit><bean:message key="selcolor.ok" /></BUTTON></td></tr>
<tr><td width=50><BUTTON id=cancel><bean:message key="selcolor.close" /></BUTTON></td></tr>
</table></center></div>

</BODY>   
</HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">Com_IncludeFile("treeview.js");</script>
<script type="text/javascript" src="./util/web/js/Calendar.js"></script>
<script type="text/javascript">
var LKSTree;
Tree_IncludeCSSFile();
//Com_Parameter.XMLDebug = true;
//获取本月日期

function getNowCalendar(){
	var nowCalendar = new Calendar();
	nowCalendar.setTime(new Date());
	return nowCalendar;

}

function getLastCalendar(){
	var lastCalendar = new Calendar();
	lastCalendar.setTime(new Date());
	lastCalendar.addMonth(-1);
	if(lastCalendar.isSmallMonth){
		lastCalendar.setDate(30);
	}else{
		lastCalendar.setDate(31);
	}
	
	return lastCalendar;
}

//将日期转为字符串如"2008-08-12"
function convertCalendarToDateStr(date,flag){
	//debugger;
	var year = date._date.getFullYear();
	var month = date.get(Calendar.MONTH) + 1;
	var day = date.get(Calendar.DAY_OF_MONTH);
	var month_str;
	var day_str;
	if(month < 10){
		month_str = "0"+month;
	}else{
		month_str = month;
	}
	if(day < 10){
		day_str = "0"+day;
	}else{
		day_str = day;
	}
	var local = "<%=UserUtil.getKMSSUser().getLocale() %>";
	if(local == "en_US"){
		switch(flag){
		case 0:
			return year;
		break;
		case 1:
			return month_str + "/" + year;
		break;
		case 3:
			return day_str + "/" + month_str + "/" + year;
		break;
		default:
			return day_str + "/" + month_str + "/" + year;
		};
	}else{
		switch(flag){
		case 0:
			return year;
		break;
		case 1:
			return year + "-" + month_str ;
		break;
		case 3:
			return year + "-" + month_str + "-" + day_str;
		break;
		default:
			return year + "-" + month_str + "-" + day_str;
		};
	}
}

function getAllDate(date,treeNode){	
	var month  = date.get(Calendar.MONTH);
	//debugger;
	var temp_month;
	do{	
	var date_str = convertCalendarToDateStr(date);	
	url = '<c:url value="/sys/sms/sys_sms_main/index.jsp?time="/>' +  date_str; 	
	treeNode.AppendURLChild(date_str,url);
	//debugger;
	date = date.addDate(-1);	
	temp_month =  date.get(Calendar.MONTH);
	}while(month == temp_month);
}
function generateTree()
{
	LKSTree = new TreeView("LKSTree", "<bean:message key='sysSmsMain.byTime' bundle='sys-sms'/>",
	 document.getElementById("treeDiv"));
	//LKSTree.isShowCheckBox=true;
	//LKSTree.isMultSel=true;
	LKSTree.isAutoSelectChildren = true;
	var nowDate =  getNowCalendar()._date.getFullYear() + "<bean:message key='sysSmsMain.date.year' bundle='sys-sms'/>" + (getNowCalendar().get(Calendar.MONTH) + 1) + "<bean:message key='sysSmsMain.date.month' bundle='sys-sms'/>" ;
	var lastDate = getLastCalendar()._date.getFullYear() + "<bean:message key='sysSmsMain.date.year' bundle='sys-sms'/>" + (getLastCalendar().get(Calendar.MONTH) + 1)+ "<bean:message key='sysSmsMain.date.month' bundle='sys-sms'/>" ;
	
	var n1, n2;
	n1 = LKSTree.treeRoot;	
	n2 = n1.AppendURLChild(nowDate,"<c:url value="/sys/sms/sys_sms_main/index.jsp?time="/>" +  convertCalendarToDateStr(getNowCalendar(),2));
	n2.isExpanded = true;
	getAllDate(getNowCalendar(),n2);
	n2 = n1.AppendURLChild(lastDate,"<c:url value="/sys/sms/sys_sms_main/index.jsp?time="/>" + convertCalendarToDateStr(getLastCalendar(),2));	
	getAllDate(getLastCalendar(),n2);
	LKSTree.Show();
}
<%@ include file="/resource/jsp/tree_down.jsp"%>

<%@page import="com.landray.kmss.sys.agenda.model.SysAgendaBaseConfig"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%
    String calendarMinuteStep = new SysAgendaBaseConfig().getCalendarMinuteStep();
    if(StringUtil.isNull(calendarMinuteStep)){
        calendarMinuteStep = "1";
    }

    //时间管理设置分钟步长，分钟选择列表 #170616
    int calendarMinuteStepInt = Integer.valueOf(calendarMinuteStep);
    List<String> calendarMinuteList = new ArrayList<>(60/calendarMinuteStepInt);
    for(int cm = 0 ;;){
        if (cm < 60){
            // cm < 10 ? "0" + cm : String.valueOf(cm)
            // km/calendar页面原先不补足0
            calendarMinuteList.add(String.valueOf(cm));
            cm += calendarMinuteStepInt;
        } else if (cm >= 60){
            break;
        }
    }
    request.setAttribute("calendarMinuteList",calendarMinuteList);
%>

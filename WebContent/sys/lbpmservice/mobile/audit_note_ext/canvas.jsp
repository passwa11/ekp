<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>	
<%@ include file="/sys/ui/jsp/common.jsp"%>
		<ul class="tab_header auditNoteHandlerHeader">
               <li>
                  	<%-- <div data-dojo-type="mui/form/Select" class="canvas_selectPenWidth"
						data-dojo-props="subject:'<bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage.handwrite.penWidth" />',name:'selectPenWidth',mul:false,value:'2',
						store:[{value:'1',text:'1'},{value:'2',text:'2'},{value:'3',text:'3'},{value:'4',text:'4'},
						{value:'5',text:'5'},{value:'6',text:'6'},{value:'7',text:'7'},{value:'8',text:'8'},
						{value:'9',text:'9'},{value:'10',text:'10'},{value:'11',text:'11'},{value:'12',text:'12'},
						{value:'13',text:'13'},{value:'14',text:'14'},{value:'15',text:'15'}]">
					</div> --%>
					
					<div 
						data-dojo-type="sys/lbpmservice/mobile/audit_note_ext/hand/PenSelect" 
						data-dojo-props="label:'<bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage.handwrite.penWidth" />',
						name:'selectPenWidth',value:2,store:[{value:2},{value:6},{value:10},{value:14}]"
						class="muiPenWidthSelect">
					</div>
					
               </li>
               <li>
                	<%-- <div data-dojo-type="mui/form/Select" class="canvas_selectPenColor"
						data-dojo-props="subject:'<bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage.handwrite.color" />',name:'selectPenColor',mul:false,value:'black',
						store:[{value:'black',text:'<bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage.handwrite.color.black" />'},{value:'red',text:'<bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage.handwrite.color.red" />'},{value:'blue',text:'<bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage.handwrite.color.blue" />'}]">
					</div> --%>
					
					<div 
						data-dojo-type="sys/lbpmservice/mobile/audit_note_ext/hand/PenSelect" 
						data-dojo-props="label:'<bean:message bundle="sys-lbpmservice" key="mui.lbpmNode.usage.handwrite.color" />',
						name:'selectPenColor',value:'#000',store:[{value:'#D0021B'},{value:'#4285f4'},{value:'#000'}]"
						class="muiPenCorloSelect">
					</div>
					
            	</li>
		</ul>
        <div class="popup_floatLayer_content canvasDiv"></div>
        <div data-dojo-type="mui/tabbar/TabBar" class="auditNoteHandlerToolbar muiTabBarBottomAdaptive" fixed="bottom" data-dojo-props='fill:"grid"'>
			<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnDefault auditNoteHandlerCancel">
				<bean:message  key="button.cancel" /></li>
			<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit auditNoteHandlerSave" 
			data-dojo-props='colSize:2'>
				<bean:message  key="button.ok" /></li>
			<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnDefault auditNoteHandlerClear">
				<bean:message  key="button.clear" /></li>
		</div>

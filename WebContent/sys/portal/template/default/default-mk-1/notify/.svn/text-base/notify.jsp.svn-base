<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>

<div class="lui_dropdown_toggle">
	<i class="lui_single_menu_header_icon lui_icon_l_icon_notify"></i>
	<span class="com_prompt_num" style="display:none;"></span>
</div>
<ui:popup align="down-left" borderWidth="2" style="overflow-y:hidden;" >
	<div class="lui_tlayout_header_notify_popup">
		<ui:tabpanel height="240" scroll="false" layout="sys.ui.tabpanel.default">
			<portal:portlet title="${ lfn:message('sys-notify:sysNotifyTodo.tab.title1') }" var-fdAppName="" var-sortType="datetime" var-fdType="13" var-rowSize="8">
				<ui:dataview format="sys.ui.iframe">
					<ui:source type="Static" >
						{"src":'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=list&source=header&home=1&fdType=13&_oldFdType=13&rowsize=8&isShowBtLable=0&LUIID=!{lui.element.id}&sortType=datetime&fdAppName=',
						"counturl":'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=getCount&fdType=todo&fdAppName='}
					</ui:source>
					<ui:render ref="sys.ui.iframe.default" var-frameName=""></ui:render>
				</ui:dataview>
				<ui:operation href="javascript:(function(){ var url = '${LUI_ContextPath }/sys/notify/index.jsp#j_path=%2Fprocess&amp;dataType=todo'; window.open(url);})();" name="{operation.more}" target="_self" type="more" align="right"></ui:operation>
			</portal:portlet>
			<portal:portlet title="${ lfn:message('sys-notify:sysNotifyTodo.tab.title2') }" var-fdAppName="" var-sortType="datetime" var-rowSize="8">
				<ui:dataview format="sys.ui.iframe">
					<ui:source type="Static" >
						{"src":'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=list&home=1&source=header&rowsize=8&isShowBtLable=0&fdType=2&LUIID=!{lui.element.id}&sortType=datetime&fdAppName=',
						"counturl":'/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=getCount&fdType=toview&fdAppName='}
					</ui:source>
					<ui:render ref="sys.ui.iframe.default" var-frameName=""></ui:render>
				</ui:dataview>
				<ui:operation href="javascript:(function(){var url = '${LUI_ContextPath }/sys/notify/index.jsp#j_path=%2Fread&amp;dataType=toview'; window.open(url);})()" name="{operation.more}" target="_self" type="more" align="right"></ui:operation>
			</portal:portlet>
		</ui:tabpanel>
	</div>
	<ui:event event="show" args="args">
		var frames = this.element.find('iframe');
		if(frames && frames.length > 0){
		for(var i=0;i<frames.length;i++){
		var win = frames[i].contentWindow;
		if(win && win.document.body)
		$(win && win.document.body).empty();
		var src = $(frames[i]).attr('src');
		$(frames[i]).attr('src', src);
		}
		}
	</ui:event>
</ui:popup>
<script>

	seajs.use(['lui/topic'], function(topic) {
		LUI.ready(function(){
			var refreshTime = parseInt(${ param['refreshTime'] });
			if(isNaN(refreshTime) || refreshTime<1){
				refreshTime = 0;
			}
			var refreshNotify = function(){
				LUI.$.getJSON(Com_Parameter.ContextPath + "sys/notify/sys_notify_todo/sysNotifyTodo.do?method=sumTodoCount",function(json){
					if(json!=null){
						var count1 = json.type_1==null?0:parseInt(json.type_1);
						var count2 = json.type_2==null?0:parseInt(json.type_2);
						var $num = LUI.$(".lui_single_menu_header_notify .lui_dropdown_toggle .com_prompt_num");
						if(count1 > 0){
							var txt = count1;
							if(count1>=100){
								txt = "99+";
							}
							$num.html(""+(txt)+"").removeClass('comPromptNum').show();
						}else{
							$num.html("");
							if(count2 > 0){
								$num.addClass('comPromptNum').show();
							}else{
								$num.removeClass('comPromptNum').hide();
							}
						}
					}
				});
				if(refreshTime>0)
					window.setTimeout(refreshNotify,refreshTime*1000*60);
			};
			refreshNotify();
			topic.subscribe('portal.notify.refresh',function(){
				refreshNotify();
			});
		});
	});
</script>
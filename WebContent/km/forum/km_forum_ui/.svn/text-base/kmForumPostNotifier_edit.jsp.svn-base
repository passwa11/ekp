<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<!-- bcs 定制，该页面全部是定制内容@功能，因为新建论坛和回帖时都需要这个功能，所以把这个页面抽出来作为公共的页面。 -->
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/dialog.js"></script>
<script type="text/javascript">
seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
	window.afterAddressSelect = function(rtnData){
		/*  
		var handlers = rtnData.GetHashMapArray();
		for(var i=0; i<handlers.length; i++){
			var handler = handlers[i];
			var name = handler.name;
			console.log(name);
		}
		*/
		$("#notifierDisplayNames").text($("input[name='fdPostNotifierNames']").val());
		$("#notifierDisplayNames").attr("title",$("input[name='fdPostNotifierNames']").val());
	};
  });
</script>
<xform:text property="fdPostNotifierIds" showStatus="noShow"/>
<xform:text property="fdPostNotifierNames" showStatus="noShow"/>
<div style="float:left;">
	<a href="javascript:;" style="color:red;" onclick="Dialog_Address(true,'fdPostNotifierIds','fdPostNotifierNames',';',ORG_TYPE_PERSON,afterAddressSelect,null,null,null,null,null,null)">
	${lfn:message("km-forum:kmForumPost.fdPostNotifier") }
	</a>
</div>
<div style="float:left; padding-left:15px;width:600px">
	<span id="notifierDisplayNames" style="overflow: hidden;text-overflow: ellipsis; -o-text-overflow: ellipsis;white-space:nowrap;display:block;" title="${kmForumPostForm.fdPostNotifierNames}">${kmForumPostForm.fdPostNotifierNames}</span>
</div>
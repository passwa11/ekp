<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<head>
	<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/xform/designer/relevance/css/relevance_dialog.css">
</head>
<script>
	//删除文档
	function deleteLi(selectedData,docId){
		var selectboxUlDom = document.getElementById('relevance_dialog_selectbox_ul');
		//如果docId为空，则全部删除
		if(docId){
			var $li;
			//如果doc是字符串，则doc是需要删除文档的id，如果不是，则为被删除文档的当前对象
			if(typeof(docId) == 'string'){
				
				$li = $(selectboxUlDom).find("li[data-relevance-dialog-selectedid='"+docId+"']");
			}else{
				$li = $(docId).closest('li');
				docId = $li.data('relevance-dialog-selectedid');
			}
			if($li){
				$li.remove();	
			}
			//删除存储的文档
			for(var i = 0 ; i < selectedData.length; i++){
				if(selectedData[i].docId == docId){
					selectedData.splice(i, 1);
					break;
				}
			}
		}else{
			$(selectboxUlDom).empty();
			selectedData = [];
		}
		initDocNum(selectedData.length);
		return selectedData;
	}

	//增加文档
	function addLi(selectedData){
		var selectboxUlDom = document.getElementById('relevance_dialog_selectbox_ul');
		var subject = stringEscape(selectedData.subject);
		var message = "${lfn:message('button.delete')}";
		var html = "<li data-relevance-dialog-selectedid='"+selectedData.docId+"'>"
		+"<span title="+subject+">"+subject+"</span>"
		+"<div class='relevance_dialog_selectedbox_del' onclick='deleteLi(parent.getSelectedData(),this);'>"+message+"</div>"
		+"</li>";
		$(selectboxUlDom).append(html);
	}

	//显示总文档数
	function initDocNum(length){
		var tipDom = document.getElementById('numTip');
		tipDom.innerHTML = ' ( ' + length + ' )';
	}
	
	function stringEscape(str){
		 var s = "";
	    if (str.length == 0) return "";
	    for (var i = 0; i < str.length; i++) {
	        switch (str.substr(i, 1)) {
	            case "<": s += "&lt;"; break;
	            case ">": s += "&gt;"; break;
	            case "&": s += "&amp;"; break;
	            case " ": s += "&nbsp;"; break;
	            case "\"": s += "&quot;"; break;
	            default: s += str.substr(i, 1); break;
	        }
	    }
	    return s;
	}
</script>

<body>
	<br/>
	<div>
		<span><bean:message bundle="sys-xform" key="sysFormMain.relevance.selectedDocument"/></span><span style='color: red;' id='numTip'></span>
		<ul id="relevance_dialog_selectbox_ul" class="relevance_dialog_selectbox_ul">
		</ul>
	</div>
</body>
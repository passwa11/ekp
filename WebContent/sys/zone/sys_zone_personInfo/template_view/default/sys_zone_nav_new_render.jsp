<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	var element = render.parent.element;
	var taText = '<c:out value="${zone_TA_text }" />';
	var length  =  data.length;
	var lenOut,lenIn;
	if(length <= 6) {
		lenOut = length;
		lenIn = 0;
	} else if(length >= 7) {
		lenOut = 6;
		lenIn = length - 6;
	}
	for(var i = 0; i < length; i ++) {
		var text = data[i].text;
		if(text.indexOf("TA")>-1){
			text = "${lfn:message(lfn:concat('sys-zone:zone.ta.text.', zone_TA))}"+text.substring(2);
		}
		var info = ["&id=", data[i].id ,"&text=", $.trim(text), 
						"&href=", data[i].href, "&serverPath=", data[i].serverPath,
							 "&target=",data[i].target,"&key=", data[i].key].join("");
		var infoHref = data[i].href;
		var infoText = " data-info='" + info + "'";
		var href = "javascript:;";
		var targrText = "";
		if("_blank" == data[i].target) {
			href = __navLinkUrl(data[i].href, data[i].serverPath, data[i].key);
			targrText = "  target='_blank'";
			infoText = "";
		}
		var ele = $('<li style="display: inline-block;"><a ' + 'class="com_bordercolor_d"'+targrText +' title="'+ text +'"  infoHref="'+ infoHref +'" href="' + href +'" '+ infoText +' data-nav-out="'+ i +'" >'
					+ text +'</a></li>');
		element.append(ele);
		element.append("<li class='line'/>");
	}

	done();
	
	var cId = render.parent.cid;
	
	var exchangeInfo =  function($t0, $tt) {
			var info0 = $t0.attr("data-info");
			var infoT = $tt.attr("data-info");
			var text0 = $t0.text();
			var textT = $tt.text();
			$t0.attr("data-info", infoT);
			$t0.text(textT);
			$t0.attr("title", textT);
			$tt.attr("data-info", info0);
			$tt.text(text0);
			$tt.attr("title", text0);
	};
	$("#" + cId).on("click", function(e) {
		var $target = $(e.target);
		var info = $(e.target).attr("data-info");
		var infoHref = $(e.target).attr("infoHref");
		if(!info) {
			return;
		}
		var target = Com_GetUrlParameter(info, "target");
		
		if("_blank" == target) {
			return;
		}
			var href = infoHref,
			serverPath = Com_GetUrlParameter(info,"serverPath"),
			key = Com_GetUrlParameter(info,"key"),
			text = Com_GetUrlParameter(info, "text");	
		$("[data-info]").removeClass("com_btn_link ");
		var indexIn = $target.attr("data-nav-in");
		if(indexIn) {
			var $target0 = $("[data-nav-out][data-info]:eq(0)");
			exchangeInfo($target0,$target);
			$target0.addClass("com_btn_link ");
		} else {
			 $target.addClass("com_btn_link ");
		}
	
		$("#iframe_body").attr("src", __navLinkUrl(href, serverPath, key));
	});
	$("[data-nav-out][data-info]:eq(0)").trigger("click");
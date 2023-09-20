<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<script>
	Com_IncludeFile("ckresize.js", Com_Parameter.ContextPath + "resource/ckeditor/", "js", true);
</script>
<script>
seajs.use(['lui/jquery'],function($) {
	$(function(){
			CKResize.addPropertyName('${sysHelpMainForm.fdId }');
			CKResize.____ckresize____(true);
		}
	);
});
</script>
<script>
	function publishMain(fdId){
		seajs.use(['lui/jquery', 'lui/dialog', 'lui/util/env'], function($, dialog, env){
			dialog.confirm("${lfn:message('sys-help:sysHelpMain.isPublish')}", function(flag, d){
				if(flag){
					var loading = dialog.loading();
					var url = env.fn.formatUrl('/sys/help/sys_help_main/sysHelpMain.do?method=updateToPublish&fdId='+fdId);
					$.post(url,null,function(data){
						if(data.indexOf('success') > -1){
							dialog.success("${lfn:message('sys-help:syshelpMain.publish.success')}", '');
							setTimeout(function(){window.location.reload();}, 500);	
						}else {
							loading.hide();
	                		dialog.alert("${lfn:message('sys-help:syshelpMain.publish.error')}");
						}
					});
				}
			});
		});
	}
</script>
<script>
//目录跳转
function catelogScroll(targetId) {
	var targetDom = $('#sysHelpMain_view_fdName_td_'+targetId);
	if(targetDom){
		var height = targetDom.offset().top - 45;
		var css = {scrollTop : height + 'px'};
		$("html,body").animate(css, 300, null);
	}
}
</script>

<script type="text/javascript">
seajs.use(['lui/view/Template', 'lui/jquery'],function(Template, $) {
	// 目录数据
	var sysHelp_catalogArray = [];
	
	$(function() {
		// 组装目录数据
		querySysHelpMainCatalog();
	});
	
	
	// 隐藏域中各种数据的下标
    var fdIdHiddenIndex = 0;
    var fdNameHiddenIndex = 1;
    var fdOrderHiddenIndex = 2;
    var fdLevelHiddenIndex = 3;
    var fdCateIndexHiddenIndex = 4;
	// 读取隐藏域的信息，组装目录数据
	function querySysHelpMainCatalog(){
        var cateHiddens = $('.sysHelpMain_cate_hidden');
        if (cateHiddens.length == 0){
        	setTimeout(function(){querySysHelpMainCatalog();},300);
        	return;
        }
        var cate_0 = $(cateHiddens[0]).children();
        var fdid_0 = $(cate_0[fdIdHiddenIndex]).val();
        if (!fdid_0){
        	setTimeout(function(){querySysHelpMainCatalog();},300);
        	return;
        }
		$('.sysHelpMain_cate_hidden').each(function (index, value) {
			// 获取目录信息
			var oneCate = $(this).children();
			var oneFdId = $(oneCate[fdIdHiddenIndex]).val();
			var oneFdName = $(oneCate[fdNameHiddenIndex]).val();
			var oneFdOrder = $(oneCate[fdOrderHiddenIndex]).val();
			var oneFdLevel = $(oneCate[fdLevelHiddenIndex]).val();
			var oneFdCateIndex = $(oneCate[fdCateIndexHiddenIndex]).val();
			var oneFdStyle = geneStyle(oneFdLevel);
			// 设置最高层级
			setSysHelpMainMaxLevel(oneFdLevel);
			// 添加锚链接
			insertTarget(oneFdId, oneFdOrder);
			// 组装目录数据
			var oneCateObj = {
				catalog_title : $.trim(oneFdName),
				catalog_fdId : oneFdId,
				catalog_fdOrder : oneFdOrder,
				catalog_fdLevel : oneFdLevel,
				catalog_fdCateIndex : oneFdCateIndex,
				catalog_fdStyle : oneFdStyle,
			}
			sysHelp_catalogArray.push(oneCateObj);
		});
		
        //计算并渲染上面目录
        //calalogCal();
        //渲染下面目录
        drawCatalogBottom();
        
		catelogScrollWindow();

	}
	
	function geneStyle(oneFdLevel){
    	var thisLevel = oneFdLevel * 1;
    	thisLevel = (thisLevel > 9) ? 9 : thisLevel;
    	thisLevel = 5 + (thisLevel - 1) * 10;
    	var thisStyle = " padding: 3px 0 3px " + thisLevel+ "px;";
    	return thisStyle;
    }
	
	// 最深层级
    var sysHelpMainMaxLevel = 1;
    // 计算最深层级，最高9级
    function setSysHelpMainMaxLevel(tempLevel){
    	sysHelpMainMaxLevel = (tempLevel > sysHelpMainMaxLevel) ? tempLevel : sysHelpMainMaxLevel;
    	sysHelpMainMaxLevel = (sysHelpMainMaxLevel > 9) ? 9 : sysHelpMainMaxLevel;
    }
	
    function insertTarget(oneFdId, oneFdOrder) {
        $("#sysHelpMain_view_fdName_" + oneFdId).prepend('<span class="lui_sys_help_c_node" id="catalog_' + oneFdOrder +'"></span>');
    }
    
    function calalogCal() {
		var length = sysHelp_catalogArray.length,
			catalog_div = LUI.$("#sys_help_catalog_content"),
			html = "";
		if(length > 0) {
			var rowNum = 0;
			if( length > 20 ) {
				 rowNum = (sysHelpMainMaxLevel > 5) ? 1 : ((sysHelpMainMaxLevel > 3) ? 2 : 3);
			} else if (length <= 20 && length > 10) {
				// 超过5层就只用一层
				rowNum = (sysHelpMainMaxLevel > 5) ? 1 : 2;
			} else if(length <=10 && length > 0) {
				rowNum = 1;
			}
			if(rowNum > 0) {
				html = new Template($('#sysHelp_catalog_template').html()).render({
						rowNum : rowNum,
						catalogArray : sysHelp_catalogArray
					}
				);
				$("#lui-sysHelp-catalog").show();
				catalog_div.append(html);
				catalog_div.on("click",scorllTo);
				bindScroll();
			}
		}
	}
    
    function scorllTo(event) {
		var _id = LUI.$(event.target).attr("data-catalog-id") || LUI.$(event.target).attr("data-bottom-catalog-id");
		if(_id){
			catelogScroll("catalog_" + _id);
		}
	}
    
  	
	//绑定滚动事件
	function bindScroll() {
		$(window).scroll(function() {
			queryCatalogTarget();
			$(".lui_catalog_bottom_select_item").removeClass("lui_catalog_bottom_select_item");
			var sId = catalogIndex(catalogTargetArray,$(document).scrollTop() + 68);
			if(sId != -1)
				$('.lui_catalog_bottom_common').find("[data-bottom-catalog-id=" + sId + "]").each(
					function() {
						$(this).parent().addClass("lui_catalog_bottom_select_item");
						setCatalogPosition();
					}
				);
		});
	}
	
	//确定当前滚动的目录范围的下限id 参数scorllHight为滚动条卷起的高度
	function catalogIndex(cArray , scorllHight) {
		if(scorllHight < cArray[0].top) return -1;
		var low = 0,
		     high = cArray.length - 1;
		while(low <= high) {
			var middle = parseInt((low + high) / 2);
			if(scorllHight == cArray[middle].top) {
				return cArray[middle].id;
			}
			else if(scorllHight > cArray[middle].top){
				low = middle + 1;
			}
			if(scorllHight < cArray[middle].top) {
				high = middle - 1;
			}
		}
		return cArray[low - 1 ].id ;
	}
	
	//封装锚点高度对象集
	var catalogTargetArray = [];
	function queryCatalogTarget() {
		$('.lui_sys_help_c_node').each(function() {
			var self = $(this);
			catalogTargetArray.push({
				top : self.next().offset().top,
				id : (function(){
					var id = self.attr("id");
					return id.substring(id.indexOf("_") + 1);
				})()
			});
		});
	}
    
	//渲染底下目录
	function drawCatalogBottom() {
		var html = "";
		var _html_no = "";
		for(var i = 0 ; i< sysHelp_catalogArray.length; i++) {
			_html_no = "";
			var thisLevel = sysHelp_catalogArray[i].catalog_fdLevel;
			if(thisLevel == 1) {
				_html_no = sysHelp_catalogArray[i].catalog_fdCateIndex + "&nbsp;&nbsp;";
			} else {
				_html_no = sysHelp_catalogArray[i].catalog_fdCateIndex + "&nbsp;";
			}
			html += [ "<li class='lui_catalog_bottom_common lui_catalog_bottom_" + thisLevel + "'>" ,
						"<span class=\"lui_catalog_bottom_select\"></span>",
						"<a href=\"javascript:;\"  data-bottom-catalog-id=\"" +  sysHelp_catalogArray[i].catalog_fdOrder + "\">",
						_html_no + sysHelp_catalogArray[i].catalog_title ,"</a></li>"].join(' ');
		}
		$('#catalog_ul_bottom').append(html);
		$('#catelog_bottom').on('click', scorllTo);
		var maxWidth = 12 + (10 * sysHelpMainMaxLevel + 5) + (10 * sysHelpMainMaxLevel) + 140;
		maxWidth = maxWidth > 305 ? 305 : maxWidth;
		$("#catelog_bottom").width(maxWidth);
		$("#sys_help_catelog_bar").width(maxWidth);
		$("#sys_help_catelog_side").width(maxWidth);
	}
	
	//根据第一段落标题的高度来控制目录的出现
	var catelogflag = false;
	function catelogScrollWindow() {
		LUI.$(window).scroll( function() { 
			if(document.getElementById("catalog_1")) {
				var _top = LUI.$('#catalog_1').offset().top;
				if(LUI.$(document).scrollTop() >=  (_top -12)) {
					if(catelogflag == false) {
						setPos();
						LUI.$("#catelog_bottom").show();
						updownShow();
						catelogflag = true;
					}	
				}else {
					LUI.$("#catelog_bottom").hide();
					catelogflag = false;
				}
			}
		});
	}
	
	//设置目录位置
	function setPos() {
	    var _bottom = parseInt(LUI.$("#top").css('bottom').replace(/[A-Za-z]/g, "")) + LUI.$("#top").height();
	    LUI.$("#catelog_bottom").css({
	        position:'fixed',
	        bottom: 180,
	        right: 69
	    });
	}
	
	//上下按钮的出现与否
	function updownShow() {
	    //目录总高度
	    var height = LUI.$('#sysHelp_catelog_side').height();
	    //目录外层高度
	    var b_height = LUI.$('.lui_sys_help_catelog_bar').height();
	    if(height <= b_height) {
	            LUI.$('.lui_sys_help_catelog_btnTop').hide();
	            LUI.$('.lui_sys_help_catelog_btnDown').hide();
	    } 
	}
	
});
</script>

<script type="text/template" id="sysHelp_catalog_template">
	var _rowNum = rowNum,
		_catalogArray = catalogArray,
		_length = catalogArray.length,
		_maxCol = Math.ceil(_length  / _rowNum),
		i = 0, 
		j = 0;

	{$
		<div class="clearfloat">
	$}
	for(var r = 0; r < rowNum; r++) {
		{$
			<div class="sys_help{%_rowNum%} lui_sys_help_catalog_row_common ">	
				<div>
					<ul>
		$}
					for(i = r * _maxCol, j = 0; i < _length && j < _maxCol; i++,j++) {
							if(_catalogArray[i].catalog_fdLevel == 1 ) {
					            {$ <li class="lui_sys_help_catalog_1"> $}
								{$<p class="lui_catalog_i1" style="padding-left: 0px;"><a data-catalog-id ="{%_catalogArray[i].catalog_fdOrder%}"
                                 href="javascript:;" title="{% _catalogArray[i].catalog_title%}" style="color:#15a4fa;"> 
									{% _catalogArray[i].catalog_fdCateIndex %}&nbsp;&nbsp;&nbsp;{% _catalogArray[i].catalog_title%} </a></p> $}
							} 
							else{
					            {$ <li class="lui_sys_help_catalog_1" style="{% _catalogArray[i].catalog_fdStyle %}" > $}
								{$<span class="lui_sys_help_catalog_index_dot"></span> $}
                                 {$ <a data-catalog-id ="{%_catalogArray[i].catalog_fdOrder%}"  href="javascript:;" title="{% _catalogArray[i].catalog_title%}" style="color:#333;">
                                        {% _catalogArray[i].catalog_fdCateIndex %}&nbsp;{% _catalogArray[i].catalog_title%}   
                                    </a>
                                  $}
							}	
						 {$ </li>$}
					}
		{$
					</ul>
				</div>
			</div>
		$}
	}

	{$
		</div>
	$}
</script>

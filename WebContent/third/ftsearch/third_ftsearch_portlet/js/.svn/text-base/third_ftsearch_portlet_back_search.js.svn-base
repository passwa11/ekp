//设置部件内部dom高度
var heig = render.parent.element.parent().height();
var dom = $("<div class='third_ftsearch_portlet_back_search_html' style='overflow-x: auto;height:"+heig+"px;'>"+data.html+"</div>");

//设置默认背景图
dom.find("#third_ftsearch_portlet-bgImg").attr("src", env.config.contextPath+"/third/ftsearch/resource/images/third_ftsearch_portlet_back_search_bg.png");

// 设置自定义背景图
var img = render.vars.img;
if(img!=""&&img!=null){
	img=img.replace(/amp;/g,"");
	dom.find("#third_ftsearch_portlet-bgImg").attr("src", env.config.contextPath+img);
}

//渲染dom
done(dom); 

//设置是否展示热词
if(render.vars.showHot=="true"){
	dom.find(".third_ftsearch_portlet_search_hot_div").css("display","block");
	getThirdFtsearchPortletSearchHot(dom);
}

//请求热词数据
function getThirdFtsearchPortletSearchHot(dom) {
	seajs.use(['lui/util/env'], function(env){
		$.ajax({
            url: env.fn.formatUrl("/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do?method=hot"),
            dataType: 'json',
            type: 'POST',
            success: function(data) {
                buildSearchHotBox(data,dom)
            },
            error: function(req) {
                
            }
        });
	})
}

//构建热词dom
function buildSearchHotBox(data,dom){
	var ul=$("<ul></ul>");
	ul.append("<li><span class='third_ftsearch_portlet_search_hot_icon'></span><span>热搜词:</span></li>");
	dom.find(".third_ftsearch_portlet_search_hot_div").append(ul);
	for(var i=0;i<data.length;i++){
		var index=i+1;
		var li=$("<li style='cursor: pointer;' onclick='thirdFtsearchPortletBackHotSearch(\""+data[i]+"\")'></li>");
		var hotIndexSpan="<span class='hotIndexSpan"+index+"'>"+index+".</span>";
		var hotSpan="<span class='hotSpan'>"+data[i]+"<span>"
		li.append(hotIndexSpan);
		li.append(hotSpan);
		ul.append(li);
	}
}
var now = new Date();
var locale = env.config.locale || 'zh-cn';
locale = locale.toLowerCase();
var element = render.parent.element;

var dataType = 1;
var ggg = [{name:"aaa",content:[{text:"tttt",href:"",children:[{text:"tttt",href:""},{text:"ddd",href:"bbb"}]}]}];
var iiii = [{text:"aaa",href:""}];

var html = (function(){
	return createMapNav();	
})();

/*地图模板dom*/
function createMapNav(){
	//create body Div
	var bodyDiv = $("<div class='mot-box'></div>")
	//添加背景dom
	createBgDom().appendTo(bodyDiv);
	
	createContent(datas).appendTo(bodyDiv);
	return bodyDiv;	
}

function createBgDom(){
	var bgDiv = $("<div class='mot-bg-picgroup'></div>");
	
	for(var i = 1;i<=5;i++){
		var spanCloud = $("<div span class='mot-bg-cloud'></div>");
		spanCloud.addClass("cloud-"+i);
		spanCloud.appendTo(bgDiv);
	}
	$("<span class='mot-bg-tree tree-1'></span><span class='mot-bg-tree tree-1'></span>").appendTo(bgDiv);
	$("<div class='mot-bg-mountain'></div>").appendTo(bgDiv);
	return bgDiv;
}

function createInterfaceDom(data){
	var interfaceDom = $("<ul class='mot-interface-list'></ul>");
	for(var i=0;i<data.length;i++){
		var str = "<li><a href='"+data.href+"'target='blank'>"+data.text+"</a></li>";
		$(str).appendTo(interfaceDom);
	}
	return interfaceDom;
}

function createContent(data){

	var content = $("<div class='mot-tab-content'></div>");
	for(var i = 0;i<data.length;i++){
		var oLi = $("<div class='mot-tab-pane active'></div>");
		oLi.addClass("mot-section-"+(i+1));
		oLi.appendTo(content);
		var dl = $("<dl class='mot-list mot-bg-yellow'></li>");
		dl.addClass("mot-list-"+i);
		var dt = $("<dt></dt>");
		dt.html(data[i].text);
		dl.appendTo(oLi);
		dt.appendTo(dl);
		for(var j = 0;j<data[i].children.length;j++){
			$("<dd><a href='"+data[i].children[j].href+"'>"+data[i].children[j].text+"</a></dd>").appendTo(dl);
		}
	}
	return content;
}
//农场导航
function createFramNav(){
	var bodyDiv = $("<div class='bmp-mapnav'></div>");
	$("<div class='bmp-bg-box'></div>");
	return bodyDiv;
}

function createAsideNav(data){
	var asideNavDom = $("<ul class='b'></ul>");
	var len = data.length>4?4:data.length;
	for(var i = 0;i<len;i++){
		$("<li><a class='bmp-btn bmp-btn-default' href='"+data[i].url+"'></a></li>").appendTo(asideNavDom);
	}
	if(len>4){
		var oLI = $("<li class='bmp-dropdown'><span class='bmp-btn bmp-btn-default bmp-dropdown-toggle' href='#' title='更多'>更多 <i>+</i></span></li>");
		oLi.appendTo(asideNavDom);
		var oBmpBtn = $("<div class='bmp-dropdown-meun'></div>");
		for(var j =i;j<data.length;j++){
			$("<a href='"+data[j].herf+"'>"+data[j].text+"</a>");
		}
	}
}

function bmpTabs(data){
	var bmpTabsDom = $("<div class='bmp-tab-heading'><ul class='bmp-tab-heading'></ul></div>");
	for(var i = 0;i<data.length;i++){
		var oLi = $("<li><a class='bmp-btn bmp-btn-default' href='javascript:void(0)'></a>"+data[i].text+"</li>");
		if(i===0){
			oLi.addClass("active");
		}
		oLi.appendTo(bmpTabsDom);
	}
	return bmpTabsDom;
}

function createBmpContent(data){
	var bmpContent = $("<div class='bmp-tab-content'></div>");
	for(var i = 0;i<data.length;i++){
		var bmPost =$("<div class='bmp-post'></div>");
		bmPost.addClass('bmp-post-'+(i+1));
		bmPost.appendTo(bmpContent);
		$("<h2 class='bmp-post-title'><span>"+data[i].text+"</span></h2>");
		$("<div class='bmp-post-content'></div>");
	}
}

done(html);


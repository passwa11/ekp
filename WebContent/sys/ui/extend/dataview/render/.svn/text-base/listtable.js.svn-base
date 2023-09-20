var datas = data.datas;
var columns = data.columns;
var hiddenColumns = [];
var showTableTitle = render.vars.showTableTitle;

var contextPath = env.config.contextPath;
if (contextPath.substring(contextPath.length -1 ) != '/') {
	contextPath += "/";
}
// URL格式化
function __formatUrl(url) {
	var href = "";
	// 判断contextPath是否是绝对路径（从子系统过来的请求，contextPath为子系统的绝对域名）
	if(contextPath.indexOf("http://") > -1 || contextPath.indexOf("https://") > -1) {
		// 如果是http(或https)打头，需要对原URL进行去除项目名称
		var urls = url.split("/");
		// 如果原URL包含有项目名称，需要去除
		if(contextPath.indexOf("/"+urls[1]+"/") > -1){
			href = contextPath + url.substring(urls[1].length + 2);
		} else {
			// 原URL没有项目名，直接拼接子系统域名
			if (url.substring(0, 1) == '/') {
				// 上面的contextPath加了/结束，这里就不需要以/开始了
				url = url.substring(1);
			}
			href = contextPath + url;
		}
	} else {
		if(url.indexOf(contextPath) == -1) {
			// 如果不是子系统过来的数据，直接按本系统的路径处理
			href = env.fn.formatUrl(url) ;
		} else {
			href = url;
		}
	}
	return href;
}
{$
	<table width="100%" class="lui_dataview_listtable_table">
$}
	if(!datas || ! columns)
		return;
	if(showTableTitle == "true"){
{$
		<thead>
$}
		for (var i = 0; i < columns.length; i ++) {
			var col = columns[i];
			if(col.title==null || $.trim(col.title) == ""){
				hiddenColumns.push(i);
				continue;
			}
			if(i==columns.length-1){
				{$<th style='border-bottom:0;{%col.headerStyle%};background-color: #F0F0F0;text-align:center;' class='{%col.headerClass%}'>{%col.title%}</th>$}
			}else{
			{$<th style='border-bottom:0;{%col.headerStyle%};background-color: #F0F0F0;border-right: 2px solid #e0e0e0;text-align:center;' class='{%col.headerClass%}'>{%col.title%}</th>$}
			}
		}
{$		
		</thead>
$}	
	} else {
		for (var i = 0; i < columns.length; i ++) {
			var col = columns[i];
			if(col.title==null || $.trim(col.title) == ""){
				hiddenColumns.push(i);
				continue;
			}
		}
	}
{$
		<tbody>
$} 
		for (var i = 0; i < datas.length; i ++) {
			var row = datas[i];
			var href = '';
			var target = '_blank';
			for(var k = 0; k < row.length; k ++){
				if(row[k]['col']=='href') {
					href = __formatUrl(row[k]['value']);
				}
				if(row[k]['col']=='target')
					target = row[k]['value'];
			}	
			{$<tr>$}
            var firstTd = true;
			for (var j = 0; j < row.length; j ++) {
				if($.inArray(j,hiddenColumns) > -1 || row[j]['col']=='href'|| row[j]['col']=='target'){
					continue;
				}
				var cell = row[j];
				var cValue = cell.value;
				// 处理XSS攻击，将标题中含有攻击性的字符进行处理
				// 判断是否有title属性
				if(null != cValue && cValue.indexOf("title=") > -1) {
					var title = cValue.match(/title="(.*)"/);
					if(null == title){
						title = cValue.match(/title='(.*)'/);
					}
					if(null != title && title.length > 0) {
						// 按=号分隔
						var __val = title[1].split("=");
						// 取出title里的属性值
						var val = __val[0];
						// 如果分隔的数据有其它属性，这里还需要处理
						if(__val.length > 1) {
							val = __val[0].substr(0, __val[0].lastIndexOf("\""));
						}
						// 取出title属性值进行编码处理
						var _title = env.fn.formatText(val);
						// 将编码后的数据替换原来的数据
						cValue = cValue.replace(val, _title);
					}
				}
                if(firstTd){
                	// 处理domain问题 
                	var tmpHref = cValue.match(/href="(\S*)\?/);
                	if(tmpHref && tmpHref.length >1) {
                		var _href = __formatUrl(tmpHref[1]);
						cValue = cValue.replace(tmpHref[1], _href);
                	}
                	var style = 'border-bottom:0;text-align:left;';
                	if(href){
                		style += 'cursor:pointer;'
                	}
                    {$<td style='{%style%}{%cell.style%}' class='lui_dataview_listtable_firstTd {%cell.styleClass%}' $}
                    if(href){
                    	{$ onclick='window.open("{%href%}", "{%target%}")' $}
                    }
                    {$>{%cValue%}</td>$}
                    firstTd = false;
                }
                else{
				    {$<td style='border-bottom:0;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;{%cell.style%};' class='{%cell.styleClass%}'>{%cValue%}</td>$}
                }
			}
			{$</tr>$}
		}
{$		</tbody>
	</table>
$}
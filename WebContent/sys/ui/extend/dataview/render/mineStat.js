var examData = {
	'stutype' : 'need',
	'student' : 'true'
}

function getData(url) {
	var result;
	var data = '';
	if (url.indexOf('exam') > -1) {
		data = examData;
	}
	$.ajax({
		url : env.fn.formatUrl(url),
		type : 'POST',
		dataType : 'json',
		data : data,
		async : false,
		success : function(data) {
			result = data;
		}
	});
	return result;
}

function buildTabs(dataArr, baseId) {

	var contain = $('<div />');

	// Tabs
	var ul = $('<ul class="lui-n-fresh-tab" id="mineStatTabs"/>');
	var hasActive = false; // 是否存在默认选中
	var num = 0; // 第几个tab数据不为0
	for (var i = 0; i < dataArr.length; i++) {
		var data = dataArr[i];
		var isLocked = false; // 是否锁上(0条数据显示锁)

		var li = $('<li data-index="' + i + '"/>');
		li.click(function() {
			var index = $(this).attr('data-index');
			index = parseInt(index);
			refresh(index, dataArr[index], baseId);
		});

		var dataDB = getData(data.url);

		if(!dataDB)
			continue;
		
		var pNum = $('<p class="nfti-num" />');
		pNum.html(dataDB.count);
		var pDesc = $('<p class="nfti-desc" />');
		pDesc.html(dataDB.text);
		// 图标
		var _icon = data.moduleName.split(':')[0];
		var pIcon=$('<p class="nfti-icon"/>').addClass('nfti-icon-'+_icon);

		var div = $('<div />');
		div.append(pNum);
		div.append(pDesc);
		div.append(pIcon);

		if (dataDB.count == 0)
			isLocked = true;
		if (isLocked) {
			var divItem = $('<div class="lui-n-fresh-tab-item locked" />');
		} else {
			if (!hasActive) {
				var divItem = $('<div class="lui-n-fresh-tab-item active" />');
				num = i;
				hasActive = true;
			} else {
				var divItem = $('<div class="lui-n-fresh-tab-item" />');
			}
		}

		divItem.append(div);

		li.append(divItem);
		ul.append(li);
	}
	contain.append(ul);
	
	// Contents
	var contentsContain = $('<div class="lui-n-fresh-content" id="mineStatContent"/>');
	
	contentsContain.append(buidlContents(dataArr[num]));
	contentsContain.append(buildMore(dataArr[num]));

	contain.append(contentsContain);

	return contain;
}

function buidlContents(data) {
	var dataDB = getData(data.detailUrl);
	if (dataDB && dataDB.length > 0) {
		var ul = $('<ul class="lui-n-fresh-content-wrap active" />');

		for (var i = 0; i < dataDB.length; i++) {
			var li = $('<li />');

			var div = $('<a class="lui-n-fresh-content-item" href="' + env.fn.formatUrl(dataDB[i].href)
			+ '" target="_blank" />');

			var aDom = $('<span class="lui-n-fresh-content-item-time-a" />')

			var a = $('<span class="lui-nfci-link">' + dataDB[i].text
					+ '</span>');
			li.append(div.append(aDom.append(a)));

			if (dataDB[i].time) {
				var timeDom = $('<span class="lui-n-fresh-content-item-time" title="' + dataDB[i].time + '"/>');
				timeDom.html(dataDB[i].time);
				div.append(timeDom);
			}

			ul.append(li);
		}

		return ul;
	}
}

function buildMore(data){
	var moreDom = $('<div class="lui-n-fresh-more"/>');
	var a = $('<a class="lui-n-fresh-more-a" id="minStatMore" href="' + env.fn.formatUrl(data.viewUrl) + '" target="_blank">更多</a>')
	moreDom.append(a);
	return moreDom;
}

function refresh(index, data, baseId) {
	
	baseId = '#' + baseId;
	
	var content = buidlContents(data);
	$(baseId + ' #mineStatContent').empty();
	$(baseId + ' #mineStatContent').append(content);

	var oldActiveDom = $(baseId + ' #mineStatTabs .active');
	oldActiveDom.removeClass('active');
	var newActiveDom = $(baseId + ' #mineStatTabs .lui-n-fresh-tab-item')[index];
	$(newActiveDom).addClass('active');
	
	$(baseId + ' #minStatMore').remove();
	$(baseId + ' #mineStatContent').append(buildMore(data));
}

function initDom(data) {
	
	var dateStr = new Date().getTime();
	
	var baseId = 'mineStat_' + dateStr;
	
	var box = $('<div class="lui-n-fresh" id=' + baseId + '/>');	
	// 版式二
	if(render.vars.imgFormat) box.addClass('lui-n-fresh'+ render.vars.imgFormat)

	// Tabs and Contents
	var div = buildTabs(data, baseId);

	box.append(div);
	return box;
}

var html = (function(e) {
	var a = $("[data-lui-type|='lui/panel!Content']");
	if (data && data.length > 0) {
		return initDom(data);
	}
})();

done(html);
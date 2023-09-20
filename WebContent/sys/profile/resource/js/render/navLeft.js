seajs.use(['lui/topic'],function(topic){
	
	var currentItem,
		navLeft = render.parent;
	
	function createContainer(){
		return $('<ul class="lui_profile_navLeft" />');
	}
	function createItem(_data){
		var item = $('<li class="lui_profile_navLeft_item"/>'),
			itemLink = $('<a class="lui_profile_link"/>').appendTo(item),
			icon = $('<div class="lui_profile_navLeft_item_icon lui_icon_m_profile_default" />').appendTo(itemLink),
			text = $('<div class="lui_profile_navLeft_item_text" />').text(_data.messageKey).appendTo(itemLink);
		icon.addClass(_data.icon);
		
		item.attr('data-type',_data.key);
		item.attr('title',_data.messageKey);
		item.data('data',_data);
		item.on('click',function(){
			currentItem = $('.lui_profile_navLeft_item[data-type="'+ _data.key +'"]');
			$('.lui_profile_navLeft_item').removeClass('current');
			currentItem.addClass('current');
			topic.publish('sys.profile.navLeft.change',{
				key : _data.key,
				url : _data.url
			});
		});
		return item;
	}
	var container = createContainer();
	for(var i = 0;i < data.length;i++){
		var item = createItem(data[i]);
		if(i == 0){
			currentItem = item;
		}
		if(navLeft.selectedItem == data[i].key){
			currentItem = item;
		}
		container.append(item);
	}
	
	if(currentItem){
		currentItem.addClass('current');
		var ___data = currentItem.data('data');
		topic.publish('sys.profile.navLeft.change',{
			key : ___data.key,
			url : ___data.url
		});
	}
	
	done(container);
});
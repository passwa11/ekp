define(function(require, exports, module) {
	
	function TopicList(element, data, options) {
		this.element = element;
		this.data = data || [];
		this.defaultOptions = {
			mode: 'view',
			onSelect: function(claxx) {
				console.log('select', claxx);
			}
		};
		this.options = $.extend(this.defaultOptions, options);
		this.initialize();
	}
	
	TopicList.prototype = {
		initialize: function() {
			this.render();
			this.bindEvents();
		},
		initProps: function() { 
			this.element.addClass('class-container');
		},
		bindEvents: function() {
			var self = this;
			self.element.on('click', '.topic-item-wrapper', function() {
				if(!$(this).hasClass('active')) {
					self.element.find('.topic-item-wrapper').removeClass('lui_seat_active lui_text_primary');
					$(this).addClass('lui_seat_active lui_text_primary');
				}
			});
		},
		render: function() {
			this.beforeRender();
			this.renderData();
		},
		beforeRender: function() {
		},
		renderData: function() {
			var self = this;
			var flag = true;
			self.element.find('.topic-item-wrapper').remove();
			$.each(self.data || [], function(_, item) {
				var itemNode = $('<li/>').addClass('topic-item-wrapper').attr('data-topicid', item.topicId).appendTo(self.element);
				
				itemNode.click(function() {
					self.options.onSelect(item);
				});
				if(self.options.mode == 'edit') {
					$('<span/>').addClass('lui_seat_status status_warn')
						.text('[ 未排全 ]').appendTo(itemNode);
				}
				
				$('<span/>')
					.addClass('lui_seat_subject_title')
					.text(item.topicSubject).appendTo(itemNode);
				//默认选中第一个
				if(flag){
					itemNode.click();
					flag = false;
				}
			});
		},
		setData: function(data) {
			
			// [
			//   {
			//    
			//     "topicId": "xxx",      //议题id
			//	   "topicSubject": "", //议题名字
			//   }
			// ]
			this.data = data;
			this.renderData();
		},
		setIsPlan:function(topicId,flag){
			var self = this;
			var topic = self.element.find('.topic-item-wrapper[data-topicid='+topicId+']');
			if(flag){
				var plan = topic.find(".status_warn");
				if(plan){
					$(plan).removeClass('status_warn').addClass("status_done").text('[ 已排全 ]');
				}
			}else{
				var plan = topic.find(".status_done");
				if(plan){
					$(plan).removeClass('status_done').addClass("status_warn").text('[ 未排全 ]');
				}
			}
			
		},
		reset: function() {
			this.setData([]);
		},
		noSelect: function() {
			this.element.find('.topic-item-wrapper').removeClass('active');
			this.options.onSelect(null);
		}
	};
	
	module.exports = TopicList;
	
});
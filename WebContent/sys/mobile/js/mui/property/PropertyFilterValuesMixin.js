define([ "dojo/_base/declare", "dojo/topic", "dojo/_base/lang", "mui/ChannelMixin" ], function(declare, topic, lang, ChannelMixin) {

	return declare("mui.property.PropertyFilterValuesMixin", [ ChannelMixin ], {

		EVENT_VALUES: '/mui/property/filter/values',
		
		// 筛选值
		values: {},
		
		// 筛选值缓存
		_values: {},
		
		buildRendering: function() {
			this.inherited(arguments);
			this.subscribe('/mui/property/filter/value/get', 'getValue');
			this.subscribe('/mui/property/filter/value/set', 'setValue');
			this.subscribe('/mui/property/filter/values/get', 'getValues');
			this.subscribe('/mui/property/filter/values/set', 'setValues');

			// TODO
			// 数据格式{'id':[value]}
			this.values = {};
		},
		
		publishValues: function() {
			topic.publish(this.EVENT_VALUES, this, {
				values: this.values
			});
		},
		
		getValues: function(obj, evt) {
			if(!this.isSameChannel(obj.key)){
				return;
			}
			
			if(!evt || !evt.cb) {
				return;
			}
			evt.cb(this.values);
		},
		
		setValues: function(obj, evt) {
			if(!this.isSameChannel(obj.key)){
				return;
			}
			
			if(!evt || !evt.values) {
				return
			}
			
			this.values = evt.values || {}
			this.publishValues();
		},
		
		getValue: function(obj, evt) {
			if(!this.isSameChannel(obj.key)){
				return;
			}
			
			if(!evt || !evt.cb || !evt.name) {
				return;
			}
			evt.cb(this.values[evt.name] || [])
		},
	
		setValue: function(obj, evt) {
			if(!this.isSameChannel(obj.key)){
				return;
			}
			
			if(!evt || !evt.name || !evt.value) {
				return;
			}
			this.values[evt.name] = evt.value || [];
			this.publishValues();
		}
	});
});

define(['dojo/_base/declare', 'dojo/dom-construct'], function(declare, domConstruct) {
	var item = declare("mui.list.item.TextItem.docStatusMixin", null, {

		/**
		 * 本mixin应该被移动端所有展现样式mixin所继承（如左图右文，右图左文，简单列表展现等）
		 * 用于对数据进行转换封装，并对数据源格式进行统一
		 */
		
		statusClass: '',
		
		buildTag : function (domNode) {
			
			this.inherited(arguments);
			
			if(this.status && domNode) {
				
				this.formatStatusData();
				
				domConstruct.create('span', {
					className : this.tagClass + this.statusClass,
					innerHTML : this.status
				}, domNode);
				
			}
			
		},
		
		formatStatusData : function () {
			
			if(this.status) {
			
				if('00' == this.status) {
					
					this.status = '废弃';
					
					this.statusClass = ' muiProcessDiscard';
					
				} else if('10' == this.status) {
					
					this.status = '草稿';
					
					this.statusClass = ' muiProcessDraft';
					
				} else if('11' == this.status) {
					
					this.status = '驳回';
					
					this.statusClass = ' muiProcessRefuse';
					
				} else if('20' == this.status) {
					
					this.status = '待审';
					
					this.statusClass = ' muiProcessExamine';
					
				} else if('30' == this.status) {
					
					this.status = '结束';
					
					this.statusClass = ' muiProcessPublish';
					
				} else if('31' == this.status) {
					
					this.status = '已反馈';
					
					this.statusClass = ' muiProcessPublish';
					
				} 
			}
		}
	});
	return item;
});
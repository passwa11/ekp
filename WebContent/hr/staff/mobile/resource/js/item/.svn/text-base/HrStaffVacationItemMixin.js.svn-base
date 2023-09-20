define([
    "dojo/_base/declare",
    "dojo/_base/array",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/list/item/_ListLinkItemMixin",
   	"dojo/on",
   	"dojo/topic"
	], function(declare, array, domConstruct,domClass , domStyle , domAttr , ItemBase , _ListLinkItemMixin,on,topic) {
	var item = declare("hr.staff.mobile.resource.js.item.HrStaffVacationMixin", [ItemBase], {
		fdYear:'',
		vaIndex:'',
		buildRendering:function(){
			this.inherited(arguments);
		},
		startup:function(){
			this.inherited(arguments);
			if(this.vaIndex=="1"){
				this.renderVacation();
			}if(this.vaIndex=="2"){
				this.renderSalary();
			}else if(this.vaIndex=="3"){
				this.renderWorkMore();
			}
		},
		renderWorkMore:function(){
			this.domNode.classList.add('ppc_c_content');
			var list = domConstruct.create("div",{className:'va_list'},this.domNode);
			list.innerHTML=`
			    <div class="va-list-item">
					<div>
						<span>姓名:</span><span>${this.fdName}</span>
					</div>
				</div>	
				<div class="va-list-item">
					<div>
						<span>类型:</span><span>${this.fdLeaveName}</span>
					</div>
					<div>
						<span>来源:</span><span>${this.fdOprType}</span>
					</div>
				</div>				
				<div class="va-list-item">
					<div>
						<span>开始时间:</span><span>${this.fdStartTime}</span>
					</div>
					<div>
						<span>加班时长:</span><span>${this.fdLeaveTime}</span>
					</div>
				</div>				
			`
/*			var leftNode = domConstruct.create("div",{className:'va-list-left',innerHTML:'<span>请假类型:</span>'+'<span>'+this.fdLeaveName+'</span>'},list);
			var rightNode = domConstruct.create("div",{className:'va-list-right',innerHTML:'<span>操作类型:</span>'+'<span>'+this.fdOprType+'</span>'},list);*/
		},
		//渲染 请假明细
		renderVacation:function(){
			this.domNode.classList.add('ppc_c_content');
			var list = domConstruct.create("div",{className:'va_list'},this.domNode);
			list.innerHTML=`
			<div class="va-list-item">
					<div>
						<span>姓名:</span><span>${this.fdName}</span>
					</div>
				</div>	
				<div class="va-list-item">
					<div>
						<span>开始时间:</span><span>${this.fdStartTime}</span>
					</div>
					<div>
						<span>请假类型:</span><span>${this.fdLeaveName}</span>
					</div>
				</div>
				<div class="va-list-item">
					<div>
						<span>结束时间:</span><span>${this.fdEndTime}</span>
					</div>
					<div>
						<span>来源:</span><span>${this.fdOprType}</span>
					</div>
				</div>
			`
/*			var leftNode = domConstruct.create("div",{className:'va-list-left',innerHTML:'<span>开始时间:</span>'+'<span>'+this.fdStartTime+'</span>'},list);
			var rightNode = domConstruct.create("div",{className:'va-list-right',innerHTML:'<span>结束时间:</span>'+'<span>'+this.fdEndTime+'</span>'},list);*/
			
		},
		renderSalary:function(){
			this.domNode.classList.add('ppc_c_content');
			var list = domConstruct.create("div",{className:'va_list',style:'min-height:3rem;'},this.domNode);
			list.innerHTML=`
			    <div class="va-list-item">
					<div>
						<span>姓名:</span><span>${this.fdName}</span>
					</div>
				</div>	
				<div class="va-list-item">
					<div>
						<span>年份:</span><span>${this.fdYear}</span>
					</div>
					<div>
						<span>剩余总天数:</span><span>${this.totalRest}</span>
					</div>					
				</div>
			`
			/*var leftNode = domConstruct.create("div",{className:'va-list-left',innerHTML:'<span>年份:</span>'+'<span>'+this.fdYear+'</span>'},list);
			var rightNode = domConstruct.create("div",{className:'va-list-right',innerHTML:'<span>剩余假期时间:</span>'+'<span>'+this.totalRest+'</span>'},list);*/
		},
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}

	});
	return item;
});
/**********************************************************
功能：订飞机（携程）
使用：模板配置使用
需求变更，该组件废弃
**********************************************************/

Designer_Config.controls['ticketPlane'] = {
		type : "ticketPlane",
		storeType : 'none',
		inherit    : 'base',
		onDraw : _Designer_Control_TicketPlane_OnDraw,
		drawXML : _Designer_Control_TicketPlane_DrawXML,
		_destroy : Designer_Control_Destroy,
		implementDetailsTable : false,
		info : {
			name: Designer_Lang.bookTicket
		},
		resizeMode : 'all',
		attrs : {
			label : Designer_Config.attrs.label,
			passengerList : {
				text: Designer_Lang.bookTicket_travelPeople,
				value: "",
				type : 'self',
				show: true,
				required: true,
				draw: Designer_Control_Ctrip_Attr_Draw,
				validator: Designer_Control_Attr_Required_Validator,
				checkout: Designer_Control_Attr_Required_Checkout,
				showControlType: 'address|new_address'
			},
			retinueList : {
				text: Designer_Lang.ticketHotel_fellowWorkers,
				type : 'self',
				show: true,
				draw: Designer_Control_Ctrip_Attr_Draw
			},
			beginDate : {
				text: Designer_Lang.bookTicket_departureTime,
				type : 'self',
				show: true,
				required: true,
				validator: Designer_Control_Attr_Required_Validator,
				checkout: Designer_Control_Attr_Required_Checkout,
				draw: Designer_Control_Ctrip_Attr_Draw,
				showControlType: 'datetime'
			},
			endDate : {
				text: Designer_Lang.bookTicket_theLatestDdepartureTime,
				type : 'self',
				show: true,
				draw: Designer_Control_Ctrip_Attr_Draw,
				showControlType: 'datetime'
			},
			fromCity : {
				text: Designer_Lang.bookTicket_departureCity,
				type : 'self',
				show: true,
				required: true,
				validator: Designer_Control_Attr_Required_Validator,
				checkout: Designer_Control_Attr_Required_Checkout,
				draw: Designer_Control_Ctrip_Attr_Draw
			},
			toCity : {
				text: Designer_Lang.bookTicket_arrivalCity,
				type : 'self',
				show: true,
				required: true,
				validator: Designer_Control_Attr_Required_Validator,
				checkout: Designer_Control_Attr_Required_Checkout,
				draw: Designer_Control_Ctrip_Attr_Draw
			},
			costCompany : {
				text: Designer_Lang.bookTicket_costCenter1,
				value: "",
				type : 'self',
				show: true,
				draw: Designer_Control_Ctrip_Attr_Draw,
				showControlType: 'inputText|inputRadio|select|address|new_address'
			},
			costDept : {
				text: Designer_Lang.bookTicket_costCenter2,
				value: "",
				type : 'self',
				show: true,
				draw: Designer_Control_Ctrip_Attr_Draw,
				showControlType: 'inputText|inputRadio|select|address|new_address'
			},
			project3 : {
				text: Designer_Lang.bookTicket_costCenter3,
				value: "",
				type : 'self',
				show: true,
				draw: Designer_Control_Ctrip_Attr_Draw,
				showControlType: 'inputText|inputRadio|select|address|new_address'
			},
			remarks1 : {
				text: Designer_Lang.bookTicket_remarks1,
				value: "",
				type : 'self',
				show: true,
				draw: Designer_Control_Ctrip_Attr_Draw,
				showControlType: 'inputText|textarea'
			},
			remarks2 : {
				text: Designer_Lang.bookTicket_remarks2,
				value: "",
				type : 'self',
				show: true,
				draw: Designer_Control_Ctrip_Attr_Draw,
				showControlType: 'inputText|textarea'
			},
			bookNodeValue : {
				text: Designer_Lang.bookTicket_nodeHiddenValue,
				type: "hidden",
				show: false,
				required : false
			},
			bookNode : {
				text: Designer_Lang.bookTicket_bookingPerson,
				type: "self",
				value: "",
				show: true,
				required : false,
				checkout: Designer_Control_Ctrip_SelectNode_Checkout,
				draw: Designer_Control_Ctrip_SelectNode
			}
		}
};

Designer_Config.operations['ticketPlane'] = {
		lab : "2",
		imgIndex : 38,
		title : Designer_Lang.bookTicket,
		order : 16,
		run : function (designer) {
			designer.toolBar.selectButton('ticketPlane');
		},
		type : 'cmd',
		select: true,
		cursorImg: 'style/cursor/plane.cur',
		isShow: function(){return _ctripIsVisibel;}
	};

Designer_Config.buttons.tool.push("ticketPlane");

Designer_Menus.add.menu['ticketPlane'] = Designer_Config.operations['ticketPlane'];

function _Designer_Control_TicketPlane_OnDraw(parentNode,childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();	
	domElement.id = this.options.values.id;	
	domElement.style.width='27px';
	var img = document.createElement("img");
	img.setAttribute("src","style/img/ctrip.gif");
	$(domElement).attr("bookNodeValue",this.options.values.bookNodeValue);
	//隐藏字段，用于保存映射的id
	var bookTicketDataDiv = document.createElement("div");
	bookTicketDataDiv.setAttribute("name","sysXformTicketPlaneData_"+domElement.id);
	bookTicketDataDiv.style.display = "none";
	Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, domElement.id, "data_controlId")
	if(this.options.values.retinueList) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.retinueList, "retinueList_relationId");
	if(this.options.values.passengerList) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.passengerList, "passengerList_relationId");
	if(this.options.values.beginDate) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.beginDate, "beginDate_relationId");
	if(this.options.values.endDate) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.endDate, "endDate_relationId");
	if(this.options.values.hotelBeginDate) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.hotelBeginDate, "hotelBeginDate_relationId");
	if(this.options.values.hotelEndDate) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.hotelEndDate, "hotelEndDate_relationId");
	if(this.options.values.fromCity) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.fromCity, "fromCity_relationId");
	if(this.options.values.toCity) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.toCity, "toCity_relationId");
	if(this.options.values.costDept) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.costDept, "costDept_relationId");
	if(this.options.values.project3) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.project3, "costDept3_relationId");
	if(this.options.values.remarks1) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.remarks1, "remarks1_relationId");
	if(this.options.values.remarks2) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.remarks2, "remarks2_relationId");
	if(this.options.values.costCompany) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, this.options.values.costCompany, "costCompany_relationId");
	domElement.appendChild(img);
	domElement.appendChild(bookTicketDataDiv);
}

function _Designer_Control_TicketPlane_DrawXML(){
	var values = this.options.values;
	var customFields = ['retinueList','passengerList','beginDate','endDate','costDept','project3','remarks1','remarks2','costCompany','fromCity','toCity','bookNodeValue'];
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="', 'String', '" ');
	buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(Designer_Control_Ctrip_GetTicketJson(customFields,values))), '" ');
	buf.push('/>');
	return buf.join('');
}


/**********************************************************
功能：订机票（携程）
使用：模板配置使用
	
作者：朱国荣
创建时间：2016-7-13
**********************************************************/

Designer_Config.controls['bookticket'] = {
		type : "bookticket",
		storeType : 'none',
		inherit    : 'base',
		onDraw : _Designer_Control_BookTicket_OnDraw,
		drawXML : _Designer_Control_BookTicket_DrawXML,
		_destroy : Designer_Control_Destroy,
		implementDetailsTable : false,
		needInsertValidate : true, //虽然不是容器，但确实需要插入校验
		insertValidate: _Designer_Control_Bookticket_InsertValidate, //插入校验，用于不支持权限控件
		info : {
			name: Designer_Lang.bookTicket
		},
		resizeMode : 'all',
		attrs : {
			label : Designer_Config.attrs.label,
			bookType : {
				text: Designer_Lang.bookTicket_ticketType,
				required: true,
				validator: Designer_Control_Attr_Required_Validator,
				checkout: Designer_Control_Attr_Required_Checkout,
				type:'self',
				show: true,
				draw: Designer_Control_Ctrip_Attr_Draw,
				showControlType: 'inputCheckbox'
			},
			help:{
				text: Designer_Lang.bookTicket_bookingTypeTips + '<br/>1、'+Designer_Lang.bookTicket_currentCtripOnlySupportPlaneAndHotel+';<br/>2、'+Designer_Lang.bookTicket_formatRequirements,
				type: 'help',
				align:'left',
				show: true
			},
			planeDraw : {
				text : Designer_Lang.bookTicket_planeField,
				type : 'self',
				show: true,
				draw : Designer_Control_BookTicket_PlaneDraw,
				attrs : {
					passengerList : {
						text: Designer_Lang.bookTicket_travelPeople,
						value: "",
						type : 'self',
						show: true,
						draw: Designer_Control_Ctrip_Attr_Draw,
						//validator: Designer_Control_Attr_Required_Validator,
						//checkout: Designer_Control_Attr_Required_Checkout,
						showControlType: 'address|new_address'
					},
					retinueList : {
						text: Designer_Lang.ticketHotel_fellowWorkers,
						type : 'self',
						show: true,
						draw: Designer_Control_Ctrip_Attr_Draw,
						showControlType: 'inputText|textarea'
					},
					beginDate : {
						text: Designer_Lang.bookTicket_departureTime,
						type : 'self',
						show: true,
						//validator: Designer_Control_Attr_Required_Validator,
						//checkout: Designer_Control_Attr_Required_Checkout,
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
						draw: Designer_Control_Ctrip_Attr_Draw
					},
					toCity : {
						text: Designer_Lang.bookTicket_arrivalCity,
						type : 'self',
						show: true,
						draw: Designer_Control_Ctrip_Attr_Draw
					},
					// 舱位
					seatType : {
						text: Designer_Lang.bookTicket_seatType,
						type : 'self',
						show: true,
						draw: Designer_Control_Ctrip_Attr_Draw,
						showControlType: 'inputRadio|select'
					},
					seatTypeHelp:{
						text: Designer_Lang.bookTicket_seatTypeHelp,
						type: 'help',
						align:'left',
						show: true
					}
				}
			},
			hotelDraw : {
				text : Designer_Lang.bookTicket_hotelField,
				type : 'self',
				show: true,
				draw : Designer_Control_BookTicket_HotelDraw,
				attrs : {
					// 入住人员
					hotelPassengerList : {
						text: Designer_Lang.ticketHotel_checkInStaff,
						value: "",
						type : 'self',
						show: true,
						draw: Designer_Control_Ctrip_Attr_Draw,
						//validator: Designer_Control_Attr_Required_Validator,
						//checkout: Designer_Control_Attr_Required_Checkout,
						showControlType: 'address|new_address'
					},
					// 同行人员
					hotelRetinueList : {
						text: Designer_Lang.ticketHotel_fellowWorkers,
						type : 'self',
						show: true,
						draw: Designer_Control_Ctrip_Attr_Draw,
						showControlType: 'inputText|textarea'
					},
					// 入店时间
					hotelEntryDate : {
						text: Designer_Lang.ticketHotel_entryTime,
						type : 'self',
						show: true,
						//validator: Designer_Control_Attr_Required_Validator,
						//checkout: Designer_Control_Attr_Required_Checkout,
						draw: Designer_Control_Ctrip_Attr_Draw,
						showControlType: 'datetime'
					},
					// 最晚入店时间
					hotelLatestEntryDate : {
						text: Designer_Lang.ticketHotel_latestEntryTime,
						type : 'self',
						show: true,
						draw: Designer_Control_Ctrip_Attr_Draw,
						showControlType: 'datetime'
					},
					// 离店时间
					hotelBeginDate : {
						text : Designer_Lang.ticketHotel_departureTime,
						type : 'self',
						show: true,
						draw: Designer_Control_Ctrip_Attr_Draw,
						//validator: Designer_Control_Attr_Required_Validator,
						//checkout: Designer_Control_Attr_Required_Checkout,
						showControlType: 'datetime'
					},
					// 最晚离店时间
					hotelEndDate : {
						text : Designer_Lang.ticketHotel_latestDepartureTime,
						type : 'self',
						show: true,
						draw: Designer_Control_Ctrip_Attr_Draw,
						showControlType: 'datetime'
					},
					// 入住城市
					hotelToCity : {
						text: Designer_Lang.ticketHotel_hostCity,
						type : 'self',
						show: true,
						//validator: Designer_Control_Attr_Required_Validator,
						//checkout: Designer_Control_Attr_Required_Checkout,
						draw: Designer_Control_Ctrip_Attr_Draw
					}
				}
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

Designer_Config.operations['bookticket'] = {
		lab : "2",
		imgIndex : 38,
		title : Designer_Lang.bookTicket,
		order : 15,
		run : function (designer) {
			designer.toolBar.selectButton('bookticket');
		},
		type : 'cmd',
		select: true,
		cursorImg: 'style/cursor/plane.cur',
		isShow: function(){return _ctripIsVisibel;}
	};

Designer_Config.buttons.tool.push("bookticket");

Designer_Menus.add.menu['bookticket'] = Designer_Config.operations['bookticket'];

function _Designer_Control_BookTicket_OnDraw(parentNode,childNode){
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();	
	domElement.id = this.options.values.id;	
	domElement.style.width='27px';
	var img = document.createElement("img");
	img.setAttribute("fdImgId", domElement.id);
	img.setAttribute("src","style/img/ctrip.gif");
	$(domElement).attr("bookNodeValue",this.options.values.bookNodeValue);
	//隐藏字段，用于保存映射的id
	var bookTicketDataDiv = document.createElement("div");
	bookTicketDataDiv.setAttribute("name","sysXformBookTicketData_"+domElement.id);
	bookTicketDataDiv.style.display = "none";
	var attrs = this.attrs;
	// 不需要绑定关联的控件id的属性
	var notBindAttr = ['label','help','bookNodeValue','bookNode'];
	_Designer_Control_BookTicket_SetRelationId(this,bookTicketDataDiv,attrs,notBindAttr);
	domElement.appendChild(img);
	domElement.appendChild(bookTicketDataDiv);
}

// 设置关联控件的id到dom上面
function _Designer_Control_BookTicket_SetRelationId(control,bookTicketDataDiv,attrs,notBindAttr){
	for(var attr in attrs){
		if($.inArray(attr,notBindAttr) > -1){
			continue;
		}
		if(attrs[attr].attrs){
			_Designer_Control_BookTicket_SetRelationId(control,bookTicketDataDiv,attrs[attr].attrs,notBindAttr);
		}else{
			if(control.options.values[attr]) Designer_Control_Ctrip_SetAttr(bookTicketDataDiv, control.options.values[attr], attr + "_relationId");	
		}
		
	}
}

function _Designer_Control_BookTicket_DrawXML(){
	var values = this.options.values;
	var attrs = this.attrs;
	var notDict = ['label','help','bookNode'];
	var customFields = [];
	for(var attr in attrs){
		if($.inArray(attr,notDict) > -1){
			continue;
		}
		if(attrs[attr].attrs){
			for(var attr2 in attrs[attr].attrs){
				customFields.push(attr2);
			}
		}else{
			customFields.push(attr);	
		}
		
	}
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="', 'String', '" ');
	buf.push('customElementProperties="',Designer.HtmlEscape(JSON.stringify(Designer_Control_Ctrip_GetTicketJson(customFields,values))),'" ');
	buf.push('/>');
	return buf.join('');
}

//cell指被插入的单元，control只即将插入的控件
function _Designer_Control_Bookticket_InsertValidate(cell, control){
	//权限控件不支持插入
	if(control && control.container){
		alert(Designer_Lang.bookticket_notSupportPutInConainer);
		return false;
	}
	return true;
}

function Designer_Control_BookTicket_HotelDraw(name, currentAttr, value, form, attrs, values){
	var buff = [];
	buff.push("<tr><td colspan='2'>");
	buff.push("<div style='border-bottom:1px solid #333333;'>");
	buff.push(Designer_Control_BookTicket_AttrsDraw(name, currentAttr, value, form, attrs, values));
	buff.push("</div>");
	buff.push("</td></tr>");
	return buff.join('');
}

function Designer_Control_BookTicket_PlaneDraw(name, currentAttr, value, form, attrs, values){
	var buff = [];
	buff.push("<tr><td colspan='2'>");
	buff.push("<div style='border-bottom:1px solid #333333;border-top:1px solid #333333;'>");
	buff.push(Designer_Control_BookTicket_AttrsDraw(name, currentAttr, value, form, attrs, values));
	buff.push("</div>");
	buff.push("</td></tr>");
	return buff.join('');
}

function Designer_Control_BookTicket_AttrsDraw(name, currentAttr, value, form, attrs, values) {
	var buff = [];
	// 标题
	buff.push("<div style='margin:5px 0 2px 6px;color:#4d4d4d;font-weight:bold;'>");
	buff.push(currentAttr.text);
	buff.push("</div>");
	// 属性内容
	buff.push("<table class='panel_table_noborder'>");
	for (var name in currentAttr.attrs) {
		var attr = currentAttr.attrs[name];
		if (attr.show) {
			if (attr.type == 'self') {
				buff.push(attr.draw(name, attr, values[name], null, attrs, values));
			} else {
				buff.push(Designer_AttrPanel[attr.type + 'Draw'](name, attr, values[name], null, attrs, values));
			}
		}
	}
	buff.push("</table>");
	return buff.join('');
}

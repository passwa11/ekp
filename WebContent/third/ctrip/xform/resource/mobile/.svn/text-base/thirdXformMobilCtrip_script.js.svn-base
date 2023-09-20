(function(win){
	// 兼容历史数据使用
	require(["sys/xform/mobile/controls/xformUtil"],function(xUtil){
		//获取必须的值存到集合里面
		win.third_ctrip_bookTicket_getNeededDom = function(){
			for(var relationName in bookTicket_relationidCollection){
				if(bookTicket_relationidCollection[relationName] && bookTicket_relationidCollection[relationName] != ''){
					//根据id找到各个dom对象
					var relationDom = GetXFormFieldById(bookTicket_relationidCollection[relationName],true);
					for(var len = 0 ; len < relationDom.length ; len++){
						var relationDomSub = relationDom[len];
						//判断是否是复选框
						if(relationDomSub.type){
							if(relationDomSub.type.toLowerCase() == 'checkbox' && relationDomSub.checked){
								bookTicket_DomValueCollection[relationName] = bookTicket_DomValueCollection[relationName] == ''?'':(bookTicket_DomValueCollection[relationName]+';')+relationDomSub.value;
							}else{
								bookTicket_DomValueCollection[relationName] = relationDomSub.value;
							}
						}else{
							bookTicket_DomValueCollection[relationName] = relationDomSub.value;
						}				
					}
				}
			}
			return bookTicket_DomValueCollection;
		}

		//中文标点符号转换
		win.replaceSymbol = function(str){
			str = str.replace(/，/g, ",");
			str = str.replace(/。/g, ".");
			str = str.replace(/：/g, ":");
			str = str.replace(/；/g, ";");
			str = str.replace(/＋/g, "+");
			str = str.replace(/－/g, "-");
			str = str.replace(/×/g, "*");
			str = str.replace(/÷/g, "/");
			str = str.replace(/（/g, "(");
			str = str.replace(/）/g, ")");
			str = str.replace(/《/g, "<");
			str = str.replace(/》/g, ">");
			return str;
		}

		//校验所有必填字段是否齐全
		win.third_ctrip_bookTicket_validateNotNull = function(collection){
			for(var fileName in collection){
				//出行人和随行人员的特殊处理
				if(fileName == 'retinueList'){
					//对于外部人员的标点符号进行中文处理
					collection[fileName] = replaceSymbol(collection[fileName]);
					continue;
				}
				if(fileName == 'passengerList'){
					if(collection[fileName] == ''){
						var passengerListDom = GetXFormFieldById(third_bookticket_data.getAttribute("passengerlist_relationid"),true)[0];
						if(bookTicket_relationidCollection['retinueList'] != ''){
							if(collection['retinueList'] == ''){
								var retinueListDom = GetXFormFieldById(third_bookticket_data.getAttribute("retinueList_relationId"),true)[0];
								if(retinueListDom && retinueListDom != null){
									alert("携程控件要求 \""+passengerListDom.subject+"\"和\""+retinueListDom.subject+"\"不能同时为空");
								}else{
									alert("携程控件要求 \""+passengerListDom.subject + "\"不能为空!");
								}							
								return false;
							}						
						}else{
							var passengerListDom = GetXFormFieldById(third_bookticket_data.getAttribute("passengerlist_relationid")+'.name',true)[0];
							if(bookTicket_relationidCollection['retinueList'] != ''){
								if(collection['retinueList'] == ''){
									var retinueListDom = GetXFormFieldById(third_bookticket_data.getAttribute("retinueList_relationId"),true)[0];
									var retinueListName = GetXFormFieldById(third_bookticket_data.getAttribute("retinueList_relationId")+'.name',true)[0];
									if(retinueListName && retinueListName != null){
										retinueListDom = retinueListName;
									}
									if(retinueListDom && retinueListDom != null){
										alert("携程控件要求 \""+$(passengerListDom).attr('subject')+"\"和\""+$(retinueListDom).attr('subject')+"\"不能同时为空");
									}else{
										alert("携程控件要求 \""+$(passengerListDom).attr('subject') + "\"不能为空!");
									}								
									return false;
								}					
							}else{
								alert("携程控件要求 \""+$(passengerListDom).attr('subject') + "\"不能为空!");
								return false;
							}
						}					
					}
					continue;
				}
				if(fileName == 'endDate'){
					if(collection[fileName] == ''){
						collection[fileName] = collection['beginDate'];
					}
				}
				if(collection[fileName] == ''){
					if($.inArray(fileName,bookTicket_NoRequired) > -1){
						continue;
					}
					//判断是否为空，以是否有字段存储到关联id为主
					if(bookTicket_relationidCollection[fileName] && bookTicket_relationidCollection[fileName] != ''){
						var nullFile = GetXFormFieldById(bookTicket_relationidCollection[fileName],true)[0];
						if(nullFile && nullFile.subject){
							alert("携程控件要求 \""+nullFile.subject + "\"不能为空");
							return false;
						}
					}			
				}
			}
			//判断出发时间范围，最晚时间必须比最早时间要晚
			if(collection['beginDate'] > collection['endDate']){
				alert("携程控件要求 最早出发日期必须比最晚出发日期要早!");
				return false;
			}
			
			//判断离店时间范围，离店时间必须比最晚离店时间要早
			if(collection['hotelBeginDate'] > collection['hotelEndDate']){
				alert("携程控件要求 离店时间必须比最晚离店时间要早!");
				return false;
			}
			return true;
		}

		//校验
		win.third_ctrip_bookTicket_bookTicket = function(bookTicketData){
			third_bookticket_data = bookTicketData;
			if(bookTicketData && bookTicketData != null){
				bookTicket_relationidCollection.passengerList = bookTicketData.getAttribute("passengerlist_relationid");
				bookTicket_relationidCollection.retinueList = bookTicketData.getAttribute("retinueList_relationId");
				bookTicket_relationidCollection.beginDate = bookTicketData.getAttribute("begindate_relationid");
				bookTicket_relationidCollection.endDate = bookTicketData.getAttribute("enddate_relationid");
				bookTicket_relationidCollection.hotelBeginDate = bookTicketData.getAttribute("hotelBeginDate_relationid");
				bookTicket_relationidCollection.hotelEndDate = bookTicketData.getAttribute("hotelEndDate_relationid");
				bookTicket_relationidCollection.fromCity = bookTicketData.getAttribute("fromcity_relationid");
				bookTicket_relationidCollection.toCity = bookTicketData.getAttribute("tocity_relationid");
				bookTicket_relationidCollection.costDept = bookTicketData.getAttribute("costDept_relationid");
				bookTicket_relationidCollection.costCompany = bookTicketData.getAttribute("costCompany_relationid");
				bookTicket_relationidCollection.project3 = bookTicketData.getAttribute("costDept3_relationid");
				bookTicket_relationidCollection.remarks1 = bookTicketData.getAttribute("remarks1_relationid");
				bookTicket_relationidCollection.remarks2 = bookTicketData.getAttribute("remarks2_relationid");
				//获取值
				//校验所有必填字段是否齐全
				third_ctrip_bookTicket_getNeededDom();
				if(third_ctrip_bookTicket_validateNotNull(bookTicket_DomValueCollection)){
					var bookTypeId = bookTicketData.getAttribute("bookType_relationid");
					if(bookTypeId){
						var wgt = xUtil.getXformWidgetBlur(document,bookTypeId);
						if(wgt && wgt.value == ""){
							alert("携程控件要求 \"" + wgt.subject + "\"不能为空!");
							return false;
						}
					}
					return true;
				}else{
					return false;
				}
				
			}else{
				alert("没找到关联数据信息的字段!");
			}
		}
		
		// 不是必填的字段
		var bookTicket_NoRequired = ['hotelBeginDate','hotelEndDate','costDept','costCompany','project3','remarks1','remarks2'];
		
		//存储预订机票控件关联的控件id
		var bookTicket_relationidCollection = {
			retinueList:'',
			passengerList:'',
			beginDate:'',
			endDate:'',
			hotelBeginDate :'',
			hotelEndDate:'',
			fromCity:'',
			toCity:'',
			costDept:'',
			costCompany:'',
			project3 : '',
			remarks1 : '',
			remarks2 : ''
		}

		//存储需要发送的数据
		var bookTicket_DomValueCollection = {
			passengerList:'',
			retinueList:'',
			approvalNumber:'',
			status:'',
			beginDate:'',
			endDate:'',
			hotelBeginDate :'',
			hotelEndDate:'',
			fromCity:'',
			toCity:'',
			costDept:'',
			costCompany:'',
			isFlight:'0',
			isHotel:'0',
			project3 : '',
			remarks1 : '',
			remarks2 : ''
		}
		
		var third_bookticket_data;
	});
})(window);
/**********************************************************
功能：控件配置
使用：
	
作者：龚健
创建时间：2009-02-21
**********************************************************/
Designer_Config = {}
Designer_Config.font = {
		style : [
			{text:Designer_Lang.configFontSelect, value:''},
			{text:Designer_Lang.configFontSongTi, value:Designer_Lang.configFontSongTi, style:'font-family:'+Designer_Lang.configFontSongTi},
			{text:Designer_Lang.configFontXinSongTi, value:Designer_Lang.configFontXinSongTi, style: 'font-family:'+Designer_Lang.configFontXinSongTi},
			{text:Designer_Lang.configFontKaiTi, value:Designer_Lang.configFontKaiTi, style: 'font-family:'+Designer_Lang.configFontKaiTi},
			{text:Designer_Lang.configFontHeiTi, value:Designer_Lang.configFontHeiTi, style: 'font-family:'+Designer_Lang.configFontHeiTi},
			{text:Designer_Lang.configFontYouYuan, value:Designer_Lang.configFontYouYuan, style: 'font-family:'+Designer_Lang.configFontYouYuan},
			{text:Designer_Lang.configFontYaHei, value:Designer_Lang.configFontYaHei, style: 'font-family:'+Designer_Lang.configFontYaHei},
			{text:Designer_Lang.configFontZhongsong, value:Designer_Lang.configFontZhongsong, style: 'font-family:'+Designer_Lang.configFontZhongsong},
			{text:Designer_Lang.configFontFangSongGB2312, value:Designer_Lang.configFontFangSongGB2312, style: 'font-family:'+Designer_Lang.configFontFangSongGB2312},
			{text:Designer_Lang.configFontFZXBSJT, value:Designer_Lang.configFontFZXBSJT, style: 'font-family:'+Designer_Lang.configFontFZXBSJT},
			{text:Designer_Lang.configFontCourierNew, value:Designer_Lang.configFontCourierNew, style: 'font-family:\"'+Designer_Lang.configFontCourierNew+'\"'},
			{text:Designer_Lang.configFontTimesNewRoman, value:Designer_Lang.configFontTimesNewRoman, style: 'font-family:\"'+Designer_Lang.configFontTimesNewRoman+'\"'},
			{text:Designer_Lang.configFontTahoma, value:Designer_Lang.configFontTahoma, style: 'font-family:\"'+Designer_Lang.configFontTahoma+'\"'},
			{text:Designer_Lang.configFontArial, value:Designer_Lang.configFontArial, style: 'font-family:\"'+Designer_Lang.configFontArial+'\"'},
			{text:Designer_Lang.configFontVerdana, value:Designer_Lang.configFontVerdana, style: 'font-family:\"'+Designer_Lang.configFontVerdana+'\"'}
		],
		size : (function() {
			var ops = [];
			ops.push({text: Designer_Lang.configSizeSelect, value:''});
			for (var i = 9; i < 26; i ++) {
				ops.push({text: i + Designer_Lang.configSizePx, value: i + 'px'});
			}
			//新加入字号
	      var sizes = [26,28,30,32,34,36,40,48,56,72];
	      for(var index=0,len=sizes.length; index < len; index++){
	        ops.push({text: sizes[index] + Designer_Lang.configSizePx, value: sizes[index] + 'px'});
	      }
			return ops;
		})(),
		b:[{text:Designer_Lang.controlTextLabelAttrDefault,value:''},{text:Designer_Lang.controlTextLabelAttrBold,value:'bold',style:'font-weight:bold'},{text:Designer_Lang.controlTextLabelAttrNormal,value:'normal',style:'font-weight:normal'}],
		i:[{text:Designer_Lang.controlTextLabelAttrDefault,value:''},{text:Designer_Lang.controlTextLabelAttrItalic,value:'italic',style:'font-style:italic'},{text:Designer_Lang.controlTextLabelAttrNormal,value:'normal',style:'font-style:normal'}],
		underline:[{text:Designer_Lang.controlTextLabelAttrDefault,value:''},{text:Designer_Lang.controlTextLabelAttrUnderline,value:'underline',style:'text-decoration:underline'},{text:Designer_Lang.controlTextLabelAttrNormal,value:'none',style:'text-decoration:none'}]
	};

Designer_Config.attrs = {
	label : {
		text : Designer_Lang.controlAttrLabel,
		value: "",
		type: 'label',
		show: true,
		lang: true,
		synchronous: true,//多表单是否同步
		required: true,
		validator: [Designer_Control_Attr_Required_Validator,Designer_Control_Attr_Label_Validator],
		checkout: [Designer_Control_Attr_Required_Checkout,Designer_Control_Attr_Label_Checkout],
		convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
	},
	defaultValue : {
		text: Designer_Lang.controlAttrDefaultValue,
		value: "",
		type: 'defaultValue',
		show: true,
		synchronous: true,//多表单是否同步
		validator: Designer_InputText_DefaultValue_Validator,
		checkout: Designer_InputText_DefaultValue_Checkout,
		convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
	},
	formula : {
		text: Designer_Lang.controlAttrFormula,
		value: "",
		type: '',
		show: false,
		synchronous: true,//多表单是否同步
		skipLogChange:true,
		convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
	},
	reCalculate : {
		text: Designer_Lang.controlAttrReCalculate,
		value: "",
		type: '',
		synchronous: true,//多表单是否同步
		show: false
	},
	readOnly : {
		text : Designer_Lang.controlAttrReadOnly,
		value : false,
		type : 'checkbox',
		show: true
	},
	tipInfo : {
		text : Designer_Lang.controlAttrTipInfo,
		value: "",
		type: 'textarea',
		show: true,
		lang: true,
		required: false,
		convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
	},
	encrypt : {
		text : Designer_Lang.controlAttrEncrypt,
		value : 'false',
		type : 'self',
		draw : Designer_Control_Attr_Encrypt,
		show: true
	}
};

Designer_Config.controls = {
		//===== textLabel begin =====
		textLabel : {
			type : "textLabel",
			storeType : 'none',
			isTextLabel : true,
			inherit    : 'base',
			onDraw : _Designer_Control_TextLabel_OnDraw,
			onDrawEnd : _Designer_Control_TextLabel_OnDrawEnd,
			setBold : _Designer_Control_SetControl_Bold,
			setItalic : _Designer_Control_SetControl_Italic,
			setUnderline : _Designer_Control_SetControl_Underline,
			setColor : _Designer_Control_SetControl_Color,
			getColor : _Designer_Control_GetControl_Color,
			setFontStyle : _Designer_Control_SetControl_FontStyle,
			getFontStyle : _Designer_Control_GetControl_FontStyle,
			setFontSize : _Designer_Control_SetControl_FontSize,
			getFontSize : _Designer_Control_GetControl_FontSize,
			implementDetailsTable : true,
			attrs : {
				content : {
					text: Designer_Lang.controlTextLabelAttrContent,
					value: "",
					type: 'textarea',
					show: true,
					required: true,
					lang: true,
					synchronous: true,//多表单是否同步
					validator: Designer_Control_Attr_Required_Validator,
					convertor: Designer_Control_Attr_HtmlEscapeConvertor
				},
				font : {
					text: Designer_Lang.controlTextLabelAttrFont,
					value: "",
					type: 'select',
					opts: Designer_Config.font.style,
					show: true
				},
				size : {
					text: Designer_Lang.controlTextLabelAttrSize,
					value: "",
					type: 'select',
					opts: Designer_Config.font.size,
					show: true
				},
				color : {
					text: Designer_Lang.controlTextLabelAttrColor,
					value: "#000",
					type: 'color',
					show: true
				},
				line : {
					text: Designer_Lang.controlTextLabelAttrLine,
					value : 'break-all',
					type : 'select',
					opts: [
						{text: Designer_Lang.controlTextLabelAttrNormal, value:'normal'},
						{text: Designer_Lang.controlTextLabelAttrBreakWord, value:'breakWord'},
						{text: Designer_Lang.controlTextLabelAttreNoWrap, value:'nowrap'}],
					translator:opts_common_translator,
					show: true
				},
				effect : {
					text: Designer_Lang.controlTextLabelAttrEffect,
					value : '',
					type : 'checkGroup',
					opts: [
						{name: 'b', text: Designer_Lang.controlTextLabelAttrBold, value:'true'},
						{name: 'i', text: Designer_Lang.controlTextLabelAttrItalic, value:'true'},
						{name: 'underline', text: Designer_Lang.controlTextLabelAttrUnderline, value:'true'}
					],
					show: true
				},
				isHiddenInMobile : {
					text : Designer_Lang.controlTextLabelAttrIsHiddenInMobile,
					value : 'false',
					type : 'checkbox',
					show: true						
				}
			},
			info : {
				name: Designer_Lang.controlTextLabelInfoName
			},
			resizeMode : 'no'  //尺寸调整模式(onlyWidth, onlyHeight, all, no)
		},
		//===== textLabel end =====
		//===== inputText begin =====
		inputText : {
			type : "inputText",
			storeType : 'field',
			inherit    : 'baseStyle',
			onDraw : _Designer_Control_InputText_OnDraw,
			drawXML : _Designer_Control_InputText_DrawXML,
			drawMobile : _Designer_Control_InputText_DrawMobile,
			mobileAlign : "right",
			
			implementDetailsTable : true,
			attrs : {
				label : Designer_Config.attrs.label,
				canShow : {
					text: Designer_Lang.controlAttrCanShow,
					value: "true",
					type: 'checkbox',
					checked: true,
					show: true,
					onclick: '_Designer_Control_InputText_CanShow(this);'
				},
				readOnly : Designer_Config.attrs.readOnly,
				required: {
					text: Designer_Lang.controlAttrRequired,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				isMark: {
					text: Designer_Lang.controlAttrIsMark,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				summary: {
					text: Designer_Lang.controlAttrSummary,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				concatSubTableRowIndex :{
					text: Designer_Lang.controlAttrConcatSubTableRowIndex,
					value: "true",
					type: 'self',
					checked: false,
					onlyShowInDetailTable: true,
					onlyShowInSomeModel : true,
					draw : Designer_Control_Attr_ConcatSubTableRowIndex,
					show: false
				},
				encrypt : Designer_Config.attrs.encrypt,
				width : {
					text: Designer_Lang.controlAttrWidth,
					value: "",
					type: 'text',
					show: true,
					validator: Designer_Control_Attr_Width_Validator,
					checkout: Designer_Control_Attr_Width_Checkout
				},
				dataType : {
					text: Designer_Lang.controlAttrDataType,
					value: "String",
					opts: [{text:Designer_Lang.controlAttrDataTypeText,value:"String"},
						{text:Designer_Lang.controlAttrDataTypeNumber,value:"Double"},						
						{text:Designer_Lang.controlAttrDataTypeBigNumber,value:"BigDecimal"},
						{text:Designer_Lang.controlAttrDataTypeMoney,value:"BigDecimal_Money"}],
					onchange: '_Designer_Control_Attr_InputText_DataTypeOnchange(this);',
					show: true,
					synchronous: true,
					translator:opts_common_translator,
					type: 'select'
				},
				validate :{
					text: Designer_Lang.controlInputTextAttrValidate,
					value: "false",
					synchronous: true,
					show: true,
					type: 'self',
					draw: _Designer_Control_Attr_InputText_Self_Draw,
					translator:opts_common_translator,
					opts: [{text:Designer_Lang.controlInputTextValFalse,value:"false"},
						{text:Designer_Lang.controlInputTextValString,value:"string"},						
						{text:Designer_Lang.controlInputTextValEmail,value:"email"},  
						{text:Designer_Lang.controlInputTextValPhoneNumber,value:"phoneNumber"},
						{text:Designer_Lang.fieldLayout_number_validate,value:"number"},
						{text:Designer_Lang.controlInputTextValIDCard,value:"idCard"}]
				},
				maxlength: {
					text: Designer_Lang.controlInputTextAttrMaxlength,
					show: false,
					synchronous: true,
					validator: Designer_Control_Attr_Int_Validator,
					checkout: Designer_Control_Attr_Int_Checkout
				},

				decimal: {
					text: Designer_Lang.controlInputTextAttrDecimal,
					show: false,
					validator: [Designer_Control_Attr_Required_Validator,
						Designer_Control_Attr_Int_Validator],
					checkout: Designer_Control_Attr_Int_Checkout
				},
				begin: {
					text: Designer_Lang.controlInputTextAttrBegin,
					show: false,
					validator: [Designer_Control_Attr_Number_Validator,
						Designer_InputText_NumberValue_Validator],
					checkout: [Designer_Control_Attr_Number_Checkout,
						Designer_InputText_NumberValue_Checkout]
				},
				end: {
					text: Designer_Lang.controlInputTextAttrEnd,
					show: false,
					validator: [Designer_Control_Attr_Number_Validator,
						Designer_InputText_NumberValue_Validator],
					checkout: [Designer_Control_Attr_Number_Checkout,
						Designer_InputText_NumberValue_Checkout]
				},
				thousandShow : {
					text: Designer_Lang.controlAttrThousandShow,
					value: "false",
					type: 'checkbox',
					checked: false,
					show: true
				},
				displayFormat :{
					text: Designer_Lang.controlInputTextAttrdisplayFormat,
					value: "false",
					synchronous: true,
					show: true,
					type: 'self',
					draw: _Designer_Control_Attr_InputText_DisplayFormat_Self_Draw,
					opts:[
					{"text":Designer_Lang.controlInputTextValFormatFalse,'value':'false'},
					{"text":Designer_Lang.controlInputTextValFormatZeroFill,'value':'zeroFill'},
					{"text":Designer_Lang.controlInputTextValFormatPercent,'value':'percent'},
			        {"text":Designer_Lang.controlInputTextValPhoneNumber,'value':'phoneNumber'}],
			        translator:opts_common_translator
				},
				tipInfo : Designer_Config.attrs.tipInfo,
				defaultValue: Designer_Config.attrs.defaultValue,
				formula: Designer_Config.attrs.formula,
				reCalculate: Designer_Config.attrs.reCalculate,
				defaultLength: {//默认长度，若未设置长度则取改长度
					value : '200',
					show : false
				}
			},
			info : {
				name: Designer_Lang.controlInputTextInfoName,
				preview: "input.bmp"
			},
			resizeMode : 'onlyWidth'
		},
		//===== inputText end =====
		//===== textarea begin =====
		textarea : {
			type : "textarea",
			storeType : 'field',
			inherit    : 'baseStyle',
			onDraw : _Designer_Control_Textarea_OnDraw,
			drawMobile : _Designer_Control_Textarea_DrawMobile,
			drawXML : _Designer_Control_Textarea_DrawXML,
			mobileAlign : "left",
			needTransfer: true,
			
			implementDetailsTable : true,
			attrs : {
				label : Designer_Config.attrs.label,
				canShow : {
					text: Designer_Lang.controlAttrCanShow,
					value: "true",
					type: 'hidden',
					checked: true,
					show: true
				},
				readOnly : Designer_Config.attrs.readOnly,
				width : {
					text: Designer_Lang.controlAttrWidth,
					value: "",
					type: 'text',
					show: true,
					validator: Designer_Control_Attr_Width_Validator,
					checkout: Designer_Control_Attr_Width_Checkout
				},
				height : {
					text: Designer_Lang.controlAttrHeight,
					value: "",
					type: 'text',
					show: true,
					validator: Designer_Control_Attr_Int_Validator,
					checkout: Designer_Control_Attr_Int_Checkout
				},
				required: {
					text: Designer_Lang.controlAttrRequired,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				isMark: {
					text: Designer_Lang.controlAttrIsMark,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				summary: {
					text: Designer_Lang.controlAttrSummary,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				encrypt : Designer_Config.attrs.encrypt,
				dataType : {
					text: Designer_Lang.controlAttrDataType,
					value: "String",
					synchronous: true,
					type : "hidden",
					show: true
				},
				maxlength: {
					text: Designer_Lang.controlTextareaMaxlength,
					show: true,
					synchronous: true,
					type: 'text',
					value: '',
					hint: Designer_Lang.controlTextareaMaxlength_hint,
					validator: Designer_Control_Attr_Int_Validator,
					checkout: Designer_Control_Attr_Int_Checkout
				},
				font : {
					text: Designer_Lang.controlTextLabelAttrFont,
					value: "",
					type: 'select',
					opts: Designer_Config.font.style,
					show: false
				},
				size : {
					text: Designer_Lang.controlTextLabelAttrSize,
					value: "",
					type: 'select',
					opts: Designer_Config.font.size,
					show: false
				},
				color : {
					text: Designer_Lang.controlTextLabelAttrColor,
					value: "#000",
					type: 'color',
					show: false
				},
				b : {
					text: Designer_Lang.controlTextLabelAttrBold,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: false
				},
				i : {
					text: Designer_Lang.controlTextLabelAttrItalic,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: false
				},
				underline : {
					text: Designer_Lang.controlTextLabelAttrUnderline,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: false
				},
				tipInfo : Designer_Config.attrs.tipInfo,
				defaultValue: Designer_Config.attrs.defaultValue,
				formula: Designer_Config.attrs.formula,
				reCalculate: Designer_Config.attrs.reCalculate,
				defaultLength: {//默认长度，若未设置长度则取改长度
					value : '4000',
					show : false
				}
			},
			info : {
				name: Designer_Lang.controlTextareaInfoName,
				preview: "textarea.bmp"
			},
			resizeMode : 'all'
		},
		//===== testarea end =====
		//===== inputCheckbox begin =====
		inputCheckbox : {
			type : "inputCheckbox",
			storeType : 'field',
			inherit    : 'baseStyle',
			onDraw : _Designer_Control_InputCheckbox_OnDraw,
			drawMobile : _Designer_Control_InputCheckbox_DrawMobile,
			drawXML : _Designer_Control_InputCheckbox_DrawXML,
		
			implementDetailsTable : true,
			attrs : {
				label : Designer_Config.attrs.label,
				canShow: {
					text: Designer_Lang.controlAttrCanShow,
					value: "true",
					type: 'hidden',
					checked: true,
					show: true
				},
				required: {
					text: Designer_Lang.controlAttrRequired,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},

				isMark: {
					text: Designer_Lang.controlAttrIsMark,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				summary: {
					text: Designer_Lang.controlAttrSummary,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				encrypt : Designer_Config.attrs.encrypt,
				mobileRenderType:{
					text: Designer_Lang.controlAttrMobileRenderType,
					value: 'normal',
					type: 'radio',
					opts: [{text:Designer_Lang.controlAttrMobileRenderTypeNormal,value:"normal"},{text:Designer_Lang.controlAttrMobileRenderTypeBlock,value:"block"}],
					show: true						
				},				
				alignment: {
					text: Designer_Lang.controlAttrAlignment,
					value: 'H',
					type: 'radio',
					opts: [{text:Designer_Lang.controlAttrAlignmentH,value:"H"},{text:Designer_Lang.controlAttrAlignmentV,value:"V"}],
					show: true
				},
				dataType : {
					text: Designer_Lang.controlAttrDataType,
					value: "String",
					synchronous: true,
					opts: [{text:Designer_Lang.controlAttrDataTypeText,value:"String"},
						{text:Designer_Lang.controlAttrDataTypeNumber,value:"Double"}],
					show: true,
					type: 'hidden'
				},
				items: {
					text: Designer_Lang.controlAttrItems,
					value: "",
					type: 'textarea',
					hint: Designer_Lang.controlAttrItemsHint,
					show: true,
					lang: true,
					required: true,
					validator: [Designer_Control_Attr_Required_Validator,Designer_Items_NumberType_Validator,Designer_Items_Values_Validator,Designer_Items_ValRepeat_Validator],
					checkout: [Designer_Control_Attr_Required_Checkout,Designer_Items_NumberType_Checkout,Designer_Items_Values_Checkout,Designer_Items_ValRepeat_Checkout],
					convertor: Designer_Control_Attr_ItemsTrimConvertor
				},
				font : {
					text: Designer_Lang.controlTextLabelAttrFont,
					value: "",
					type: 'select',
					opts: Designer_Config.font.style,
					show: false
				},
				size : {
					text: Designer_Lang.controlTextLabelAttrSize,
					value: "",
					type: 'select',
					opts: Designer_Config.font.size,
					show: false
				},
				color : {
					text: Designer_Lang.controlTextLabelAttrColor,
					value: "#000",
					type: 'color',
					show: false
				},
				b : {
					text: Designer_Lang.controlTextLabelAttrBold,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: false
				},
				i : {
					text: Designer_Lang.controlTextLabelAttrItalic,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: false
				},
				underline : {
					text: Designer_Lang.controlTextLabelAttrUnderline,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: false
				},
				defaultValue : {
					text: Designer_Lang.controlAttrDefaultValue,
					defaultValueHint: Designer_Lang.controlAttrItemsDefaultValueHint,
					value: "",
					type: 'defaultValue',
					show: true,
					validator: [Designer_Items_DefaultValues_Validator,Designer_Control_InputCheckBox_Validator],
					checkout: Designer_Items_DefaultValues_Checkout,
					convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
				},
				formula: Designer_Config.attrs.formula,
				reCalculate: Designer_Config.attrs.reCalculate
			},
			info : {
				name: Designer_Lang.controlInputCheckboxInfoName,
				preview: "checkbox.bmp"
			},
			resizeMode : 'no'
		},
		//===== inputCheckbox end =====
		//===== inputRadio begin =====
		inputRadio : {
			type : "inputRadio",
			storeType : 'field',
			inherit    : 'baseStyle',
			onDraw : _Designer_Control_InputRadio_OnDraw,
			drawMobile : _Designer_Control_InputRadio_DrawMobile,
			drawXML : _Designer_Control_InputRadio_DrawXML,
		
			implementDetailsTable : true,
			attrs : {
				label : Designer_Config.attrs.label,
				canShow: {
					text: Designer_Lang.controlAttrLabel,
					value: "true",
					type: 'hidden',
					checked: true,
					show: true
				},
				required: {
					text: Designer_Lang.controlAttrRequired,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				isMark: {
					text: Designer_Lang.controlAttrIsMark,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				summary: {
					text: Designer_Lang.controlAttrSummary,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				encrypt : Designer_Config.attrs.encrypt,
				mobileRenderType:{
					text: Designer_Lang.controlAttrMobileRenderType,
					value: 'normal',
					type: 'radio',
					opts: [{text:Designer_Lang.controlAttrMobileRenderTypeNormal,value:"normal"},{text:Designer_Lang.controlAttrMobileRenderTypeBlock,value:"block"}],
					show: true,
					translator:opts_common_translator			
				},				
				alignment: {
					text: Designer_Lang.controlAttrAlignment,
					value: 'H',
					type: 'radio',
					opts: [{text:Designer_Lang.controlAttrAlignmentH,value:"H"},{text:Designer_Lang.controlAttrAlignmentV,value:"V"}],
					show: true
				},
				dataType : {
					text: Designer_Lang.controlAttrDataType,
					value: "String",
					opts: [{text:Designer_Lang.controlAttrDataTypeText,value:"String"},
						{text:Designer_Lang.controlAttrDataTypeNumber,value:"Double"}],
					show: true,
					synchronous: true,
					type: 'select'
				},
				items: {
					text: Designer_Lang.controlAttrItems,
					value: "",
					type: 'textarea',
					hint: Designer_Lang.controlAttrItemsHint,
					show: true,
					required: true,
					lang: true,
					validator: [Designer_Control_Attr_Required_Validator,Designer_Items_NumberType_Validator,Designer_Items_ValRepeat_Validator],
					checkout: [Designer_Control_Attr_Required_Checkout,Designer_Items_NumberType_Checkout,Designer_Items_ValRepeat_Checkout],
					convertor: Designer_Control_Attr_ItemsTrimConvertor
				},
				font : {
					text: Designer_Lang.controlTextLabelAttrFont,
					value: "",
					type: 'select',
					opts: Designer_Config.font.style,
					show: false
				},
				size : {
					text: Designer_Lang.controlTextLabelAttrSize,
					value: "",
					type: 'select',
					opts: Designer_Config.font.size,
					show: false
				},
				color : {
					text: Designer_Lang.controlTextLabelAttrColor,
					value: "#000",
					type: 'color',
					show: false
				},
				b : {
					text: Designer_Lang.controlTextLabelAttrBold,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: false
				},
				i : {
					text: Designer_Lang.controlTextLabelAttrItalic,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: false
				},
				underline : {
					text: Designer_Lang.controlTextLabelAttrUnderline,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: false
				},
				defaultValue : {
					text: Designer_Lang.controlAttrDefaultValue,
					value: "",
					type: 'defaultValue',
					show: true,
					validator: [Designer_Items_DefaultValue_Validator,Designer_Control_InputCheckBox_Validator],
					checkout: Designer_Items_DefaultValue_Checkout,
					convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
				},
				formula: Designer_Config.attrs.formula,
				reCalculate: Designer_Config.attrs.reCalculate
			},
			info : {
				name: Designer_Lang.controlInputRadioInfoName,
				preview: "radio.bmp"
			},
			resizeMode : 'no'
		},
		//===== inputRadio end =====
		//===== select begin =====
		select : {
			type : "select",
			storeType : 'field',
			inherit : 'baseStyle',
			onDraw : _Designer_Control_Select_OnDraw,
			drawMobile : _Designer_Control_Select_DrawMobile,
			drawXML : _Designer_Control_Select_DrawXML,
			mobileAlign : "right",
			//#154128-在流程管理中，打开请假套件，进行请假，但请假类型出不来
			//在打开表单模板的时候执行这个方法
			onInitialize: _Designer_Control_Select_OnInitialize,
			
			implementDetailsTable : true,
			attrs : {
				label : Designer_Config.attrs.label,
				canShow: {
					text: Designer_Lang.controlAttrCanShow,
					value: "true",
					type: 'hidden',
					checked: true,
					show: true
				},
				required: {
					text: Designer_Lang.controlAttrRequired,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				isMark: {
					text: Designer_Lang.controlAttrIsMark,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				summary: {
					text: Designer_Lang.controlAttrSummary,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				encrypt : Designer_Config.attrs.encrypt,
				width : {
					text: Designer_Lang.controlAttrWidth,
					value: "",
					type: 'text',
					show: true,
					validator: Designer_Control_Attr_Width_Validator_Select,
					checkout: Designer_Control_Attr_Width_Checkout
				},
				dataType : {
					text: Designer_Lang.controlAttrDataType,
					value: "String",
					opts: [{text:Designer_Lang.controlAttrDataTypeText,value:"String"},
						{text:Designer_Lang.controlAttrDataTypeNumber,value:"Double"}],
					show: true,
					synchronous: true,
					type: 'select'
				},
				items: {
					text: Designer_Lang.controlAttrItems,
					value: "",
					type: 'textarea',
					hint: Designer_Lang.controlAttrItemsHint,
					show: true,
					required: true,
					lang: true,
					validator: [Designer_Control_Attr_Required_Validator,Designer_Items_NumberType_Validator,Designer_Items_DoubleType_Validator,Designer_Items_ValRepeat_Validator],
					checkout: [Designer_Control_Attr_Required_Checkout,Designer_Items_NumberType_Checkout,Designer_Items_DoubleType_Checkout,Designer_Items_ValRepeat_Checkout],
					convertor: Designer_Control_Attr_ItemsTrimConvertor
				},
				defaultValue : {
					text: Designer_Lang.controlAttrDefaultValue,
					value: "",
					type: 'defaultValue',
					show: true,
					validator: [Designer_Items_DefaultValue_Validator,Designer_Control_InputCheckBox_Validator],
					checkout: Designer_Items_DefaultValue_Checkout,
					convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
				},
				formula: Designer_Config.attrs.formula,
				reCalculate: Designer_Config.attrs.reCalculate
			},
			info : {
				name: Designer_Lang.controlSelectInfoName,
				preview: "select.bmp"
			},
			resizeMode : 'onlyWidth'
		},
		//===== select end =====
		//===== rtf begin =====
		rtf : {
			type : "rtf",
			storeType : 'field',
			inherit    : 'base',
			onDraw : _Designer_Control_Rtf_OnDraw,
			drawMobile : _Designer_Control_Rtf_DrawMobile,
			drawXML : _Designer_Control_Rtf_DrawXML,
			needTransfer: true,
			attrs : {
				label : Designer_Config.attrs.label,
				canShow: {
					text: Designer_Lang.controlAttrCanShow,
					value: "true",
					type: 'hidden',
					checked: true,
					show: true
				},
				required: {
					text: Designer_Lang.controlAttrRequired,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				width: {
					text: Designer_Lang.controlAttrWidth,
					value: "80%",
					type: 'text',
					show: true,
					validator: Designer_Control_Attr_Width_Validator,
					checkout: Designer_Control_Attr_Width_Checkout
				},
				height: {
					text: Designer_Lang.controlAttrHeight,
					value: "220",
					type: 'text',
					show: true
				},
				dataType : {
					text : Designer_Lang.controlAttrDataType,
					type : 'hidden',
					show : true,
					synchronous: true,
					value : 'RTF'
				},
				needFilter: {
					text: Designer_Lang.controlAttrNeedFilter,
					value: "true",
					type: 'checkbox',
					checked: true,
					show: false
				},
				defaultValue: Designer_Config.attrs.defaultValue,
				formula: Designer_Config.attrs.formula,
				reCalculate: Designer_Config.attrs.reCalculate
			},
			info : {
				name: Designer_Lang.controlRtfInfoName,
				preview: "rtf.bmp"
			},
			resizeMode : 'all'
		},
		//===== rtf end =====
		//===== address begin =====
		address : {
			type : "address",
			storeType : 'field',
			inherit    : 'base',
			onDraw : _Designer_Control_Address_OnDraw,
			drawXML : _Designer_Control_Address_DrawXML,
			drawMobile : _Designer_Control_Address_DrawMobile,
			implementDetailsTable : true,
			mobileAlign : "right",
			attrs : {
				label : Designer_Config.attrs.label,
				canShow : {
					text: Designer_Lang.controlAttrCanShow,
					value: "true",
					type: 'hidden',
					checked: true
				},
				readOnly : Designer_Config.attrs.readOnly,
				required: {
					text: Designer_Lang.controlAttrRequired,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				isMark: {
					text: Designer_Lang.controlAttrIsMark,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				summary: {
					text: Designer_Lang.controlAttrSummary,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				encrypt : Designer_Config.attrs.encrypt,
				width : {
					text: Designer_Lang.controlAttrWidth,
					value: "",
					type: 'text',
					show: true,
					validator: Designer_Control_Attr_Width_Validator,
					checkout: Designer_Control_Attr_Width_Checkout
				},
				businessType : {
					text: Designer_Lang.controlAddressAttrBusinessType,
					value: "addressDialog",
					type: 'hidden',
					skipLogChange:true,
					show: true
				},
				multiSelect : {
					text: Designer_Lang.controlAddressAttrMultiSelect,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true,
					onclick: '_Show_Address_DefaultValue(this.form.defaultValue);_Designer_Control_Attr_Address_SelectChange(this.form);'
				},
				orgType :{
					text: Designer_Lang.controlAddressAttrOrgType,
					value: "ORG_TYPE_PERSON",
					type: 'checkGroup',
					opts: [{text:Designer_Lang.controlAddressAttrOrgTypeOrg,value:"ORG_TYPE_ORG", name: '_org_org', onclick: "_Show_Address_DefaultValue(this.form.defaultValue);_Designer_Control_Attr_Address_SelectChange(this.form);"},
						{text:Designer_Lang.controlAddressAttrOrgTypeDept,value:"ORG_TYPE_DEPT", name: '_org_dept', onclick: "_Designer_Control_Attr_Address_SelectDept(this.form);_Show_Address_DefaultValue(this.form.defaultValue);_Designer_Control_Attr_Address_SelectChange(this.form);"},
						{text:Designer_Lang.controlAddressAttrOrgTypePost,value:"ORG_TYPE_POST", name: '_org_post', onclick: "_Show_Address_DefaultValue(this.form.defaultValue);_Designer_Control_Attr_Address_SelectChange(this.form);"},
						{text:Designer_Lang.controlAddressAttrOrgTypePerson,value:"ORG_TYPE_PERSON", name: '_org_person', checked: true, onclick: "_Show_Address_DefaultValue(this.form.defaultValue);_Designer_Control_Attr_Address_SelectChange(this.form);"},
						{text:Designer_Lang.controlAddressAttrOrgTypeGroup,value:"ORG_TYPE_GROUP", name: '_org_group', onclick: "_Designer_Control_Attr_Address_SelectGroup(this.form);_Show_Address_DefaultValue(this.form.defaultValue);_Designer_Control_Attr_Address_SelectChange(this.form);"}],
					show: true,
					validator : Designer_Address_OrgType_Validator,
					checkout: Designer_Address_OrgType_Checkout,
				    getVal: Designer_Address_OrgType_getVal,
				    translator:opts_common_translator_many
				},
				maxPersonNum : {
					text: Designer_Lang.controlAddressMaxPersonNumText,
					value: "",
					type: 'self',
					show: true,
					draw: _Designer_Control_Attr_MaxPersonNum_Draw,
					validator: Designer_Control_Attr_MaxPersonNum_Validator,
					checkout: Designer_Control_Attr_MaxPersonNum_Checkout
				},
				defaultValue: {
					text: Designer_Lang.controlAttrDefaultValue,
					value: "null",
					type: 'self',
					draw: _Designer_Control_Attr_Address_Self_Draw,
					opts: [{text:Designer_Lang.controlAddressAttrDefaultValueNull,value:"null"},
						{text:Designer_Lang.controlAddressAttrDefaultValueSelf,value:"ORG_TYPE_PERSON"},
						{text:Designer_Lang.controlAddressAttrDefaultValueSelfOrg,value:"ORG_TYPE_ORG"},
						{text:Designer_Lang.controlAddressAttrDefaultValueSelfDept,value:"ORG_TYPE_DEPT"},
						{text:Designer_Lang.controlAddressAttrDefaultValueSelfPost,value:"ORG_TYPE_POST"},
						{text:Designer_Lang.controlAddressAttrDefaultValueSelect,value:"select"}],
					show: true,
					validator : Designer_Address_DefaultValue_Validator,
					checkout: Designer_Address_DefaultValue_Checkout,
					translator:opts_common_translator
				},
				formula: Designer_Config.attrs.formula,
				reCalculate: Designer_Config.attrs.reCalculate
			},
			onAttrLoad : _Designer_Control_Attr_Address_OnAttrLoad,
			info : {
				name: Designer_Lang.controlAddressInfoName,
				preview: "address.bmp"
			},
			resizeMode : 'onlyWidth'
		},
		//===== address end =====
		//===== datetime begin =====
		datetime : {
			type : "datetime",
			storeType : 'field',
			inherit    : 'base',
			onDraw : _Designer_Control_Datetime_OnDraw,
			drawMobile : _Designer_Control_Datetime_DrawMobile,
			drawXML : _Designer_Control_Datetime_DrawXML,
			implementDetailsTable : true,
			mobileAlign : "right",
			attrs : {
				label : Designer_Config.attrs.label,
				canShow : {
					text: Designer_Lang.controlAttrCanShow,
					value: "true",
					type: 'hidden',
					checked: true,
					show: true
				},
				readOnly : Designer_Config.attrs.readOnly,
				showScheduling: {
					text : Designer_Lang.controlAttrShowScheduling,
					value : false,
					type : 'checkbox',
					show: true
				},
				required: {
					text: Designer_Lang.controlAttrRequired,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				isMark: {
					text: Designer_Lang.controlAttrIsMark,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				summary: {
					text: Designer_Lang.controlAttrSummary,
					value: "true",
					type: 'checkbox',
					checked: false,
					show: true
				},
				width : {
					text: Designer_Lang.controlAttrWidth,
					value: "",
					type: 'text',
					show: true,
					validator: Designer_Control_Attr_Width_Validator,
					checkout: Designer_Control_Attr_Width_Checkout
				},
				businessType : {
					text: Designer_Lang.controlDatetimeAttrBusinessType,
					value: "dateDialog",
					type: 'select',
					opts: [{text:Designer_Lang.controlDatetimeAttrBusinessTypeDate,value:"dateDialog"},
						{text:Designer_Lang.controlDatetimeAttrBusinessTypeTime,value:"timeDialog"},
						{text:Designer_Lang.controlDatetimeAttrBusinessTypeDatetime,value:"datetimeDialog"}],
					onchange: "_Designer_Control_Attr_Datetime_ShowDefaultValue(this.form.defaultValue)",
					show: true,
					translator:opts_common_translator

				},
				// 维度
				dimension : {
					text: Designer_Lang.controlDatetimeAttrDimension,
					value: "yearMonthDay",
					type: 'select',
					opts: [{text:Designer_Lang.controlDatetimeAttrYearMonthDay,value:"yearMonthDay"},
						{text:Designer_Lang.controlDatetimeAttrYearMonth,value:"yearMonth"},
						{text:Designer_Lang.controlDatetimeAttrYear,value:"year"}],
					show: true,
					onchange: "_Designer_Control_Attr_Datetime_ShowDefaultValue(this.form.defaultValue)",// 这里主要是为了生成对应的时间选择框窗口
					translator:opts_common_translator
				},
				tipInfo : Designer_Config.attrs.tipInfo,
				defaultValue: {
					text: Designer_Lang.controlAttrDefaultValue,
					value: "null",
					type: 'self',
					opts: [{text:Designer_Lang.controlAddressAttrDefaultValueNull,value:"null"},
						{text:Designer_Lang.controlDatetimeAttrDefaultValueNowTime,value:"nowTime"},
						{text:Designer_Lang.controlDatetimeAttrDefaultValueSelect,value:"select"}],
					show: true,
					draw: _Designer_Control_Attr_Datetime_Self_Draw,
					validator : Designer_Datetime_DefaultValue_Validator,
					checkout : Designer_Datetime_DefaultValue_Checkout,
					translator:opts_common_translator
				}, //formula: Designer_Config.attrs.formula,
				reCalculate: Designer_Config.attrs.reCalculate
			},
			onAttrLoad : _Designer_Control_Attr_Datetime_OnAttrLoad,
			info : {
				name: Designer_Lang.controlDatetimeInfoName,
				preview: "date.bmp"
			},
			resizeMode : 'onlyWidth'
		},
		//===== datetime end =====
		//===== standardTable begin =====
		standardTable : {
			type : "standardTable",
			storeType : 'layout',
			inherit    : 'table',
			onDraw : _Designer_Control_StandardTable_OnDraw,
			onDrawEnd : _Designer_Control_StandardTable_OnDrawEnd,
			drawXML : _Designer_Control_StandardTable_DrawXML,
			implementDetailsTable : true,
			insertValidate:function(cell, control) {
				//普通表格插入明细表后，不能插入明细表中的控件也不能插入普通表格
				if (control.implementDetailsTable != true && cell) {
				    var inDetails = $(cell).parents("table[showStatisticRow]").length > 0;
				    if (inDetails) {
                        var detailsTableDom = Designer.GetDetailsTable(cell);
                        var detailsTableObj = control.owner.getControlByDomElement(detailsTableDom);
                        if (detailsTableObj.isAdvancedDetailsTable) {
                            alert(Designer_Lang.controlSeniorDetailsTable_notSupportControl);
                        } else {
                            alert(Designer_Lang.controlDetailsTable_notSupportControl);
                        }
                        return false;
                    }
				}
				return true;
			},
			//onInitialize:_Designer_Contro_StandardTable_DoInitialize,
			onShiftDrag   : _Designer_Control_Base_DoDrag,
			onShiftDragMoving: _Designer_Control_Base_DoDragMoving,
			onShiftDragStop: _Designer_Control_Base_DoDragStop,
			onDragStop    : _Designer_Control_StandardTable_OnDragStop,     //拖拽结束
			// 属性框点击OK的时候执行
			onAttrSuccess:function(){
				if(this.options.domElement){
					// 由于容器类型，在属性框关闭的时候，不会重新执行ondraw方法，故在此处设置id
					this.options.domElement.id = this.options.values.id;
				}
			},
			onAttrApply : function(){
				this.onAttrSuccess();
			},
			onDrag:_Designer_Control_StandardTable_OnDrag,
			onDragMoving:function(event){
				if(Designer.UserAgent == 'msie') {
					this.options.domElement.setCapture();
					event.cancelBubble = true;
				} else {
					event.preventDefault();
					event.stopPropagation();
				}
				window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
				//控件锁定，则不能移动
				if (event.ctrlKey) return;
				if (this.dragAction.onMove)
					this.dragAction.onMove(event, this);
				
				this.onDragMovingChooseCell(event);
				
			},
			onDragMovingChooseCell : _Designer_Control_StandardTable_OnDragMovingChooseCell,
			attrs : {
				label : {
					text : Designer_Lang.controlAttrLabel,
					value: "",
					type: 'text',
					show: true,
					required: true,
					validator: Designer_Control_Attr_Required_Validator,
					checkout: Designer_Control_Attr_Required_Checkout
				},
				tableStyle : {
					text : Designer_Lang.controlStandardTableStyle,
					value : '',
					type : 'self',
					draw : _Designer_Control_Attr_standardTable_style_Self_Draw,
					//checkout:Designer_Layout_Control_fieldParams_Required_Checkout,
					translator:tableStyleOutputParams_translator,
					show : true
				},
				help:{
					text: Designer_Lang.controlStandardTableLabelHelp,
					type: 'help',
					align:'left',
					show: true
				},
				width : {
					text: Designer_Lang.controlAttrWidth,
					value: "",
					type: 'text',
					show: true,
					validator: Designer_Control_Attr_Width_Validator,
					checkout: Designer_Control_Attr_Width_Checkout
				}
			},
			domAttrs : {
				td : {
					chooseTable:{
						text: "<a href='javascript:void(0)' style='text-decoration:underline' onclick='if(Designer.instance.attrPanel.panel.control.onUnLock)Designer.instance.attrPanel.panel.control.onUnLock();Designer.instance.attrPanel.open();'>"+Designer_Lang.controlStandardTableOpenTableAttr+"</a>",
						type: 'help',
						align:'right'
					}
					,
					style_textAlign: {
						text: Designer_Lang.controlStandardTableDomAttrTdAlign,
						value: "left",
						type: 'radio',
						opts: [
							{text:Designer_Lang.controlStandardTableDomAttrTdAlignLeft,value:"left"}, // left
							{text:Designer_Lang.controlStandardTableDomAttrTdAlignCenter,value:"center"},
							{text:Designer_Lang.controlStandardTableDomAttrTdAlignRight,value:"right"}
						]
					},
					style_verticalAlign: {
						text: Designer_Lang.controlStandardTableDomAttrTdVAlign,
						value: "middle",
						type: 'radio',
						opts: [
							{text:Designer_Lang.controlStandardTableDomAttrTdVAlignTop,value:"top"},
							{text:Designer_Lang.controlStandardTableDomAttrTdVAlignMiddle,value:"middle"}, // middle
							{text:Designer_Lang.controlStandardTableDomAttrTdVAlignBottom,value:"bottom"}
						]
					},
					className: {
						text: Designer_Lang.controlStandardTableDomAttrTdClassName,
						value: "tb_normal",
						type: 'radio',
						opts: [
							{text:Designer_Lang.controlStandardTableDomAttrTdClassNameNormal,value:"tb_normal"}, // tb_normal
							{text:Designer_Lang.controlStandardTableDomAttrTdClassNameTitle,value:"td_normal_title"}
						]
					},
					style_borderLeftWidth : {
						text: Designer_Lang.controlStandardTableLeftBorder,
						value: "null",
						type: 'select',
						opts: [
						       	{text:Designer_Lang.controlStandardTableBorderDefault,value:"null"},
						       	{text:'0px',value:"0px"},
								{text:'1px',value:"1px"}, // tb_normal
								{text:'2px',value:"2px"},
								{text:'3px',value:"3px"},
								{text:'4px',value:"4px"},
								{text:'5px',value:"5px"}
							]
					}
					,
					style_borderRightWidth : {
						text: Designer_Lang.controlStandardTableRightBorder,
						value: "null",
						type: 'select',
						opts: [
						   	{text:Designer_Lang.controlStandardTableBorderDefault,value:"null"},
					       	{text:'0px',value:"0px"},
							{text:'1px',value:"1px"}, // tb_normal
							{text:'2px',value:"2px"},
							{text:'3px',value:"3px"},
							{text:'4px',value:"4px"},
							{text:'5px',value:"5px"}
							]
					},
					style_borderTopWidth : {
						text: Designer_Lang.controlStandardTableTopBorder,
						value: "null",
						type: 'select',
						opts: [
						       	{text:Designer_Lang.controlStandardTableBorderDefault,value:"null"},
						       	{text:'0px',value:"0px"},
								{text:'1px',value:"1px"}, // tb_normal
								{text:'2px',value:"2px"},
								{text:'3px',value:"3px"},
								{text:'4px',value:"4px"},
								{text:'5px',value:"5px"}
							]
					},
					style_borderBottomWidth : {
						text: Designer_Lang.controlStandardTableBottomBorder,
						value: "null",
						type: 'select',
						opts: [
						   	{text:Designer_Lang.controlStandardTableBorderDefault,value:"null"},
					       	{text:'0px',value:"0px"},
							{text:'1px',value:"1px"}, // tb_normal
							{text:'2px',value:"2px"},
							{text:'3px',value:"3px"},
							{text:'4px',value:"4px"},
							{text:'5px',value:"5px"}
							]
					}
					,
					style_width: {
						text: Designer_Lang.controlAttrWidth,
						value: "auto",
						type: 'text',
						validator: Designer_Control_Attr_Width_Validator
					}
					,
					//当form里面只有一个input type=‘text’时，ie下默认按enter即提交内容，解决方案：1.设置keypress方法；2、增加多一个input type=‘text’，设为隐藏，此处多做一个隐藏text
					hidden_text: {
						text: '',
						value: "",
						type: 'text',
						show: false
					}
					,
					help:{
						text: Designer_Lang.controlStandardTableLabelHelp,
						type: 'help',
						align:'left'
					}
				}
			},
			info : {
				name: Designer_Lang.controlStandardTableInfoName,
				td: Designer_Lang.controlStandardTableInfoTd
			},
			resizeMode : 'no'
		}
		//===== standardTable end =====
	}

// =================== 控件绘制 
// 生成控件Dom对象
function _CreateDesignElement(type, control, parentNode, childNode) {
	var domElement = document.createElement(type);
	domElement.setAttribute("formDesign", "landray");
	domElement.style.display = 'inline';
	//用于模板提交保存的时候，计算在移动端流程文档的td里面有多少个可视化控件
	if(control.hideInMainModel && control.hideInMainModel == true){
		domElement.setAttribute("fd_hideInMainModel", "true");
	}
	control.options.domElement = domElement;
	parentNode.insertBefore(domElement, childNode);
	return domElement;
}

_Designer_Index_Object = {
	label : 1,
	textLabel : 1,
	linkLabel : 1,
    advancedDetailsTableLabel : 1,
	test: 1,
	table: 0
};
function _Get_Designer_Control_Label(values, control) {
	if (control.attrs.label && values._label_bind == null) {
		values._label_bind = 'true';
	}
	if (values._label_bind == 'true') {
		var textLabel = control.getRelatedTextControl();
		values.label = textLabel ? textLabel.options.values.content : '';
		values._label_bind_id = textLabel ? textLabel.options.values.id : '';
	}
	if(values.label){
		values.label = Designer.ClearStrSensiChar(values.label);
	}
	return values.label;
}

function _Get_Designer_Control_TableName(parent) {
	if (parent == null) return "";
	var tableName = parent.options.domElement.id;
	//div控件不作为父表格控件 作者 曹映辉 #日期 2016年7月22日
	if (tableName == null || tableName == "" ||parent.type=='divcontrol') {
		tableName = _Get_Designer_Control_TableName(parent.parent);
	}
	return tableName;
}


//生成文本标签Dom对象
function _Designer_Control_TextLabel_OnDraw(parentNode, childNode){
	var domElement = _CreateDesignElement('label', this, parentNode, childNode);
	domElement.className="xform_label";
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	if(this.options.values.content==null || this.options.values.content==''){
		this.options.values.content = Designer_Lang.controlTextLabelInfoName + _Designer_Index_Object.textLabel ++;
		domElement.innerHTML = this.options.values.content;
	} else {
		//chrome和ie等浏览器换行符不一致
		var html = this.options.values.content.replace(/\r\n/g, '<br>').replace(/\n/g, '<br>');
		html = html.replace(/ /g, '&nbsp;');
		domElement.innerHTML = html;
	}
	if(this.options.values.font)	
	_Designer_Control_TextLabel_SetStyle(domElement, this.options.values.font, "fontFamily");
	if(this.options.values.size)
	_Designer_Control_TextLabel_SetStyle(domElement, this.options.values.size, "fontSize");
	if(this.options.values.color)
	_Designer_Control_TextLabel_SetStyle(domElement, this.options.values.color, "color");
	if(this.options.values.isHiddenInMobile)
		domElement.setAttribute("isHiddenInMobile",this.options.values.isHiddenInMobile);
	if(this.options.values.b=="true") domElement.style.fontWeight="bold";
	if(this.options.values.i=="true") domElement.style.fontStyle = "italic";
	if(this.options.values.underline=="true") domElement.style.textDecoration="underline";
	//domElement.style.fontWeight = this.options.values.b=="true"?"bold":"normal";
	//domElement.style.fontStyle = this.options.values.i=="true"?"italic":"normal";
	//domElement.style.textDecoration = this.options.values.underline=="true"?"underline":"none";
	domElement.style.width="auto";
	
	$(domElement).css("word-break","break-all");
	$(domElement).css("word-wrap","break-word");
	if (this.options.values.line == "breakWord"){
		$(domElement).css("word-break","break-word");
	}
	$(domElement).attr("line",this.options.values.line || "normal");
}

function _Designer_Control_TextLabel_OnDrawEnd() {
	_Designer_Control_TextLabel_OnDrawReleaseEvent(this, this.owner.controls);
	_Designer_Control_TextLabel_OnDrawRelationEvent(this);
}

_Designer_Control_TextLabel_BorderType = {left: 'right', right: 'left', up : 'down', down: 'up', self : 'self'};

function _Designer_Control_TextLabel_OnDrawReleaseEvent(textLabel, controls) {
	for (var i = 0; i < controls.length; i ++) {
		var c = controls[i];
		if (!c.isTextLabel && c.options.values._label_bind == 'true'
			&& textLabel.options.values.id == c.options.values._label_bind_id) {
			textLabel.owner.setProperty(c);
		}
		_Designer_Control_TextLabel_OnDrawReleaseEvent(textLabel, c.children);
	}
}

function _Designer_Control_TextLabel_OnDrawRelationEvent(textLabel) {
	var parent = textLabel.parent, currElement = textLabel.options.domElement, controls = [];
	if (parent && parent.getTagName() == 'table') {
		var pCell = textLabel.getBorderCell(currElement, _Designer_Control_TextLabel_BorderType[ parent.getRelateWay ? parent.getRelateWay(textLabel) : (parent.relatedWay ? parent.relatedWay : 'left') ]);
		if (pCell == null) return null;
		//获得后一单元格里所有控件
		var children = pCell.childNodes, element, control;
		for (var i = children.length - 1; i >= 0; i--) {
			element = children[i];
			if (element.nodeType != 3 && Designer.isDesignElement(element)) {
				control = parent.getControlByDomElement(element);
				if (control && !control.isTextLabel) controls.push(control);
			}
		}
	} else {
		var element = currElement.nextSibling;
		if (element && element.nodeType != 3 && Designer.isDesignElement(element)) {
			control = textLabel.owner.getControlByDomElement(element);
			if (control && !control.isTextLabel) controls.push(control);
		}
	}
	for (var i = 0; i < controls.length; i ++) {
		textLabel.owner.setProperty(controls[i]);
		// 设置完属性之后，重新初始化数据字典
		if(controls[i].onInitializeDict){
			controls[i].onInitializeDict();
		}
	}
}

function _Designer_Control_TextLabel_SetStyle(domElement, targetValue, styleName){
	if(targetValue==null || targetValue=='') {
		domElement.style[styleName] = null;
	} else {
		domElement.style[styleName] = targetValue;
	}
}

//生成单行文本Dom对象
function _Designer_Control_InputText_OnDraw(parentNode, childNode){
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	domElement.className="xform_inputText";
	if(this.options.values.font) _Designer_Control_Common_SetStyle(domElement, this.options.values.font, "fontFamily");
	if(this.options.values.size) _Designer_Control_Common_SetStyle(domElement, this.options.values.size, "fontSize");
	if(this.options.values.color) _Designer_Control_Common_SetStyle(domElement, this.options.values.color, "color");
	if(this.options.values.b=="true") domElement.style.fontWeight="bold";
	if(this.options.values.i=="true") domElement.style.fontStyle = "italic";
	if(this.options.values.underline=="true") domElement.style.textDecoration="underline";
	//单行文本改为自动换行  作者 曹映辉 #日期 2016年5月18日 ，在百分比的情况下自动换行
	var _length = 12;
	if(this.options.values.displayFormat === "percent"){
		_length += 20;
	}
	if (this.options.values.width){
		if(this.options.values.width.toString().indexOf('%') > -1) {
			domElement.style.width = this.options.values.width;
		}
		else{
			//+5是为了防止设置必填的时候“*”号被换行
		    domElement.style.width =(parseInt(this.options.values.width)+_length) + 'px';
		}
	}
	else{
		this.options.values.width=120;
		 domElement.style.width =(parseInt(this.options.values.width)+_length) + 'px';
	}
	$(domElement).css("display","inline-block");
	var htmlCode = _Designer_Control_InputText_DrawByType(this.parent, this.attrs, this.options.values, this);
	domElement.innerHTML = htmlCode;
	
	if (this.options.values.canShow == 'false') {
		domElement.setAttribute('canShow', 'false');
	} else {
		domElement.setAttribute('canShow', 'true');
	}
	var fd_values = this.options.values;
	if (fd_values.dataType === "Double" ||fd_values.dataType === "BigDecimal" 
		|| fd_values.dataType === "BigDecimal_Money") {
		if (fd_values.decimal) {
			fd_values.scale = fd_values.decimal;
		}
	}
	$(domElement).css("word-break","break-all");
	$(domElement).css("word-wrap","break-word");
}

// 生成XML
function _Designer_Control_InputText_DrawXML() {
	var values = this.options.values;
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="',values.label, '" ');
	
	buf.push('type="', values.dataType ? (values.dataType.indexOf('BigDecimal') > -1 ? 'BigDecimal' : values.dataType) : 'String', '" ');
	// 控件类型
	buf.push('businessType="', this.type, '" ');
	if (values.validate == 'string') {
		if ( values.maxlength != null &&  values.maxlength != '') {
			buf.push('length="', values.maxlength, '" ');
		} else {
			buf.push('length="', this.attrs.defaultLength.value, '" ');
		}
	} else if (values.validate == 'number') {
		if(values.dataType=='BigDecimal'){
			buf.push('length="', '16', '" ');
		}
		else{
			buf.push('length="', '16', '" ');
		}
		if (values.decimal == "0") {
			buf.push('scale="', 0, '" ');
		} else {
			buf.push('scale="', values.decimal, '" ');
		}
	} else {
		buf.push('length="200" ');
	}
	if (values.required == "true") {
		buf.push('notNull="true" ');
	}
	if (values.formula != '' && values.formula != null) {
		buf.push('formula="true" ');
		buf.push('defaultValue="', values.formula, '" ');
		if (values.reCalculate == 'true') {
			buf.push('recalculateOnSave="true" ');
		}
	} else if (values.defaultValue != '' && values.defaultValue != null) {
		buf.push('defaultValue="', values.defaultValue, '" ');
	}
	if(values.readOnly && values.readOnly == 'true'){
		buf.push('readOnly="true" ');
	}
	if (values.canShow == 'false') {
		buf.push('canDisplay="false" ');
	}
	
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	
	//摘要汇总
	if(values.summary == 'true'){
		buf.push('summary="true" ');
	}
	
	if(values.isMark == 'true'){
		buf.push('isMark="true" ');
	}else{
		buf.push('isMark="false" ');
	}
	
	//是否拼接明细表的索引号
	if(values.concatSubTableRowIndex == 'true' && values.dataType == 'String'){
		buf.push('concatSubTableRowIndex="true" ');
	}

	//显示格式
	var customElementProperties = {};
	if (values.thousandShow == 'true') {
		customElementProperties.thousandShow = 'true';
	}
	if (values.dataType == "String") {
		if (values.displayFormat == 'phoneNumber') {
			customElementProperties.displayFormat = {'displayFormat':'phoneNumber'};
		}
	} else if (values.dataType == "Double") {
		if (values.displayFormat == 'zeroFill') {
			customElementProperties.displayFormat = {'displayFormat':'zeroFill','zeroFillFormat':values.zeroFill};
		} else if (values.displayFormat == 'percent') {
			customElementProperties.displayFormat = {'displayFormat':'percent'};
		} else if (values.displayFormat == 'false') {
			customElementProperties.displayFormat = {'displayFormat':'false'};
		}
	} else if (values.dataType == "BigDecimal_Money") {
		customElementProperties.scale = values.decimal;
		//金额类型，默认显示千分位
		customElementProperties.thousandShow = 'true';
	}
	buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');

	buf.push('/>');
	return buf.join('');
}

// input text 类型HTML生成基础函数
function _Designer_Control_InputText_DrawByType(parent, attrs, values, control) {
	var htmlCode = '<input id="'+values.id+'" class="' + (values.canShow=='false'?'inputhidden':'inputsgl');
	if (values.canShow == 'false') {
		htmlCode += '" canShow="false"';
	} else {
		htmlCode += '" canShow="true"';
	}
	if (values.thousandShow == 'false') {
		htmlCode += ' thousandShow="false"';
	} else {
		htmlCode += ' thousandShow="true"';
	}
	if (values.readOnly == 'true') {
		htmlCode += ' _readOnly="true"';
	} else {
		htmlCode += ' _readOnly="false"';
	}
	if(values.showScheduling == 'true') {
		htmlCode += ' showScheduling="true"';
	} else {
		htmlCode += ' showScheduling="false"';
	}
	
	if (values.dataType == "String"){
		if (values.displayFormat == 'phoneNumber'){
			htmlCode += 'displayFormat={\'displayFormat\':\'phoneNumber\'}';
		}
	}else if (values.dataType == "Double"){
		if (values.displayFormat == 'zeroFill'){
			htmlCode += 'displayFormat={\'displayFormat:\'zeroFill\',\'zeroFillFormat:\'' + values.zeroFill + '\'}';
		}else if (values.displayFormat == 'percent'){
			htmlCode += 'displayFormat={\'displayFormat\':\'percent\'}';
		}else if (values.displayFormat == 'false'){
			htmlCode += 'displayFormat={\'displayFormat\':\'false\'}';
		}
	}else{
		htmlCode += ' ';
	}
	
	if(values.tipInfo != null && values.tipInfo != ''){
		htmlCode += '" tipInfo ="' + values.tipInfo + '"';
	}
	if(values.width==null || values.width==''){
		values.width = "120";
	}
	if (values.width.toString().indexOf('%') > -1) {
		var pWidth = values.width;
		htmlCode += ' inputwidth=' + pWidth;
		htmlCode += ' style="width:'+pWidth+'';
		if (values.color){
			htmlCode += 'color:' + values.color + ';"';
		}else{
			htmlCode += '"';
		}
	} else {
		htmlCode += ' style="width:'+values.width+'px;';
		if (values.color){
			htmlCode += 'color:' + values.color + ';"';
		}else{
			htmlCode += '"';
		}
		htmlCode += ' inputwidth="' + values.width + 'px"';
	}
	
	if (values.required == "true") {
		htmlCode += ' required="true"'
		htmlCode += ' _required="true"'
	} else {
		htmlCode += ' required="false"'
		htmlCode += ' _required="false"'
	}
	//摘要汇总
	if(values.summary == "true"){
		htmlCode += ' summary="true"';
	}else{
		htmlCode += ' summary="false"';
	}
	//是否留痕
	if(values.isMark == "true"){
		htmlCode += ' isMark="true"';
	}else{
		htmlCode += ' isMark="false"';
	}
	//地址本限制选择人员数量
	if(control.type == "address" && values.maxPersonNum && parseInt(values.maxPersonNum) != 0){
		htmlCode += ' maxPersonNum="' + values.maxPersonNum +'"';
	}
	if (parent != null) {
		htmlCode += ' tableName="' + _Get_Designer_Control_TableName(parent) + '"';
	}
	if (values.description != null) {
		htmlCode += ' description="' + values.description + '"';
	}
	htmlCode += ' label="' + _Get_Designer_Control_Label(values, control) + '"';
	if (values.validate != null && values.validate != 'false') {
		htmlCode += ' dataType="' + values.validate + '"';
		htmlCode += ' _dataType="' + values.dataType + '"';
		if (values.validate == 'string') {
			htmlCode += ' maxlength="' + values.maxlength + '"';
			htmlCode += ' validate="{dataType:\'string\'';
			if ( values.maxlength != null &&  values.maxlength != '') {
				htmlCode += ',maxlength:' + values.maxlength;
			}
			htmlCode += '}"';
		} else if (values.validate == 'number') {
			htmlCode += ' scale="' + values.decimal + '"'; // 小数位
			htmlCode += ' beginNum="' +  values.begin + '"';
			htmlCode += ' endNum="' +  values.end + '"';
			htmlCode += ' validate="{dataType:\'number\',decimal:' + values.decimal; // 小数位
			if (values.begin != null && values.begin != '')
				htmlCode += ',begin:' + values.begin;
			if (values.end != null && values.end != '')
				htmlCode += ',end:' + values.end;
			htmlCode += '}"';
		} else if (values.validate == 'email') {
			htmlCode += ' validate="{dataType:\'email\'}"';
		}else if (values.validate == "phoneNumber"){
			htmlCode += ' validate="{dataType:\'phoneNumber\'}"';
		}else if (values.validate == "idCard"){
			htmlCode += ' validate="{dataType:\'idCard\'}"';
		}
		
	}
	if (values.defaultValue != null && values.defaultValue != '') {
		htmlCode += ' defaultValue="' + values.defaultValue + '"';
		if (!attrs.businessType) {
			htmlCode += ' value="' + values.defaultValue + '"';
		}
	}
	if (attrs.businessType) {
		htmlCode += ' businessType="' + (values.businessType == null ? attrs.businessType.value : values.businessType) + '"';
		if ((values.businessType == 'dateDialog7' || values.businessType == 'dateDialog' ||  values.businessType == 'timeDialog' ||  values.businessType == 'datetimeDialog')) {
			if (values.defaultValue == 'select') {
				htmlCode += ' selectedValue="' + values._selectValue + '"';
				htmlCode += ' value="' + values._selectValue + '"';
			} else if (values.defaultValue == 'nowTime') {
				htmlCode += ' value="' + attrs.defaultValue.opts[1].text + '"';
			}
		} else if (values.businessType == "addressDialog") {
			if (values.defaultValue == 'select') {
				htmlCode += ' selectedValue="' + values._selectValue + '"';
				htmlCode += ' selectedName="' + values._selectName + '"';
				htmlCode += ' value="' + values._selectName + '"';
			} else if (values.defaultValue != 'null') {
				for (var jj = 0, l = attrs.defaultValue.opts.length; jj < l; jj ++) {
					if (attrs.defaultValue.opts[jj].value == values.defaultValue) {
						htmlCode += ' value="' + attrs.defaultValue.opts[jj].text + '"';
						break;
					}
				}
			}
		}
	}
	if (values.dimension != null && values.dimension != '') {
		htmlCode += ' dimension="' + values.dimension + '"';

	}

	if (attrs.multiSelect) {
		htmlCode += ' multiSelect="' + (values.multiSelect == 'true' ? 'true' : 'false') + '"';
	}
	if (attrs.orgType) {
		var orgType = [];
		var opts = attrs.orgType.opts;
		if (values['_org_group'] == "true") {
			for (var i = 0; i < opts.length; i ++) {
				values[opts[i].name] = "true"
			}
		} else if (values['_org_dept'] == "true") {
			values['_org_org'] = "true"
		}
		for (var i = 0; i < opts.length; i ++) {
			var opt = opts[i];
			if (values[opt.name] == null && attrs.orgType.value != null && opts[i].value == attrs.orgType.value) {
				values[opt.name] = "true";
			}
			if (values[opt.name] == "true") {
				orgType.push(opt.value);
			}
		}
		values._orgType = orgType.join('|');
		htmlCode += ' orgType="' + values._orgType + '"';
	}
	if (attrs.history) {
		htmlCode += ' history="' + (values.history == "true" ? 'true' : 'false') + '"';
	}
	htmlCode += ' readOnly >';
	
	if(values.required == 'true') {
		htmlCode += '<span class=txtstrong>*</span>';
	}
	
	if (attrs.businessType && values.readOnly != 'true') {
		htmlCode += '<label>&nbsp;<a>'+Designer_Lang.controlAttrSelect+'</a></label>';
	}
	
	return htmlCode;
}

//生成地址本Dom对象
function _Designer_Control_Address_OnDraw(parentNode, childNode) {
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var domElement = _CreateDesignElement('label', this, parentNode, childNode);
	domElement.className="xform_address";
	if (this.options.values.width && this.options.values.width.toString().indexOf('%') > -1) {
		//domElement.style.whiteSpace = 'nowrap';
		domElement.style.width = this.options.values.width;
		//domElement.style.overflow = 'visible';
	}
	var htmlCode = _Designer_Control_InputText_DrawByType(this.parent, this.attrs, this.options.values, this);
	//当控件只读的时候，只有展示样式只有设置为inline-block才对居左、居中、居右有效
	if (this.options.values.readOnly && this.options.values.readOnly == 'true') {
		domElement.style.display = 'inline-block';
	}
	domElement.innerHTML = htmlCode;
}

// 生成XML
function _Designer_Control_Address_DrawXML() {
	var values = this.options.values;
	var buf = [];//mutiValueSplit
	buf.push('<extendElementProperty ');
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	if('ORG_TYPE_PERSON'==values._orgType){
		buf.push('type="com.landray.kmss.sys.organization.model.SysOrgPerson" ');
	}
	else{
		buf.push('type="com.landray.kmss.sys.organization.model.SysOrgElement" ');
	}
	if (values.required == "true") {
		buf.push('notNull="true" ');
	}
	if (values.multiSelect == 'true') {
		buf.push('mutiValueSplit=";" ');
	}
	if (values.defaultValue == 'select') {
		buf.push('formula="true" ');
		buf.push('defaultValue="OtherFunction.getModel(&quot;', values._selectValue
			, '&quot;, &quot;com.landray.kmss.sys.organization.model.SysOrgElement&quot;');
		if (values.multiSelect == "true") {
			buf.push(', &quot;;&quot;');
		} else {
			buf.push(', null');
		}
		buf.push(')" ');
	} else if (values.defaultValue != '' && values.defaultValue != 'null') {
		//ORG_TYPE_PERSON ORG_TYPE_ORG ORG_TYPE_DEPT ORG_TYPE_POST
		var dv = null;
		if (values.defaultValue == 'ORG_TYPE_PERSON') {
			dv = 'OrgFunction.getCurrentUser()';
		} else if (values.defaultValue == 'ORG_TYPE_ORG') {
			dv = 'OrgFunction.getCurrentOrg()';
		} else if (values.defaultValue == 'ORG_TYPE_DEPT') {
			dv = 'OrgFunction.getCurrentDept()';
		} else if (values.defaultValue == 'ORG_TYPE_POST') {
			if (values.multiSelect == 'true') {
				dv = 'OrgFunction.getCurrentPosts()';
			} else {
				dv = 'OrgFunction.getCurrentPost()';
			}
		}
		buf.push('defaultValue="', dv, '" ');
		buf.push('formula="true" ');
	}
	if(values.readOnly && values.readOnly == 'true'){
		buf.push('readOnly="true" ');
	}
	buf.push('dialogJS="Dialog_Address(!{mulSelect}, \'!{idField}\',\'!{nameField}\', \';\',', values._orgType,');" ');
	
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	
	var customElementProperties = {};
	customElementProperties.orgType = values._orgType;
	buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');
	buf.push('businessType="', this.type, '" ');
	//摘要汇总
	if(values.summary == 'true'){
		buf.push('summary="true" ');
	}
	
	if(values.isMark == 'true'){
		buf.push('isMark="true" ');
	}else{
		buf.push('isMark="false" ');
	}
	
	if(values.maxPersonNum && parseInt(values.maxPersonNum) != 0){
		buf.push('maxPersonNum="',values.maxPersonNum,'" ');
	}
	
	buf.push('/>');
	return buf.join('');
}

//生成日期Dom对象
function _Designer_Control_Datetime_OnDraw(parentNode, childNode) {
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var domElement = _CreateDesignElement('label', this, parentNode, childNode);
	domElement.className="xform_datetime";
	if (this.options.values.width && this.options.values.width.toString().indexOf('%') > -1) {
		domElement.style.whiteSpace = 'nowrap';
		domElement.style.width = this.options.values.width;
		domElement.style.overflow = 'visible';
	}
	var htmlCode = _Designer_Control_InputText_DrawByType(this.parent, this.attrs, this.options.values, this);
	//当控件只读的时候，只有展示样式只有设置为inline-block才对居左、居中、居右有效
	if (this.options.values.readOnly && this.options.values.readOnly == 'true') {
		domElement.style.display = 'inline-block';
	}
	domElement.innerHTML = htmlCode;
}

// 生成XML
function _Designer_Control_Datetime_DrawXML() {
	var values = this.options.values;
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	if (values.businessType == 'timeDialog') {
		buf.push('type="Time" ');
	} else if (values.businessType == 'dateDialog' || values.businessType == null) {
		buf.push('type="Date" ');
    } else if (values.businessType == 'dateDialog7' || values.businessType == null) {
    	buf.push('type="Date7" ');	  //	
	} else {
		buf.push('type="DateTime" ');
	}
	if (values.dimension) {
		buf.push('dimension="', values.dimension, '" ');

	}
	if (values.required == "true") {
		buf.push('notNull="true" ');
	}
	if(values.readOnly && values.readOnly == 'true'){
		buf.push('readOnly="true" ');
	}
	if (values.defaultValue == 'select') {
		buf.push('defaultValue="', values._selectValue, '" ');
	}
	if (values.defaultValue == 'nowTime') {
		buf.push('formula="true" ');
		buf.push('defaultValue="DateTimeFunction.getNow()" ');
		if (values.reCalculate == 'true') {
			buf.push('recalculateOnSave="true" ');
		}
		if (values.reCalculateDraft == 'true') {
			buf.push('recalculateDraftOnSave="true" ');
		}
	}
	
	buf.push('businessType="', this.type, '" ');
	//摘要汇总
	if(values.summary == 'true'){
		buf.push('summary="true" ');
	}
	
	if(values.isMark == 'true'){
		buf.push('isMark="true" ');
	}else{
		buf.push('isMark="false" ');
	}
	
	buf.push('/>');
	return buf.join('');
}

//生成多行文本Dom对象
function _Designer_Control_Textarea_OnDraw(parentNode, childNode){
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	domElement.className="xform_textArea";
	if(this.options.values.font) _Designer_Control_Common_SetStyle(domElement, this.options.values.font, "fontFamily");
	if(this.options.values.size) _Designer_Control_Common_SetStyle(domElement, this.options.values.size, "fontSize");
	if(this.options.values.color) _Designer_Control_Common_SetStyle(domElement, this.options.values.color, "color");
	if(this.options.values.b=="true") domElement.style.fontWeight="bold";
	if(this.options.values.i=="true") domElement.style.fontStyle = "italic";
	if(this.options.values.underline=="true") domElement.style.textDecoration="underline";

	
	$(domElement).css("display","inline-block");
	var htmlCode = '<textarea readOnly id="'+this.options.values.id+'" class="' + (this.options.values.canShow=='false'?'inputhidden':'inputmul');
	if (this.options.values.canShow == 'false') {
		htmlCode += '" canShow="false"';
	} else {
		htmlCode += '" canShow="true"';
	}
	if (this.options.values.required == "true") {
		htmlCode += ' required="true"'
		htmlCode += ' _required="true"'
	} else {
		htmlCode += ' required="false"'
		htmlCode += ' _required="false"'
	}
	if (this.options.values.readOnly == 'true') {
		htmlCode += '" _readOnly="true"';
	} else {
		htmlCode += '" _readOnly="false"';
	}
	//是否摘要
	if(this.options.values.summary == "true"){
		htmlCode += ' summary="true"';
	}else{
		htmlCode += ' summary="false"';
	}
	//是否留痕
	if(this.options.values.isMark == "true"){
		htmlCode += ' isMark="true"';
	}else{
		htmlCode += ' isMark="false"';
	}
	if(this.options.values.tipInfo != null && this.options.values.tipInfo != ''){
		htmlCode += '" tipInfo ="' + this.options.values.tipInfo + '"';
	}
	if (this.parent != null) {
		htmlCode += ' tableName="' + _Get_Designer_Control_TableName(this.parent) + '"';
	}
	if (this.options.values.defaultValue != null) {
		htmlCode += ' defaultValue="' + Designer.HtmlEscape(this.options.values.defaultValue) + '"';
	}
	if (this.options.values.maxlength != null && this.options.values.maxlength != '') {
		htmlCode += ' maxlength="' + this.options.values.maxlength + '"';
	}
	var width = '250px';
	if(this.options.values.width !=null && this.options.values.width !=''){
		if (this.options.values.width.toString().indexOf('%') > -1) {
			var pWidth = this.options.values.width;
			//width = '100%';
			width = pWidth;
			htmlCode += ' pWidth="' + pWidth + '"';
			domElement.style.width = pWidth;
			domElement.style.whiteSpace = 'inherit';
		} else {
			width = this.options.values.width + 'px';
		}
	} else {
		this.options.values.width = "250";
	}
	var height = '80px';
	if(this.options.values.height !=null && this.options.values.height !=''){
		height = this.options.values.height + 'px';
	} else {
		this.options.values.height = "80";
	}
	if (this.options.values.description != null) {
		htmlCode += ' description="' + this.options.values.description + '"';
	}
	htmlCode += ' label="' + _Get_Designer_Control_Label(this.options.values, this) + '"';
	htmlCode += ' style="width:'+width+';height:'+height+';'+'max-width:100%;';
	if(this.options.values.color){
		htmlCode += 'color:' + this.options.values.color +';">';
	}else{
		htmlCode += '">';
	}
	if (this.options.values.defaultValue != null) {
		htmlCode += this.options.values.defaultValue;
	}
	htmlCode += '</textarea>';
	if(this.options.values.required == 'true') {
		htmlCode += '<span class=txtstrong>*</span>';
	}
	$(domElement).css("width",this.options.values.width);
	$(domElement).css("height",this.options.values.height);
	domElement.innerHTML = htmlCode;
}

// 生成XML
function _Designer_Control_Textarea_DrawXML() {
	var values = this.options.values;
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="String" ');
	if (values.maxlength != null && values.maxlength != '') {
		buf.push('length="'+values.maxlength+'" ');
	} else {
		buf.push('length="'+this.attrs.defaultLength.value+'" ');
	}
	if (values.required == "true") {
		buf.push('notNull="true" ');
	}
	if (values.formula != '' && values.formula != null) {
		buf.push('formula="true" ');
		buf.push('defaultValue="', values.formula, '" ');
		if (values.reCalculate == 'true') {
			buf.push('recalculateOnSave="true" ');
		}
	} else if (values.defaultValue != '' && values.defaultValue != null) {
		buf.push('defaultValue="', values.defaultValue, '" ');
	}
	if(values.readOnly && values.readOnly == 'true'){
		buf.push('readOnly="true" ');
	}
	
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	
	buf.push('businessType="', this.type, '" ');
	
	//摘要汇总
	if(values.summary == 'true'){
		buf.push('summary="true" ');
	}
	if(values.isMark == 'true'){
		buf.push('isMark="true" ');
	}else{
		buf.push('isMark="false" ');
	}
	
	buf.push('/>');
	return buf.join('');
}

function Designer_Control_MultiValues(items) {
	if (items == null || items == '') return '';
	var items = items.split("\r\n");
	var text, value, index;
	var newItems = [];
	for(var i=0; i<items.length; i++) {
		if (items[i] == '') continue;
		//items[i] = Designer.HtmlEscape(items[i]);
		index = items[i].lastIndexOf("|");
		if(index == -1){
			text = items[i];
			value = items[i];
		}else{
			text = items[i].substring(0, index);
			value = items[i].substring(index + 1);
			if (value == null || value == "")
				value = text;
		}
		newItems.push(text + "|" + value);
	}
	return (newItems.join(';'));
	//return Designer.HtmlEscape(newItems.join(';'));
}

//生成复选框Dom对象
function _Designer_Control_InputCheckbox_OnDraw(parentNode, childNode){
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var domElement = document.createElement("div");
	domElement.setAttribute("formDesign", "landray");
	this.options.domElement = domElement;
	parentNode.insertBefore(domElement, childNode);
	domElement.className="xform_inputCheckBox";
	if(this.options.values.font) _Designer_Control_Common_SetStyle(domElement, this.options.values.font, "fontFamily");
	if(this.options.values.size) _Designer_Control_Common_SetStyle(domElement, this.options.values.size, "fontSize");
	if(this.options.values.color) _Designer_Control_Common_SetStyle(domElement, this.options.values.color, "color");
	if(this.options.values.b=="true") domElement.style.fontWeight="bold";
	if(this.options.values.i=="true") domElement.style.fontStyle = "italic";
	if(this.options.values.underline=="true") domElement.style.textDecoration="underline";

	domElement.style.width ='auto';
	
	$(domElement).css("display","inline-block");
	$(domElement).css("min-width","120px");
	
	//$(domElement).attr("style",domElement.style);
	
	var name = this.options.values.id;
	var htmlCode = '';
	var htmlCode2 = '';
	var itemsTexts = '';
	var itemsValues = '';
	if(this.options.values.items==null || this.options.values.items==''){
		htmlCode = '<input type="checkbox" onclick="return false;" id="'+name;
		if (this.parent != null) {
			htmlCode += '" tableName="' + _Get_Designer_Control_TableName(this.parent);
		}
		htmlCode += '" label="' + _Get_Designer_Control_Label(this.options.values, this);
		htmlCode += '" value="0" items="'
			+ Designer_Lang.controlInputCheckboxItemsLabel + '" itemValues="0"><label style="margin-right:10px" attach="' + name + '">'
			+ Designer_Lang.controlInputCheckboxItemsLabel + '</label>&nbsp;';
	} else {
		var itemStr = this.options.values.items;
		var items = [];
		if(itemStr.indexOf("\r\n")>-1){
			items = itemStr.split("\r\n");
		}else{
			items = itemStr.split("\n");
		}
		var isFirstCheckbox = true;
		var dv = null;
		var dvs = [];
		if(this.options.values.alignment == 'V'){
			//明细表里面的单元格默认居中，当单选竖向的时候，居中的单元格显示会错乱，故这里设置为居左
			if($(domElement).closest('td')){
				$(domElement).closest('td').attr('align','left');
			}
		}
		for(var i=0; i<items.length; i++){
			if(items[i]=="")
				continue;
			//items[i] = Designer.HtmlEscape(items[i]);
			var index = items[i].lastIndexOf("|");
			if(index == -1){
				var text = items[i];
				var value = items[i];
			}else{
				var text = items[i].substring(0, index);
				var value = items[i].substring(index+1);
			}
			if (isFirstCheckbox) {
				itemsTexts += text;
				itemsValues += value;
			} else {
				itemsTexts += ';' + text;
				itemsValues += ';' + value;
			}
			if(isFirstCheckbox) {
				isFirstCheckbox = false;
				htmlCode += '<input type="checkbox" onclick="return false;" id="'+name+'" value="'+value;
				if (this.parent != null) {
					htmlCode += '" tableName="' + _Get_Designer_Control_TableName(this.parent);
				}
				if (this.options.values.mobileRenderType) {
					htmlCode +=  '" mobileRenderType="'+this.options.values.mobileRenderType;
				}				
				if (this.options.values.alignment == 'V') {
					htmlCode +=  '" alignment="V';
				} else {
					htmlCode +=  '" alignment="H';
				}
				if (this.options.values.description != null) {
					htmlCode += '" description="' + this.options.values.description;
				}
				if (this.options.values.width) {
					htmlCode += '" pWidth="' + this.options.values.width;
				}
				//是否摘要
				if(this.options.values.summary == "true"){
					htmlCode += '" summary="true';
				}else{
					htmlCode += '" summary="false';
				}
				//是否留痕
				if(this.options.values.isMark == "true"){
					htmlCode += '" isMark="true';
				}else{
					htmlCode += '" isMark="false';
				}
				htmlCode += '" label="' + _Get_Designer_Control_Label(this.options.values, this);
				htmlCode += '" required="' + (this.options.values.required == 'true' ? 'true' : 'false');
				htmlCode += '" _required="' + (this.options.values.required == 'true' ? 'true' : 'false');
				if (this.options.values.defaultValue != null) {
					dv = Designer.HtmlEscape(this.options.values.defaultValue)
					htmlCode += '" defaultValue="' + dv;
					dvs = dv.split(";");
				}
				for (var n = 0; n < dvs.length; n ++) {
					if (value == dvs[n]) {
						htmlCode += '" checked="checked';
						break;
					}
				}
				htmlCode2 += '"><label style="margin-right:10px" attach="' + name + '">'+text+'</label>&nbsp;';
			} else {
				if(this.options.values.alignment == 'V'){
					htmlCode2 += '<br>';
				}else{
					//htmlCode2 += '<font>&nbsp;</font>';
				}
				htmlCode2 += '<input type="checkbox" onclick="return false;" id="'+name+'" value="'+value;
				for (var n = 0; n < dvs.length; n ++) {
					if (value == dvs[n]) {
						htmlCode2 += '" checked="checked';
						break;
					}
				}
				htmlCode2 += '" attach="' + name +'"><label style="margin-right:10px" attach="' + name + '">'+text+'</label>&nbsp;';
			}
		}
		htmlCode += '" items="' + itemsTexts + '" itemValues="' + itemsValues + htmlCode2;
		if(this.options.values.required == 'true') {
			htmlCode += '<span class=txtstrong>*</span>';
		}
	}
	domElement.innerHTML = htmlCode;
}

function Designer_Control_InputCheckBox_Validator(elem, name, attr, value, values){
	//如果初始值是用公式定义器设置的，则判断是否为多选，如果是多选，则提示公式定义器不支持多选
	if(values.formula && values.formula != ''){
		var eligibleVal = Designer.HtmlUnEscape(values.formula);
		var valueArray = eligibleVal.split(';');
		if(valueArray && valueArray.length > 1){
			alert('公式定义器不支持初始值为多值，请手动输入！');
			/*#152983 start*/
			var parent = $(elem).parent("td");
			$(parent).find("input[name='defaultValue']").each(function(){
				$(this).removeAttr('readonly');
				$(this).val('');
			});
			$(parent).find("input[name='formula']").each(function(){
				$(this).val('');
			});
			$(parent).find("#reCalculate_field").hide();//隐藏重新计算
			/*#152983 end*/
			return false;
		}
	}
	return true;
}

// 生成XML
function _Designer_Control_InputCheckbox_DrawXML() {
	var values = this.options.values;
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	if (values.required == "true") {
		buf.push('notNull="true" ');
	}
	buf.push('enumValues="', Designer_Control_MultiValues(values.items), '" ');
	buf.push('type="', values.dataType ? values.dataType : 'String', '" ');
	// 控件类型
	buf.push('businessType="', this.type, '" ');
	if (values.formula != '' && values.formula != null) {
		buf.push('formula="true" ');
		buf.push('defaultValue="', values.formula, '" ');
		if (values.reCalculate == 'true') {
			buf.push('recalculateOnSave="true" ');
		}
	} else if (values.defaultValue != '' && values.defaultValue != null) {
		buf.push('defaultValue="', values.defaultValue, '" ');
	}
	
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	//摘要汇总
	if(values.summary == 'true'){
		buf.push('summary="true" ');
	}
	
	if(values.isMark == 'true'){
		buf.push('isMark="true" ');
	}else{
		buf.push('isMark="false" ');
	}
	
	buf.push('/>');
	return buf.join('');
}

//生成单选按钮Dom对象
function _Designer_Control_InputRadio_OnDraw(parentNode, childNode){
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var name = this.options.values.id;
	var domElement = document.createElement("div");
	domElement.setAttribute("formDesign", "landray");
	this.options.domElement = domElement;
	parentNode.insertBefore(domElement, childNode);
	domElement.className="xform_inputRadio";
	if(this.options.values.font) _Designer_Control_Common_SetStyle(domElement, this.options.values.font, "fontFamily");
	if(this.options.values.size) _Designer_Control_Common_SetStyle(domElement, this.options.values.size, "fontSize");
	if(this.options.values.color) _Designer_Control_Common_SetStyle(domElement, this.options.values.color, "color");
	if(this.options.values.b=="true") domElement.style.fontWeight="bold";
	if(this.options.values.i=="true") domElement.style.fontStyle = "italic";
	if(this.options.values.underline=="true") domElement.style.textDecoration="underline";

	
	domElement.style.width ='auto';
	
	$(domElement).css("display","inline-block");
	$(domElement).css("min-width","120px");
	var htmlCode = '';
	var htmlCode2 = '';
	var itemsTexts = '';
	var itemsValues = '';
	if(this.options.values.items==null || this.options.values.items==''){
		htmlCode = '<input type="radio" id="'+name;
		if (this.parent != null) {
			htmlCode += '" tableName="' + _Get_Designer_Control_TableName(this.parent);
		}
		htmlCode += '" label="' + _Get_Designer_Control_Label(this.options.values, this);
		htmlCode += '" value="0" items="'
				+ Designer_Lang.controlInputRadioItemsLabel + '" itemValues="0"><label attach="' + name + '">'
				+ Designer_Lang.controlInputRadioItemsLabel + '</label>&nbsp;';
	}else{
		var itemStr = this.options.values.items;
		var items = [];
		if(itemStr.indexOf("\r\n")>-1){
			items = itemStr.split("\r\n");
		}else{
			items = itemStr.split("\n");
		}
		var isFirstRadio = true;
		var dv = null;
		if(this.options.values.alignment == 'V'){
			//明细表里面的单元格默认居中，当单选竖向的时候，居中的单元格显示会错乱，故这里设置为居左
			if($(domElement).closest('td')){
				$(domElement).closest('td').attr('align','left');
			}
			
		}
		for(var i=0; i<items.length; i++){
			if(items[i]=="")
				continue;
			//items[i] = Designer.HtmlEscape(items[i]);
			var index = items[i].lastIndexOf("|");
			if(index == -1){
				var text = items[i];
				var value = items[i];
			}else{
				var text = items[i].substring(0, index);
				var value = items[i].substring(index+1);
			}
			if (isFirstRadio) {
				itemsTexts += text;
				itemsValues += value;
			} else {
				itemsTexts += ';' + text;
				itemsValues += ';' + value;
			}
			if(isFirstRadio){
				isFirstRadio = false;
				htmlCode += '<input type="radio" onclick="return false;" id="'+name+'" value="'+value;
				if (this.parent != null) {
					htmlCode += '" tableName="' + _Get_Designer_Control_TableName(this.parent);
				}
				if (this.options.values.mobileRenderType) {
					htmlCode +=  '" mobileRenderType="'+this.options.values.mobileRenderType;
				}				
				if (this.options.values.alignment == 'V') {
					htmlCode +=  '" alignment="V';
				} else {
					htmlCode +=  '" alignment="H';
				}				
				//是否摘要
				if(this.options.values.summary == "true"){
					htmlCode += '" summary="true';
				}else{
					htmlCode += '" summary="false';
				}
				//是否留痕
				if(this.options.values.isMark == "true"){
					htmlCode += '" isMark="true';
				}else{
					htmlCode += '" isMark="false';
				}
				if (this.options.values.description != null) {
					htmlCode += '" description="' + this.options.values.description;
				}
				if (this.options.values.width) {
					htmlCode += '" pWidth="' + this.options.values.width;
				}
				htmlCode += '" label="' + _Get_Designer_Control_Label(this.options.values, this);
				if (this.options.values.defaultValue != null && this.options.values.defaultValue != '') {
					dv = Designer.HtmlEscape(this.options.values.defaultValue)
					htmlCode += '" defaultValue="' + dv;
				}
				htmlCode += '" required="' + (this.options.values.required == 'true' ? 'true' : 'false');
				htmlCode += '" _required="' + (this.options.values.required == 'true' ? 'true' : 'false');
				if (value == dv) {
					htmlCode += '" checked="checked';
				}
				htmlCode2 += '"><label attach="' + name + '">'+text+'</label>&nbsp;';
			}else{
				if(this.options.values.alignment == 'V'){
					htmlCode2 += '<br>';
				}else{
					//htmlCode2 += '<font>&nbsp;&nbsp;</font>';
				}
				htmlCode2 += '<input type="radio" onclick="return false;" id="'+name+'" value="'+value
					+ (value == dv ? '" checked="checked' : '') 
					+ '" attach="' + name +'"><label attach="' + name + '">'+text+'</label>&nbsp;';
			}
		}
		htmlCode += '" items="' + itemsTexts + '" itemValues="' + itemsValues + htmlCode2;
		if(this.options.values.required == 'true') {
			htmlCode += '<span class=txtstrong>*</span>';
		}
	}
	
	
	
	domElement.innerHTML = htmlCode;
}

// 生成XML
function _Designer_Control_InputRadio_DrawXML() {
	var values = this.options.values;
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	if (values.required == "true") {
		buf.push('notNull="true" ');
	}
	buf.push('enumValues="', Designer_Control_MultiValues(values.items), '" ');
	buf.push('type="', values.dataType ? values.dataType : 'String', '" ');
	// 控件类型
	buf.push('businessType="', this.type, '" ');
	if (values.formula != '' && values.formula != null) {
		buf.push('formula="true" ');
		buf.push('defaultValue="', values.formula, '" ');
		if (values.reCalculate == 'true') {
			buf.push('recalculateOnSave="true" ');
		}
	} else if (values.defaultValue != '' && values.defaultValue != null) {
		buf.push('defaultValue="', values.defaultValue, '" ');
	}
	
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	//摘要汇总
	if(values.summary == 'true'){
		buf.push('summary="true" ');
	}
	
	if(values.isMark == 'true'){
		buf.push('isMark="true" ');
	}else{
		buf.push('isMark="false" ');
	}
	
	buf.push('/>');
	return buf.join('');
}

//生成下拉框Dom对象
function _Designer_Control_Select_OnDraw(parentNode, childNode){
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	domElement.className="select_div_box xform_Select";
	//去掉宽度自动，默认宽度改为120
	//$(domElement).css('width','auto');
	//$(domElement).css('min-width','100px');
	if(this.options.values.font) _Designer_Control_Common_SetStyle(domElement, this.options.values.font, "fontFamily");
	if(this.options.values.size) _Designer_Control_Common_SetStyle(domElement, this.options.values.size, "fontSize");
	if(this.options.values.color) _Designer_Control_Common_SetStyle(domElement, this.options.values.color, "color");
	if(this.options.values.b=="true") domElement.style.fontWeight="bold";
	if(this.options.values.i=="true") domElement.style.fontStyle = "italic";
	if(this.options.values.underline=="true") domElement.style.textDecoration="underline";

	
	var selectDom = document.createElement('select');
	selectDom.style.display = 'none';

	// 没有值就默认120px
	if (!this.options.values.width) {
		this.options.values.width='120';
	}
	if (this.options.values.width.indexOf("%") > -1){
		alert("暂不支持%宽度");
		this.options.values.width='120';
	}
	$(domElement).css('width', this.options.values.width);
	selectDom.style.width = this.options.values.width+"px";
	
	selectDom.id = this.options.values.id;
	if (this.options.values.description) {
		selectDom.description = this.options.values.description;
	}
	selectDom.label = _Get_Designer_Control_Label(this.options.values, this);
	if (this.parent != null) {
		selectDom.tableName=_Get_Designer_Control_TableName(this.parent);
	}
	selectDom.required = (this.options.values.required == 'true' ? 'true' : 'false');
	$(selectDom).attr('_required',this.options.values.required == 'true' ? 'true' : 'false');
	//selectDom._required = (this.options.values.required == 'true' ? 'true' : 'false');
	selectDom.canShow = (this.options.values.canShow == 'true' ? 'true' : 'false');
	if (this.options.values.formula != null && this.options.values.formula != '') {
		selectDom.formula = 'true';
		selectDom.defaultValue = this.options.values.defaultValue;
		selectDom.reCalculate = (this.options.values.reCalculate == 'true' ? 'true' : 'false');
	}
	else if (this.options.values.defaultValue != null && this.options.values.defaultValue != '') {
		selectDom.defaultValue = this.options.values.defaultValue;
	}
	//是否摘要
	if(this.options.values.summary == "true"){
		$(selectDom).attr("summary","true");
	}else{
		$(selectDom).attr("summary","false");
	}
	//是否留痕
	if(this.options.values.isMark == "true"){
		$(selectDom).attr("isMark","true");
	}else{
		$(selectDom).attr("isMark","false");
	}
	var itemsText = [];
	var itemsValue = [];
	var defaultValueName = "";
	if (this.options.values.items != null && this.options.values.items != '') {
		var itemStr = this.options.values.items;
		var items = [];
		if(itemStr.indexOf("\r\n")>-1){
			items = itemStr.split("\r\n");
		}else{
			items = itemStr.split("\n");
		}
		for(var i = 0; i < items.length; i++) {
			if(items[i] == "")
				continue;
			//items[i] = Designer.HtmlEscape(items[i]);
			var index = items[i].lastIndexOf("|");
			if(index == -1){
				itemsText.push(items[i]);
				itemsValue.push(items[i]);
			}else{
				itemsText.push(items[i].substring(0, index));
				itemsValue.push(items[i].substring(index+1));
			}
			if (selectDom.defaultValue == itemsValue[itemsValue.length - 1]) {
				defaultValueName = itemsText[itemsText.length - 1]
			}
		}
	} else {
		
	}
	if (itemsValue.length > 0) {
		//selectDom.items =itemsText.join(';');// $('<input type="hidden" value="' + itemsText.join(';') + '"/>').val();
		$(selectDom).attr("items",itemsText.join(';'));
		$(selectDom).attr("itemValues",itemsValue.join(';'));// $('<input type="hidden" value="' + itemsValue.join(';') + '"/>').val();
	}
	var buf = [];
	var face_width = "85%";
	if (this.options.values.width>600){
		face_width = "95%";
	}
	buf.push('<label class="select_tag_left" style="width:'+this.options.values.width+'px"><label class="select_tag_right" style="width:95%">');
	if (itemsText.length == 0) {
		buf.push('<label class="select_tag_face" style="width:'+face_width+';overflow: hidden">',Designer_Lang.controlSelectPleaseAdd,'</label>');
	} else {
		var lab = '<label class="select_tag_face" style="width:'+face_width+';overflow: hidden"';
		if (this.options.values.color){
			lab += ' style="color:' + this.options.values.color + ';">';
		}else{
			lab += '>';
		}
		if (selectDom.defaultValue != null && selectDom.defaultValue != ""
				&& (this.options.values.formula == null || this.options.values.formula == '')) {
			
			buf.push(lab, defaultValueName, '</label>');
		} else {
			buf.push(lab,Designer_Lang.controlSelectPleaseSelect,'</label>');
		}
	}
	buf.push('</label></label>');
	if(this.options.values.required == 'true') {
		buf.push('<span class=txtstrong>*</span>');
	}
	domElement.innerHTML = buf.join('');
	domElement.appendChild(selectDom);
	setTimeout(function () {
		let selecttagleft = $(domElement).find(".select_tag_left");
		if (selecttagleft.width()){
			if (selecttagleft.width() > 600){
				selecttagleft.find(".select_tag_right").css("width","95%");
				selecttagleft.find(".select_tag_face").css("width","97%");
			}else{
				selecttagleft.find(".select_tag_right").css("width","95%");
				selecttagleft.find(".select_tag_face").css("width","85%");
			}
		}
	},100);
}

function Designer_Control_Attr_Width_Validator_Select(elem, name, attr, value, values){
	if (name == "style_width" && value == "auto"){
		return true;
	}
	if (value != null && value != '' && !(/^([+]?)(\d+)([%]?)$/.test(value.toString())) || value.indexOf("%") > 0) {
		alert(Designer_Lang.controlAttrValWidthSelect);
		return false;
	}
	return true;
}

// 生成XML
function _Designer_Control_Select_DrawXML() {
	var values = this.options.values;
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	if (values.required == "true") {
		buf.push('notNull="true" ');
	}
	buf.push('enumValues="', Designer_Control_MultiValues(values.items), '" ');
	buf.push('type="', values.dataType ? values.dataType : 'String', '" ');
	if (values.formula != '' && values.formula != null) {
		buf.push('formula="true" ');
		buf.push('defaultValue="', values.formula, '" ');
		if (values.reCalculate == 'true') {
			buf.push('recalculateOnSave="true" ');
		}
	} else if (values.defaultValue != '' && values.defaultValue != null) {
		buf.push('defaultValue="', values.defaultValue, '" ');
	}
	
	// 字段是否需要加密
	if (values.encrypt == 'true') {
		buf.push('encrypt="true" ');
		buf.push('encryptionMethod="AES" ');
	}
	buf.push('businessType="', this.type, '" ');
	//摘要汇总
	if(values.summary == 'true'){
		buf.push('summary="true" ');
	}
	
	if(values.isMark == 'true'){
		buf.push('isMark="true" ');
	}else{
		buf.push('isMark="false" ');
	}
	
	buf.push('/>');
	return buf.join('');
}

//生成RTF Dom对象
function _Designer_Control_Rtf_OnDraw(parentNode, childNode){
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	var domElement = _CreateDesignElement('div', this, parentNode, childNode);
	
	domElement.id = this.options.values.id;
	$(domElement).append("<img src='style/img/rtf.jpg' width='98%' height='100%'/>");
	if (this.options.values.required == 'true') {
		$(domElement).append("<span class='txtstrong'>*</span>");
	}
	domElement.businessType = "rtfEditor";
	if (this.options.values.description) {
		domElement.description = this.options.values.description;
	}
	domElement.label = _Get_Designer_Control_Label(this.options.values, this);
	if (this.options.values.defaultValue != null) {
		domElement.defaultValue = Designer.HtmlEscape(this.options.values.defaultValue);
	}
	if (this.parent != null) {
		domElement.tableName=_Get_Designer_Control_TableName(this.parent);
	}
 	$(domElement).attr("required",this.options.values.required == 'true' ? 'true' : 'false');
 	$(domElement).attr("_required",this.options.values.required == 'true' ? 'true' : 'false');
	//修改为强制过滤 2014-08-12 曹映辉 
	domElement.needFilter = true;//(this.options.values.needFilter == 'true' ? 'true' : 'false');
	domElement.canShow = (this.options.values.canShow == 'true' ? 'true' : 'false');
	if (this.options.values.formula != null) {
		domElement.formula = this.options.values.formula;
		domElement.reCalculate = (this.options.values.reCalculate == 'true' ? 'true' : 'false');
	}
	if (this.options.values.width == null) {
		this.options.values.width = this.attrs.width.value;
	}
	$(domElement).attr("pWidth",this.options.values.width);
	//domElement.style.width = this.options.values.width;
	$(domElement).css("width",this.options.values.width);
	if (this.options.values.height == null) {
		this.options.values.height = this.attrs.height.value;
	}
	//domElement.pHeight = this.options.values.height;
	$(domElement).attr("pHeight",this.options.values.height);
	//domElement.style.height = this.options.values.height;
	$(domElement).css("height",this.options.values.height);
}

// 生成XML
function _Designer_Control_Rtf_DrawXML() {
	var values = this.options.values;
	var buf = ['<extendSimpleProperty '];
	buf.push('name="', values.id, '" ');
	buf.push('label="', values.label, '" ');
	buf.push('type="RTF" length="1000000" ');
	if (values.formula != '' && values.formula != null) {
		buf.push('formula="true" ');
		buf.push('defaultValue="', values.formula, '" ');
		if (values.reCalculate == 'true') {
			buf.push('recalculateOnSave="true" ');
		}
	} else if (values.defaultValue != '' && values.defaultValue != null) {
		buf.push('defaultValue="', values.defaultValue, '" ');
	}
	buf.push('/>');
	return buf.join('');
}
function StandardTable_ModelDialog_Show(url,data,callback){
	this.AfterShow=callback;
	this.data=data;
	this.width=600;
	this.height=400;
	this.url=url;
	this.setWidth=function(width){
		this.width=width;
	};
	this.setHeight=function(height){
		this.height=height;
	};
	this.setCallback=function(action){
		this.callback=action;
	};
	this.setData=function(data){
		this.data=data;
	};
	
	this.show=function(){
		var obj={};
		obj.data=this.data;
		obj.AfterShow=this.AfterShow;
		Com_Parameter.Dialog=obj;
		var left = (screen.width-this.width)/2;
		var top = (screen.height-this.height)/2;
		
		var winStyle = "resizable=1,scrollbars=1,width="+this.width+",height="+this.height+",left="+left+",top="+top+",dependent=yes,alwaysRaised=1";
		var win= window.open(this.url, "_blank", winStyle);
		try{
			win.focus();
		}
		catch(e){
			
		}
		//用window.open 达到模态效果
		window.onfocus=function (){
			try{
				win.focus();
			}catch(e){
				
			}
		};
	    window.onclick=function (){
	    	try{
				win.focus();
			}catch(e){
				
			}
	    };
	};
}

function _Designer_Control_StandardTable_OnDrag(event){
	//取消锁定状态
	var _prevDragDomElement = this.owner._dragDomElement, currElement = event.srcElement || event.target;
	//右键不认为是点击事件  2009-05-18 傅游翔
	if (Designer.eventButton(event) == 2) return;
	
	var currElement = event.srcElement || event.target;
	//若选中的不是单元格，则退出
	if (Designer.checkTagName(currElement, 'td')){
		this.columnStart=currElement.cellIndex;
		this.rowStart=currElement.parentNode.rowIndex;
	}
	_Designer_Control_Base_DoDrag.call(this,event);
}

function _Designer_Control_StandardTable_OnDragStop(event){
	_Designer_Control_Base_DoDragStop.call(this,event);
	this.rowStart=-1;
	this.columnStart=-1;
}

function _Designer_Control_StandardTable_OnDragMovingChooseCell(event){
	//排除调整单元格大小时也出现选中现象
	if(this.owner.domElement.style.cursor != 'move'&&this.owner.domElement.style.cursor!='default'){
		return;
	}

	
	var builder = this.owner,currElement=table = this.options.domElement;
	var mousePos = builder.getMouseRelativePosition(event);
	//减少循环次数
	if(mousePos.x%5!=0&&mousePos.y%3!=0){
		return ;
	}
	
	var rows = table.rows;		
	if(this.rowStart<0||this.columnStart<0 ||(this.columnStart!=0&& !this.columnStart)){
		return ;
	}
	var startCell=rows[this.rowStart].cells[this.columnStart];
	var startPos=Designer.absPosition(startCell);
	
	for(var i=0;i<rows.length;i++){
		var height=rows[i].offsetHeight;
		var columns=rows[i].cells.length;
		for(var j=0;j<columns;j++){
			var cellPos=Designer.absPosition(rows[i].cells[j]);	
			var width=parseInt(rows[i].cells[j].width);
			// 明细表的单元格没有宽度
			if(isNaN(width)){
				width = parseInt(rows[i].cells[j].offsetWidth);
			}
			var leftX=startPos.x;
			var rightX=mousePos.x;
			
			if(mousePos.x<startPos.x){
				leftX=mousePos.x-width;
				rightX=startPos.x;
			}
			var leftY= startPos.y;
			var rightY= mousePos.y;
			if(mousePos.y<startPos.y){
				leftY=mousePos.y-height ;
				rightY=startPos.y;
			}
			if(cellPos.x>=leftX && cellPos.x<=rightX &&cellPos.y>=leftY &&cellPos.y<=rightY ){
				this.chooseCell(rows[i].cells[j],true);
			}
			else{
				this.chooseCell(rows[i].cells[j],false);
			}
		}
	}
}

function _Designer_Control_Attr_standardTable_style_Self_Draw(name, attr, value,
		form, attrs, values, control){
	var html="";
	html+="<a href='javascript:void(0)' onclick='_Designer_Control_standardTable_style_Set(this);' style='text-decoration:underline;margin-left:5px'>"+Designer_Lang.controlStandardTableSetStyle+"<a>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

//表格样式转义
function tableStyleOutputParams_translator(change){
	var data=new KMSSData().AddBeanData("sysStandardTableStyleExt").GetHashMapArray();
	var oldVal = "";
	if(change.before != ''){
		oldVal = JSON.parse(change.before.replaceAll('quot;','"'));
	}
	var newVal = JSON.parse(change.after.replaceAll('quot;','"'));
	var oldName = "";
	var newName = "";
	if (!change) {
		return "";
	}
	if(data != ''||data != undefined || data != 'undefined'){
		for(var i=0;i<data.length;i++){
			if(data[i].pathProfix == oldVal.pathProfix){
				oldName = data[i].name;
			}
			if(data[i].pathProfix == newVal.pathProfix){
				newName = data[i].name;
			}
		}
	}
	if(change.before == ''){
		oldName = "默认样式";
	}
	var html = "<span> 由 (" + oldName + ") 变更为 (" + newName + ")</span>";
	return html;
}

function _Designer_Control_standardTable_style_Set(){
	var control=Designer.instance.attrPanel.panel.control;
	//document.writeln("<link href='<%=request.getContextPath() %>/sys/xform/designer/standardtable/tablestyle/tb_normal_redsolid/tb_normal_redsolid.css' type='text/css' rel='stylesheet' />");
	var data=new KMSSData().AddBeanData("sysStandardTableStyleExt").GetHashMapArray();
	var paramObj={};
	// 移动端过滤掉表格内外无边框，互联网风格轻量化的样式
	if(Designer.instance.isMobile){
		for(var i=0;i<data.length;i++){
			if(typeof data[i].name!= "undefined" && data[i].name === Designer_Lang.tb_normal_lightweight){
				data.splice(i,1);
				break;
			}
		}

	}
	paramObj.data=data;

	var values=control.options.values;
	//加载表格样式
	if(values.tableStyle){
		var tableStyle=JSON.parse(values.tableStyle.replace(/quot;/g,"\""));
		paramObj.chooseStyle=tableStyle.tbClassName;
		//$("#"+control.options.domElement.getAttribute("id")).attr("class",tableStyle["tbClassName"]);
	}
	
	var dialog=new StandardTable_ModelDialog_Show(Com_Parameter.ContextPath+"sys/xform/designer/standardtable/stylePreview.jsp",paramObj,function(rtnValue){
		if(!rtnValue){
			return;
		}
		if (Designer.instance.isMobile) {
			$(control.options.domElement).attr("setTableStyle",true);
		}
		$("#"+control.options.domElement.getAttribute("id")).prev("[name='dynamicTableStyle']").remove();
		var __tbClassName = rtnValue["tbClassName"];
		if (__tbClassName === "tb_normal_noanyborder") {
			__tbClassName = "tb_normal " + __tbClassName;
		}
		//#159889 主表样式从其他样式切换成标准样式，也会显示表格 by  ouyu start
		if (Designer.instance.isMobile&&__tbClassName === "tb_normal") {
			__tbClassName = "tb_normal muiTable";
		}else{
			$("#"+control.options.domElement.getAttribute("id")).before("<link rel='stylesheet' name='dynamicTableStyle' type='text/css' href='"+Com_Parameter.ContextPath+rtnValue['pathProfix']+"/standardtable.css'/>");
		}
		//#159889 主表样式从其他样式切换成标准样式，也会显示表格 by  ouyu end
		$("#"+control.options.domElement.getAttribute("id")).attr("class",__tbClassName);
		//将rtnValue复制一份，防止出现无法解析的js错误 #32909 作者 曹映辉 #日期 2016年11月9日
		control.options.values.tableStyle=JSON.stringify(jQuery.extend({}, rtnValue)).replace(/"/g,"quot;");
//		control.options.domElement.tableStyle=control.options.values.tableStyle;
		
		//#48118【表单引擎-修复】表单中表格的样式设置在模板中设置会显示正常（生效），但是新建文档时未按照设置的样式进行显示。 by liwc
		if (Designer.instance.isMobile&&__tbClassName === "tb_normal muiTable"){
			$(control.options.domElement).attr("tableStyle","");
		}else{
			$(control.options.domElement).attr("tableStyle",control.options.values.tableStyle);
		}
		//判断基本信息是否设置，如果没有设置，默认取第一个表格的颜色（暂时屏蔽该接口）
		/*var tableControl = _Designer_Control_GetFirstTableControl();
		if(tableControl && $(tableControl.options.domElement).attr("tableStyle") 
				&& !$(tableControl.options.domElement).attr("baseInfoTableStyle")){
			var rtnValue = JSON.parse(tableControl.options.values.tableStyle.replace(/quot;/g,"\""));
			_Designer_Control_baseInfoTable_style_Set_CallBack(tableControl, rtnValue);
		}*/
	});
	dialog.setWidth(window.screen.width*520/1366+"");
	dialog.show();
}

function _Designer_Control_baseInfoTable_style_Set(){
	var builder = MobileDesigner.instance.mobileDesigner.builder;
	if(!builder){
		return;
	}
	//请求数据
	var data=new KMSSData().AddBeanData("sysStandardTableStyleExt").GetHashMapArray();
	// 移动端过滤掉表格内外无边框，互联网风格轻量化的样式
	if(Designer.instance.isMobile){
		for(var i=0;i<data.length;i++){
			if(typeof data[i].name!= "undefined" && data[i].name === Designer_Lang.tb_normal_lightweight){
				data.splice(i,1);
				break;
			}
		}

	}
	var paramObj={};
	paramObj.data=data;
	//获取原先的表格样式
	var designer = $("#designer_draw")[0] || $("#designPanel")[0];
	var table = $("table[fd_type='standardTable']",designer)[0];
	var tableControl = builder.getControlByDomElement(table);
	//paramObj.chooseStyle=$(table).attr("baseInfoTableStyle");
	//加载表格样式
	var values=tableControl.options.values;
	if(values.baseInfoTableStyle){
		var baseInfoTableStyle=JSON.parse(values.baseInfoTableStyle.replace(/quot;/g,"\""));
		paramObj.chooseStyle=baseInfoTableStyle.tbClassName;
		//$("#"+control.options.domElement.getAttribute("id")).attr("class",tableStyle["tbClassName"]);
	}
	//构建弹窗
	var dialog=new StandardTable_ModelDialog_Show(Com_Parameter.ContextPath+"sys/xform/designer/standardtable/stylePreview.jsp",paramObj,function(rtnValue){
		if(!rtnValue){
			return;
		}
		if (Designer.instance.isMobile) {
			$(tableControl.options.domElement).attr("setBaseInfoTableStyle",true);
		}
		$("#"+tableControl.options.domElement.getAttribute("id")).prev("[name='dynamicBaseInfoTableStyle']").remove();
		var __tbClassName = rtnValue["tbClassName"];
		if (__tbClassName === "tb_normal_noanyborder") {
			__tbClassName = "tb_normal " + __tbClassName;
		}
		if (Designer.instance.isMobile&&__tbClassName === "tb_normal") {
			__tbClassName="muiSimple"
		}else{
			$("#"+tableControl.options.domElement.getAttribute("id")).before("<link rel='stylesheet' name='dynamicBaseInfoTableStyle' type='text/css' href='"+Com_Parameter.ContextPath+rtnValue['pathProfix']+"/standardtable.css'/>");
		}
		//$("#"+tableControl.options.domElement.getAttribute("id")).attr("baseInfoTableStyle",__tbClassName);
		$("#baseInfoTable").attr("class",__tbClassName);
		tableControl.options.values.baseInfoTableStyle=JSON.stringify(jQuery.extend({}, rtnValue)).replace(/"/g,"quot;");
		if (Designer.instance.isMobile&&__tbClassName === "muiSimple") {
			$(tableControl.options.domElement).attr("baseInfoTableStyle","");
		}else{
			$(tableControl.options.domElement).attr("baseInfoTableStyle",tableControl.options.values.baseInfoTableStyle);
		}

	});
	dialog.setWidth(window.screen.width*520/1366+"");
	dialog.show();
}

//钉钉模式下设置默认表格样式
function _Designer_Control_StandardTable_Set_Default_Style(control){
	if (Com_Parameter.dingXForm === "true") {
		if (control.type === "standardTable" && !control.options.values.tableStyle) {
			var tbClassName = "tb_normal_blacksolid";
			var pathProfix = "sys/xform/designer/standardtable/tablestyle/tb_normal_blacksolid";
			var defaultTbStyle = Com_Parameter.ContextPath + pathProfix;
			$("#"+control.options.domElement.getAttribute("id")).prev("[name='dynamicTableStyle']").remove();
			$("#"+control.options.domElement.getAttribute("id")).before("<link rel='stylesheet' name='dynamicTableStyle' type='text/css' href='"+defaultTbStyle+"/standardtable.css'/>");
			$("#"+control.options.domElement.getAttribute("id")).attr("class",tbClassName);
			var styleObj = {};
			styleObj["pathProfix"]=pathProfix;
			styleObj["tbClassName"]="tb_normal_blacksolid";
			control.options.values.tableStyle=JSON.stringify(jQuery.extend({}, styleObj)).replace(/"/g,"quot;");
			$(control.options.domElement).attr("tableStyle",control.options.values.tableStyle)
		}
	}
}

/**
 * 使用两列布局
 * @param tableControl
 * @returns
 */
function _Designer_Control_StandardTable_Use_2Column(tableControl){
	if (typeof Com_Parameter.dingXForm != "undefined" && Com_Parameter.dingXForm == "true") {
		var designer = tableControl.owner.owner;
		var tableAttrs = tableControl.attrs;
		tableAttrs.cell.value = 2;
	}
}


function _Designer_Control_GetFirstTableControl(){
	if(typeof MobileDesigner == "undefined" || !MobileDesigner || !MobileDesigner.instance || !MobileDesigner.instance.mobileDesigner)
		return;
	var builder = MobileDesigner.instance.mobileDesigner.builder;
	if(!builder){
		return;
	}
	var designer = $("#designer_draw")[0] || $("#designPanel")[0];
	var table = $("table[fd_type='standardTable']",designer)[0];
	tableControl = builder.getControlByDomElement(table);
	return tableControl;
}

function _Designer_Control_baseInfoTable_style_Set_CallBack(tableControl, rtnValue){
	if(!tableControl || !rtnValue){
		return;
	}
	
	if (Designer.instance.isMobile) {
		$(tableControl.options.domElement).attr("setBaseInfoTableStyle",true);
	}
	$("#"+tableControl.options.domElement.getAttribute("id")).prev("[name='dynamicBaseInfoTableStyle']").remove();
	$("#"+tableControl.options.domElement.getAttribute("id")).before("<link rel='stylesheet' name='dynamicBaseInfoTableStyle' type='text/css' href='"+Com_Parameter.ContextPath+rtnValue['pathProfix']+"/standardtable.css'/>");
	var __tbClassName = rtnValue["tbClassName"];
	if (__tbClassName === "tb_normal_noanyborder") {
		__tbClassName = "tb_normal " + __tbClassName;
	}
	//$("#"+tableControl.options.domElement.getAttribute("id")).attr("baseInfoTableStyle",__tbClassName);
	$("#baseInfoTable").attr("class",__tbClassName);
	tableControl.options.values.baseInfoTableStyle=JSON.stringify(jQuery.extend({}, rtnValue)).replace(/"/g,"quot;");
	$(tableControl.options.domElement).attr("baseInfoTableStyle",tableControl.options.values.baseInfoTableStyle)
}


//标准表格样式
function _Designer_Control_StandardTable_OnDraw(parentNode, childNode) {
	_Designer_Control_StandardTable_Use_2Column(this);
	var cells = {row:this.attrs.row.value, cell:this.attrs.cell.value}, domElement, row, cell;
	this.options.domElement = document.createElement('table');
	var index = _Designer_Index_Object.table ++;
	var label = index == 0 ? Designer_Lang.controlStandardTableMainLabelName : Designer_Lang.controlStandardTableLabelName + index;
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
	this.options.domElement.id = this.options.values.id;
	this.options.domElement.label = label;
	this.options.values.label = label;
	parentNode.appendChild(this.options.domElement);
	//设置表格相应属性
	domElement = this.options.domElement;
	domElement.setAttribute('formDesign', 'landray');
	domElement.setAttribute('align', 'center');
	domElement.className = 'tb_normal';
	domElement.setAttribute('id', this.options.values.id);
	domElement.style.width = '98%';
	var isDingXForm = false;
	if (typeof Com_Parameter.dingXForm != "undefined" && Com_Parameter.dingXForm == "true") {
		isDingXForm = true;
		_Designer_Control_StandardTable_Set_Default_Style(this);
	}
	//绘制行和列
	for (var i = 0; i < cells.row; i++) {
		row = domElement.insertRow(-1);
		for (var j = 0; j < cells.cell; j++) {
			cell = row.insertCell(-1);
			cell.setAttribute('row', '' + i);            //记录行数(多值，以逗号分割，有严格顺序)
			cell.setAttribute('column', '' + j);         //记录列数(多值，以逗号分割，有严格顺序)
			cell.innerHTML = '&nbsp;';
			if (j % 2 == 0) {
				cell.className = this.attrs.cell.style || 'td_normal_title';
				// 如果是钉钉集成，则固定标题单元格为99.6px
				if(isDingXForm){
					cell.width = '99.6px';
				}else{
					cell.width = '15%';					
				}
			} else {
				if(!isDingXForm){
					cell.width = '35%';
				}
			}
		}
	}

}

function _Designer_Control_StandardTable_OnDrawEnd() {
	var domElement = this.options.domElement;
	domElement.label = this.options.values.label;
	if(this.options.values.width){
		$(domElement).css('width',this.options.values.width);
		$(domElement).attr('_width',this.options.values.width);
	}else{
		domElement.style.width = '98%';
		$(domElement).removeAttr('_width');
	}
}

// 生成XML
function _Designer_Control_StandardTable_DrawXML() {
	if (this.children.length > 0) {
		var xmls = [];
		this.children = this.children.sort(Designer.SortControl);
		for (var i = 0, l = this.children.length; i < l; i ++) {
			var c = this.children[i];
			if (c.drawXML) {
				var xml = c.drawXML();
				if (xml != null)
					xmls.push(xml, '\r\n');
			}
		}
		return xmls.join('');
	}
	return '';
}

function _Designer_Control_StandardTable_AddCommons(children, tables, commons) {
	for (var i = 0, l = children.length; i < l; i ++) {
		var c = children[i];
		if (c.drawXML) {
			var xml = c.drawXML();
			if (xml != null)
				commons.push(xml, '\r\n');
		}
	}
}



// ============ 链接控件
Designer_Config.controls.linkLabel = {
	type : "linkLabel",
	storeType : 'none',
	inherit    : 'textLabel',
	onDraw : _Designer_Control_LinkLabel_OnDraw,
	onDrawEnd : null,
	onInitialize : _Designer_Control_LinkLabel_OnInitialize,
	attrs : {
		link : {
			text: Designer_Lang.controlLinkLabelAttrLink,
			value: "http://",
			hint: Designer_Lang.controlLinkLabelAttrLinkHint,
			type: 'textarea',
			show: true,
			required: true,
			validator: Designer_Control_Attr_Required_Validator,
			convertor: Designer_Control_Attr_HtmlQuotEscapeConvertor
		},
		color : {
			text: Designer_Lang.controlTextLabelAttrColor,
			value: "#4285f4",
			type: 'color',
			show: true
		}
	},
	info : {
		name: Designer_Lang.controlHyperLinkLabelInfoName
	},
	resizeMode : 'no'
}

function _Designer_Control_LinkLabel_OnInitialize() {
	var attrs = this.attrs;
	this.attrs = {};
	var index = 1;
	for (var name in attrs) {
		if (name == 'link') {
			break;
		}
		if (index == 2) {
			this.attrs.link = attrs.link;
		}
		this.attrs[name] = attrs[name];
		index ++;
	}
	//修复历史换行
	/*$(this.options.domElement).css("word-wrap","normal");
	$(this.options.domElement).css("word-break","keep-all");*/
	$(this.options.domElement).addClass("xform_linkLabel");
}

function _Designer_Control_LinkLabel_OnDraw(parentNode, childNode) {
	var domElement = _CreateDesignElement('label', this, parentNode, childNode);
	if(this.options.values.id == null)
		this.options.values.id = "fd_" + Designer.generateID();
		
	var linkObj = document.createElement("a");
	linkObj.onclick = "return false;";
	linkObj.ondblclick = "return false;";
	linkObj.href = "#";
	//临时存储这几个属性 ，后台替换回来，防止多浏览器下直接链接到页面 作者 曹映辉 #日期 2015年7月1日
	$(linkObj).attr("_target","_blank");
	$(linkObj).attr("_href",this.options.values.link ? this.options.values.link : "#");
	//linkObj.target = "none";
	
	if(this.options.values.content==null || this.options.values.content==''){
		this.options.values.content = this.info.name + _Designer_Index_Object.linkLabel ++;
		linkObj.innerHTML = this.options.values.content;
	} else {
		linkObj.innerHTML = this.options.values.content.replace(/\r\n|\n/g, '<br>');
	}
	linkObj.style.fontFamily = this.options.values.font ? this.options.values.font : null;
	linkObj.style.fontSize = this.options.values.size ? this.options.values.size : null;
	linkObj.style.color = this.options.values.color ? this.options.values.color : null;
	linkObj.style.fontWeight = this.options.values.b == "true" ? "bold" : "normal";
	linkObj.style.fontStyle = this.options.values.i == "true" ? "italic" : "normal";
	linkObj.style.textDecoration = this.options.values.underline == "true" ? "underline" : "none";
	
	domElement.innerHTML = linkObj.outerHTML; // only ie
	$(this.options.domElement).addClass("xform_linkLabel");
	
}

//单行文本框勾选不显示时，必填无意义
function _Designer_Control_InputText_CanShow(dom){
	if(!dom.checked){
		$(dom.form.required).prop('checked',false);
	}
}

function Designer_Control_Attr_Encrypt(name, attr, value, form, attrs, values, control){
	var checked = false;
	var encrypt = control.options.values.encrypt || 'false';
	if(encrypt == 'true'){
		checked = true		
	}
	var msg = Designer_Lang.controlAttrEncryptTip;
	var html = Designer_AttrPanel.msgCheckBoxDraw("encrypt","true",checked,msg);
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function Designer_Control_Attr_ConcatSubTableRowIndex(name, attr, value, form, attrs, values, control){
	var checked = false;
	var concatSubTableRowIndex = control.options.values.concatSubTableRowIndex || 'false';
	if(concatSubTableRowIndex == 'true'){
		checked = true		
	}
	var msg = Designer_Lang.controlAttrConcatSubTableRowIndexTip;
	var html = Designer_AttrPanel.msgCheckBoxDraw("concatSubTableRowIndex","true",checked,msg);
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}
function relationSource_getVal(name,attr,value,controlValue) {
	var opts = attr["opts"];
	if(undefined==opts){
    		return "";
    	}
	if(undefined==opts){
		return "";
	}
	for (var i = 0; i < opts.length; i++) {
		var opt = opts[i];
		if (opt.value === value) {
			controlValue[name] = opt.text;
			return opt.text;
		}
	}
	return "";
}

function opts_common_translator(change,obj) {
	if (!change) {
		return "";
	}
	var opts = obj.opts;
	if(undefined==opts){
    		return "";
    }
	for (var i = 0; i < opts.length; i++) {
		var opt = opts[i];
		if (opt.value === change.before) {
			change.oldVal= opt.text;
		}
		if (opt.value === change.after) {
			change.newVal= opt.text;
		}
	}
	if(change.oldVal== undefined){
		change.oldVal="";
	}
	if(change.newVal== undefined){
		change.newVal="";
	}
	
	var html = "<span> "+Designer_Lang.from+" (" + change.oldVal + ") "+Designer_Lang.to+" (" + change.newVal + ")</span>";
	return html; 
}

//#154128-在流程管理中，打开请假套件，进行请假，但请假类型出不来
function _Designer_Control_Select_OnInitialize() {
	//如果能找到钉钉套件的模板遮罩，那么就给select控件添加标识
	//在模板保存的时候根据标识来判断是否走新的select结构保存
	if ($(this.options.domElement).closest("[fd_type = 'mask']").length > 0) {
		$("#" + this.options.values.id).attr("useNewStyle", "false");
	}
}

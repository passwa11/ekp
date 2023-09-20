(function(){
	
	var SQLStructureInstance = null;
	var previewChart = null;
	var previewZoomInChart = null;
	var fdConfigTypeData = {systemData:"01",staticData:"10"}; /* systemData：系统数据配置、staticData：静态数据配置  */

	/*************************************************  $(document).ready Start  *************************************************/
	$(document).ready(function() {  
	    // 初始化定义组件
		var _db_components = {};
	    // "work":当前视图该组件是否有效 ,"required":该组件是否不能为空（必填校验）,"fun":组件实例类,"arr":实例数组,"dom":组件元素
		_db_components["where"] = {"work":true ,"required":false,"fun":window.SQLWhereCondition,"arr":[],"dom":$(".whereConditionTable")};
		_db_components["series"] = {"work":true ,"required":false,"fun":window.SQLSeries,"arr":[],"dom":$(".seriesTable")};
		_db_components["category"] = {"work":true ,"required":true,"fun":window.CategoryComponent,"arr":[],"dom":$(".categoryTable")};
		_db_components["select"] = {"work":true ,"required":true,"fun":window.SQLSelectValue,"arr":[],"dom":$(".selectValueTable")};
		_db_components["filter"] = {"work":true ,"required":false,"fun":window.SQLFilterItem,"arr":[],"dom":$(".filterItemTable")};
		
		SQLStructureInstance = new SQLStructure(_db_components);
		
		JSONComponent.init(window.DbEcharts_RelationDiagram);
		
		SQLStructureInstance.relationDiagram = JSONComponent; 
		
		SQLStructureInstance.readData = dbEchartsChart_SQLStructure_Read;
		
		/**********待优化***********/
		$(document).on("SQLStructure_AfterAddTrWhenInit",function(event,argu){
			if(argu.type == "series" || argu.type == "category"){
				var table = argu.collection["dom"];
				var id = parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
				table.find("[data-pair]").attr("data-pair-id",id);
			}
		});
		
		// SQLStructure每次初始化（例如切换数据源）的时候，都重新设置data-pair元素显示
		$(document).on("SQLStructure_Init",function(event,argu){
			var structure = argu.structure;
			for(var key in structure.components){
				var component = structure.components[key];
				var paires =component["dom"].find("[data-pair]");
				if(paires.length > 0){
					$(paires[0]).show();
				}
			}
		});
		
		// 预览图表
		previewChart = new Chart({"domNode":$("#main_chart")[0]});
		previewZoomInChart = new Chart({"domNode":$("#preview_zoom_in_echart")[0]});	
		
		// 绑定  “全屏预览” 按钮点击事件
		$(".fullScreenPreviewButton").on("click",function(){
			// 校验通过才允许全屏预览图表
			if(!validateForm(true)) 
				return; 
			renderPreviewZoomInEchart();
		});
		
		// 绑定  “预览” 按钮点击事件
		$(".previewButton").on("click",function(){
			// 校验通过才允许预览渲染图表
			if(!validateForm(true)) 
				return; 
			renderPreviewEchart("click_preview_button");
		});
		
		// dbecharts定义在config.js中（详见：dbcenter/echarts/common/config.js） 
		dbecharts.init(updateFormField);	

		window.previewChart = previewChart;
		
	}); 
	/*************************************************  $(document).ready End  *************************************************/


	
	/**
	* 渲染全屏预览图表
	* @return
	*/	
	function renderPreviewZoomInEchart(){
		// 显示全屏预览区域div（覆盖显示在原配置区域之上）
		var $previewArea = $("#echart_preview_zoom_in_area");
		var scrollHeight = document.documentElement.scrollHeight;     // 页面滚动高度（总高度）
		$previewArea.css("height",scrollHeight+"px");
		$previewArea.show();
		$("html,body").scrollTop(0); // 滚动条回到顶部
		
		var isSend = false;    // 是否发送ajax请求按照系统数据配置的内容从后台读取数据来渲染EChart
		var isRandom = false;  // 是否使用随机数据
		var configType = $("input[type='radio'][name='fdConfigType']:checked").val();  // 选择的图表配置方式
		var config = {};
		if(configType == fdConfigTypeData.systemData){  // 系统数据配置
			isSend = true;
	    }
		config = getFullConfig(configType);  // 读取完整配置数据
		var echartWidth = "600px";   // 预览图表默认宽度
		var echartHeight = "500px";  // 预览图表默认高度
		// 判断图表是否宽高自适应，如果用户选择非自适应，则读取用户配置的宽高
		if(config.isAdapterSize=="false"){
			var adapterConfig = {};
			dbecharts.read("fdConfig", adapterConfig);
			var configWidth = adapterConfig.width;
			var configHeight = adapterConfig.height;
			var patrn = /^\d+(\.\d+)?$/; // 正则表达式判断是否为纯数字
			if($.trim(configWidth)!=""){
				echartWidth = patrn.test(configWidth)==true?configWidth+"px":configWidth;
			}
			if($.trim(configHeight)!=""){
				echartHeight = patrn.test(configHeight)==true?configHeight+"px":configHeight;
			}
		}
		$("#preview_zoom_in_echart").css({"width":echartWidth,"height":echartHeight}); // 重置预览图表宽高
		previewZoomInChart.newEchart(getCurrentTheme()); // 设置EChart图表主题
		previewZoomInChart.load(configType,config,isSend,isRandom);
	}
	
	/**
	* 渲染右侧预览图表
	* @param sceneId  场景标识，判断当前是在什么动作或状态下触发的渲染图表
	* 可选场景           page_init: 页面初始化      
	*                 change_style: 修改图表样式配置     
	*                 upload_static_data: 上传静态数据   
	*                 click_preview_button: 点击预览按钮
	* @return
	*/	
	function renderPreviewEchart( sceneId ){
		var isSend = false;   // 是否发送ajax请求按照系统数据配置的内容从后台读取数据来渲染EChart
		var isRandom = false;  // 是否使用随机数据（新增场景下首次加载时使用随机数据）
		var pageMethod = $('input[type="hidden"][name="method_GET"]').val(); // 获取页面编辑类型（add:新增、edit：修改）
		var configType = $("input[type='radio'][name='fdConfigType']:checked").val();  // 选择的图表配置方式
		var config = {};
		if(sceneId=="page_init"){ /**--------- 场景《 页面初始化 》  ---------**/
			
			if(pageMethod=="add"){
				isRandom = true; // 新增场景下图表数据随机	
			}else if(pageMethod=="edit" && configType==fdConfigTypeData.systemData){
				isSend = true;   // 修改场景并且系统数据配置模式下从后台请求数据渲染图表
			}
			config = getFullConfig(configType);  // 读取完整配置数据
			previewChart.newEchart(getCurrentTheme()); // 设置EChart图表主题
			
		}else if(sceneId=="change_style"){ /**--------- 场景《 修改图表样式配置 》  ---------**/
			
			var area = $("table .chartViewTable")[0];
			dbecharts.read("fdCode", config, area); // 只读取图表样式的配置
			
		}else if(sceneId=="upload_static_data"){ /**--------- 场景《 上传静态数据 》  ---------**/
			
			config = getFullConfig(configType);  // 读取完整配置数据
			
		}else if(sceneId=="click_preview_button"){ /**--------- 场景《 点击预览按钮 》  ---------**/
			
			if(configType == fdConfigTypeData.systemData){  // 系统数据配置
				isSend = true;
		    }
			config = getFullConfig(configType);  // 读取完整配置数据
			
		}else if(sceneId == "static_json_data"){
			
			config = getJsonConfig();
			
		}
		previewChart.load(configType,config,isSend,isRandom);
	}
	
	
	/**
	* 获取完整图表配置
	* @param configType 配置方式（01：系统数据配置、10：静态数据配置）
	* @return
	*/
	function getFullConfig( configType ){
		var config = getCodeData(); // 读取配置数据
        if(configType == fdConfigTypeData.staticData){ // 静态数据配置
        	//var staticDataJsonStr = $('input[type="hidden"][name="fdStaticData"]').val();
        	var staticDataJsonStr = $('textarea[name="fdStaticData"]').val();
        	var staticDataOption = staticDataJsonStr!=""?eval('('+staticDataJsonStr+')'):{};
        	// 合并excel解析出的option和用于静态数据初始化预览的基础配置option
        	previewChart.mergePersonality(staticDataOption,getInitStaticDataOption()); 
        	// 合并页面上配置的option和excel解析出的option
			previewChart.mergePersonality(config.chartOption,staticDataOption);    
			
			
        }
		return config;
	}

	function getJsonConfig(){
		var config = getCodeData(); // 读取配置数据
	    var staticDataJsonStr = $('textarea[name="fdStaticData"]').val();
	    $('input[type="hidden"][name="fdStaticData"]').val(staticDataJsonStr); // 赋值静态数据 hidden
	    try {
	    	var staticDataOption = staticDataJsonStr!=""?eval('('+staticDataJsonStr+')'):{};
			previewChart.mergePersonality(staticDataOption,getInitStaticDataOption()); 
			previewChart.mergePersonality(config.chartOption,staticDataOption); 
			$(".json_fail_title").closest("tr").hide();
		} catch (e) {
			 $(".json_fail_title").closest("tr").show();
			 $(".json_fail_error_content").html(e);
			 
		}
		return config;
	}
	/**
	* 获取静态数据echarts初始化配置，主要是显示工具栏“保存为图片”、“还原”
	* @return
	*/	
	function getInitStaticDataOption(){
		var option = {
				toolbox:{
					feature : {
						saveAsImage : {
							show : true
						},
						restore : {
							show : true
						}
					}
				}
		};
		return option;
	}

	/**
	* 列表数据-图表展现  onchange 事件处理
	* @param dom   HTML DOM Element对象
	* @return
	*/	
	function dbEcharts_changeChartShow(){
		switchConfigRowDisplay(); // 切换列表数据配置项行的隐藏或显示
	}
	
	
    /**
	* 切换列表数据配置项行的隐藏或显示
	* @return
	*/	
	function switchConfigRowDisplay(){
		var configType = $("input[type='radio'][name='fdConfigType']:checked").val();  // 选择的图表配置方式
		var type = $(".chart_template_type").attr("chart_type"); // 图表类型
		// 当图表类型为 “折线图”、“面积图”、“柱形图” 时分类组件可用，反之不可用;系列在分类不可用时，为必填
		if(type == "line" || type == "area" || type == "bar"){
			SQLStructureInstance.getComponentByKey("category").work = true;
			SQLStructureInstance.getComponentByKey("series").required = false;
		}else if(type == "gauge"){
			// 仪表盘
			SQLStructureInstance.getComponentByKey("category").work = false;
			SQLStructureInstance.getComponentByKey("series").required = false;
		}else{
			SQLStructureInstance.getComponentByKey("category").work = false;
			SQLStructureInstance.getComponentByKey("series").required = true;
		}
		SQLStructureInstance.updateDisplayComponent(); // 更新样式，目前是更新必填元素的显示隐藏
		
		var components = SQLStructureInstance.components; // 获取所有的组件对象（分类、数据系列、返回值、数据过滤、筛选项）
        if(configType == fdConfigTypeData.systemData){  /** 系统数据配置   **/
        	
        	$(".chartDataSource").closest("tr").show();  // 显示 “数据来源”
			for(var key in components){                  // 显示所有的组件（分类、数据系列、返回值、数据过滤、筛选项）
                var obj = components[key];
				if(obj.work){
					obj.dom.closest("tr").show();
				}else{
					obj.dom.closest("tr").hide();
				}
			}
        	if(components.category.work){
        		$(".XYAxisWrap").closest("tr").show();  // 显示 “轴名称”
        	}else{
        		$(".XYAxisWrap").closest("tr").hide();  // 隐藏 “轴名称”
        	}
            $(".excelDownload").closest("tr").hide();     // 隐藏 “EXCEL模板下载”
            $(".excelUpload").closest("tr").hide();       // 隐藏 “点击上传EXCEL”
            $(".upload_fail_error_content").closest("tr").hide(); // 隐藏 上传失败消息
            $(".staticData").closest("tr").hide();
            $(".jsonView").closest("tr").hide();
            $(".jsonInfo").closest("tr").hide();
            $(".json_fail_title").closest("tr").hide();
		} else if (configType == fdConfigTypeData.staticData){ /** 静态数据配置   **/
			
			$(".chartDataSource").closest("tr").hide();   // 隐藏 “数据来源”
			for(var key in components){                   // 隐藏所有的组件（分类、数据系列、返回值、数据过滤、筛选项）
                var obj = components[key];
                obj.dom.closest("tr").hide();
			}		
			$(".XYAxisWrap").closest("tr").hide();        // 隐藏 “轴名称” 
            $(".excelDownload").closest("tr").hide();     // 显示 “EXCEL模板下载”
            $(".jsonView").closest("tr").hide();
            $(".jsonInfo").closest("tr").hide();
            $(".json_fail_title").closest("tr").hide();
            $(".excelUpload").closest("tr").hide();       // 显示 “点击上传EXCEL”  隐藏
            $(".staticData").closest("tr").show();
            if($(".upload_fail_error_content").children().length>0){
            	$(".upload_fail_error_content").closest("tr").show(); // 显示 上传失败消息
            }else{
            	$(".upload_fail_error_content").closest("tr").hide(); // 隐藏 上传失败消息
            }
            
		}	
	}
	
	
	
    /**
	* 获取当前主题
	* @return
	*/	
	function getCurrentTheme(){
		var theme = $("[name='fdTheme'] option:selected").val();
		theme = (theme == "default") ? null : theme;
	    return theme;
	}
	
    /**
	* 图表样式配置-主题 onchange 事件处理，变更预览图表的主题
	* @param dom   HTML DOM Element对象
	* @return
	*/	
	function dbEcharts_changeTheme(dom){
		previewChart.changeTheme(getCurrentTheme());
	}
	
	/**
	* 选择分类，弹出分类选择框 
	* @return
	*/	
	function dbEcharts_treeDialog() {
		seajs.use(['sys/ui/js/dialog'],function(dialog) {
		 	dialog.simpleCategory('com.landray.kmss.dbcenter.echarts.model.DbEchartsTemplate','fdDbEchartsTemplateId','fdDbEchartsTemplateName',false,returnFun,null,true);
	 	});
		
		function returnFun(data){
			
			if(typeof(data.id)=='undefined'){
				return false;
			}
			
			//清空地址本的值
			$('.mf_container ol li').remove();
			$('input[name=authReaderIds]').val("");
			
			$.ajax({
			url:  Com_Parameter.ContextPath+"dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=Readable&fdTemplateId="+data.id,
			type: "GET",
			dataType: "json",
			success:function(res){
				
				var arryAuthReaderIds=[];
				var arryAuthReaderNames=[];
				$.each(res.reader,function(index,item){
					arryAuthReaderIds.push(item.id)
					arryAuthReaderNames.push(item.name)
			      
				});
				arryAuthReaderIds=arryAuthReaderIds.join(';');
				arryAuthReaderNames=arryAuthReaderNames.join(';');
				$("input[name='authReaderIds']").val(arryAuthReaderIds);
				$("textarea[name='authReaderNames']").val(arryAuthReaderNames);
				//
				var arryAutheditorIds=[];
				var arryAutheditorNames=[];
				$.each(res.editor,function(index,item){
					arryAutheditorIds.push(item.id)
					arryAutheditorNames.push(item.name)
			      
				});
				arryAutheditorIds=arryAutheditorIds.join(';');
				arryAutheditorNames=arryAutheditorNames.join(';');
				$("input[name='authEditorIds']").val(arryAutheditorIds);
				$("textarea[name='authEditorNames']").val(arryAutheditorNames);
				Address_QuickSelection("authReaderIds","authReaderNames",";",ORG_TYPE_ALL,true,res.reader,null,null,"");
				Address_QuickSelection("authEditorIds","authEditorNames",";",ORG_TYPE_ALL,true,res.editor,null,null,"");
				
			},error:function(res){
			   alert("加载分类可阅读者错误");
			 }
		}) 
			
		}
	}


	/**
	* 复选框勾选之后，控制目标的显示或者隐藏
	* @param dom 当前的复选框DOM
	* @param targetDomClass 目标DOM的class
	* @param ifTrueDisplay 复选框勾选时，目标的display样式
	* @param ifTrueDisplay 复选框不勾选时，目标的display样式
	* @return
	*/
	function dbEchartsChart_CheckBoxChange(dom,targetDomClass,ifTrueDisplay,ifFalseDisplay,callback){
		var checked = $(dom).prop("checked");
		if(checked){
			$("."+targetDomClass).css("display",ifTrueDisplay);
		}else{
			$("."+targetDomClass).css("display",ifFalseDisplay);
		}
		if(callback){
			callback($(dom),$("."+targetDomClass));
		}
	}
	
    /**
	* 宽高自适应复选框勾选事件回调函数(勾选后清空宽度、高度输入值)
	* @param $dom      jQuery DOM 对象
	* @param $target   目标 jQuery DOM 对象
	* @return
	*/
	function dbEchartsChart_AdapterSizeChange($dom,$target){
		var checked = $dom.prop("checked");
		if(checked == true){
			$target.find("input").val("");
		}
	}
	
    /**
	* 缩放区域的下拉菜单值改变事件
	* @param dom   HTML DOM Element对象
	* @return
	*/
	function dbEchartsChart_DataZoomTypeChange(dom){
		var selectVal = $(dom).val();
		if(selectVal == 'range'){
			$(".chartOption_dataZoom_range").show();
			$(".chartOption_dataZoom_count").hide();
		}else if(selectVal == 'number'){
			$(".chartOption_dataZoom_range").hide();
			$(".chartOption_dataZoom_count").show();
		}
	}
	
    /**
	* 列表数据 >> 模板下载 >> EXCEL模板下载  处理事件
	* @return
	*/	
	function downloadExcelTemplate(){
		var downloadUrl = Com_Parameter.ContextPath+"dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=downloadStaticDataTemplate&type="+$(".chart_template_type").attr("chart_type");
	     var form=$("<form>");               // 定义一个form表单
	     form.attr("style","display:none");  // form隐藏
	     form.attr("method","post");         // 请求类型（以post方式提交）
	     form.attr("action",downloadUrl);    // 模板文件下载请求地址
	     $("body").append(form);             // 将表单放置在页面body中
	     form.submit();                      // 表单提交
	}
	
	
    /**
	* 列表数据 >> 数据来源 >> 点击上传EXCEL 处理事件
	* @param fileDom  HTML DOM Element对象
	* @return
	*/	
	function uploadFileChange(fileDom){
		var index = fileDom.value.lastIndexOf("\\");
		var fileName = fileDom.value.substring(index + 1);
		var ext = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase(); // 文件扩展名

		// 校验文件类型
        if(ext!="xls"&&ext!="xlsx"){
           alert(DbcenterLang.plzChooseExcel);
           return;
        }
		
		// 显示上传文件名称
		$(".upload_file_name").text(fileName); 
		
		// jQuery ajax 异步上传文件
		var uploadUrl = Com_Parameter.ContextPath+"dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=importStaticData&type="+$(".chart_template_type").attr("chart_type");

        
        seajs.use([ 'lui/dialog' ], function( dialog ) {
        	
			window.import_load = dialog.loading(); // 显示导入loading遮罩层
		    var uploadForm = $("<form enctype=\"multipart/form-data\"></form>");  // 定义一个上传文件form表单
		    uploadForm.append($("#upload_excel_link_button").find("input[name='importFile']")); // 移动文件上传input DOM到上传表单中
		    $("body").append(uploadForm);  // 将表单放置在页面body中
			
			$.ajaxSetup({ cache: false });         // 禁止jquery ajax缓存
			uploadForm.ajaxSubmit({
				type: "post", 
				url: uploadUrl,
				dataType: "json", 
				contentType: "application/x-www-form-urlencoded; charset=utf-8",
				success: function(data) { 
					window.import_load.hide();
                	var result = data;
            		$(".upload_fail_error_content").empty();
                	if(result.importStatus=="success"){    /** 导入成功  **/
                		$(".upload_fail_error_content").closest("tr").hide(); // 隐藏 上传失败消息
                		var staticDataOption = result.result; // 用户上传的excel解析后封装出来的echart option内容
                		//$('input[type="hidden"][name="fdStaticData"]').val(LUI.stringify(staticDataOption)); // 赋值静态数据 hidden
                		$('textarea[name="fdStaticData"]').val(LUI.stringify(staticDataOption));
                		renderPreviewEchart("upload_static_data"); // 重新渲染预览图表
                	}else if(result.importStatus=="fail"){ /** 导入失败时显示错误消息  **/
                		var errorList = result.errorList;
                		for(var i=0;i<errorList.length;i++){
                			var errorDiv = "<div>"+errorList[i]+"</div>";
                			$(".upload_fail_error_content").append(errorDiv);
                		}
                		$(".upload_fail_error_content").closest("tr").show(); // 显示 上传失败消息
                	}
					// 重新创建input file文件上传元素（避免file在某些浏览器的onChange事件对同一文件不会二次触发，导致第二次选择相同文件时导入功能失效）
                	$("#upload_excel_link_button").append("<input type=\"file\" name=\"importFile\" accept=\".xls,.xlsx\" onchange=\"uploadFileChange(this);\">");
					// 删除作为临时上传文件的表单
					uploadForm.remove();
				}
			});	

        });
        
        return false; // 阻止表单自动提交事件
	}
	
	
    /**
	* 初始化 选择完数据源的回调函数
	* @param rs
	* @return
	*/
	function initSQLStructure(rs){
		var config = {};
		config.modelName = rs.modelName;
		config.modelNameText = rs.modelNameText;
		config.data = $.parseJSON(rs.rs).data;
		config.isXform = rs.isXform;
		SQLStructureInstance.init(config);
	}
	
	function dbEchartsChart_AddRow(dom,type){
		
		if(!SQLStructureInstance.isInit){
			alert(DbcenterLang.plzChoosedataSource);
			var e=window.event || arguments.callee.caller.arguments[0];
			e.preventDefault();
			e.stopPropagation();
			return false;
		}
		// 添加行时，可能需要校验
		var funStr = "dbEchartsChart_AddRow_" + type;
		if(typeof(window[funStr]) === "function"){
			if(!eval(funStr).call(window,dom)){
				return;
			}
		}
		var table = $(dom).closest("table")[0];
		
		SQLStructureInstance.newComponent(type,table);
	}
	
	function dbEchartsChart_AddRow_series(dom){
		var arr = SQLStructureInstance.getComponentByKey("series").arr;
		if(SQLStructureInstance.getComponentByKey("select").arr.length >= 2){
			if(arr.length >= 0){
				// 判断数据系列是否添加
				alert(DbcenterLang.seriesWarning);				
			}
		}
		return true;
	}
	
	function dbEchartsChart_AddRow_select(dom){
		var arr = SQLStructureInstance.getComponentByKey("select").arr;
		if(arr.length >= 1){
			// 判断数据系列是否添加
			if(SQLStructureInstance.getComponentByKey("series").arr.length > 0){
				alert(DbcenterLang.seriesWarning2);				
			}
		}
		return true;
	}
	

    /**
	* 保存的时候，需要把对象的数据抽取出来保存
	* @param data
	* @return
	*/
	function dbEchartsChart_SQLStructure_Read(data){
		if(!data.table || !data.table){
			return;
		}
		var table = data.table;
		
		for(var key in SQLStructureInstance.components){
			var com = SQLStructureInstance.components[key];
			if(!com.work){
				continue;
			}
			if(key == 'filter'){
				var datas = [];
				// 为兼容配置模式的筛选参数，做兼容（不另外做一套）
				var inputs = [];
				for(var i = 0;i < com["arr"].length;i++){   //判断有没有筛选项
					var c = com["arr"][i];  //当前筛选项对象 SQLFilterItem
					
					if(c.isValid && c.isValid()){
						var v = c.getKeyData();  //返回v:{"field":{"name":"docCreateTime","type":"DateTime","text":"创建时间"},
			                                   //         "format":{"val":"Date"},
					                                   // "defaultVal":{"val":[{"name":"defaultFilterVal","value":"2019-10-24 15:16"}]}}
						
						datas.push(v);
						inputs.push(c.transferData(v));						
					}
				}
				table[key] = datas;
				data.inputs = inputs;
			}else if(key == "where"){
				var datas = [];
				// 入参抽出来
				var dynamic = [];
				for(var i = 0;i < com["arr"].length;i++){
					var c = com["arr"][i];
					if(c.isValid && c.isValid()){
						var v = c.getKeyData();
						datas.push(v);
						if(v.fieldVal.val == "!{dynamic}"){
							dynamic.push(v);						
						}						
					}
				}
				table[key] = datas;
				table.dynamic = dynamic;
			}else{
				var datas = [];
				for(var i = 0;i < com["arr"].length;i++){
					var c = com["arr"][i];
					if(c.isValid && c.isValid()){
						datas.push(c.getKeyData());						
					}
				}
				table[key] = datas;
			}
		}
		
		table.baseModelData = {};
		table.baseModelData.modelName = SQLStructureInstance.dataSource.modelName;
		table.baseModelData.isXform = SQLStructureInstance.dataSource.isXform;
	}
	
	
	function getCodeData(){
		
		var data = {};
		dbecharts.read("fdCode", data);
		SQLStructureInstance.readData(data);
		return data;
	}
	
	
    /**
	* 更新存储JSON配置信息字符串的隐藏hidden值
	* @return
	*/	
	function updateCodeField(){
		
		var data = getCodeData();
		LUI.$('[name="fdCode"]').val(LUI.stringify(data));
		
		data = {};
		dbecharts.read("fdConfig", data);
		LUI.$('[name="fdConfig"]').val(LUI.stringify(data));
	}
	
    /**
	* 提交保存
	* @return
	*/  
	function submitForm(method){
		// 校验表单
		if(!validateForm()){
			 return false;
		}
		
		// 更新存储JSON配置信息字符串的隐藏hidden值
		updateCodeField(); 
		// 只需禁用列表数据区域，列表数据区域有自己的校验，图表样式区域仍然需要系统的标准校验
		dbecharts.disable(true,$(".dbEcharts_Configure_Table")[0]);
		
		// 添加分类id到url，用于权限过滤
		var url = document.dbEchartsChartForm.action;
		if($("input[name='fdModelName']").val() === "com.landray.kmss.sys.modeling.base.model.ModelingAppModel") {
			url = Com_Parameter.ContextPath + "sys/modeling/base/dbEchartsChart.do";
		}
		document.dbEchartsChartForm.action = Com_SetUrlParameter(url, "fdTemplateId", $("[name='fdDbEchartsTemplateId']").val());
		
		if(!Com_Submit(document.dbEchartsChartForm, method)){
			dbecharts.disable(false,$(".dbEcharts_Configure_Table")[0]);
		}
	}
	
	/**
	* 校验配置表单数据
	* @param isWarning  是否弹出校验错误提示框 （true：弹出、false：不弹出，默认是true）
	* @return
	*/	
	function validateForm( isWarning ){
		isWarning = (typeof(isWarning)=="undefined" || isWarning== true) ? true : false;
		var configType = $("input[type='radio'][name='fdConfigType']:checked").val();  // 选择的图表配置方式
        if(configType == fdConfigTypeData.systemData){  /** 系统数据配置   **/
    		// 校验一些必须的信息
    		// 数据来源不能为空
    		if(!SQLStructureInstance.isInit){
    			if(isWarning){
    				alert(DbcenterLang.plzChoosedataSource);	
    			}
    			return false;
    		}
        
    		//当分类可用时，分类是必填
    		if(!SQLStructureInstance.isValidByKey("category")){
    			if(isWarning){
    				alert(DbcenterLang.categoryCantNull);	
    			}
    			return false;
    		}
    		
    		//当系列必填
    		if(!SQLStructureInstance.isValidByKey("series")){
    			if(isWarning){
    				alert(DbcenterLang.seriesCantNull);	
    			}
    			return false;
    		}	
    		
    		// 返回值不能为空
    		if(!SQLStructureInstance.isValidByKey("select")){
    			if(isWarning){
    				alert(DbcenterLang.rsCantNull);	
    			}
    			return false;
    		}
    		
        }else if(configType == fdConfigTypeData.staticData){  /** 静态数据配置   **/
        	//var fdStaticData = $('input[type="hidden"][name="fdStaticData"]').val(); 
        	var fdStaticData = $('textarea[name="fdStaticData"]').val(); 
        	if(fdStaticData==""){
        		if(isWarning){
        		  alert(DbcenterLang.plzUploadExcel);
        		}
        		return false;
        	}
        }
		return true;
	}
	
	function dbEchartsChart_InitButton(){
		var group = {};
		var paires = $("[data-pair-id]");
		for(var i = 0;i < paires.length;i++){
			var pair = $(paires[i]).data("pair-id");
			if(group.hasOwnProperty(pair)){
				group[pair].push($(paires[i]));
			}else{
				group[pair] = [];
				group[pair].push($(paires[i]));
			}
		}
		
		for(var key in group){
			if(group[key].length > 1){
				group[key][0].hide();
			}
		}
	}
	
	function updateFormField(){
		var value = LUI.$.trim(LUI.$('[name="fdCode"]').val());
		var data = value==''?{}:LUI.toJSON(value);

		/**************************** 图表样式配置设置 *********************************/
		seajs.use(['dbcenter/echarts/db_echarts_chart/configure/js/chartStyle','lui/topic'], function(chartStyle,topic) {
			
			// 必须样式加载完，再加载预览，刷新chart
			topic.channel("dbcenterchart").subscribe('chart.style.onload', function(chartSty){
				// 获取图表类型
				var type = chartSty.curItem.chart.type; 

				// 设置模板下载中显示的图表类型名称
				$(".chart_template_type").text(chartSty.curItem.chart.text);
                
				// 获取上一次选择的图表类型，用来跟本次选择的图表类型比较，如果发生变化说明用户重新选择了新的图表类型，则清空已上传的Excel名称文本、从Excel导入的静态数据、Excel导入错误提醒消息
				var lastChartType = $(".chart_template_type").attr("chart_type"); 
				if(lastChartType && lastChartType!="" && lastChartType!=type){
					$(".upload_file_name").text("");
					$('input[type="hidden"][name="fdStaticData"]').val("");
					$(".upload_fail_error_content").empty();
				}

				// 将本次选择的图表类型设置到指定的特殊属性中
				$(".chart_template_type").attr("chart_type",type);
				// 切换列表数据配置项行的隐藏或显示
				switchConfigRowDisplay(); 

				// 渲染预览图表
				renderPreviewEchart("page_init");

			});

			// 图表样式配置 Table
			var table = $(".chartViewTable"); 
			// 初始化图表样式对象（domNode为样式配置表格的dom，value为echart配置的option对象，chartsType为所有图表类型JSON信息对象）
			var chartStyle = new chartStyle.chartStyle({"domNode":table,"value":data.chartOption,"chartsType":window.__chartsType});
			// 主题可选集合
			chartStyle.themes = window.echart_themes;
			// 显示为空值的控件名称集合
			chartStyle.nullValueControls = data.nullValueControls||[];
			chartStyle.startup();
			
			chartStyle.chart = previewChart;
			previewChart.chartStyle = chartStyle;
			
			// 首次进来，设置“宽高自适应”默认值
			if($.isEmptyObject(data)){
				chartStyle.fixedValue.isAdapterSize = "true";
			}else{
				chartStyle.fixedValue.isAdapterSize = data.isAdapterSize;
			}
			
			if(data.table && data.table.baseModelData){
			
				var fieldDatas = SQLDataSource_findFieldDict(data.table.baseModelData);
				data.table.data = fieldDatas.data;
				data.table.isXform = data.table.baseModelData.isXform;
				
				if(!data.table.category){
					SQLStructureInstance.getComponentByKey("category").work = false;
				}
				
				SQLStructureInstance.init(data.table);
				//console.log(data.table);
			}
			
			dbecharts.write("fdCode", data);
		
			value = LUI.$.trim(LUI.$('[name="fdConfig"]').val());
			data = value==''?{}:LUI.toJSON(value);
			dbecharts.write("fdConfig", data);
			
			// 控制添加的显示和隐藏
			dbEchartsChart_InitButton();
		});
	}
	
    /**
	* 设置每个字段组件的选项
	* @param source
	* @return
	*/  
	function SQLDataSource_CustomPropertyItem(source){
		var countItem = new SQLPropertyItem({
			fieldText : DbcenterLang.count,
			fieldPinYinText : DbcenterLang.indexAndCount,
			field : '!{count}',
			fieldType : '!{count}'
		});
		source.whereItems = source.propertyItems;
		for(var i = 0;i < source.propertyItems.length;i++){
			var item = source.propertyItems[i];
			if(item.type == 'Integer' || item.type == 'Double' || item.type == 'BigDecimal' || item.type.indexOf("com.landray.kmss") > -1){
				source.selectItems.push(item);		
			}
		}
		source.selectItems.push(countItem);
		
		source.seriesItems = source.propertyItems;
		source.filterItems = source.propertyItems;
		source.categoryItems = source.propertyItems;
		
	}
	
    window.SQLDataSource_CustomPropertyItem = SQLDataSource_CustomPropertyItem;
    window.dbEcharts_changeChartShow = dbEcharts_changeChartShow;
    window.renderPreviewEchart = renderPreviewEchart;
    window.dbEcharts_changeTheme = dbEcharts_changeTheme;
    window.dbEcharts_treeDialog = dbEcharts_treeDialog;
    window.dbEchartsChart_CheckBoxChange = dbEchartsChart_CheckBoxChange;
    window.dbEchartsChart_AdapterSizeChange = dbEchartsChart_AdapterSizeChange;
    window.dbEchartsChart_DataZoomTypeChange = dbEchartsChart_DataZoomTypeChange;
    window.downloadExcelTemplate = downloadExcelTemplate;
    window.uploadFileChange = uploadFileChange;
    window.initSQLStructure = initSQLStructure;
    window.dbEchartsChart_AddRow = dbEchartsChart_AddRow;
    window.dbEchartsChart_AddRow_select = dbEchartsChart_AddRow_select;
    window.dbEchartsChart_AddRow_series = dbEchartsChart_AddRow_series;
    window.submitForm = submitForm;
   
})();
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
	<script type="text/javascript">
		Com_IncludeFile("data.js");
		seajs.use(['theme!list','sys/lbpmperson/style/css/docreater.css#']);	
	</script>
<script type="text/javascript">

var _mapModuleConfigInfo={};

var _secondaryDirectory={};

//是否为第一次
var isFrist = true;

function loadModule(){
	var kmssData = new KMSSData();
	
	kmssData.AddBeanData("sysLbpmModuleInfoService&authAdd=true&ignoreOtherCate=true", null, null, null, null);
	var data = kmssData.GetHashMapArray();
	var allModuleLabel = "${ lfn:message('sys-lbpmperson:lbpmperson.createDoc.allModule') }";
	var html="<option value=''>" +allModuleLabel+ "</option>";
	
	for(var i in data){
		
		_mapModuleConfigInfo[data[i].value]=data[i];
		//构建一级目录，若modelPath重复，则认为该模块含有多个model,需构建二级目录
		if(checkModelPathIsRepeat(data,data[i])){
			if(!_secondaryDirectory[data[i].modelPath]){
				_secondaryDirectory[data[i].modelPath] = data[i];
				html+="<option value='"+data[i].modelPath+"'>"+data[i].pathText+"</option>";
			}
		}else{
			html+="<option value='"+data[i].value+"'>"+data[i].text+"</option>";
		}
	}
	$("#search_module").html(html);
}

function checkModelPathIsRepeat(data,object){
	var bool = false;
	if(object.modelPath){
		var j = 0;
		for(var i=0;i<data.length;i++){
			if(data[i].modelPath && object.modelPath == data[i].modelPath){
				j++;
			}
			if(j>=2){
				bool = true;
				break;
			}
		}
	}
	return bool;
}
</script>
	
<div id="newDiv" style="display:none;position:absolute;">
</div>
		<div>
			
			<div class="lui-backstage-panel">
			    <div class="lui-backstage-panel-heading" style="padding: 10px 10px 10px 0px">
			    	<c:if test="${empty JsParam.mainModelName}">
				      <select id="search_module" style="height:25px;padding:0px;border: 1px solid #ccc;float: left;height: 35px;margin-right: 5px;">
				       		<option>${lfn:message('sys-lbpmperson:lbpmperson.createDoc.allModule')}</option>
				       </select>
				       <select id="_secondaryDirectory" style="height:25px;padding:0px;border: 1px solid #ccc;float: left;height: 35px;margin-right: 5px;display: none;">
				       		<option>${lfn:message('sys-lbpmperson:lbpmperson.createDoc.allModule')}</option>
				       </select>
			       </c:if>
			       <%@ include file="/sys/lbpmperson/import/category_search.jsp"%>
			</div>
			</div>
			
			<ui:iframe id="modelInfoIframe" src="${LUI_ContextPath }/sys/lbpmperson/import/createContent.jsp?cateType=${JsParam.cateType}&mainModelName=${JsParam.mainModelName}&modelName=${JsParam.modelName}&customTemplateKey=${JsParam.customTemplateKey}"></ui:iframe>
		</div>
		<script>
			function openCreate(obj,addURL){
				seajs.use(['lui/jquery',"lui/data/source",'lui/util/str'],function($,source,str){
					 var myIframe=LUI("modelInfoIframe");
					 var contentWindow = myIframe.$iframeNode[0].contentWindow;
					 if(!contentWindow){
							customIframe = myIframe.$iframeNode[0];
					 }
					 if(addURL){
						 window.open("${LUI_ContextPath }"+str.variableResolver(addURL,{id:obj}),"_blank");
					 }
					 else{
						 contentWindow.openCreate(obj);
					 }
				 });
			}
		</script>
		<c:if test="${empty JsParam.mainModelName}">
		<script>
		loadModule();
		$("#search_module").change(function(){
			selectByModelName(this.value);
		});
		
		function selectByModelName(modelName){
			//切换到全部模式
			if(!modelName){
				$("#_secondaryDirectory").hide();
				seajs.use(['lui/jquery',"lui/data/source"],function($,source){
					 var myIframe=LUI("modelInfoIframe");
					 myIframe.src="${LUI_ContextPath }/sys/lbpmperson/import/createContent.jsp?cateType=&mainModelName=&modelName=";
					 myIframe.reload();
				 });
				return ;
			}
			//判断是否需要出现二级目录
			if(_secondaryDirectory[modelName]){
				var html = '';
				var first = '';
				for(var i in _mapModuleConfigInfo){
					if(_mapModuleConfigInfo[i].modelPath==modelName){
						if(!first){
							first = _mapModuleConfigInfo[i].value;
						}
						html+="<option value='"+_mapModuleConfigInfo[i].value+"'>"+_mapModuleConfigInfo[i].text+"</option>";
					}
				}
				$("#_secondaryDirectory").html(html);
				$("#_secondaryDirectory").show();
				//默认根据二级目录的第一个选项加载Iframe
				iframeReload(first);
			}else{
				$("#_secondaryDirectory").hide();
				iframeReload(modelName);
			}
		}
		
		$("#_secondaryDirectory").change(function(){
			iframeReload(this.value);
		});
		
		function iframeReload(modelName){
			var templateName=_mapModuleConfigInfo[modelName].templateModelName;
			var isSimpleCategory="";
			var cateType=_mapModuleConfigInfo[modelName].cateType;
			var key = _mapModuleConfigInfo[modelName].key;
			seajs.use(['lui/jquery',"lui/data/source"],function($,source){
				 var myIframe=LUI("modelInfoIframe");
				 myIframe.src="${LUI_ContextPath }/sys/lbpmperson/import/createContent.jsp?cateType="+cateType+"&mainModelName="+modelName+"&modelName="+templateName+"&key="+key ;
				 
				 myIframe.reload();
			});
		}
		</script>
		</c:if>

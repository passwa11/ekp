<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>
<script type="text/javascript">

    function batchUpdate()
    {
        var checkCollect=$("input[type='checkbox']:checked");
        if(checkCollect.length==0)
        {
        	seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
        	    	dialog.alert("${lfn:message('kms-knowledge:kmsKnowledge.slectAttachments.execute')}");
        	});
          return false;
        }
        var attIds="";
        var delFormIds="";
        var formId="";
        var editId="";
        var batchId="";
        var batchIdObj=null;
        var attId="";
        checkCollect.each(function(){ 
        	attId=$(this).val();
        	if(attId.length>3)
        	{
        		attIds+=attId+";"; 
            	editId=$('#'+attId+'_doc').val();
            	batchIdObj=$('#'+attId+'_batch').val();
            	//如果该列是已经被批量编辑过
            	if(batchIdObj.length>3)
            	{
            		delFormIds+=batchIdObj.split(";")[1]+";";
            	}
            	//如果该列被编辑过，则将他们记录，需要从缓存移除
            	if(editId.length>3)
            	{
            		delFormIds+=editId+';';
            	}	  
        	}       	     
    	}); 
    	var url='${LUI_ContextPath}/kms/knowledge/kms_knowledge_multiple_upload/kmsMultipleUploadEditDoc.do?method=edit&fdModelName=${kmsMultipleUploadMainForm.modelClassName}&cateId=${kmsMultipleUploadMainForm.fdCategoryId}&categoryIndicateName=${kmsMultipleUploadMainForm.categoryIndicateName}&isBatchOperate=true&batchAttIds='+attIds+'&delFormIds='+delFormIds;
    	window.open("<c:url value='"+url+"'/>");
    }

	function addDoc() {	
		var isUploaded = attachmentObject_attachment.isUploaded();
		if(isUploaded){
			if(checkDocSubject()){
				return ;
			}
			if(checkAttSize()){
				getFileArrays();
				if(getFileArrays()){
					seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
						dialog.confirm("${lfn:message('kms-knowledge:kmsKnowledge.onloadRepetition')}", function(flag, d) {
							if (flag) {
								docSubmitBefore();
								submitFormByAjax();
							}
						});
					});
				}else{
					docSubmitBefore();
					submitFormByAjax();
				}	
			}
		}else{//附件还在上传中
			seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
		    	dialog.alert("${lfn:message('kms-knowledge:kmsKnowledge.onloadWaiting')}");
			});
		}
	}

	//检查该分类下文件是否已经存在
	function checkDocSubject(){
		var flag = false;
		seajs.use( [ 'lui/dialog' ], function(dialog) {
			var form = $('form[name=kmsMultipleUploadMainForm]');
			var cateId = form.find('input[name=fdCategoryId]').val();
			var fileNames = '';
			form.find('input[name=attIdNameJson]').each(function(){
				var inputV = $(this).val();
				if(!inputV || inputV.trim().length == 0){
					return ;
				}
				var values = inputV.split(";");
				if(!values || values.length != 2){
					return ;
				}
				fileNames+=values[1]+";";
			});
			var dataParam = {
				"docSubject" : encodeURIComponent(fileNames),
				"cateId":cateId
			};
			var url = "<c:url  value='/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=checkAddSubject'/>";
			LUI.$.ajax({
				url:url,
				data: dataParam,
				cache : false,
				dataType : "json",
				async:false,
				success : function(result) {
					//批量方式
					if(result.exsits){
						var eChecks = result.exsits;
						var msgs = '';
						for(var i=0;i<eChecks.length;i++){
							if(eChecks[i].fdIsExist && eChecks[i].fdIsExist){
								msgs+=eChecks[i].existSubject + "、";
							}
						}
						if(msgs!= ''){
							msgs = msgs.substr(0,msgs.length-1);
							dialog.alert(msgs +" 在该分类下已经存下!");
							flag = true;
							return ;
						}
					}else{
						if(result.fdIsExist && result.fdIsExist){
							dialog.alert(result.existSubject +" 在该分类下已经存下!");
							flag = true;
							return ;
						}
					}
				},
				error : function(error) {

					//dialog.alert("出错了: "+error);
				}
			});
		});
		return flag;
	}

	//删除
	function deleteUpdate(){
		var checkCollect=$("input[type='checkbox']:checked");
		  if(checkCollect.length==0){
	        	seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
	        	    	dialog.alert("${lfn:message('kms-knowledge:kmsKnowledge.chooseAttachments')}");
	        	});
	          return false;
	        }
		 var self=window.attachmentObject_${kmsMultipleUploadMainForm.fdKey};
		 
		 checkCollect.each(function(){
			 var attrId=$(this).val();
			 for(var i=0;i<self.fileList.length;i++){
                 var file = self.fileList[i];
                 if(file.fdId == attrId){
                     file.fileStatus = -1;
                     file.isDelete = -1;
                     $("#"+file.fdId).remove();
                     self.emit('editDelete',{"file":file});
                 }
			 }
		 });
		var len =  $('.upload_list_tr_edit_block').length;
		if(len == 0){
			$('.upload_list_title').hide();
			$('.upload_list_title').find('input[type=checkbox]').attr('checked', false);
			$('.upload_list_title').find('.upload_opt_select').text('全选');
		}else {
			$('.upload_list_title').show();
		}
	}
	

	function submitFormByAjax()
	{
    	seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
            	var loading = dialog.loading("${lfn:message('kms-knowledge:kmsKnowledge.uploadWaiting')}");
            		$.ajax({
    					type : "POST",
    					url :  "<c:url value='/kms/knowledge/kms_knowledge_multiple_upload/kmsMultipleUploadMain.do?method=saveDoc'/>",
    					data : $('form[name=kmsMultipleUploadMainForm]').serialize(),
    					dataType : 'json',
    					success : function(result) {
    						 loading.hide();
        				     if(result.uploadStatus!='success')
        				     {
        				    	 dialog.alert(result.errorMessage || result.uploadStatus,function(){
        				    		 parent.location.reload();
        				    	 });
        				     }else{
        				    	 parent.location.reload();
        				     }
    					}
    				});
        	     });		
	}
	//检查待上传附件数量
	function checkAttSize(){
		var fileNameArray = document.getElementsByName("inputFileName");  
		if(fileNameArray.length<1)
		{
			seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
    	    	dialog.alert("${lfn:message('kms-knowledge:kmsKnowledge.chooseAttachments')}");
    	    });
       		return false;
		}
		if(fileNameArray.length>10)
		{
			seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
		    	dialog.alert("${lfn:message('kms-knowledge:kmsKnowledge.uploadAtt.description')}");
			});
			return false;
		}
		return true;
	}
	
	function getFileArrays(){
		//提交的时候还要获取附件的动态ID
		var fileIdObjs = document.getElementsByName("attIdAndAttNameJson");
		var attPartJsonArray=document.getElementsByName("attIdNameJson");
		var ary = new Array();
		var jsonArray='[';
		for (var j = 0; j < attPartJsonArray.length; j++) {
		       var temp=attPartJsonArray[j].value.split(";");
		       temp[1] = temp[1].replace(/(['"])/g, "\\\\$1");
		       jsonArray+="{attId:'"+temp[0]+"',attName:'"+temp[1] +"'}";
		       ary[j]=temp[1];
			 if (j!= attPartJsonArray.length - 1) {
				 jsonArray += ',';
			}
		}
		var flag=false;
		var s = ary.join(",")+","; 
		for(var i=0;i<ary.length;i++) {  
			if(s.replace(ary[i]+",","").indexOf(ary[i]+",")>-1) {  
				flag=true;
				break;  
			}  
		}
		jsonArray+=']';
		fileIdObjs[0].value = jsonArray;
		return flag;	
	}

	function getAttFileInfo(rtnFunc,_dialog, getPreSubmitCallBack){
		var isUploaded = attachmentObject_attachment.isUploaded();
		if(isUploaded){
			inputVals = $('form[name=kmsMultipleUploadMainForm]');
			if(getPreSubmitCallBack){
				if(getPreSubmitCallBack(inputVals)){
					return ;
				}
			}
			if(checkAttSize()){
				getFileArrays();
				docSubmitBefore();
				var inputVals = $('form[name=kmsMultipleUploadMainForm]'); 
				rtnFunc(inputVals,_dialog);
			}
		}else{//附件还在上传中
			seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
		    	dialog.alert("${lfn:message('kms-knowledge:kmsKnowledge.onloadWaiting')}");
			});
		}
	}

	
    function setBathOperateValue()
    {
        //获取所有属于批量操作的附件id和对应的属性form的fd_Id的关系
    	var fileIdObjs = document.getElementsByName("bachOperate");
        var jsonArray='[';
		for (var j = 0; j < fileIdObjs.length; j++) {
		  if(fileIdObjs[j].value.length>0)
		  {
		       var temp=fileIdObjs[j].value.split(";");
		       jsonArray+="{attId:'"+temp[0]+"',formId:'"+temp[1] +"'}";
			   if (j!= fileIdObjs.length - 1) {
				 jsonArray += ',';
			   }
		   }
		}
		jsonArray+=']';
		//除去[]是否有实际的值存在，不存在则不需要赋值
		if(jsonArray.length>20)
		{
			document.getElementsByName("batchIdJson")[0].value=jsonArray;  
		}
    }

	
	//跳转到文档编辑页面
	function redirectEditPage(obj)
	{
		var fdId="";
		var isBatchColumn='false';
		var attIdAndName=obj.id.split(";");
		//如果被编辑过说明缓存中已经存在数据，需要将数据取出，然后呈现
		var formIdObj=document.getElementById(attIdAndName[0]+'_doc');
		//批量修改过的formId
		var batchFormIdObj=document.getElementById(attIdAndName[0]+'_batch');
		//如果该列属于批量操作列,用批量操作的form代替自身的文档form信息
        if(batchFormIdObj.value.length>3)
        {
        	fdId=batchFormIdObj.value.split(";")[1];
        	isBatchColumn='true';
        }
        else{
        	fdId=formIdObj.value;
        }
		var attEncodeName=encodeURIComponent(encodeURIComponent(attIdAndName[1]));
		var url='${LUI_ContextPath}/kms/knowledge/kms_knowledge_multiple_upload/kmsMultipleUploadEditDoc.do?method=edit&fdModelName=${kmsMultipleUploadMainForm.modelClassName}&cateId=${kmsMultipleUploadMainForm.fdCategoryId}&categoryIndicateName=${kmsMultipleUploadMainForm.categoryIndicateName}&attId='+attIdAndName[0]+'&isBatchColumn='+isBatchColumn+'&fdId='+fdId+'&attName='+attEncodeName;
		window.open("<c:url value='"+url+"'/>");
	}
	
	function redirectEditPageByfile(file)
	{
		var fdId="";
		var isBatchColumn='false';
		var attId=file.fdId;
		var attName=Com_HtmlEscape(file.fileName.substring(0,file.fileName.lastIndexOf('.')));
		//var attIdAndName=obj.id.split(";");
		//如果被编辑过说明缓存中已经存在数据，需要将数据取出，然后呈现
		var formIdObj=document.getElementById(attId+'_doc');
		//批量修改过的formId
		var batchFormIdObj=document.getElementById(attId+'_batch');
		//如果该列属于批量操作列,用批量操作的form代替自身的文档form信息
        if(batchFormIdObj.value.length>3)
        {
        	fdId=batchFormIdObj.value.split(";")[1];
        	isBatchColumn='true';
        }
        else{
        	fdId=formIdObj.value;
        }
		var attEncodeName=encodeURIComponent(encodeURIComponent(attName));
		var url='${LUI_ContextPath}/kms/knowledge/kms_knowledge_multiple_upload/kmsMultipleUploadEditDoc.do?method=edit&fdModelName=${kmsMultipleUploadMainForm.modelClassName}&cateId=${kmsMultipleUploadMainForm.fdCategoryId}&categoryIndicateName=${kmsMultipleUploadMainForm.categoryIndicateName}&attId='+attId+'&isBatchColumn='+isBatchColumn+'&fdId='+fdId+'&attName='+attEncodeName;
		window.open("<c:url value='"+url+"'/>");
	}
	
	//重命名
	function reNameInKms(docId,self){
		seajs.use( [ 'lui/jquery', 'lui/dialog', 'lui/topic' ], function($, dialog, topic) {
			for (var i = 0; i < self.fileList.length; i++){
				if(self.fileList[i].fdId == docId){
					var att=self;
						var fdFileName = att.fileList[i].fileName;
						if(fdFileName !=null &&fdFileName !=""){
							fdFileName = fdFileName.substring(0,fdFileName.lastIndexOf("."))
							fdFileName = encodeURIComponent(fdFileName);
						}
						
						var iframeUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=editName&fdFileName="+fdFileName;
						var title = '<bean:message bundle="sys-attachment" key="sysAttMain.button.rename"/>' ;
						dialog.iframe(iframeUrl, title, function(data) {
							if (null != data && undefined != data) {
								changeName = data.fdFileName;
								var fileName = att.fileList[i].fileName;
								var fileExt = fileName.substring(fileName.lastIndexOf("."));
								att.fileList[i].fileName = changeName+fileExt;
								var _filename = $("#" + att.fileList[i].fdId + " .upload_list_filename_title");
								_filename.text(changeName);
								_filename.parent().attr("title", changeName + fileExt);
								
								$('#'+att.fileList[i].fdId+'_fileName').val(changeName);
								
								var idAndNameColumn=$('#'+att.fileList[i].fdId+'_idAndName');
								idAndNameColumn.val(att.fileList[i].fdId+";"+changeName);
							}
						}, {
							width : 450,
							height : 200
						});
					
					
					break;
				}
			}
		});	
		
	}
	
	function redirectDeletePage(obj){
		 var self=window.attachmentObject_${kmsMultipleUploadMainForm.fdKey};
		 var dataAttr=$(obj).attr("data-attr");
		 for(var i=0;i<self.fileList.length;i++){
			 if(self.fileList[i].id==dataAttr){
				 self.fileList[i].fileStatus=-1;
			 }
		 }
		 $("#"+dataAttr).remove();
	}
	
	//提交前刷新附件ID信息
	function docSubmitBefore()
	{
		updateIds();
		updateAttInfo(window.attachmentObject_${kmsMultipleUploadMainForm.fdKey});
		setBathOperateValue();
	}
	
    //更新添加删除的主键ids,如果被删除，则不生成文档
    function updateIds()
    { 
    	initArray();
        //实际单个编辑的列
    	var mainDocIdObj = document.getElementsByName("mainDocId");
    	var mainDocIdsArray=buildArray(mainDocIdObj);
    	//所有单个编辑并保存到缓存的formId
    	var fdDocAddIdsValue=document.getElementsByName("fdDocAddIds")[0].value;
    	var fdDocAddIdsArray=fdDocAddIdsValue.split(";");
    	var docId="";
    	var delIds="";
    	for(var i=0;i<fdDocAddIdsArray.length;i++)
    	{
          if(mainDocIdsArray.indexOf(fdDocAddIdsArray[i])<0)
          {
        	  delIds+=fdDocAddIdsArray[i];
          }
          if(i<fdDocAddIdsArray.length-1)
          {
        	  delIds+=";";  
          }
      	}
         var reallyAddIds='';
    	 for(var j=0;j<mainDocIdsArray.length;j++)
         { 
     		 reallyAddIds+=mainDocIdsArray[j]+";";    
         }
    	document.getElementsByName("fdDocAddIds")[0].value=reallyAddIds;
        document.getElementsByName("fdDelIds")[0].value=delIds;
      }

    function buildArray(htmlCollectObj)
    {
          var array=[];
    	  for(var i=0;i<htmlCollectObj.length;i++)
          { 
            	docId=htmlCollectObj[i].value;
            	if(docId!='')
            	  array.push(docId);         	
          }
          return array;
    }

    //覆盖上传更新附件的ID的方法
    function updateAttInfo(self) {
		var attIds = document.getElementsByName("attachmentForms." + self.fdKey + ".attachmentIds");
		var attIdsJson = document.getElementsByName("fdAttIdsJson");
		if (attIds && attIds.length > 0) {
			var tempIds = "";
			var tempDelIds = "";
			var tempAttIds="[";
			for ( var i = 0; i < self.fileList.length; i++) {
				var doc = self.fileList[i];
				if (doc.fileStatus == -1) {
					if(doc.fdId.indexOf("SWFUpload") >=0){
					}else{
						tempDelIds += ";" + self.createAttMainInfo(doc);;
						
					}
				} else if(doc.fileStatus == 1) {
					if((self.fdAttType!="pic" || self.uploadAfterSelect==false )){
						var tempDocId=doc.fdId;
						doc.fdId = self.createAttMainInfo(doc);
						tempAttIds+="{attId:'"+tempDocId+"',docAttId:'"+doc.fdId +"'}";
						if(i<self.fileList.length-1){
							tempAttIds+=',';
						}
						else if(i==self.fileList.length-1)
						{
							tempAttIds+="]";
						}
					}
					tempIds += ";" + doc.fdId;
					if(doc.fdId=='' || doc.fdId==null){
						alert(doc.fileName + Attachment_MessageInfo["msg.uploadFail"]);
						return false;
					}
				}
			}
			if (tempIds.length > 0) {
				if(self.onFinishPostCustom){
					self.onFinishPostCustom(self.fileList);
				} 
				tempIds = tempIds.substring(1, tempIds.length);
			}
			if (tempDelIds.length > 0) {
				tempDelIds = tempDelIds.substring(1, tempDelIds.length);
			}
			if(attIdsJson!=null&&attIdsJson[0]!=undefined)
			{
				attIdsJson[0].value=tempAttIds;
			}
         	attIds[0].value = tempIds;
			return true;
		}
	}

	
    setTimeout(function(){$(".uploadinfotext").html($(".uploadinfotext").html()+"<span style='font-size:12px'>(${lfn:message('kms-knowledge:kmsKnowledge.uploadAtt.description')})</span>");},600);

	function initArray(){
		if (!Array.prototype.indexOf)
		{
		  Array.prototype.indexOf = function(elt /*, from*/)
		  {
		    var len = this.length >>> 0;
		    var from = Number(arguments[1]) || 0;
		    from = (from < 0)
		         ? Math.ceil(from)
		         : Math.floor(from);
		    if (from < 0)
		      from += len;
		    for (; from < len; from++)
		    {
		      if (from in this &&
		          this[from] === elt)
		        return from;
		    }
		    return -1;
		  };
		}
	};
</script>

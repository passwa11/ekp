<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>    <%--<meta http-equiv="X-UA-Compatible" content="IE=7"></meta>--%>    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<script>
	Com_IncludeFile('echarts.js','${LUI_ContextPath}/sys/ui/js/chart/echarts/','js',true);
	
</script>
<c:set var="ssForm" value="${requestScope[param.formName]}" />
<c:set var="template" value="${param.template}" />
<c:set var="sysTagMainForm" value="${requestScope[param.formName].sysTagMainForm}" />
<% 
Object form = pageContext.getAttribute("ssForm");
Object template = pageContext.getAttribute("template");
if(template!=null){
	String temp=(String)template;
	String[] tempArr=temp.split(":");
	String[] catArr=tempArr[1].split("=");
	String docCategoryId=catArr[1].substring(2, catArr[1].length()-1);
	if(PropertyUtils.isReadable(form, docCategoryId)){
		Object  docCategoryIdFind = PropertyUtils.getProperty(form,docCategoryId);
		String categoryId = (String)docCategoryIdFind;
		String url=tempArr[1].replace(catArr[1], categoryId);
		request.setAttribute("url",url);
	}
	if(PropertyUtils.isReadable(form,tempArr[0])){
		Object  docCategoryName = PropertyUtils.getProperty(form,tempArr[0]);
		String docCategory = (String)docCategoryName;
		request.setAttribute("docCategory",docCategory);
	}
}
Object sysTagMainForm = pageContext.getAttribute("sysTagMainForm");
if(sysTagMainForm!=null){
	if(PropertyUtils.isReadable(sysTagMainForm,"fdModelName")){
		Object  modelName =PropertyUtils.getProperty(sysTagMainForm,"fdModelName");
		String fdMmodelName=(String)modelName;
		Object  fdTagNames = PropertyUtils.getProperty(sysTagMainForm,"fdTagNames");
		String fdTag=(String)fdTagNames;
		request.setAttribute("fdTag",fdTag);
		String[] fdTagNs=fdTag.split(" ");
		int num=0;
		if(fdTag.length()!=0){
			num=fdTagNs.length;
		}
		request.setAttribute("fdTagNs",fdTag);
		request.setAttribute("num",num);
		request.setAttribute("fdMmodelName",fdMmodelName);
	}
}
if(PropertyUtils.isReadable(form,"docAuthorId")){
	Object  docAuthorId = PropertyUtils.getProperty(form,"docAuthorId");
	if(PropertyUtils.isReadable(form,"outerAuthor")){
		Object  docAuthorName = PropertyUtils.getProperty(form,"outerAuthor");
		if(("".equals(docAuthorName))||docAuthorName==null){
			docAuthorName = PropertyUtils.getProperty(form,"docAuthorName");
		}else{
			docAuthorId = null;
		}
		String authorName = (String)docAuthorName;
		request.setAttribute("authorName",authorName);
	}
		request.setAttribute("docAuthorId",docAuthorId);
}if(PropertyUtils.isReadable(form,"docSubject")){
	Object  docSubject = PropertyUtils.getProperty(form,"docSubject");
	String docSub = (String)docSubject;
	request.setAttribute("docSub",docSub);
}

boolean isLowerThanIE8 = (SysAttViewerUtil.isLowerThanIE8(request) ? true
		: false);
if (!isLowerThanIE8) {
%>

<c:choose>
<c:when test="${JsParam.titleicon != null and JsParam.titleicon != '' }">
	<c:set var="this_titleicon" value="${JsParam.titleicon }"></c:set>
	<c:set var="this_toggle" value="true"></c:set>
</c:when>
<c:otherwise>
	<c:set var="this_titleicon" value="lui_iconfont_navleft_releation_group"></c:set>
	<c:set var="this_toggle" value="false"></c:set>
</c:otherwise>
</c:choose>
<ui:content title="${lfn:message('kms-multidoc:kmsMultidocKnowledge.relationCircle')}" toggle="${this_toggle }" titleicon="${this_titleicon }">
<div id="relation_echarts" style="width:316px;height:300px;"  >

</div>
<ui:event event="show">

var myChart = echarts.init(document.getElementById('relation_echarts'),true);

	myChart.showLoading();
    myChart.hideLoading();
    		var nadenames=[];//处理node重名不显示问题
    		var node1s=[];
    		var name;
    		var subname;
    		var url;
    		var fdTagNs = '${fdTagNs}'.split(' ');
    		var labelNameAppli="${lfn:message('sys-tag:sysTagCategory.alltags')}";
    		var	docSubNameAppli="${lfn:message('sys-follow:sysFollowPersonConfig.fdSubject')}";
    		var	authorNameAppli="${lfn:message('sys-doc:sysDocBaseInfo.docAuthor')}";
    		var	catagoryNameAppli="${lfn:message('sys-category:table.SysCategoryFiling')}";
    		
			for (var i = 0; i <parseInt('${num}'); i++) {
				name=fdTagNs[i];
				url = '${ LUI_ContextPath}/sys/ftsearch/searchBuilder.do?method=search&searchFields=tag&newLUI=true&modelName=${fdMmodelName }&queryString='+name;
				url = encodeURI(url);	  				
				if(name.length>=4){
					subname=name.substring(0,3)+"…";
				}else{
					subname=name;
				}
				nadenames.push(subname);
			       node1s[i]={
	 	            	category:0,
	     	            name:subname,
	     	            key:labelNameAppli,
	     	           tooltip:{show:true},
	     	            subname:name,
	     	            value :10,
	     	            itemStyle: {
		                    normal: {
		                    	color: '#8DCEE7',
		                    	
			           
		                    },
		                    emphasis: {
			                    label: {
			                        show: true
			                    }
			                }
	                	},
	                	
	     	            url:url
	     	         }
			}
			name='${docSub}';
			if(name.length>=4){
				subname=name.substring(0,3)+"…";
			}else{
				subname=name;
			}
			if((nadenames.indexOf(subname))>-1){
				subname=subname+" ";
				nadenames.push(subname);
			}else{
				nadenames.push(subname);
			}
    	   var  nodes=[
           		 {	
           	   		category:0,
  	            	draggable:'true',
            	    name:subname,
            	    subname:name,
            	    key:docSubNameAppli,
            	    value :20,
            	    tooltip:{show:true},
            	    itemStyle: {
		                normal: {
		                	color: '#5AB1EF',
		                    label: {
		                        show: true,
		                        textStyle: {
		                          color: '#000000'
		                        }
		                    },
		                    nodeStyle : {
		                        brushType : 'both',
		                        borderColor : 'green',
		                        borderWidth : 1
		                    },
		                    linkStyle: {
		                        type: 'curve'
		                    }
		                },
		                emphasis: {
		                    label: {
		                        show: false
		                        // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
		                    },
		                    nodeStyle : {
		                        //r: 30
		                    },
		                    linkStyle : {}
		                }
		            }
  	             }

    	   ],
    	   nodes=nodes.concat(node1s);
    	   name='${authorName}';
    	   var names=[];
    	   names = name.split(";");
    	   for(var i=0;i<names.length;i++){
			if(names[i].length>=4){
				subname=names[i].substring(0,3)+"…";
			}else{
				subname=names[i];
			}
			if((nadenames.indexOf(subname))>-1){
				subname=subname+" ";
				nadenames.push(subname);
				
			}else{
				nadenames.push(subname);
			}
    	   var a=names[i]
	    	  if(names[i]){
	    	 	 var nadeAuthor={
	           		   category:0,
	            	   name:subname,//默认显示的
	            	   subname:names[i],//鼠标监听显示的
	            	    tooltip:{show:true},
	            	   value :3,
	            	   key: authorNameAppli,
	           	       itemStyle: {
	               		    normal: {
	               		   		color: '#00fa9a',
		               		    label:{
									show: true,
									 textStyle: {
	                                    color: 'black'
	                                }
								}
	                      	},
	                      	emphasis: {
			                    label: {
			                        show: true
			                    }
			                }
	                   	}
	    	         };
		    	  	 if(${not empty docAuthorId}){
		    	  			var docAuthorId='${docAuthorId}';
		    	  	 		var authorIds=[];
    	   					authorIds = docAuthorId.split(";");
			    	       nadeAuthor.url = '${ LUI_ContextPath}/sys/zone/index.do?userid='+authorIds[i];
		    	  	 }
	    	         nodes.push(nadeAuthor);
	    	  }
	    	 }  
	    	  if(${not empty docCategory}){
		    	name='${docCategory}';
				if(name.length>=4){
					subname=name.substring(0,3)+"…";
				}else{
					subname=name;
				}
				if((nadenames.indexOf(subname))>-1){
					subname=subname+" ";
					nadenames.push(subname);
				}else{
					nadenames.push(subname);
				}
	    	  	 
	    	  var nadeCategory={
 	            	category:0,
     	            subname:name,//默认显示的
	            	name:subname,//鼠标监听显示的
     	            value :2,
     	            tooltip:{show:true},
     	            key: catagoryNameAppli,
     	            itemStyle: {
	                    normal: {
	                   		 color: '#2EC7C0',
		                     label:{
									show: true,
									 textStyle: {
	                                    color: 'black'
	                                }
							}
	                    },
	                	emphasis: {
			                    label: {
			                        show: true,
			                         textStyle: {
	                                    color: 'yellow'
	                                }
			                    }
			            }
                	},
     	           	url:'${ LUI_ContextPath}${url}'
     	            	  
     	         };
    	       	 nodes.push(nadeCategory);
    	       	 }
				
			 var link1=[]
			for (var i = 0; i <names.length+3; i++) {
				link1[i]={
					source : i,
					target : 0,
					weight : 4,
					
					
					 
				}
			}	
          
        myChart.setOption({ 
	    tooltip: {
	    formatter: function(data){
		       	var d  = data["data"];
		       	if(!d.source){
		       		return d.key+':'+Com_HtmlEscape(d.subname);
		       	}else
		       		return null;
	       	},
	       	 show: false
	    },
	   
		toolbox: {
	        show : true,
	        x: 'left',
			y: 'bottom',
	        feature : {
	            restore : {show: true}
	        }
	    },
		isShowScrollBar:false,
        series : [
            {	 name: 'Les Miserables',
	          type: 'graph',
	          symbolSize: 55,
	         label: {
                normal: {
                    show: true,
                    color:'yellow'
                }
            },
             
                lineStyle:{
                	normal:{
                		color: '#000000',
                		width:2,
                		
                	},
                	
                },
             edgeLabel:{
                	normal:{
                		show:false
                		
                	},
                	
                },
                layout: 'force',
                nodes: nodes,
                links: link1,
		        minRadius : 22,
		        maxRadius : 30,
		        scaling: 1.1,
		         draggable: true,
		        roam: 'move',
                force: {
                    repulsion: 250,
                    edgeLength:100
                    //gravity: 1
                }
            }
        ]
    });
    window.onresize = myChart.resize;
      //实现节点点击事件
		function focus(param) {
			var option = myChart.getOption();
			var data = param.data;
			//判断节点的相关数据是否正确
			if (data != null && data != undefined) {
				if (data.url != null && data.url != undefined) {
					//根据节点的扩展属性url打开新页面
					window.open(data.url);
				}
			}
		}
		//绑定图表节点的点击事件
		myChart.on('click', focus);
		myChart.resize();
</ui:event>
</ui:content>
	<% 
		}
	%>
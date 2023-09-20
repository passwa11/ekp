define(
		['dojo/_base/declare',"dijit/_WidgetBase","dojo/query","dojo/dom-attr"],
		function(declare,WidgetBase,query,domAttr) {
			var freeflowNodeSplit = declare("sys.lbpmservice.mobile.freeflow.freeflowNodeSplit",
					[WidgetBase], {
				
						buildRendering: function() {
					        this.inherited(arguments);
						},
						
						postCreate : function() {
							this.inherited(arguments);
							this.subscribe("/sys/lbpmservice/freeflow/formatNodeFinish","buildSplit");
						},
						
						buildSplit : function(){
							var node = lbpm.nodes[this.nodeId];
							if(node){
								if(node.XMLNODENAME=="splitNode"){
									var qDom = query(".freeFlowOrg[data-id='"+this.nodeId+"']",query(".freeFlowFiledShow")[0]);
									qDom.addClass("freeflowSplit");
									var nextPoint = this.getNextPoint();
									var point = this.getPoint();
									var width = nextPoint.maxTop-nextPoint.minTop;
									var top = nextPoint.minTop + width/2 - point.top + 18.5 ;
									var left = 95-width/2;
									qDom.append("<style>.freeFlowFiledShow .freeflowSplit[data-id='"+this.nodeId+"']:before{width:"+width+"px;top:"+top+"px;left:"+left+"px}</style>");
									for(var i=0;i<node.endLines.length;i++){
										var nextNodeId = node.endLines[i].endNode.id;
										var qDom = query(".freeFlowOrg[data-id='"+nextNodeId+"']",query(".freeFlowFiledShow")[0]);
										qDom.addClass("freeflowSplitNodeLeft");
									}
								}else if(node.XMLNODENAME=="joinNode"){
									var qDom = query(".freeFlowOrg[data-id='"+this.nodeId+"']",query(".freeFlowFiledShow")[0]);
									qDom.addClass("freeflowSplit");
									var prePoint = this.getNextPoint();
									var point = this.getPoint();
									var width = prePoint.maxTop-prePoint.minTop;
									var top = prePoint.minTop + width/2 - point.top + 18.5 ;
									var left = -width/2;
									qDom.append("<style>.freeFlowFiledShow .freeflowSplit[data-id='"+this.nodeId+"']:before{width:"+width+"px;top:"+top+"px;left:"+left+"px}</style>");
									qDom.addClass("freeflowSplitNodeLeft");
									for(var i=0;i<node.startLines.length;i++){
										var preNodeId = node.startLines[i].startNode.id;
										var qDom = query(".freeFlowOrg[data-id='"+preNodeId+"']",query(".freeFlowFiledShow")[0]);
										qDom.addClass("freeflowSplitNodeRight");
										var qLeft = domAttr.get(qDom[0],"data-left");
										if(qLeft){
											qleft = parseInt(qLeft);
										}
										qDom.append("<style>.freeFlowFiledShow .freeflowSplitNodeRight[data-id='"+preNodeId+"'] .nodeAddLine:after{width:"+(point.left-qleft-97)+"px;}</style>");
									}
								}
							}
						},
						
						getNextPoint:function(){
							var maxTop = 0;
							var minTop = 999999;
							var left = 0;
							var node = lbpm.nodes[this.nodeId];
							if(node){
								if(node.XMLNODENAME=="splitNode"){
									for(var i=0;i<node.endLines.length;i++){
										var nextNodeId = node.endLines[i].endNode.id;
										var qDom = query(".freeFlowOrg[data-id='"+nextNodeId+"']",query(".freeFlowFiledShow")[0]);
										if(qDom.length>0){
											var qLeft = domAttr.get(qDom[0],"data-left");
											if(qLeft){
												left = parseInt(qLeft);
											}
											var qTop = domAttr.get(qDom[0],"data-top");
											if(qTop){
												qTop = parseInt(qTop);
												if(maxTop<qTop){
													maxTop = qTop;
												}
												if(minTop>qTop){
													minTop = qTop;
												}
											}
										}
									}
								}else if(node.XMLNODENAME=="joinNode"){
									for(var i=0;i<node.startLines.length;i++){
										var preNodeId = node.startLines[i].startNode.id;
										var qDom = query(".freeFlowOrg[data-id='"+preNodeId+"']",query(".freeFlowFiledShow")[0]);
										if(qDom.length>0){
											var qLeft = domAttr.get(qDom[0],"data-left");
											if(qLeft){
												left = parseInt(qLeft);
											}
											var qTop = domAttr.get(qDom[0],"data-top");
											if(qTop){
												qTop = parseInt(qTop);
												if(maxTop<qTop){
													maxTop = qTop;
												}
												if(minTop>qTop){
													minTop = qTop;
												}
											}
										}
									}
								}
							}
							return {maxTop:maxTop,minTop:minTop,left:left};
						},
						
						getPoint:function(){
							var left = 0;
							var top = 0;
							var qDom = query(".freeFlowOrg[data-id='"+this.nodeId+"']",query(".freeFlowFiledShow")[0]);
							if(qDom.length>0){
								var qLeft = domAttr.get(qDom[0],"data-left");
								if(qLeft){
									left = parseInt(qLeft);
								}
								var qTop = domAttr.get(qDom[0],"data-top");
								if(qTop){
									top = parseInt(qTop);
								}
							}
							return {top:top,left:left};
						}
					});
			return freeflowNodeSplit;
		});
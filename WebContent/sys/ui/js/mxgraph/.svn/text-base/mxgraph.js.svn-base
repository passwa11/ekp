define(function(require, exports, module) {
	var $ = require('lui/jquery');
	
//	/** UML节点信息接口 */
//	export interface INode {
//	  /** 节点id */
//	  id: string | number
//	  /** 节点名称 */
//	  label: string
//	  /** 节点层级，从0开始 */
//	  level?: number
//	}
//
//	/** UML连线信息 */
//	export interface IEdge {
//	  /** 连线id，默认为`startNodeId + x + endNodeId` */
//	  id?: string | number
//	  /** 连线名称，可不填 */
//	  label?: string
//	  /** 连线开始节点，取节点id */
//	  source: string | number
//	  /** 连线结束节点，取节点id */
//	  target: string | number
//	}
//
//	/** UML数据格式 */
//	export interface IUMLData {
//	  /** 节点集合 */
//	  nodes: INode[]
//	  /** 连线集合 */
//	  edges: IEdge[]
//	}
	
	var defaultOptions = {
		nodeWidth: 120,
		nodeHeight:50,
		nodeStyle: {
			STYLE_FONTSIZE: 14
		},
		edgeStyle: {
			STYLE_ROUNDED: true,
			STYLE_EDGE: mxEdgeStyle.ElbowConnector
		},
		onClickNode: function() {},
		onClickEdge: function() {},
	}
	
	
	/**
	 * 绘图类
	 * @param {Node} container: 容器
	 * @param {IUMLData} data 数据
	 * @param {Void} options 配置
	 */
	function MxGraph(container, data, options) {
		this.container = container;
		this.data = data || {};
		this.options = $.extend(defaultOptions, options || {});
		
		this.__INITED__ = false;

		var ctx = this;
		this.beforeInit(function() {
			ctx.init();
			ctx.draw();
			ctx.bindEvents();
			ctx.start();
		});
	}
	
	MxGraph.prototype = {
		beforeInit: function(cb) {
			// 判断浏览器兼容性
			if (!mxClient.isBrowserSupported()){
				mxUtils.error('当前浏览器不支持！', 200, false);
			} else {
				cb();
			}
		},
		init: function() {
			
			var options = this.options;
			var nodeStyleConfig = options.nodeStyle;
			var edgeStyleConfig = options.edgeStyle;
			
			// 新建画布
			var graph = this.graph = new mxGraph(this.container);
			
			// 禁止编辑
			graph.setEnabled(false);
	
			// 设置样式
			var nodeStyle = graph.getStylesheet().getDefaultVertexStyle();
			$.each(nodeStyleConfig, function(k, v) {
				nodeStyle[mxConstants[k]] = v;
			});
			var edgeStyle = graph.getStylesheet().getDefaultEdgeStyle();
			$.each(edgeStyleConfig, function(k, v) {
				edgeStyle[mxConstants[k]] = v;
			});
			
		},
		draw: function() {
			
			var graph = this.graph;
			
			// 设置布局
			var layout = new mxParallelEdgeLayout(graph);
			var layoutMgr = new mxLayoutManager(graph);
			layoutMgr.getLayout = function(cell) {
				if(cell.getChildCount() > 0) {
					return layout;
				}
			};
			
			this.renderData(this.data);
		},
		
		renderData: function(data) {
			
			var ctx = this;
			
			var options = ctx.options;
			
			var nodeWidth = options.nodeWidth;
			var nodeHeight = options.nodeHeight;
			
			this.beforeInit(function() {
				
				var graph = ctx.graph;
				
				// 自定义渲染节点文本
				graph.convertValueToString = function(cell) {
					if(cell && cell.isVertex(cell)) {
						var value = cell.value || {};
						return value.label || '';
					}
					return '';
				}
				
				var parent = graph.getDefaultParent();
				
				// 设置数据
				graph.getModel().beginUpdate();
				try {
					var nodes = {};
					var edges = {};
					
					var nodesArray = (data.nodes || []);
					var l = nodesArray.length, i, j;
					for(i = 0; i < l; i++) {
						var l1 = (nodesArray[i].level || 0);
						for(j = i + 1; j < l; j++) {
							var l2 = (nodesArray[j].level || 0);
							if(l2 < l1) {
								var t = nodesArray[i];
								nodesArray[i] = nodesArray[j];
								nodesArray[j] = t;
							}
						}
					}
					
					var edgesArray = data.edges || [];
					
					$.each(nodesArray, function(i, d) {
						var t = nodes[d.id] = graph.insertVertex(
							parent, 
							null, 
							d, 
							(d.level || 0) * (nodeWidth + nodeHeight), 
							i * nodeHeight, 
							nodeWidth, 
							nodeHeight
						);
					});
					$.each(edgesArray, function(i, d) {
						var t = graph.insertEdge(parent, null, d, nodes[d.source], nodes[d.target]);
					});
					
				} finally {
					// 渲染数据
					graph.getModel().endUpdate();
					// 画布居中
					graph.center(true, true);
				}
			});

		},
		
		bindEvents: function() {
			
			var options = this.options;
			var onClickNode = options.onClickNode;
			var onClickEdge = options.onClickEdge;
			
			// 新增点击事件
			this.graph.addListener(mxEvent.CLICK, function(source, evt) {
				var cell = evt.getProperty('cell');
				
				if(!cell) {
					return;
				}

				if (cell.isVertex(cell)) {
					onClickNode && onClickNode(cell.value || {});
				} else if(cell.isEdge(cell)) {
					onClickEdge && onClickEdge(cell.value || {});
				} else {
					// DO NOTHING
				}
			});
		},
		
		start: function() {
			// DO NOTHING
		}
	}
	
	module.exports = MxGraph
	
});
define(function (require, exports, module) {
    var $ = require('lui/jquery');
    var modelingLang = require("lang!sys-modeling-base");
    const defaultOptions = {
        container: "container",
        height: 500,
        linkCenter: true,
        defaultSkin:0,
        // plugins: [],
        modes: {
            default: [
                'drag-canvas',
                'zoom-canvas',
            ],

        },
        defaultNode: {
            type: 'tree-node'
        },
        defaultEdge: {
            type: 'cubic-vertical',
            style: {
                stroke: '#A3B1BF',
            },
        },
        /**
         * Boolean optional default: false ;
         * 是否开启画布自适应。开启后图自动适配画布大小。
         * @type {boolean}
         */
        fitView: true,
        /**
         * Array | Number optional default: 0
         * fitView 为 true 时生效。图适应画布时，指定四周的留白。
         * - 可以是一个值, 例如：fitViewPadding: 20
         * - 也可以是一个数组，例如：fitViewPadding: [ 20, 40, 50, 20 ]
         * 当指定一个值时，四边的边距都相等，当指定数组时，数组内数值依次对应 上，右，下，左四边的边距。
         * @type {number}
         */
        fitViewPadding: 0,
        /**
         * Boolean optional default: true
         * 当图中元素更新，或视口变换时，是否自动重绘。
         * 建议在批量操作节点时关闭，以提高性能，完成批量操作后再打开，
         * 参见后面的 setAutoPaint() 方法。
         * @type {boolean}
         */
        autoPaint: true,
        /**
         * @param type
         * 可选值 compactBox 紧凑树布局
         * dendrogram 生态树布局
         * indented 缩进树布局
         * mindmap 脑图树布局
         *
         * @param direction
         *可选值：'LR' 根节点在左，往右布局
         * 'RL' 根节点在右，往左布局
         * 'TB' 根节点在上，往下布局
         * 'BT' 根节点在下，往上布局
         * 'H' 根节点在中间，水平对称布局
         * 'V'根节点在中间，垂直对称布局
         * @type {{type: string, direction: string}}
         */
        layout: {
            type: 'compactBox',
            direction: 'TB',
            getId: function getId(d) {
                return d.id;
            },
            getHeight: function getHeight() {
                return 16;
            },
            getWidth: function getWidth() {
                return 16;
            },
            getVGap: function getVGap() {
                return 20;
            },
            getHGap: function getHGap() {
                return 60;
            },
        },
    };

    /**
     * rectFill 节点颜色
     * strokeFill节点边框颜色
     *
     * @type {{"tree-node": {update: defaultNodeOptions.tree-node.update, drawShape: (function(*, *): *)}}}
     */
    var nodeIndex ={};
    const defaultNodeOptions = {
        "tree-node": {
            drawShape: function drawShape(cfg, group) {
            console.log("group",group);
                group.cfg.item._cfg.styles.stroke = "red";

                var depth = cfg.depth;
                if (nodeIndex[depth]){
                    nodeIndex[depth] +=1;
                }else {
                    nodeIndex[depth] = 1;
                }
              /*  graph.updateItem(node, {
                    // 节点的样式
                    style: {
                        stroke: 'red'
                    }
                })*/
                switch (defaultSkin){
                    //稳重
                    case "0" :
                        var radiusFill = 4;
                        var rectFill = cfg.depth == 0 ? '#34435B' : cfg.depth == 1 ? '#A0ABBD' : '#EEF4F8';
                        var strokeFill = cfg.depth == 0 ? '#34435B' : cfg.depth == 1 ? '#A0ABBD' : '#EEF4F8';
                        var textFill = cfg.depth == 0 ? '#FFFFFF' : cfg.depth == 1 ? '#FFFFFF' : '#34435B';
                        break;
                    //简约
                    case "1" :
                        var radiusFill = 12;
                        var rectFill = cfg.depth == 0 ? '#FFFFFF' : cfg.depth == 1 ? '#FFFFFF' : '#FFFFFF';
                        var strokeFill = cfg.depth == 0 ? '#333333' : cfg.depth == 1 ? '#333333' : 'transparent';
                        var textFill = cfg.depth == 0 ? '#333333' : cfg.depth == 1 ? '#333333' : '#333333';
                        break;
                    //缤纷
                    case "2" :
                        var index =  nodeIndex[depth];
                        var colorIndex = index%5;
                        var radiusFill = cfg.depth == 0 ? 4:12;
                        var rectFill = cfg.depth == 0 ? '#4285F4' : cfg.depth == 1 ? '#FFFFFF' : '#FFFFFF';
                        var strokeFill ;
                        if (depth>0){
                            switch (colorIndex){
                                case 0 :
                                    strokeFill = '#7BC3CF';
                                    break;
                                case 1 :
                                    strokeFill = '#A9E5AE';
                                    break;
                                case 2 :
                                    strokeFill = '#EF9595';
                                    break;
                                case 3 :
                                    strokeFill = '#AC9DE6';
                                    break;
                                case 4 :
                                    strokeFill = '#FCB189';
                                    break;
                            }
                        }else {
                            strokeFill = '#4285F4';
                        }
                        var textFill = cfg.depth == 0 ? '#FFFFFF' : cfg.depth == 1 ? '#2A304A' : '#34435B';
                        break;
                    //蓝天
                    case "3" :
                        var radiusFill = 4;
                        var rectFill = cfg.depth == 0 ? '#FFFFFFFF' : cfg.depth == 1 ? '#FFFFFFFF' : '#F5FCFF';
                        var strokeFill = cfg.depth == 0 ? '#9ADDFB' : cfg.depth == 1 ? '#9ADDFB' : 'transparent';
                        var textFill = cfg.depth == 0 ? '#2A304A' : cfg.depth == 1 ? '#2A304A' : '#2A304A';
                        break;
                    //深海
                    case "4" :
                        var radiusFill = cfg.depth == 0 ? 12:cfg.depth == 1?12:4;
                        var rectFill = cfg.depth == 0 ? '#E1E5ED' : cfg.depth == 1 ? '#C9D4E7' : '#21273E';
                        var strokeFill = cfg.depth == 0 ? '#E1E5ED' : cfg.depth == 1 ? '#C9D4E7': 'transparent';
                        var textFill = cfg.depth == 0 ? '#2A304A' : cfg.depth == 1 ? '#2A304A' : '#FFFFFF';
                        break;
                    //暗影
                    case "5" :
                        var radiusFill = cfg.depth == 0 ? 4:12;
                        var rectFill = cfg.depth == 0 ? '#1B1B1D' : cfg.depth == 1 ? '#3A3A3C' : '#59595C';
                        var strokeFill = cfg.depth == 0 ? '#1B1B1D' : cfg.depth == 1 ? '#3A3A3C' : '#59595C';
                        var textFill = '#FFFFFF';
                        break;
                }
                const rect = group.addShape('rect', {
                    attrs: {
                        fill: rectFill,
                        stroke: strokeFill,
                        x: 0,
                        y: 0,
                        cursor: 'pointer',
                        radius: radiusFill,
                        width: 1,
                        height: 1,
                    },
                    style: {
                        radius: 10
                    },
                    name: 'rect-shape',
                });
                var content = cfg.name.replace(/(.{19})/g, '$1\n');
                if (!content) {
                    content = modelingLang['modelingMindMap.nnnamed.node'];
                }
                const text = group.addShape('text', {
                    attrs: {
                        text: content.length > 8 ? content.substr(0, 8) + '...' : content,
                        x: 0,
                        y: 0,
                        cursor: 'pointer',
                        textAlign: 'left',
                        textBaseline: 'middle',
                        fill: textFill,
                        fontSize: 12
                    },
                    name: 'text-shape',
                });
                const bbox = text.getBBox();
                const hasChildren = cfg.children && cfg.children.length > 0;
                rect.attr({
                    x: -bbox.width / 2 - 4,
                    y: -bbox.height / 2 - 6,
                    width: bbox.width + (hasChildren ? 36 : 22),
                    height: bbox.height + 12,
                });

                text.attr({
                    x: -bbox.width / 2 + 4,
                    y: 0 ,
                })
                if (hasChildren) {
                    group.addShape('rect', {
                        attrs: {
                            x: bbox.width / 2 + 12,
                            y: -bbox.height / 2 - 1,
                            width: 12,
                            height: 12,
                            stroke: textFill,
                            cursor: 'pointer',
                            radius: [2, 4],
                        },
                        nodeId: cfg.id,
                        name: 'collapse-rect'
                    });

                    // collpase text
                    group.addShape('text', {
                        attrs: {
                            x: bbox.width / 2 + 18,
                            y: 0,
                            textAlign: 'center',
                            textBaseline: 'middle',
                            text: cfg.collapsed ? '+' : '-',
                            fontSize: 16,
                            cursor: 'pointer',
                            fontWeight: 'lighter',
                            stroke: textFill,
                            fontWeight: 200,
                            lineHeight: bbox.height
                        },
                        name: 'collapse-icon',
                        nodeId: cfg.id,
                    });

                }
                console.log("rect",rect)
                return rect;
            },
            update: (cfg, item) => {
                const group = item.getContainer();
                const icon = group.find((e) => e.get('name') === 'collapse-icon');
                icon.attr('text', cfg.collapsed ? '+' : '-');
            },
        },


        'file-node': {
            draw: function draw(cfg, group) {
                const keyShape = group.addShape('rect', {
                    attrs: {
                        x: 10,
                        y: -12,
                        fill: '#fff',
                        stroke: null,
                    },
                });
                let isLeaf = false;
                if (cfg.collapsed) {
                    group.addShape('marker', {
                        attrs: {
                            symbol: 'triangle',
                            x: 4,
                            y: -2,
                            r: 4,
                            fill: '#666',
                        },
                        name: 'marker-shape',
                    });
                } else if (cfg.children && cfg.children.length > 0) {

                } else {
                    isLeaf = true;
                }
                const shape = group.addShape('text', {
                    attrs: {
                        x: 15,
                        y: 4,
                        text: cfg.name,
                        fill: '#666',
                        fontSize: 16,
                        textAlign: 'left',
                        fontFamily:
                            typeof window !== 'undefined'
                                ? window.getComputedStyle(document.body, null).getPropertyValue('font-family') ||
                                'Arial, sans-serif'
                                : 'Arial, sans-serif',
                    },
                    name: 'text-shape',
                });
                const bbox = shape.getBBox();
                let backRectW = bbox.width;
                let backRectX = keyShape.attr('x');
                if (!isLeaf) {
                    backRectW += 8;
                    backRectX -= 15;
                }
                keyShape.attr({
                    width: backRectW,
                    height: bbox.height + 4,
                    //x: backRectX,
                });
                return keyShape;
            },
        }

    }


    /**
     * 绘图类
     * @param {Node} container: 容器
     * @param {IUMLData} data 数据
     * @param {Void} options 配置
     */
    function G6Graph(container, data, options) {
        if (!container) {
            console.error("G6Graph 初始化失败 container 为空 ");
            return;
        }
        this.container = container;
        this.data = data || {};
        this.nodeStyles = {};
        this.options = $.extend(defaultOptions, options || {});
        this.options.container = container;
        this.graphConstruction;
        this.__INITED__ = false;
        //初始化参数
        if (!this.options.width) {
            this.options.width = container.scrollWidth;
        }

    }

    /**
     *
     * @type {{registerNode: G6Graph.registerNode, TreeGraph: G6Graph.TreeGraph, updateItem: G6Graph.updateItem, pushRegisterNode: (function(*=, *=, *=): G6Graph), changeSize: G6Graph.changeSize, registerPlugins: G6Graph.registerPlugins}}
     */
    G6Graph.prototype = {
        rebuilid: function (data, options) {
            this.container = container;
            this.data = data || {};
            this.options = $.extend(defaultOptions, options || {});
            //初始化参数
            if (!this.options.width) {
                this.options.width = container.scrollWidth;
            }
        },
        /**
         *
         * @param data
         * @constructor
         */
        TreeGraph: function (data, ops) {
            this.data = data || this.data
            //内置节点注册
            var nodeType = this.options.defaultNode.type;
            if (!this.nodeStyles[nodeType] && defaultNodeOptions[nodeType]) {
                //未注册的
                this.registerNode(nodeType, defaultNodeOptions[nodeType])
            }
            var direction = this.options.layout.direction;
            if (!(ops && ops.defaultEdge && ops.defaultEdge.type)) {
                this.options.defaultEdge.type = direction == "TB" ? 'cubic-vertical' : 'cubic-horizontal';
            }
            var skin = this.getSkinColor();
            this.options.defaultEdge.style.stroke =skin.stroke;
            // 新建画布
            this.graph = new G6.TreeGraph(this.options);
            this.graphConstruction = "TreeGraph";
            this.graph.data(this.data);
            this.graph.render();
            this.graph.fitCenter();
            $("#container canvas").css("background-color",skin.bgColor);
        },
        FileGraph: function (data, ops) {
            this.data = data || this.data
            //内置节点注册
            this.registerNode("file-node",defaultNodeOptions["file-node"])
            //缩进线注册
            G6.registerEdge(
                'step-line',
                {
                    getControlPoints: function getControlPoints(cfg) {
                        const startPoint = cfg.startPoint;
                        const endPoint = cfg.endPoint;
                        return [
                            startPoint,
                            {
                                x: startPoint.x,
                                y: endPoint.y,
                            },
                            endPoint,
                        ];
                    },
                },
                'polyline',
            );
            // 新建画布
            var self = this;
            this.graph = new G6.TreeGraph(this.options);

            this.graphConstruction = "FileGraph";
            this.graph.node((node) => {
                return {
                    type: 'file-node',
                    label: node.name,
                };
            });
            this.graph.edge(() => {
                return {
                    type: 'step-line',
                };
            });
            this.graph.data(this.data);

            this.graph.render();
            this.graph.fitCenter();

        },
        changeLayoutDirection: function (direction, type) {
            if (type) {
                this.options.layout.type = type;
            }
            if (direction) {
                this.options.layout.type = direction;
            }
            if (!this.graph.get('destroyed')) {
                this.graph.destroy();
            }
            if (this.graphConstruction) {
                this[this.graphConstruction]();
            }

        },
        /**
         *
         * @param nodeName
         * @param type
         * @param options
         */
        registerPlugins: function (type, ops) {
            if (!this.options.plugins) {
                this.options.plugins = [];
            }
            this.options.plugins.push(new G6[type](ops))
        },
        /**
         *
         * @param nodeName
         * @param options
         * @param extendedNodeName
         */
        registerNode: function (nodeName, ops, extendedNodeName) {
            this.nodeStyles[nodeName] = G6.registerNode(nodeName, ops, extendedNodeName)
        },
        /**
         *
         * @param nodeName
         * @param options
         * @param extendedNodeName
         * @returns {G6Graph}
         */
        pushRegisterNode: function (nodeName, ops, extendedNodeName) {
            this.nodeStyles[nodeName] = G6.registerNode(nodeName, ops, extendedNodeName)
            this.options.plugins.push(nodeName)
            return this;
        },
        /**
         *
         * @param item
         * @param cfg
         */
        updateItem: function (item, cfg) {
            this.graph.updateItem(item, cfg);
        },
        /**
         *
         * @param width
         * @param height
         */
        changeSize: function (width, height) {
            if (!width) {

                width = this.container.clientWidth;
            }
            if (!height) {
                height = this.container.clientHeight || 500;
            }
            if (this.graph) {
                this.graph.changeSize(width, height);
                this.graph.fitCenter();
                if (this.graph.get("layout").direction == "LR") {
                    this.graph.moveTo(60, height / 3)
                } else {
                    this.graph.moveTo(width / 3, 60)
                }

            }

        },
        destroy: function () {
            this.graph.destroy();
        },
        switchLayout: function (direction) {
            var ly = this.graph.get("layout");
            ly.direction = direction;
            this.graph.set("layout", ly);
            var edgeCfg = this.options.defaultEdge;
            edgeCfg.type = direction == "TB" ? 'cubic-vertical' : 'cubic-horizontal';
            this.graph.edge((edge) => {
                edgeCfg.id = edge.id;
                return edgeCfg;
            });
            this.graph.render();
            this.changeSize();
        },
        changeSkin:function (value){
            defaultSkin = ""+value+"";
            var edgeCfg = this.options.defaultEdge;
            var skin = this.getSkinColor();
            edgeCfg.style.stroke = skin.stroke;
            this.graph.edge((edge) => {
                edgeCfg.id = edge.id;
                return edgeCfg;
            });
            $("#container canvas").css("background-color",skin.bgColor);
        /*    this.graph.data(this.data);*/
            this.graph.render();
            this.changeSize();
            /*
        this.graph.updateItem(node, {
            // 节点的样式
            style: {
                stroke: 'blue'
            }
        })*/
        },
        moveTo: function (x, y) {
            this.graph.moveTo(x, y)
        },
        /**
         *
         * @param d
         */
        read: function (d) {
            this.data = d || this.data
            this.graph.read(this.data);
            this.changeSize();
        },
        getSkinColor:function (){
            var skin={};
            var stroke;
            var bgColor;
            var edgeCfg = this.options.defaultEdge;
            switch (defaultSkin){
                //稳重
                case "0" :
                    stroke = "#A0ABBD";
                    bgColor = "#FFFFFF";
                    break;
                //简约
                case "1" :
                    stroke = "#34435B";
                    bgColor = "#FFFFFF";
                    break;
                //缤纷
                case "2" :
                    stroke = "#4285F4";
                    bgColor = "#FFFFFF";
                    break;
                //蓝天
                case "3" :
                    stroke = "#9ADDFB";
                    bgColor = "#F5FCFF";
                    break;
                //深海
                case "4" :
                    stroke = "#DDDDDD";
                    bgColor = "#21273E";
                    break;
                //暗影
                case "5" :
                    stroke = "#59595C";
                    bgColor = "#000000";
                    break;
            }
            skin.stroke = stroke;
            skin.bgColor = bgColor;
            return skin
        }

    };

    module.exports = G6Graph

})
;

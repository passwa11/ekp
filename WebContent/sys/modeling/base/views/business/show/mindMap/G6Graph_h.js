


const data = {
    id: 'root_id',
    name: 'root',
    children: [
        {
            id: 'c1_id',
            name: 'c1',
            children: [
                {
                    id: 'c1-1_id',
                    name: 'c1-1',
                },
                {
                    id: 'c1-2_id',
                    name: 'c1-2',
                    children: [
                        {
                            id: 'c1-2-1_id',
                            name: 'c1-2-1',
                        },
                        {
                            id: 'c1-2-2_id',
                            name: 'c1-2-2',
                        },
                    ],
                },
            ],
        },
        {
            id: 'c2_id',
            name: 'c2',
        },
        {
            id: 'c3_id',
            name: 'c3',
            children: [
                {
                    id: 'c3-1_id',
                    name: 'c3-1',
                },
                {
                    id: 'c3-2_id',
                    name: 'c3-2',
                    children: [
                        {
                            id: 'c3-2-1_id',
                            name: 'c3-2-1wxxxxxxxxxxxxxxxx',
                        },
                        {
                            id: 'c3-2-2_id',
                            name: 'c3-2-2',
                        },
                        {
                            id: 'c3-2-3_id',
                            name: 'c3-2-3',
                        },
                    ],
                },
                {
                    id: 'c3-3_id',
                    name: 'c3-3',
                },
            ],
        },
    ],
};

G6.registerNode(
    'tree-node',
    {
        drawShape: function drawShape(cfg, group) {
            var rectFill = cfg.depth == 0 ? '#34435B' : cfg.depth == 1 ? '#A0ABBD' : '#ffffff';
            const rect = group.addShape('rect', {
                attrs: {
                    fill: rectFill,
                    stroke: '#666',
                    x: 0,
                    y: 0,
                    width: 1,
                    height: 1
                },
                name: 'rect-shape',
            });
            const content = cfg.name.replace(/(.{19})/g, '$1\n');
            var textFill = cfg.depth <= 1 ? '#ffffff' : '#34435B';
            const text = group.addShape('text', {
                attrs: {
                    text: content,
                    x: 0,
                    y: 0,
                    textAlign: 'left',
                    textBaseline: 'middle',
                    fill: textFill,
                    fontSize:14
                },
                name: 'text-shape',
            });
            const bbox = text.getBBox();
            const hasChildren = cfg.children && cfg.children.length > 0;
            rect.attr({
                x: -bbox.width / 2 - 4,
                y: -bbox.height / 2 - 3,
                width: bbox.width + (hasChildren ? 26 : 12),
                height: bbox.height + 6,
            });
            text.attr({
                x: -bbox.width / 2,
                y: 0
            })
            if (hasChildren) {
                group.addShape('marker', {
                    attrs: {
                        x: bbox.width / 2 + 12,
                        y: 0,
                        r: 6,
                        symbol: cfg.collapsed ? G6.Marker.expand : G6.Marker.collapse,
                        stroke: '#666',
                        lineWidth: 2,
                    },
                    name: 'collapse-icon',
                });
            }
            return rect;
        },
        update: (cfg, item) => {
            const group = item.getContainer();
            const icon = group.find((e) => e.get('name') === 'collapse-icon');
            icon.attr('symbol', cfg.collapsed ? G6.Marker.expand : G6.Marker.collapse);
        },
    },
    'single-node',
);


const container = document.getElementById('container');
const width = container.scrollWidth;
const height = container.scrollHeight || 500;

const minimap = new G6.Minimap({
    size: [150, 100],
});
const graph = new G6.TreeGraph({
    container: 'container',
    width,
    height,
    linkCenter: true,
    plugins: [minimap],
    modes: {
        default: [
            {
                type: 'collapse-expand',
                onChange: function onChange(item, collapsed) {
                    const data = item.get('model');
                    graph.updateItem(item, {
                        collapsed,
                    });
                    data.collapsed = collapsed;
                    return true;
                },
            },
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
            return 40;
        },
        getHGap: function getHGap() {
            return 70;
        },
    },
});

graph.data(data);
graph.render();
graph.fitView();

// if (typeof window !== 'undefined')
//     window.onresize = () => {
//         if (!graph || graph.get('destroyed')) return;
//         if (!container || !container.scrollWidth || !container.scrollHeight) return;
//         graph.changeSize(container.scrollWidth, container.scrollHeight);
//     };

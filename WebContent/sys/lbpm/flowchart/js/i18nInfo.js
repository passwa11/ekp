(function () {
    let url = Com_Parameter.ContextPath + 'sys/lbpmservice/support/lbpmHistoryWorkitemAction.do?method=getNodeHandlerI18nInfo';
    let list = ['info.nodeAuth', 'info.nodeHandler', 'info.nodeHandler.formula',
            'info.nodeHandler.name', 'info.nodeHandler.arrivalTime', 'info.nodeHandler.processingStatus',
            'info.nodeHandler.processingTime', 'info.nodeType.multiplayer', 'info.nodeType.multiplayer.serial',
            'info.nodeType.multiplayer.parallel', 'info.nodeType.multiplayer.jointReview', 'info.nodeType.serial',
            'info.nodeType.parallel', 'info.nodeType.jointReview', 'info.result.unArrive', 'info.result.done',
            'info.result.todo', 'info.result.read', 'info.result.unread'];
    $.get(url, $.param({keys: list}, true), function (data) {
            localStorage.setItem('nodeI18n', data);
        }).fail(function (data) {
    });
})();
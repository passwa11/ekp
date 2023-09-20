
var formOption = {
    formName: 'fsscExpenseTempForm',
    modelName: 'com.landray.kmss.fssc.expense.model.FsscExpenseTemp',
    templateName: '',
    subjectField: 'fdMainId',
    mode: '',
	dialogs:{
		eop_basedata_expense_item_selectExpenseItem: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',
            sourceUrl: '/eop/basedata/eop_basedata_expense_item/eopBasedataExpenseItemData.do?method=fdParent'
        },
        eop_basedata_tax_rate_getTaxRate: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataTaxRate',
            sourceUrl: '/eop/basedata/eop_basedata_tax_rate/eopBasedataTaxRateData.do?method=getTaxRate'
        },
        fssc_ledger_fdInvoice: {
        	modelName: 'com.landray.kmss.fssc.ledger.model.FsscLedgerInvoice',
        	sourceUrl: '/fssc/ledger/fssc_ledger_invoice/fsscLedgerInvoiceData.do?method=fdInvoice'
        }
	},
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    detailTables: [],
    dataType: {},
    detailNotNullProp: {
        fdInvoiceListTemp_Form: {
            text: [],
            textarea: []
        }
    }
};

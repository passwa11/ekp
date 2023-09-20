
var formOption = {
    formName: 'fsscFeeExpenseItemForm',
    modelName: 'com.landray.kmss.fssc.fee.model.FsscFeeExpenseItem'

    ,
    dialogs: {
        fssc_fee_template_getTemplate: {
            modelName: 'com.landray.kmss.fssc.fee.model.FsscFeeTemplate',
            sourceUrl: '/fssc/fee/fssc_fee_template/fsscFeeTemplateData.do?method=getTemplate'
        },

        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany'
        },

        eop_basedata_expense_item_fdParent: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem',
            sourceUrl: '/eop/basedata/eop_basedata_expense_item/eopBasedataExpenseItemData.do?method=fdParent&type=all'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};

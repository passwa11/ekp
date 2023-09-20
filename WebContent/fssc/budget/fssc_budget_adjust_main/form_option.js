var formOption = {
    formName: 'fsscBudgetAdjustMainForm',
    modelName: 'com.landray.kmss.fssc.budget.model.FsscBudgetAdjustMain',
    templateName: 'com.landray.kmss.fssc.budget.model.FsscBudgetAdjustCategory',
    subjectField: 'docSubject',
    mode: 'main_scategory'

    ,
    dialogs: {
        eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=getCompanyByPerson&type=byScheme'
        },

        eop_basedata_currency_fdCurrency: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCurrency',
            sourceUrl: '/eop/basedata/eop_basedata_currency/eopBasedataCurrencyData.do?method=fdCurrency'
        },

        eop_basedata_budget_scheme_fdCategory: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme',
            sourceUrl: '/eop/basedata/eop_basedata_budget_scheme/eopBasedataBudgetSchemeData.do?method=fdCategory'
        },

        eop_basedata_cost_center_fdParent: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
            sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=fdParent'
        },

        eop_basedata_cost_center_selectCostCenter: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
            sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=selectCostCenter'
        },

        eop_basedata_budget_item_com_fdBudgetItem: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem',
            sourceUrl: '/eop/basedata/eop_basedata_budget_item/eopBasedataBudgetItemData.do?method=fdBudgetItem'
        },

        eop_basedata_project_project: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataProject',
            sourceUrl: '/eop/basedata/eop_basedata_project/eopBasedataProjectData.do?method=project&type=budget'
        },

        eop_basedata_inner_order_fdInnerOrder: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataInnerOrder',
            sourceUrl: '/eop/basedata/eop_basedata_inner_order/eopBasedataInnerOrderData.do?method=fdInnerOrder'
        },

        eop_basedata_wbs_fdWbs: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataWbs',
            sourceUrl: '/eop/basedata/eop_basedata_wbs/eopBasedataWbsData.do?method=fdWbs'
        },
        fssc_base_fdBudgetScheme: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataBudgetScheme',
            sourceUrl: '/eop/basedata/eop_basedata_budget_scheme/eopBasedataBudgetSchemeData.do?method=fdCategory'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};

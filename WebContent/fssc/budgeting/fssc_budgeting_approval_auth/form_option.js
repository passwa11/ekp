
var formOption = {
    formName: 'fsscBudgetingApprovalAuthForm',
    modelName: 'com.landray.kmss.fssc.budgeting.model.FsscBudgetingApprovalAuth'

    ,
    dialogs: {
    	eop_basedata_company_fdCompany: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCompany',
            sourceUrl: '/eop/basedata/eop_basedata_company/eopBasedataCompanyData.do?method=fdCompany'
        },
    	eop_basedata_cost_center_selectCostCenter: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataCostCenter',
            sourceUrl: '/eop/basedata/eop_basedata_cost_center/eopBasedataCostCenterData.do?method=selectAllCostCenter'
        },

        eop_basedata_budget_item_fdBudgetItem: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataBudgetItem',
            sourceUrl: '/eop/basedata/eop_basedata_budget_item/eopBasedataBudgetItemData.do?method=fdBudgetItem&query=all'
        },

        eop_basedata_project_project: {
            modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataProject',
            sourceUrl: '/eop/basedata/eop_basedata_project/eopBasedataProjectData.do?method=selectAllProject'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    dataType: {}
};

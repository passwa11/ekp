
var formOption = {
    formName: 'hrOrganizationConPostForm',
    modelName: 'com.landray.kmss.hr.organization.model.HrOrganizationConPost',
    templateName: '',
    subjectField: 'fdType',
    mode: ''

    ,
    dialogs: {
    	hr_organization_staffing_level: {
            modelName: 'com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel',
            sourceUrl: '/hr/organization/hr_organization_staffing_level/hrOrganizationStaffingLevel.do?method=dialogData'
        }
    },
    dialogLinks: [],
    valueLinks: [],
    attrLinks: [],
    optionLinks: [],
    linkValidates: [],
    detailTables: [],
    dataType: {},
    detailNotNullProp: {}
};
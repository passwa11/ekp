package com.landray.kmss.hr.staff.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.service.IHrOrganizationRankService;
import com.landray.kmss.hr.staff.forms.HrStaffMoveRecordForm;
import com.landray.kmss.hr.staff.model.HrStaffMoveRecord;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.hr.staff.report.SysAttendPersonReportService;
import com.landray.kmss.hr.staff.service.IHrStaffMoveRecordService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.metadata.service.ISysMetadataService;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.profile.service.ISysOrgImportService;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 异动信息 服务实现
 */
public class HrStaffMoveRecordServiceImp extends HrStaffImportServiceImp implements IHrStaffMoveRecordService {
    private static final Logger logger = LoggerFactory.getLogger(HrStaffMoveRecordServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrStaffMoveRecord) {
            HrStaffMoveRecord hrStaffMoveRecord = (HrStaffMoveRecord) model;
        }
        return model;
    }

    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrStaffMoveRecord hrStaffMoveRecord = (HrStaffMoveRecord) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    private IHrStaffPersonInfoService hrStaffPersonInfoService;

    @Override
    public IHrStaffPersonInfoService getHrStaffPersonInfoService() {
        if (hrStaffPersonInfoService == null) {
            hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil.getBean("hrStaffPersonInfoService");
        }
        return hrStaffPersonInfoService;
    }

    private ISysOrgPostService sysOrgPostService;

    public ISysOrgPostService getSysOrgPostService() {
        if (sysOrgPostService == null) {
            sysOrgPostService = (ISysOrgPostService) SpringBeanUtil.getBean("sysOrgPostService");
        }
        return sysOrgPostService;
    }

    private ISysOrgPersonService sysOrgPersonService;
    public ISysOrgPersonService getSysOrgPersonService() {
        if (sysOrgPersonService == null) {
        	sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
        }
        return sysOrgPersonService;
    }

    private ISysMetadataService sysMetadataService;

    public void setSysMetadataService(ISysMetadataService sysMetadataService) {
        this.sysMetadataService = sysMetadataService;
    }

    @Override
    public String[] getImportFields() {
        return null;
    }

    private ISysOrgImportService sysOrgImportService;

    private ISysOrgImportService getSysOrgImportService() {
        if (sysOrgImportService == null) {
            sysOrgImportService = (ISysOrgImportService) SpringBeanUtil.getBean("sysOrgImportService");
        }
        return sysOrgImportService;
    }

    private ISysOrgElementService sysOrgElementService;

    private ISysOrgElementService getSysOrgElementService() {
        if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }
    private IHrOrganizationElementService hrOrganizationElementService;
    private IHrOrganizationElementService getHrOrganizationElementService() {
        if (hrOrganizationElementService == null) {
        	hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil.getBean("hrOrganizationElementService");
        }
        return hrOrganizationElementService;
    }
    private IHrStaffTrackRecordService hrStaffTrackRecordService;

    private IHrStaffTrackRecordService getHrStaffTrackRecordService() {
        if (hrStaffTrackRecordService == null) {
            hrStaffTrackRecordService = (IHrStaffTrackRecordService) SpringBeanUtil.getBean("hrStaffTrackRecordService");
        }
        return hrStaffTrackRecordService;
    }


    private IHrOrganizationRankService hrOrganizationRankService;

    private IHrOrganizationRankService getHrOrganizationRankService() {
        if (hrOrganizationRankService == null) {
            hrOrganizationRankService = (IHrOrganizationRankService) SpringBeanUtil.getBean("hrOrganizationRankService");
        }
        return hrOrganizationRankService;
    }

    @Override
    public KmssMessage saveImportData(HrStaffMoveRecordForm moveRecordForm) throws Exception {
        String[] fields = getFields();
        Workbook wb = null;
        Sheet sheet;
        InputStream inputStream = null;
        int count = 0;
        KmssMessages messages;
        StringBuffer errorMsg = new StringBuffer();
        SysOrgElement fdParent1=null;
        SysOrgElement fdParent2=null;
        try {
            inputStream = moveRecordForm.getFile().getInputStream();
            //抽象类创建Workbook，适合excel 2003和2007以上
            wb = WorkbookFactory.create(inputStream);
            sheet = wb.getSheetAt(0);
            // 一份标准的模板，最少包含2行，第一行是字段名称，导入的数据应该是从第二行开始
            int rowNum = sheet.getLastRowNum();
            if (rowNum < 1) {
                throw new RuntimeException(ResourceUtil.getString("hrStaff.import.empty", "hr-staff"));
            }
            if (sheet.getRow(0).getLastCellNum() != 20) {
                System.out.println(sheet.getRow(0).getLastCellNum());
                throw new RuntimeException(ResourceUtil.getString("hrStaff.import.errFile", "hr-staff"));
            }

            // 从第二行开始取数据
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                HrStaffMoveRecord track = new HrStaffMoveRecord();
                messages = new KmssMessages();
                Row row = sheet.getRow(i);
                // 跳过空行
                if (row == null) {
                    continue;
                }
                // 获取列数
                int cellNum = row.getLastCellNum();
                HrStaffPersonInfo personInfo = null;
                HrStaffPersonInfo personInfo1 = new HrStaffPersonInfo();
                // 是否是新建
                boolean isNew = true;
                // 是否有账号或工号
                boolean hasLoginNameOrStaffNo = false;
                for (int j = 1; j < cellNum; j++) {
                    String value = ImportUtil.getCellValue(row.getCell(j));
                    if (StringUtil.isNull(value)) {
                        continue;
                    }
                    String fdNo = null;
                    if (j == 1) {
                        fdNo = value;
                        personInfo = getHrStaffPersonInfoService().findPersonInfoByStaffNo(fdNo);
                        if (personInfo != null)
                        	track.setFdPersonInfo(personInfo);
                            hasLoginNameOrStaffNo = true;
                        BeanUtils.setProperty(track, "fdStaffNumber", value);
                    }
                    if (j == 2 && personInfo == null) {
                        hasLoginNameOrStaffNo = true;
                        personInfo = getHrStaffPersonInfoService().findPersonInfoByLoginNameAndNo(value, fdNo);
                    	track.setFdPersonInfo(personInfo);
                        BeanUtils.setProperty(track, "fdStaffName", value);
                    }


                    if (j == 2 && !hasLoginNameOrStaffNo) {
                        messages.addError(new KmssMessage(ResourceUtil.getString("hrStaffTrackRecord.error.person", "hr.staff")));
                        throw new KmssRuntimeException(new KmssMessage(ResourceUtil.getString("hrStaffTrackRecord.error.person", "hr.staff")));
                    } else {
                        //是否考察
                        if (j == 3) {
                            BeanUtils.setProperty(track, "fdIsExplore", value);
                        }
                        //见习开始日期,见习结束日期
                        if (j == 4 || j == 5) {

                            Date date = DateUtil.convertStringToDate(value, "yyyy-MM-dd");
                            if (date != null) {
                                BeanUtils.setProperty(track, fields[j], date);
                            }
                        }
                        //异动类型
                        if (j == 6) {
                            BeanUtils.setProperty(track, "fdMoveType", value);
                        }
                        if(j == 7 || j == 9 || j == 8){
                            SysOrgElement fdParent = findSysOrgElementByNames(value,personInfo1);
                        	if (j == 7)
                                personInfo1.setFdFirstLevelDepartment(fdParent);
                            if (j == 8)
                                personInfo1.setFdSecondLevelDepartment(fdParent);
                            if (j == 9)
                                personInfo1.setFdThirdLevelDepartment(fdParent);
                            if (null != fdParent) {
                            	fdParent2=fdParent;
                                BeanUtils.setProperty(track, fields[j], fdParent.getFdName());
                            } else {
                                messages.addError(new KmssMessage(value+ResourceUtil.getString("hrStaffMoveRecord.error.dept", "hr.staff")));
                            }
                        }
                        //现部门处理
                        if (j == 15 || j == 14 || j == 13 ) {
                            SysOrgElement fdParent = findSysOrgElementByNames(value,personInfo);
                            if (j == 13)
                                personInfo.setFdFirstLevelDepartment(fdParent);
                            if (j == 14)
                                personInfo.setFdSecondLevelDepartment(fdParent);
                            if (j == 15)
                                personInfo.setFdThirdLevelDepartment(fdParent);
                            if (null != fdParent) {
                            	fdParent1=fdParent;
                                BeanUtils.setProperty(track, fields[j], fdParent.getFdName());
                            } else {
                                messages.addError(new KmssMessage(value+ResourceUtil.getString("hrStaffMoveRecord.error.dept", "hr.staff")));
                            }
                        }
                        
                        //职级处理
                        if (j == 16 || j == 10) {
                            HrOrganizationRank rank = getHrOrganizationRankService().findByName(value);
                            if (j == 16)
                                personInfo.setFdOrgRank(rank);
                            if (null != rank) {
                                BeanUtils.setProperty(track, fields[j], rank.getFdName());
                            } else {
                                messages.addError(new KmssMessage(ResourceUtil.getString("hrStaffMoveRecord.error.rank", "hr.staff")+value));
                            }
                        }
                        if(j==11){
                        	 List fdPosts = findByNames(value.split(";"),fdParent2);
                             if (!ArrayUtil.isEmpty(fdPosts)) {
                                 BeanUtils.setProperty(track, fields[j], fdPosts);
                             } else {
                                 messages.addError(new KmssMessage(fdParent2.getFdName()+ResourceUtil.getString("hrStaffMoveRecord.error.post", "hr.staff")));
                             }
                        }
                        //岗位处理
                        if (j == 17) {
                            List fdPosts = findByNames(value.split(";"),fdParent1);
                            if (j == 17) {
                                personInfo.setFdPosts(fdPosts);
                            }
                            if (!ArrayUtil.isEmpty(fdPosts)) {
                                BeanUtils.setProperty(track, fields[j], fdPosts);
                            } else {
                                messages.addError(new KmssMessage(fdParent1.getFdName()+ResourceUtil.getString("hrStaffMoveRecord.error.post", "hr.staff")));
                            }
                        }
                        //直接上级处理
                        if (j == 18 || j == 12) {
                            SysOrgElement fdLeader = findSysOrgElementByNames1(value);
                            if (j == 18)
                                personInfo.setFdReportLeader(fdLeader);
                            if (null != fdLeader) {
                                BeanUtils.setProperty(track, fields[j], fdLeader);
                            } else {
                                messages.addError(new KmssMessage(ResourceUtil.getString("hrStaffMoveRecord.error.noleader", "hr.staff")));
                            }
                        }
                        //异动时间
                        if (j == 19) {
                            Date fdMoveDate = DateUtil.convertStringToDate(value, "yyyy-MM-dd");
                            BeanUtils.setProperty(track, fields[j], fdMoveDate);
                        }
                    }

                }
                track.setFdPersonInfo(personInfo);
                track.setFdAfterDept(fdParent1);
                track.setFdBeforeDept(fdParent2);
                // 如果有错误，就不进行导入
                if (!messages.hasError()) {
                    this.add(track);
                    count++;
                } else {
                    errorMsg.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i));
                    // 解析错误信息
                    for (KmssMessage message : messages.getMessages()) {
                        errorMsg.append(message.getMessageKey());
                    }
                    errorMsg.append("<br>");
                }
            }
            KmssMessage message;
            if (errorMsg.length() > 0) {
                errorMsg.insert(0, ResourceUtil.getString(
                        "hrStaff.import.portion.failed", "hr-staff", null, count)
                        + "<br>");
                message = new KmssMessage(errorMsg.toString());
                message.setMessageType(KmssMessage.MESSAGE_ERROR);
            } else {
                message = new KmssMessage(ResourceUtil.getString("hrStaff.import.success", "hr-staff", null, count));
                message.setMessageType(KmssMessage.MESSAGE_COMMON);
            }
            return message;
        } catch (Exception e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(wb);
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 异动需要导入字段
     *
     * @return
     */
    private String[] getFields() {
        return new String[]{
                "fdIndex", "fdStaffNumber", "fdStaffName", "fdIsExplore", "fdInternStartDate", "fdInternEndDate",
                "fdMoveType", "fdBeforeFirstDeptName", "fdBeforeSecondDeptName", "fdBeforeThirdDeptName", "fdBeforeRank",
                "fdBeforePosts", "fdBeforeLeader", "fdAfterFirstDeptName", "fdAfterSecondDeptName", "fdAfterThirdDeptName",
                "fdAfterRank", "fdAfterPosts", "fdAfterLeader", "fdMoveDate"
        };
    }

    /**
     * 重写add方法，新增异动信息后，修改员工个人信息，及任职记录
     *
     * @param modelObj model对象
     * @return
     * @throws Exception
     */
    @Override
    public String add(IBaseModel modelObj) throws Exception {
        HrStaffMoveRecord hrStaffMoveRecord = (HrStaffMoveRecord) modelObj;
        HrStaffPersonInfo personInfo = hrStaffMoveRecord.getFdPersonInfo();
        if (null != personInfo) {
            //设置原部门，职级，岗位等信息
            setPersonBeforeInfo(hrStaffMoveRecord, personInfo);
            //员工编号
            hrStaffMoveRecord.setFdStaffNumber(personInfo.getFdStaffNo());
            //姓名
            hrStaffMoveRecord.setFdStaffName(personInfo.getFdName());
//            if (hrStaffMoveRecord.getFdAfterDept() != null) {
//                SysOrgElement[] sysOrgElements = getLeaderDeptByDept(hrStaffMoveRecord.getFdAfterDept());
//                //一级部门
//                if (null != sysOrgElements[0]) {
//                    personInfo.setFdFirstLevelDepartment(sysOrgElements[0]);
//                    hrStaffMoveRecord.setFdAfterFirstDeptName(sysOrgElements[0].getFdName());
//                } else {
//                    personInfo.setFdFirstLevelDepartment(null);
//                    hrStaffMoveRecord.setFdAfterFirstDeptName("");
//                }
//                //二级部门
//                if (null != sysOrgElements[1]) {
//                    personInfo.setFdSecondLevelDepartment(sysOrgElements[1]);
//                    hrStaffMoveRecord.setFdAfterSecondDeptName(sysOrgElements[1].getFdName());
//                } else {
//                    personInfo.setFdSecondLevelDepartment(null);
//                    hrStaffMoveRecord.setFdAfterSecondDeptName("");
//                }
//                //三级部门
//                if (null != sysOrgElements[2]) {
//                    personInfo.setFdThirdLevelDepartment(sysOrgElements[2]);
//                    hrStaffMoveRecord.setFdAfterThirdDeptName(sysOrgElements[2].getFdName());
//                } else {
//                    personInfo.setFdThirdLevelDepartment(null);
//                    hrStaffMoveRecord.setFdAfterThirdDeptName("");
//                }
//                //所在部门
//                personInfo.setFdOrgParent(hrStaffMoveRecord.getFdAfterDept());
//                personInfo.setFdParent((HrOrganizationElement)getHrOrganizationElementService().findByPrimaryKey(hrStaffMoveRecord.getFdAfterDept().getFdId()));
//                personInfo.getFdOrgPerson().setFdParent(hrStaffMoveRecord.getFdAfterDept());
//            }
            //设置职级
            if (StringUtil.isNotNull(hrStaffMoveRecord.getFdAfterRank())) {
                HrOrganizationRank hrOrganizationRank = getHrOrganizationRankService().findRankByName(hrStaffMoveRecord.getFdAfterRank());
                if (hrOrganizationRank != null) {
                    personInfo.setFdOrgRank(hrOrganizationRank);
                }
            }
            //设置岗位
            List<SysOrgPost> fdPosts = hrStaffMoveRecord.getFdAfterPosts();
            List<SysOrgPost> fdPosts1 = new ArrayList();
            if (!ArrayUtil.isEmpty(fdPosts)) {
            	for(SysOrgPost sysOrgPost : fdPosts){
            		fdPosts1.add(sysOrgPost);
            	}
            	personInfo.setFdOrgPosts(fdPosts1);
                hrStaffMoveRecord.setFdAfterPosts(fdPosts1);
            }
            //直接上级
            if (hrStaffMoveRecord.getFdAfterLeader() != null) {
                personInfo.setFdReportLeader(hrStaffMoveRecord.getFdAfterLeader());
            }
            //如果异动前后一级部门一致
            if (hrStaffMoveRecord.getFdBeforeFirstDeptName().equals(hrStaffMoveRecord.getFdAfterFirstDeptName())) {
                hrStaffMoveRecord.setFdTransDept("0");
            } else {
                hrStaffMoveRecord.setFdTransDept("1");
            }
            getHrStaffPersonInfoService().update(personInfo);
            getSysOrgPersonService().update(personInfo.getFdOrgPerson());;
            //添加任职记录
            addTrackRecord(personInfo,hrStaffMoveRecord.getFdMoveDate());
        }
        return super.add(hrStaffMoveRecord);
    }

    @Override
    public void add1(HrStaffMoveRecord modelObj) throws Exception {

        super.add(modelObj);
    }

    /**
     * 获取当月异动数据
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<String[]> findMoveMonthData(HttpServletRequest request) throws Exception {
        List<String[]> moveData = new ArrayList<>();
        HQLInfo info = new HQLInfo();
//        String where = "fdMoveDate >:star t and fdMoveDate <:end";
        String where = " 1=1 ";
        String fdTransDept = request.getParameter("fdTransDept");
        if (StringUtil.isNotNull(fdTransDept) && !fdTransDept.equals("2")) {
            where = StringUtil.linkString(where, " and ", "fdTransDept=:fdTransDept");
            info.setParameter("fdTransDept", fdTransDept);
        }
        String y = request.getParameter("y");
        String m = request.getParameter("m");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
        ca.setTime(new Date()); // 设置时间为当前时间
        ca.set(Calendar.YEAR, Integer.valueOf(y));
        ca.set(Calendar.MONTH, Integer.valueOf(m) - 1);

        // 本年1月1日
        String ben_nian_1yue_1ri = ca.get(Calendar.YEAR) + "-01-01";

        // 本月第一天
        ca.set(Calendar.DATE, 1);
        String ben_yue_1 = sdf.format(ca.getTime());

        // 本月最后一天
        ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
        String ben_yue_31 = sdf.format(ca.getTime());

        // 上月最后一天
        ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
        ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
        String shang_yue_31 = sdf.format(ca.getTime());
        if (StringUtil.isNotNull(y) && StringUtil.isNotNull(m)) {
            where = StringUtil.linkString(where, " and ", " fdMoveDate >=:ben_yue_1 and  fdMoveDate <=: ben_yue_31 ");
            info.setParameter("ben_yue_1", DateUtil.convertStringToDate(ben_yue_1));
            info.setParameter("ben_yue_31", DateUtil.convertStringToDate(ben_yue_31));
        }
        info.setWhereBlock(where);
        Date start = DateUtil.getBeginDayOfMonth();
        Date end = DateUtil.getEndDayOfMonth();
//        info.setParameter("start",start);
//        info.setParameter("end",end);
        List<HrStaffMoveRecord> moveRecords = this.findList(info);
        int index = 0;
        for (HrStaffMoveRecord hrStaffMoveRecord : moveRecords) {
            String[] data = new String[21];
            //序号
            data[0] = String.valueOf(index + 1);
            //工号
            data[1] = hrStaffMoveRecord.getFdStaffNumber();
            //姓名
            data[2] = hrStaffMoveRecord.getFdStaffName();
            //性别
//            if(hrStaffMoveRecord.getFdPersonInfo() != null){
//                data[3] = "M".equals(hrStaffMoveRecord.getFdPersonInfo().getFdSex())?"男":"女";
//            }else{
//                data[3] = "";
//            }
            data[3] = hrStaffMoveRecord.getFdIsExplore();

            Date fdInternStartDate = hrStaffMoveRecord.getFdInternStartDate();
            data[4] = DateUtil.convertDateToString(fdInternStartDate, "yyyy-MM-dd hh:mm:ss");

            Date fdInternEndDate = hrStaffMoveRecord.getFdInternEndDate();
            data[5] = DateUtil.convertDateToString(fdInternEndDate, "yyyy-MM-dd hh:mm:ss");
//            data[6] = "0".equals(hrStaffMoveRecord.getFdTransDept())?"否":"是";
//            data[7] =  hrStaffMoveRecord.getFdMoveType();
            Date fdMoveDate = hrStaffMoveRecord.getFdMoveDate();
            data[8] = DateUtil.convertDateToString(fdMoveDate, "yyyy-MM-dd hh:mm:ss");
            //入职时间
//            Date fdEntryTime = hrStaffMoveRecord.getFdPersonInfo().getFdEntryTime();
//            data[4] = DateUtil.convertDateToString(fdEntryTime,"yyyy-MM-dd hh:mm:ss");
            //原一级部门
            data[9] = hrStaffMoveRecord.getFdBeforeFirstDeptName();
            //原二级部门
            data[10] = hrStaffMoveRecord.getFdBeforeSecondDeptName();
            //原三级部门
            data[11] = hrStaffMoveRecord.getFdBeforeThirdDeptName();
            //原职级
            data[12] = hrStaffMoveRecord.getFdBeforeRank();

            //原职位
            String[] fdName = ArrayUtil.joinProperty(hrStaffMoveRecord.getFdBeforePosts(), "fdName", "");
            data[13] = StringUtil.join(fdName, ",");
            if (hrStaffMoveRecord.getFdBeforeLeader() != null)
                data[14] = hrStaffMoveRecord.getFdBeforeLeader().getFdName();
            else
                data[14] = "";
            //现一级部门
            data[15] = hrStaffMoveRecord.getFdAfterFirstDeptName();
            //现二级部门
            data[16] = hrStaffMoveRecord.getFdAfterSecondDeptName();
            //现三级部门
            data[17] = hrStaffMoveRecord.getFdAfterThirdDeptName();
            //现职级
            data[18] = hrStaffMoveRecord.getFdAfterRank();
            //现职位
            String[] fdAfterName = ArrayUtil.joinProperty(hrStaffMoveRecord.getFdAfterPosts(), "fdName", "");
            data[19] = StringUtil.join(fdAfterName, ",");
            if (hrStaffMoveRecord.getFdAfterLeader() != null) {
                data[20] = hrStaffMoveRecord.getFdAfterLeader().getFdName();
            } else {
                data[20] = "";
            }
            //异动类型
            data[7] = EnumerationTypeUtil.getColumnEnumsLabel("hr_staff_move_type", hrStaffMoveRecord.getFdMoveType());
            //是否跨一级部门
            if (StringUtil.isNull(hrStaffMoveRecord.getFdTransDept())) {
                data[6] = "";
            } else {
                data[6] = "0".equals(hrStaffMoveRecord.getFdTransDept()) ? "否" : "是";
            }
            moveData.add(data);
            index++;
        }
        return moveData;
    }
    

    @Override
    public List<String[]> findStatListDetail(Date start, Date end, List<String> targetIds) throws Exception {
    	SysAttendPersonReportService sysAttendPersonReportService = (SysAttendPersonReportService)SpringBeanUtil.getBean("sysAttendPersonReportService");
        List<String[]> listNew = sysAttendPersonReportService.getAttendDataByTempleteIds(start,AttendUtil.getEndDate(end, 0), targetIds);
    	
    	return listNew;
    }
    @Override
    public List<String[]> findMoveMonthData1(String fdTransDept, String y, String m, String No) throws Exception {
        if (y.equals("") || m.equals("")) {
            return new ArrayList<String[]>();
        }
        List<String[]> moveData = new ArrayList<>();
        HQLInfo info = new HQLInfo();
//        String where = "fdMoveDate >:star t and fdMoveDate <:end";
        String where = " 1=1 ";
        where = StringUtil.linkString(where, " and ", "fdFlag!=1 or fdFlag is Null ");
        if (StringUtil.isNotNull(fdTransDept) && !fdTransDept.equals("2")) {
            where = StringUtil.linkString(where, " and ", "fdTransDept=:fdTransDept");
            info.setParameter("fdTransDept", fdTransDept);
        }
        if (StringUtil.isNotNull(No)) {
            where = StringUtil.linkString(where, " and ", "fdStaffNumber=:No");
            info.setParameter("No", No);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
        ca.setTime(new Date()); // 设置时间为当前时间
        ca.set(Calendar.YEAR, Integer.valueOf(y));
        ca.set(Calendar.MONTH, Integer.valueOf(m) - 1);

        // 本年1月1日
        String ben_nian_1yue_1ri = ca.get(Calendar.YEAR) + "-01-01";

        // 本月第一天
        ca.set(Calendar.DATE, 1);
        String ben_yue_1 = sdf.format(ca.getTime());

        // 本月最后一天
        ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
        String ben_yue_31 = sdf.format(ca.getTime());

        // 上月最后一天
        ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
        ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
        String shang_yue_31 = sdf.format(ca.getTime());
        if (StringUtil.isNotNull(y) && StringUtil.isNotNull(m)) {
            where = StringUtil.linkString(where, " and ", " fdMoveDate >=:ben_yue_1 and  fdMoveDate <=: ben_yue_31 ");
            info.setParameter("ben_yue_1", DateUtil.convertStringToDate(ben_yue_1));
            info.setParameter("ben_yue_31", DateUtil.convertStringToDate(ben_yue_31));
        }
        info.setWhereBlock(where);
        Date start = DateUtil.getBeginDayOfMonth();
        Date end = DateUtil.getEndDayOfMonth();
//        info.setParameter("start",start);
//        info.setParameter("end",end);
        List<HrStaffMoveRecord> moveRecords = this.findList(info);
        int index = 0;
        for (HrStaffMoveRecord hrStaffMoveRecord : moveRecords) {
            String[] data = new String[21];
            //序号
            data[0] = String.valueOf(index + 1);
            //工号
            data[1] = hrStaffMoveRecord.getFdStaffNumber();
            //姓名
            data[2] = hrStaffMoveRecord.getFdStaffName();
            //性别
//            if(hrStaffMoveRecord.getFdPersonInfo() != null){
//                data[3] = "M".equals(hrStaffMoveRecord.getFdPersonInfo().getFdSex())?"男":"女";
//            }else{
//                data[3] = "";
//            }
            data[3] = hrStaffMoveRecord.getFdIsExplore();

            Date fdInternStartDate = hrStaffMoveRecord.getFdInternStartDate();
            data[4] = DateUtil.convertDateToString(fdInternStartDate, "yyyy-MM-dd hh:mm:ss");

            Date fdInternEndDate = hrStaffMoveRecord.getFdInternEndDate();
            data[5] = DateUtil.convertDateToString(fdInternEndDate, "yyyy-MM-dd hh:mm:ss");
//            data[6] = "0".equals(hrStaffMoveRecord.getFdTransDept())?"否":"是";
//            data[7] =  hrStaffMoveRecord.getFdMoveType();
            Date fdMoveDate = hrStaffMoveRecord.getFdMoveDate();
            data[8] = DateUtil.convertDateToString(fdMoveDate, "yyyy-MM-dd hh:mm:ss");
            //入职时间
//            Date fdEntryTime = hrStaffMoveRecord.getFdPersonInfo().getFdEntryTime();
//            data[4] = DateUtil.convertDateToString(fdEntryTime,"yyyy-MM-dd hh:mm:ss");
            //原一级部门
            data[9] = hrStaffMoveRecord.getFdBeforeFirstDeptName();
            //原二级部门
            data[10] = hrStaffMoveRecord.getFdBeforeSecondDeptName();
            //原三级部门
            data[11] = hrStaffMoveRecord.getFdBeforeThirdDeptName();
            //原职级
            data[12] = hrStaffMoveRecord.getFdBeforeRank();

            //原职位
            String[] fdName = ArrayUtil.joinProperty(hrStaffMoveRecord.getFdBeforePosts(), "fdName", "");
            data[13] = StringUtil.join(fdName, ",");
            if (hrStaffMoveRecord.getFdBeforeLeader() != null) {
                data[14] = hrStaffMoveRecord.getFdBeforeLeader().getFdName();
            } else {
                data[14] = "";
            }
            //现一级部门
            data[15] = hrStaffMoveRecord.getFdAfterFirstDeptName();
            //现二级部门
            data[16] = hrStaffMoveRecord.getFdAfterSecondDeptName();
            //现三级部门
            data[17] = hrStaffMoveRecord.getFdAfterThirdDeptName();
            //现职级
            data[18] = hrStaffMoveRecord.getFdAfterRank();
            //现职位
            String[] fdAfterName = ArrayUtil.joinProperty(hrStaffMoveRecord.getFdAfterPosts(), "fdName", "");
            data[19] = StringUtil.join(fdAfterName, ",");
            if (hrStaffMoveRecord.getFdAfterLeader() != null)
                data[20] = hrStaffMoveRecord.getFdAfterLeader().getFdName();
            else
                data[20] = "";
            //异动类型
            data[7] = EnumerationTypeUtil.getColumnEnumsLabel("hr_staff_move_type", hrStaffMoveRecord.getFdMoveType());
            //是否跨一级部门
            if (StringUtil.isNull(hrStaffMoveRecord.getFdTransDept())) {
                data[6] = "";
            } else {
                data[6] = "0".equals(hrStaffMoveRecord.getFdTransDept()) ? "否" : "是";
            }
            moveData.add(data);
            index++;
        }
        return moveData;
    }

    /**
     * 获取在职期间的人员异动记录
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<String[]> findMoveAllData(HttpServletRequest request) throws Exception {
        List<String[]> moveData = new ArrayList<>();
        HQLInfo info = new HQLInfo();
        String where = "fdPersonInfo.fdStatus='official'";
        String begin = request.getParameter("begin");
        if (StringUtil.isNotNull(begin)) {
            Date beginDate = DateUtil.convertStringToDate(begin, "yyyy-MM-dd");
            where = StringUtil.linkString(where, " and ", "fdMoveDate >=:begin");
            info.setParameter("begin", beginDate);
        }

        String end = request.getParameter("end");
        if (StringUtil.isNotNull(end)) {
            Date endDate = DateUtil.convertStringToDate(end, "yyyy-MM-dd");
            where = StringUtil.linkString(where, " and ", "fdMoveDate <=:end");
            info.setParameter("end", endDate);
        }

        info.setWhereBlock(where);
        List<HrStaffMoveRecord> moveRecords = this.findList(info);
        int index = 0;
        for (HrStaffMoveRecord hrStaffMoveRecord : moveRecords) {
            String[] data = new String[11];
            //序号
            data[0] = String.valueOf(index + 1);
            //工号
            data[4] = hrStaffMoveRecord.getFdStaffNumber();
            //姓名
            data[5] = hrStaffMoveRecord.getFdStaffName();
            String fdAfterFirstDeptName = "";
            if (hrStaffMoveRecord.getFdAfterFirstDeptName() != null)
                fdAfterFirstDeptName = hrStaffMoveRecord.getFdAfterFirstDeptName();
            String fdBeforeFirstDeptName = "";
            if (hrStaffMoveRecord.getFdBeforeFirstDeptName() != null)
                fdBeforeFirstDeptName = hrStaffMoveRecord.getFdBeforeFirstDeptName();
            String fdAfterSecondDeptName = "";
            if (hrStaffMoveRecord.getFdAfterSecondDeptName() != null)
                fdAfterSecondDeptName = hrStaffMoveRecord.getFdAfterSecondDeptName();
            String fdBeforeSecondDeptName = "";
            if (hrStaffMoveRecord.getFdBeforeSecondDeptName() != null)
                fdBeforeSecondDeptName = hrStaffMoveRecord.getFdBeforeSecondDeptName();
            String fdAfterThirdDeptName = "";
            if (hrStaffMoveRecord.getFdAfterThirdDeptName() != null)
                fdAfterThirdDeptName = hrStaffMoveRecord.getFdAfterThirdDeptName();
            String fdBeforeThirdDeptName = "";
            if (hrStaffMoveRecord.getFdBeforeSecondDeptName() != null)
                fdAfterThirdDeptName = hrStaffMoveRecord.getFdAfterThirdDeptName();
            String fdBeforeRank = "";
            if (hrStaffMoveRecord.getFdBeforeRank() != null)
                fdBeforeRank = hrStaffMoveRecord.getFdBeforeRank();
            String fdAfterRank = "";
            //性别
            if ((!((fdAfterFirstDeptName.isEmpty()) && (fdBeforeFirstDeptName.isEmpty())) & !fdAfterFirstDeptName.equals(fdBeforeFirstDeptName))) {
                data[6] = "一级部门";
                data[7] = hrStaffMoveRecord.getFdBeforeFirstDeptName();
                data[8] = hrStaffMoveRecord.getFdAfterFirstDeptName();

            } else if ((!((fdAfterSecondDeptName.isEmpty()) && (fdBeforeSecondDeptName.isEmpty())) & !hrStaffMoveRecord.getFdAfterSecondDeptName().equals(hrStaffMoveRecord.getFdBeforeSecondDeptName()))) {
                data[6] = "二级部门";
                data[7] = hrStaffMoveRecord.getFdBeforeSecondDeptName();
                data[8] = hrStaffMoveRecord.getFdAfterSecondDeptName();
            } else if (!hrStaffMoveRecord.getFdAfterThirdDeptName().equals(hrStaffMoveRecord.getFdBeforeThirdDeptName())) {
                data[6] = "三级部门";
                data[7] = hrStaffMoveRecord.getFdBeforeThirdDeptName();
                data[8] = hrStaffMoveRecord.getFdAfterThirdDeptName();
            } else if (!hrStaffMoveRecord.getFdAfterRank().equals(hrStaffMoveRecord.getFdBeforeRank())) {
                data[6] = "职级";
                data[7] = hrStaffMoveRecord.getFdBeforeRank();
                data[8] = hrStaffMoveRecord.getFdAfterRank();
            } else if (!StringUtil.join(ArrayUtil.joinProperty(hrStaffMoveRecord.getFdAfterPosts(), "fdName", ""), ",").equals(StringUtil.join(ArrayUtil.joinProperty(hrStaffMoveRecord.getFdBeforePosts(), "fdName", ""), ","))) {
                data[6] = "岗位";
                String[] fdBeforeName = ArrayUtil.joinProperty(hrStaffMoveRecord.getFdBeforePosts(), "fdName", "");
                data[7] = StringUtil.join(fdBeforeName, ",");
                String[] fdAfterName = ArrayUtil.joinProperty(hrStaffMoveRecord.getFdAfterPosts(), "fdName", "");
                data[8] = StringUtil.join(fdAfterName, ",");
            } else if (hrStaffMoveRecord.getFdAfterLeader() != (hrStaffMoveRecord.getFdBeforeLeader())) {
                data[6] = "直接上级";
                if (hrStaffMoveRecord.getFdBeforeLeader() != null)
                    data[7] = hrStaffMoveRecord.getFdBeforeLeader().getFdName();
                else
                    data[7] = "";
                if (hrStaffMoveRecord.getFdAfterLeader() != null)
                    data[8] = hrStaffMoveRecord.getFdAfterLeader().getFdName();
                else
                    data[8] = "";
            }
            //是否最新状态
//            data[6] = "是";
            //异动时间
//            data[8] = DateUtil.convertDateToString(hrStaffMoveRecord.getFdMoveDate(),"yyyy-MM-dd");
            //现一级部门
            data[1] = hrStaffMoveRecord.getFdAfterFirstDeptName();
            //现二级部门
            data[2] = hrStaffMoveRecord.getFdAfterSecondDeptName();
            //现三级部门
            data[3] = hrStaffMoveRecord.getFdAfterThirdDeptName();
            //现职级
            data[9] = hrStaffMoveRecord.getFdBeforeRank();
            //现职位
            String[] fdAfterName = ArrayUtil.joinProperty(hrStaffMoveRecord.getFdAfterPosts(), "fdName", "");
            data[10] = StringUtil.join(fdAfterName, ",");
            moveData.add(data);
            index++;
        }
        return moveData;
    }

    /**
     * 根据部门获取上3级部门
     *
     * @param dept
     * @return
     * @throws Exception
     */
    public SysOrgElement[] getLeaderDeptByDept(SysOrgElement dept) throws Exception {
        logger.info("正在查找部门：{}的3级部门", dept.getFdName());
        //获取前3级部门
        SysOrgElement[] sysOrgElements = new SysOrgElement[3];
        String[] ids = dept.getFdHierarchyId().substring(1).split("x");
        int index = 0;
        for (int i = 0; i < ids.length; i++) {
            if (index >= 3) {
                break;
            }
            SysOrgElement tempElement = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(ids[i], null, true);
            if (tempElement != null && tempElement.getFdOrgType().equals(SysOrgConstant.ORG_TYPE_DEPT)) {
                sysOrgElements[index] = tempElement;
                logger.info("部门:{}的{}级部门是{}", dept.getFdName(), i, tempElement.getFdName());
                index++;
            }
        }
        return sysOrgElements;
    }

    /**
     * 新增任职记录
     *
     * @param personInfo
     * @throws Exception
     */
    private void addTrackRecord(HrStaffPersonInfo personInfo, Date fdEffectiveDate) throws Exception {
        HrStaffTrackRecord record = new HrStaffTrackRecord();
        //部门
        record.setFdRatifyDept(personInfo.getFdOrgParent());
        //人员
        record.setFdPersonInfo(personInfo);
        //当前时间
        Date currentDate = new Date();
        //开始时间
        if(fdEffectiveDate != null){
            record.setFdEntranceBeginDate(fdEffectiveDate);
        } else{
            record.setFdEntranceBeginDate(currentDate);
        }
        //职务
        record.setFdStaffingLevel(personInfo.getFdStaffingLevel());
        //任职类型
//        record.setFdType("1");
        //任职状态 1、任职中  2、已结束  3、待任职
//        if(fdEffectiveDate.getTime() > System.currentTimeMillis()){
//            record.setFdStatus("3");
//        }else{
//            record.setFdStatus("1");
//        }
        //创建时间
        record.setFdCreateTime(currentDate);
        if(!ArrayUtil.isEmpty(personInfo.getFdOrgPosts())){
            record.setFdOrgPosts(personInfo.getFdOrgPosts());
        }
        getHrStaffTrackRecordService().add(record);
    }

    /**
     * 根据personInfo信息设置
     */
    private void setPersonBeforeInfo(HrStaffMoveRecord hrStaffMoveRecord, HrStaffPersonInfo hrStaffPersonInfo) throws Exception {
        //设置原部门
        SysOrgElement fdBeforeDept = null;
        if (hrStaffPersonInfo.getFdOrgParent() != null) {
            fdBeforeDept = hrStaffPersonInfo.getFdOrgParent();
            hrStaffPersonInfo.getFdOrgPerson().setFdParent(hrStaffMoveRecord.getFdAfterDept());
        }
        //设置原一级，二级，三级部门信息
        if (fdBeforeDept != null) {
            SysOrgElement[] sysOrgElements = getLeaderDeptByDept(fdBeforeDept);
            //一级部门
            if (null != sysOrgElements[0]) {
                hrStaffMoveRecord.setFdBeforeFirstDeptName(sysOrgElements[0].getFdName());
            }
            //二级部门
            if (null != sysOrgElements[1]) {
                hrStaffMoveRecord.setFdBeforeSecondDeptName(sysOrgElements[1].getFdName());
            }
            //三级部门
            if (null != sysOrgElements[2]) {
                hrStaffMoveRecord.setFdBeforeThirdDeptName(sysOrgElements[2].getFdName());
            }
        }
        //原职级
//        if (hrStaffPersonInfo.getFdOrgRank() != null) {
//            hrStaffMoveRecord.setFdBeforeRank(hrStaffPersonInfo.getFdOrgRank().getFdName());
//        }
        //原岗位
        List<SysOrgPost> fdPosts = hrStaffPersonInfo.getFdOrgPosts();
        List<SysOrgPost> fdPosts1 = new ArrayList();
        if (!ArrayUtil.isEmpty(fdPosts)) {
        	for(SysOrgPost sysOrgPost : fdPosts){
        		fdPosts1.add(sysOrgPost);
        	}
            hrStaffMoveRecord.setFdBeforePosts(fdPosts1);
        }
        //原直接上级
//        if (hrStaffPersonInfo.getFdReportLeader() != null) {
//            hrStaffMoveRecord.setFdBeforeLeader(hrStaffPersonInfo.getFdReportLeader());
//        }
    }

    /**
     * 根据岗位名获取岗位信息
     *
     * @param names
     * @return
     * @throws Exception
     */
    private List<SysOrgPost> findByNames(String[] names,SysOrgElement fdParent) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock(HQLUtil.buildLogicIN("fdName", ArrayUtil.asList(names)));
        List<SysOrgElement> list = getSysOrgPostService().findList(info);
        List<SysOrgElement> list1=getSysOrgElementService().findAllChildElement(fdParent, 4);
        List list2 = new ArrayList();
        for(Object elm:list){
        	for(Object elm1:list1){
            	if( elm == elm1)
            	list2.add(elm);
        	}
        }
        return list2;
    }

    /**
     * 根据名称获取组织架构信息
     */
    private SysOrgElement findSysOrgElementByNames(String name,HrStaffPersonInfo personInfo) throws Exception {
    	
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdName=:fdName and fdOrgType=:fdOrgType and fdIsAvailable=true");
        info.setParameter("fdName", name);
        info.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_DEPT);
        List<SysOrgElement> sysOrgElements = getSysOrgElementService().findList(info);
        if (!ArrayUtil.isEmpty(sysOrgElements)) {
        for(SysOrgElement sysOrgElement : sysOrgElements){
        	List<SysOrgElement> allParent = sysOrgElement.getAllParent(true);
        	if(allParent.size()!=1){
    			for (int i=0;i<=allParent.size() - 2; i++) {
    				SysOrgElement sysOrgElement1 = allParent.get(i);
    				if(sysOrgElement1==personInfo.getFdSecondLevelDepartment()||sysOrgElement1==personInfo.getFdFirstLevelDepartment()){
    					return sysOrgElement;
    				}
    			}
        	}else if(sysOrgElements.size()==1){
        		return sysOrgElements.get(0);
        	}else{
        		throw new Exception("一级部门出现出现重名情况");
        	}
        }
        }
        return null;
    }

    private SysOrgElement findSysOrgElementByNames1(String name) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("fdNo=:fdName and fdOrgType=:fdOrgType");
        info.setParameter("fdName", name);
        info.setParameter("fdOrgType", SysOrgConstant.ORG_TYPE_PERSON);
        List<SysOrgElement> sysOrgElements = getSysOrgElementService().findList(info);
        if (!ArrayUtil.isEmpty(sysOrgElements)) {
            if (sysOrgElements.size() > 1) {
                throw new Exception("上级领导出现出现重名情况，请使用登录名");
            } else {
                return sysOrgElements.get(0);
            }
        }
        return null;
    }

    @Override
    public List<String[]> findRecruitData(HttpServletRequest request) throws Exception {
        List<String[]> recruitData = new ArrayList<>();
        HQLInfo info = new HQLInfo();
        String where = "";
        String begin = request.getParameter("begin");
        if (StringUtil.isNotNull(begin)) {
            Date beginDate = DateUtil.convertStringToDate(begin, "yyyy-MM-dd");
            where = StringUtil.linkString(where, " and ", "fdEntryTime >=:begin");
            info.setParameter("begin", beginDate);
        }

        String end = request.getParameter("end");
        if (StringUtil.isNotNull(end)) {
            Date endDate = DateUtil.convertStringToDate(end, "yyyy-MM-dd");
            where = StringUtil.linkString(where, " and ", "fdEntryTime <=:end");
            info.setParameter("end", endDate);
        }
        info.setWhereBlock(where);

        List<HrStaffPersonInfo> moveRecords = getHrStaffPersonInfoService().findList(info);
        int index = 0;
        for (HrStaffPersonInfo hrStaffPersonInfo : moveRecords) {
            String[] data = new String[12];
            //序号
            data[0] = String.valueOf(index + 1);
            //入职日期
            data[1] = DateUtil.convertDateToString(hrStaffPersonInfo.getFdEntryTime(), "yyyy-MM-dd");
            //人员编号
            data[2] = hrStaffPersonInfo.getFdStaffNo();
            //姓名
            data[3] = hrStaffPersonInfo.getFdName();
            //人员类型
            data[4] = hrStaffPersonInfo.getFdStaffType();
            //性别
            data[5] = "M".equals(hrStaffPersonInfo.getFdSex()) ? "男" : "女";
            //岗位名称
            List fdPosts = hrStaffPersonInfo.getFdOrgPosts();
            if (!ArrayUtil.isEmpty(fdPosts)) {
                String[] postString = ArrayUtil.joinProperty(fdPosts, "fdName", "");
                data[6] = StringUtil.join(postString, ";");
            } else {
                data[6] = "";
            }
            //所属公司
            data[7] = hrStaffPersonInfo.getFdAffiliatedCompany();
            //职级
            data[8] = hrStaffPersonInfo.getFdOrgRank() != null ? hrStaffPersonInfo.getFdOrgRank().getFdName() : "";
            //一级部门
            data[9] = hrStaffPersonInfo.getFdFirstLevelDepartment() != null ? hrStaffPersonInfo.getFdFirstLevelDepartment().getFdName() : "";
            //二级部门
            data[10] = hrStaffPersonInfo.getFdSecondLevelDepartment() != null ? hrStaffPersonInfo.getFdSecondLevelDepartment().getFdName() : "";
            //三级部门
            data[11] = hrStaffPersonInfo.getFdThirdLevelDepartment() != null ? hrStaffPersonInfo.getFdThirdLevelDepartment().getFdName() : "";
            recruitData.add(data);
            index++;
        }
        return recruitData;
    }
}

package com.landray.kmss.hr.staff.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.util.ResourceUtil;
import org.apache.commons.collections.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Henry Pan
 * @date 2021/8/11 20:11
 * @email diypyh@163.com
 */
public class HrStaffPersonInfoPortlet implements IXMLDataBean {

    private IHrStaffPersonInfoService hrStaffPersonInfoService;

    public void setHrStaffPersonInfoService(IHrStaffPersonInfoService hrStaffPersonInfoService) {
        this.hrStaffPersonInfoService = hrStaffPersonInfoService;
    }

    @Override
    public List getDataList(RequestContext requestInfo) throws Exception {
        // 获取当天生日人员信息
        if ("birthday".equals(requestInfo.getParameter("type"))) {
            return getBirthday(requestInfo);
        }
        return Collections.emptyList();
    }

    private List getBirthday(RequestContext requestInfo) throws Exception {
        List<Map> rtnList = new ArrayList<Map>();
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("hrStaffPersonInfo.fdStatus in (:status) and hrStaffPersonInfo.fdBirthdayOfYear >= :birthdayOfYear1 and hrStaffPersonInfo.fdBirthdayOfYear <= :birthdayOfYear2");
        List<String> status = new ArrayList<>();
        status.add("trial");
        status.add("official");
        status.add("temporary");
        status.add("trialDelay");
        status.add("retire");
        hqlInfo.setParameter("status", status);
        Calendar cal = Calendar.getInstance();
        // 获取今天是一年中的第几天
        int day = cal.get(Calendar.DAY_OF_YEAR);
        // 由于平润年的关系，DAY_OF_YEAR并不是绝对准确的，这里以DAY_OF_YEAR为基础，查询前3天和后3天的数据，然后在程序里处理
        hqlInfo.setParameter("birthdayOfYear1", day - 3);
        hqlInfo.setParameter("birthdayOfYear2", day + 3);
        hqlInfo.setOrderBy("hrStaffPersonInfo.fdId");
        List<HrStaffPersonInfo> list = hrStaffPersonInfoService.findList(hqlInfo);
        if (CollectionUtils.isNotEmpty(list)) {
            Date today = new Date();
            Iterator<HrStaffPersonInfo> iterator = list.iterator();
            while (iterator.hasNext()) {
                HrStaffPersonInfo info = iterator.next();
                // 判断该人员的生日是否今天
                if (info.getFdDateOfBirth() == null || !sameDate(info.getFdDateOfBirth(), today)) {
                    iterator.remove();
                }
            }
            // 5个以内：祝：%s 同学生日快乐！永远十八岁青春永驻！
            // 5个以上：祝：%s... 等%d位同学生日快乐！
            StringBuffer text = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                HrStaffPersonInfo info = list.get(i);
                if (text.length() > 0) {
                    text.append(", ");
                }
                String name = info.getFdName();
                if (info.getFdOrgPerson() != null) {
                    name = info.getFdOrgPerson().getFdName();
                }
                text.append(name);
                if (i >= 5) {
                    break;
                }
            }
            // 祝福语
            String wishes;
            if (list.size() > 5) {
                wishes = ResourceUtil.getString("hr-staff:hrStaff.portlet.birthday.wishes.more");
                wishes = String.format(wishes, text, list.size());
            } else {
                wishes = ResourceUtil.getString("hr-staff:hrStaff.portlet.birthday.wishes");
                wishes = String.format(wishes, text);
            }
            Map<String, String> map = new HashMap<>(1);
            if (requestInfo.isCloud()) {
                map.put("text", wishes);
                // 暂时没有详情页面
                // map.put("href", "#");
            } else {
                // EKP未实现该组件
            }
            rtnList.add(map);
        }
        return rtnList;
    }

    private boolean sameDate(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("MMdd");
        return fmt.format(d1).equals(fmt.format(d2));
    }

}

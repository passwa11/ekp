package com.landray.kmss.km.calendar.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventAsyncCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.km.calendar.cms.CMSThreadPoolManager;
import com.landray.kmss.km.calendar.cms.interfaces.ISyncOutAuthProvider;
import com.landray.kmss.km.calendar.model.KmCalendarAuth;
import com.landray.kmss.km.calendar.model.KmCalendarAuthList;
import com.landray.kmss.km.calendar.model.KmCalendarRequestAuth;
import com.landray.kmss.km.calendar.model.KmCalendarShareGroup;
import com.landray.kmss.km.calendar.service.*;
import com.landray.kmss.sys.authentication.user.UserAuthInfo;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.sys.mobile.util.MobileUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.interfaces.NotifyReplace;
import com.landray.kmss.sys.organization.event.SysOrgElementChangeEvent;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.*;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.CacheMode;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypeTemplate;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 原来注释：日程共享人员业务访问接口实现
 *
 * @description: 新增组织变动事件监听
 * @author: wangjf
 * @time: 2021/11/18 3:21 下午
 */
public class KmCalendarAuthServiceImp extends BaseServiceImp implements
        IKmCalendarAuthService, IEventMulticasterAware, IXMLDataBean, ApplicationListener<SysOrgElementChangeEvent> {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmCalendarAuthServiceImp.class);

    private ISysOrgCoreService sysOrgCoreService = null;

    public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
        this.sysOrgCoreService = sysOrgCoreService;
    }

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        return sysNotifyMainCoreService;
    }

    public void setSysNotifyMainCoreService(
            ISysNotifyMainCoreService sysNotifyMainCoreService) {
        this.sysNotifyMainCoreService = sysNotifyMainCoreService;
    }

    private IKmCalendarShareGroupService kmCalendarShareGroupService;

    public void setKmCalendarShareGroupService(
            IKmCalendarShareGroupService kmCalendarShareGroupService) {
        this.kmCalendarShareGroupService = kmCalendarShareGroupService;
    }

    private IKmCalendarRequestAuthService kmCalendarRequestAuthService;

    public void setKmCalendarRequestAuthService(
            IKmCalendarRequestAuthService kmCalendarRequestAuthService) {
        this.kmCalendarRequestAuthService = kmCalendarRequestAuthService;
    }

    private IKmCalendarMainService kmCalendarMainService;

    public void setKmCalendarMainService(
            IKmCalendarMainService kmCalendarMainService) {
        this.kmCalendarMainService = kmCalendarMainService;
    }

    private IKmCalendarAuthListService kmCalendarAuthListService;

    public void setKmCalendarAuthListService(
            IKmCalendarAuthListService kmCalendarAuthListService) {
        this.kmCalendarAuthListService = kmCalendarAuthListService;
    }

    private IEventMulticaster multicaster;

    @Override
    public void setEventMulticaster(IEventMulticaster multicaster) {
        this.multicaster = multicaster;
    }

    @Override
    public String add(IBaseModel modelObj) throws Exception {
        String fdId = super.add(modelObj);
        syncOutAuth((KmCalendarAuth) modelObj);
        return fdId;
    }

    @Override
    public void update(IBaseModel modelObj) throws Exception {
        super.update(modelObj);
        syncOutAuth((KmCalendarAuth) modelObj);
    }

    private void syncOutAuth(KmCalendarAuth kmCalendarAuth) {

        multicaster.attatchEvent(new EventOfTransactionCommit(kmCalendarAuth), new IEventAsyncCallBack() {
            @Override
            public void execute(ApplicationEvent event) throws Throwable {
                Object obj = event.getSource();
                if (obj instanceof KmCalendarAuth) {
                    KmCalendarAuth auth = (KmCalendarAuth) obj;
                    SyncOutThread t = new SyncOutThread(auth.getFdId());
                    CMSThreadPoolManager manager = CMSThreadPoolManager.getInstance();
                    if (!manager.isStarted()) {
                        manager.start();
                    }
                    manager.submit(t);
                }
            }
        });
    }

    private void sendNotify(KmCalendarAuth kmCalendarAuth,
                            List<String> persons, boolean remove) throws Exception {
        List<SysOrgElement> elements = new ArrayList<SysOrgElement>();
        for (String personId : persons) {
            elements.add(sysOrgCoreService.findByPrimaryKey(personId));
        }
        NotifyContext notifyContext = null;
        NotifyReplace notifyReplace = new NotifyReplace();

        if (remove) {
            notifyContext = sysNotifyMainCoreService.getContext("km-calendar:kmCalendarShareGroup.shareClose");
        } else {
            notifyContext = sysNotifyMainCoreService.getContext("km-calendar:kmCalendarShareGroup.shareOpen");
        }
        // 获取通知方式
        notifyContext.setNotifyType("todo");
        notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
        notifyContext.setNotifyTarget(elements);

        notifyReplace.addReplaceModel("km-calendar:share", UserUtil.getUser(), "fdName");
        notifyContext.setLink("/km/calendar/group.jsp?groupId=defaultGroup");

        sysNotifyMainCoreService.sendNotify(kmCalendarAuth, notifyContext,
                notifyReplace);
    }

    private void notifyReaders(KmCalendarAuth kmCalendarAuth,
                               List<String> readerIds_current, List<String> readerIds_prior)
            throws Exception {
        if (readerIds_current == null && readerIds_prior == null) {
            return;
        } else if (readerIds_current == null) {
            // 通知删除
            sendNotify(kmCalendarAuth, readerIds_prior, true);
        } else if (readerIds_prior == null) {
            // 通知新增
            sendNotify(kmCalendarAuth, readerIds_current, false);
        } else {
            List<String> readerIds_add = new ArrayList<String>();
            for (String id : readerIds_current) {
                if (!readerIds_prior.remove(id)) {
                    readerIds_add.add(id);
                }
            }
            try {
                // 通知新增 readerIds_add
                sendNotify(kmCalendarAuth, readerIds_add, false);
                // 通知删除readerIds_prior
                sendNotify(kmCalendarAuth, readerIds_prior, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(IExtendForm form, RequestContext requestContext)
            throws Exception {

        String fdId = requestContext.getParameter("fdId");
        if (StringUtil.isNull(fdId)) {
            fdId = UserUtil.getUser().getFdId();
        }

        List list_prior = findList("kmCalendarAuth.docCreator.fdId = '" + fdId + "'", null);
        UserOperHelper.logUpdate(getModelName());
        IBaseModel model = convertFormToModel(form, null, requestContext);
        if (model == null) {
            throw new NoRecordException();
        }
        KmCalendarAuth kmCalendarAuth = (KmCalendarAuth) model;
        List<String> readersIdList_current = sysOrgCoreService
                .expandToPersonIds(kmCalendarAuth.getAuthReaders());

        if (list_prior != null && list_prior.size() > 0) {
            KmCalendarAuth auth = (KmCalendarAuth) list_prior.get(0);
            List<String> readersIdList_pre = sysOrgCoreService
                    .expandToPersonIds(auth.getAuthReaders());
            // 记录修改日志
            if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_UPDATE,
                    getModelName())) {
                UserOperContentHelper.putUpdate(auth)
                        .putSimple("authEditors", auth.getAuthEditors(),
                                kmCalendarAuth.getAuthEditors())
                        .putSimple("authReaders", auth.getAuthReaders(),
                                kmCalendarAuth.getAuthReaders())
                        .putSimple("authModifiers", auth.getAuthModifiers(),
                                kmCalendarAuth.getAuthModifiers());
            }
            auth.setAuthEditors(kmCalendarAuth.getAuthEditors());
            auth.setAuthReaders(kmCalendarAuth.getAuthReaders());
            auth.setAuthModifiers(kmCalendarAuth.getAuthModifiers());
            update(auth);

            // 通知修改的人
            notifyReaders(auth, readersIdList_current, readersIdList_pre);

        } else {
            update(model);

            // 通知所有人
            notifyReaders(kmCalendarAuth, readersIdList_current, null);
        }
    }

    @Override
    public void updateByAuthList(KmCalendarAuth auth) throws Exception {
        List<String> readersIdList_pre = sysOrgCoreService
                .expandToPersonIds(auth.getAuthReaders());
        List<String> readersIdList_current = null;

        // 将KmCalendarAuthList中的可创建者、可阅读者、可维护者汇总
        List<SysOrgElement> authEditors = new ArrayList<>();
        List<SysOrgElement> authReaders = new ArrayList<>();
        List<SysOrgElement> authModifiers = new ArrayList<>();
        List<KmCalendarAuthList> authLists = auth.getKmCalendarAuthList();
        if (authLists != null && !authLists.isEmpty()) {
            for (KmCalendarAuthList kmCalendarAuthList : authLists) {
                if (BooleanUtils
                        .isTrue(kmCalendarAuthList.getFdIsPartShare())) {
                    continue;
                }
                if (BooleanUtils.isTrue(kmCalendarAuthList.getFdIsEdit())) {
                    ArrayUtil.concatTwoList(kmCalendarAuthList.getFdPerson(),
                            authEditors);
                }
                if (BooleanUtils.isTrue(kmCalendarAuthList.getFdIsRead())) {
                    ArrayUtil.concatTwoList(kmCalendarAuthList.getFdPerson(),
                            authReaders);
                }
                if (BooleanUtils.isTrue(kmCalendarAuthList.getFdIsModify())) {
                    ArrayUtil.concatTwoList(kmCalendarAuthList.getFdPerson(),
                            authModifiers);
                }
            }
        }

        auth.setAuthEditors(authEditors);
        auth.setAuthReaders(authReaders);
        auth.setAuthModifiers(authModifiers);
        update(auth);

        // 通知修改的人
        if (!authReaders.isEmpty()) {
            readersIdList_current = sysOrgCoreService
                    .expandToPersonIds(authReaders);
        }
        notifyReaders(auth, readersIdList_current, readersIdList_pre);
    }

    @Override
    public Map<String, List> getDefaultGroupMembers(
            RequestContext requestContext, Boolean loadAll) throws Exception {
        String userId = requestContext.getParameter("userId");// 当前用户id
        String personId = requestContext.getParameter("personsId");// 查询指定人员
        String loadAllPara = requestContext.getParameter("loadAll");// 加载所有标示位
        String operType = requestContext.getParameter("operType");// 是否是月视图标志
        if (StringUtil.isNotNull(loadAllPara)) {
            loadAll = new Boolean(loadAllPara);
        }
        List<SysOrgElement> totalPersons = new ArrayList<SysOrgElement>();// 全部日程人员
        List<SysOrgElement> persons = new ArrayList<SysOrgElement>();// 当前日程人员

        List orgIds = new ArrayList();
        if (StringUtil.isNotNull(userId)) {
            // 跟指定用户相关的组织架构ID列表
            UserAuthInfo userInfo = sysOrgCoreService
                    .getOrgsUserAuthInfo(sysOrgCoreService
                            .findByPrimaryKey(userId));
            orgIds = userInfo.getAuthOrgIds();
        } else {
            // 跟当前用户相关的组织架构ID列表
            orgIds = UserUtil.getKMSSUser().getUserAuthInfo().getAuthOrgIds();
        }

        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        hqlInfo.setSelectBlock("kmCalendarAuth.docCreator");
        hqlInfo.setOrderBy("kmCalendarAuth.fdId");
        hqlInfo.setJoinBlock("left join kmCalendarAuth.authReaders auths");
        String whereBlock = HQLUtil.buildLogicIN("auths.fdId", orgIds);
        whereBlock += " and kmCalendarAuth.docCreator.fdIsAvailable=:fdIsAvailable";
        hqlInfo.setParameter("fdIsAvailable", true);
        hqlInfo.setWhereBlock(whereBlock);
        totalPersons = findList(hqlInfo);

        hqlInfo.setJoinBlock("left join kmCalendarAuth.authModifiers auths");
        ArrayUtil.concatTwoList(findList(hqlInfo), totalPersons);

        hqlInfo.setJoinBlock("left join kmCalendarAuth.authEditors auths");
        ArrayUtil.concatTwoList(findList(hqlInfo), totalPersons);

        // totalPersons 增加单独共享人员
        // 即用户A新建日程后，将用户B添加到日程的可阅读者域，但B不在A的共享权限里面
        List<SysOrgElement> partSharePersons = new ArrayList<SysOrgElement>();
        List<KmCalendarAuthList> authList = kmCalendarAuthListService
                .getPartShareAuthList(orgIds);
        if (authList != null && !authList.isEmpty()) {
            for (KmCalendarAuthList kmCalendarAuthList : authList) {
                KmCalendarAuth auth = kmCalendarAuthList.getFdAuth();
                if (auth != null) {
                    partSharePersons.add(auth.getDocCreator());
                }
            }
        }
        ArrayUtil.concatTwoList(partSharePersons, totalPersons);

        // 按拼音排序
        Collections.sort(totalPersons, new Comparator<SysOrgElement>() {
            @Override
            public int compare(SysOrgElement arg0, SysOrgElement arg1) {
                if (StringUtil.isNotNull(arg0.getFdNamePinYin())
                        && StringUtil.isNotNull(arg1.getFdNamePinYin())) {
                    return arg0.getFdNamePinYin().compareTo(
                            arg1.getFdNamePinYin());
                }
                return -1;
            }
        });

        if (totalPersons != null && !totalPersons.isEmpty()) {
            if (StringUtil.isNotNull(personId)) {// 筛选指定人员
                for (SysOrgElement person : totalPersons) {
                    if (personId.indexOf(person.getFdId()) > -1) {
                        persons.add(person);
                    }
                }
            } else if (loadAll) {
                persons = totalPersons;
            } else {
                if ("week".equals(operType) || MobileUtil.getClientType(requestContext) > -1) {
                    int fromIndex = 0;// 开始index
                    int toIndex = fromIndex + 15 > totalPersons.size() ? totalPersons
                            .size() : fromIndex + 15;// 结束index
                    Iterator iterator = totalPersons.iterator();
                    int index = 0;
                    while (iterator.hasNext()) {
                        SysOrgElement person = (SysOrgElement) iterator.next();
                        if (index >= fromIndex) {
                            persons.add(person);
                        }
                        index++;
                        if (index >= toIndex) {
                            break;
                        }
                    }
                } else {
                    Iterator iterator = totalPersons.iterator();
                    while (iterator.hasNext()) {
                        SysOrgElement person = (SysOrgElement) iterator.next();
                        persons.add(person);
                    }
                }
            }
        }

        Map<String, List> rtnMap = new HashMap<String, List>();
        rtnMap.put("totalPersons", totalPersons);
        rtnMap.put("persons", persons);
        return rtnMap;

    }

    @Override
    public Map<String, List> getDefaultGroupMembers(
            RequestContext requestContext) throws Exception {
        return getDefaultGroupMembers(requestContext, false);
    }

    /**
     * 获取一个用户列表，当前用户拥有其日程的默认阅读权限
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<String[]> getReadAuthPersonList() throws Exception {
        List<String[]> rtnList = new ArrayList();
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        hqlInfo.setJoinBlock("left join kmCalendarAuth.authReaders auths");
        String whereBlock = HQLUtil.buildLogicIN("auths.fdId", UserUtil
                .getKMSSUser().getUserAuthInfo().getAuthOrgIds());
        hqlInfo.setWhereBlock(whereBlock);
        List list = findList(hqlInfo);
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                KmCalendarAuth KmCalendarAuth = (KmCalendarAuth) list.get(i);
                if (KmCalendarAuth.getDocCreator() != null
                        && !UserUtil.getUser().equals(
                        KmCalendarAuth.getDocCreator())) {
                    String[] temp = new String[2];
                    temp[1] = KmCalendarAuth.getDocCreator().getFdName();
                    temp[0] = KmCalendarAuth.getDocCreator().getFdId();
                    rtnList.add(temp);
                }
            }
        }
        // String[] temp = new String[2];
        // temp[1] = UserUtil.getUser().getFdName();
        // temp[0] = UserUtil.getUser().getFdId();
        // rtnList.add(temp);
        return rtnList;

    }

    /**
     * 获取一个用户列表，当前用户可以代其创建日程
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<String[]> getModifyAuthPersonList() throws Exception {
        List<String[]> rtnList = new ArrayList();
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        hqlInfo.setJoinBlock("left join kmCalendarAuth.authModifiers auths");
        String whereBlock = HQLUtil.buildLogicIN("auths.fdId", UserUtil
                .getKMSSUser().getUserAuthInfo().getAuthOrgIds());
        hqlInfo.setWhereBlock(whereBlock);
        List list = findList(hqlInfo);
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                KmCalendarAuth KmCalendarAuth = (KmCalendarAuth) list.get(i);
                if (KmCalendarAuth.getDocCreator() != null
                        && !UserUtil.getUser().equals(
                        KmCalendarAuth.getDocCreator())) {
                    String[] temp = new String[2];
                    temp[1] = KmCalendarAuth.getDocCreator().getFdName();
                    temp[0] = KmCalendarAuth.getDocCreator().getFdId();
                    rtnList.add(temp);
                }
            }
        }
        // String[] temp = new String[2];
        // temp[1] = UserUtil.getUser().getFdName();
        // temp[0] = UserUtil.getUser().getFdId();
        // rtnList.add(temp);
        return rtnList;

    }

    /**
     * 获取一个用户列表，当前用户可以代其创建日程
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<String[]> getCreateAuthPersonList() throws Exception {
        List<String[]> rtnList = new ArrayList();
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setDistinctType(HQLInfo.DISTINCT_YES);
        hqlInfo.setJoinBlock("left join kmCalendarAuth.authEditors auths");
        String whereBlock = HQLUtil.buildLogicIN("auths.fdId", UserUtil
                .getKMSSUser().getUserAuthInfo().getAuthOrgIds());
        hqlInfo.setWhereBlock(whereBlock);
        List list = findList(hqlInfo);
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                KmCalendarAuth KmCalendarAuth = (KmCalendarAuth) list.get(i);
                if (KmCalendarAuth.getDocCreator() != null
                        && KmCalendarAuth.getDocCreator().getFdIsAvailable() != false
                        && !UserUtil.getUser().equals(
                        KmCalendarAuth.getDocCreator())) {
                    String[] temp = new String[2];
                    temp[1] = KmCalendarAuth.getDocCreator().getFdName();
                    temp[0] = KmCalendarAuth.getDocCreator().getFdId();
                    rtnList.add(temp);
                }
            }
        }
        // String[] temp = new String[2];
        // temp[1] = UserUtil.getUser().getFdName();
        // temp[0] = UserUtil.getUser().getFdId();
        // rtnList.add(temp);
        return rtnList;
    }

    @Override
    public Map<String, List<String>> getHierarchyIdsFromReaderAuth(List<String> orgIds) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT ele.fd_id, ele.fd_hierarchy_id FROM km_calendar_auth auth ");
        sqlBuilder.append("INNER JOIN km_calendar_auth_reader reader ON auth.fd_id = reader.fd_calendar_auth_id ");
        sqlBuilder.append("INNER JOIN sys_org_element ele ON reader.auth_reader_id = ele.fd_id ");
        sqlBuilder.append("WHERE " + HQLUtil.buildLogicIN("auth.doc_create_id", orgIds));
        return doSearchHierarchyIds(sqlBuilder);
    }

    private Map<String, List<String>> doSearchHierarchyIds(StringBuilder sqlBuilder) {
        Query query = this.getBaseDao().getHibernateSession().createNativeQuery(sqlBuilder.toString());
        List result = query.list();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        if(result != null){
            result.stream().forEach(record->{
                Object[] columns = (Object[]) record;
                String eleId = (String) columns[0];
                String hierarchyId = (String) columns[1];
                if(!map.containsKey(eleId)){
                    map.put(eleId, new ArrayList<String>());
                }
                map.get(eleId).add(hierarchyId);
            });
        }
        return map;
    }

    @Override
    public Map<String, List<String>> getHierarchyIdsFromEditorAuth(List<String> orgIds) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT ele.fd_id, ele.fd_hierarchy_id FROM km_calendar_auth auth ");
        sqlBuilder.append("INNER JOIN km_calendar_auth_editor editor ON auth.fd_id = editor.fd_calendar_auth_id ");
        sqlBuilder.append("INNER JOIN sys_org_element ele ON editor.auth_editor_id = ele.fd_id ");
        sqlBuilder.append("WHERE " + HQLUtil.buildLogicIN("auth.doc_create_id", orgIds));
        return doSearchHierarchyIds(sqlBuilder);
    }

    @Override
    public Map<String, List<String>> getHierarchyIdsFromModifierAuth(List<String> orgIds) throws Exception {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT ele.fd_id, ele.fd_hierarchy_id FROM km_calendar_auth auth ");
        sqlBuilder.append("INNER JOIN km_calendar_auth_modifier modifier ON auth.fd_id = modifier.fd_calendar_auth_id ");
        sqlBuilder.append("INNER JOIN sys_org_element ele ON modifier.auth_modifier_id = ele.fd_id ");
        sqlBuilder.append("WHERE " + HQLUtil.buildLogicIN("auth.doc_create_id", orgIds));
        return doSearchHierarchyIds(sqlBuilder);
    }

    /**
     * 返回用户日程的所有可创建者
     *
     * @param personId
     * @return
     * @throws Exception
     */
    // public Set<SysOrgPerson> getDocCreators(String personId) throws Exception
    // {
    // List list = findList("kmCalendarAuth.docCreator.fdId = '" + personId
    // + "'", null);
    // Set<SysOrgPerson> personSet = new HashSet<SysOrgPerson>();
    //
    // if (list != null && !list.isEmpty()) {
    // KmCalendarAuth auth = (KmCalendarAuth) list.get(0);
    // List<SysOrgElement> editors = auth.getAuthEditors();
    // for (SysOrgElement editor : editors) {
    // if (editor instanceof SysOrgPerson) {
    // personSet.add((SysOrgPerson) editor);
    // } else {
    // personSet.addAll(sysOrgCoreService.findAllChildren(editor,
    // SysOrgConstant.ORG_TYPE_PERSON));
    // }
    // }
    // return personSet;
    // } else {
    // return null;
    // }
    // }
    //
    @Override
    public KmCalendarAuth findByPerson(String personId) throws Exception {
        HQLInfo info = new HQLInfo();
        info.setWhereBlock("kmCalendarAuth.docCreator.fdId=:personId");
        info.setParameter("personId", personId);
        List<KmCalendarAuth> list = findList(info);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<KmCalendarAuth> findUserCalendarAuth(List orgIds)
            throws Exception {
        if (orgIds == null || orgIds.isEmpty()) {
            return null;
        }
        HQLInfo info = new HQLInfo();
        info.setWhereBlock(
                HQLUtil.buildLogicIN("kmCalendarAuth.docCreator.fdId", orgIds));
        List<KmCalendarAuth> list = findList(info);
        return list;
    }

    @Override
    public void updateAuthByPerson(KmCalendarAuth auth, SysOrgElement person,
                                   RequestContext requestContext) throws Exception {
        String isAuthRead = requestContext.getParameter("authRead");
        String isAuthEdit = requestContext.getParameter("authEdit");
        String isAuthModify = requestContext.getParameter("authModify");
        String requestAuth = "";

        if ("true".equals(isAuthRead) || "true".equals(isAuthEdit)
                || "true".equals(isAuthModify)) {
            List oldAuthEditors = auth.getAuthEditors();
            List oldAuthReaders = auth.getAuthReaders();
            List oldAuthModifiers = auth.getAuthModifiers();

            KmCalendarAuthList authList = new KmCalendarAuthList();
            authList.setFdIsPartShare(false);
            List<SysOrgElement> fdPerson = new ArrayList<SysOrgElement>();
            fdPerson.add(person);
            authList.setFdPerson(fdPerson);
            if ("true".equals(isAuthRead)) {
                authList.setFdIsRead(true);
                requestAuth = "authRead";
                if (!auth.getAuthReaders().contains(person)) {
                    auth.getAuthReaders().add(person);
                }
            }
            if ("true".equals(isAuthEdit)) {
                authList.setFdIsEdit(true);
                requestAuth += ";authEdit";
                if (!auth.getAuthEditors().contains(person)) {
                    auth.getAuthEditors().add(person);
                }
            }
            if ("true".equals(isAuthModify)) {
                authList.setFdIsModify(true);
                requestAuth += ";authModify";
                if (!auth.getAuthModifiers().contains(person)) {
                    auth.getAuthModifiers().add(person);
                }
            }
            if (UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_UPDATE,
                    getModelName())) {
                UserOperContentHelper.putUpdate(auth)
                        .putSimple("authEditors", oldAuthEditors,
                                auth.getAuthEditors())
                        .putSimple("authReaders", oldAuthReaders,
                                auth.getAuthReaders())
                        .putSimple("authModifiers", oldAuthModifiers,
                                auth.getAuthModifiers());
            }

            boolean updateCalendar = StringUtil.isNotNull(requestContext
                    .getParameter("updateCalendar"))
                    && "true".equals(
                    requestContext.getParameter("updateCalendar"));
            String startDate = requestContext.getParameter("startDate");
            Date d = new Date();
            if (StringUtil.isNotNull(startDate)) {
                d = DateUtil.convertStringToDate(startDate, DateUtil.TYPE_DATE,
                        requestContext.getLocale());
            }
            if (updateCalendar) {
                authList.setFdIsShare(true);
                authList.setFdShareDate(d);
            } else {
                authList.setFdIsShare(false);
            }

            List<KmCalendarAuthList> lists = auth
                    .getKmCalendarAuthList();
            if (lists == null) {
                lists = new ArrayList<>();
            }
            lists.add(authList);
            auth.setKmCalendarAuthList(lists);
            update(auth);
            kmCalendarAuthListService.updateCalendarByAddList(auth, authList);
        }

        String fdGroupId = requestContext.getParameter("fdGroupId");
        if (StringUtil.isNotNull(fdGroupId)) {
            // 删除待办
            KmCalendarShareGroup shareGroup = (KmCalendarShareGroup) kmCalendarShareGroupService
                    .findByPrimaryKey(fdGroupId);
            if (shareGroup != null) {
                sysNotifyMainCoreService.getTodoProvider().removePerson(
                        shareGroup, "InviteKey", UserUtil.getUser());
            }
        }
        String fdRequestAuthId = requestContext.getParameter("fdRequestAuthId");
        if (StringUtil.isNotNull(fdRequestAuthId)) {
            // 删除待办
            KmCalendarRequestAuth kmCalendarRequestAuth = (KmCalendarRequestAuth) kmCalendarRequestAuthService
                    .findByPrimaryKey(fdRequestAuthId);
            kmCalendarRequestAuth.setFdRequestAuth(requestAuth); //修改请求权限
            kmCalendarRequestAuthService.update(kmCalendarRequestAuth);
            if (kmCalendarRequestAuth != null) {
                sysNotifyMainCoreService.getTodoProvider().removePerson(
                        kmCalendarRequestAuth, "requestAuthKey",
                        UserUtil.getUser());
            }
        }
    }

    @Override
    public Map<String, String> getAuthStatusByUserId(String userId)
            throws Exception {
        Map<String, String> result = new HashMap<String, String>();
        String docCreatorId = UserUtil.getUser().getFdId();
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("docCreator.fdId =:docCreatorId");
        hqlInfo.setParameter("docCreatorId", docCreatorId);
        List<KmCalendarAuth> kmCalendarAuths = findList(hqlInfo);
        if (kmCalendarAuths.size() > 0) {
            KmCalendarAuth auth = kmCalendarAuths.get(0);
            // 是否可创建日程
            List ids = sysOrgCoreService.expandToPersonIds(auth
                    .getAuthEditors());
            if (ids.contains(userId)) {
                result.put("authEdit", "true");
            } else {
                result.put("authEdit", "false");
            }
            // 是否可编辑日程
            ids = sysOrgCoreService.expandToPersonIds(auth.getAuthModifiers());
            if (ids.contains(userId)) {
                result.put("authModify", "true");
            } else {
                result.put("authModify", "false");
            }
            // 是否可阅读日程
            ids = sysOrgCoreService.expandToPersonIds(auth.getAuthReaders());
            if (ids.contains(userId)) {
                result.put("authRead", "true");
            } else {
                result.put("authRead", "false");
            }
        }
        return result;
    }

    /**
     * 监听组织架构人员的变化，如果变换了部门，则需要把原来所属部门共享权限中删除
     *
     * @param changeEvent
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/11/18 3:26 下午
     */
    @Override
    public void onApplicationEvent(SysOrgElementChangeEvent changeEvent) {

        //只接收人员变动数据
        if (changeEvent != null && changeEvent.getSysOrgElement() != null
                && changeEvent.getSysOrgElement().getFdOrgType() != null
                && SysOrgConstant.ORG_TYPE_PERSON == changeEvent.getSysOrgElement().getFdOrgType()) {
            //如果人员组织未变化,则不执行任何操作
            if (changeEvent.getBeforeHierarchyId().equals(changeEvent.getAfterHierarchyId())) {
                return;
            }

            String[] hierarchyIds = changeEvent.getBeforeHierarchyId().split(BaseTreeConstant.HIERARCHY_ID_SPLIT);
            if (hierarchyIds.length < 3) {
                //说明人员在变化之前不隶属任何部门或机构，则不执行任何操作
                return;
            }
            //找出最后一个属于机构或者部门的组织对象，从后面进行查找，从倒数二个开始查找
            //变更前的部门或机构
            SysOrgElement preSysOrgElement = null;
            try {
                for (int i = hierarchyIds.length - 2; i > 0; i--) {

                    String hierarchyId = hierarchyIds[i];
                    if (StringUtil.isNull(hierarchyId)) {
                        continue;
                    }
                    SysOrgElement sysOrgElement = this.sysOrgCoreService.findByPrimaryKey(hierarchyId, SysOrgElement.class, true);
                    if (sysOrgElement != null &&
                            (SysOrgConstant.ORG_TYPE_ORG == sysOrgElement.getFdOrgType() || SysOrgConstant.ORG_TYPE_DEPT == sysOrgElement.getFdOrgType())) {
                        //如果属于部门或者机构则跳出
                        preSysOrgElement = sysOrgElement;
                        break;
                    }

                }
            } catch (Exception e) {
                logger.error("监听组织变动，查询组织架构出错", e);
            }

            if (preSysOrgElement != null) {
                // 删除可阅读者
                deleteReader(preSysOrgElement, changeEvent.getSysOrgElement());
                // 删除可创建者
                deleteEditor(preSysOrgElement, changeEvent.getSysOrgElement());
                // 删除可编辑者
                deleteModifier(preSysOrgElement, changeEvent.getSysOrgElement());
                // 删除个人共享日程
                deleteAuthListPerson(preSysOrgElement, changeEvent.getSysOrgElement());
            }
        }
    }

    /**
     * 删除可阅读者信息
     *
     * @param preDeptSysOrgElement
     * @param sysOrgPerson
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/11/25 3:29 下午
     */
    private void deleteReader(SysOrgElement preDeptSysOrgElement, SysOrgElement sysOrgPerson) {
        //先通过sql把authReaders表中的fd_calendar_auth_id查询出来，查询条件auth_read_id
        //查询出所有关于之前所在部门的共享数据
        String authReadersSql = "select fd_calendar_auth_id from km_calendar_auth_reader where auth_reader_id='" + preDeptSysOrgElement.getFdId() + "'";
        NativeQuery query = this.getBaseDao().getHibernateSession().createNativeQuery(authReadersSql);
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        query.setCacheRegion("km-calendar");
        query.addScalar("fd_calendar_auth_id", StandardBasicTypes.STRING);
        List calendarAuthIds = query.list();
        if (CollectionUtils.isEmpty(calendarAuthIds)) {
            //属于部门只有单个用户的情况
            return;
        }
        //根据部门共享authId查询出部门所有日程共享权限数据
        HQLInfo hqlInfo = new HQLInfo();
        String whereBlock = HQLUtil.buildLogicIN("kmCalendarAuth.fdId", calendarAuthIds);
        hqlInfo.setWhereBlock(whereBlock);
        try {
            List<KmCalendarAuth> list = findList(hqlInfo);
            if (CollectionUtils.isEmpty(list)) {
                //未查询到共享日程信息
                return;
            }
            for (KmCalendarAuth kmCalendarAuth : list) {
                //删除多对多的关联表
                if (kmCalendarAuth.getDocCreator().getFdId().equals(sysOrgPerson.getFdId())) {
                    String deleteSql = "delete from km_calendar_auth_reader where fd_calendar_auth_id='" + kmCalendarAuth.getFdId() + "' and auth_reader_id='" + preDeptSysOrgElement.getFdId() + "'";
                    NativeQuery query1 = this.getBaseDao().getHibernateSession().createNativeQuery(deleteSql);
                    query1.addSynchronizedQuerySpace("km_calendar_auth_reader");
                    query1.executeUpdate();
                }
            }
        } catch (Exception e) {
            logger.error("监听组织变动，删除日程可阅读者出现错误", e);
        }
    }

    /**
     * 删除可创建者信息
     *
     * @param preDeptSysOrgElement
     * @param sysOrgPerson
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/11/25 3:31 下午
     */
    private void deleteEditor(SysOrgElement preDeptSysOrgElement, SysOrgElement sysOrgPerson) {
        //先通过sql把authEditor表中的fd_calendar_auth_id查询出来，查询条件auth_editor_id
        //查询出所有关于之前所在部门的共享数据
        String authEditorsSql = "select fd_calendar_auth_id from km_calendar_auth_editor where auth_editor_id='" + preDeptSysOrgElement.getFdId() +"'";
        NativeQuery query = this.getBaseDao().getHibernateSession().createNativeQuery(authEditorsSql);
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        query.setCacheRegion("km-calendar");
        query.addScalar("fd_calendar_auth_id", StandardBasicTypes.STRING);
        List calendarAuthIds = query.list();
        if (CollectionUtils.isEmpty(calendarAuthIds)) {
            //属于部门只有单个用户的情况
            return;
        }
        //根据部门共享authId查询出部门所有日程共享权限数据
        HQLInfo hqlInfo = new HQLInfo();
        String whereBlock = HQLUtil.buildLogicIN("kmCalendarAuth.fdId", calendarAuthIds);
        hqlInfo.setWhereBlock(whereBlock);
        try {
            List<KmCalendarAuth> list = findList(hqlInfo);
            if (CollectionUtils.isEmpty(list)) {
                //未查询到共享日程信息
                return;
            }
            for (KmCalendarAuth kmCalendarAuth : list) {
                //删除多对多的关联表
                if (kmCalendarAuth.getDocCreator().getFdId().equals(sysOrgPerson.getFdId())) {
                    String deleteSql = "delete from km_calendar_auth_editor where fd_calendar_auth_id='" + kmCalendarAuth.getFdId() + "' and auth_editor_id='" + preDeptSysOrgElement.getFdId() + "'";
                    NativeQuery query1 = this.getBaseDao().getHibernateSession().createNativeQuery(deleteSql);
                    query1.addSynchronizedQuerySpace("km_calendar_auth_editor");
                    query1.executeUpdate();
                }
            }
        } catch (Exception e) {
            logger.error("监听组织变动，删除日程可创建者出现错误", e);
        }
    }

    /**
     * 删除可编辑者信息
     *
     * @param preDeptSysOrgElement
     * @param sysOrgPerson
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/11/25 3:32 下午
     */
    private void deleteModifier(SysOrgElement preDeptSysOrgElement, SysOrgElement sysOrgPerson) {
        //先通过sql把authModifier表中的fd_calendar_auth_id查询出来，查询条件auth_modifier_id
        //查询出所有关于之前所在部门的共享数据
        String authModifiersSql = "select fd_calendar_auth_id from km_calendar_auth_modifier where auth_modifier_id='" + preDeptSysOrgElement.getFdId() + "'";
        NativeQuery query = this.getBaseDao().getHibernateSession().createNativeQuery(authModifiersSql);
        query.setCacheable(true);
        query.setCacheMode(CacheMode.NORMAL);
        query.setCacheRegion("km-calendar");
        query.addScalar("fd_calendar_auth_id", StandardBasicTypes.STRING);
        List calendarAuthIds = query.list();
        if (CollectionUtils.isEmpty(calendarAuthIds)) {
            //属于部门只有单个用户的情况
            return;
        }
        //根据部门共享authId查询出部门所有日程共享权限数据
        HQLInfo hqlInfo = new HQLInfo();
        String whereBlock = HQLUtil.buildLogicIN("kmCalendarAuth.fdId", calendarAuthIds);
        hqlInfo.setWhereBlock(whereBlock);
        try {
            List<KmCalendarAuth> list = findList(hqlInfo);
            if (CollectionUtils.isEmpty(list)) {
                //未查询到共享日程信息
                return;
            }
            for (KmCalendarAuth kmCalendarAuth : list) {
                //删除多对多的关联表
                if (kmCalendarAuth.getDocCreator().getFdId().equals(sysOrgPerson.getFdId())) {
                    String deleteSql = "delete from km_calendar_auth_modifier where fd_calendar_auth_id='" + kmCalendarAuth.getFdId() + "' and auth_modifier_id='" + preDeptSysOrgElement.getFdId() + "'";;
                    NativeQuery query1 = this.getBaseDao().getHibernateSession().createNativeQuery(deleteSql);
                    query1.addSynchronizedQuerySpace("km_calendar_auth_modifier");
                    query1.executeUpdate();
                }
            }
        } catch (Exception e) {
            logger.error("监听组织变动，删除日程可编辑者出现错误", e);
        }
    }

    /**
     * 删除个人共享权限
     *
     * @param preDeptSysOrgElement
     * @param sysOrgPerson
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/11/25 4:23 下午
     */
    private void deleteAuthListPerson(SysOrgElement preDeptSysOrgElement, SysOrgElement sysOrgPerson) {

        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("kmCalendarAuth.docCreator.fdId=:docCreatorFdId");
        hqlInfo.setParameter("docCreatorFdId", sysOrgPerson.getFdId());
        try {
            //获取所有日程
            List<KmCalendarAuth> auths = findList(hqlInfo);
            if (CollectionUtils.isEmpty(auths)) {
                return;
            }
            for (KmCalendarAuth auth : auths) {
                if (CollectionUtils.isEmpty(auth.getKmCalendarAuthList())) {
                    continue;
                }
                for (KmCalendarAuthList kmCalendarAuthList : auth.getKmCalendarAuthList()) {

                    // 查询是否存在数据
                    String deleteSql = "delete from km_calendar_auth_list_person where fd_source_id='" + kmCalendarAuthList.getFdId() + "' and fd_target_id='" + preDeptSysOrgElement.getFdId() + "'";
                    NativeQuery query = this.getBaseDao().getHibernateSession().createNativeQuery(deleteSql);
                    query.addSynchronizedQuerySpace("km_calendar_auth_list_person");
                    int count = query.executeUpdate();
                    // 如果存在数据 count>0代表删除了数据 则需要删除kmCalendarAuthList中的当前记录
                    if (count > 0) {
                        String deleteListSql = "delete from km_calendar_auth_list where fd_id='" + kmCalendarAuthList.getFdId() + "'";
                        NativeQuery query1 = this.getBaseDao().getHibernateSession().createNativeQuery(deleteListSql);
                        query1.addSynchronizedQuerySpace("km_calendar_auth_list");
                        query1.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("监听组织变动，删除个人共享权限出现错误", e);
        }


    }

    @Override
    public List getDataList(RequestContext requestInfo) throws Exception {
        String fdName = requestInfo.getParameter("fdName");
        List<Object[]> peopleList = new ArrayList<Object[]>();
        List<Object[]> searchList = new ArrayList<Object[]>();
        List<String[]> list = getCreateAuthPersonList();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String[] person = list.get(i);
                if (person == null) {
                    continue;
                }
                if (StringUtil.isNotNull(fdName)) {
                    if (person.length > 1 && person[1].indexOf(fdName) != -1) {
                        searchList.add(person);
                    }
                } else {
                    peopleList.add(person);
                }
            }
        }

        if (StringUtil.isNotNull(fdName)) {
            return searchList;
        }
        return peopleList;
    }

    private static final String CMS_POINT = "com.landray.kmss.km.calendar.cms";
    private static final String CMS_POINT_AUTH_ITEM = "syncOutAuth";
    private static IExtension[] extensions = null;

    class SyncOutThread extends Thread {

        private String authId;

        public SyncOutThread(String authId) {
            this.authId = authId;
        }

        @Override
        public void run() {
            if (extensions == null) {
                extensions = Plugin.getExtensions(CMS_POINT, "*", CMS_POINT_AUTH_ITEM);
            }
            if (extensions != null && extensions.length > 0) {
                for (IExtension extension : extensions) {
                    ISyncOutAuthProvider provider = (ISyncOutAuthProvider) Plugin.getParamValue(extension, "provider");
                    if (provider.isNeedSyncro(authId)) {
                        provider.sync(authId);
                    }
                }
            }
        }

    }


}

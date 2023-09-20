package com.landray.kmss.sys.attachment.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.model.SysAttMainBak;
import com.landray.kmss.sys.attachment.model.SysAttMainHistoryConfig;
import com.landray.kmss.sys.attachment.plugin.HistoryVersionPlugin;
import com.landray.kmss.sys.attachment.plugin.HistoryVersionPluginData;
import com.landray.kmss.sys.attachment.service.ISysAttClearService;
import com.landray.kmss.sys.attachment.service.ISysAttMainBakService;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttCatalog;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysAttFileBak;
import com.landray.kmss.sys.filestore.service.ISysAttFileBakService;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.sunbor.web.tag.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.TransactionStatus;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;

public class SysAttClearServiceImpl implements ISysAttClearService {
    protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttClearServiceImpl.class);

    public static final long gap = 1000*60;

    private ISysAttMainCoreInnerService sysAttMainService;

    private ISysAttMainBakService sysAttMainBakService;

    private ISysAttFileBakService sysAttFileBakService;

    protected ISysAttUploadService sysAttUploadService;

    public void setSysAttMainService(ISysAttMainCoreInnerService sysAttMainService) {
        this.sysAttMainService = sysAttMainService;
    }

    public void setSysAttMainBakService(ISysAttMainBakService sysAttMainBakService) {
        this.sysAttMainBakService = sysAttMainBakService;
    }

    public void setSysAttFileBakService(ISysAttFileBakService sysAttFileBakService) {
        this.sysAttFileBakService = sysAttFileBakService;
    }

    public void setSysAttUploadService(ISysAttUploadService sysAttUploadService) {
        this.sysAttUploadService = sysAttUploadService;
    }

    public String getDestRoot() throws Exception {
        String destRoot = null;
        SysAttMainHistoryConfig config = new SysAttMainHistoryConfig();
        String rootType = config.getRootType();
        if ("0".equals(rootType)) {
            destRoot = ResourceUtil.getKmssConfigString("kmss.resource.path").replaceAll("\\\\","/");
            if (!destRoot.endsWith("/")) {
                destRoot += "/";
            }
            destRoot += "temphistoryatt";
        }else{
            destRoot = config.getRootPath().replaceAll("\\\\","/");
            if (destRoot.endsWith("/")) {
                destRoot = destRoot.substring(0, destRoot.length() - 1);
            }
            File temp = new File(destRoot);
            try {
                String path = temp.getCanonicalPath().replaceAll("\\\\","/");
                if (!StringUtils.equals(path, destRoot)) {
                    throw new RuntimeException(config.getRootPath() + " 不是有效的绝对路径，识别为 " + path);
                }
            } catch (IOException e) {
                throw new RuntimeException(config.getRootPath() + " 不是有效的绝对路径");
            }
        }
        File dir = new File(destRoot);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException(destRoot + " 不是正常路径或没有读写权限");
            }
        }
        return destRoot;
    }

    private SysQuartzJobContext context;

    @Override
    public void moveHisAttachments(SysQuartzJobContext context) {
        this.context = context;
        context.logMessage("定时移出附件历史版本开始");
        long start = System.currentTimeMillis();
        try {
            SysAttMainHistoryConfig config = new SysAttMainHistoryConfig();
            if (!"true".equals(config.getAttClearEnable())) {
                context.logMessage("定时移出附件历史版本中止，未开启开关");
                return;
            }
            String key = SysFileLocationUtil.getKey(null);
            if (!"server".equals(key)) {
                context.logMessage("定时移出附件历史版本中止，附件存储类型是云存储");
                return;
            }
            try {
                getDestRoot();
            } catch (Exception e) {
                context.logMessage("定时移出附件历史版本中止，" + e.getMessage());
                return;
            }
            //保留天数
            Integer days = Integer.valueOf(config.getAttKeepDays());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
            calendar.add(Calendar.DAY_OF_YEAR,days);
            //移出的时间
            Date movingTime = calendar.getTime();
            //获取数据量
            Integer count = Integer.valueOf(config.getAttClearCount());
            List<SysAttMain> toDelList2 = new ArrayList<>();
            List<SysAttMain> toMoveList2 = new ArrayList<>();
            long s = System.currentTimeMillis();
            //查询没有启用版本管理的附件
            getToMove2(count, toDelList2, toMoveList2);
            context.logMessage("查询没有启用版本管理的附件，cost = " + (System.currentTimeMillis() - s));
            //待移出的主文档记录移出时间，备份到bak表
            s = System.currentTimeMillis();
            addBak(toDelList2, movingTime);
            context.logMessage("待移出的主文档备份到bak表，size=" + toDelList2.size() + "，cost = " + (System.currentTimeMillis() - s));
            //查询bak表当前可以移出的主文档记录
            s = System.currentTimeMillis();
            List<SysAttMainBak> sysAttMainBaks = selectBakToMoving();
            if (CollectionUtils.isEmpty(sysAttMainBaks)) {
                sysAttMainBaks = Collections.EMPTY_LIST;
            }
            context.logMessage("查询bak表当前可以移出的主文档记录，size="+sysAttMainBaks.size()+"，cost = " + (System.currentTimeMillis() - s));
            if (!CollectionUtils.isEmpty(sysAttMainBaks)) {
                s = System.currentTimeMillis();
                for (SysAttMainBak bak : sysAttMainBaks) {
                    //移动处理
                    move(bak);
                }
                context.logMessage("移动处理完成，cost = " + (System.currentTimeMillis() - s));
            }
        } catch (Exception e) {
            context.logError("定时移出附件历史版本执行异常", e);
        }
        context.logMessage("定时移出附件历史版本结束，cost = " + (System.currentTimeMillis() - start));
    }

    /**
     * 待移出的主文档记录添加移出时间，备份到bak表
     * @param list
     * @param movingTime
     * @throws Exception
     */
    private void addBak(List<SysAttMain> list,Date movingTime) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (SysAttMain attMain : list) {
            SysAttMainBak bak = new SysAttMainBak();
            BeanUtils.copyProperties(attMain,bak);
            bak.setFdMovingTime(movingTime);
            sysAttMainBakService.getBaseDao().add(bak);
            sysAttMainService.getSysAttMainDao().delete(attMain);
        }
    }

    /**
     * 查询bak表可以移出的主文档记录
     * @return
     * @throws Exception
     */
    private List<SysAttMainBak> selectBakToMoving() throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        String where = " fdMovingStatus=:fdMovingStatus and fdMovingTime<:fdMovingTime";
        hqlInfo.setWhereBlock(where);
        hqlInfo.setParameter("fdMovingStatus",0);
        hqlInfo.setParameter("fdMovingTime",new Date());
        hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
        hqlInfo.setOrderBy("docCreateTime");
        return sysAttMainBakService.getBaseDao().findList(hqlInfo);
    }

    private String getRoot(SysAttCatalog sysAttCatalog) {
        String root = ResourceUtil.getKmssConfigString("kmss.resource.path").replaceAll("\\\\","/");
        if (sysAttCatalog != null) {
            root = sysAttCatalog.getFdPath().replaceAll("\\\\","/");
        }
        if (root.endsWith("/")) {
            root = root.substring(0, root.length() - 1);
        }
        return root;
    }

    /**
     * 附件移到临时目录，相关的convert和bak文件也移动
     * @param bak
     */
    private void move(SysAttMainBak bak) throws Exception {
        SysAttFile attFile = sysAttUploadService.getFileById(bak.getFdFileId());
        if (attFile == null) {
            //主文档有记录，关联的文件在att_file表不存在，此场景忽略
            bak.setFdMovingStatus(2);
            sysAttMainBakService.getBaseDao().update(bak);
            context.logMessage("fdId = " + bak.getFdId() + " 关联的文件在att_file表不存在");
        }else{
            String root = getRoot(attFile.getFdCata());
            String filePath = root + attFile.getFdFilePath();
            File file = new File(filePath);
            if (!file.exists()) {
                bak.setFdMovingStatus(3);
                sysAttMainBakService.getBaseDao().update(bak);
                context.logMessage("fdId = " + bak.getFdId() + " 的物理文件 " + filePath + " 不存在");
            }else{
                String destRoot = getDestRoot();
                String destPath = destRoot + attFile.getFdFilePath();
                File destFile = new File(destPath);
                if (destFile.exists()) {
                    destFile.delete();
                }
                FileUtils.moveFile(file, destFile);

                String parentPath = file.getParentFile().getCanonicalPath();
                File convert = new File(parentPath, bak.getFdFileId() + "_convert");
                if (convert.exists()) {
                    File destConvert = new File(destFile.getParentFile().getCanonicalPath(), bak.getFdFileId() + "_convert");
                    if (!destConvert.exists()) {
                        destConvert.mkdirs();
                    }
                    FileUtils.copyDirectory(convert, destConvert);
                    FileUtils.deleteQuietly(convert);
                }
                File bakFile = new File(parentPath, bak.getFdFileId() + "_bak");
                if (bakFile.exists()) {
                    File destBak = new File(destFile.getParentFile().getCanonicalPath(), bak.getFdFileId() + "_bak");
                    if (destBak.exists()) {
                        destBak.delete();
                    }
                    FileUtils.moveFile(bakFile, destBak);
                }
                //文件移动完成，更新bak表记录，删除主文档的记录
                bak.setFdMovingStatus(1);
                sysAttMainBakService.getBaseDao().update(bak);
                SysAttFileBak fileBak = new SysAttFileBak();
                BeanUtils.copyProperties(attFile, fileBak);
                sysAttFileBakService.getBaseDao().add(fileBak);
                sysAttUploadService.deleteRecord(attFile.getFdId());
            }
        }
    }

    /**
     * 查出没有挂载主文档的附件
     * @param count
     * @param list
     * @param toMoveList
     */
    private void getToMove1(Integer count,List<SysAttMain> list,List<SysAttMain> toMoveList) throws Exception {
        String whereBlock = " fdModelId is null ";
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setPageNo(1);
        hqlInfo.setRowSize(count);
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,SysAuthConstant.AllCheck.NO);
        hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setOrderBy("docCreateTime asc");
        Page page = sysAttMainService.findPage(hqlInfo);
        list.addAll(page.getList());
        //待迁移附件数组
        for (SysAttMain att : list) {
            String where = " fdModelId is not null and fdFileId = :fdFileId";
            HQLInfo hql = new HQLInfo();
            hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,SysAuthConstant.AllCheck.NO);
            hql.setGetCount(true);
            hql.setWhereBlock(where);
            hql.setParameter("fdFileId", att.getFdFileId());
            Page p = sysAttMainService.findPage(hql);
            if (p.getTotalrows() < 1) {
                toMoveList.add(att);
            }
        }

    }

    private void getToMove2(Integer count,List<SysAttMain> list,List<SysAttMain> toMoveList) throws Exception {
        //启用版本管理的模块
        List<String> exModels = new ArrayList<>();
        int len = 0;
        if ("true".equals(SysAttMainHistoryConfig.newInstance().getAttHistoryEnable())) {
            Map<String, HistoryVersionPluginData> enabledModules = HistoryVersionPlugin.getEnabledModules();
            if (enabledModules != null) {
                for (String m : enabledModules.keySet()) {
                    exModels.add(m);
                }
                len = exModels.size();
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(" fdKey='historyVersionAttachment' and fdModelId is not null ");
        if (CollectionUtils.isNotEmpty(exModels)) {
            sb.append(" and fdModelName not in (");
            for (String model : exModels) {
                sb.append("'").append(model).append("'");
                if (--len != 0) {
                    sb.append(",");
                }
            }
            sb.append(")");
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setRowSize(count);
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,SysAuthConstant.AllCheck.NO);
        hqlInfo.setWhereBlock(sb.toString());
        hqlInfo.setOrderBy("docCreateTime");
        Page page = sysAttMainService.findPage(hqlInfo);
        list.addAll(page.getList());
        //待迁移附件数组
        toMoveList.addAll(page.getList());
    }

    @Override
    public void delHisAttachments() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
//        c.add(Calendar.MONTH, -KEEP_MONTH);
//        File dir = getDir();
//        for (int i = 0; i < DEL_HIS_ATT_MONTH; i++) {
//            c.add(Calendar.MONTH, -1);
//            File f = new File(dir,c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1));
//            FileUtils.deleteQuietly(f);
//        }
    }

    public void delWpsCenterHisAttachments(SysQuartzJobContext context) {

        TransactionStatus status = null;

        try {
            if(!SysAttWpsCenterUtil.isEnable()) {
                return;
            }

            status = TransactionUtils.beginNewTransaction();

            HQLInfo hqlInfo = new HQLInfo();
            //批处理500条数据
            hqlInfo.setRowSize(500);
            hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
            hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
                    SysAuthConstant.AreaCheck.NO);
            hqlInfo.setWhereBlock("sysAttMain.fdKey = :fdKey");
            hqlInfo.setParameter("fdKey", SysAttBase.WPS_CENTER_TEMP_NAME);

            Page page = sysAttMainService.findPage(hqlInfo);
            List<SysAttMain> attList = page.getList();

            for(SysAttMain att : attList) {
                if (StringUtil.isNotNull(att.getFdFileId())) {

                    //先判断有没公用同一个sysAttFile的数据，有则不删除，只删记录。
                    if(hasMoreAttMain(att)) {
                        try {
                            sysAttMainService.delete(att.getFdId());
                        }catch(Exception e) {
                            logger.error("删除记录失败：" + att.getFdId(),e);
                        }
                        continue;
                    }

                    SysAttFile attFile = sysAttUploadService.getFileById(att.getFdFileId());
                    if(attFile !=null) {
                        Boolean delData = true;
                        ISysFileLocationProxyService sysFileLocationService = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation());

                        try {
                            sysFileLocationService.deleteFile(attFile.getFdFilePath());
                        }catch(Exception e) {
                            logger.error("删除文件失败："+att.getFdFileId(),e);
                            delData = false;
                        }

                        try {
                            sysFileLocationService.deleteDirectory(attFile.getFdFilePath()+"_convert");
                        }catch(Exception e) {
                            logger.error("删除转换目录失败："+att.getFdFileId()+"_convert",e);
                            delData = false;
                        }

                        if(delData) {
                            try {
                                sysAttUploadService.deleteRecord(att.getFdFileId());
                                sysAttMainService.delete(att.getFdId());
                            }catch(Exception e) {
                                logger.error("删除记录失败："+att.getFdFileId()+" - " + att.getFdId(),e);
                            }
                        }
                    }
                }

            }

            if(page.getTotal()>1){
                for(int o=2;o<=page.getTotal();o++){
                    hqlInfo.setPageNo(o);
                    Page pageNext = sysAttMainService.findPage(hqlInfo);
                    List<SysAttMain> attNextList = pageNext.getList();

                    for (SysAttMain att : attNextList) {
                        if (StringUtil.isNotNull(att.getFdFileId())) {

                            //先判断有没公用同一个sysAttFile的数据，有则不删除，只删记录。
                            if(hasMoreAttMain(att)) {
                                try {
                                    sysAttMainService.delete(att.getFdId());
                                }catch(Exception e) {
                                    logger.error("删除记录失败：" + att.getFdId(),e);
                                }
                                continue;
                            }

                            SysAttFile attFile = sysAttUploadService.getFileById(att.getFdFileId());
                            if(attFile !=null) {
                                Boolean delData = true;
                                ISysFileLocationProxyService sysFileLocationService = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation());

                                try {
                                    sysFileLocationService.deleteFile(attFile.getFdFilePath());
                                }catch(Exception e) {
                                    logger.error("删除文件失败："+att.getFdFileId(),e);
                                    delData = false;
                                }

                                try {
                                    sysFileLocationService.deleteDirectory(attFile.getFdFilePath()+"_convert");
                                }catch(Exception e) {
                                    logger.error("删除转换目录失败："+att.getFdFileId()+"_convert",e);
                                    delData = false;
                                }

                                if(delData) {
                                    try {
                                        sysAttUploadService.deleteRecord(att.getFdFileId());
                                        sysAttMainService.delete(att.getFdId());
                                    }catch(Exception e) {
                                        logger.error("删除记录失败："+att.getFdFileId()+" - " + att.getFdId(),e);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            TransactionUtils.commit(status);
        } catch (Exception e) {
            logger.error("delWpsCenterHisAttachments异常: ",e);
            if (status != null) {
                TransactionUtils.rollback(status);
            }
        }

        //20211122 发现会删除同时上传的多个附件，而且中台文档没有改动，只会保存一次，所以暂时注释
//        TransactionStatus transactionStatus = null;
//        try {
//            transactionStatus = TransactionUtils.beginNewTransaction();
//            delWpsCenterAutoSaveFiles();
//            TransactionUtils.commit(transactionStatus);
//        } catch (Exception e) {
//            logger.error("delWpsCenterAutoSaveFiles异常: ",e);
//            if (status != null)
//                TransactionUtils.rollback(transactionStatus);
//        }
    }

    public void delWpsCenterAutoSaveFiles() {
        CacheConfig config = CacheConfig.get(SysAttMainCoreInnerServiceImp.class);
        KmssCache cache = new KmssCache(SysAttMainCoreInnerServiceImp.class,config.setCacheType(3));
        Object m = cache.get(SysAttBase.wpsCenterCacheKey);
        if(m == null) {
            return;
        }
        ConcurrentHashMap<String,String> map = (ConcurrentHashMap<String,String>)m;
        Set<Entry<String,String>> set = map.entrySet();
        Iterator<Entry<String,String>> it = set.iterator();
        while(it.hasNext()) {
            Entry<String,String> en = it.next();
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
            hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck, SysAuthConstant.AreaCheck.NO);
            String whereBlock = "fdModelId=:fdModelId and fdModelName=:fdModelName and fdKey not in ('editonline','clearAtt','finalAtt','modifyAtt','originalAtt','redheadAtt','revisionAtt','revisionNodeAtt')";
            hqlInfo.setWhereBlock(whereBlock);
            hqlInfo.setParameter("fdModelId", en.getKey());
            hqlInfo.setParameter("fdModelName", en.getValue());
            hqlInfo.setOrderBy("fdUploadTime desc");
            try {
                List<SysAttMain> list = sysAttMainService.findList(hqlInfo);
                doDel(list);
            } catch (Exception e) {
                logger.error("doDel异常: ",e);
            }
        }
        //删除缓存
        boolean stop = false;
        while(!stop) {
            Object o = cache.get(SysAttBase.wpsCenterLockKey);
            if(o == null) {
                cache.put(SysAttBase.wpsCenterLockKey, SysAttBase.wpsCenterLockKey);
                try {
                    Object newm = cache.get(SysAttBase.wpsCenterCacheKey);
                    if(newm == null) {
                        break;
                    }
                    ConcurrentHashMap newmap = (ConcurrentHashMap)newm;
                    Iterator<String> keys = map.keySet().iterator();
                    while(keys.hasNext()) {
                        newmap.remove(keys.next());
                    }
                    cache.put(SysAttBase.wpsCenterCacheKey, newmap);
                }finally {
                    cache.remove(SysAttBase.wpsCenterLockKey);
                    stop = true;
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {

            }
        }
    }

    private void doDel(List<SysAttMain> list) {
        if(CollectionUtils.isEmpty(list)) {
            return;
        }
        //按照提交人分组
        Map<String,List<SysAttMain>> map = new HashMap<String,List<SysAttMain>>();
        for(SysAttMain attMain : list) {
            List<SysAttMain> data = map.get(attMain.getFdUploaderId());
            if(data == null) {
                data = new ArrayList<SysAttMain>();
                map.put(attMain.getFdUploaderId(), data);
            }
            data.add(attMain);
        }
        Iterator<Entry<String,List<SysAttMain>>> it = map.entrySet().iterator();
        while(it.hasNext()) {
            Entry<String,List<SysAttMain>> en = it.next();
            List<SysAttMain> li = en.getValue();
            if(CollectionUtils.isEmpty(li) || li.size()<3) {
                continue;
            }
            Collections.sort(li, new Comparator<SysAttMain>() {
                @Override
                public int compare(SysAttMain arg0, SysAttMain arg1) {
                    long c = arg0.getFdUploadTime().getTime()-arg1.getFdUploadTime().getTime();
                    if(c > 0) {
                        return -1;
                    }else if(c < 0 ) {
                        return 1;
                    }else {
                        return 0;
                    }
                }});
            int len = li.size()-2;
            if(len == 1) {
                //直接删
                delete(li.get(li.size()-1));
            }else {
                for(int i=li.size()-3;i>0;i--) {
                    SysAttMain m1 = li.get(i);
                    SysAttMain m2 = li.get(i-1);
                    long t1 = m1.getFdUploadTime().getTime();
                    long t2 = m2.getFdUploadTime().getTime();
                    if((t1 - t2) < gap) {
                        //删m1
                        delete(m1);
                    }
                }
                //最后删0
                delete(li.get(li.size()-1));
            }
        }
    }

    private void delete(SysAttMain att) {
        try {
            //主文档不能删，key为editonline
            if("editonline".equals(att.getFdKey().trim())) {
                return;
            }
            if(StringUtil.isNotNull(att.getFdFileId())) {
                //先判断有没公用同一个sysAttFile的数据，有则不删除，只删记录。
                if(hasMoreAttMain(att)) {
                    try {
                        sysAttMainService.delete(att.getFdId());
                    }catch(Exception e) {
                        logger.error("删除记录失败：" + att.getFdId(),e);
                    }
                    return;
                }
            }else {
                return;
            }

            if (StringUtil.isNotNull(att.getFdFileId())) {
                SysAttFile attFile = sysAttUploadService.getFileById(att.getFdFileId());
                if(attFile !=null) {
                    Boolean delData = true;
                    ISysFileLocationProxyService sysFileLocationService = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation());
                    try {
                        sysFileLocationService.deleteFile(attFile.getFdFilePath());
                    }catch(Exception e) {
                        logger.error("删除文件失败："+att.getFdFileId(),e);
                        delData = false;
                    }
                    try {
                        sysFileLocationService.deleteDirectory(attFile.getFdFilePath()+"_convert");
                    }catch(Exception e) {
                        logger.error("删除转换目录失败："+att.getFdFileId()+"_convert",e);
                        delData = false;
                    }
                    if(delData) {
                        try {
                            sysAttUploadService.deleteRecord(att.getFdFileId());
                            sysAttMainService.delete(att.getFdId());
                        }catch(Exception e) {
                            logger.error("删除记录失败："+att.getFdFileId()+" - " + att.getFdId(),e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("delete执行异常，",e);
        }
    }

    private boolean hasMoreAttMain(SysAttMain att) throws Exception {
        //先判断有没公用同一个sysAttFile的数据，有则不删除，只删记录。
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
                SysAuthConstant.AreaCheck.NO);
        hqlInfo.setWhereBlock("sysAttMain.fdId != :fdId and sysAttMain.fdFileId = :fdFileId");
        hqlInfo.setParameter("fdId", att.getFdId());
        hqlInfo.setParameter("fdFileId", att.getFdFileId());

        List<SysAttMain> attList = sysAttMainService.findList(hqlInfo);
        if(attList.size()>0) {
            return true;
        }else {
            return false;
        }
    }

    public void delWPSOAassistEmbedHisAttachments(SysQuartzJobContext context) {

        TransactionStatus status = null;

        try {
            if(!SysAttWpsoaassistUtil.isWPSOAassistEmbed()) {
                return;
            }

            status = TransactionUtils.beginNewTransaction();

            HQLInfo hqlInfo = new HQLInfo();
            //批处理500条数据
            hqlInfo.setRowSize(100);
            hqlInfo.setAuthCheckType(SysAuthConstant.AUTH_CHECK_NONE);
            hqlInfo.setCheckParam(SysAuthConstant.CheckType.AreaCheck,
                    SysAuthConstant.AreaCheck.NO);
            hqlInfo.setWhereBlock("sysAttMain.fdKey = :fdKey and sysAttMain.fdModelId ='' and sysAttMain.fdModelName = '' ");
            hqlInfo.setParameter("fdKey", "tmpWpsOaassist");

            Page page = sysAttMainService.findPage(hqlInfo);
            List<SysAttMain> attList = page.getList();

            for(SysAttMain att : attList) {
                if (StringUtil.isNotNull(att.getFdFileId())) {

                    //先判断有没公用同一个sysAttFile的数据，有则不删除，只删记录。
                    if(hasMoreAttMain(att)) {
                        try {
                            sysAttMainService.delete(att.getFdId());
                        }catch(Exception e) {
                            logger.error("删除记录失败：" + att.getFdId(),e);
                        }
                        continue;
                    }

                    SysAttFile attFile = sysAttUploadService.getFileById(att.getFdFileId());
                    if(attFile !=null) {
                        Boolean delData = true;
                        ISysFileLocationProxyService sysFileLocationService = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation());

                        try {
                            sysFileLocationService.deleteFile(attFile.getFdFilePath());
                        }catch(Exception e) {
                            logger.error("删除文件失败："+att.getFdFileId(),e);
                            delData = false;
                        }

                        try {
                            sysFileLocationService.deleteDirectory(attFile.getFdFilePath()+"_convert");
                        }catch(Exception e) {
                            logger.error("删除转换目录失败："+att.getFdFileId()+"_convert",e);
                            delData = false;
                        }

                        if(delData) {
                            try {
                                sysAttUploadService.deleteRecord(att.getFdFileId());
                                sysAttMainService.delete(att.getFdId());
                            }catch(Exception e) {
                                logger.error("删除记录失败："+att.getFdFileId()+" - " + att.getFdId(),e);
                            }
                        }
                    }
                }

            }

            if(page.getTotal()>1){
                for(int o=2;o<=page.getTotal();o++){
                    hqlInfo.setPageNo(o);
                    Page pageNext = sysAttMainService.findPage(hqlInfo);
                    List<SysAttMain> attNextList = pageNext.getList();

                    for (SysAttMain att : attNextList) {
                        if (StringUtil.isNotNull(att.getFdFileId())) {

                            //先判断有没公用同一个sysAttFile的数据，有则不删除，只删记录。
                            if(hasMoreAttMain(att)) {
                                try {
                                    sysAttMainService.delete(att.getFdId());
                                }catch(Exception e) {
                                    logger.error("删除记录失败：" + att.getFdId(),e);
                                }
                                continue;
                            }

                            SysAttFile attFile = sysAttUploadService.getFileById(att.getFdFileId());
                            if(attFile !=null) {
                                Boolean delData = true;
                                ISysFileLocationProxyService sysFileLocationService = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation());

                                try {
                                    sysFileLocationService.deleteFile(attFile.getFdFilePath());
                                }catch(Exception e) {
                                    logger.error("删除文件失败："+att.getFdFileId(),e);
                                    delData = false;
                                }

                                try {
                                    sysFileLocationService.deleteDirectory(attFile.getFdFilePath()+"_convert");
                                }catch(Exception e) {
                                    logger.error("删除转换目录失败："+att.getFdFileId()+"_convert",e);
                                    delData = false;
                                }

                                if(delData) {
                                    try {
                                        sysAttUploadService.deleteRecord(att.getFdFileId());
                                        sysAttMainService.delete(att.getFdId());
                                    }catch(Exception e) {
                                        logger.error("删除记录失败："+att.getFdFileId()+" - " + att.getFdId(),e);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            TransactionUtils.commit(status);
        } catch (Exception e) {
            logger.error("delWPSOAassistEmbedHisAttachments异常: ",e);
            if (status != null) {
                TransactionUtils.rollback(status);
            }
        }
    }

}

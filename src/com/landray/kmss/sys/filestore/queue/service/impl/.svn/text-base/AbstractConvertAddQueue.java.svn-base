package com.landray.kmss.sys.filestore.queue.service.impl;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.dto.ConvertParameter;
import com.landray.kmss.sys.filestore.queue.service.ConvertAddQueueExtension;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class AbstractConvertAddQueue implements ConvertAddQueueExtension{
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(AbstractConvertAddQueue.class);

    private ISysFileConvertQueueService sysFileConvertQueueService;

    @Override
    public SysFileConvertQueue addQueue(SysAttMain sysAttMain, ConvertParameter convertParams, Map<String, Object> extParams) throws Exception {
        SysFileConvertQueue sysFileConvertQueue = beforeAdd(sysAttMain, convertParams, extParams);
        if (sysFileConvertQueue != null) {
            return sysFileConvertQueue;
        }
        sysFileConvertQueue = handleAdd(sysAttMain, convertParams, extParams);
        sysFileConvertQueue = afterAdd(sysFileConvertQueue, sysAttMain, convertParams, extParams);
        return sysFileConvertQueue;
    }

    protected List<SysFileConvertQueue> exsitsQueue(SysAttMain sysAttMain, ConvertParameter convertParams, Map<String, Object> extParams) throws Exception {
        List<SysFileConvertQueue> result = new ArrayList<>();
        String whereBlock = " fdConverterKey=:fdConverterKey ";
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setGetCount(false);
        if (StringUtil.isNotNull(convertParams.getConverterType())) {
            whereBlock += " and fdConverterType=:fdConverterType";
            hqlInfo.setParameter("fdConverterType", convertParams.getConverterType());
        }
        whereBlock += " and fdAttMainId=:fdAttMainId";
        hqlInfo.setParameter("fdAttMainId", sysAttMain.getFdId());
        hqlInfo.setParameter("fdConverterKey", convertParams.getConverterKey());
        hqlInfo.setWhereBlock(whereBlock);
        SysFileConvertQueue queue = (SysFileConvertQueue)getSysFileConvertQueueService().findFirstOne(hqlInfo);
        if(queue != null) {
            result.add(queue);
        }
        return result;
    }

    protected SysFileConvertQueue beforeAdd(SysAttMain sysAttMain, ConvertParameter convertParams, Map<String, Object> extParams) throws Exception {
        List<SysFileConvertQueue> exsitsQueue = exsitsQueue(sysAttMain, convertParams, extParams);
        if (exsitsQueue != null && exsitsQueue.size() > 0) {
            for (SysFileConvertQueue q : exsitsQueue) {
                if (q.getFdConvertStatus() == 4) {
                    return q;
                }
            }
            return exsitsQueue.get(0);
        }
        return null;
    }

    protected SysFileConvertQueue buildQueue(SysAttMain sysAttMain, ConvertParameter convertParams, Map<String, Object> extParams) {
        String extName = FilenameUtils.getExtension(sysAttMain.getFdFileName());
        final SysFileConvertQueue queue = new SysFileConvertQueue();
        Date inQueueTime = new Date();
        queue.setFdFileId(sysAttMain.getFdFileId());
        queue.setFdConvertStatus(0);
        queue.setFdConvertNumber(0);
        queue.setFdIsFinish(false);
        queue.setFdStatusTime(inQueueTime);
        queue.setFdCreateTime(inQueueTime);
        queue.setFdConverterKey(convertParams.getConverterKey());
        queue.setFdFileName(sysAttMain.getFdFileName());
        queue.setFdFileExtName(extName);
        queue.setFdConverterParam(convertParams.getConvertParam());
        queue.setFdDispenser(convertParams.getDispenser());
        queue.setFdAttMainId(sysAttMain.getFdId());
        queue.setFdModelUrl(SysFileStoreUtil.getQueueHelpFul(sysAttMain.getFdModelId(), sysAttMain.getFdModelName(), sysAttMain.getFdFileName())[1]);
        queue.setFdAttModelId(sysAttMain.getFdModelId());
        queue.setFdConverterType(convertParams.getConverterType());
        queue.setFdIsLongQueue(false);
        return queue;
    }

    private SysFileConvertQueue handleAdd(SysAttMain sysAttMain, ConvertParameter convertParams, Map<String, Object> extParams) throws Exception {
        SysFileConvertQueue sysFileConvertQueue = buildQueue(sysAttMain, convertParams, extParams);
        getSysFileConvertQueueService().add(sysFileConvertQueue);
        return sysFileConvertQueue;
    }

    protected SysFileConvertQueue afterAdd(SysFileConvertQueue queue, SysAttMain sysAttMain, ConvertParameter convertParams, Map<String, Object> extParams) {
        return queue;
    }

    public ISysFileConvertQueueService getSysFileConvertQueueService() {
        if (sysFileConvertQueueService == null) {
            sysFileConvertQueueService = (ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService");
        }
        return sysFileConvertQueueService;
    }

}

package com.landray.kmss.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * <pre>
 * 手动事务控制工具：提供开始、开启事务的功能。
 * 开启事务：意味着总是开启一个新事务，所以可以指定事务读写和超时设置
 * 开始事务：意味着没有当前没有事务时开启一个事务，如果存在（多数情况）则沿用，所以读写和超时特性尽量不要干预
 * <b>特别注意，该工具类不可在服务启动过程中使用</b>
 * 调用示例代码：
 * TransactionStatus status = null;
 * Throwable t = null;
 * try {
 *     status = TransactionUtils.beginNewTransaction();
 *     action.doAction();
 *     TransactionUtils.commit(status);
 * } catch (Throwable e) {
 *     t = e;
 *     throw e;
 * } finally {
 *     if (t != null && status != null) {
 *         TransactionUtils.rollback(status);
 *     }
 * }
 *
 * </pre>
 */
public class TransactionUtils {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(TransactionUtils.class);

    /**
     * 获取事务管理器对象
     *
     * @return 事务管理器
     */
    public static PlatformTransactionManager getTransactionManager() {
        return (PlatformTransactionManager) SpringBeanUtil
                .getBean("transactionManager");
    }

    public static SessionFactory getSessionFactory() {
        return (SessionFactory) SpringBeanUtil
                .getBean("sessionFactory");
    }

    /**
     * 开始事务，返回默认属性的事务状态对象。如果需要自定义事务属性，请自己编写类似代码即可。
     *
     * @return 事务状态对象
     */
    public static TransactionStatus beginTransaction() {
        return beginTransaction(TransactionDefinition.TIMEOUT_DEFAULT);
    }

    /**
     * 开始事务，返回默认属性的事务状态对象。如果需要自定义事务属性，请自己编写类似代码即可。
     *
     * @param timeoutInSecond -1：表示无超时，配置大于0的值，单位：秒
     * @return
     */
    public static TransactionStatus beginTransaction(int timeoutInSecond) {
        DefaultTransactionDefinition td = new DefaultTransactionDefinition(
                TransactionDefinition.PROPAGATION_REQUIRED);
        td.setTimeout(timeoutInSecond);
        TransactionStatus status = getTransactionManager().getTransaction(td);
        postSetHibernateTransactionTimeout(timeoutInSecond);
        return status;
    }

    /**
     * 设置hibernate的事务超时时间，这个与{@link DefaultTransactionDefinition#setTimeout(int)}不同
     * 注意两者调用时入参必须保持一致，并且顺序不能动
     *
     * @param timeoutInSecond
     */
    private static void postSetHibernateTransactionTimeout(int timeoutInSecond) {
        /*
        之所以要设置hibernate维护的timeout，是因为HibernateTransactionManager.doBegin的时候并没有针对beginTransaction设置timeout
        这会导致一旦某个中间事务设置了timeout，会影响到当前事务链中的所有事务
            if (timeout != TransactionDefinition.TIMEOUT_DEFAULT) {
                // Use Hibernate's own transaction timeout mechanism on Hibernate 3.1+
                // Applies to all statements, also to inserts, updates and deletes!
                hibTx = session.getTransaction();
                hibTx.setTimeout(timeout);
                hibTx.begin();
            } else {
                // Open a plain Hibernate transaction without specified timeout.
                hibTx = session.beginTransaction();
                //缺少这一步操作
                //hibTx.setTimeout(TransactionDefinition.TIMEOUT_DEFAULT);
            }
         */
        Session currentSession = getSessionFactory().getCurrentSession();
        if (currentSession != null) {
            Transaction transaction = currentSession.getTransaction();
            if (transaction != null) {
                transaction.setTimeout(timeoutInSecond);
            }
        }
    }

    /**
     * 开启事务，可写，无超时控制
     *
     * @return
     */
    public static TransactionStatus beginNewTransaction() {
        return beginNewTransaction(TransactionDefinition.TIMEOUT_DEFAULT);
    }

    /**
     * 开启事务，可写，有超时控制
     *
     * @param timeoutInSecond -1：表示无超时，配置大于0的值，单位：秒
     * @return
     */
    public static TransactionStatus beginNewTransaction(int timeoutInSecond) {
        DefaultTransactionDefinition td = new DefaultTransactionDefinition(
                TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        td.setTimeout(timeoutInSecond);
        TransactionStatus status = getTransactionManager().getTransaction(td);
        postSetHibernateTransactionTimeout(timeoutInSecond);
        return status;
    }

    /**
     * 开启事务，只读，无超时控制
     *
     * @return
     */
    public static TransactionStatus beginNewReadTransaction() {
        return beginNewReadTransaction(TransactionDefinition.TIMEOUT_DEFAULT);
    }

    /**
     * 开启事务，只读，有超时控制
     *
     * @param timeoutInSecond -1：表示无超时，配置大于0的值，单位：秒
     * @return
     */
    public static TransactionStatus beginNewReadTransaction(int timeoutInSecond) {
        DefaultTransactionDefinition td = new DefaultTransactionDefinition(
                TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        td.setReadOnly(true);
        td.setTimeout(timeoutInSecond);
        TransactionStatus status = getTransactionManager().getTransaction(td);
        postSetHibernateTransactionTimeout(timeoutInSecond);
        return status;
    }

    public static void commit(TransactionStatus status) {
        if (status == null) {
            return;
        }
        getTransactionManager().commit(status);
    }

    public static void rollback(TransactionStatus status) {
        //预防NPExc
        if (status == null) {
            return;
        }
        if (!status.isCompleted()) {
            getTransactionManager().rollback(status);
        }
    }

    /**
     * 事务内要执行的业务接口<br/>
     * {@code com.landray.kmss.util.TransactionUtils#doInNewTransaction(com.landray.kmss.util.TransactionUtils.TransactionAction)}
     */
    public interface TransactionAction {
        public void doAction();
    }

    /**
     * com.landray.kmss.util.TransactionUtils#beginNewTransaction()的便捷用法<br/>
     * 调用方把业务逻辑包装在action里即可，事务会在异常抛出时回滚，在action正常完成后提交<br/>
     * 为了降低程序的复杂性，不建议在action中再调用doInNewTransaction方法
     * 调用样例:<br/>
     * <pre>
     * TransactionUtils.doInNewTransaction(new TransactionUtils.TransactionAction() {
     *      public void doAction() {
     *          //your code
     *      }
     * });
     *
     * </pre>
     *
     * @param action
     * @throws Exception
     * @Override
     */
    public static void doInNewTransaction(final TransactionAction action) throws Exception {
        doInNewTransaction(action, TransactionDefinition.TIMEOUT_DEFAULT);
    }

    /**
     * com.landray.kmss.util.TransactionUtils#beginNewTransaction()的便捷用法<br/>
     * 调用方把业务逻辑包装在action里即可，事务会在异常抛出时回滚，在action正常完成后提交<br/>
     * 为了降低程序的复杂性，不建议在action中再调用doInNewTransaction方法
     * 调用样例:<br/>
     * <pre>
     * TransactionUtils.doInNewTransaction(new TransactionUtils.TransactionAction() {
     *      public void doAction() {
     *          //your code
     *      }
     * });
     *
     * </pre>
     *
     * @param action
     * @param timeoutInSecond -1：表示无超时，配置大于0的值，单位：秒
     * @throws Exception
     * @Override
     */
    public static void doInNewTransaction(final TransactionAction action, int timeoutInSecond) throws Exception {
        if (action != null) {
            TransactionStatus status = null;
            Throwable t = null;
            try {
                status = TransactionUtils.beginNewTransaction(timeoutInSecond);
                action.doAction();
                TransactionUtils.commit(status);
            } catch (Throwable e) {
                t = e;
                throw e;
            } finally {
                if (t != null && status != null) {
                    TransactionUtils.rollback(status);
                }
            }
        }
    }
}

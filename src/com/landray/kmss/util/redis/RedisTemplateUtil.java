package com.landray.kmss.util.redis;

import com.landray.kmss.util.SpringBeanUtil;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author 严明镜
 * @create 2020年12月08日
 */
@SuppressWarnings("unused")
public class RedisTemplateUtil {

    private static RedisTemplateUtil instance;

    @SuppressWarnings("unchecked")
    public static RedisTemplateUtil getInstance() {
        if (instance == null) {
            Object simple = SpringBeanUtil.getBean("redisTemplate");
            if(null != simple) {
            	 RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) SpringBeanUtil.getBean("redisTemplate");
                 instance = new RedisTemplateUtil(redisTemplate);
            }
        }
        return instance;
    }

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public RedisTemplateUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private boolean unboxing(Boolean bool) {
        return bool == null ? false : bool;
    }

    private long unboxing(Long l, long defaultValue) {
        return l == null ? defaultValue : l;
    }

    /////////////////////////////////////////////// KEY /////////////////////////////////////////////

    /**
     * 设置key的过期时间
     */
    public boolean expire(String key, long time) {
        return unboxing(redisTemplate.expire(key, time, TimeUnit.SECONDS));
    }

    /**
     * 根据key获取过期时间
     */
    public long getTime(String key) {
        return unboxing(redisTemplate.getExpire(key, TimeUnit.SECONDS), -1);
    }

    /**
     * key是否存在
     */
    public boolean hasKey(String key) {
        return unboxing(redisTemplate.hasKey(key));
    }

    /**
     * 移除指定key 的过期时间
     */
    public boolean persist(String key) {
        return unboxing(redisTemplate.boundValueOps(key).persist());
    }

    /**
     * 删除key
     */
    public boolean delete(String key) {
        return unboxing(redisTemplate.delete(key));
    }

    /**
     * 批量删除key
     */
    public long delete(Collection<String> keys) {
        return unboxing(redisTemplate.delete(keys), 0);
    }

    /////////////////////////////////////////////// VALUE /////////////////////////////////////////////

    /**
     * 根据key获取值
     */
    public @Nullable
    Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key获取Integer值
     */
    public @Nullable
    Integer getInteger(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        try {
            return value == null ? null : Integer.parseInt(value.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据key获取Long值
     */
    public @Nullable
    Long getLong(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        try {
            return value == null ? null : Long.parseLong(value.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据key获取String值
     */
    public @Nullable
    String getString(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? null : value.toString();
    }

    /**
     * 将值放入缓存
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 将值放入缓存并设置时间
     *
     * @param key
     * @param value
     * @param time  时间(秒)
     */
    public void set(String key, Object value, int time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * 批量添加
     */
    public void multiSet(Map<String, Object> keyAndValue) {
        redisTemplate.opsForValue().multiSet(keyAndValue);
    }

    /**
     * 批量添加
     * 但凡有一个key已存在,则全部不添加
     */
    public void multiSetIfAbsent(Map<String, Object> keyAndValue) {
        redisTemplate.opsForValue().multiSetIfAbsent(keyAndValue);
    }

    /////////////////////////////////////////////// SET /////////////////////////////////////////////

    /**
     * 新增set
     */
    public void sAdd(String key, Object... value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 根据key获取set
     */
    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 获取set的长度
     */
    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 从set中查询是否存在value
     */
    public boolean sIsMember(String key, Object value) {
        return unboxing(redisTemplate.opsForSet().isMember(key, value));
    }

    /**
     * 批量移除set中的元素
     */
    public void sRemove(String key, Object... values) {
        redisTemplate.opsForSet().remove(key, values);
    }

    /////////////////////////////////////////////// Hash /////////////////////////////////////////////

    /**
     * 添加hash key-value
     */
    public void hPut(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 批量添加hash key-value
     */
    public void hPutAll(String key, Map<Object, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取hash key-value
     */
    public Map<Object, Object> hEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * hash中是否存在该key
     */
    public boolean hHasKey(String key, Object hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 获取hash中key的值
     */
    public Object hGet(String key, Object hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 根据hash的key批量删除
     *
     * @return 成功删除的个数
     */
    public long hDelete(String key, Object... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 获取hash的所有key
     */
    public Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取hash的长度
     */
    public Long hSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /////////////////////////////////////////////// LIST /////////////////////////////////////////////

    /**
     * 在list最前面添加
     */
    public void lLeftPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 在list最前面批量添加
     */
    public void lLeftPushAll(String key, String... values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * 获取list中指定下标的值
     */
    public Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取list中指定区间的值
     */
    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 在list最后添加
     */
    public void leftPushAll(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 在list最后批量添加
     */
    public void rightPushAll(String key, Object... values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 在已存在的list最后添加
     */
    public void rightPushIfPresent(String key, Object value) {
        redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 获取list长度
     */
    public long lSize(String key) {
        return unboxing(redisTemplate.opsForList().size(key), 0);
    }

    /**
     * 移除并返回list最前面的值
     */
    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移除并返回list最后的值
     */
    public Object rightPop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }
}

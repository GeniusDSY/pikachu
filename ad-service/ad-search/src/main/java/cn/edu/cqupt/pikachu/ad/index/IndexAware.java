package cn.edu.cqupt.pikachu.ad.index;

/**
 * @author :DengSiYuan
 * @date :2021/3/9 16:42
 * @desc : 索引
 */
public interface IndexAware<K, V> {

    /**
     * 获取索引
     *
     * @param key 索引的键
     * @return 索引值
     */
    V get(K key);

    /**
     * 添加索引
     *
     * @param key 索引的键
     * @param value 索引的值
     */
    void add(K key, V value);

    /**
     * 更新索引
     *
     * @param key 索引的键
     * @param value 索引的值
     */
    void update(K key, V value);

    /**
     * 删除索引
     *
     * @param key 索引的键
     * @param value  索引的值
     */
    void delete(K key, V value);

}

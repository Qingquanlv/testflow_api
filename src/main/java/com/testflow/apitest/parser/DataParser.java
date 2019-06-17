package com.testflow.apitest.parser;

/**
 *
 * @author qq.lv
 * @date 2019/6/14
 */
public abstract class DataParser<T, K> {

    /**
     *parse data
     *
     * @return 树的深度
     */
    public abstract K parse(T preObj);
}

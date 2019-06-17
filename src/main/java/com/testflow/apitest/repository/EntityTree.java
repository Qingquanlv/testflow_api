package com.testflow.apitest.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author qq.lv
 * @date 2019/6/2
 */
public class EntityTree {
    private Object key;
    private String valueType;
    private Object value;
    private List<EntityTree> childs;

    public EntityTree(){
        key = null;
        value = null;
        childs = new ArrayList();
        childs.clear();
    }

    public EntityTree(Object key) {
        this.key = key;
        childs = new ArrayList();
        childs.clear();
    }

    public EntityTree(Object key, String valueType, Object value) {
        this.key = key;
        this.valueType = valueType;
        this.value = value;
        childs = new ArrayList();
        childs.clear();
    }

    public EntityTree(Object key, Object value) {
        this.key = key;
        this.value = value;
        childs = new ArrayList();
        childs.clear();
    }

    /**
     * 添加子树
     * @param EntityTree 子树
     */
    public void addNode(EntityTree EntityTree) {
        childs.add(EntityTree);
    }

    /**
     * 置空树
     */
    public void clearTree() {
        key = null;
        value = null;
        childs.clear();
    }

    /**
     * 求树的深度
     * 这方法还有点问题，有待完善
     * @return 树的深度
     */
    public int dept() {
        return dept(this);
    }
    /**
     * 求树的深度
     * 这方法还有点问题，有待完善
     * @param EntityTree
     * @return
     */
    private int dept(EntityTree EntityTree) {
        if(EntityTree.isEmpty()) {
            return 0;
        }else if(EntityTree.isLeaf()) {
            return 1;
        } else {
            int n = childs.size();
            int[] a = new int[n];
            for(int i=0; i<n; i++) {
                if(childs.get(i).isEmpty()) {
                    a[i] = 0+1;
                } else {
                    a[i] = dept(childs.get(i)) + 1;
                }
            }
            Arrays.sort(a);
            return a[n-1];
        }
    }
    /**
     * 返回递i个子树
     * @param i
     * @return
     */
    public EntityTree getChild(int i) {
        return childs.get(i);
    }

    /**
     * 求第一个孩子 结点
     * @return
     */
    public EntityTree getFirstChild() {
        return childs.get(0);

    }

    /**
     * 求最后 一个孩子结点
     * @return
     */
    public EntityTree getLastChild() {
        return childs.get(childs.size()-1);
    }

    /**
     * 求所有孩子结点
     * @return
     */
    public List<EntityTree> getChilds() {
        return childs;
    }

    public EntityTree getSpecifyChild(String chlidName) {
        return this.getChilds()
                .stream()
                .filter(item->item.key.toString().equals(chlidName))
                .collect(Collectors.toList()).get(0);
    }

    public boolean hasSpecifyChild(String chlidName) {
        if(this.getChilds()
                .stream()
                .filter(item->item.key.toString().equals(chlidName))
                .count() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 获得根结点的数据
     * @return
     */
    public Object getRootData() {
        return key;
    }

    /**
     * 判断是否为空树
     * @return 如果为空，返回true,否则返回false
     */
    public boolean isEmpty() {
        if(childs.isEmpty() && key == null) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为List结点
     * @return
     */
    public boolean isListNode() {
        if(childs.isEmpty())
            return false;
        else {
            String pattern = ".*\\[(\\d+)\\]";
            Pattern r = Pattern.compile(pattern);
            for (EntityTree obj : childs)
            {
                if(Pattern.matches(pattern, obj.getNodeKey().toString()))
                    return true;
            }
            return false;
        }
    }

    /**
     * 判断当前节点是否为List结点中的一个
     * @return
     */
    public boolean isListNodeChild() {
        String pattern = ".*\\[(\\d+)\\]";
        Pattern r = Pattern.compile(pattern);
        if(this.key == null) {
            return false;
        }
        if(Pattern.matches(pattern, this.key.toString())) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断是否为叶子结点
     * @return
     */
    public boolean isLeaf() {
        if(childs.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 获得树根
     * @return 树的根
     */
    public EntityTree root() {
        return this;
    }

    /**
     * 设置根结点的数据
     */
    public void setRootData(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getNodeKey() {
        return key;
    }

    public Object getNodeValue() {
        return value;
    }

    public void setNodeValue(Object value) {
        this.value = value;
    }

    /**
     * 求结点数
     * 这方法还有点问题，有待完善
     * @return 结点的个数
     */
    public int size() {
        return size(this);
    }
    /**
     * 求结点数
     * 这方法还有点问题，有待完善
     * @param EntityTree
     * @return
     */
    private int size(EntityTree EntityTree) {
        if(EntityTree.isEmpty()) {
            return 0;
        }else if(EntityTree.isLeaf()) {
            return 1;
        } else {
            int count = 1;
            int n = childs.size();
            for(int i=0; i<n; i++) {
                if(!childs.get(i).isEmpty()) {
                    count += size(childs.get(i));
                }
            }
            return count;
        }
    }
}

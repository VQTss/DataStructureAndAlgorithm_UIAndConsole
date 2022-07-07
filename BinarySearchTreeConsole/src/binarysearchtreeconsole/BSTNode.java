/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarysearchtreeconsole;

/**
 *
 * @author Vo Quoc Thai CE160568
 */
public class BSTNode {

    private int data;
    private int count; // for duplicate value
    private BSTNode leftChild;
    private BSTNode rightChild;
    private BSTNode parent;

    public enum NodeType {
        LEFT_CHILD,
        RIGHT_CHILD;
    }

    private int level;
    private int order;

    public BSTNode(int data) {
        this.data = data;
        this.count = 1;
        this.leftChild = this.rightChild = this.parent = null;
        this.level = 0;
        this.order = 0;
    }

    public boolean isLeaf() {
        return this.leftChild == null && this.rightChild == null;
    }

    public boolean hasLeftChild() {
        return this.leftChild != null;
    }

    public boolean hasRightChild() {
        return this.rightChild != null;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean hasChild() {
        return !isLeaf();
    }

    public boolean isInside() {
        return !isRoot() && !isLeaf();
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BSTNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(BSTNode leftChild) {
        this.leftChild = leftChild;
        if (this.leftChild != null) {
            this.leftChild.setParent(this, NodeType.LEFT_CHILD);
        }
        
    }

    public BSTNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(BSTNode rightChild) {
        this.rightChild = rightChild;
        if (this.rightChild != null) {
            this.rightChild.setParent(this, NodeType.RIGHT_CHILD);
        }
        
    }

    public BSTNode getParent() {
        return parent;
    }

    public void setParent(BSTNode parent, NodeType type) {
        this.parent = parent;
        this.level = parent.getLevel() + 1;
        if (type == NodeType.LEFT_CHILD) {
            this.order = parent.getOrder() * 2 + 1;
        } else {
            this.order = parent.getOrder() * 2 + 2;
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public BSTNode findMinNode() {
        BSTNode node = this;
        while (node.hasLeftChild()) {
            node = node.getLeftChild();
        }
        return node;
    }

    public BSTNode findMaxNode() {
        BSTNode node = this;
        while (node.hasRightChild()) {
            node = node.getRightChild();
        }
        return node;
    }

  public boolean removeLeafChild(BSTNode node) {
        if (node == null) {
            return false;
        } else {
            if (this.hasLeftChild()) {
                if (this.getLeftChild().getData() == node.getData()) {
                    this.setLeftChild(null); // remove the left leaf child
                    return true;
                }
            }
            if (this.hasRightChild()) {
                if (this.getRightChild().getData() == node.getData()) {
                    this.setRightChild(null); // remove the right leaf child
                    return true;
                }
            }
        }
        return false;
    }

}

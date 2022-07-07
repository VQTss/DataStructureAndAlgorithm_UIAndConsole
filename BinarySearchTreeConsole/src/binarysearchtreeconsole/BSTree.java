/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binarysearchtreeconsole;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Thai Vo Quoc CE160568
 */
public class BSTree {

    BSTNode root;
    private String result;
    ArrayList<BSTNode> ascendingArray;

    public BSTree() {
        root = null;
        result = "";
        ascendingArray = new ArrayList<>();
    }

    public void addNode(int data) {
        if (root == null) {
            root = new BSTNode(data);
        } else {
            boolean isAdded = false;
            BSTNode node = root;
            while (!isAdded) {
                if (node.getData() > data) {
                    if (node.hasLeftChild()) {
                        node = node.getLeftChild();
                    } else {
                        node.setLeftChild(new BSTNode(data));
                        isAdded = true;
                    }
                } else if (node.getData() < data) {
                    if (node.hasRightChild()) {
                        node = node.getRightChild();
                    } else {
                        node.setRightChild(new BSTNode(data));
                        isAdded = true;
                    }
                } else {
                    node.setCount(node.getCount() + 1);
                    isAdded = true;
                }
            }
        }
    }

    public void preOrder() {
        this.result = "";
        preOrder(root);

    }

    private void preOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < node.getCount(); i++) {
            this.result += node.getData() + ", ";
        }
        preOrder(node.getLeftChild());
        preOrder(node.getRightChild());
    }

    public void inOrder() {
        this.result = "";
        this.ascendingArray.clear();
        inOrder(root);
    }

    private void inOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeftChild());
        for (int i = 0; i < node.getCount(); i++) {
            this.result += node.getData() + ", ";
            this.ascendingArray.add(node);
        }
        inOrder(node.getRightChild());
    }

    public void postOrder() {
        this.result = "";
        postOrder(root);
    }

    private void postOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.getLeftChild());
        postOrder(node.getRightChild());
        for (int i = 0; i < node.getCount(); i++) {
            this.result += node.getData() + ", ";
        }
    }

    public String getResult() {
        return result;
    }

    public BSTNode findNode(int data) {
        BSTNode node = root;
        result = "";
        while (node != null) {
            result += node.getData() + "->";
            if (data == node.getData()) {
                return node;
            } else if (data < node.getData()) {
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        return null;
    }

    public boolean removeNode(int data) {
        BSTNode node = findNode(data);
        return removeNode(node);
    }

    public boolean removeNode(BSTNode node) {
        if (node == null) {
            return false;
        } else {
            node.setCount(node.getCount() - 1);
            if (node.getCount() == 0) {
                if (node.isLeaf()) {
                    node.getParent().removeLeafChild(node);
                    return true;
                } else {
                    BSTNode incomer = null;
                    if (node.hasLeftChild()) {
                        incomer = node.getLeftChild().findMaxNode();
                    } else {
                        incomer = node.getRightChild().findMinNode();
                    }
                    node.setData(incomer.getData());
                    node.setCount(incomer.getCount());
                    incomer.setCount(1);
                    return removeNode(incomer);
                }
            } else {
                return true;
            }
        }
    }

    public void countLeaf() {
        result = "";
        countLeaf(root);
    }

    public void countLeaf(BSTNode node) {
        if (node == null) {
            return;
        } else {
            if (node.isLeaf()) {
                for (int i = 0; i < node.getCount(); i++) {
                    result += node.getData() + ", ";
                }
            }
            countLeaf(node.getLeftChild());
            countLeaf(node.getRightChild());
        }
    }

    public void clear() {
        clearNode(root);
        this.root = null;
    }

    public void clearNode(BSTNode node) {
        if (node != null) {
            if (node.hasLeftChild()) {
                clearNode(node.getLeftChild());
            }
            if (node.hasRightChild()) {
                clearNode(node.getRightChild());
            }
            if (!node.isRoot()) {
                node.getParent().removeLeafChild(node);
            }
        }
    }

    public void blancing() {
        inOrder();
        result = "";
        clear();
        blancing(0, this.ascendingArray.size() - 1);
    }

    public void blancing(int leftIndex, int rightIndex) {
        if (leftIndex <= rightIndex) {
            int middleIndex = (leftIndex + rightIndex) / 2;
            int nodeData = this.ascendingArray.get(middleIndex).getData();
            int nodeCount = this.ascendingArray.get(middleIndex).getCount();
            for (int i = 0; i < nodeCount; i++) {
                result += nodeData + ", ";
                this.addNode(nodeData);
            }
            blancing(leftIndex, middleIndex - 1);
            blancing(middleIndex + 1, rightIndex);
        }
    }

    public void BFS() {
        this.result = "";
        Queue<BSTNode> q = new LinkedList<>();
        q.add(root);
        BSTNode node;
        while (!q.isEmpty()) {
            node = q.poll();
            if (node != null) {
                for (int i = 0; i < node.getCount(); i++) {
                    this.result += node.getData() + ",";
                }
                q.add(node.getLeftChild());
                q.add(node.getRightChild());
            }
        }
    }

    public void DFS() {
        this.result = "";
        Stack<BSTNode> s = new Stack<>();
        s.push(root);
        BSTNode node;
        while (!s.isEmpty()) {
            node = s.pop();
            if (node != null) {
                for (int i = 0; i < node.getCount(); i++) {
                    this.result += node.getData() + ",";
                }
                s.add(node.getRightChild());
                s.add(node.getLeftChild());
            }
        }
    }
}

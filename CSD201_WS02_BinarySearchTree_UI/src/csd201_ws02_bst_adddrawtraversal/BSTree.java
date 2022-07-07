package csd201_ws02_bst_adddrawtraversal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author CE160568 Vo Quoc Thai
 */
public class BSTree {

    BSTNode root;
    private int y, width;
    private String result;
    private String pathOfNode;
    ArrayList<BSTNode> path;
    ArrayList<BSTNode> acendingArray;

    private String fileData;
    private int countData;

    public String getFileData() {
        return fileData;
    }

    public int getCountData() {
        return countData;
    }

    public BSTree(int y, int screenWidth) {
        root = null;
        this.y = y;
        this.width = screenWidth / 2;
        path = new ArrayList<>();
        acendingArray = new ArrayList<>();
    }

    public void addNode(int nodeValue) {
        if (root == null) {
            root = new BSTNode(nodeValue, y, width);
        } else {
            BSTNode node = root;
            boolean isAdded = false;
            while (!isAdded) {
                if (nodeValue < node.getData()) {
                    if (node.hasLeftChild()) {
                        node = node.getLeftChild();
                    } else {
                        node.setLeftChild(new BSTNode(nodeValue));
                        isAdded = true;
                    }
                } else if (nodeValue > node.getData()) {
                    if (node.hasRightChild()) {
                        node = node.getRightChild();
                    } else {
                        node.setRightChild(new BSTNode(nodeValue));
                        isAdded = true;
                    }
                } else { //nodeValue == node.getData()
                    node.setCount(node.getCount() + 1);
                    isAdded = true;
                }
            }
        }
    }

    public BSTNode getRoot() {
        return this.root;
    }

    public String getPathOfNode() {
        return pathOfNode;
    }

    public String getResult() {
        return this.result;
    }

    public void PreOrder() {
        this.result = "";
        this.fileData = "";
        PreOrder(this.root);
    }

    public void PreOrder(BSTNode node) {
        if (node != null) {
            for (int i = 0; i < node.getCount(); i++) {
                this.result += node.getData() + ",";
                this.fileData += node.getData() + " ";
                ++this.countData;
            }

            PreOrder(node.getLeftChild());
            PreOrder(node.getRightChild());
        }
    }

    public void InOrder() {
        this.result = "";
        this.acendingArray.clear();
        InOrder(this.root);
    }

    public void InOrder(BSTNode node) {
        if (node != null) {
            InOrder(node.getLeftChild());
            for (int i = 0; i < node.getCount(); i++) {
                this.result += node.getData() + ",";
            }
            this.acendingArray.add(node);
            InOrder(node.getRightChild());
        }
    }

    public void PostOrder() {
        this.result = "";
        PostOrder(this.root);
    }

    public void PostOrder(BSTNode node) {
        if (node != null) {
            PostOrder(node.getLeftChild());
            PostOrder(node.getRightChild());
            for (int i = 0; i < node.getCount(); i++) {
                this.result += node.getData() + ",";
            }
        }
    }

    public ArrayList<BSTNode> getPath() {
        return path;
    }

    public BSTNode findNode(int data) {
        BSTNode node = this.root;
        result = "";
        path.clear();

        while (node != null) {
//            System.out.println(node.getData() + " -> ");
            result += node.getData() + " -> ";
            path.add(node);
            if (data == node.getData()) {
                return node;
            } else if (data < node.getData()) {
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        path.clear();
        return null;
    }

    public boolean removeNode(int data) {
        BSTNode node = findNode(data);
        return removeNode(node);
    }

    public void clearPath() {
        path.clear();
        pathOfNode = "";
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

    public void balacing() {
        InOrder(); // tìm ra được mảng tăng dần
        clear();
        balacing(0, this.acendingArray.size() - 1);
    }

    public void balacing(int leftIndex, int rightIndex) {
        if (leftIndex <= rightIndex) {
            int middleIndex = (leftIndex + rightIndex) / 2;
            int nodeData = this.acendingArray.get(middleIndex).getData();
            int nodeCount = this.acendingArray.get(middleIndex).getCount();
            for (int i = 0; i < nodeCount; i++) {
                this.addNode(nodeData);
            }
            balacing(leftIndex, middleIndex - 1);
            balacing(middleIndex + 1, rightIndex);
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
                    this.result += "," + node.getData();
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
                    this.result += "," + node.getData();
                }
                s.add(node.getRightChild());
                s.add(node.getLeftChild());
            }
        }
    }
}

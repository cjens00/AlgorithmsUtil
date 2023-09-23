package DataStructures.Trees;

import java.util.Objects;

public class TreeNode<T> implements Comparable<TreeNode<T>> {
    private int key;
    private T value;
    private TreeNode<T> left;
    private TreeNode<T> right;
    private TreeNode<T> parent;

    public TreeNode(int key, T value) {
        this.key = key;
        this.value = value;
        this.left = this.right = this.parent = null;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public TreeNode<T> getLeftChild() {
        return left;
    }

    public void setLeftChild(TreeNode<T> left) {
        this.left = left;
    }

    public TreeNode<T> getRightChild() {
        return right;
    }

    public void setRightChild(TreeNode<T> right) {
        this.right = right;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public boolean hasLeftChild() {
        return this.left == null;
    }

    public boolean hasRightChild() {
        return this.right == null;
    }

    public boolean hasAnyChildren() {
        return this.left != null || this.right != null;
    }

    public boolean hasBothChildren() {
        return this.left != null && this.right != null;
    }

    public boolean isLeftChild() {
        return this.parent != null && this.parent.left == this;
    }

    public boolean isRightChild() {
        return this.parent != null && this.parent.right == this;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    public TreeNode<T> findSuccessor() {
        TreeNode<T> successor = null;
        if (this.hasRightChild()) successor = this.right.findMinimum();
        else {
            if (this.parent != null) {
                if (this.isLeftChild()) successor = this.parent;
                else {
                    this.parent.right = null;
                    successor = this.parent.findSuccessor();
                    this.parent.right = this;
                }
            }
        }
        return successor;
    }

    public TreeNode<T> findMinimum() {
        var current = this;
        while (current.hasLeftChild()) current = current.left;
        return current;
    }

    public void spliceOut() {
        if (this.isLeaf()) {
            if (this.isLeftChild()) this.parent.left = null;
            else this.parent.right = null;
        } else if (this.hasAnyChildren()) {
            if (this.hasLeftChild()) {
                if (this.isLeftChild()) this.parent.left = this.left;
                else this.parent.right = this.left;
                this.left.parent = this.parent;
            } else {
                if (this.isLeftChild()) this.parent.left = this.right;
                else this.parent.right = this.right;
                this.right.parent = this.parent;
            }
        }
    }

    @Override
    public int compareTo(TreeNode<T> o) {
        return Integer.compare(this.key, o.key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode<?> treeNode = (TreeNode<?>) o;
        return key == treeNode.key && Objects.equals(value, treeNode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
}

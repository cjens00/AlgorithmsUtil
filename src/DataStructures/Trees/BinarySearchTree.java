package DataStructures.Trees;

import java.util.NoSuchElementException;

public class BinarySearchTree<T> {
    private TreeNode<T> root;
    private int size;

    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    public BinarySearchTree(TreeNode<T> root) {
        this.root = root;
        this.size = 1;
    }

    public T get(int key) {
        if (this.root == null) return null;
        else {
            var match = getHelper(key, this.root);
            if (match == null) return null;
            else return match.getValue();
        }
    }

    public TreeNode<T> getHelper(int key, TreeNode<T> node) {
        if (node == null) return null;
        else if (key == node.getKey()) return node;
        else if (key < node.getKey()) return getHelper(key, node.getLeftChild());
        else return getHelper(key, node.getRightChild());
    }


    public void put(int key, T value) {
        if (this.root != null) putHelper(key, value, this.root);
        else this.root = new TreeNode<>(key, value);
        this.size++;
    }

    private void putHelper(int key, T value, TreeNode<T> currentNode) {
        var nodeToAdd = new TreeNode<>(key, value);
        nodeToAdd.setParent(currentNode);
        if (key < currentNode.getKey()) {
            if (currentNode.hasLeftChild()) putHelper(key, value, currentNode.getLeftChild());
            else currentNode.setLeftChild(nodeToAdd);
        } else {
            if (currentNode.hasRightChild()) putHelper(key, value, currentNode.getRightChild());
            else currentNode.setRightChild(nodeToAdd);
        }
    }

    public boolean contains(int key) {
        return getHelper(key, this.root) != null;
    }

    public void delete(int key) throws NoSuchElementException {
        String errMessage = "The provided key was not found in the tree.";
        if (this.size > 1) {
            var nodeToRemove = getHelper(key, this.root);
            if (nodeToRemove != null) {
                remove(nodeToRemove);
                this.size--;
            } else throw new NoSuchElementException(errMessage);

        } else if (this.size == 1 && key == this.root.getKey()) {
            this.root = null;
            this.size--;
        } else throw new NoSuchElementException(errMessage);
    }

    private void remove(TreeNode<T> node) {
        if (node.isLeaf()) {
            if (node.isLeftChild()) node.getParent().setLeftChild(null);
            else node.getParent().setRightChild(null);
        } else if (node.hasBothChildren()) {
            var successor = node.findSuccessor();
            successor.spliceOut();
            node.setKey(successor.getKey());
            node.setValue(successor.getValue());
        } else {
            if (node.hasLeftChild()) {
                if (node.isLeftChild()) {
                    node.getLeftChild().setParent(node.getParent());
                    node.getParent().setLeftChild(node.getLeftChild());
                } else if (node.isRightChild()) {
                    node.getLeftChild().setParent(node.getParent());
                    node.getParent().setRightChild(node.getLeftChild());
                } else {
                    node.setKey(node.getLeftChild().getKey());
                    node.setValue(node.getLeftChild().getValue());
                    node.setLeftChild(node.getLeftChild().getLeftChild());
                    node.setRightChild(node.getLeftChild().getRightChild());
                }
            } else {
                if (node.isLeftChild()) {
                    node.getRightChild().setParent(node.getParent());
                    node.getParent().setLeftChild(node.getRightChild());
                } else if (node.isRightChild()) {
                    node.getRightChild().setParent(node.getParent());
                    node.getParent().setRightChild(node.getRightChild());
                } else {
                    node.setKey(node.getRightChild().getKey());
                    node.setValue(node.getRightChild().getValue());
                    node.setLeftChild(node.getRightChild().getLeftChild());
                    node.setRightChild(node.getRightChild().getRightChild());
                }
            }
        }
    }

    public int size() {
        return this.size;
    }
}

package com.huhx0015.poa;

/**
 * Created by Michael Yoon Huh on 4/4/2017.
 */

public class Node {

    private int mData;
    private Node mHead;
    private Node mTail;

    public Node(int data) {
        this.mData = data;
    }

    public int getData() {
        return mData;
    }

    public void setData(int data) {
        this.mData = data;
    }

    public Node getHead() {
        return mHead;
    }

    public void setHead(Node head) {
        this.mHead = head;
    }

    public Node getTail() {
        return mTail;
    }

    public void setTail(Node tail) {
        this.mTail = tail;
    }
}

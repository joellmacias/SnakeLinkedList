/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

/**
 *
 * @author joellmacias
 */
public class Snake {

    int length = 1;
    Node<String> head;

    char direction = 'R';
    final int x[] = new int[25600];
    final int y[] = new int[25600];

    Snake(String init_snake, int x, int y) {
        this.x[0] = x;
        this.y[0] = y;
        addHead(init_snake);
    }

    public void addHead(String data) {
        Node<String> newNode = new Node();
        newNode.data = data;
        if (length == 1) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
            length++;
        }
    }

    public void addInOrder(String data) {
        Node<String> newNode = new Node();
        newNode.data = data;
        addInOrder(head, newNode);
        length++;
    }

    private void addInOrder(Node currentNode, Node newNode) {
        if (head == null) {
            head = newNode;
            return;
        } else if (newNode.compareTo(head) < 0) {
            newNode.next = head;
            head = newNode;
            return;
        } else if (currentNode.next == null) {
            currentNode.next = newNode;
            return;
        } else if (newNode.compareTo(currentNode.next) < 0) {
            newNode.next = currentNode.next;
            currentNode.next = newNode;
            return;
        }
        addInOrder(currentNode.next, newNode);
    }

    public void remove(int position) {
        if (position == 0)
        {
            if (head != null)
            {
                length--;
                head = head.next;
            }
        }
        else{
        removeByIndex(head, position);
        }
    }

    private void removeByIndex(Node headNode, int position) {
        if (position >= length) {
            removeFromTail();
            return;
        }
        if (headNode.next == null) {
            return;
        }
        if (position == 1) {
            length--;
            headNode.next = headNode.next.next;
        } else {
            removeByIndex(headNode.next, position - 1);
        }
        
    }

    public Node removeFromTail() {
        if (head == null) {
            return null;
        }
        return removeFromTail(head);
    }

    private Node removeFromTail(Node node) {
        if (node.next.next == null) {
            length--;
            return node.next = null;
        } else if (node.next == null) {
            length--;
            return node = null;
        } else {
            return removeFromTail(node.next);
        }
    }

    //to move snake
    public void move() {
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];

        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - 25;
                break;
            case 'D':
                y[0] = y[0] + 25;
                break;
            case 'L':
                x[0] = x[0] - 25;
                break;
            case 'R':
                x[0] = x[0] + 25;
                break;
        }
    }
}

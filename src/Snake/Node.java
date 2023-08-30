
package Snake;

/**
 *
 * @author joellmacias
 */
public class Node<E extends Comparable> implements Comparable<Node> {

    public E data;
    public Node<E> next;

    public boolean equals(Node node) {
        if (this.data.equals(node.data)) {
            return true;
        }

        return false;

    }
    
    public int compareTo(Node node) {
        return this.data.compareTo(node.data);
    }
}

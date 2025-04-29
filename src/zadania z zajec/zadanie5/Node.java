public class Node <T> {
    private T value;
    private Color color;
    private Node<T> Left;
    private Node<T> Right;
    private Node<T> Parent;

    public Node(T value, Color color, Node<T> Left, Node<T> Right, Node<T> Parent) {
        this.value = value;
        this.color = color;
        this.Left = Left;
        this.Right = Right;
        this.Parent = Parent;
    }

    public T getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public Node<T> getLeft() {
        return Left;
    }

    public Node<T> getRight() {
        return Right;
    }

    public Node<T> getParent() {
        return Parent;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLeft(Node<T> left) {
        Left = left;
    }

    public void setRight(Node<T> right) {
        Right = right;
    }

    public void setParent(Node<T> parent) {
        Parent = parent;
    }

    // --- DODANE: pomocnicze metody ---
    public boolean isRed() {
        return this.color == Color.RED;
    }

    public boolean isBlack() {
        return this.color == Color.BLACK;
    }

    public void setRed() {
        this.color = Color.RED;
    }

    public void setBlack() {
        this.color = Color.BLACK;
    }
}

public class RBT <T extends Comparable<T>> {
    private Node<T> root = null;

    public Node<T> getRoot() {
        return root;
    }

    public void leftRotate(Node<T> x) {
        Node<T> y = x.getRight();
        if (y == null) {
            return; // nie da się wykonać rotacji, bo nie ma prawego dziecka
        }
        x.setRight(y.getLeft());
        if (y.getLeft() != null) {
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        y.setLeft(x);
        x.setParent(y);
    }

    public void rightRotate(Node<T> x) {
        Node<T> y = x.getLeft();
        if (y == null) {
            return; // nie da się wykonać rotacji, bo nie ma lewego dziecka
        }
        x.setLeft(y.getRight());
        if (y.getRight() != null) {
            y.getRight().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            root = y;
        } else if (x == x.getParent().getRight()) {
            x.getParent().setRight(y);
        } else {
            x.getParent().setLeft(y);
        }
        y.setRight(x);
        x.setParent(y);
    }

    public void RbInsertFixup(Node<T> z) {
        while (z.getParent() != null && z.getParent().isRed()) {
            if (z.getParent() == z.getParent().getParent().getLeft()) {
                Node<T> y = z.getParent().getParent().getRight(); // wujek z prawej strony
                if (y != null && y.isRed()) {
                    z.getParent().setBlack(); // przypadek 1
                    y.setBlack();
                    z.getParent().getParent().setRed();
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getRight()) {
                        z = z.getParent(); // przypadek 2
                        leftRotate(z);
                    }
                    z.getParent().setBlack(); // przypadek 3
                    z.getParent().getParent().setRed();
                    rightRotate(z.getParent().getParent());
                }
            } else {
                Node<T> y = z.getParent().getParent().getLeft(); // wujek z lewej strony
                if (y != null && y.isRed()) {
                    z.getParent().setBlack(); // przypadek 1 (odbicie lustrzane)
                    y.setBlack();
                    z.getParent().getParent().setRed();
                    z = z.getParent().getParent();
                } else {
                    if (z == z.getParent().getLeft()) {
                        z = z.getParent(); // przypadek 2 (odbicie lustrzane)
                        rightRotate(z);
                    }
                    z.getParent().setBlack(); // przypadek 3 (odbicie lustrzane)
                    z.getParent().getParent().setRed();
                    leftRotate(z.getParent().getParent());
                }
            }
        }
        root.setBlack();
    }

    public void add(T value) {
        Node<T> n = new Node<>(value, Color.RED, null, null, null);
        if (root == null) {
            root = n;
            root.setBlack();
        }
        else{
            Node<T> current = root;
            Node<T> parent = root;
            while (true) {
                if(n.getValue().compareTo(current.getValue()) < 0) {
                    if(current.getLeft() == null) {
                        n.setParent(current);
                        current.setLeft(n);
                        break;
                    }
                    parent = current;
                    current = current.getLeft();
                }
                else {
                    if(current.getRight() == null) {
                        n.setParent(current);
                        current.setRight(n);
                        break;
                    }
                    parent = current;
                    current = current.getRight();
                }
            }
        }
        RbInsertFixup(n);
    }

    public void printInOrder(Node<T> n) {
        if (n == null) return;
        if(n.getLeft() != null)
            printInOrder(n.getLeft());
        System.out.print(n.getValue() + " ");
        if(n.getRight() != null)
            printInOrder(n.getRight());
    }
}
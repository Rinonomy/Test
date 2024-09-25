import java.util.Stack;

public class MyStringBuilder2 {
    private char[] value;
    private int size;
    private Stack<Snapshot> snapshots;

    public MyStringBuilder2() {
        this.value = new char[16];
        this.size = 0;
        this.snapshots = new Stack<>();
    }

    public MyStringBuilder2(String str) {
        this();
        if (str == null) {
            str = "null";
        }
        append(str);
    }

    private void saveState() {
        snapshots.push(new Snapshot(value, size));
    }

    public MyStringBuilder2 append(String str) {
        saveState();
        if (str == null) {
            str = "null";
        }
        return append(str.toCharArray(), 0, str.length());
    }

    public MyStringBuilder2 append(char c) {
        saveState();
        ensureCapacity(size + 1);
        value[size++] = c;
        return this;
    }

    public MyStringBuilder2 append(char[] str, int offset, int len) {
        saveState();
        if (str == null) {
            return append("null");
        }
        ensureCapacity(size + len);
        System.arraycopy(str, offset, value, size, len);
        size += len;
        return this;
    }

    public MyStringBuilder2 insert(int index, String str) {
        saveState();
        if (str == null) {
            str = "null";
        }
        return insert(index, str.toCharArray(), 0, str.length());
    }

    public MyStringBuilder2 insert(int index, char[] str, int offset, int len) {
        saveState();
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacity(size + len);
        System.arraycopy(value, index, value, index + len, size - index);
        System.arraycopy(str, offset, value, index, len);
        size += len;
        return this;
    }

    public MyStringBuilder2 delete(int start, int end) {
        saveState();
        if (start < 0 || end > size || start > end) {
            throw new IndexOutOfBoundsException("start: " + start + ", end: " + end + ", Size: " + size);
        }
        int len = end - start;
        System.arraycopy(value, end, value, start, size - end);
        size -= len;
        return this;
    }

    public void undo() {
        if (!snapshots.isEmpty()) {
            Snapshot snapshot = snapshots.pop();
            value = snapshot.value;
            size = snapshot.size;
        }
    }

    public String toString() {
        return new String(value, 0, size);
    }

    public int length() {
        return size;
    }

    private void ensureCapacity(int required) {
        if (required > value.length) {
            int newSize = Math.max(value.length * 2, required);
            char[] newValue = new char[newSize];
            System.arraycopy(value, 0, newValue, 0, size);
            value = newValue;
        }
    }

    private static class Snapshot {
        private final char[] value;
        private final int size;

        public Snapshot(char[] value, int size) {
            this.value = new char[size];
            System.arraycopy(value, 0, this.value, 0, size);
            this.size = size;
        }
    }
}

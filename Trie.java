// Префиксное дерево Trie

public class Trie {
    private static final int R = 26;        // Размер алфавита: только a–z
    private final Node root = new Node();   // Корень дерева

    // Узел дерева
    private static class Node {
        Node[] children = new Node[R];   // Дочерние узлы (по одной букве)
        boolean isEnd = false;           // true — здесь заканчивается слово
    }

    // Приводим строку к нижнему регистру и убирает пробелы
    private String norm(String s) {
        return s == null ? "" : s.toLowerCase().trim();
    }

    // Добавляем слово. Если слово уже есть, игнорируем
    public void insert(String word) {
        String s = norm(word);
        if (s.isEmpty() || contains(s)) return;  // Если дубликат, то выходим

        Node cur = root;
        for (char c : s.toCharArray()) {
            int i = c - 'a';
            if (i < 0 || i >= R) throw new IllegalArgumentException("Только латинские буквы a-z");
            if (cur.children[i] == null) cur.children[i] = new Node();
            cur = cur.children[i];
        }
        cur.isEnd = true;
    }

    // Проверяем наличие слова в дереве
    public boolean contains(String word) {
        Node node = find(norm(word));
        return node != null && node.isEnd;
    }

    // Проверяем, есть ли слова с заданным префиксом
    public boolean startsWith(String prefix) {
        return find(norm(prefix)) != null;
    }

    // Возвращаем все слова, начинающиеся на prefix, если "", то все слова
    public MyList<String> getByPrefix(String prefix) {
        MyList<String> result = new MyList<>();
        Node node = find(norm(prefix));
        if (node != null) {
            collect(node, new StringBuilder(norm(prefix)), result);
        }
        return result;
    }

    // Удаляем слово из дерева с очисткой ненужных узлов
    public boolean remove(String word) {
        String s = norm(word);
        if (!contains(s)) return false;
        removeHelper(root, s, 0);
        return true;
    }

    // Рекурсивное удаление
    private boolean removeHelper(Node node, String s, int depth) {
        if (depth == s.length()) {
            node.isEnd = false;         // Снимаем метку конца слова
            return !hasChildren(node);  // Можно удалить если нет детей
        }

        int i = s.charAt(depth) - 'a';
        Node child = node.children[i];
        boolean shouldDelete = removeHelper(child, s, depth + 1);

        // Удаляем ссылку если дочерний узел можно удалить
        if (shouldDelete) {
            node.children[i] = null;
        }

        return !node.isEnd && !hasChildren(node);
    }

    // Есть ли у узла дочерние узлы
    private boolean hasChildren(Node node) {
        for (Node child : node.children) {
            if (child != null) return true;
        }
        return false;
    }

    // Находим узел по строке
    private Node find(String s) {
        Node cur = root;
        for (char c : s.toCharArray()) {
            int i = c - 'a';
            if (i < 0 || i >= R || cur.children[i] == null) return null;
            cur = cur.children[i];
        }
        return cur;
    }

    // Рекурсивно собираем все слова под узлом
    private void collect(Node node, StringBuilder prefix, MyList<String> result) {
        if (node.isEnd) result.add(prefix.toString());  // Добавляем слово если есть
        // Рекурсивно обходим всех детей
        for (int i = 0; i < R; i++) {
            if (node.children[i] != null) {
                prefix.append((char)(i + 'a'));
                collect(node.children[i], prefix, result);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }

    // Возвращаем общее количество слов в дереве
    public int size() {
        return countWords(root);
    }

    // Рекурсивный подсчет слов
    private int countWords(Node node) {
        if (node == null) return 0;
        int count = node.isEnd ? 1 : 0;
        for (Node child : node.children) {
            count += countWords(child);
        }
        return count;
    }

    // Возвращаем все слова в дереве
    public MyList<String> getAllWords() {
        return getByPrefix("");
    }

    // Проверяем, пустое ли дерево
    public boolean isEmpty() {
        return size() == 0;
    }
}

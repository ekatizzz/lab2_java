// Главный класс для демонстрации Trie и Graph

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Trie trie = new Trie();
    private static Graph graph = new Graph();

    public static void main(String[] args) {
        loadSampleData();  // Инициализация демонстрационными данными
        
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║       Реализация префиксного дерева и графа              ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        // Главный цикл программы
        while (true) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> workWithTrie();
                case "2" -> workWithGraph();
                case "0" -> {
                    System.out.println("Выход");
                    return;
                }
                default -> System.out.println("Неверный выбор");
            }
        }
    }

    // Меню и пользовательский интерфейс
    private static void printMainMenu() {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║ 1. Работа с Trie (префиксное дерево)                     ║");
        System.out.println("║ 2. Работа с Graph (граф)                                 ║");
        System.out.println("║ 0. Выход                                                 ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.print("Выберите действие: ");
    }

    // Работа с префиксным деревом
    private static void workWithTrie() {
        while (true) {
            printTrieMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> insertWord();
                case "2" -> searchWord();
                case "3" -> checkPrefix();
                case "4" -> getByPrefix();
                case "5" -> removeWord();
                case "6" -> showAllWords();
                case "7" -> showTrieInfo();
                case "0" -> { return; }
                default -> System.out.println("Неверный выбор");
            }
        }
    }

    // Работа с графом
    private static void workWithGraph() {
        while (true) {
            printGraphMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> addVertex();
                case "2" -> addEdge();
                case "3" -> showGraph();
                case "4" -> dfsTraversal();
                case "5" -> bfsTraversal();
                case "6" -> showGraphInfo();
                case "0" -> { return; }
                default -> System.out.println("Неверный выбор");
            }
        }
    }

    // Методы для работы с Trie
    private static void printTrieMenu() {
        System.out.println("\n═════════════════ TRIE МЕНЮ ═════════════════");
        System.out.println("1. Добавить слово");
        System.out.println("2. Найти слово");
        System.out.println("3. Проверить префикс");
        System.out.println("4. Слова по префиксу");
        System.out.println("5. Удалить слово");
        System.out.println("6. Все слова");
        System.out.println("7. Информация о дереве");
        System.out.println("0. Назад");
        System.out.print("Выберите: ");
    }

    private static void printGraphMenu() {
        System.out.println("\n════════════════ GRAPH МЕНЮ ════════════════");
        System.out.println("1. Добавить вершину");
        System.out.println("2. Добавить ребро");
        System.out.println("3. Показать граф");
        System.out.println("4. DFS обход");
        System.out.println("5. BFS обход");
        System.out.println("6. Информация о графе");
        System.out.println("0. Назад");
        System.out.print("Выберите: ");
    }

    // Реализации операций с Trie
    private static void insertWord() {
        System.out.print("Введите слово: ");
        String word = scanner.nextLine();
        try {
            trie.insert(word);
            System.out.println("✔️ Слово добавлено: " + word);
        } catch (Exception e) {
            System.out.println("❗️ Ошибка: " + e.getMessage());
        }
    }

    private static void searchWord() {
        System.out.print("Введите слово для поиска: ");
        String word = scanner.nextLine();
        boolean found = trie.contains(word);
        System.out.println(found ? "✔️ Слово найдено" : "✖️ Слово не найдено");
    }

    private static void checkPrefix() {
        System.out.print("Введите префикс: ");
        String prefix = scanner.nextLine();
        boolean exists = trie.startsWith(prefix);
        System.out.println(exists ? "✔️ Есть слова с таким префиксом" : "✖️ Нет слов с таким префиксом");
    }

    private static void getByPrefix() {
        System.out.print("Введите префикс: ");
        String prefix = scanner.nextLine();
        MyList<String> words = trie.getByPrefix(prefix);
        
        if (words.isEmpty()) {
            System.out.println("✖️ Слова не найдены");
        } else {
            System.out.println("Найдено слов: " + words.size());
            for (int i = 0; i < words.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + words.get(i));
            }
        }
    }

    private static void removeWord() {
        System.out.print("Введите слово для удаления: ");
        String word = scanner.nextLine();
        boolean removed = trie.remove(word);
        System.out.println(removed ? "✔️ Слово удалено" : "✖️ Слово не найдено");
    }

    private static void showAllWords() {
        MyList<String> allWords = trie.getAllWords();
        System.out.println("Всего слов: " + allWords.size());
        for (int i = 0; i < allWords.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + allWords.get(i));
        }
    }

    private static void showTrieInfo() {
        System.out.println("Информация о Trie:");
        System.out.println("   Количество слов: " + trie.size());
        System.out.println("   Пустое дерево: " + (trie.isEmpty() ? "Да" : "Нет"));
    }

    // Реализации операций с Graph
    private static void addVertex() {
        System.out.print("Введите номер вершины: ");
        try {
            int vertex = Integer.parseInt(scanner.nextLine());
            graph.addVertex(vertex);
            System.out.println("✔️ Вершина добавлена: " + vertex);
        } catch (Exception e) {
            System.out.println("❗️ Ошибка: " + e.getMessage());
        }
    }

    private static void addEdge() {
        System.out.print("Введите начальную вершину: ");
        try {
            int from = Integer.parseInt(scanner.nextLine());
            System.out.print("Введите конечную вершину: ");
            int to = Integer.parseInt(scanner.nextLine());
            graph.addEdge(from, to);
            System.out.println("✔️ Ребро добавлено: " + from + " → " + to);
        } catch (Exception e) {
            System.out.println("❗️ Ошибка: " + e.getMessage());
        }
    }

    private static void showGraph() {
        System.out.println(graph.toString());
    }

    private static void dfsTraversal() {
        System.out.print("Введите начальную вершину для DFS: ");
        try {
            int start = Integer.parseInt(scanner.nextLine());
            MyList<Integer> traversal = graph.dfs(start);
            System.out.println("DFS обход: " + traversal);
        } catch (Exception e) {
            System.out.println("❗️ Ошибка: " + e.getMessage());
        }
    }

    private static void bfsTraversal() {
        System.out.print("Введите начальную вершину для BFS: ");
        try {
            int start = Integer.parseInt(scanner.nextLine());
            MyList<Integer> traversal = graph.bfs(start);
            System.out.println("BFS обход: " + traversal);
        } catch (Exception e) {
            System.out.println("❗️ Ошибка: " + e.getMessage());
        }
    }

    private static void showGraphInfo() {
        System.out.println("Информация о Graph:");
        System.out.println("   Вершин: " + graph.getVertexCount());
        System.out.println("   Рёбер: " + graph.getEdgeCount());
        System.out.println("   Тип: " + (graph.isDirected() ? "Ориентированный" : "Неориентированный"));
    }

    // Инициализация демонстрационными данными
    private static void loadSampleData() {
        // Загрузка примеров в Trie
        String[] sampleWords = {"apple", "app", "application", "appliance", "banana", "bank", "banner", "band", "cat", "carpet", "car", "card", "garden", "garlic", "international", "communication", "collaboration"};
        for (String word : sampleWords) {
            try {
                trie.insert(word);
            } catch (Exception ignored) {}
        }

        // Создание примера графа
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 4);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);

        System.out.println("✔️ Примеры данных загружены");
    }
}

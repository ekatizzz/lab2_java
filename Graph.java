// Реализация графа с использованием собственных коллекций
 
public class Graph {
    private final MyList<Integer> vertices;               // Список всех вершин графа
    private final MyList<MyList<Integer>> adjacencyList;  // Списки смежности для каждой вершины
    private final boolean directed;                       // Флаг ориентированности графа

    // Конструкторы и базовые методы управления графом
    public Graph() {
        this(false);
    }

    public Graph(boolean directed) {
        this.vertices = new MyList<>();
        this.adjacencyList = new MyList<>();
        this.directed = directed;
    }

    // Добавление вершины (игнорирует дубликаты)
    public void addVertex(int vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            adjacencyList.add(new MyList<>());
        }
    }

    // Добавление ребра между вершинами
    public void addEdge(int from, int to) {
        addVertex(from);  // Гарантирует существование вершин
        addVertex(to);

        int fromIndex = vertices.indexOf(from);
        int toIndex = vertices.indexOf(to);

        MyList<Integer> neighbors = adjacencyList.get(fromIndex);
        if (!neighbors.contains(to)) {
            neighbors.add(to);
        }

        // Для неориентированного графа добавляем обратное ребро
        if (!directed) {
            MyList<Integer> reverseNeighbors = adjacencyList.get(toIndex);
            if (!reverseNeighbors.contains(from)) {
                reverseNeighbors.add(from);
            }
        }
    }

    // Проверка существования вершины
    public boolean hasVertex(int vertex) {
        return vertices.contains(vertex);
    }

    // Проверка существования ребра
    public boolean hasEdge(int from, int to) {
        if (!hasVertex(from) || !hasVertex(to)) {
            return false;
        }

        int fromIndex = vertices.indexOf(from);
        MyList<Integer> neighbors = adjacencyList.get(fromIndex);
        return neighbors.contains(to);
    }

    // Получение соседей вершины
    public MyList<Integer> getNeighbors(int vertex) {
        if (!hasVertex(vertex)) {
            return new MyList<>();
        }

        int index = vertices.indexOf(vertex);
        return adjacencyList.get(index);
    }

    // Обход в глубину (DFS)
    public MyList<Integer> dfs(int startVertex) {
        MyList<Integer> result = new MyList<>();
        if (!hasVertex(startVertex)) return result;

        boolean[] visited = new boolean[vertices.size()];
        dfsHelper(startVertex, visited, result);
        return result;
    }

    // Рекурсивный помощник для DFS
    private void dfsHelper(int vertex, boolean[] visited, MyList<Integer> result) {
        int index = vertices.indexOf(vertex);
        visited[index] = true;
        result.add(vertex);

        // Рекурсивно посещаем всех непосещенных соседей
        MyList<Integer> neighbors = getNeighbors(vertex);
        for (int i = 0; i < neighbors.size(); i++) {
            int neighbor = neighbors.get(i);
            int neighborIndex = vertices.indexOf(neighbor);
            if (!visited[neighborIndex]) {
                dfsHelper(neighbor, visited, result);
            }
        }
    }

    // Обход в ширину (BFS)
    public MyList<Integer> bfs(int startVertex) {
        MyList<Integer> result = new MyList<>();
        if (!hasVertex(startVertex)) return result;

        boolean[] visited = new boolean[vertices.size()];
        MyList<Integer> queue = new MyList<>();  // Используется как очередь

        int startIndex = vertices.indexOf(startVertex);
        visited[startIndex] = true;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int current = queue.removeAt(0);  // Извлекаем из начала
            result.add(current);

            // Добавляем всех непосещенных соседей в очередь
            MyList<Integer> neighbors = getNeighbors(current);
            for (int i = 0; i < neighbors.size(); i++) {
                int neighbor = neighbors.get(i);
                int neighborIndex = vertices.indexOf(neighbor);
                if (!visited[neighborIndex]) {
                    visited[neighborIndex] = true;
                    queue.add(neighbor);
                }
            }
        }

        return result;
    }

    // Статистика графа
    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        int count = 0;
        for (int i = 0; i < adjacencyList.size(); i++) {
            count += adjacencyList.get(i).size();
        }
        return directed ? count : count / 2;  // Для неориентированного делим на 2
    }

    public boolean isDirected() {
        return directed;
    }

    // Строковое представление графа
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph (").append(directed ? "directed" : "undirected").append(")\n");
        sb.append("Vertices: ").append(vertices.size()).append(", Edges: ").append(getEdgeCount()).append("\n");
        
        // Вывод списков смежности
        for (int i = 0; i < vertices.size(); i++) {
            int vertex = vertices.get(i);
            sb.append(vertex).append(" -> ").append(adjacencyList.get(i)).append("\n");
        }
        
        return sb.toString();
    }
}

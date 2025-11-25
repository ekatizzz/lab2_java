// Реализация динамического массива
  
public class MyList<T> {
    private Object[] data;    // Внутренний массив для хранения элементов
    private int size;         // Текущее количество элементов

    public MyList() {
        this(10);
    }

    public MyList(int initialCapacity) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be positive");
        }
        data = new Object[initialCapacity];
        size = 0;
    }

    // Добавление элемента с автоматическим расширением
    public void add(T value) {
        ensureCapacity(size + 1);
        data[size++] = value;
    }

    // Получение элемента по индексу
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    // Установка значения по индексу
    public void set(int index, T value) {
        checkIndex(index);
        data[index] = value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Проверка наличия элемента
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    // Поиск индекса элемента
    public int indexOf(T value) {
        if (value == null) {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (value.equals(data[i])) return i;
            }
        }
        return -1;
    }

    // Удаление по индексу со сдвигом
    @SuppressWarnings("unchecked")
    public T removeAt(int index) {
        checkIndex(index);
        T removed = (T) data[index];

        // Сдвигаем элементы
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        data[--size] = null;
        return removed;
    }

    // Удаление по значению
    public boolean remove(T value) {
        int index = indexOf(value);
        if (index != -1) {
            removeAt(index);
            return true;
        }
        return false;
    }

    // Очистка коллекции
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    // Строковое представление
    @Override
    public String toString() {
        if (size == 0) return "[]";
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // Увеличение емкости при необходимости
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > data.length) {
            int newCapacity = Math.max(data.length * 2, minCapacity);
            Object[] newData = new Object[newCapacity];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

    // Проверка корректности индекса
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}

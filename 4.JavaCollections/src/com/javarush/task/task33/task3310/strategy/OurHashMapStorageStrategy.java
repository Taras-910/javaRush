package com.javarush.task.task33.task3310.strategy;
public class OurHashMapStorageStrategy implements StorageStrategy {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    int size;
    int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    float loadFactor = DEFAULT_LOAD_FACTOR;

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        Entry[] tab = table;
        for (int i = 0; i < tab.length; i++)
            for (Entry e = tab[i]; e != null; e = e.next)
                if (value.equals(e.value))
                    return true;
        return false;
    }

    @Override
    public Long getKey(String value) {
        Entry[] tab = table;
        for (int i = 0; i < tab.length; i++)
            for (Entry e = tab[i]; e != null; e = e.next)
                if (value.equals(e.value)) {
                    return e.getKey();
                }
        return null;
    }

    @Override
    public String getValue(Long key) {

        int hash = hash((long) key.hashCode());
        for (Entry e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
            Long k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
                return e.value;
        }
        return null;
    }


    public final int hash(Long key) {
        long h = key;
        h ^= (h >>> 20) ^ (h >>> 12);
        return (int)(h ^ (h >>> 7) ^ (h >>> 4));
    }


    public int indexFor(int hash, int length) {
        return hash & length - 1;
    }

    final Entry getEntry(Long key) {
        int hash = hash(key);
        for (Entry entry = table[indexFor(hash, table.length)]; entry != null; entry = entry.next) {
            if (entry.hashCode() == hash && entry.getKey().equals(key))
                return entry;
        }
        return null;
    }

    @Override
    public void put(Long key, String value) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
//        System.out.print("OurHashMapS 80: hash = "+hash+" index = "+index+" ("+(table.length)+")");
        for (Entry entry = table[index]; entry != null; entry = entry.next) {
            if (entry.hash == hash && key.equals(entry.getKey())) {
                entry.value = value;
                createEntry(hash, key, value, index);
            }
        }
        addEntry(hash, key, value, index);
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry entry = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, entry);
   //     System.out.println("OurHashMapStorageStrategy 111: table.length = " +table.length+" size = "+size+ " threshold = "+threshold);
//        System.out.println("OurHashMapS 95: bucketIndex = "+bucketIndex+" table.length = "+table.length);
 //       System.out.print("OurHashMapS 96: addEntry -> *2 if "+ size +" > "+  threshold);
        if (size++ >= threshold) {
 //           System.out.println(" true -> resize(2 * table.length)");
            resize(2 * table.length);
        }
//        System.out.println(" false выход из addEntry -> OurHashMapS put -> Shotener put -> Shotene GetId// индекс корзины =" + bucketIndex +
//                " размер корзины = " + table.length);
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry entry = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, entry);
        size++;
//     System.out.println("OurHashMapStorageStrategy 123: table.length = " +table.length+" size = "+size);

    }

    void transfer(Entry[] newTable) {
        Entry[] src = table;
        int newCapacity = newTable.length;
        for (int i = 0; i < src.length; i++) {
            Entry entry = src[i];
            if (entry != null) {
                src[i] = null;
                while (entry != null) {
                    Entry next = entry.next;
                    int index = indexFor(entry.hash, newCapacity);
                    entry.next = newTable[index];
                    newTable[index] = entry;
                    entry = next;
                }
            }
        }
    }

    void resize(int newCapacity) {
        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (threshold == table.length*loadFactor) {
            threshold = threshold*2;
//        System.out.println("OurHashMapS 148: oldCapacity = " +oldCapacity+" newCapacity = "+newCapacity+ " threshold = "+threshold);
        }

        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
//        System.out.println(" table.length = "+table.length);
        threshold = (int) (newCapacity * loadFactor);
    }
}


/*
Shortener (8)
Добавь и реализуй класс OurHashMapStorageStrategy, используя класс Entry из предыдущей подзадачи.
  Класс OurHashMapStorageStrategy должен реализовывать интерфейс StorageStrategy.
8.1. Добавь в класс следующие поля:
8.1.1. static final int DEFAULT_INITIAL_CAPACITY = 16;
8.1.2. static final float DEFAULT_LOAD_FACTOR = 0.75f;
8.1.3. Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
8.1.4. int size;
8.1.5. int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
8.1.6. float loadFactor = DEFAULT_LOAD_FACTOR;
8.2. Реализуй в классе следующие вспомогательные методы:
8.2.1. int hash(Long k)
8.2.2. int indexFor(int hash, int length)
8.2.3. Entry getEntry(Long key)
8.2.4. void resize(int newCapacity)
8.2.5. void transfer(Entry[] newTable)
8.2.6. void addEntry(int hash, Long key, String value, int bucketIndex)
8.2.7. void createEntry(int hash, Long key, String value, int bucketIndex)
8.3. Добавь в класс публичные методы, которые требует интерфейс StorageStrategy.
Какие-либо дополнительные поля класса не использовать. Методы, не описанные в задании, реализовывать не нужно.
Если возникнут вопросы как реализовать какой-то метод или что он должен делать, то ты всегда можешь посмотреть, как работает похожий метод в HashMap.
Можешь добавить в метод main класса Solution тестирование новой стратегии. Запусти и сравни время работы двух стратегий на одинаковом количестве элементов.


Требования:
1. Класс OurHashMapStorageStrategy должен поддерживать интерфейс StorageStrategy.
2. В классе OurHashMapStorageStrategy должны быть созданы все необходимые поля (согласно условию задачи).
3. Методы интерфейса StorageStrategy должны быть реализованы в OurHashMapStorageStrategy таким образом,
     чтобы обеспечивать корректную работу Shortener созданного на его основе.
4. В классе OurHashMapStorageStrategy должны присутствовать все вспомогательные методы перечисленные в условии задачи.
 */


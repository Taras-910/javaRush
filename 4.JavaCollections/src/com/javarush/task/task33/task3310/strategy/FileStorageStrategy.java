
package com.javarush.task.task33.task3310.strategy;

public class FileStorageStrategy implements StorageStrategy {
    private FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    private int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    private long maxBucketSize = 0;

    public FileStorageStrategy() {
        for (int i = 0; i < DEFAULT_INITIAL_CAPACITY; i++) {
            table[i] = new FileBucket();
        }
    }
    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    public final int hash(Long key) {
        int h = Math.toIntExact(key);
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);

    }

    public int indexFor(int hash, int length) {
        return hash & length - 1;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        if (value == null) return false;
        for (FileBucket t : table) {
            for (Entry e = t.getEntry(); e != null; e = e.next)
                if (value.equals(e.value))
                    return true;
        }
        return false;
    }

    @Override
    public Long getKey(String value) {
//        System.out.println("FileSS 54: вход в getKey");
        if (value == null) return 0l;
        for (FileBucket t : table) {                                                // проход по корзинам в таблице
            for (Entry entry = t.getEntry(); entry != null; entry = entry.next)      // проход по списку в корзине
                if (entry.getValue().equals(value)){
//                    System.out.println("FileSS 59: entry.getKey() = "+entry.getKey());
                    return entry.getKey();
                }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        int hash = hash(key);
        for (FileBucket t: table){
            for (Entry entry = t.getEntry(); entry != null; entry = entry.next) {
                if (entry.hash == hash &&  entry.getKey().equals(key)){
                    return entry.value;
                }
            }
    }
        return null;
    }

    final Entry getEntry(Long key) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for (FileBucket t : table){
            for(Entry entry = t.getEntry(); entry != null; entry = entry.next) {
                if (entry.hash == hash && entry.getKey().equals(key)) {
                    return entry;
                }
            }
        }
        return null;
    }

    @Override
    public void put(Long key, String value) {
//        System.out.println("FileSS 94: вход в put");
        int hash = hash(key);
        int index = indexFor(hash, table.length);
 //       System.out.println("FileSS 94: key = "+key+" value = "+ value+" hash ="+hash+" index = "+index );
        if (table[index] != null) {
            for (Entry entry = table[index].getEntry();entry != null; entry =entry.next) {  // проход по всему листу корзины
                if (entry.getKey().equals(key)) {
                    entry.value = value;
                    return;
                }
            }
            addEntry(hash, key, value, index);

        } else {
            createEntry(hash, key, value, index);
        }
//        System.out.println("FileSS 110: выход из put");
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash,key,value,e));
        size++;
//        System.out.print(" FileSS 117: addEntry - if *2?   ");
        if (table[bucketIndex].getFileSize() > getBucketSizeLimit()) {
 //           System.out.println(table[bucketIndex].getFileSize()+" > "+ bucketSizeLimit +"  = true -> resize ");
            resize(2 * table.length);
//            System.out.println("FileSS 116: выход из addEntry -> *2 -> on resize // индекс корзины =" + bucketIndex + " размер корзины = "
//                    + table[bucketIndex].getFileSize() + " bucketSizeLimit = " + bucketSizeLimit);
        }
//        else System.out.println(" = false");
//        System.out.println("FileSS 125:  -> возврат -> FileSS put -> Shotener put -> Shotener getID");
//        System.out.print("FileSS 126: выход из addEntry  // индекс корзины =" + bucketIndex + " размер корзины = "
//                + table[bucketIndex].getFileSize() + " bucketSizeLimit = " + bucketSizeLimit);
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        FileBucket e = table[bucketIndex];
        table[bucketIndex].putEntry(new Entry(hash, key, value, e.getEntry()));
        size++;
//        System.out.println("OurHashMapStorageStrategy 134: table.length = " +table.length+" size = "+size);

    }

    void transfer(FileBucket[] newTable) {
//        System.out.println("FileSS 139: вход в transfer");
        FileBucket[] src = table;
        for (int i = 0; i < src.length; i++) {
            Entry entry = src[i].getEntry();
            if (entry != null) {
                while (entry != null) {
                    Entry next = entry.next;
                    int newIndex = indexFor(entry.hash, newTable.length);
                    if (newTable[newIndex] == null) {
                        entry.next = null;
                        newTable[newIndex] = new FileBucket();
                    } else {
                        entry.next = newTable[newIndex].getEntry();
                    }
                    newTable[newIndex].putEntry(entry);
                    entry = next;
                }
            }
            src[i].remove();
        }
 //       System.out.println("FileSS 159: выход из transfer");
    }

    public void resize(int newCapacity) {
//        System.out.println("FileSS 163: вход в resize");
        FileBucket[] oldTable = table;
        int oldCapacity = oldTable.length;

        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
//        System.out.println("FileSS 169: вызов transfer из resize");
        table = newTable;
//        System.out.println("FileSS 171: выход resize -> oldCapacity = " +oldCapacity+" newCapacity = "+newCapacity);
    }
}


/*
package com.javarush.task.task33.task3310.strategy;

import java.util.Map;

import static java.util.Objects.hash;

public class FileStorageStrategy implements StorageStrategy{

    public FileStorageStrategy() {
        for(int i = 0; i < table.length; i++){
            table[i] = new FileBucket();
        }
    }

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 1000;
    FileBucket[] table;
    int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize = 0;        // maxBucketSize - это размер самого большого имеющегося бакета
                               // классе FileBucket добавил поле size и считал у каждого объекта его личный размер

    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    public final int hash(Long key) {
        int h = 0;
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);

    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        FileBucket[] tab = table;
        for (int i = 0; i < tab.length; i++)
            for (FileBucket e = tab[i]; e != null; e = e.next)
                if (value.equals(e.value))
                    return true;
        return false;    }

    @Override
    public Long getKey(String value) {
        FileBucket[] tab = table;
        for (int i = 0; i < tab.length; i++)
            for (FileBucket e = tab[i]; e != null; e = (FileBucket)e.next)
                if (value.equals(e.value)) {
                    return e.getKey();
                }
        return null;
    }

    @Override
    public String getValue(Long key) {

        int hash = hash((long) key.hashCode());
        for (FileBucket e = table[indexFor(hash, table.length)]; e != null; e = (FileBucket)e.next) {
            Long k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
                return e.value;
        }
        return null;
    }



public Entry getEntry(Long key) {
        int hash = (key == null) ? 0 : hash(key);
        for (Entry e = table[indexFor(hash, table.length)].getEntry();
             e != null;
             e = e.next) {
            Object k;
            if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                return e;
        }
        return null;
    }

    public int indexFor(int hash, int length) {
        return hash & length - 1;
    }

    @Override
    public void put(Long key, String value) {
        int hash = hash((long) key.hashCode());
        int i = indexFor(hash, table.length);
        FileBucket e;
        for ( e = table[i]; e != null; e = (FileBucket)e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                e.value = value;
                createEntry(hash, key, value, i);
            }
        }
        size++;
        addEntry(hash, key, value, i);
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex] = new FileBucket(hash, key, value, e);
//        if (size++ >= bucketSizeLimit)
        size++;

        if(table[bucketIndex].getFileSize() > bucketSizeLimit){
            resize(2 * table.length);
        }
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, e);
        size++;
    }

//     * Transfers all entries from current table to newTable.

   void transfer(FileBucket[] newTable) {
        FileBucket[] src = table;
        int newCapacity = newTable.length;
        for (int j = 0; j < src.length; j++) {
            FileBucket e = (FileBucket)src[j];
            if (e != null) {
                src[j] = null;
                while (true) {
                    FileBucket next = (FileBucket)e.next;
                    int i = indexFor(e.hash, newCapacity);
                    e.next = newTable[i];
                    newTable[i] = (FileBucket)e;
                    e = next;
                    if (e == null) return;
                }
            }
        }
    }

//удваивать количество ведер не когда количество элементов size станет больше какого-то порога,
//      а когда размер одного из ведер (файлов) стал больше bucketSizeLimit

    void resize(int newCapacity) {
        FileBucket[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == Integer.MAX_VALUE) {
        System.out.println("OFileStorageStrategy 170: oldCapacity = " +oldCapacity+" newCapacity = "+newCapacity+ " maxBucketSize = "+maxBucketSize);
            return;
        }

        FileBucket[] newTable = new FileBucket[newCapacity];
        transfer(newTable);
        table = newTable;
        System.out.println(" table.length = "+table.length);
//        removeMapping(oldTable);
   }

    public void remove(Long key) {
        int hash = hash((long) key.hashCode());
        for (FileBucket e = table[indexFor(hash, table.length)]; e != null; e = (FileBucket)e.next) {
            Long k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k)))  remove(key);
        }
    }
    //При реализации метода resize(int newCapacity) проследи, чтобы уже не нужные файлы были удалены (вызови метод remove()
    final FileBucket removeMapping(Object o) {
        if (!(o instanceof Map.Entry))
            return null;

        Map.Entry entry = (Map.Entry) o;
        Object key = entry.getKey();
        int hash = (key == null) ? 0 : hash(key.hashCode());
        int i = indexFor(hash, table.length);
        Entry prev = table[i];
        Entry e = prev;

        while (e != null) {
            Entry next = e.next;
            if (e.hash == hash && e.equals(entry)) {
//                modCount++;
                size--;
                if (prev == e)
                    table[i] = (FileBucket) next;
                else
                    prev.next = next;
 //               e.recordRemoval(this);
                return (FileBucket)e;
            }
            prev = e;
            e = next;
        }

        return (FileBucket)e;
    }


}


 */

















/*
Создай и реализуй класс FileStorageStrategy. Он должен:
10.1. Реализовывать интерфейс StorageStrategy.
10.2. Использовать FileBucket в качестве ведер (англ. bucket).

Подсказка: класс должен содержать поле FileBucket[] table.

10.3. Работать аналогично тому, как это делает OurHashMapStorageStrategy,


      но удваивать количество ведер не когда количество элементов size станет больше какого-то порога,
      а когда размер одного из ведер (файлов) стал больше bucketSizeLimit.


10.3.1. Добавь в класс поле long bucketSizeLimit.
10.3.2. Проинициализируй его значением по умолчанию, например, 10000 байт.



10.3.3. Добавь сеттер и геттер для этого поля.
10.4. При реализации метода resize(int newCapacity) проследи, чтобы уже не нужные файлы были удалены (вызови метод remove()).

Проверь новую стратегию в методе main(). Учти, что стратегия FileStorageStrategy гораздо более медленная, чем остальные.
Не используй большое количество элементов для теста, это может занять оооочень много времени.
Запусти программу и сравни скорость работы всех 3х стратегий.

P.S. Обрати внимание на наличие всех необходимых полей в классе FileStorageStrategy, по аналогии с OurHashMapStorageStrategy:
static final int DEFAULT_INITIAL_CAPACITY
static final long DEFAULT_BUCKET_SIZE_LIMIT
FileBucket[] table
int size
private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT
long maxBucketSize



Shortener (9)
Напишем еще одну стратегию, назовем ее FileStorageStrategy. Она будет очень похожа на стратегию OurHashMapStorageStrategy,
но в качестве ведер (англ. buckets) будут файлы. Я знаю, ты знаешь о каких buckets идет речь, если нет - повтори внутреннее устройство HashMap.
9.1. Создай класс FileBucket в пакете strategy.
9.2. Добавь в класс поле Path path. Это будет путь к файлу.
9.3. Добавь в класс конструктор без параметров, он должен:
9.3.1. Инициализировать path временным файлом. Файл должен быть размещен в директории для временных файлов и иметь случайное имя.

Подсказка: Files.createTempFile.

9.3.2. Создавать новый файл, используя path. Если такой файл уже есть, то заменять его.
9.3.3. Обеспечивать удаление файла при выходе из программы.

Подсказка: deleteOnExit().

9.4. Добавь в класс методы:
9.4.1. long getFileSize(), он должен возвращать размер файла на который указывает path.
9.4.2. void putEntry(Entry entry) - должен сериализовывать переданный entry в файл. Учти, каждый entry может содержать еще один entry.
9.4.3. Entry getEntry() - должен забирать entry из файла. Если файл имеет нулевой размер, вернуть null.
9.4.4. void remove() - удалять файл на который указывает path.
Конструктор и методы не должны кидать исключения.

Требования:
1. В классе FileBucket должно быть создано поле path типа Path.
2. Конструктор без параметров класса FileBucket должен быть реализован в соответствии с условием задачи.
3. Метод getFileSize должен возвращать размер файла на который указывает path.
4. Метод putEntry должен сериализовывать полученный объект типа Entry в файл на который указывает path,
    чтобы получить OutputStream используй метод Files.newOutputStream.
5. Метод getEntry должен десериализовывать объект типа Entry из файл на который указывает path,
    чтобы получить InputStream используй метод Files.newInputStream.
6. Метод remove должен удалять файл на который указывает path с помощью метода Files.delete().
 */

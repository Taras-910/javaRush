package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.StorageStrategy;
import org.junit.Test;

//Наше хранилище будет оперировать двумя понятиями: ключ и значение. Ключом будет идентификатор строки, а значением сама строка.
public class Shortener implements StorageStrategy {
    private String string;                            // будет возвращать строку для заданного идентификатора или null, если передан неверный идентификатор
    private Long id;                                  // будет возвращать идентификатор id для заданной строки
    private Long lastId = 0L;                         // последнее значение идентификатора, которое было использовано при добавлении новой строки в хранилище
    private StorageStrategy storageStrategy;                     // стратегия хранения данных

    public Shortener(StorageStrategy storageStrategy) {

        this.storageStrategy = storageStrategy;
    }
    @Test
    public synchronized Long getId(String string){                // есть ли переданное значение в хранилище, если есть - вернуть его ключ
//        System.out.print("\nShotener 17: вход в getId("+string+") (containsValue(string) ? = ");
        if(containsValue(string)){
//            System.out.println("Shotener 17: такой элемент уже есть");
            return getKey(string);
        }
        else{
 //           System.out.println(" Shotener 17: добавляем элемент ");
            lastId++;
//            System.out.println("Shotener 25: вызов put("+lastId+" "+string+")");
            put(lastId,string);
//            System.out.println("Shotener 28: проверка наличия элемента value ? = " + containsValue(string));
//            System.out.println("Shotener 25: ->  вызов FileSS: getKey("+string+")");
            return storageStrategy.getKey(string);                 // ->
        }
    }
    @Test
    public synchronized String getString(Long id){                 // вернуть строку по заданному идентификатору (ключу)

        return storageStrategy.getValue(id);
    }

    @Override
    public boolean containsKey(Long key) {                          // true, если хранилище содержит переданный ключ
        return storageStrategy.containsKey(key);
    }

    @Override
    public boolean containsValue(String value) {                    // true, если хранилище содержит переданное значение
        return storageStrategy.containsValue(value);
    }

    @Override
    public void put(Long key, String value) {                       // добавить в хранилище новую пару ключ - значение
        storageStrategy.put(key,value);
    }

    @Override
    public Long getKey(String value) {                               // вернуть ключ для переданного значения
        return storageStrategy.getKey(value);
    }

    @Override
    public String getValue(Long key) {                                // вернуть значение для переданного ключа
        return storageStrategy.getValue(key);
    }
}

/*
Shortener (7)
Приступим к реализации второй стратегии OurHashMapStorageStrategy.
Она не будет использовать готовый HashMap из стандартной библиотеки, а будет сама являться коллекцией.

7.1. Разберись как работает стандартный HashMap, посмотри его исходники или погугли статьи на эту тему.
7.2. Если ты честно выполнил предыдущий пункт, то ты знаешь для чего используется класс Node поддерживающий интерфейс Entry внутри HashMap.
       Создай свой аналог внутри пакета strategy. Это должен быть обычный, не вложенный, не generic класс. Сделай его публичным.
       В отличии от класса Node из HashMap, наш класс будет поддерживать только интерфейс Serializable и будет называться Entry.
7.3. Добавь в Entry следующие поля: Long key, String value, Entry next, int hash.
       Как видишь, наша реализация будет поддерживать только тип Long для ключа и только String для значения. Область видимости полей оставь по умолчанию.
7.4. Добавь и реализуй конструктор Entry(int hash, Long key, String value, Entry next).
7.5. Добавь и реализуй методы: Long getKey(), String getValue(), int hashCode(), boolean equals() и String toString().
       Реализовывать остальные методы оригинального Entry не нужно, мы пишем упрощенную версию.

Требования:
1. В классе Entry должны быть созданы поля перечисленные в условии задачи.
2. В классе Entry должен быть реализован конструктор с четырьмя параметрами (int, Long, String, Entry) инициализирующий соответствующие поля класса.
3. Метод getKey должен возвращать значение поля key.
4. Метод getValue должен возвращать значение поля value.
5. Метод toString должен возвращать строку формата key + "=" + value.
6. Методы hashCode и equals должны быть корректно реализованы используя для сравнения поля key и value.


Shortener (5)
Давай напишем наше первое хранилище (стратегию хранилища). Внутри оно будет содержать обычный HashMap. Все стратегии будем хранить в пакете strategy.
5.1. Создай класс HashMapStorageStrategy, реализующий интерфейс StorageStrategy.
5.2. Добавь в класс поле HashMap<Long, String> data. В нем будут храниться наши данные.
5.3. Реализуй в классе все необходимые методы. Реализации методов должны использовать поле data. Дополнительные поля не создавать.

Требования:
1. Класс HashMapStorageStrategy должен поддерживать интерфейс StorageStrategy.
2. В классе HashMapStorageStrategy должно быть создано и инициализировано поле data типа HashMap.
3. В классе HashMapStorageStrategy должен быть корректно реализован метод containsKey.
4. В классе HashMapStorageStrategy должен быть корректно реализован метод containsValue.
5. В классе HashMapStorageStrategy должен быть корректно реализован метод put.
6. В классе HashMapStorageStrategy должен быть корректно реализован метод getValue.
7. В классе HashMapStorageStrategy должен быть корректно реализован метод getKey.


Shortener (3)
Вернемся к классу Shortener:
3.1. Добавь в него поле Long lastId. Проинициализируй его нулем. Это поле будет
отвечать за последнее значение идентификатора, которое было использовано при добавлении новой строки в хранилище.
3.2. Добавь поле StorageStrategy storageStrategy в котором будет храниться стратегия хранения данных.
3.3. Добавь конструктор, который принимает StorageStrategy и инициализирует соответствующее поле класса.

3.4. Реализуй метод getId, он должен:
3.4.1. Проверить есть ли переданное значение в хранилище, если есть - вернуть его ключ.
3.4.2. Если преданного значения нет в хранилище, то:
3.4.2.1. Увеличить значение lastId на единицу;
3.4.2.2. Добавить в хранилище новую пару ключ-значение (новое значение lastId и переданную строку);
3.4.2.3. Вернуть новое значение lastId.

3.5. Реализуй метод getString, он должен вернуть строку по заданному идентификатору (ключу).
3.6. Предусмотреть возможность вызова методов getId и getString из разных потоков добавив соответствующий модификатор к заголовкам методов.

Требования:
1. В классе Shortener должно быть создано приватное поле Long lastId инициализированное нулем.
2. В классе Shortener должно быть создано приватное поле storageStrategy типа StorageStrategy.
3. Конструктор класса Shortener должен принимать один параметр типа StorageStrategy и инициализировать им поле storageStrategy.
4. Метод getId должен быть реализован в соответствии с условием задачи.
5. Метод getString должен быть реализован в соответствии с условием задачи.


Shortener (2)
Укорачиватель Shortener будет поддерживать разные стратегии хранения данных (строк и их идентификаторов).
Все эти стратегии будут наследоваться от интерфейса StorageStrategy. Почитай подробнее про паттерн Стратегия на Вики.
Наше хранилище будет оперировать двумя понятиями: ключ и значение. Ключом будет идентификатор строки, а значением сама строка.

2.1. Добавь интерфейс StorageStrategy в пакет strategy.
2.2. Добавь в интерфейс следующие методы:
2.2.1. boolean containsKey(Long key) - должен вернуть true, если хранилище
содержит переданный ключ.
2.2.2. boolean containsValue(String value) - должен вернуть true, если хранилище
содержит переданное значение.
2.2.3. void put(Long key, String value) - добавить в хранилище новую пару ключ -
значение.
2.2.4. Long getKey(String value) - вернуть ключ для переданного значения.
2.2.5. String getValue(Long key) - вернуть значение для переданного ключа.

Требования:
1. В интерфейсе StorageStrategy должен быть объявлен метод boolean containsKey(Long key).
2. В интерфейсе StorageStrategy должен быть объявлен метод boolean containsValue(String value).
3. В интерфейсе StorageStrategy должен быть объявлен метод void put(Long key, String value).
4. В интерфейсе StorageStrategy должен быть объявлен метод Long getKey(String value).
5. В интерфейсе StorageStrategy должен быть объявлен метод boolean String getValue(Long key).
6. Интерфейс StorageStrategy должен быть создан в пакете strategy.


Shortener (1)
Давай напишем укорачиватель Shortener. Это будет некий аналог укорачивателя
ссылок Google URL Shortener (https://goo.gl), но мы расширим его функциональность и
сделаем консольным. Он будет сокращать не только ссылки, но и любые строки.
Наш Shortener - это класс, который может для любой строки вернуть некий
уникальный идентификатор и наоборот, по ранее полученному идентификатору
вернуть строку.

Два дополнительных требования к Shortener:
- для двух одинаковых строк должен возвращаться один и тот же идентификатор;
- он должен поддерживать столько строк, сколько значений может принимать long,
именно этот тип будет использоваться для идентификатора.
Первое требование очень сильно влияет на производительность, т.к. при получении
идентификатора для новой строки мы должны проверить не обрабатывалась ли эта
строка ранее, чтобы вернуть старый идентификатор.

1.1. Объяви класс Shortener.
1.2. Добавь методы заглушки в объявленный класс:
1.2.1. Long getId(String string) - будет возвращать идентификатор id для заданной
строки.
1.2.2. String getString(Long id) - будет возвращать строку для заданного
идентификатора или null, если передан неверный идентификатор.

1.3. Создай класс Solution с пустым методом main.

P.S. Все методы делай публичными, а поля приватными, если нет явных указаний касательно модификаторов доступа.

Требования:
1. В классе Shortener должен быть объявлен метод public Long getId(String string).
2. В классе Shortener должен быть объявлен метод public String getString(Long id).
3. В классе Solution должен быть объявлен метод public static void main(String[] args).



 */
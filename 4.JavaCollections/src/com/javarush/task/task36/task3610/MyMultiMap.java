package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        return values().size();
    }
    @Override
    public V put(K key, V value) {
        if(!map.containsKey(key)){
            List<V> list = new ArrayList<V>();
            list.add(value);
            map.put(key, list);
            return null;
        } else{
            V object = map.get(key).get(map.get(key).size()-1);
            if(map.get(key).size() < repeatCount){
                List list = map.get(key);
                list.add(value);
                map.put(key,list);
            }
            else{
                map.get(key).remove(0);
                map.get(key).add(value);
            }
            return object;
        }
    }
    @Override
    public V remove(Object key) {
        if(map.containsKey(key) && map.get(key).size() != 0){
            V object = map.get(key).get(0);
            map.get(key).remove(0);
            if (map.get(key).size() == 0) map.remove(key);
            return object;
        }
        return null;
    }
    @Override
    public Set<K> keySet() {
        HashSet set = new HashSet(map.keySet());
        return set;
    }
    @Override
    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        Iterator<Map.Entry<K, List<V>>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<K, List<V>> pair = iter.next();
            K key = pair.getKey();
            V value = (V) pair.getValue();
            list.addAll((Collection<? extends V>) value);
        }
        return list;
    }
    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }
    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}

/*
MyMultiMap
Амиго, иногда существующих в Java коллекций недостаточно.
Тогда можно либо взять стороннюю реализацию, например, Google Guava или Apache Commons,
либо реализовать свою структуру данных. Сегодня у тебя есть уникальная возможность испытать свои силы и написать часть своей структуры данных.

Наша структура данных называется MyMultiMap.
Она параметризована дженериками, наследуется от HashMap, и реализует интерфейсы Cloneable, Serializable.
Особенность нашей мапы будет в том, что конструктор принимает число типа int repeatCount - это количество, сколько значений может хранится по одному ключу.

Реализуй методы:
1) int size() - должен возвращать количество значений в нашей коллекции.
2) V put(K key, V value) - должен добавить элемент value по ключу key.
   Если в мапе такой ключ уже есть, и количество значений по этому ключу меньше, чем repeatCount - то добавь элемент value в конец листа в объекте map.
   Если по такому ключу количество значений равняется repeatCount - то удали из листа в объекте map элемент с индексом ноль, и добавь в конец листа value.
   Метод должен возвращать значение последнего добавленного элемента по ключу key (но не значение, которое мы сейчас добавляем).
   Если по ключу key значений еще нет - верни null.
3) V remove(Object key) - должен удалить элемент по ключу key.
   Если по этому ключу хранится несколько элементов - должен удаляться элемент из листа с индексом ноль.
   Если по какому-то ключу хранится лист размером ноль элементов - удали такую пару ключ : значение.
   Метод должен возвращать элемент, который ты удалил. Если в мапе нет ключа key - верни null.
4) Set<K> keySet() - должен вернуть сет всех ключей, которые есть в мапе map.
5) Collection<V> values() - должен вернуть ArrayList<V> всех значений. Порядок значений в листе не имеет значения.
6) boolean containsKey(Object key) - должен вернуть true, если в мапе присутствует ключ key, иначе вернуть false.
7) boolean containsValue(Object value) - должен вернуть true, если в мапе присутствует значение value, иначе вернуть false.

Смотри пример поведения в методе main().
Метод main() не принимает участия в тестировании.

Требования:
1. Класс MyMultiMap<K, V> должен наследоваться от HashMap<K, V> и реализовать интерфейсы Cloneable, Serializable.
2. В классе MyMultiMap должно присутствовать приватное поле HashMap<K, List<V>> map.
3. Необходимо реализовать метод int size() согласно условию.
4. Необходимо реализовать метод boolean containsKey(Object key) согласно условию.
5. Необходимо реализовать метод boolean containsValue(Object value) согласно условию.
6. Необходимо реализовать метод V put(K key, V value) согласно условию.
7. Необходимо реализовать метод V remove(Object key) согласно условию.
8. Необходимо реализовать метод Set<K> keySet() согласно условию.
9. Необходимо реализовать метод Collection<V> values() согласно условию.
 */
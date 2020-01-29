package com.javarush.task.task34.task3413;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoftCache {
    private Map<Long, SoftReference<AnyObject>> cacheMap = new ConcurrentHashMap<>();

    public AnyObject get(Long key) {
        SoftReference<AnyObject> softReference = cacheMap.get(key);
        if(cacheMap.containsKey(key)) {
            AnyObject object = softReference.get();
            return object;
        }
        else return null;
    }

    public AnyObject put(Long key, AnyObject value) {

        SoftReference<AnyObject> softReference = cacheMap.put(key, new SoftReference<>(value));

        if(softReference!=null) {
            AnyObject object = softReference.get();
            softReference.clear();
            return object;
        }
        else return null;
    }

    public AnyObject remove(Long key) {

        SoftReference<AnyObject> softReference = cacheMap.remove(key);

        if(softReference!=null) {
            AnyObject object = softReference.get();
            softReference.clear();
            return object;
        }
        else return null;
    }
}

/*

//  создаем объект для хранения дополнительной информации о пользователе
//  WeakHashMap<User, StatisticInfo> userStatistics = new WeakHashMap<User, StatisticInfo>();

//  кладем информацию о пользователе в userStatistics
//  User user = session.getUser();
//  userStatistics.put(user, new StatisticInfo (…));

//  получаем информацию о пользователе из userStatistics
//  User user = session.getUser();
//  StatisticInfo statistics = userStatistics.get(user);

//  удаление любой информации о пользователе из userStatistics
//  User user = session.getUser();
//  userStatistics.remove(user);

Кеш на основании SoftReference
Реализуй логику методов get, put, remove класса SoftCache:
Метод AnyObject get(Long key) должен возвращать объект типа AnyObject из мапы cacheMap по ключу key. Если такого ключа в cacheMap нет - верни null.

Метод AnyObject put(Long key, AnyObject value) должен добавлять в мапу пару key : value.
Метод должен вернуть null, если в cacheMap по такому ключу ранее не было значения.
Иначе - верни предыдущее значение value по этому ключу. Не забудь вызвать метод clear() у объекта типа SoftReference<AnyObject>.

Метод AnyObject remove(Long key) должен удалить из мапы cacheMap пару key : value по ключу key.
Метод должен вернуть null, если в cacheMap по такому ключу ранее не было значения. Иначе - верни предыдущее значение value по этому ключу.
Не забудь вызвать метод clear() у объекта типа SoftReference<AnyObject>.

Не изменяй класс AnyObject.
Метод main не принимает участия в тестировании.


Требования:
1. Класс AnyObject не должен быть изменен.
2. В классе SoftCache должно существовать приватное поле Map<Long, SoftReference<AnyObject>> cacheMap.
3. Реализуй метод get согласно условию.
4. Реализуй метод put согласно условию.
 */

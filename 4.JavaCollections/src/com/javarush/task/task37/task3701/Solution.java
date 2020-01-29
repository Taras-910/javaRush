package com.javarush.task.task37.task3701;

/*
Круговой итератор
*/


import java.util.ArrayList;
import java.util.Iterator;

public class Solution<T> extends ArrayList<T> {
    public static void main(String[] args) {
        Solution<Integer> list = new Solution<>();
        list.add(1);
        list.add(2);
        list.add(3);

        int count = 0;
        for (Integer i : list) {
            //1 2 3 1 2 3 1 2 3 1
            System.out.print(i + " ");
            count++;
            if (count == 10) {
                break;
            }
        }
    }
    public Iterator<T> iterator() {
        return new RoundIterator();
    }

    public class RoundIterator implements Iterator <T>{
        Iterator<T> iter = Solution.super.iterator();
        int index = 0;
        @Override
        public boolean hasNext() {
            return Solution.this.size() > 0;
        }

        @Override
        public T next() {
            if (!iter.hasNext()) iter = Solution.super.iterator();
            return iter.next();
        }

/*
        @Override
        public boolean hasNext() {
            if (index >= size()) {
                iter = Solution.super.iterator();
                index = 0;
            }
            return true;
        }

        @Override
        public T next() {
            index++;
            return iter.next();
        }
*/

        @Override
        public void remove() {
            iter.remove();

        }
    }
}

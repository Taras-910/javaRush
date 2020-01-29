package com.javarush.task.task39.task3905;

public class PhotoPaint {
    public boolean paintFill(Color[][] image, int a, int b, Color desiredColor) {
        int y = a;
        int x = b;
        if(x < 0 || y < 0 || image.length <= x || image[0].length <= y) return false;
        Color currentColor = image[x][y];
        if(currentColor == desiredColor) return false;

        if(desiredColor != null){
            paint(image, x, y, desiredColor, currentColor);
        }
         return true;
    }

    public void paint(Color[][] image, int x, int y, Color desiredColor, Color currentColor) {
        if(x >= 0 && y >= 0 && image.length > x && image[0].length > y && image[x][y] == currentColor) {
            image[x][y] = desiredColor;
            paint(image, x,y - 1, desiredColor, currentColor);
            paint(image, x,y + 1, desiredColor, currentColor);
            paint(image,x - 1, y, desiredColor, currentColor);
            paint(image,x + 1, y, desiredColor, currentColor);
        }
        return;
    }
}

/*
Залей меня полностью
В процессе разработки новой версии популярного графического редактора
возникла необходимость реализовать заливку области картинки определенным цветом.

Реализуй метод paintFill в классе PhotoPaint таким образом, чтобы он возвращал:
- false, если цвет начальной точки (координаты приходят в качестве параметров) совпадает с цветом заливки
или если начальные координаты выходят за рамки массива.
- модифицировал входящий массив и возвращал true, если заливка все же может быть выполнена.

Точке с координатами (x, y) соответствует элемент массив с индексом [y][x].
P.S. Если алгоритм работы заливки не очевиден, можешь попрактиковаться в графическом редакторе Paint.

Требования:
1. Метод paintFill должен возвращать false, если переданные координаты выходят за границы изображения.
2. Метод paintFill должен возвращать false, если цвет начальной точки совпадает с цветом заливки.
3. Метод paintFill должен возвращать true и корректно модифицировать изображение, если это возможно.
4. Метод paintFill должен быть публичным.
 */
package com.javarush.task.task39.task3905;

/*
Залей меня полностью
*/
/*public class Solution {
    public static void main(String[] args) {
        Color[][] image = new Color[][]{
                {BLUE}
        };

        new PhotoPaint().paintFill(image, 0, 0, ORANGE);

        System.out.println(image[0][0]);
    }
}*/
public class Solution {
    public static void main(String[] args) {
        PhotoPaint photoPaint = new PhotoPaint();
        Color[][] image = new Color[5][5];
        for(int i = 0; i < image.length; i++){
            for(int j = 0; j < image.length; j++) {
                image[i][j] = Color.BLUE;
            }
        }
        image[3][3] = Color.RED;
        image[3][4] = Color.RED;
        image[3][2] = Color.RED;
        image[3][1] = Color.RED;
        image[3][0] = Color.RED;

        for(int i = 0; i < image.length; i++){
            for(int j = 0; j < image[0].length; j++) {
                System.out.print(image[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println(photoPaint.paintFill(image, 3, 2, Color.GREEN));

        for(int i = 0; i < image.length; i++){
            for(int j = 0; j < image[0].length; j++) {
                System.out.print(image[i][j] + " ");
            }
            System.out.println();
        }
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
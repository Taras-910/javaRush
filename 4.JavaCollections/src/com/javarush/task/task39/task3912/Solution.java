package com.javarush.task.task39.task3912;

/* 
Максимальная площадь
*/

public class Solution {
    public static void main(String[] args) {
        int[][] matrix = {
                {99, 98, 97, 96, 95, 94, 93, 92, 91},
                {89, 88, 87, 86, 85, 84, 83, 82, 81},
                {79, 78, 77, 76, 75, 74, 73, 72, 71},
                {69, 68, 67, 66, 65, 64, 63, 62, 61},
                {59, 58, 57, 56, 55, 54, 53,  0, 51},
                {49, 48, 47, 46, 45, 44, 43,  0, 41},
                {39, 38, 37, 36, 35, 34, 33, 32, 31},
                {29, 28, 27, 26,  0, 24,  0, 22, 21},
                {19, 18, 17, 16,  0, 14, 13, 12, 11},
        };
        System.out.println("S max = " + maxSquare(matrix));
    }

    public static int maxSquare(int[][] matrix) {
        int maxLength = 0;
        int count;
        for (int r = matrix.length - 1; r >= 0; r--) {
            for (int c = matrix[0].length - 1; c >= 0; c--) {
                count = maxLength;
                if (matrix[r][c] != 0 && count < matrix.length) {
                    count++;
                    if (c + 1 - count >= 0 && r + 1 - count >= 0 && testY(matrix, c, r, count)) {
                        maxLength = count;
                        c++;
                    }
                }
            }
        }
        return maxLength * maxLength;
    }

    public static boolean testY(int[][] matrix, int startX, int startY, int count) {
//        System.out.print("начальная точка matrix["+startY+"]["+(startX)+"] = ("+ matrix[startY][startX]+")");
//        System.out.println(" count = "+count);
        for (int j = startY; j > startY - count; j--) {
            for (int i = startX; i > startX - count; i--) {
                if (matrix[j][i] == 0) {
                    return false;
                }
            }
        }
//        for(int j = startY; j > startY - count ; j--) {
//            for (int i = startX; i > startX - count; i--) {
//                System.out.print("matrix["+j+"]["+i+"]=" + matrix[j][i] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println(" true ");
        return true;
    }
}

/*
Максимальная площадь
Реализуй метод int maxSquare(int[][] matrix),
возвращающий площадь самого большого квадрата состоящего из единиц в двумерном массиве matrix.
Массив matrix заполнен только нулями и единицами.

Требования:
1. Метод maxSquare должен быть реализован в соответствии с условием задачи.
2. Метод maxSquare должен эффективно работать с большими массивами.
3. Метод maxSquare должен быть публичным.
4. Метод maxSquare должен возвращать число типа int.
5. Метод maxSquare должен быть статическим.
 */








/*
    public static int maxSquare(int[][] matrix) {
        int maxLength = 0;
        int startX = matrix[0].length - 1;
        int startY;
        int count = 0;
        for(int r = matrix.length - 1; r >= 0; --r){
            for(int c = matrix[0].length - 1; c >= 0; --c){
                if(matrix[r][c] != 0){
                    if(count == 0) startX = c; // переход от "0" -> "1": начало единиц
                    count++;
                }
                if(matrix[r][c] == 0 || c == 0 || count > maxLength){
                    if(count != 0 ) {  // переход от "1" -> "0": начало нулей
                        startY = r;

                        // проверка по высоте
                        if (startY + 1 - count >= 0 && testY(matrix, startX, startY, count)) {
                            maxLength = count;
                        }

                    }
                    if(matrix[r][c] == 0 || c == 0) count = 0;
                }
            }


        }
        return maxLength*maxLength;
    }






   public static boolean testY(int[][] matrix, int startX, int startY, int count){
        System.out.println("startY = "+startY+ " startX = "+startX+" count = "+count);

        System.out.println("начальная точка matrix["+startY+"]["+(startX)+"] = ("+ matrix[startY][startX]+")");
//        System.out.println("конечная точка"+ matrix[startY - count +2][startX - count +1]);
        for(int j = startY; j > startY - count ; j--) {
            for (int i = startX; i > startX - count; i--) {
//                System.out.println("startX = "+startX+" startY = "+startY+ " count = "+count);
                //               if(matrix[i][j] == 0){
//                    System.out.println("false: startX = "+startX+" startY = "+startY+ " count = "+count);
//                    return false;
//                }
                System.out.print(matrix[j][i] + " ");
            }
            System.out.println();
        }
//        System.out.println("true");
        return true;
    }
    public static int maxSquare(int[][] matrix) {
        int maxLength = 0;
        int startX = 4;
        int startY = 4;
        int count = 3;

//        System.out.println(matrix[matrix[0].length - 1][matrix.length - 1]);

        for(int i = matrix.length - 1; i >= 0; i--){
            for(int j = matrix[0].length - 1; j >= 0; j--){

                System.out.print(matrix[i][j] + " ");
            }


            System.out.println();
        }
        System.out.println("testY\n");

        testY(matrix, startX, startY, count);

        return maxLength*maxLength;
    }
}

 */
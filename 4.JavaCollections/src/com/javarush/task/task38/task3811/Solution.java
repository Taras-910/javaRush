package com.javarush.task.task38.task3811;

/*
Тикеты
*/

@Ticket (
        priority = Ticket.Priority.HIGH,
//        priority = Priority.HIGH,
        createdBy = "Noodles",
        tags = {"bug","fix asap"}
)
public class Solution {
    public static void main(String[] args) {
        System.out.println(Solution.class.getAnnotations()[0].toString());
    }
}

/*
Тикеты
Реализуй в отдельном файле аннотацию Ticket.

Требования к ней следующие:
1) Должна быть публичная и доступна во время выполнения программы.
2) Применяться может только к новым типам данных (Class, interface (including annotation type), or enum declaration).
3) Должна содержать enum Priority c элементами LOW, MEDIUM, HIGH.
4) Приоритет будет задавать свойство priority - по умолчанию Priority.MEDIUM.
5) Свойство tags будет массивом строк и будет хранить теги связанные с тикетом.
6) Свойство createdBy будет строкой - по умолчанию Amigo.

Требования:
1. Аннотация Ticket должна быть публичная и доступна во время выполнения программы.
2. Аннотация Ticket должна применяться только к новым типам данных.
3. Аннотация Ticket должна содержать enum Priority c элементами LOW, MEDIUM, HIGH.
4. Аннотация Ticket должна содержать свойство priority - по умолчанию Priority.MEDIUM.
5. Аннотация Ticket должна содержать свойство tags - массив строк, пустой по умолчанию.
6. Аннотация Ticket должна содержать свойство createdBy - строку, равную "Amigo" по умолчанию.
 */

/*
@Changelog({
        @Revision(
                revision = 4089,
                date = @Date(year = 2011, month = 5, day = 30, hour = 18, minute = 35, second = 18),
                comment = "Новый файл добавлен"),
        @Revision(
                revision = 6018,
                date = @Date(year = 2013, month = 1, day = 1, hour = 0, minute = 0, second = 1),
                authors = {@Author(value = "Серега", position = Position.MIDDLE)},
                comment = "Фикс багов"),
        @Revision(
                revision = 10135,
                date = @Date(year = 2014, month = 12, day = 31, hour = 23, minute = 59, second = 59),
                authors = {@Author(value = "Диана", position = Position.OTHER),
                        @Author(value = "Игорь"),
                        @Author(value = "Витек", position = Position.SENIOR)})
})
public class Solution {
    public static void main(String[] args) {
        System.out.println(Solution.class.getAnnotation(Changelog.class).toString());
    }
}
 */
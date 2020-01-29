package com.javarush.task.task39.task3913;

import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Solution {
    public static void main(String[] args) {
        String dateAfter = "01.01.2020";
        String dateBefore = " ";

        Date beforeDate = myParse(dateBefore);
        Date afterDate = myParse(dateAfter);

        LogParser logParser = new LogParser(Paths.
                get("/Users/taras/Downloads/JavaRushTasks/4.JavaCollections/src/com/javarush/task/task39/task3913/logs"));

//        System.out.println(logParser.getNumberOfUniqueIPs(null, null));
//        System.out.println(logParser.getUniqueIPs(null, null));
//        System.out.println(logParser.getIPsForUser("Amigo", null, null));
//        System.out.println(logParser.getIPsForEvent(DONE_TASK, null, null));
//        System.out.println(logParser.getIPsForStatus(OK,null, null));
//
//        System.out.println(logParser.getAllUsers());                                                     // должен возвращать множество содержащее всех пользователей.
//        System.out.println(logParser.getNumberOfUsers(afterDate, null));            //  должен возвращать количество уникальных пользователей за выбранный период.
//        System.out.println(logParser.getNumberOfUserEvents("Amigo", null, null));        //  должен возвращать количество событий от переданного пользователя за выбранный период.
//        System.out.println(logParser.getUsersForIP("192.168.100.2", afterDate, beforeDate));        //  должен возвращать множество содержащее пользователей, которые работали с переданного IP адреса за выбранный период.
//        System.out.println(logParser.getLoggedUsers(afterDate, null));        //  должен возвращать множество содержащее пользователей, которые были залогинены за выбранный период.
//        System.out.println(logParser.getDownloadedPluginUsers(afterDate, beforeDate));        //  должен возвращать множество содержащее пользователей, которые скачали плагин за выбранный период.
//        System.out.println(logParser.getWroteMessageUsers(afterDate, beforeDate));        //  должен возвращать множество содержащее пользователей, которые отправили сообщение за выбранный период.
//        System.out.println(logParser.getSolvedTaskUsers(null, null));        //  должен возвращать множество содержащее пользователей, которые решали любую задачу за выбранный период.
//        System.out.println(logParser.getSolvedTaskUsers(null, null, 15));        //  должен возвращать множество содержащее пользователей, которые решали задачу с номером task за выбранный период.
//        System.out.println(logParser.getDoneTaskUsers(null, null));       //
//        System.out.println(logParser.getDoneTaskUsers(null, null, 15));        //  должен возвращать множество содержащее пользователей, которые решали задачу с номером task за выбранный период.

//        System.out.println(logParser.getDatesForUserAndEvent("Amigo", SOLVE_TASK, null, null));        //   должен возвращать множество дат, когда переданный пользователь произвел переданное событие за выбранный период.
//        System.out.println(logParser.getDatesWhenSomethingFailed(null, null));        //   должен возвращать множество дат, когда любое событие не выполнилось за выбранный период.
//        System.out.println(logParser.getDatesWhenErrorHappened(null, null));        //  должен возвращать множество дат, когда любое событие закончилось ошибкой за выбранный период.
//        System.out.println(logParser.getDateWhenUserLoggedFirstTime("Vasya Pupkin", null, null));        //  должен возвращать дату, когда переданный пользователь впервые залогинился за выбранный период. Если такой даты в логах нет - null.
//        System.out.println(logParser.getDateWhenUserSolvedTask("Amigo", 15, null, null));        //  должен возвращать дату, когда переданный пользователь впервые попытался решить задачу с номером task за выбранный период. Если такой даты в логах нет - null.
//        System.out.println(logParser.getDateWhenUserDoneTask("Amigo", 15, null, null));        //  должен возвращать дату, когда переданный пользователь впервые решил задачу с номером task за выбранный период. Если такой даты в логах нет - null.
//        System.out.println(logParser.getDatesWhenUserWroteMessage("Eduard Petrovich Morozko", null, null));        //  должен возвращать множество дат, когда переданный пользователь написал сообщение за выбранный период.
//        System.out.println(logParser.getDatesWhenUserDownloadedPlugin("Eduard Petrovich Morozko", null, null));        //  должен возвращать множество дат, когда переданный пользователь скачал плагин за выбранный период.

//        System.out.println(logParser.getNumberOfAllEvents(null, null));   //должен возвращать количество уникальных событий за выбранный период.
//        System.out.println(logParser.getAllEvents(null, null));   //должен возвращать множество уникальных событий за выбранный период.
//        System.out.println(logParser.getEventsForIP("192.168.100.2", null, null));   //должен возвращать множество уникальных событий, которые происходили с переданного IP адреса за выбранный период.
//        System.out.println(logParser.getEventsForUser("192.168.100.2", null, null));   //должен возвращать множество уникальных событий, которые произвел переданный пользователь за выбранный период.
//        System.out.println(logParser.getFailedEvents(null, null));   //должен возвращать множество уникальных событий, у которых статус выполнения FAILED за выбранный период.
//        System.out.println(logParser.getErrorEvents(null, null));   //должен возвращать множество уникальных событий, у которых статус выполнения ERROR за выбранный период.
//        System.out.println(logParser.getNumberOfAttemptToSolveTask(18, null, null));   //должен возвращать количество попыток решить задачу с номером task за выбранный период.
//        System.out.println(logParser.getNumberOfSuccessfulAttemptToSolveTask(15, null, null));   //должен возвращать количество успешных решений задачи с номером task за выбранный период.
//        System.out.println(logParser.getAllSolvedTasksAndTheirNumber(null, null));   //должен возвращать мапу (номер_задачи : количество_попыток_решить_ее) за выбранный период.
//        System.out.println(logParser.getAllDoneTasksAndTheirNumber(null, null));   //должен возвращать мапу (номер_задачи : сколько_раз_ее_решили) за выбранный период

//        System.out.println(logParser.execute("get ip")); //
//        System.out.println(logParser.execute("get user"));
//        System.out.println(logParser.execute("get date"));
//        System.out.println(logParser.execute("get event"));
//        System.out.println(logParser.execute("get status"));

        //   get ip for user = "Eduard Petrovich Morozko"".

//        System.out.println(logParser.execute("get ip for user = \"Vasya Pupkin\"")); // должен возвращать множество уникальных IP адресов, с которых работал пользователь с именем [any_user].
//        System.out.println(logParser.execute("get ip for date = \"12.12.2013 21:56:30\"")); //должен возвращать множество уникальных IP адресов, события с которых произведены в указанное время [any_date].
//        System.out.println(logParser.execute("get ip for event = \"WRITE_MESSAGE\""));  // должен возвращать множество уникальных IP адресов, у которых событие равно [any_event].
//        System.out.println(logParser.execute("get ip for status = \"OK\""));            // должен возвращать множество уникальных IP адресов, события с которых закончились со статусом [any_status].
//
//        System.out.println(logParser.execute("get user for ip = \"146.34.15.5\""));     // должен возвращать множество уникальных пользователей, которые работали с IP адреса [any_ip].
//        System.out.println(logParser.execute("get user for date = \"14.10.2021 11:38:21\"")); // должен возвращать множество уникальных пользователей, которые произвели любое действие в указанное время [any_date].
//        System.out.println(logParser.execute("get user for event = \"DONE_TASK\""));    // должен возвращать множество уникальных пользователей, у которых событие равно [any_event].
//        System.out.println(logParser.execute("get user for status = \"ERROR\""));       //должен возвращать множество уникальных пользователей, у которых статус равен [any_status].
//
//        System.out.println(logParser.execute("get date for ip = \"192.168.100.2\""));   // должен возвращать множество уникальных дат, за которые с IP адреса [any_ip] произведено любое действие.
//        System.out.println(logParser.execute("get date for user = \"Vasya Pupkin\""));  // должен возвращать множество уникальных дат, за которые пользователь [any_user] произвел любое действие.
//        System.out.println(logParser.execute("get date for event = \"LOGIN\""));        // должен возвращать множество уникальных дат, за которые произошло событие равно [any_event].
//        System.out.println(logParser.execute("get date for status = \"FAILED\""));      // должен возвращать множество уникальных дат, за которые произошло любое событие со статусом [any_status].
//
//        System.out.println(logParser.execute("get event for ip = \"127.0.0.1\""));      // должен возвращать множество уникальных событий, которые произошли с IP адреса [any_ip].
//        System.out.println(logParser.execute("get event for user = \"Amigo\""));        // должен возвращать множество уникальных событий, которые произвел пользователь [any_user].
//        System.out.println(logParser.execute("get event for date = \"11.12.2013 10:11:12\""));        // должен возвращать множество уникальных событий, которые произошли во время [any_date].
//        System.out.println(logParser.execute("get event for status = \"OK\""));         // должен возвращать множество уникальных событий, которые завершены со статусом [any_status].
//
//        System.out.println(logParser.execute("get status for ip = \"12.12.12.12\""));   // должен возвращать множество уникальных статусов, которые произошли с IP адреса [any_ip].
//        System.out.println(logParser.execute("get status for user = \"Eduard Petrovich Morozko\"")); // должен возвращать множество уникальных статусов, которые произвел пользователь [any_user].
//        System.out.println(logParser.execute("get status for date = \"29.2.2028 5:4:7\"")); // должен возвращать множество уникальных статусов, которые произошли во время [any_date].
//        System.out.println(logParser.execute("get status for event = \"SOLVE_TASK\""));  // должен возвращать множество уникальных статусов, у которых событие равно [any_event].

        //   get ip for user   = "Eduard Petrovich Morozko"           and date between "11.12.2013 0:00:00" and "03.01.2014 23:59:59".
       // имеем:                                      146.34.15.5	Vasya Pupkin	03.01.2014 03:45:23	LOGIN	OK
//        System.out.println(logParser.execute("get ip for user = \"Vasya Pupkin\" and date between \"11.12.2013 0:00:00\" and \"03.04.2014 23:59:59\"")); // должен возвращать множество уникальных IP адресов, с которых работал пользователь с именем [any_user].
//        System.out.println(logParser.execute("get ip for date = \"12.12.2013 21:56:30\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\"")); //должен возвращать множество уникальных IP адресов, события с которых произведены в указанное время [any_date].
//        System.out.println(logParser.execute("get ip for event = \"WRITE_MESSAGE\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));  // должен возвращать множество уникальных IP адресов, у которых событие равно [any_event].
//        System.out.println(logParser.execute("get ip for status = \"OK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));            // должен возвращать множество уникальных IP адресов, события с которых закончились со статусом [any_status].
//
//        System.out.println(logParser.execute("get user for ip = \"146.34.15.5\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));     // должен возвращать множество уникальных пользователей, которые работали с IP адреса [any_ip].
//        System.out.println(logParser.execute("get user for date = \"14.10.2021 11:38:21\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\"")); // должен возвращать множество уникальных пользователей, которые произвели любое действие в указанное время [any_date].
//        System.out.println(logParser.execute("get user for event = \"DONE_TASK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));    // должен возвращать множество уникальных пользователей, у которых событие равно [any_event].
//        System.out.println(logParser.execute("get user for status = \"ERROR\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));       //должен возвращать множество уникальных пользователей, у которых статус равен [any_status].
//
//        System.out.println(logParser.execute("get date for ip = \"192.168.100.2\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));   // должен возвращать множество уникальных дат, за которые с IP адреса [any_ip] произведено любое действие.
//        System.out.println(logParser.execute("get date for user = \"Vasya Pupkin\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));  // должен возвращать множество уникальных дат, за которые пользователь [any_user] произвел любое действие.
//        System.out.println(logParser.execute("get date for event = \"LOGIN\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));        // должен возвращать множество уникальных дат, за которые произошло событие равно [any_event].
//        System.out.println(logParser.execute("get date for status = \"FAILED\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));      // должен возвращать множество уникальных дат, за которые произошло любое событие со статусом [any_status].
//
//        System.out.println(logParser.execute("get event for ip = \"127.0.0.1\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));      // должен возвращать множество уникальных событий, которые произошли с IP адреса [any_ip].
//        System.out.println(logParser.execute("get event for user = \"Amigo\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));        // должен возвращать множество уникальных событий, которые произвел пользователь [any_user].
//        System.out.println(logParser.execute("get event for date = \"11.12.2013 10:11:12\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));        // должен возвращать множество уникальных событий, которые произошли во время [any_date].
//        System.out.println(logParser.execute("get event for status = \"OK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));         // должен возвращать множество уникальных событий, которые завершены со статусом [any_status].
//
//        System.out.println(logParser.execute("get status for ip = \"12.12.12.12\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));   // должен возвращать множество уникальных статусов, которые произошли с IP адреса [any_ip].
//        System.out.println(logParser.execute("get status for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\"")); // должен возвращать множество уникальных статусов, которые произвел пользователь [any_user].
//        System.out.println(logParser.execute("get status for date = \"29.2.2028 5:4:7\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\"")); // должен возвращать множество уникальных статусов, которые произошли во время [any_date].
//        System.out.println(logParser.execute("get status for event = \"SOLVE_TASK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));  // должен возвращать множество уникальных статусов, у которых событие равно [any_event].




        System.out.println(logParser.execute("get ip for user = \"Amigo\" and event = \"LOGIN\" and date between \"11.12.2011 0:00:00\" and \"03.04.2014 23:59:59\"")); // должен возвращать множество уникальных IP адресов, с которых работал пользователь с именем [any_user].
        System.out.println(logParser.execute("get ip for user = \"Vasya Pupkin\" and status = \"OK\" and date between \"11.12.2011 0:00:00\" and \"03.04.2014 23:59:59\"")); // должен возвращать множество уникальных IP адресов, с которых работал пользователь с именем [any_user].

        System.out.println(logParser.execute("get user for ip = \"146.34.15.5\" and event = \"DOWNLOAD_PLUGIN\" and date between \"11.08.2013 0:00:00\" and \"03.01.2014 23:59:59\""));     // должен возвращать множество уникальных пользователей, которые работали с IP адреса [any_ip].
        System.out.println(logParser.execute("get user for event = \"WRITE_MESSAGE\" and status = \"OK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\"")); // должен возвращать множество уникальных пользователей, которые произвели любое действие в указанное время [any_date].
        System.out.println(logParser.execute("get date for ip = \"192.168.100.2\" and for event = \"SOLVE_TASK\""));   // должен возвращать множество уникальных дат, за которые с IP адреса [any_ip] произведено любое действие.

        System.out.println(logParser.execute("get ip for user = \"Eduard Petrovich Morozko\"")); // должен возвращать множество уникальных IP адресов, с которых работал пользователь с именем [any_user].
        System.out.println(logParser.execute("get ip for user = \"Eduard Petrovich Morozko\" and date between \"11.12.2013 0:00:00\" and \"03.04.2014 23:59:59\"")); // должен возвращать множество уникальных IP адресов, с которых работал пользователь с именем [any_user].
        System.out.println(logParser.execute("get ip for user = \"Eduard Petrovich Morozko\" and event = \"WRITE_MESSAGE\" and date between \"11.12.2013 0:00:00\" and \"03.04.2014 23:59:59\"")); // должен возвращать множество уникальных IP адресов, с которых работал пользователь с именем [any_user].



        System.out.println(logParser.execute("get ip")); //
        System.out.println(logParser.execute("get ip for user = \"Vasya Pupkin\"")); // должен возвращать множество уникальных IP адресов, с которых работал пользователь с именем [any_user].
        System.out.println(logParser.execute("get ip for user = \"Vasya Pupkin\" and status = \"OK\" and date between \"11.12.2011 0:00:00\" and \"03.04.2014 23:59:59\"")); // должен возвращать множество уникальных IP адресов, с которых работал пользователь с именем [any_user].
        System.out.println(logParser.execute("get ip for user = \"Vasya Pupkin\" and date between \"11.12.2011 0:00:00\" and \"03.04.2014 23:59:59\"")); // должен возвращать множество уникальных IP адресов, с которых работал пользователь с именем [any_user].
        System.out.println(logParser.execute("get user for event = \"WRITE_MESSAGE\" and status = \"OK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\"")); // должен возвращать множество уникальных пользователей, которые произвели любое действие в указанное время [any_date].




//1146.34.15.5	Eduard Petrovich Morozko	12.12.2013 21:56:30	WRITE_MESSAGE	OK
        System.out.println(logParser.execute("get ip for event = \"WRITE_MESSAGE\" and date between \"12.12.2013 21:56:30\" and \"03.01.2014 23:59:59\""));  // должен возвращать множество уникальных IP адресов, у которых событие равно [any_event].
//        System.out.println(logParser.execute("get ip for status = \"OK\" and date between \"11.12.2013 0:00:00\" and \"03.01.2014 23:59:59\""));            // должен возвращать множество уникальных IP адресов, события с которых закончились со статусом [any_status].


    }
    public static Date myParse(String string){
        Date date = null;
  //      if(string == null)  string = String.valueOf(Integer.MIN_VALUE);
        try {
            date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH).parse(string);
        } catch (ParseException e) {
            try {
                date = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(string);
            } catch (ParseException ex) {
                try {
                    date = new SimpleDateFormat("d.M.y", Locale.ENGLISH).parse(string);
                } catch (ParseException exc) {
                    try {
                        date = new SimpleDateFormat("d.m.y", Locale.ENGLISH).parse(string);
                    } catch (ParseException e1) {
                        try {
                            date = new SimpleDateFormat("dd.mm.yy", Locale.ENGLISH).parse(string);
                        } catch (ParseException e2) {
                            try {
                                date = new SimpleDateFormat("mm.yy", Locale.ENGLISH).parse(string);
                            } catch (Exception e3) {
                                try {
                                    date = new SimpleDateFormat("y", Locale.ENGLISH).parse(string);
                                } catch (ParseException e4) {
                                    try {
                                        date = new SimpleDateFormat(" ", Locale.ENGLISH).parse(string);
                                    } catch (ParseException e5) {
                                        return null;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return date;
    }
}
/*
event - одно из событий:
LOGIN - пользователь залогинился,
DOWNLOAD_PLUGIN - пользователь скачал плагин,
WRITE_MESSAGE - пользователь отправил сообщение,
SOLVE_TASK - пользователь попытался решить задачу,
DONE_TASK - пользователь решил задачу.
Для событий SOLVE_TASK и DONE_TASK существует дополнительный параметр,
который указывается через пробел, это номер задачи.
status - статус:
OK - событие выполнилось успешно,
FAILED - событие не выполнилось,
ERROR - произошла ошибка.

 */

//"get ip for user = \"Vasya Pupkin\" and status = \"DONE_TASK\" and date between \"11.12.2011 0:00:00\" and \"03.04.2014 23:59:59\"
//     192.168.100.2	Vasya Pupkin	30.08.2012 16:08:40	DONE_TASK 15	OK

/*
127.0.0.1	Amigo	30.08.2012 16:08:13	LOGIN	OK
192.168.100.2	Vasya Pupkin	30.08.2012 16:08:40	DONE_TASK 15	OK
146.34.15.5	Eduard Petrovich Morozko	13.09.2013 5:04:50	DOWNLOAD_PLUGIN	OK
127.0.0.1	Eduard Petrovich Morozko	11.12.2013 10:11:12	WRITE_MESSAGE	FAILED
146.34.15.5	Eduard Petrovich Morozko	12.12.2013 21:56:30	WRITE_MESSAGE	OK
146.34.15.5	Eduard Petrovich Morozko	03.01.2014 03:45:23	LOGIN	OK
192.168.100.2	Vasya Pupkin	30.01.2014 12:56:22	SOLVE_TASK 18	ERROR
127.0.0.1	Vasya Pupkin	14.11.2015 07:08:01	WRITE_MESSAGE	OK
192.168.100.2	Vasya Pupkin	19.03.2016 00:00:00	SOLVE_TASK 1	OK
146.34.15.5	Eduard Petrovich Morozko	05.01.2021 20:22:55	DONE_TASK 48	FAILED
127.0.0.1	Vasya Pupkin	14.10.2021 11:38:21	LOGIN	OK
12.12.12.12	Amigo	21.10.2021 19:45:25	SOLVE_TASK 18	OK
120.120.120.122	Amigo	29.2.2028 5:4:7	SOLVE_TASK 18	OK

146.34.81.5	Michail Ivanovich Koschedub	13.04.2001 5:04:50	DOWNLOAD_PLUGIN	FAILED
146.34.85.5	Michail Ivanovich Koschedub	13.04.2003 5:04:50	WRITE_MESSAGE	ERROR
146.34.84.5	Michail Ivanovich Koschedub	13.04.2008 5:04:50	LOGIN	FAILED
146.34.85.5	Michail Ivanovich Koschedub	13.04.2015 5:04:50	SOLVE_TASK 18	FAILED
146.34.85.5	Michail Ivanovich Koschedub	13.04.2020 5:04:50	DOWNLOAD_PLUGIN	OK
192.168.100.2	Tanja Kupik	30.01.2014 12:56:22	SOLVE_TASK 18	ERROR
192.168.100.2.4	Vasya Kupik	30.01.2015 12:56:22	SOLVE_TASK 18	ERROR
192.168.100.23	Vasya Pupkin	30.01.2014 12:56:22	SOLVE_TASK 18	ERROR
192.168.100.2	Vasya Kravets	30.01.2024 12:56:22	SOLVE_TASK 18	ERROR
146.34.85.5	Vasya Pupkin	30.01.2014 12:56:22	SOLVE_TASK 18	ERROR
127.0.0.1	Vasya Pupkin	14.11.2015 07:18:01	WRITE_MESSAGE	OK
192.168.100.2	Vasya Pupkin	19.03.2016 00:00:00	SOLVE_TASK 1	OK
146.34.15.5	Eduard Petrovich Morozko	05.01.2001 20:22:55	DONE_TASK 48	FAILED
127.0.0.1	Vasya Pupkin	14.10.2011 11:38:21	LOGIN	FAILED
12.12.12.12	Amigo	21.10.2001 19:45:25	SOLVE_TASK 18	OK
120.120.120.122	Amigo	29.2.2128 5:4:7	SOLVE_TASK 18	ERROR
*/
/*
        if(!entry.contains("=")){
            tempSet.addAll(setLogs);       // если у запроса нет параметров или промежутка времени
        } else {
            String[] stringSet = entry.trim().split("and");                  // делим на части по "and"
            ArrayDeque<String> listOfRequestFields = new ArrayDeque<>();   // записываем пары в стек
            for(int i = 0; i < stringSet.length; i++) {
                listOfRequestFields.add(stringSet[i]);
            }
            tempSet.addAll(getTempSet(listOfRequestFields, setLogs));       //   !!!!!!!!!
        }
        for(Log l: tempSet) set.add(l.ip);
        return set;

 */
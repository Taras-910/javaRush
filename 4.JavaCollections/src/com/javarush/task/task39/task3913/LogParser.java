package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.javarush.task.task39.task3913.Event.LOGIN;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    protected HashSet<Log> setLogs = new HashSet<>();
    protected List<String> listsFromFile;
    public Path logDir;                                                    // директория с логами (логов может быть несколько,все они должны иметь расширение log)

    public LogParser(Path logDir) {
        this.logDir = logDir;
        listsFromFile = new ArrayList<>();
        ArrayDeque list = new ArrayDeque<Path>();
        list.add(logDir);
        readRecords((ArrayDeque<Path>) list);
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {             //должен возвращать количество уникальных IP адресов за выбранный период
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {               //должен возвращать множество, содержащее все не повторяющиеся IP ( IP - String)
        Set set = new HashSet();
        for (Log l: setLogs) if(isSuitDate(l.date, after, before)) set.add(l.ip);
        return set;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {    //должен возвращать IP, с которых работал переданный пользователь.
        Set set = new HashSet();
        for (Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user)) set.add(l.ip);
        return set;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {      //должен возвращать IP, с которых было произведено переданное событие
        Set set = new HashSet();
        for (Log l: setLogs) if(l.event.toString().startsWith(event.toString()) && isSuitDate(l.date, after, before)) set.add(l.ip);
        return set;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {     //должен возвращать IP, события с которых закончилось переданным статусом
        Set set = new HashSet();
        for (Log l: setLogs) if (l.status.equals(status) && isSuitDate(l.date, after, before)) set.add(l.ip);
        return set;
    }

    public boolean isSuitDate(Date date, Date after, Date before) {
        boolean result = false;
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        if ((date.getTime()>=after.getTime())&&(date.getTime()<=before.getTime())) result = true;
        return result;
    }

    public boolean isSuitDateTaskSeven(Date date, Date after, Date before) {
        boolean result = false;
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        if (date.getTime()>after.getTime() && date.getTime()<before.getTime()) result = true;
//System.out.println(result);
        return result;
    }

    public Date getDate(String string) {
        Date date = null;
        if(string == null) return null; //string = String.valueOf(Integer.MIN_VALUE);
            try {
                date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH).parse(string);
            } catch (ParseException e) {
                try {
                    date = new SimpleDateFormat("d.M.y", Locale.ENGLISH).parse(string);
                } catch (ParseException e1){
                        return null;
                }
            }
            return date;
        }

    public Event getEvent(String word) {
        String[] words = word.split("\\h+");
        switch (words[0].trim()) {
            case "DONE_TASK": return Event.DONE_TASK;
            case "LOGIN": return LOGIN;
            case "DOWNLOAD_PLUGIN": return Event.DOWNLOAD_PLUGIN;
            case "WRITE_MESSAGE": return Event.WRITE_MESSAGE;
            case "SOLVE_TASK": return Event.SOLVE_TASK;
            default: return null;
        }
    }

    public Status getStatus(String word) {
        switch (word) {
            case "OK": return Status.OK;
            case "FAILED": return Status.FAILED;
            case "ERROR": return Status.ERROR;
            default: return null;
        }
    }

    public int getTask (String word){
        String[] words = word.split("\\h+");
        if(words.length != 2) return -1;
        else {
            try {
                int number = Integer.parseInt(words[1].trim());
                return number;
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }

    public void readRecords(ArrayDeque<Path>listOfPahts) {
        List<String> temp = new ArrayList();
        Path log = listOfPahts.pop();
        if (Files.isRegularFile(log) && log.getFileName().toString().endsWith(".log")) {
            try {
                temp = Files.readAllLines(log, StandardCharsets.UTF_8);
                listsFromFile.addAll(temp);
                for (int i = 0; i < temp.size(); i++) {
                    String line = temp.get(i).trim();
                    if(line.length() == 0) continue;
                    String[] s = line.split("\\t+");
                    String ip = s[0].trim();
                    String name = s[1].trim();
                    Date date = getDate(s[2]);
                    Event event = getEvent(s[3].trim());
                    Status status = getStatus(s[4]);
                    int task = getTask(s[3]);
                    setLogs.add(new Log(ip, name, date, event, status, task));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (Files.isDirectory(log)) {
                File[] listOfFiles = log.toFile().listFiles();
                for (File l : listOfFiles) {
                    listOfPahts.addLast(l.toPath());
                }
                readRecords(listOfPahts);
            }
        }
        if(!listOfPahts.isEmpty()){
            readRecords(listOfPahts);
        }
    }

    @Override
    public Set<String> getAllUsers() {                                          //  должен возвращать всех пользователей.
        Set set = new HashSet();
        for(Log l: setLogs) set.add(l.name);
        return set;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {                      //  должен возвращать количество уникальных пользователей
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before)) set.add(l.name);
        return set.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {    // должен возвращать кол-во событий от переданного польз-ля за выбр-й период
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user)) set.add(l.event);
        return set.size();
    }

    @Override                      // должен возвращать множество содержащее пользователей, которые работали с переданного IP адреса за выбранный период
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.ip.equals(ip)) set.add(l.name);
        return set;
    }

    @Override                      // должен возвращать множество содержащее пользователей, которые были залогинены за выбранный период
    public Set<String> getLoggedUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(LOGIN)) set.add(l.name);
        return set;
    }

    @Override                      // должен возвращать множество содержащее пользователей, которые скачали плагин за выбранный период
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.DOWNLOAD_PLUGIN) && l.status.equals(Status.OK)) set.add(l.name);
        return set;
    }

    @Override                       // должен возвращать множество содержащее пользователей, которые отправили сообщение за выбранный период
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.WRITE_MESSAGE) && l.status.equals(Status.OK)) set.add(l.name);
        return set;
    }

    @Override                        // должен возвращать множество содержащее пользователей, которые решали любую задачу за выбранный период
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.SOLVE_TASK)) set.add(l.name);
        return set;
    }

    @Override                        // должен возвращать множество содержащее пользователей, которые решали задачу с номером task за выбранный период
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.SOLVE_TASK) && l.task == task) set.add(l.name);
        return set;
    }

    @Override                         // должен возвращать множество содержащее пользователей, которые решили любую задачу за выбранный период
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.DONE_TASK)) set.add(l.name);
        return set;
    }

    @Override                         // должен возвращать множество содержащее пользователей, которые решили задачу с номером task за выбранный период
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.DONE_TASK) && l.task == task ) set.add(l.name);
        return set;
    }

    @Override                         // должен возвращать множество дат, когда переданный пользователь произвел переданное событие за выбранный период
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(event)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать множество дат, когда любое событие не выполнилось за выбранный период
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.status.equals(Status.FAILED)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать множество дат, когда любое событие закончилось ошибкой за выбранный период
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.status.equals(Status.ERROR)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать дату, когда переданный пользователь впервые залогинился за выбранный период, или null
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        Set set = new TreeSet();
        Date minDate;
        if(getDatesForUserAndEvent(user, Event.LOGIN, after, before).isEmpty()) return null;
        else {
            minDate = Collections.min(getDatesForUserAndEvent(user, Event.LOGIN, after, before));                 // min дата выполненного события
        }
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user)) {
            long curent = l.date.getTime();
            if(curent < minDate.getTime()) return null;
        }
        return minDate;
    }

    @Override            // должен возвращать дату, когда переданный пользователь впервые попытался решить задачу с номером task за выбранный период, или null
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        Set<Date> set = new TreeSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(Event.SOLVE_TASK) && l.task == task) set.add(l.date);
        if(!set.isEmpty()) return Collections.min(set);
        return null;
    }

    @Override                         // должен возвращать дату, когда переданный пользователь впервые решил задачу с номером task за выбранный период, или null
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        Set<Date> set = new TreeSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(Event.DONE_TASK) && l.task == task) set.add(l.date);
        if(!set.isEmpty()) return Collections.min(set);
        return null;
    }

    @Override                         // должен возвращать множество дат, когда переданный пользователь написал сообщение за выбранный период
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(Event.WRITE_MESSAGE)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать множество дат, когда переданный пользователь скачал плагин за выбранный период
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(Event.DOWNLOAD_PLUGIN)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать количество уникальных событий за выбранный период
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override                         // должен возвращать множество уникальных событий за выбранный период
    public Set<Event> getAllEvents(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) ) set.add(l.event);
        return set;
    }

    @Override                         // должен возвращать множество уникальных событий, которые происходили с переданного IP адреса за выбранный период
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.ip.equals(ip)) set.add(l.event);
        return set;
    }

    @Override                         // должен возвращать множество уникальных событий, которые произвел переданный пользователь за выбранный период
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user)) set.add(l.event);
        return set;
    }

    @Override                         // должен возвращать множество уникальных событий, у которых статус выполнения FAILED за выбранный период
    public Set<Event> getFailedEvents(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.status.equals(Status.FAILED)) set.add(l.event);
        return set;
    }

    @Override                         // должен возвращать множество уникальных событий, у которых статус выполнения ERROR за выбранный период
    public Set<Event> getErrorEvents(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.status.equals(Status.ERROR)) set.add(l.event);
        return set;
    }
                                      // должен правильно возвращать количество попыток решить задачу с номером task за период с null по null.
    @Override                         // должен возвращать количество попыток решить задачу с номером task за выбранный период
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        int count = 0;
        for(Log l: setLogs) if(l.event.equals(Event.SOLVE_TASK) && l.task == task && isSuitDate(l.date, after, before)) count++;
        return count;
    }

    @Override                         // должен возвращать количество успешных решений задачи с номером task за выбранный период
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        int count = 0;
        for(Log l: setLogs) if(l.event.equals(Event.DONE_TASK) && l.task == task && isSuitDate(l.date, after, before)) count++;
        return count;
    }

    @Override                         // должен возвращать мапу (номер_задачи : количество_попыток_решить_ее) за выбранный период
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        HashMap<Integer, Integer> map = new HashMap<>();
        for(Log l: setLogs) if(l.event == Event.SOLVE_TASK && l.task > 0 && isSuitDate(l.date, after, before)) {
                if (!map.containsKey(l.task)) map.put(l.task, 1);
                else map.put(l.task, map.get(l.task) + 1);
        }
        return map;
    }

    @Override                         // должен возвращать мапу (номер_задачи : сколько_раз_ее_решили) за выбранный период
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        HashMap<Integer, Integer> map = new HashMap<>();
        for(Log l: setLogs) if(l.event == Event.DONE_TASK && l.task > 0 && isSuitDate(l.date, after, before)){
            if(!map.containsKey(l.task)) map.put(l.task, 1);
            else map.put(l.task, map.get(l.task)+1);
        }
        return map;
    }

    private Log getRequestLog(String word){
        Log log = new Log();
//        if(stringSet.length == 0) return log;       // если у запроса нет параметров или промежутка времени
        String[] stringSet = word.trim().split("and");                  // делим на части по "and"
//        System.out.println("400 word ("+word+")");
            HashMap map = new HashMap();                                           // записываем пары в map
            for(String s :stringSet){
                String key  = s.split(" = ")[0].trim();
                String value = s.split(" = ")[1].trim();
                map.put(key,value);
            }
//            System.out.println("407 "+map);
        log.ip = map.containsKey("ip") ? (String) map.get("ip") : null;
        log.name = map.containsKey("user") ? (String) map.get("user") : null;
        log.date = map.containsKey("date") ?  getDate((String) map.get("date")) : null;
        log.event = map.containsKey("event") ? getEvent((String) map.get("event")) : null;
        log.status = map.containsKey("status") ? getStatus((String) map.get("status")) : null;
//        System.out.println("407 "+ log.toString());
        return log;
    }
//"get user for event = "WRITE_MESSAGE" and status = "OK" and date between "11.12.2013 0:00:00" and "03.01.2014 23:59:59"
//    127.0.0.1	Vasya Pupkin	14.12.2015 07:08:01	WRITE_MESSAGE	OK
    @Override
    public Set<Object> execute(String entry) {
        Set<Object> set = new HashSet<>();
        Set<Log> tempSet = new HashSet();                                         // множество выбранных логов
        String[] string = entry.trim().split(" for ");
        String field1 = string[0].split(" ")[1].trim();
        if(string.length == 1 ) tempSet.addAll(setLogs);                          // запрос на одно поле
        else {
            String word = string[1].trim();                                            //  строка без "get ... for"
//            System.out.println("424 " + word);
            word = word.replace("\"", "");
            String words[] = word.split("and date between ");
            Log requestLog = getRequestLog(words[0]);                            // передается строка без "get ... for" и без "and date between ..."
            String after = null;
            String before = null;
//System.out.print("429 words ");
//for (String s : words) System.out.print("(" + s + ") ");
//System.out.println("\n");
            if (words.length > 1) {
                after = words[1].split(" and ")[0].trim();
                before = words[1].split(" and ")[1].trim();
//System.out.println("432 after (" + after + ") before (" + before + ")");
//System.out.println("432 " + requestLog);
//System.out.println("433 " + setLogs);
            }
            for (Log log : setLogs) {
//                    System.out.println("436");
                if (log.equals(requestLog) && isSuitDateTaskSeven(log.date, getDate(after), getDate(before))) {
//                        System.out.println("437 ");
                    tempSet.add(log);
                }
            }
        }
//        System.out.println("466 ");
        if(field1.equals("ip")) for(Log l: tempSet) set.add(l.ip);
        if(field1.equals("user")) for(Log l: tempSet) set.add(l.name);
        if(field1.equals("date")) for(Log l: tempSet) set.add(l.date);
        if(field1.equals("event")) for(Log l: tempSet) set.add(l.event);
        if(field1.equals("status")) for(Log l: tempSet) set.add(l.status);
        return set;
    }

//    Вызов метода execute с параметром "get ip for event = "[any_event]" and date between "[after]" and "[before]""
//    должен возвращать множество уникальных IP адресов, у которых событие равно [any_event] в период между датами [after] и [before].

//    Вызов метода execute с параметром "get ip for status = "[any_status]" and date between "[after]" and "[before]""
//    должен возвращать множество уникальных IP адресов, события с которых закончились со статусом [any_status] в период между датами [after] и [before].

  /*  @Override
    public Set<Object> execute(String entry) {
        Set<Log> tempSet = new HashSet();                                         // множество выбранных логов
        Set<Object> set = new HashSet<>();                                        // множество результат
        if(entry.length() == 0) return set;
        String[] string = entry.trim().split(" for ");
        String field1 = string[0].split(" ")[1].trim();
        String word = entry.replace(string[0],"").replace("for"," ").trim();
        if (word.contains("between")) {
            word = word.replace("\" and \"", "\" ### \"");
            word = word.replace("\" and date between \"", "\" and datebetween = \"");
        }
        if(!word.contains("=")){
            tempSet.addAll(setLogs);       // если у запроса нет параметров или промежутка времени
        } else {
            String[] stringSet = word.trim().split("and");                  // делим на части по "and"
            ArrayDeque<String> listOfRequestFields = new ArrayDeque<>();           // записываем пары в стек
            for(int i = 0; i < stringSet.length; i++) listOfRequestFields.add(stringSet[i]);
            tempSet.addAll(getTempSet(listOfRequestFields, setLogs));               //   список выбранных логов
        }
        if(field1.equals("ip")) for(Log l: tempSet) set.add(l.ip);
        if(field1.equals("user")) for(Log l: tempSet) set.add(l.name);
        if(field1.equals("date")) for(Log l: tempSet) set.add(l.date);
        if(field1.equals("event")) for(Log l: tempSet) set.add(l.event);
        if(field1.equals("status")) for(Log l: tempSet) set.add(l.status);
        return set;
    }
*/


    public HashSet<Log> getTempSet(ArrayDeque<String> stringSet, Set<Log> set){
        HashSet<Log> tempSet = new HashSet<>();
//System.out.print("495 вход ");
//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"+"set "+set);
//System.out.println("tempSet "+tempSet+"\n");
        String entry = stringSet.pop();
//System.out.println("498 "+entry);
        String field2 = entry.split("=")[0].trim();
        String value1 = entry.split("=")[1].replace("\"", "").trim();
        String after = null;
        String before = null;
//System.out.println("503 field2 ("+field2+") value1("+value1+")");
        if(value1.contains("###")){
            String[] betweenDate = value1.trim().split("###");
            after = betweenDate[0].trim();
            before = betweenDate[1].trim();
        }
//        System.out.println("508 "+ tempSet);
        if (field2.equals("ip")) for (Log s : set) if (s.ip.equals(value1)) tempSet.add(s);
        if (field2.equals("user")) for (Log s : set) if (s.name.equals(value1)) tempSet.add(s);
        if (field2.equals("date")) for (Log s : set) if (s.date.equals(getDate(value1))) {
//            System.out.println("512 "+ tempSet);
            tempSet.add(s);
        }
        if (field2.equals("event")) for (Log s : set) if (s.event.equals(getEvent(value1))) tempSet.add(s);
        if (field2.equals("status")) for (Log s : set) if (s.status.equals(getStatus(value1))) tempSet.add(s);
//        if (field2.contains("datebetween")) for (Log s : set) if (isSuitDateTaskSeven(s.date, getDate(after), getDate(before))) tempSet.add(s);

//        System.out.println("515 "+ tempSet);
        if (field2.contains("datebetween")){
//            System.out.println("517 "+ tempSet);
            for (Log s : set) {
                if (isSuitDateTaskSeven(s.date, getDate(after), getDate(before))) {
                    tempSet.add(s);
                }
            }
        }

        if(stringSet.size() != 0) {
// System.out.println("\ngetTempSet "+tempSet+"\n525 выход ===========================================================================\n\n");
            return getTempSet(stringSet, tempSet);
        }
        return tempSet;
    }

    private class Log {
        protected String ip;
        protected String name;
        protected Date date;
        protected Event event;
        protected Status status;
        protected int task;

        public Log(String ip, String nameUser, Date date, Event event, Status status, int task) {
            this.ip = ip;
            this.name = nameUser;
            this.date = date;
            this.event = event;
            this.status = status;
            this.task = task;
        }
        public Log(){
            this.task = 0;
        }

        public boolean equals(Log log) {
            boolean ipEquals = log.ip == null ? true : this.ip.equals(log.ip);
            boolean dateEquals = log.date == null ? true : this.date.equals(log.date);
            boolean nameEquals = log.name == null ? true : this.name.equals(log.name);
            boolean eventEquals = log.event == null ? true : this.event.equals(log.event);
            boolean statusEquals = log.status == null ? true : this.status.equals(log.status);
            return ipEquals && dateEquals && nameEquals && eventEquals && statusEquals;
        }

        @Override
        public String toString() {
            return "Log{" + "ip='" + ip + '\'' + ", user='" + name + '\'' + ", date=" + date + ", event=" + event +
                    ", status=" + status + ", numberTask='" + task + '\'' + '}';
        }

        public String getIp() { return ip; }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public Date getDate() { return date; }

        public void setDate(Date date) { this.date = date; }

        public Event getEvent() { return event; }

        public void setEvent(Event event) { this.event = event; }

        public int getTask() { return task; }

        public void setTask(int numberTask) { this.task = numberTask; }
    }
}
/*
Парсер логов (8)
Ты реализовал парсер логов из разных файлов.

Помимо парсера, ты реализовал свой собственный язык запросов. Он нужен для того, что бы минимизировать количество методов.
Строчка в нашем лог-файле содержала всего 5 параметров плюс один вариативный параметр.
При количестве параметров запроса два - это 25 возможный комбинаций, соответственно, что бы сделать любую выборку нужно реализовать 25 методов.
Теперь представь, что параметров в строчке лог-файла не 5, а 10.
И количество параметров запроса не 2, а 3. Уже нужно было бы написать 10 * 10 * 10 = 1000 методов.
Чем сложнее лог - тем больше времени разработчик может себе сэкономить.

Из рекомендаций и возможных улучшений можно реализовать запрос с количеством параметров 3, например такой:
get field1 for field2 = "value1" and field3 = "value2" and date between "after" and "before"

Из архитектурных улучшений в этой программе уместно использовать паттерн команда (для получения значения полей field, действуя единообразно).
Реализуй его, если еще не сделал этого.

Требования:
1. Поздравляю! Ты написал собственный парсер и свой язык запросов.


Парсер логов (7)
Теперь добавим поддержку дополнительного параметра запроса в наш QL.
Дополнительный параметр будет отвечать за диапазон дат, которые нас интересуют.

Пример запроса:
get ip for user = "Eduard Petrovich Morozko" and date between "11.12.2013 0:00:00" and "03.01.2014 23:59:59".

Ожидаемый результат:
Set<String> с записями: 127.0.0.1 и 146.34.15.5.

Общий формат запроса:
get field1 for field2 = "value1" and date between "after" and "before"
Дополнительным параметром может быть только интервал дат, который нас интересует.

Поддержка старых форматов запросов должна сохраниться.

Требования:
1. Вызов метода execute с параметром "get ip for user = "[any_user]" and date between "[after]" and "[before]"" должен возвращать множество уникальных IP адресов,
с которых работал пользователь с именем [any_user] в период между датами [after] и [before].
2. Вызов метода execute с параметром "get ip for date = "[any_date]" and date between "[after]" and "[before]"" должен возвращать множество уникальных IP адресов,
события с которых произведены в указанное время [any_date] в период между датами [after] и [before].
3. Вызов метода execute с параметром "get ip for event = "[any_event]" and date between "[after]" and "[before]"" должен возвращать множество уникальных IP адресов,
 у которых событие равно [any_event] в период между датами [after] и [before].
4. Вызов метода execute с параметром "get ip for status = "[any_status]" and date between "[after]" and "[before]"" должен возвращать множество уникальных IP адресов,
 события с которых закончились со статусом [any_status] в период между датами [after] и [before].

5. Вызов метода execute с параметром "get user for ip = "[any_ip]" and date between "[after]" and "[before]"" должен возвращать множество уникальных пользователей,
которые работали с IP адреса [any_ip] в период между датами [after] и [before].
6. Вызов метода execute с параметром "get user for date = "[any_date]" and date between "[after]" and "[before]"" должен возвращать множество уникальных пользователей,
которые произвели любое действие в указанное время [any_date] в период между датами [after] и [before].
7. Вызов метода execute с параметром "get user for event = "[any_event]" and date between "[after]" and "[before]"" должен возвращать множество уникальных пользователей,
у которых событие равно [any_event] в период между датами [after] и [before].
8. Вызов метода execute с параметром "get user for status = "[any_status]" and date between "[after]" and "[before]"" должен возвращать множество уникальных пользователей,
у которых статус равен [any_status] в период между датами [after] и [before].

9. Вызов метода execute с параметром "get date for ip = "[any_ip]" and date between "[after]" and "[before]"" должен возвращать множество уникальных дат,
за которые с IP адреса [any_ip] произведено любое действие в период между датами [after] и [before].
10. Вызов метода execute с параметром "get date for user = "[any_user]" and date between "[after]" and "[before]"" должен возвращать множество уникальных дат,
за которые пользователь [any_user] произвел любое действие в период между датами [after] и [before].
11. Вызов метода execute с параметром "get date for event = "[any_event]" and date between "[after]" and "[before]"" должен возвращать множество уникальных дат,
за которые произошло событие равно [any_event] в период между датами [after] и [before].
12. Вызов метода execute с параметром "get date for status = "[any_status]" and date between "[after]" and "[before]"" должен возвращать множество уникальных дат,
за которые произошло любое событие со статусом [any_status] в период между датами [after] и [before].

13. Вызов метода execute с параметром "get event for ip = "[any_ip]" and date between "[after]" and "[before]"" должен возвращать множество уникальных событий,
которые произошли с IP адреса [any_ip] в период между датами [after] и [before].
14. Вызов метода execute с параметром "get event for user = "[any_user]" and date between "[after]" and "[before]"" должен возвращать множество уникальных событий,
которые произвел пользователь [any_user] в период между датами [after] и [before].
15. Вызов метода execute с параметром "get event for date = "[any_date]" and date between "[after]" and "[before]"" должен возвращать множество уникальных событий,
которые произошли во время [any_date] в период между датами [after] и [before].
16. Вызов метода execute с параметром "get event for status = "[any_status]" and date between "[after]" and "[before]"" должен возвращать множество уникальных событий,
которые завершены со статусом [any_status] в период между датами [after] и [before].

17. Вызов метода execute с параметром "get status for ip = "[any_ip]" and date between "[after]" and "[before]"" должен возвращать множество уникальных статусов,
которые произошли с IP адреса [any_ip] в период между датами [after] и [before].
18. Вызов метода execute с параметром "get status for user = "[any_user]" and date between "[after]" and "[before]"" должен возвращать множество уникальных статусов,
которые произвел пользователь [any_user] в период между датами [after] и [before].
19. Вызов метода execute с параметром "get status for date = "[any_date]" and date between "[after]" and "[before]"" должен возвращать множество уникальных статусов,
которые произошли во время [any_date] в период между датами [after] и [before].
20. Вызов метода execute с параметром "get status for event = "[any_event]" and date between "[after]" and "[before]"" должен возвращать множество уникальных статусов,
у которых событие равно [any_event] в период между датами [after] и [before].

21. Поддержка формата запросов из задания 5 должна сохраниться.
22. Поддержка формата запросов из задания 6 должна сохраниться.


Парсер логов (6)
Давай добавим поддержку параметра запроса в наш QL.

Примеры запросов с параметром:
1) get ip for user = "Vasya"
2) get user for event = "DONE_TASK"
3) get event for date = "03.01.2014 03:45:23"

Общий формат запроса с параметром:
get field1 for field2 = "value1"
Где: field1 - одно из полей: ip, user, date, event или status;
field2 - одно из полей: ip, user, date, event или status;
value1 - значение поля field2.

Алгоритм обработки запроса следующий: просматриваем записи в логе, если поле field2 имеет значение value1, то добавляем поле field1 в множество,
которое затем будет возвращено методом execute.

Пример:
Вызов метода execute("get event for date = "30.01.2014 12:56:22″") должен вернуть Set<Event>, содержащий только одно событие SOLVE_TASK.
Какая именно задача решалась возвращать не нужно.

Поддержка старого формата запросов должна сохраниться.

Требования:
1. Вызов метода execute с параметром "get ip for user = "[any_user]"" должен возвращать множество уникальных IP адресов,
   с которых работал пользователь с именем [any_user].
2. Вызов метода execute с параметром "get ip for date = "[any_date]"" должен возвращать множество уникальных IP адресов,
   события с которых произведены в указанное время [any_date].
3. Вызов метода execute с параметром "get ip for event = "[any_event]"" должен возвращать множество уникальных IP адресов, у которых событие равно [any_event].
4. Вызов метода execute с параметром "get ip for status = "[any_status]"" должен возвращать множество уникальных IP адресов,
   события с которых закончились со статусом [any_status].
5. Вызов метода execute с параметром "get user for ip = "[any_ip]"" должен возвращать множество уникальных пользователей,
   которые работали с IP адреса [any_ip].
6. Вызов метода execute с параметром "get user for date = "[any_date]"" должен возвращать множество уникальных пользователей,
   которые произвели любое действие в указанное время [any_date].
7. Вызов метода execute с параметром "get user for event = "[any_event]"" должен возвращать множество уникальных пользователей, у которых событие равно [any_event].
8. Вызов метода execute с параметром "get user for status = "[any_status]"" должен возвращать множество уникальных пользователей, у которых статус равен [any_status].
9. Вызов метода execute с параметром "get date for ip = "[any_ip]"" должен возвращать множество уникальных дат,
за которые с IP адреса [any_ip] произведено любое действие.
10. Вызов метода execute с параметром "get date for user = "[any_user]"" должен возвращать множество уникальных дат,
за которые пользователь [any_user] произвел любое действие.
11. Вызов метода execute с параметром "get date for event = "[any_event]"" должен возвращать множество уникальных дат, за которые произошло событие равно [any_event].
12. Вызов метода execute с параметром "get date for status = "[any_status]"" должен возвращать множество уникальных дат,
за которые произошло любое событие со статусом [any_status].
13. Вызов метода execute с параметром "get event for ip = "[any_ip]"" должен возвращать множество уникальных событий, которые произошли с IP адреса [any_ip].
14. Вызов метода execute с параметром "get event for user = "[any_user]"" должен возвращать множество уникальных событий, которые произвел пользователь [any_user].
15. Вызов метода execute с параметром "get event for date = "[any_date]"" должен возвращать множество уникальных событий, которые произошли во время [any_date].
16. Вызов метода execute с параметром "get event for status = "[any_status]"" должен возвращать множество уникальных событий, которые завершены со статусом [any_status].
17. Вызов метода execute с параметром "get status for ip = "[any_ip]"" должен возвращать множество уникальных статусов, которые произошли с IP адреса [any_ip].
18. Вызов метода execute с параметром "get status for user = "[any_user]"" должен возвращать множество уникальных статусов, которые произвел пользователь [any_user].
19. Вызов метода execute с параметром "get status for date = "[any_date]"" должен возвращать множество уникальных статусов, которые произошли во время [any_date].
20. Вызов метода execute с параметром "get status for event = "[any_event]"" должен возвращать множество уникальных статусов, у которых событие равно [any_event].
21. Поддержка старого формата запросов должна сохраниться.


Парсер логов (5)
Как ты заметил существует огромное количество комбинаций параметров для выбора определенных записей из лог файла.
Покрыть их все соответствующими методами дело не благодарное. Поэтому мы реализуем свой язык запросов (QL).

Пример запроса:
get ip for user = "Vasya"
Такой запрос должен будет вернуть все IP адреса, с которых пользователь Vasya что-то делал и это отображено в нашем логе.
Представь, как будет удобно ввести запрос в консоль и получить необходимую информацию из лога.

5.1. Реализуй интерфейс QLQuery у класса LogParser. Метод execute() пока должен поддерживать только следующие запросы:
5.1.1. get ip
5.1.2. get user
5.1.3. get date
5.1.4. get event
5.1.5. get status

Пример: Вызов метода execute("get ip") должен вернуть Set<String>, содержащий все уникальные IP из лога
(это будет: 127.0.0.1, 12.12.12.12, 146.34.15.5, 192.168.100.2 для тестового файла). Аналогично должны работать и другие запросы.

Реальные объекты в возвращаемом множестве должны быть типа String для запросов ip и user, для запроса date - тип объектов Date,
для event и status - Event и Status соответственно.

Требования:
1. Класс LogParser должен поддерживать интерфейс QLQuery.
2. Вызов метода execute("get ip") класса LogParser должен возвращать множество (Set<String>) содержащее все уникальные IP адреса.
3. Вызов метода execute("get user") класса LogParser должен возвращать множество (Set<String>) содержащее всех уникальных пользователей.
4. Вызов метода execute("get date") класса LogParser должен возвращать множество (Set<Date>) содержащее все уникальные даты.
5. Вызов метода execute("get event") класса LogParser должен возвращать множество (Set<Event>) содержащее все уникальные события.
6. Вызов метода execute("get status") класса LogParser должен возвращать множество (Set<Status>) содержащее все уникальные статусы.


Парсер логов (4)
Реализуй интерфейс EventQuery у класса LogParser:
4.1. Метод getNumberOfAllEvents() должен возвращать количество событий за указанный период.
4.2. Метод getAllEvents() должен возвращать все события за указанный период.
4.3. Метод getEventsForIP() должен возвращать события, которые происходили с указанного IP.
4.4. Метод getEventsForUser() должен возвращать события, которые инициировал
определенный пользователь.
4.5. Метод getFailedEvents() должен возвращать события, которые не выполнились.
4.6. Метод getErrorEvents() должен возвращать события, которые завершились ошибкой.
4.7. Метод getNumberOfAttemptToSolveTask() должен возвращать количество попыток
решить определенную задачу.
4.8. Метод getNumberOfSuccessfulAttemptToSolveTask() должен возвращать количество
успешных решений определенной задачи.
4.9. Метод getAllSolvedTasksAndTheirNumber() должен возвращать мапу (номер_задачи :
количество_попыток_решить_ее).
4.10. Метод getAllDoneTasksAndTheirNumber() должен возвращать мапу (номер_задачи :
сколько_раз_ее_решили).

Требования:
1. Класс LogParser должен поддерживать интерфейс EventQuery.
2. Метод getNumberOfAllEvents(Date, Date) должен возвращать количество уникальных событий за выбранный период.
3. Метод getAllEvents(Date, Date) должен возвращать множество уникальных событий за выбранный период.
4. Метод getEventsForIP(String, Date, Date) должен возвращать множество уникальных событий, которые происходили с переданного IP адреса за выбранный период.
5. Метод getEventsForUser(String, Date, Date) должен возвращать множество уникальных событий, которые произвел переданный пользователь за выбранный период.

6. Метод getFailedEvents(Date, Date) должен возвращать множество уникальных событий, у которых статус выполнения FAILED за выбранный период.
7. Метод getErrorEvents(Date, Date) должен возвращать множество уникальных событий, у которых статус выполнения ERROR за выбранный период.
8. Метод getNumberOfAttemptToSolveTask(int, Date, Date) должен возвращать количество попыток решить задачу с номером task за выбранный период.
9. Метод getNumberOfSuccessfulAttemptToSolveTask(int, Date, Date) должен возвращать количество успешных решений задачи с номером task за выбранный период.
10. Метод getAllSolvedTasksAndTheirNumber(Date, Date) должен возвращать мапу (номер_задачи : количество_попыток_решить_ее) за выбранный период.
11. Метод getAllDoneTasksAndTheirNumber(Date, Date) должен возвращать мапу (номер_задачи : сколько_раз_ее_решили) за выбранный период.





Парсер логов (3)
Реализуй интерфейс DateQuery у класса LogParser:
3.1. Метод getDatesForUserAndEvent() должен возвращать даты, когда определенный пользователь произвел определенное событие.
3.2. Метод getDatesWhenSomethingFailed() должен возвращать даты, когда любое событие не выполнилось (статус FAILED).
3.3. Метод getDatesWhenErrorHappened() должен возвращать даты, когда любое событие закончилось ошибкой (статус ERROR).
3.4. Метод getDateWhenUserLoggedFirstTime() должен возвращать дату, когда пользователь залогинился впервые за указанный период. Если такой даты в логах нет - null.
3.5. Метод getDateWhenUserSolvedTask() должен возвращать дату, когда пользователь впервые попытался решить определенную задачу. Если такой даты в логах нет - null.
3.6. Метод getDateWhenUserDoneTask() должен возвращать дату, когда пользователь впервые решил определенную задачу. Если такой даты в логах нет - null.
3.7. Метод getDatesWhenUserWroteMessage() должен возвращать даты, когда пользователь написал сообщение.
3.8. Метод getDatesWhenUserDownloadedPlugin() должен возвращать даты, когда пользователь скачал плагин.

Требования:
1. Класс LogParser должен поддерживать интерфейс DateQuery.
2. Метод getDatesForUserAndEvent(String, Event, Date, Date) должен возвращать множество дат, когда переданный пользователь произвел
переданное событие за выбранный период.
3. Метод getDatesWhenSomethingFailed(Date, Date) должен возвращать множество дат, когда любое событие не выполнилось за выбранный период.
4. Метод getDatesWhenErrorHappened(Date, Date) должен возвращать множество дат, когда любое событие закончилось ошибкой за выбранный период.
5. Метод getDateWhenUserLoggedFirstTime(String, Date, Date) должен возвращать дату, когда переданный пользователь впервые залогинился за выбранный период.
Если такой даты в логах нет - null.
6. Метод getDateWhenUserSolvedTask(String, int, Date, Date) должен возвращать дату, когда переданный пользователь впервые попытался решить задачу
с номером task за выбранный период. Если такой даты в логах нет - null.
7. Метод getDateWhenUserDoneTask(String, int, Date, Date) должен возвращать дату, когда переданный пользователь впервые решил задачу
с номером task за выбранный период. Если такой даты в логах нет - null.
8. Метод getDatesWhenUserWroteMessage(String, Date, Date) должен возвращать множество дат, когда переданный пользователь написал сообщение за выбранный период.
9. Метод getDatesWhenUserDownloadedPlugin(String, Date, Date) должен возвращать множество дат, когда переданный пользователь скачал плагин за выбранный период.

Парсер логов (2)
Реализуй интерфейс UserQuery у класса LogParser:
2.1. Метод getAllUsers() должен возвращать всех пользователей.
2.2. Метод getNumberOfUsers() должен возвращать количество уникальных пользователей.
2.3. Метод getNumberOfUserEvents() должен возвращать количество событий от определенного пользователя.
2.4. Метод getUsersForIP() должен возвращать пользователей с определенным IP.
Несколько пользователей могут использовать один и тот же IP.
2.5. Метод getLoggedUsers() должен возвращать пользователей, которые были залогинены.
2.6. Метод getDownloadedPluginUsers() должен возвращать пользователей, которые скачали плагин.
2.7. Метод getWroteMessageUsers() должен возвращать пользователей, которые отправили сообщение.
2.8. Метод getSolvedTaskUsers(Date after, Date before) должен возвращать пользователей, которые решали любую задачу.
2.9. Метод getSolvedTaskUsers(Date after, Date before, int task) должен возвращать пользователей, которые решали задачу с номером task.
2.10. Метод getDoneTaskUsers(Date after, Date before) должен возвращать пользователей, которые решали любую задачу.
2.11. Метод getDoneTaskUsers(Date after, Date before, int task) должен возвращать пользователей, которые решали задачу с номером task.


Требования:
1. Класс LogParser должен поддерживать интерфейс UserQuery.
2. Метод getAllUsers() должен возвращать множество содержащее всех пользователей.
3. Метод getNumberOfUsers(Date, Date) должен возвращать количество уникальных пользователей за выбранный период.
4. Метод getNumberOfUserEvents(String, Date, Date) должен возвращать количество событий от переданного пользователя за выбранный период.
5. Метод getUsersForIP(String, Date, Date) должен возвращать множество содержащее пользователей, которые работали с переданного IP адреса за выбранный период.
6. Метод getLoggedUsers(Date, Date) должен возвращать множество содержащее пользователей, которые были залогинены за выбранный период.
7. Метод getDownloadedPluginUsers(Date, Date) должен возвращать множество содержащее пользователей, которые скачали плагин за выбранный период.
8. Метод getWroteMessageUsers(Date, Date) должен возвращать множество содержащее пользователей, которые отправили сообщение за выбранный период.
9. Метод getSolvedTaskUsers(Date, Date) должен возвращать множество содержащее пользователей, которые решали любую задачу за выбранный период.
10. Метод getSolvedTaskUsers(Date, Date, int task) должен возвращать множество содержащее пользователей, которые решали задачу с номером task за выбранный период.
11. Метод getDoneTaskUsers(Date, Date) должен возвращать множество содержащее пользователей, которые решили любую задачу за выбранный период.
12. Метод getDoneTaskUsers(Date, Date, int task) должен возвращать множество содержащее пользователей, которые решили задачу с номером task за выбранный период.


Парсер логов (1)
Сегодня мы напишем парсер логов.

Лог файл имеет следующий формат:
ip username date event status

Где:
ip - ip адрес с которого пользователь произвел событие.
user - имя пользователя (одно или несколько слов разделенные пробелами).
date - дата события в формате day.month.year hour:minute:second.
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

Пример строки из лог файла:
"146.34.15.5 Eduard Petrovich Morozko 05.01.2021 20:22:55 DONE_TASK 48 FAILED".
Записи внутри лог файла не обязательно упорядочены по дате, события могли произойти и быть записаны в лог в разной последовательности.

Класс, который будет отвечать за парсинг логов называется LogParser.
1.1. Добавь в класс LogParser конструктор с параметром Path logDir, где logDir - директория с логами (логов может быть несколько,
     все они должны иметь расширение log).
1.2. Реализуй интерфейс IPQuery у класса LogParser:
1.2.1. Метод getNumberOfUniqueIPs(Date after, Date before) должен возвращать количество уникальных IP адресов за выбранный период.
       Здесь и далее, если в методе есть параметры Date after и Date before,
       то нужно возвратить данные касающиеся только данного периода (включая даты after и before).
Если параметр after равен null, то нужно обработать все записи, у которых дата меньше или равна before.
Если параметр before равен null, то нужно обработать все записи, у которых дата больше или равна after.
Если и after, и before равны null, то нужно обработать абсолютно все записи (без фильтрации по дате).
1.2.2. Метод getUniqueIPs() должен возвращать множество, содержащее все не повторяющиеся IP. Тип в котором будем хранить IP будет String.
1.2.3. Метод getIPsForUser() должен возвращать IP, с которых работал переданный пользователь.
1.2.4. Метод getIPsForEvent() должен возвращать IP, с которых было произведено переданное событие.
1.2.5. Метод getIPsForStatus() должен возвращать IP, события с которых закончилось переданным статусом.

Реализацию метода main() можешь менять по своему усмотрению.

Требования:
1. В классе LogParser должен быть создан конструктор public LogParser(Path logDir).
2. Класс LogParser должен поддерживать интерфейс IPQuery.
3. Метод getNumberOfUniqueIPs(Date, Date) должен возвращать количество уникальных IP адресов за выбранный период.
4. Метод getUniqueIPs(Date, Date) класса LogParser должен возвращать множество, содержащее все не повторяющиеся IP адреса за выбранный период.
5. Метод getIPsForUser(String, Date, Date) класса LogParser должен возвращать IP адреса, с которых работал переданный пользователь за выбранный период.
6. Метод getIPsForEvent(Event, Date, Date) класса LogParser должен возвращать IP адреса, с которых было произведено переданное событие за выбранный период.
7. Метод getIPsForStatus(Status, Date, Date) класса LogParser должен возвращать IP адреса, события с которых закончилось переданным статусом за выбранный период.






В валидаторе есть проблема с проверкой требований №3, 4, 11.
Исключительно для этих случаев при проверке попадания даты в диапазон, валидатору не нравится  date >= after && date <= before, как для всех остальных требований.
Теперь ему требуется ТОЛЬКО date > after && date < before.




 */

/*
// парсит любые поля любое количество + промежуток времени

package com.javarush.task.task39.task3913;

import com.javarush.task.task39.task3913.query.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.javarush.task.task39.task3913.Event.LOGIN;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    protected HashSet<Log> setLogs = new HashSet<>();
    protected List<String> listsFromFile;
    public Path logDir;                                                    // директория с логами (логов может быть несколько,все они должны иметь расширение log)

    public LogParser(Path logDir) {
        this.logDir = logDir;
        listsFromFile = new ArrayList<>();
        ArrayDeque list = new ArrayDeque<Path>();
        list.add(logDir);
        readRecords((ArrayDeque<Path>) list);
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {             //должен возвращать количество уникальных IP адресов за выбранный период
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {               //должен возвращать множество, содержащее все не повторяющиеся IP ( IP - String)
        Set set = new HashSet();
        for (Log l: setLogs) if(isSuitDate(l.date, after, before)) set.add(l.ip);
        return set;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {    //должен возвращать IP, с которых работал переданный пользователь.
        Set set = new HashSet();
        for (Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user)) set.add(l.ip);
        return set;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {      //должен возвращать IP, с которых было произведено переданное событие
        Set set = new HashSet();
        for (Log l: setLogs) if(l.event.toString().startsWith(event.toString()) && isSuitDate(l.date, after, before)) set.add(l.ip);
        return set;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {     //должен возвращать IP, события с которых закончилось переданным статусом
        Set set = new HashSet();
        for (Log l: setLogs) if (l.status.equals(status) && isSuitDate(l.date, after, before)) set.add(l.ip);
        return set;
    }

    public boolean isSuitDate(Date date, Date after, Date before) {
        boolean result = false;
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        if ((date.getTime()>=after.getTime())&&(date.getTime()<=before.getTime())) result = true;
        return result;
    }

    public boolean isSuitDateTarkSeven(Date date, Date after, Date before) {
        boolean result = false;
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        if ((date.getTime()>after.getTime())&&(date.getTime()<before.getTime())) result = true;
        return result;
//        if ((after == null || !date.before(after)) && (before == null || !date.after(before))) return true;
    }

    public Date getDate(String string) {
        Date date = null;
        if(string == null) return null; //string = String.valueOf(Integer.MIN_VALUE);
            try {
                date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH).parse(string);
            } catch (ParseException e) {
                try {
                    date = new SimpleDateFormat("d.M.y", Locale.ENGLISH).parse(string);
                } catch (ParseException e1){
                        return null;
                }
            }
            return date;
        }

    public Event getEvent(String word) {
        String[] words = word.split("\\h+");
        switch (words[0].trim()) {
            case "DONE_TASK": return Event.DONE_TASK;
            case "LOGIN": return LOGIN;
            case "DOWNLOAD_PLUGIN": return Event.DOWNLOAD_PLUGIN;
            case "WRITE_MESSAGE": return Event.WRITE_MESSAGE;
            case "SOLVE_TASK": return Event.SOLVE_TASK;
            default: return null;
        }
    }

    public Status getStatus(String word) {
        switch (word) {
            case "OK": return Status.OK;
            case "FAILED": return Status.FAILED;
            case "ERROR": return Status.ERROR;
            default: return null;
        }
    }

    public int getTask (String word){
        String[] words = word.split("\\h+");
        if(words.length != 2) return -1;
        else {
            try {
                int number = Integer.parseInt(words[1].trim());
                return number;
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }

    public void readRecords(ArrayDeque<Path>listOfPahts) {
        List<String> temp = new ArrayList();
        Path log = listOfPahts.pop();
        if (Files.isRegularFile(log) && log.getFileName().toString().endsWith(".log")) {
            try {
                temp = Files.readAllLines(log, StandardCharsets.UTF_8);
                listsFromFile.addAll(temp);
                for (int i = 0; i < temp.size(); i++) {
                    String line = temp.get(i).trim();
                    if(line.length() == 0) continue;
                    String[] s = line.split("\\t+");
                    String ip = s[0].trim();
                    String name = s[1].trim();
                    Date date = getDate(s[2]);
                    Event event = getEvent(s[3].trim());
                    Status status = getStatus(s[4]);
                    int task = getTask(s[3]);
                    setLogs.add(new Log(ip, name, date, event, status, task));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (Files.isDirectory(log)) {
                File[] listOfFiles = log.toFile().listFiles();
                for (File l : listOfFiles) {
                    listOfPahts.addLast(l.toPath());
                }
                readRecords(listOfPahts);
            }
        }
        if(!listOfPahts.isEmpty()){
            readRecords(listOfPahts);
        }
    }

    @Override
    public Set<String> getAllUsers() {                                          //  должен возвращать всех пользователей.
        Set set = new HashSet();
        for(Log l: setLogs) set.add(l.name);
        return set;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {                      //  должен возвращать количество уникальных пользователей
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before)) set.add(l.name);
        return set.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {    // должен возвращать кол-во событий от переданного польз-ля за выбр-й период
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user)) set.add(l.event);
        return set.size();
    }

    @Override                      // должен возвращать множество содержащее пользователей, которые работали с переданного IP адреса за выбранный период
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.ip.equals(ip)) set.add(l.name);
        return set;
    }

    @Override                      // должен возвращать множество содержащее пользователей, которые были залогинены за выбранный период
    public Set<String> getLoggedUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(LOGIN)) set.add(l.name);
        return set;
    }

    @Override                      // должен возвращать множество содержащее пользователей, которые скачали плагин за выбранный период
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.DOWNLOAD_PLUGIN) && l.status.equals(Status.OK)) set.add(l.name);
        return set;
    }

    @Override                       // должен возвращать множество содержащее пользователей, которые отправили сообщение за выбранный период
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.WRITE_MESSAGE) && l.status.equals(Status.OK)) set.add(l.name);
        return set;
    }

    @Override                        // должен возвращать множество содержащее пользователей, которые решали любую задачу за выбранный период
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.SOLVE_TASK)) set.add(l.name);
        return set;
    }

    @Override                        // должен возвращать множество содержащее пользователей, которые решали задачу с номером task за выбранный период
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.SOLVE_TASK) && l.task == task) set.add(l.name);
        return set;
    }

    @Override                         // должен возвращать множество содержащее пользователей, которые решили любую задачу за выбранный период
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.DONE_TASK)) set.add(l.name);
        return set;
    }

    @Override                         // должен возвращать множество содержащее пользователей, которые решили задачу с номером task за выбранный период
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.DONE_TASK) && l.task == task ) set.add(l.name);
        return set;
    }

    @Override                         // должен возвращать множество дат, когда переданный пользователь произвел переданное событие за выбранный период
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(event)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать множество дат, когда любое событие не выполнилось за выбранный период
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.status.equals(Status.FAILED)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать множество дат, когда любое событие закончилось ошибкой за выбранный период
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.status.equals(Status.ERROR)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать дату, когда переданный пользователь впервые залогинился за выбранный период, или null
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        Set set = new TreeSet();
        Date minDate;
        if(getDatesForUserAndEvent(user, Event.LOGIN, after, before).isEmpty()) return null;
        else {
            minDate = Collections.min(getDatesForUserAndEvent(user, Event.LOGIN, after, before));                 // min дата выполненного события
        }
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user)) {
            long curent = l.date.getTime();
            if(curent < minDate.getTime()) return null;
        }
        return minDate;
    }

    @Override            // должен возвращать дату, когда переданный пользователь впервые попытался решить задачу с номером task за выбранный период, или null
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        Set<Date> set = new TreeSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(Event.SOLVE_TASK) && l.task == task) set.add(l.date);
        if(!set.isEmpty()) return Collections.min(set);
        return null;
    }

    @Override                         // должен возвращать дату, когда переданный пользователь впервые решил задачу с номером task за выбранный период, или null
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        Set<Date> set = new TreeSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(Event.DONE_TASK) && l.task == task) set.add(l.date);
        if(!set.isEmpty()) return Collections.min(set);
        return null;
    }

    @Override                         // должен возвращать множество дат, когда переданный пользователь написал сообщение за выбранный период
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(Event.WRITE_MESSAGE)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать множество дат, когда переданный пользователь скачал плагин за выбранный период
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(Event.DOWNLOAD_PLUGIN)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать количество уникальных событий за выбранный период
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override                         // должен возвращать множество уникальных событий за выбранный период
    public Set<Event> getAllEvents(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) ) set.add(l.event);
        return set;
    }

    @Override                         // должен возвращать множество уникальных событий, которые происходили с переданного IP адреса за выбранный период
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.ip.equals(ip)) set.add(l.event);
        return set;
    }

    @Override                         // должен возвращать множество уникальных событий, которые произвел переданный пользователь за выбранный период
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user)) set.add(l.event);
        return set;
    }

    @Override                         // должен возвращать множество уникальных событий, у которых статус выполнения FAILED за выбранный период
    public Set<Event> getFailedEvents(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.status.equals(Status.FAILED)) set.add(l.event);
        return set;
    }

    @Override                         // должен возвращать множество уникальных событий, у которых статус выполнения ERROR за выбранный период
    public Set<Event> getErrorEvents(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.status.equals(Status.ERROR)) set.add(l.event);
        return set;
    }
                                      // должен правильно возвращать количество попыток решить задачу с номером task за период с null по null.
    @Override                         // должен возвращать количество попыток решить задачу с номером task за выбранный период
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        int count = 0;
        for(Log l: setLogs) if(l.event.equals(Event.SOLVE_TASK) && l.task == task && isSuitDate(l.date, after, before)) count++;
        return count;
    }

    @Override                         // должен возвращать количество успешных решений задачи с номером task за выбранный период
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        int count = 0;
        for(Log l: setLogs) if(l.event.equals(Event.DONE_TASK) && l.task == task && isSuitDate(l.date, after, before)) count++;
        return count;
    }

    @Override                         // должен возвращать мапу (номер_задачи : количество_попыток_решить_ее) за выбранный период
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        HashMap<Integer, Integer> map = new HashMap<>();
        for(Log l: setLogs) if(l.event == Event.SOLVE_TASK && l.task > 0 && isSuitDate(l.date, after, before)) {
                if (!map.containsKey(l.task)) map.put(l.task, 1);
                else map.put(l.task, map.get(l.task) + 1);
        }
        return map;
    }

    @Override                         // должен возвращать мапу (номер_задачи : сколько_раз_ее_решили) за выбранный период
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        HashMap<Integer, Integer> map = new HashMap<>();
        for(Log l: setLogs) if(l.event == Event.DONE_TASK && l.task > 0 && isSuitDate(l.date, after, before)){
            if(!map.containsKey(l.task)) map.put(l.task, 1);
            else map.put(l.task, map.get(l.task)+1);
        }
        return map;
    }
//task7    get     ip for user   = "Eduard Petrovich Morozko" and date between "11.12.2013 0:00:00" and "03.01.2014 23:59:59".
//task8    get field1 for field2 = "value1" and field3      = "value2"        and date between "after"              and "before"
//         get     ip for user   = "Vasya Pupkin" and event = "WRITE_MESSAGE" and date between "11.12.2013 0:00:00" and "03.04.2014 23:59:59"
    @Override
    public Set<Object> execute(String entry) {
        Set<Log> copySetLogs = new HashSet<>(setLogs);
        Set<Object> set = new HashSet<>();
//        System.out.println("395 "+ entry);
        if(entry.length() == 0) return set;
        String[] string = entry.trim().split(" for ");


        System.out.print("400 "+ string.length);
        for (String s : string) System.out.print(" (" + s + ") ");

        String field1 = string[0].trim().split(" ")[1];
        String word = entry.replace(string[0],"");
//        String word = entry.replace(string[0],"").replace(string[1],"");

        System.out.println();
        System.out.println("409 field1 ("+field1+ ") : word (" +word+")");

        if (field1.contains("ip")) set.addAll(getSetIp(word, copySetLogs));
        if (field1.contains("user")) set.addAll(getSetUser(word, copySetLogs));
        if (field1.contains("date")) set.addAll(getSetDate(word, copySetLogs));
        if (field1.contains("event")) set.addAll(getSetEvent(word, copySetLogs));
        if (field1.contains("status")) set.addAll(getSetStatus(word, copySetLogs));
        return set;
    }

    //task8    get field1 for field2 = "value1" and field3      = "value2"        and date between "after"              and "before"
    //         get ip for user = "Vasya Pupkin" and event = "WRITE_MESSAGE" and date between "11.12.2013 0:00:00" and "03.04.2014 23:59:59"

    public Set<Integer> getSetIp(String entry, Set<Log> copySetLogs){
        entry = entry.replace("for"," ").trim();
//System.out.println("424 entry:" +entry);
        Set set = new HashSet();

        if(!entry.contains("=")){ for(Log l: copySetLogs) set.add(l.ip);
        } else {
            if (entry.contains("between")) {
                System.out.println("427 between:" + entry);
                entry = entry.replace("\" and \"", "\" ### \"");
                entry = entry.replace("\" and date between \"", "\" date \"");

System.out.println("434:" + entry);
            }
            String[] stringSet = entry.trim().split("and");                  // делим на части по "and"
System.out.print("437:");
for (String s : stringSet) System.out.print("(" + s + ") ");
System.out.println();

            if(stringSet.length == 1){                                              // обработка "хвостика" с одним полем
                for (Log l : copySetLogs) {
                    if (find(l, entry)) set.add(l.ip);
                }

            }else {                                                                 // если запрос по двум параметрам и более .........
                Set<Log> tempSet = new HashSet();
                ArrayDeque<String> listOfRequestFields = new ArrayDeque<>();
                for(int i = 0; i < stringSet.length - 1; i++) {
                    listOfRequestFields.add(stringSet[i]);
                }
                System.out.println("451" +setLogs.toString());
                tempSet.addAll(getTempSet(listOfRequestFields, setLogs));          //   !!!!!!!!!


                String stringFinal = stringSet[stringSet.length - 1];
                System.out.println(" 450 stringFinal:"+stringFinal);
                for (Log l : tempSet) {
                    if (find(l, stringFinal)) set.add(l.ip);
                }
            }
        }
        return set;
    }

    public HashSet<Log> getTempSet(ArrayDeque<String> stringSet, Set<Log> set){
        System.out.println(set.size());
        System.out.println("476:"+ set.toString());

        HashSet<Log> tempSet = new HashSet<>();
        while(stringSet.size() != 0) {

            String entry = stringSet.pop();


            String field2 = entry.split("=")[0].trim();
            String value1 = entry.split("=")[1].replace("\"", "").trim();

            System.out.print("483 map:");
            System.out.print("field2:(" + field2 + ") value1:(" + value1 + ")");

            if (field2.contains("ip")) for(Log s: set) if(s.ip.equals(value1)) tempSet.add(s);
            if (field2.contains("user")){
                for(Log s: set){
                    System.out.println("484 test");
                    if(s.name.equals(value1)) tempSet.add(s);
                }
            }
            if (field2.contains("date")) for(Log s: set) if(s.date.equals(value1)) tempSet.add(s);
            if (field2.contains("event")) {
                for(Log s: set){
                    System.out.println("491 test");
                    if(s.event.equals(value1)) tempSet.add(s);
                }
            }
            if (field2.contains("status")) for(Log s: set) if(s.status.equals(value1)) tempSet.add(s);
            getTempSet(stringSet,tempSet);

        }
        System.out.println("495: ");
        for(Log l : tempSet) System.out.println(l);
        return tempSet;
    }
    //   get event for ip = "[any_ip]"                and date between "[after]" and "[before]"
    //   get ip for user = "Eduard Petrovich Morozko" and date between "11.12.2013 0:00:00" and/to/ "03.01.2014 23:59:59".
    //   get ip for user = \"Vasya Pupkin\"





    public boolean find (Log log, String entry) {
        String[] stringSet = entry.trim().split("=");                   // делим на части по "="
System.out.print("448:");
for(String s : stringSet) System.out.print("("+s+") ");
System.out.println("");

        String field2 = stringSet[0].trim();
        String []word = entry.replace(stringSet[0] + "= \"", "").replace("\"", "").split("date");
        String value1 = word[0].trim();

System.out.print("481: field2 = "+field2+ " word[]:"+word.length);
for(String s : word) System.out.print("("+ s+") ");
System.out.println();
System.out.println("value1 = word[0]:"+value1);
 String after = null;
        String before = null;
        if(word.length > 1 && entry.contains("###")) {
System.out.println("         word[1]:"+word[1]);
            String[] betweenDate = word[1].trim().split("###");
            after = betweenDate[0].trim();
            before = betweenDate[1].trim();
        }
        switch (field2.trim()) {
            case "ip":
                return log.getIp().equals(value1) && isSuitDateTarkSeven(log.date,getDate(after),getDate(before));
            case "user":
                return log.getName().equals(value1) && isSuitDateTarkSeven(log.date,getDate(after),getDate(before));
            case "date":
                return log.getDate().equals(getDate(value1)) && isSuitDateTarkSeven(log.date,getDate(after),getDate(before));
            case "event":
                return log.event.equals(getEvent(value1)) && isSuitDateTarkSeven(log.date,getDate(after),getDate(before));
            case "status":
                return log.status.equals(getStatus(value1)) && isSuitDateTarkSeven(log.date,getDate(after),getDate(before));
            default: return false;
        }
    }

    public Set<String> getSetUser(String entry, Set<Log> copySetLogs){
        Set set = new HashSet();
        if(entry.length() == 0) for(Log l: setLogs) set.add(l.name);
        else {
            String[] string = entry.trim().split("=");
            String field2 = string[0].trim().split(" ")[1];
            String word = entry.replace(string[0]+"= \"","").replace("\"","");
            for(Log l: setLogs){
                if(find(l,entry)) set.add(l.name);
            }
        }
        return set;
    }

    public Set<Date> getSetDate(String entry, Set<Log> copySetLogs){
        Set set = new HashSet();
        if(entry.length() == 0) for(Log l: setLogs) set.add(l.date);
        else {
            String[] string = entry.trim().split("=");
            String field2 = string[0].trim().split(" ")[1];
            String word = entry.replace(string[0]+"= \"","").replace("\"","");
            for(Log l: setLogs){
                if(find(l,entry)) set.add(l.date);
            }
        }
        return set;
    }

    public Set<Event> getSetEvent(String entry, Set<Log> copySetLogs){
        Set set = new HashSet();
        if(entry.length() == 0) for(Log l: setLogs) set.add(l.event);
        else {
            String[] string = entry.trim().split("=");
            String field2 = string[0].trim().split(" ")[1];
            String word = entry.replace(string[0]+"= \"","").replace("\"","");
            for(Log l: setLogs){
                if(find(l,entry)) set.add(l.event);
            }
        }
        return set;
    }

    public Set<Status> getSetStatus(String entry, Set<Log> copySetLogs){
        Set set = new HashSet();
        if(entry.length() == 0) for(Log l: setLogs) set.add(l.status);
        else {
            String[] string = entry.trim().split("=");
            String field2 = string[0].trim().split(" ")[1];
            String word = entry.replace(string[0]+"= \"","").replace("\"","");
            for(Log l: setLogs){
                if(find(l,entry)) set.add(l.status);
            }
        }
        return set;
    }

    private class Log {
        protected String ip;
        protected String name;
        protected Date date;
        protected Event event;
        protected Status status;
        protected int task;

        public Log(String ip, String nameUser, Date date, Event event, Status status, int task) {
            this.ip = ip;
            this.name = nameUser;
            this.date = date;
            this.event = event;
            this.status = status;
            this.task = task;
        }
        public Log(){
            this.task = 0;
        }

        @Override
        public String toString() {
            return "Log{" + "ip='" + ip + '\'' + ", user='" + name + '\'' + ", date=" + date + ", event=" + event +
                    ", status=" + status + ", numberTask='" + task + '\'' + '}';
        }

        public String getIp() { return ip; }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public Date getDate() { return date; }

        public void setDate(Date date) { this.date = date; }

        public Event getEvent() { return event; }

        public void setEvent(Event event) { this.event = event; }

        public int getTask() { return task; }

        public void setTask(int numberTask) { this.task = numberTask; }
    }
}

 */


/*
// парсит два поля + промежуток времени

package com.javarush.task.task39.task3913;
import com.javarush.task.task39.task3913.query.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static com.javarush.task.task39.task3913.Event.LOGIN;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    protected HashSet<Log> setLogs = new HashSet<>();
    protected List<String> listsFromFile;
    public Path logDir;                                                    // директория с логами (логов может быть несколько,все они должны иметь расширение log)

    public LogParser(Path logDir) {
        this.logDir = logDir;
        listsFromFile = new ArrayList<>();
        ArrayDeque list = new ArrayDeque<Path>();
        list.add(logDir);
        readRecords((ArrayDeque<Path>) list);
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {             //должен возвращать количество уникальных IP адресов за выбранный период
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {               //должен возвращать множество, содержащее все не повторяющиеся IP ( IP - String)
        Set set = new HashSet();
        for (Log l: setLogs) if(isSuitDate(l.date, after, before)) set.add(l.ip);
        return set;
    }

    @Override
    public Set<String> getIPsForUser(String user, Date after, Date before) {    //должен возвращать IP, с которых работал переданный пользователь.
        Set set = new HashSet();
        for (Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user)) set.add(l.ip);
        return set;
    }

    @Override
    public Set<String> getIPsForEvent(Event event, Date after, Date before) {      //должен возвращать IP, с которых было произведено переданное событие
        Set set = new HashSet();
        for (Log l: setLogs) if(l.event.toString().startsWith(event.toString()) && isSuitDate(l.date, after, before)) set.add(l.ip);
        return set;
    }

    @Override
    public Set<String> getIPsForStatus(Status status, Date after, Date before) {     //должен возвращать IP, события с которых закончилось переданным статусом
        Set set = new HashSet();
        for (Log l: setLogs) if (l.status.equals(status) && isSuitDate(l.date, after, before)) set.add(l.ip);
        return set;
    }

    public boolean isSuitDate(Date date, Date after, Date before) {
        boolean result = false;
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        if ((date.getTime()>=after.getTime())&&(date.getTime()<=before.getTime())) result = true;
        return result;
    }

    public boolean isSuitDateTarkSeven(Date date, Date after, Date before) {
        boolean result = false;
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        if ((date.getTime()>after.getTime())&&(date.getTime()<before.getTime())) result = true;
        return result;
//        if ((after == null || !date.before(after)) && (before == null || !date.after(before))) return true;
    }

    public Date getDate(String string) {
        Date date = null;
        if(string == null) return null; //string = String.valueOf(Integer.MIN_VALUE);
            try {
                date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH).parse(string);
            } catch (ParseException e) {
                try {
                    date = new SimpleDateFormat("d.M.y", Locale.ENGLISH).parse(string);
                } catch (ParseException e1){
                        return null;
                }
            }
            return date;
        }

    public Event getEvent(String word) {
        String[] words = word.split("\\h+");
        switch (words[0].trim()) {
            case "DONE_TASK": return Event.DONE_TASK;
            case "LOGIN": return LOGIN;
            case "DOWNLOAD_PLUGIN": return Event.DOWNLOAD_PLUGIN;
            case "WRITE_MESSAGE": return Event.WRITE_MESSAGE;
            case "SOLVE_TASK": return Event.SOLVE_TASK;
            default: return null;
        }
    }

    public Status getStatus(String word) {
        switch (word) {
            case "OK": return Status.OK;
            case "FAILED": return Status.FAILED;
            case "ERROR": return Status.ERROR;
            default: return null;
        }
    }

    public int getTask (String word){
        String[] words = word.split("\\h+");
        if(words.length != 2) return -1;
        else {
            try {
                int number = Integer.parseInt(words[1].trim());
                return number;
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }

    public void readRecords(ArrayDeque<Path>listOfPahts) {
        List<String> temp = new ArrayList();
        Path log = listOfPahts.pop();
        if (Files.isRegularFile(log) && log.getFileName().toString().endsWith(".log")) {
            try {
                temp = Files.readAllLines(log, StandardCharsets.UTF_8);
                listsFromFile.addAll(temp);
                for (int i = 0; i < temp.size(); i++) {
                    String line = temp.get(i).trim();
                    if(line.length() == 0) continue;
                    String[] s = line.split("\\t+");
                    String ip = s[0].trim();
                    String name = s[1].trim();
                    Date date = getDate(s[2]);
                    Event event = getEvent(s[3].trim());
                    Status status = getStatus(s[4]);
                    int task = getTask(s[3]);
                    setLogs.add(new Log(ip, name, date, event, status, task));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (Files.isDirectory(log)) {
                File[] listOfFiles = log.toFile().listFiles();
                for (File l : listOfFiles) {
                    listOfPahts.addLast(l.toPath());
                }
                readRecords(listOfPahts);
            }
        }
        if(!listOfPahts.isEmpty()){
            readRecords(listOfPahts);
        }
    }

    @Override
    public Set<String> getAllUsers() {                                          //  должен возвращать всех пользователей.
        Set set = new HashSet();
        for(Log l: setLogs) set.add(l.name);
        return set;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {                      //  должен возвращать количество уникальных пользователей
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before)) set.add(l.name);
        return set.size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {    // должен возвращать кол-во событий от переданного польз-ля за выбр-й период
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user)) set.add(l.event);
        return set.size();
    }

    @Override                      // должен возвращать множество содержащее пользователей, которые работали с переданного IP адреса за выбранный период
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.ip.equals(ip)) set.add(l.name);
        return set;
    }

    @Override                      // должен возвращать множество содержащее пользователей, которые были залогинены за выбранный период
    public Set<String> getLoggedUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(LOGIN)) set.add(l.name);
        return set;
    }

    @Override                      // должен возвращать множество содержащее пользователей, которые скачали плагин за выбранный период
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.DOWNLOAD_PLUGIN) && l.status.equals(Status.OK)) set.add(l.name);
        return set;
    }

    @Override                       // должен возвращать множество содержащее пользователей, которые отправили сообщение за выбранный период
    public Set<String> getWroteMessageUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.WRITE_MESSAGE) && l.status.equals(Status.OK)) set.add(l.name);
        return set;
    }

    @Override                        // должен возвращать множество содержащее пользователей, которые решали любую задачу за выбранный период
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.SOLVE_TASK)) set.add(l.name);
        return set;
    }

    @Override                        // должен возвращать множество содержащее пользователей, которые решали задачу с номером task за выбранный период
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.SOLVE_TASK) && l.task == task) set.add(l.name);
        return set;
    }

    @Override                         // должен возвращать множество содержащее пользователей, которые решили любую задачу за выбранный период
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.DONE_TASK)) set.add(l.name);
        return set;
    }

    @Override                         // должен возвращать множество содержащее пользователей, которые решили задачу с номером task за выбранный период
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.event.equals(Event.DONE_TASK) && l.task == task ) set.add(l.name);
        return set;
    }

    @Override                         // должен возвращать множество дат, когда переданный пользователь произвел переданное событие за выбранный период
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(event)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать множество дат, когда любое событие не выполнилось за выбранный период
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.status.equals(Status.FAILED)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать множество дат, когда любое событие закончилось ошибкой за выбранный период
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.status.equals(Status.ERROR)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать дату, когда переданный пользователь впервые залогинился за выбранный период, или null
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        Set set = new TreeSet();
        Date minDate;
        if(getDatesForUserAndEvent(user, Event.LOGIN, after, before).isEmpty()) return null;
        else {
            minDate = Collections.min(getDatesForUserAndEvent(user, Event.LOGIN, after, before));                 // min дата выполненного события
        }
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user)) {
            long curent = l.date.getTime();
            if(curent < minDate.getTime()) return null;
        }
        return minDate;
    }

    @Override            // должен возвращать дату, когда переданный пользователь впервые попытался решить задачу с номером task за выбранный период, или null
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        Set<Date> set = new TreeSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(Event.SOLVE_TASK) && l.task == task) set.add(l.date);
        if(!set.isEmpty()) return Collections.min(set);
        return null;
    }

    @Override                         // должен возвращать дату, когда переданный пользователь впервые решил задачу с номером task за выбранный период, или null
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        Set<Date> set = new TreeSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(Event.DONE_TASK) && l.task == task) set.add(l.date);
        if(!set.isEmpty()) return Collections.min(set);
        return null;
    }

    @Override                         // должен возвращать множество дат, когда переданный пользователь написал сообщение за выбранный период
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(Event.WRITE_MESSAGE)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать множество дат, когда переданный пользователь скачал плагин за выбранный период
    public Set<Date> getDatesWhenUserDownloadedPlugin(String user, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user) && l.event.equals(Event.DOWNLOAD_PLUGIN)) set.add(l.date);
        return set;
    }

    @Override                         // должен возвращать количество уникальных событий за выбранный период
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override                         // должен возвращать множество уникальных событий за выбранный период
    public Set<Event> getAllEvents(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) ) set.add(l.event);
        return set;
    }

    @Override                         // должен возвращать множество уникальных событий, которые происходили с переданного IP адреса за выбранный период
    public Set<Event> getEventsForIP(String ip, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.ip.equals(ip)) set.add(l.event);
        return set;
    }

    @Override                         // должен возвращать множество уникальных событий, которые произвел переданный пользователь за выбранный период
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.name.equals(user)) set.add(l.event);
        return set;
    }

    @Override                         // должен возвращать множество уникальных событий, у которых статус выполнения FAILED за выбранный период
    public Set<Event> getFailedEvents(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.status.equals(Status.FAILED)) set.add(l.event);
        return set;
    }

    @Override                         // должен возвращать множество уникальных событий, у которых статус выполнения ERROR за выбранный период
    public Set<Event> getErrorEvents(Date after, Date before) {
        Set set = new HashSet();
        for(Log l: setLogs) if(isSuitDate(l.date, after, before) && l.status.equals(Status.ERROR)) set.add(l.event);
        return set;
    }
                                      // должен правильно возвращать количество попыток решить задачу с номером task за период с null по null.
    @Override                         // должен возвращать количество попыток решить задачу с номером task за выбранный период
    public int getNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        int count = 0;
        for(Log l: setLogs) if(l.event.equals(Event.SOLVE_TASK) && l.task == task && isSuitDate(l.date, after, before)) count++;
        return count;
    }

    @Override                         // должен возвращать количество успешных решений задачи с номером task за выбранный период
    public int getNumberOfSuccessfulAttemptToSolveTask(int task, Date after, Date before) {
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        int count = 0;
        for(Log l: setLogs) if(l.event.equals(Event.DONE_TASK) && l.task == task && isSuitDate(l.date, after, before)) count++;
        return count;
    }

    @Override                         // должен возвращать мапу (номер_задачи : количество_попыток_решить_ее) за выбранный период
    public Map<Integer, Integer> getAllSolvedTasksAndTheirNumber(Date after, Date before) {
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        HashMap<Integer, Integer> map = new HashMap<>();
        for(Log l: setLogs) if(l.event == Event.SOLVE_TASK && l.task > 0 && isSuitDate(l.date, after, before)) {
                if (!map.containsKey(l.task)) map.put(l.task, 1);
                else map.put(l.task, map.get(l.task) + 1);
        }
        return map;
    }

    @Override                         // должен возвращать мапу (номер_задачи : сколько_раз_ее_решили) за выбранный период
    public Map<Integer, Integer> getAllDoneTasksAndTheirNumber(Date after, Date before) {
        if (after == null) after = new Date(0);
        if (before == null) before = new Date(Long.MAX_VALUE);
        HashMap<Integer, Integer> map = new HashMap<>();
        for(Log l: setLogs) if(l.event == Event.DONE_TASK && l.task > 0 && isSuitDate(l.date, after, before)){
            if(!map.containsKey(l.task)) map.put(l.task, 1);
            else map.put(l.task, map.get(l.task)+1);
        }
        return map;
    }
//task7    get ip for user = "Eduard Petrovich Morozko" and date between "11.12.2013 0:00:00" and "03.01.2014 23:59:59".
//task8    get field1 for field2 = "value1" and field3 = "value2" and date between "after" and "before"
    @Override
    public Set<Object> execute(String entry) {
        Set<Object> set = new HashSet<>();
//        System.out.println("444 "+ entry);
        if(entry.length() == 0) return set;
            String[] string = entry.trim().split(" for ");
//            System.out.println("436 "+ string.length);
//            for (String s : string) System.out.print(" (" + s + ") ");
            String field1 = string[0].trim().split(" ")[1];
            String word = entry.replace(string[0],"");
//            System.out.println();
//            System.out.println("438 field ("+field1+ ") : word (" +word+")");
            if (field1.contains("ip")) set.addAll(getSetIp(word));
            if (field1.contains("user")) set.addAll(getSetUser(word));
            if (field1.contains("date")) set.addAll(getSetDate(word));
            if (field1.contains("event")) set.addAll(getSetEvent(word));
            if (field1.contains("status")) set.addAll(getSetStatus(word));
        return set;
    }
//   get ip for user = "Eduard Petrovich Morozko" and date between "11.12.2013 0:00:00" and "03.01.2014 23:59:59".
    public Set<Integer> getSetIp(String entry){
        Set set = new HashSet();
//        System.out.println("464 " +entry);
        if(entry.length() == 0) for(Log l: setLogs) set.add(l.ip);
        else {
            String[] string = entry.trim().split("=");
            String field2 = string[0].trim().split(" ")[1];
//            String word = entry.replace(field2+"=\"","").replace("\"","");
            String word = entry.replace(string[0]+"= \"","").replace("\"","");
//            System.out.println("473 field2 ("+field2+") : word ("+word+")");
            for(Log l: setLogs){
                if(find(l,field2,word)) set.add(l.ip);
            }
        }
        return set;
    }

    //   get event for ip = "[any_ip]"                and date between "[after]" and "[before]"
    //   get ip for user = "Eduard Petrovich Morozko" and date between "11.12.2013 0:00:00" and "03.01.2014 23:59:59".
    //   get ip for user = \"Vasya Pupkin\"
    public boolean find(Log log, String field2, String entry) {
//        System.out.println("\n 488");
//        System.out.println(field2);
//        System.out.println(entry);
        String value1 = entry.trim();
        String after = null;
        String before = null;

        String[] string = entry.trim().split("and");
        if(string.length > 1 && string[1].trim().contains("date")) {
            value1 = string[0].trim();
            after = string[1].replace("date between ","").trim();
            before = string[2].trim();
        }
        switch (field2.trim()) {
            case "ip": return log.getIp().equals(value1) && isSuitDateTarkSeven(log.date,getDate(after),getDate(before));
            case "user": return log.getName().equals(value1) && isSuitDateTarkSeven(log.date,getDate(after),getDate(before));
            case "date": return log.getDate().equals(getDate(value1)) && isSuitDateTarkSeven(log.date,getDate(after),getDate(before));
            case "event": return log.event.equals(getEvent(value1)) && isSuitDateTarkSeven(log.date,getDate(after),getDate(before));
            case "status": return log.status.equals(getStatus(value1)) && isSuitDateTarkSeven(log.date,getDate(after),getDate(before));
            default: return false;
        }
    }

    public Set<String> getSetUser(String entry){
        Set set = new HashSet();

        if(entry.length() == 0) for(Log l: setLogs) set.add(l.name);
        else {
            String[] string = entry.trim().split("=");
            String field2 = string[0].trim().split(" ")[1];
            String word = entry.replace(string[0]+"= \"","").replace("\"","");
            for(Log l: setLogs){
                if(find(l,field2,word)) set.add(l.name);
            }
        }
        return set;
    }

    public Set<Date> getSetDate(String entry){
        Set set = new HashSet();
        if(entry.length() == 0) for(Log l: setLogs) set.add(l.date);
        else {
            String[] string = entry.trim().split("=");
            String field2 = string[0].trim().split(" ")[1];
            String word = entry.replace(string[0]+"= \"","").replace("\"","");
            for(Log l: setLogs){
                if(find(l,field2,word)) set.add(l.date);
            }
        }
        return set;
    }

    public Set<Event> getSetEvent(String entry){
        Set set = new HashSet();
        if(entry.length() == 0) for(Log l: setLogs) set.add(l.event);
        else {
            String[] string = entry.trim().split("=");
            String field2 = string[0].trim().split(" ")[1];
            String word = entry.replace(string[0]+"= \"","").replace("\"","");
            for(Log l: setLogs){
                if(find(l,field2,word)) set.add(l.event);
            }
        }
        return set;
    }

    public Set<Status> getSetStatus(String entry){
        Set set = new HashSet();
        if(entry.length() == 0) for(Log l: setLogs) set.add(l.status);
        else {
            String[] string = entry.trim().split("=");
            String field2 = string[0].trim().split(" ")[1];
            String word = entry.replace(string[0]+"= \"","").replace("\"","");
            for(Log l: setLogs){
                if(find(l,field2,word)) set.add(l.status);
            }
        }
        return set;
    }

    private class Log {
        protected String ip;
        protected String name;
        protected Date date;
        protected Event event;
        protected Status status;
        protected int task;

        public Log(String ip, String nameUser, Date date, Event event, Status status, int task) {
            this.ip = ip;
            this.name = nameUser;
            this.date = date;
            this.event = event;
            this.status = status;
            this.task = task;
        }
        public Log(){
            this.task = 0;
        }

        @Override
        public String toString() {
            return "Log{" + "ip='" + ip + '\'' + ", user='" + name + '\'' + ", date=" + date + ", event=" + event +
                    ", status=" + status + ", numberTask='" + task + '\'' + '}';
        }

        public String getIp() { return ip; }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public Date getDate() { return date; }

        public void setDate(Date date) { this.date = date; }

        public Event getEvent() { return event; }

        public void setEvent(Event event) { this.event = event; }

        public int getTask() { return task; }

        public void setTask(int numberTask) { this.task = numberTask; }
    }
} */


/*
    public Set<Integer> getSetIp(String entry){
        Set set = new HashSet();
        String[] stringSet;
        System.out.println("464 " +entry);
        if(entry.length() == 0){
            for(Log l: setLogs) set.add(l.ip);
        } else {
            stringSet = entry.trim().split(" and ");                   // делим на части по "and"
            if(stringSet.length <= 3) {
                stringSet = entry.trim().split("=");                   // делим на части по "="
//            System.out.println("417 " + stringSet.length);
//            for(String s : stringSet) System.out.print(s+" ");
                if(stringSet.length == 2) {                                           // *** финальный цикл
                    String field2 = stringSet[0].trim().split(" ")[1];
                    String word = entry.replace(stringSet[0] + "= \"", "").replace("\"", "");
                    for (Log l : setLogs) {
                        if (find(l, field2, word)) set.add(l.ip);
                    }
                }

            } else {

                System.out.println("417 " + stringSet.length);
                for(String s : stringSet) System.out.print("("+s+") ");



            }




        }
        return set;
    }
*/

package com.javarush.task.task37.task3713.space.crew;

public abstract class AbstractCrewMember {
    public enum CompetencyLevel {
        NOVICE,             // 0  cabinBoy
        INTERMEDIATE,       // 1  engineer
        ADVANCED,           // 2  FirstMate
        EXPERT              // 3  Captain
        }

    protected CompetencyLevel competencyLevel;

    protected AbstractCrewMember nextCrewMember;

    public void setNextCrewMember(AbstractCrewMember nextCrewMember) {
        this.nextCrewMember = nextCrewMember;
    }

    public void handleRequest(CompetencyLevel competencyLevel, String request) {

        if (this.competencyLevel ==  competencyLevel) {
            doTheJob(request);
        }

        if (this.competencyLevel.compareTo(competencyLevel) < 0 ) {
            nextCrewMember.handleRequest(competencyLevel, request);
        }
    }

    /*
    public void handleRequest(CompetencyLevel competencyLevel, String request) {

*/
/*
        System.out.println("competencyLevel = "+competencyLevel);
        System.out.println("nextCrewMember = "+nextCrewMember.competencyLevel);
        System.out.println("this.competencyLevel = "+this.competencyLevel+"\n");
*//*



        if (competencyLevel == this.competencyLevel) {
            doTheJob(request);
        } else if (!nextCrewMember.equals(null)) {
            try {
                nextCrewMember.handleRequest(competencyLevel, request);
            } catch (NullPointerException e) {
                doTheJob(request);
            }
        }
    }
*/
    protected abstract void doTheJob(String request);
}
/*
Chain of Responsibility
Амиго, у нас проблема! Во время визита на планету #IND893 мы рискнули отдать на аутсорсинг автоматизацию входящих задач для членов команды.
В это сложно поверить, но похоже всю работу теперь должен выполнять первый помощник!

Необходимо срочно исправить поведение программы, ведь полы может помыть и юнга, а приказ "к бою!" может дать только капитан.

P.S. Постарайся реализовать метод handleRequest таким образом, чтобы при добавлении новых должностей нам не требовалось вносить в него изменения.
Другие методы не трогай.

P.P.S. Все enum поддерживают интерфейс Comparable.


Требования:
1. Запрос должен быть обработан текущим членом команды, если это возможно.
2. Запрос должен быть передан по цепочке выше, если текущий член команды не может его обработать.
3. Классы CabinBoy, Engineer, FirstMate и Captain должны быть потомками класса AbstractCrewMember.
4. Класс AbstractCrewMember должен быть абстрактным.
 */
/*
 public void handleRequest(CompetencyLevel competencyLevel, String request) {
        if (nextCrewMember.competencyLevel == CompetencyLevel.EXPERT) {
            doTheJob(request);
        } else if (nextCrewMember != null) {
            nextCrewMember.handleRequest(competencyLevel, request);
        }
    }
 */



 /*
        public void handleRequest(CompetencyLevel competencyLevel, String request) {
        AbstractCrewMember member = this;
        while (member.competencyLevel.compareTo(competencyLevel) < 0 ) {
            member = member.nextCrewMember;
        }
        member.doTheJob(request);
    }

*/

 /*
        System.out.println(AbstractCrewMember.CompetencyLevel.values()[0]);
        System.out.println(AbstractCrewMember.CompetencyLevel.values()[1]);
        System.out.println(AbstractCrewMember.CompetencyLevel.values()[2]);
        System.out.println(AbstractCrewMember.CompetencyLevel.values()[3]);
*/

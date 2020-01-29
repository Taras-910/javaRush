package com.javarush.task.task37.task3702;

import com.javarush.task.task37.task3702.female.FemaleFactory;
import com.javarush.task.task37.task3702.male.MaleFactory;


public class FactoryProducer {

    public static enum HumanFactoryType {
        MALE, FEMALE;
    }

        public static AbstractFactory getFactory(HumanFactoryType factory) {

            if (factory == HumanFactoryType.MALE){
                return new MaleFactory();
            }

            if (factory == HumanFactoryType.FEMALE){
                return new FemaleFactory();
            }
            return null;
        }
    }


    //AbstractFactory factory = FactoryProducer.getFactory(FactoryProducer.HumanFactoryType.FEMALE);
//        useFactory(factory);
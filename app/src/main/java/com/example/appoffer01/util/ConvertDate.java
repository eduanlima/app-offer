package com.example.appoffer01.util;

public class ConvertDate {
    public static String convert(String date){
        String dateVet[];
        date = date.substring(0, 10);

        dateVet = date.split("-");

        date = dateVet[2].concat("/" + dateVet[1].concat("/" + dateVet[0]));

        return date;
    }
}

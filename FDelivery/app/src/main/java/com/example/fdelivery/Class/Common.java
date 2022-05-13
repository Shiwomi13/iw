package com.example.fdelivery.Class;

public class Common {
    public static User currentUser;
    public static final String DELETE = "Удалить";
    public static final String UPDATE = "Изменить";

    public static String convertCodeStatus(String status){
        if(status.equals("0"))
            return "Обработка";
        else if(status.equals("1"))
            return  "В пути";
        else
            return "Доставлен";
    }
}

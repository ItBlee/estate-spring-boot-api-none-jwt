package com.itblee.utils;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
    public static boolean isValidUsername(String username) {
        return username != null && !username.isBlank() && !username.contains(" ");
    }

    public static boolean isValidPassword(String password) {
        return password != null && !password.isBlank() && !password.contains(" ");
    }

    public static boolean isValidName(String name) {
        if (name == null)
            return false;
        StringTokenizer tokenizer = new StringTokenizer(name, " ", false);
        while (tokenizer.hasMoreTokens())
            if (Character.isLowerCase(tokenizer.nextToken().charAt(0)))
                return false;
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    public static boolean isValidEmail(String mail) {
        if (mail == null)
            return false;
        String regx = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.matches(regx, mail);
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null)
            return false;
        if (phone.length() == 10) {
            if (phone.startsWith("0")){
                String regex = ".*[a-zA-Z].*";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcherText = pattern.matcher(phone);
                return !matcherText.matches();
            }
        }
        return false;
    }

    public static boolean isValidBirthday(Date date) {
        if (date == null)
            return false;
        java.util.Date current = new java.util.Date(System.currentTimeMillis());
        if (date.after(current))
            return false;
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(current);
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.setTime(date);
        int dateYear = calendar.get(Calendar.YEAR);
        final int min_age = 18;
        final int max_age = 70;
        return (currentYear - dateYear) > min_age && (currentYear - dateYear) <= max_age;
    }
}

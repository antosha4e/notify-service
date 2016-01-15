package org.antosha;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by antosha4e on 09.12.15.
 */
public class Test {
    public static void main(String[] args) {
        String mydata = "Ваш пароль - 12331 для";
        Pattern pattern = Pattern.compile("пароль - (.*?) ");
        Matcher matcher = pattern.matcher(mydata);

        if(matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}

package com.jack.utils;

public class MathSalary {

    public static float[] getSalary(String str) {
        float[] salary = {0f,0f};
        if (str != "" && str != null && str != " ") {

            float min = Float.valueOf(str.substring(0, str.lastIndexOf("-")));
            float max = Float.valueOf(str.substring(str.lastIndexOf("-") + 1, str.lastIndexOf("/") - 1));
            String s = str.substring(str.lastIndexOf("/") - 1, str.lastIndexOf("/"));
            if (s.equals("万")) {
                salary[0] = min * 10000;
                salary[1] = max * 10000;
            } else {
                salary[0] = min * 1000;
                salary[1] = max * 1000;
            }

        }
        return salary;

    }



}

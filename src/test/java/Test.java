import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {
    static ArrayList<String> workWeek = new ArrayList<String>();
    static ArrayList<String> curPlan = new ArrayList<String>();
    static Map<String, String> ruls = new HashMap<String, String>();

    static {
        workWeek.add("d-1");
        workWeek.add("d-2");
        workWeek.add("d-3");

        curPlan.add("1");
        curPlan.add("2");
        curPlan.add("3");

        ruls.put("d-2", "2");
        ruls.put("d-3", "3");
    }

    public static void main(String[] args) throws Exception {
        back(0);
    }


    static boolean check(String aCurPlan, String day) {
        String rull = ruls.get(day);
        if (rull != null) {
            if (!rull.equals(aCurPlan)) {
                //System.out.println(ruls.get(day));
                return false;
            }
        }
        return true;
    }


    static void back(int k) {
        for (String aCurPlan : curPlan) {
            if (check(aCurPlan, workWeek.get(k))) {
                map2.put(workWeek.get(k), aCurPlan);
                if (k == workWeek.size() - 1) {
                    System.out.println(map2);
                } else {
                    back(k + 1);
                }
            }
        }
    }

    static Map<String, String> map2 = new HashMap<String, String>();
}
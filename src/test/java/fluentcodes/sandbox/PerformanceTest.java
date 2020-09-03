package fluentcodes.sandbox;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformanceTest {
    private Gson gson = new Gson();
    private String jsonMap;
    private String jsonList;
    @Test
    public void start () {
        System.out.println("|max|listSerialize|untypedList|typedList|mapSerialize|untypedMap|typedMap|");
        System.out.println("|----:|----:|----:|----:|----:|----:|----:|");
        run(100000);
        run(1000000);
        run(5000000);
    }

    private void run (int max) {
        System.out.print( "|" + max + "|");
        System.out.print(runListToJson(max) + "|");
        System.out.print(parseListUntyped() + "|");
        System.out.print(parseListTyped() + "|");
        System.out.print(runMapToJson(max) + "|");
        System.out.print(parseMapUntyped() + "|");
        System.out.println(parseMapTyped() + "|");

    }

    private long runListToJson (int max) {
        Simple simple;
        List<Simple> list = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            simple = new Simple("string" + i, i);
            list.add(simple);
        }
        long start = System.currentTimeMillis();
        jsonList = gson.toJson(list);
        return System.currentTimeMillis() - start;
    }

    private long parseListUntyped() {
        long start = System.currentTimeMillis();
        gson.fromJson(jsonList, List.class);
        return System.currentTimeMillis() - start;
    }

    private long parseListTyped() {
        long start = System.currentTimeMillis();
        Type type = new TypeToken<List<Simple>>() {
        }.getType();
        gson.fromJson(jsonList, type);
        return System.currentTimeMillis() - start;
    }

    private long runMapToJson (int max) {
        Map<Integer, Simple> map = new HashMap<>();
        Simple simple;
        for (int i = 0; i < max; i++) {
            simple = new Simple("string" + i, i);
            map.put(i, simple);
        }
        long start = System.currentTimeMillis();
        jsonMap = gson.toJson(map);
        return System.currentTimeMillis() - start;
    }

    private long parseMapUntyped() {
        long start = System.currentTimeMillis();
        gson.fromJson(jsonMap, Map.class);
        return System.currentTimeMillis() - start;
    }

    private long parseMapTyped() {
        long start = System.currentTimeMillis();
        Type type = new TypeToken<Map<Integer, Simple>>(){}.getType();
        gson.fromJson(jsonMap, type);
        return System.currentTimeMillis() - start;
    }



}

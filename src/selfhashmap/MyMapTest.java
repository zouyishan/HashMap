package selfhashmap;

import java.util.HashMap;
import java.util.Map;

public class MyMapTest {
    public static void main(String[] args) {

        MyHashMap<String, String> map = new MyHashMap<>();
        Long start1 = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++ ) {
            map.put("key" + i, "value" + i);
        }
        Long end1 = System.currentTimeMillis();
        System.out.println("MyHashMap耗时" + (end1 - start1) + "ms");
        System.out.println(map.size());


        System.out.println("==============================================");
        Map<String, String> map1 = new HashMap<>();
        Long start2 = System.currentTimeMillis();
        for (int i = 1; i <= 1000000; i++) {
            map1.put("key" + i, "value" + i);
        }
        Long end2 = System.currentTimeMillis();
        System.out.println("JDK的耗时" + (end2 - start2) + "ms");
        System.out.println(map1.size());
    }
}

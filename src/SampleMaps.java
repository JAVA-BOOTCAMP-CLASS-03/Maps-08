import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SampleMaps {

    private static void sampleHash() {
        /*
            No es Thread Safe
         */
        Map<Integer, String> hash = new HashMap<>();

        hash.put(1, "uno");
        hash.put(5, "cinco");
        hash.put(10, "diez");
        hash.put(4, "cuatro");

        /*
          Lo muestra en orden pero NO garantiza el orden
         */
        System.out.println("HASH -> " + hash);
        System.out.println("HASH KEYS -> " + hash.keySet());


        ConcurrentMap<Integer, String> synch = new ConcurrentHashMap<>();
        synch.putAll(hash);
        System.out.println("HASH -> " + synch);
        System.out.println("HASH KEYS -> " + synch.keySet());

        /*
            Podemos utilizar cualquier objecto como key pero, para que el mapa funcione bien debe implementar equals() y hashCode()
         */

        class SampleKey {
            private int intValue;

            private String strValue;

            public SampleKey(int v, String s) {
                this.intValue = v;
                this.strValue = s;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }

                SampleKey sampleKey = (SampleKey) o;
                return Objects.equals(intValue, sampleKey.intValue) && Objects.equals(strValue, sampleKey.strValue);
            }

            @Override
            public int hashCode() {
                return Objects.hash(intValue, strValue);
            }

            @Override
            public String toString() {
                return "[" + this.intValue + " - " + this.strValue + "]";
            }
        }

        Map<SampleKey, Object> sampleKeyMap = new HashMap<>();

        sampleKeyMap.put(new SampleKey(1, "uno"), Boolean.FALSE);
        sampleKeyMap.put(new SampleKey(2, "dos"), "Hola");
        sampleKeyMap.put(new SampleKey(3, "tres"), 10);

        System.out.println("HASH IMPLEMENTED KEY -> " + sampleKeyMap);
    }

    private static void sampleLinked() {
        /*
            Access-Order FALSE por defecto
         */
        Map<Integer, String> linked = new LinkedHashMap<>();

        linked.put(1, "uno");
        linked.put(5, "cinco");
        linked.put(10, "diez");
        linked.put(4, "cuatro");

        System.out.println("LINKED KEYS -> " + linked.keySet());

        Map<Integer, String> linkedAccessOrder = new LinkedHashMap<>(10, .75f, true);

        linkedAccessOrder.put(1, "uno");
        linkedAccessOrder.put(5, "cinco");
        linkedAccessOrder.put(10, "diez");
        linkedAccessOrder.put(4, "cuatro");

        System.out.println("LINKED ACCESS ORDER KEYS -> " + linkedAccessOrder.keySet());

        linkedAccessOrder.get(10);
        linkedAccessOrder.get(5);

        System.out.println("LINKED ACCESS ORDER KEYS -> " + linkedAccessOrder.keySet());

        /*
         * Podemos implementar un LRU (Least Recently Used) muy facil.
         */

        /*
            También podemos redefinir el método removeEldestEntry (devuelve FALSE por defecto) para
            quedarnos con los últimos N elementos.
         */
        Map<Integer, String> linkedSizeFix = new LinkedHashMap<>() {
            protected boolean removeEldestEntry(Map.Entry<Integer,String> eldest) {
                return size() > 5;
            }
        };

        linkedSizeFix.put(1, "uno");
        System.out.println("FIXED SIZE -> " + linkedSizeFix);
        linkedSizeFix.put(2, "dos");
        System.out.println("FIXED SIZE -> " + linkedSizeFix);
        linkedSizeFix.put(3, "tres");
        System.out.println("FIXED SIZE -> " + linkedSizeFix);
        linkedSizeFix.put(4, "cuatro");
        System.out.println("FIXED SIZE -> " + linkedSizeFix);
        linkedSizeFix.put(5, "cinco");
        System.out.println("FIXED SIZE -> " + linkedSizeFix);
        linkedSizeFix.put(6, "seis");
        System.out.println("FIXED SIZE -> " + linkedSizeFix);
        linkedSizeFix.put(7, "siete");
        System.out.println("FIXED SIZE -> " + linkedSizeFix);
    }

    private static void sampleTree() {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(3, "tres");
        treeMap.put(2, "dos");
        treeMap.put(1, "uno");
        treeMap.put(5, "cinco");
        treeMap.put(4, "cuatro");

        /*
            Obtendremos una salida ordenada idem HashMap pero aca SI se asegura el orden.
         */
        System.out.println("TREE KEYS -> " + treeMap.keySet());

        Map<Integer, String> treeMapInvertido = new TreeMap<>(Comparator.reverseOrder());
        treeMapInvertido.putAll(treeMap);

        System.out.println("TREE KEYS (INVERTIDO) -> " + treeMapInvertido.keySet());

        System.out.println("FIRST KEY -> " + treeMap.firstKey());
        System.out.println("LAST KEY -> " + treeMap.lastKey());
        System.out.println("TREE MENOR A KEY 3 -> " + treeMap.headMap(3));
        System.out.println("TREE MENOR O IGUAL A KEY 3 -> " + treeMap.headMap(3, true));
        System.out.println("TREE MAYOR A KEY 3 -> " + treeMap.tailMap(3, false));
        System.out.println("TREE MAYOR O IGUAL A KEY 3 -> " + treeMap.tailMap(3));
    }

    public static void main(String[] args) {

        sampleHash();

        sampleLinked();

        sampleTree();
    }

}

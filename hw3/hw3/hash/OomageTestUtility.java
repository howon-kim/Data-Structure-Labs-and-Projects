package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int [] table = new int[M];
        for (Oomage oomage : oomages) {
            int bucketNum = (oomage.hashCode() & 0x7FFFFFFF) % M;
            table[bucketNum] += 1;
        }
        int n = oomages.size();
        for (int index : table) {
            if (index < n / 50 || index > n / 2.5) {
                return false;
            }
        }
        return true;
    }
}

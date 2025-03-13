package algorithms;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MaxPosAndNeg {
    public static void main(String[] args) {
//        int[] nums = new int[]{-2, -1, -1, 1, 2, 3};
//        int[] nums = new int[]{-3, -2, -1, 0, 0, 1, 2};
//        int[] nums = new int[]{5, 20, 66, 1231};

//        HashMap<String, Integer> negPos = new HashMap<>();
//        maximumCountV2(nums, negPos);
//        System.out.println(Math.max(negPos.get("neg"), negPos.get("pos")));
//        System.out.println(maximumCount(nums));


        Random random = new Random();
        int count = 10000;
        int[] nums = new int[count];
        for (int i = 0; i < count; i++) {
            nums[i] = random.nextInt();
        }
        Arrays.sort(nums);

        long now = System.currentTimeMillis();

//        HashMap<String, Integer> negPos = new HashMap<>();
//        maximumCountV2(nums, negPos);
//        long ms = System.currentTimeMillis() - now;
//        System.out.println("O(log n) = " + Math.max(negPos.get("neg"), negPos.get("pos")) + ". Spent " + ms + " ms");


        long ms = System.currentTimeMillis() - now;
        System.out.println("O(n) = " + maximumCount(nums) + ". Spent " + ms + " ms");

    }

    //O(n)
    public static int maximumCount(int[] nums) {
        if (nums.length == 0) return 0;

        int pos = 0;
        int neg = 0;

        for (int num : nums) {
            if (num == 0) continue;
            if (num > 0) {
                pos++;
            } else {
                neg++;
            }
        }

        return Math.max(pos, neg);
    }

    //O(log n)
    public static void maximumCountV2(int[] nums, Map<String, Integer> negPos) {
        if (nums.length == 0) return;
        if (!negPos.containsKey("neg")) {
            negPos.put("neg", 0);
        }
        if (!negPos.containsKey("pos")) {
            negPos.put("pos", 0);
        }

        if (nums.length == 1) {
            if (nums[0] == 0) return;
            if (nums[0] < 0) {
                negPos.put("neg", negPos.get("neg") + 1);
            } else {
                negPos.put("pos", negPos.get("pos") + 1);
            }
            return;
        }

        int middleIndex = nums.length / 2;
        int[] left = Arrays.copyOfRange(nums, 0, middleIndex);
        int[] right = Arrays.copyOfRange(nums, middleIndex, nums.length);

        maximumCountV2(left, negPos);
        maximumCountV2(right, negPos);
    }
}

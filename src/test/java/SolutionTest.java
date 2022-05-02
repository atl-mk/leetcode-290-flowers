import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SolutionTest {
    @Test
    void example1() {
        assertArrayEquals(new int[]{1,2,2,2}, new Solution().fullBloomFlowers(
                new int[][]{{1,6},{3,7},{9,12},{4,13}},
                new int[] {2,3,7,11}
        ));
    }

    @Test
    void example2() {
        assertArrayEquals(new int[]{2,2,1}, new Solution().fullBloomFlowers(
                new int[][]{{1,10},{3,3}},
                new int[] {3,3,2}
        ));
    }

    @Test
    void testCase1() {
        assertArrayEquals(new int[]{2,0,0,0,2,1,1}, new Solution().fullBloomFlowers(
                new int[][]{{43,50},{31,39},{37,42},{38,47},{22,25},{31,42},{29,43},{15,30},{37,42}},
                new int[] {47,4,12,12,30,18,17}
        ));
    }
}

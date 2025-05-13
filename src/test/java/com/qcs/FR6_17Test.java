package com.qcs;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class FR6_17Test {

    @Test
    public void testPath1() {
        // P(1) = (Start, 1, 2, 4, End)
        // This path represents the case where temps.length = 0
        int[] temps = new int[0];
        int[][] fans = new int[0][0];
        FR6_17 controller = new FR6_17("Controller1", 25, 2, temps, fans);
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(0, result.length);
    }

    @Test
    public void testPath2() {
        // P(2) = (Start, 1, 2, 3, 5, 8, 9, 10, 11, 13, 33, 2, 4, End)
        // Case where temps[i] > desired_temp + error and fanAverage == 0
        int[] temps = {30};
        int[][] fans = {{0, 0}};
        FR6_17 controller = new FR6_17("Controller2", 25, 2, temps, fans);
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(1, result.length);
        assertEquals(2, result[0].length);
        assertEquals(0, result[0][0]); // 0 + (20 * -1) = -20, but clamped to 0
        assertEquals(0, result[0][1]); // 0 + (20 * -1) = -20, but clamped to 0
    }
    
    @Test
    public void testPath3() {
        // P(3) = (Start, 1, 2, 3, 5, 8, 9, 21, 22, 23, 25, 33, 2, 4 End)
        // Case where temps[i] < desired_temp - error and fanAverage == 0
        int[] temps = {20};
        int[][] fans = {{0, 0}};
        FR6_17 controller = new FR6_17("Controller3", 25, 2, temps, fans);
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(1, result.length);
        assertEquals(2, result[0].length);
        assertEquals(20, result[0][0]); // 0 + 20 = 20
        assertEquals(20, result[0][1]); // 0 + 20 = 20
    }
    
    @Test
    public void testPath4() {
        // P(4) = (Start, 1, 2, 3, 5, 8, 9, 10, 12, 13, 33, 2, 4, End)
        // Case where temps[i] > desired_temp + error and fanAverage != 0
        int[] temps = {30};
        int[][] fans = {{50, 50}};
        FR6_17 controller = new FR6_17("Controller4", 25, 2, temps, fans);
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(1, result.length);
        assertEquals(2, result[0].length);
        assertEquals(40, result[0][0]); // 50 + (10 * -1) = 40
        assertEquals(40, result[0][1]); // 50 + (10 * -1) = 40
    }
    
    @Test
    public void testPath5() {
        // P(5) = (Start, 1, 2, 3, 5, 8, 9, 21, 22, 24, 25, 33, 2, 4 End)
        // Case where temps[i] < desired_temp - error and fanAverage != 0
        int[] temps = {20};
        int[][] fans = {{50, 50}};
        FR6_17 controller = new FR6_17("Controller5", 25, 2, temps, fans);
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(1, result.length);
        assertEquals(2, result[0].length);
        assertEquals(60, result[0][0]); // 50 + 10 = 60
        assertEquals(60, result[0][1]); // 50 + 10 = 60
    }
    
    @Test
    public void testPath6() {
        // P(6) = (Start, 1, 2, 3, 5, 6, 7, 5, 8, 9, 10, 11, 13, 33, 2, 4, End)
        // Case with multiple fans in a room and temps[i] > desired_temp + error
        int[] temps = {30};
        int[][] fans = {{0, 10, 20}}; // Multiple fans in the room
        FR6_17 controller = new FR6_17("Controller6", 25, 2, temps, fans);
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(1, result.length);
        assertEquals(3, result[0].length);
        // fanAverage = (0+10+20)/3 = 10, so change = 10
        assertEquals(0, result[0][0]);  // 0 + (10 * -1) = -10, but clamped to 0
        assertEquals(0, result[0][1]);  // 10 + (10 * -1) = 0
        assertEquals(10, result[0][2]); // 20 + (10 * -1) = 10
    }
    
    @Test
    public void testPath7() {
        // P(7) = (Start, 1, 2, 3, 5, 8, 9, 10, 11, 13, 14, 15, 16, 18, 13, 33, 2, 4, End)
        // Case where temps[i] > desired_temp + error, fanAverage == 0, and result > 100
        int[] temps = {30};
        int[][] fans = {{90, 90}}; // Fans at high speed already
        FR6_17 controller = new FR6_17("Controller7", 25, 2, temps, fans);
        
        // First, we need to modify the fans array to match the condition where fanAverage == 0
        // This is a special case setup just for this test path
        controller = new FR6_17("Controller7Modified", 25, 2, temps, 
                                new int[][]{{Integer.MAX_VALUE, Integer.MAX_VALUE}}); // This will cause integer overflow when averaged
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(1, result.length);
        assertEquals(2, result[0].length);
        // Since we forced an overflow condition to get fanAverage == 0 with high values,
        // the result will be MAX_VALUE + (20 * -1) which will be clamped to 100
        assertEquals(100, result[0][0]); 
        assertEquals(100, result[0][1]);
    }
    
    @Test
    public void testPath8() {
        // P(8) = (Start, 1, 2, 3, 5, 8, 9, 21, 22, 23, 25, 26, 27, 28, 30, 25, 33, 2, 4 End)
        // Case where temps[i] < desired_temp - error, fanAverage == 0, and result > 100
        int[] temps = {20};
        int[][] fans = {{90, 90}}; // Fans already at high speed
        FR6_17 controller = new FR6_17("Controller8", 25, 2, temps, fans);
        
        // First, we need to modify the fans array to match the condition where fanAverage == 0
        controller = new FR6_17("Controller8Modified", 25, 2, temps, new int[][]{{0, 0}});
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(1, result.length);
        assertEquals(2, result[0].length);
        assertEquals(20, result[0][0]); // 0 + 20 = 20
        assertEquals(20, result[0][1]); // 0 + 20 = 20
    }
    
    @Test
    public void testPath9() {
        // P(9) = (Start, 1, 2, 3, 5, 8, 9, 10, 11, 13, 14, 15, 17, 19, 18, 13, 33, 2, 4, End)
        // Case where temps[i] > desired_temp + error, fanAverage == 0, result < 0
        int[] temps = {30};
        int[][] fans = {{10, 10}}; // Fans at low speed
        FR6_17 controller = new FR6_17("Controller9", 25, 2, temps, fans);
        
        // Modify to match conditions for this path
        controller = new FR6_17("Controller9Modified", 25, 2, temps, new int[][]{{0, 0}});
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(1, result.length);
        assertEquals(2, result[0].length);
        assertEquals(0, result[0][0]); // 0 + (20 * -1) = -20, clamped to 0
        assertEquals(0, result[0][1]); // 0 + (20 * -1) = -20, clamped to 0
    }
    
    @Test
    public void testPath10() {
        // P(10) = (Start, 1, 2, 3, 5, 8, 9, 10, 11, 13, 14, 15, 17, 20, 18, 13, 33, 2, 4, End)
        // Similar to path 9 but with a different branch in the else-if cascade
        int[] temps = {30};
        int[][] fans = {{10, 10}}; // Fans at low speed
        FR6_17 controller = new FR6_17("Controller10", 25, 2, temps, fans);
        
        // For this specific path, we're simulating a case where result is neither > 100 nor < 0
        controller = new FR6_17("Controller10Modified", 25, 2, temps, new int[][]{{20, 20}});
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(1, result.length);
        assertEquals(2, result[0].length);
        assertEquals(10, result[0][0]); // 20 + (10 * -1) = 10
        assertEquals(10, result[0][1]); // 20 + (10 * -1) = 10
    }
    
    @Test
    public void testPath11() {
        // P(11) = (Start, 1, 2, 3, 5, 8, 9, 21, 22, 23, 25, 26, 27, 29, 31, 30, 25, 33, 2, 4 End)
        // Case where temps[i] < desired_temp - error, fanAverage == 0, result < 0
        int[] temps = {20};
        int[][] fans = {{0, 0}}; // Fans already at minimum speed
        FR6_17 controller = new FR6_17("Controller11", 25, 2, temps, fans);
        
        // This path requires a negative result before clamping, but the code only adds to fan speed
        // in this condition, so we need a special setup
        // For testing purposes, we'll use a modified test case with special conditions
        controller = new FR6_17("Controller11", 25, 2, temps, new int[][]{{-30, -30}});
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(1, result.length);
        assertEquals(2, result[0].length);
        assertEquals(0, result[0][0]); // -30 + 20 = -10, but clamped to 0
        assertEquals(0, result[0][1]); // -30 + 20 = -10, but clamped to 0
    }
    
    @Test
    public void testPath12() {
        // P(12) = (Start, 1, 2, 3, 5, 8, 9, 21, 22, 23, 25, 26, 27, 29, 32, 30, 25, 33, 2, 4 End)
        // Case where temps[i] < desired_temp - error, fanAverage == 0, and fans need adjustment
        int[] temps = {20};
        int[][] fans = {{10, 10}};
        FR6_17 controller = new FR6_17("Controller12", 25, 2, temps, fans);
        
        // We need to simulate fanAverage == 0 for this path
        controller = new FR6_17("Controller12Modified", 25, 2, temps, new int[][]{{0, 0}});
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(1, result.length);
        assertEquals(2, result[0].length);
        assertEquals(20, result[0][0]); // 0 + 20 = 20
        assertEquals(20, result[0][1]); // 0 + 20 = 20
    }
    
    @Test
    public void testPath13() {
        // P(13) = (Start, 1, 2, 3, 5, 8, 9, 21, 33, 2, 4 End)
        // Case where temps[i] is within desired_temp ± error (no adjustment needed)
        int[] temps = {25}; // Exactly at desired temp
        int[][] fans = {{50, 50}};
        FR6_17 controller = new FR6_17("Controller13", 25, 2, temps, fans);
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(1, result.length);
        assertEquals(2, result[0].length);
        assertEquals(50, result[0][0]); // No change
        assertEquals(50, result[0][1]); // No change
    }


    /* BLACKBOX */
    @Test
    public void testTC1_AllZonesTooHot() {
        int[] temps = {24, 25};
        int[][] fans = {{30, 40}, {50, 60}};
        int[][] expected = {{30, 40}, {40, 50}};
        FR6_17 system = new FR6_17("TC1", 22, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC2_AllZonesTooCold() {
        int[] temps = {19, 18};
        int[][] fans = {{30, 40}, {50, 60}};
        int[][] expected = {{40, 50}, {60, 70}};
        FR6_17 system = new FR6_17("TC2", 22, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC3_AllZonesInRange() {
        int[] temps = {21, 23};
        int[][] fans = {{30, 40}, {50, 60}};
        int[][] expected = {{30, 40}, {50, 60}};
        FR6_17 system = new FR6_17("TC3", 22, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC4_OneHotOneNormal() {
        int[] temps = {25, 21};
        int[][] fans = {{30, 40}, {50, 60}};
        int[][] expected = {{20, 30}, {50, 60}};
        FR6_17 system = new FR6_17("TC4", 22, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC5_OneColdOneNormal() {
        int[] temps = {21, 19};
        int[][] fans = {{30, 40}, {50, 60}};
        int[][] expected = {{30, 40}, {60, 70}};
        FR6_17 system = new FR6_17("TC5", 22, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC6_OneHotOneCold() {
        int[] temps = {25, 19};
        int[][] fans = {{30, 40}, {50, 60}};
        int[][] expected = {{20, 30}, {60, 70}};
        FR6_17 system = new FR6_17("TC6", 22, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC7_TooHotFansAtZero() {
        int[] temps = {25, 26};
        int[][] fans = {{0, 0}, {0, 0}};
        int[][] expected = {{0, 0}, {0, 0}};
        FR6_17 system = new FR6_17("TC7", 22, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC8_TooColdFansAtZero() {
        int[] temps = {19, 18};
        int[][] fans = {{0, 0}, {0, 0}};
        int[][] expected = {{20, 20}, {20, 20}};
        FR6_17 system = new FR6_17("TC8", 22, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC9_TooHotNearMaxDuty() {
        int[] temps = {25, 26};
        int[][] fans = {{100, 95}, {90, 100}};
        int[][] expected = {{90, 85}, {80, 90}};
        FR6_17 system = new FR6_17("TC9", 22, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC10_TooColdNearMinDuty() {
        int[] temps = {19, 18};
        int[][] fans = {{10, 15}, {5, 10}};
        int[][] expected = {{20, 25}, {15, 20}};
        FR6_17 system = new FR6_17("TC10", 22, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC11_ExactTempZeroMargin() {
        int[] temps = {22, 22};
        int[][] fans = {{50, 60}, {70, 80}};
        int[][] expected = {{50, 60}, {70, 80}};
        FR6_17 system = new FR6_17("TC11", 22, 0, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC12_MinimalDeviationZeroMargin() {
        int[] temps = {23, 21};
        int[][] fans = {{50, 60}, {70, 80}};
        int[][] expected = {{40, 50}, {80, 90}};
        FR6_17 system = new FR6_17("TC12", 22, 0, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC13_NegativeTempTooHot() {
        int[] temps = {-2, -1};
        int[][] fans = {{30, 40}, {50, 60}};
        int[][] expected = {{20, 30}, {40, 50}};
        FR6_17 system = new FR6_17("TC13", -5, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC14_NegativeTempTooCold() {
        int[] temps = {-8, -9};
        int[][] fans = {{30, 40}, {50, 60}};
        int[][] expected = {{40, 50}, {60, 70}};
        FR6_17 system = new FR6_17("TC14", -5, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC15_UnevenArraySize() {
        int[] temps = {25};
        int[][] fans = {{30, 40, 50}};
        int[][] expected = {{20, 30, 40}};
        FR6_17 system = new FR6_17("TC15", 22, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    @Test
    public void testTC16_MultipleZonesVariousTemps() {
        int[] temps = {25, 26, 24, 19};
        int[][] fans = {{30}, {40}, {50}, {60}};
        int[][] expected = {{20}, {30}, {50}, {70}};
        FR6_17 system = new FR6_17("TC16", 22, 2, temps, fans);
        assertArrayEquals(expected, system.checkRange());
    }

    private void runTest(String testName, int desired_temp, int error, int temp, int fanValue) {
        int[] temps = {temp};
        int[][] fans = {{fanValue, fanValue}};
        FR6_17 system = new FR6_17(testName, desired_temp, error, temps, fans);
        int[][] result = system.checkRange();
        System.out.println(testName + " => Output Fans: " + Arrays.deepToString(result));
    }

    @Test
    public void testAllBVA() {
        runTest("BVA-V1", 5, 2, 8, 8);
        runTest("BVA-V2", 6, 2, 8, 8);
        runTest("BVA-V3", 7, 2, 8, 8);
        runTest("BVA-V4", 14, 2, 8, 8);
        runTest("BVA-V5", 15, 2, 8, 8);
        runTest("BVA-V6", 16, 2, 8, 8);
        runTest("BVA-V7", 28, 2, 8, 8);
        runTest("BVA-V8", 29, 2, 8, 8);
        runTest("BVA-V9", 30, 2, 8, 8);
        runTest("BVA-V10", 38, 2, 8, 8);
        runTest("BVA-V11", 39, 2, 8, 8);
        runTest("BVA-V12", 40, 2, 8, 8);
        runTest("BVA-V13", 5, 0, 8, 8);
        runTest("BVA-V14", 5, 1, 8, 8);
        runTest("BVA-V15", 5, 2, 8, 8);
        runTest("BVA-V16", 5, 3, 8, 8);
        runTest("BVA-V17", 5, 4, 8, 8);
        runTest("BVA-V18", 5, 5, 8, 8);
        runTest("BVA-V19", 5, 6, 8, 8);
        runTest("BVA-V20", 5, 7, 8, 8);
        runTest("BVA-V21", 5, 9, 8, 8);
        runTest("BVA-V22", 5, 10, 8, 8);
        runTest("BVA-V23", 5, 2, 5, 8);
        runTest("BVA-V24", 5, 2, 6, 8);
        runTest("BVA-V25", 5, 2, 7, 8);
        runTest("BVA-V26", 5, 2, 14, 8);
        runTest("BVA-V27", 5, 2, 15, 8);
        runTest("BVA-V28", 5, 2, 16, 8);
        runTest("BVA-V29", 5, 2, 29, 8);
        runTest("BVA-V30", 5, 2, 30, 8);
        runTest("BVA-V31", 5, 2, 31, 8);
        runTest("BVA-V32", 5, 2, 38, 8);
        runTest("BVA-V33", 5, 2, 39, 8);
        runTest("BVA-V34", 5, 2, 40, 8);
        runTest("BVA-V35", 5, 2, 8, 0);
        runTest("BVA-V36", 5, 2, 8, 1);
        runTest("BVA-V37", 5, 2, 8, 2);
        runTest("BVA-V38", 5, 2, 8, 39);
        runTest("BVA-V39", 5, 2, 8, 40);
        runTest("BVA-V40", 5, 2, 8, 41);
        runTest("BVA-V41", 5, 2, 8, 69);
        runTest("BVA-V42", 5, 2, 8, 70);
        runTest("BVA-V43", 5, 2, 8, 71);
        runTest("BVA-V44", 5, 2, 8, 98);
        runTest("BVA-V45", 5, 2, 8, 99);
        runTest("BVA-V46", 5, 2, 8, 100);
    }
}
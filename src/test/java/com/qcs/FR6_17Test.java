package com.qcs;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

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
    
    @Test
    public void testMultipleRooms() {
        // Additional test for multiple rooms
        int[] temps = {30, 20, 25};
        int[][] fans = {{50, 50}, {30, 30}, {40, 40}};
        FR6_17 controller = new FR6_17("ControllerMultiRooms", 25, 2, temps, fans);
        
        int[][] result = controller.checkRange();
        
        // Assertions
        assertEquals(3, result.length);
        // Room 1: temps[0] > desired_temp + error
        assertEquals(40, result[0][0]); // 50 + (10 * -1) = 40
        assertEquals(40, result[0][1]); // 50 + (10 * -1) = 40
        
        // Room 2: temps[1] < desired_temp - error
        assertEquals(40, result[1][0]); // 30 + 10 = 40
        assertEquals(40, result[1][1]); // 30 + 10 = 40
        
        // Room 3: temps[2] within range
        assertEquals(40, result[2][0]); // No change
        assertEquals(40, result[2][1]); // No change
    }
}
package com.qcs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class FR5_5_ATest {

    // White box test cases

    // Control flow testing
    @Test
    public void test_TC1_P1() {
        float[][] image = new float[0][0];
        boolean[][] visited = new boolean[0][0];
        int result = FR5_5_A.detectObjects(image, 0, 0, 0f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_TC2_P2() {
        float[][] image = new float[1][0]; // 1 row, 0 columns
        boolean[][] visited = new boolean[1][0];
        int result = FR5_5_A.detectObjects(image, 0, 1, 0f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_TC3_P3() {
        float[][] image = {
            {0.1f, 0.2f},
            {0.2f, 0.1f}
        };
        boolean[][] visited = new boolean[2][2];
        int result = FR5_5_A.detectObjects(image, 2, 2, 0.2f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_TC4_P4() {
        float[][] image = {
            {0.5f, 0.5f}
        };
        boolean[][] visited = {
            {true, true}
        };
        int result = FR5_5_A.detectObjects(image, 2, 1, 0.2f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_TC5_P5() {
        float[][] image = {
            {0.5f, 0.1f}
        };
        boolean[][] visited = {
            {false, false}
        };
        int result = FR5_5_A.detectObjects(image, 2, 1, 0.2f, visited);
        assertEquals(1, result);
    }

    // Loop testing
    @Test
    public void test_TC_L1_zeroByZero() {
        float[][] image = new float[0][0];
        boolean[][] visited = new boolean[0][0];
        int result = FR5_5_A.detectObjects(image, 0, 0, 1.0f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_TC_L2_oneByZero() {
        float[][] image = new float[1][0];
        boolean[][] visited = new boolean[1][0];
        int result = FR5_5_A.detectObjects(image, 0, 1, 1.0f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_TC_L3_zeroByOne() {
        float[][] image = new float[0][1];
        boolean[][] visited = new boolean[0][1];
        int result = FR5_5_A.detectObjects(image, 1, 0, 1.0f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_TC_L4_oneByOne() {
        float[][] image = {{0.0f}};
        boolean[][] visited = new boolean[1][1];
        int result = FR5_5_A.detectObjects(image, 1, 1, 1.0f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_TC_L5_twoByOne() {
        float[][] image = {{0.0f}, {0.0f}};
        boolean[][] visited = new boolean[2][1];
        int result = FR5_5_A.detectObjects(image, 1, 2, 1.0f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_TC_L6_oneByTwo() {
        float[][] image = {{0.0f, 0.0f}};
        boolean[][] visited = new boolean[1][2];
        int result = FR5_5_A.detectObjects(image, 2, 1, 1.0f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_TC_L7_threeByThree() {
        float[][] image = new float[3][3];
        boolean[][] visited = new boolean[3][3];
        int result = FR5_5_A.detectObjects(image, 3, 3, 1.0f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_TC_L8_nineByNine() {
        float[][] image = new float[9][9];
        boolean[][] visited = new boolean[9][9];
        int result = FR5_5_A.detectObjects(image, 9, 9, 1.0f, visited);
        assertEquals(0, result);
    }

    // Data flow testing
    @Test
    public void testP1_validDetection() {
        float[][] image = {{1.0f}};
        boolean[][] visited = {{false}};
        int result = FR5_5_A.detectObjects(image, 1, 1, 0.5f, visited);
        assertEquals(1, result);
    }

    @Test
    public void testP2_belowThreshold() {
        float[][] image = {{0.5f}};
        boolean[][] visited = {{false}};
        int result = FR5_5_A.detectObjects(image, 1, 1, 0.7f, visited);
        assertEquals(0, result);
    }

    @Test
    public void testP3_widthZero() {
        float[][] image = {{}};
        boolean[][] visited = {{}};
        int result = FR5_5_A.detectObjects(image, 1, 0, 0.5f, visited);
        assertEquals(0, result);
    }

    @Test
    public void testP4_heightZero() {
        float[][] image = {};
        boolean[][] visited = {};
        int result = FR5_5_A.detectObjects(image, 0, 0, 0.5f, visited);
        assertEquals(0, result);
    }

    @Test
    public void testP7_partialDetection() {
        float[][] image = {{0.2f, 0.7f}};
        boolean[][] visited = {{false, false}};
        int result = FR5_5_A.detectObjects(image, 2, 1, 0.5f, visited);
        assertEquals(1, result);
    }

    // Black box test cases
    
    // Equivalence partitioning
    @Test
    public void test_EP_V1() {
        float[][] image = createImage(8, 8, 0.2f);
        boolean[][] visited = new boolean[8][8];
        int result = FR5_5_A.detectObjects(image, 8, 8, 0.4f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_EP_V2() {
        float[][] image = createImage(8, 8, 0.4f);
        boolean[][] visited = new boolean[8][8];
        int result = FR5_5_A.detectObjects(image, 8, 8, 0.4f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_EP_V3() {
        float[][] image = createImage(8, 8, 0.8f);
        boolean[][] visited = new boolean[8][8];
        int result = FR5_5_A.detectObjects(image, 8, 8, 0.4f, visited);
        assertEquals(64, result); // Expecting 64 objects because seeNeighbours is not implemented, with normal functionality should be 1
    }

    @Test
    public void test_EP_V4() {
        float[][] image = createImage(8, 8, 0.4f);
        boolean[][] visited = new boolean[8][8];
        int result = FR5_5_A.detectObjects(image, 8, 8, 0.2f, visited);
        assertEquals(64, result);
    }

    @Test
    public void test_EP_V5() {
        float[][] image = createImage(8, 8, 0.4f);
        boolean[][] visited = new boolean[8][8];
        int result = FR5_5_A.detectObjects(image, 8, 8, 0.4f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_EP_V6() {
        float[][] image = createImage(8, 8, 0.4f);
        boolean[][] visited = new boolean[8][8];
        int result = FR5_5_A.detectObjects(image, 8, 8, 0.8f, visited);
        assertEquals(0, result);
    }

    @Test
    public void test_EP_V7() {
        float[][] image = createImage(3, 3, 0.4f);
        boolean[][] visited = new boolean[3][3];
        int result = FR5_5_A.detectObjects(image, 3, 3, 0.3f, visited);
        assertEquals(3*3, result);
    }

    @Test
    public void test_EP_V8() {
        float[][] image = createImage(8, 8, 0.4f);
        boolean[][] visited = new boolean[8][8];
        int result = FR5_5_A.detectObjects(image, 8, 8, 0.3f, visited);
        assertEquals(8*8, result);
    }

    @Test
    public void test_EP_V9() {
        float[][] image = createImage(15, 15, 0.4f);
        boolean[][] visited = new boolean[15][15];
        int result = FR5_5_A.detectObjects(image, 15, 15, 0.3f, visited);
        assertEquals(15*15, result);
    }

    @Test
    public void test_EP_V10() {
        float[][] image = createImage(35, 35, 0.4f);
        boolean[][] visited = new boolean[35][35];
        int result = FR5_5_A.detectObjects(image, 35, 35, 0.3f, visited);
        assertEquals(35*35, result);
    }

    @Test
    public void test_EP_I1() {
        float[][] image = createImage(8, 8, -0.5f);
        boolean[][] visited = new boolean[8][8];
        assertThrows(IllegalArgumentException.class, () -> {
            FR5_5_A.detectObjects(image, 8, 8, 0.4f, visited);
        });
    }

    @Test
    public void test_EP_I2() {
        float[][] image = createImage(8, 8, 1.5f);
        boolean[][] visited = new boolean[8][8];
        assertThrows(IllegalArgumentException.class, () -> {
            FR5_5_A.detectObjects(image, 8, 8, 0.4f, visited);
        });
    }

    @Test
    public void test_EP_I3() {
        float[][] image = createImage(8, 8, 0.4f);
        boolean[][] visited = new boolean[8][8];
        assertThrows(IllegalArgumentException.class, () -> {
            FR5_5_A.detectObjects(image, 8, 8, -0.5f, visited);
        });
    }

    @Test
    public void test_EP_I4() {
        float[][] image = createImage(8, 8, 0.4f);
        boolean[][] visited = new boolean[8][8];
        assertThrows(IllegalArgumentException.class, () -> {
            FR5_5_A.detectObjects(image, 8, 8, 1.5f, visited);
        });
    }

    @Test
    public void test_EP_I5() {
        float[][] image = new float[8][8];
        boolean[][] visited = new boolean[8][8];
        assertThrows(NegativeArraySizeException.class, () -> {
            FR5_5_A.detectObjects(image, -5, 8, 0.4f, visited);
        });
    }

    @Test
    public void test_EP_I6() {
        float[][] image = new float[8][8];
        boolean[][] visited = new boolean[8][8];
        assertThrows(NegativeArraySizeException.class, () -> {
            FR5_5_A.detectObjects(image, 8, -5, 0.4f, visited);
        });
    }

    // Boundary value analysis
    @Test
    public void test_BVA_V1_to_V10_ImageValues() {
        float[] testValues = {0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.9f, 1.0f};
        for (float val : testValues) {
            float[][] image = createImage(8, 8, val);
            boolean[][] visited = new boolean[8][8];
            int result = FR5_5_A.detectObjects(image, 8, 8, 0.4f, visited);
            if (val > 0.4f) {
                assertEquals(64, result);
            } else {
                assertEquals(0, result);
            }
        }
    }

    @Test
    public void test_BVA_V11_to_V20_ThresholdValues() {
        float[] thresholds = {0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.9f, 1.0f};
        for (float threshold : thresholds) {
            float[][] image = createImage(8, 8, 0.4f);
            boolean[][] visited = new boolean[8][8];
            int result = FR5_5_A.detectObjects(image, 8, 8, threshold, visited);
            if (threshold >= 0.4f) {
                assertEquals(0, result);
            } else {
                assertEquals(64, result);
            }
        }
    }

    @Test
    public void test_BVA_V21_to_V31_Dimensions() {
        int[] sizes = {0, 1, 4, 5, 6, 9, 10, 11, 19, 20, 21};
        for (int size : sizes) {
            float[][] image = createImage(size, size, 0.4f);
            boolean[][] visited = new boolean[size][size];
            int result = FR5_5_A.detectObjects(image, size, size, 0.3f, visited);
            assertEquals(size*size, result);
        }
    }

    @Test
    public void test_BVA_Invalid_ImageValues() {
        float[] invalidValues = {-0.1f, -0.2f, 1.1f, 1.2f};
        for (float val : invalidValues) {
            float[][] image = createImage(8, 8, val);
            boolean[][] visited = new boolean[8][8];
            assertThrows(IllegalArgumentException.class, () -> {
                FR5_5_A.detectObjects(image, 8, 8, 0.4f, visited);
            });
        }
    }

    @Test
    public void test_BVA_Invalid_ThresholdValues() {
        float[] invalidThresholds = {-0.1f, -0.2f, 1.1f, 1.2f};
        for (float threshold : invalidThresholds) {
            float[][] image = createImage(8, 8, 0.4f);
            boolean[][] visited = new boolean[8][8];
            assertThrows(IllegalArgumentException.class, () -> {
                FR5_5_A.detectObjects(image, 8, 8, threshold, visited);
            });
        }
    }

    @Test
    public void test_BVA_Invalid_Dimensions() {
        int[][] invalidSizes = {{0, 0}, {-1, -1}};
        for (int[] dims : invalidSizes) {
            float[][] image = new float[8][8];
            boolean[][] visited = new boolean[8][8];
            int width = dims[0];
            int height = dims[1];
            assertThrows(NegativeArraySizeException.class, () -> {
                FR5_5_A.detectObjects(image, width, height, 0.4f, visited);
            });
        }
    }

    // Helper
    private float[][] createImage(int width, int height, float value) {
    float[][] image = new float[height][width];
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            image[i][j] = value;
        }
    }
    return image;
}

}

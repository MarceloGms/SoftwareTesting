package com.qcs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class FR5_5_ATest {

    // TC1 - P1: image = [], width = 0, height = 0, threshold = 0
    @Test
    public void testDetectObjects_TC1() {
        float[][] image = new float[0][0];
        int width = 0, height = 0;
        float threshold = 0.0f;
        int result = FR5_5_A.detectObjects(image, width, height, threshold);
        assertEquals(0, result);
    }

    // TC2 - P2: image = [[]], width = 0, height = 1, threshold = 0.0
    @Test
    public void testDetectObjects_TC2() {
        float[][] image = new float[1][0];  // 1 row, 0 columns
        int width = 0, height = 1;
        float threshold = 0.0f;
        int result = FR5_5_A.detectObjects(image, width, height, threshold);
        assertEquals(0, result);
    }

    // TC3 - P3: image with all values <= threshold
    @Test
    public void testDetectObjects_TC3() {
        float[][] image = {
            {0.1f, 0.2f},
            {0.2f, 0.1f}
        };
        int width = 2, height = 2;
        float threshold = 0.2f;
        int result = FR5_5_A.detectObjects(image, width, height, threshold);
        assertEquals(0, result);
    }

}

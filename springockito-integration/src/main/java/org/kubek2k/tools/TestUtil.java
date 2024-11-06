package org.kubek2k.tools;

import org.mockito.Mockito;

public class TestUtil {

    public static boolean isMock(Object mock) {
        return Mockito.mockingDetails(mock).isMock();
    }
}

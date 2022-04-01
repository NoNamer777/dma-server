package org.eu.nl.dndmapp.dmaserver.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Executors {
    public static <C> void methodExecutor(C clazzInstance, String methodName, Object... params) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Method method;

        if (params.length == 0) {
            method = clazzInstance.getClass().getDeclaredMethod(methodName);
            method.invoke(clazzInstance);

            return;
        }
        Class<?>[] paramTypes = new Class[params.length];

        for (int idx = 0; idx < params.length; idx++) {
            paramTypes[idx] = params[idx].getClass();
        }
        method = clazzInstance.getClass().getDeclaredMethod(methodName, paramTypes);
        method.invoke(clazzInstance, params);
    }
}

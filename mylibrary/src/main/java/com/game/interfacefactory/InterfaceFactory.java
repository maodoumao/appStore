package com.game.interfacefactory;

import com.game.implementation.AppLibImplementation;
import com.game.interfacecode.AppLibInterface;

public class InterfaceFactory {
    public static AppLibInterface createInstance() {
        // 在这里动态加载实现类，并返回接口实例
        return new AppLibImplementation();
    }
}

package com.upseil.gdx.testbed;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.upseil.gdx.util.exception.CheckedException;
import com.upseil.gdx.util.function.IntObjectConsumer;

public class TestbedLauncher {
    
    private static final String TestbedMarker = "Testbed";
    
    public static final Map<String, Class<? extends ApplicationListener>> Testbeds = new HashMap<>(); static {
//        addPlayground(PolygonActorTestbed.class);
    }

    public static void addPlayground(Class<? extends ApplicationListener> clazz) {
        String fullName = clazz.getName();
        Testbeds.put(fullName, clazz);
        
        String simpleName = clazz.getSimpleName();
        Testbeds.put(simpleName, clazz);
        
        if (simpleName.endsWith(TestbedMarker)) {
            String shortName = simpleName.substring(0, simpleName.length() - TestbedMarker.length());
            Testbeds.put(shortName, clazz);
        }
    }
    
    private static final LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
    private static ApplicationListener listener;
    
    private static final Map<String, Option> Options = new HashMap<>(); static {
        registerOption("-class", 1, args -> createListener(args[0]));
        registerOption("-width", 1, args -> configuration.width = Integer.parseInt(args[0]));
        registerOption("-height", 1, args -> configuration.height = Integer.parseInt(args[0]));
    }
    
    private static void registerOption(String option, int size, Consumer<String[]> action) {
        Options.put(option, new Option(option, size, action));
    }
    
    public static void main(String[] args) {
        for (int index = 0; index < args.length; index++) {
            Option option = Options.get(args[index]);
            if (option == null) {
                throw new IllegalArgumentException("No option '" + args[index] + "' has been found");
            }
            option.accept(index, args);
            index += option.size();
        }
        
        if (listener == null) {
            throw new IllegalStateException("The " + ApplicationListener.class.getSimpleName() + " wasn't initialized");
        }
        configuration.title = listener.getClass().getSimpleName();
        new LwjglApplication(listener, configuration);
    }
    
    private static void createListener(String classKey) {
        Class<? extends ApplicationListener> clazz = Testbeds.get(classKey);
        if (clazz == null) {
            throw new IllegalArgumentException("No " + TestbedMarker + " found for the given key: " + classKey);
        }
        
        try {
            listener = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CheckedException("Error initialising the " + ApplicationListener.class.getSimpleName(), e);
        }
    }

    private static class Option implements IntObjectConsumer<String[]> {

        private final String option;
        private final int size;
        private final Consumer<String[]> action;
        
        public Option(String option, int size, Consumer<String[]> action) {
            this.option = option;
            this.size = size;
            this.action = action;
        }

        @Override
        public void accept(int index, String[] args) {
            if (!args[index].equals(option)) {
                throw new IllegalArgumentException(String.format("The given option '%s' doesn't match the called option '%s'", args[index], option));
            }
            
            String[] arguments = new String[size];
            for (int i = index + 1, j = 0; j < arguments.length; i++, j++) {
                arguments[j] = args[i];
            }
            action.accept(arguments);
        }

        public int size() {
            return size;
        }
        
    }
    
}

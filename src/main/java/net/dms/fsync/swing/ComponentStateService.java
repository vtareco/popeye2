package net.dms.fsync.swing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ComponentStateService {
    private static Set<Enum> initilizedComponent = new HashSet<>();
    private static ComponentStateService INSTANCE;

    private ComponentStateService(){

    }

    public static ComponentStateService getInstance(){
        if (INSTANCE == null){
            INSTANCE = new ComponentStateService();
        }
        return INSTANCE;
    }

    public void addInitializedComponent(Enum name){
        initilizedComponent.add(name);
    }

    public boolean isInitialized(Enum name){
        return initilizedComponent.contains(name);
    }

    public void clearInitialized(Enum[] values){
        initilizedComponent.removeAll(Arrays.asList(values));
    }
}

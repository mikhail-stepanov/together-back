package ru.together.database.entities;

import ru.together.database.entities.auto._Datamap;

public class Datamap extends _Datamap {

    private static Datamap instance;

    private Datamap() {}

    public static Datamap getInstance() {
        if(instance == null) {
            instance = new Datamap();
        }

        return instance;
    }
}

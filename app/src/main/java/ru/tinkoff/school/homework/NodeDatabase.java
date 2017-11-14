package ru.tinkoff.school.homework;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Влад on 12.11.2017.
 */
@Database(entities = {Node.class, NodeNodeJoin.class}, version = 1)
public abstract class NodeDatabase extends RoomDatabase {
    public abstract NodeDao getNodeDao();
    public abstract NodeNodeJoinDao getNodeNodeJoinDao();
}

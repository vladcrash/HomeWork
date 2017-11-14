package ru.tinkoff.school.homework;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by Влад on 12.11.2017.
 */

@Entity(tableName = "node_node_join",
        primaryKeys = {"parentId", "childId"},
        foreignKeys = {
                @ForeignKey(entity = Node.class,
                        parentColumns = "id",
                        childColumns = "parentId"),
                @ForeignKey(entity = Node.class,
                        parentColumns = "id",
                        childColumns = "childId"),
        })
public class NodeNodeJoin {
    public final long parentId;
    public final long childId;

    public NodeNodeJoin(long parentId, long childId) {
        this.parentId = parentId;
        this.childId = childId;
    }
}

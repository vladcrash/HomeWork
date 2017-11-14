package ru.tinkoff.school.homework;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Влад on 12.11.2017.
 */

@Dao
public interface NodeNodeJoinDao {

    @Insert
    void insert(NodeNodeJoin nodeNodeJoin);

    @Delete
    void delete(NodeNodeJoin node);

    @Query("SELECT * FROM node INNER JOIN node_node_join ON " +
            "node.id=node_node_join.parentId WHERE " +
            "node_node_join.childId = :childId")
    List<Node> getParents(long childId);

    @Query("SELECT * FROM node INNER JOIN node_node_join ON " +
            "node.id=node_node_join.childId WHERE " +
            "node_node_join.parentId = :parentId")
    List<Node> getChildren(long parentId);

    @Query("SELECT * FROM node_node_join " +
            "WHERE childId = :childId")
    List<NodeNodeJoin> getParentsJoin(long childId);

    @Query("SELECT * FROM node_node_join " +
            "WHERE parentId = :parentId")
    List<NodeNodeJoin> getChildrenJoin(long parentId);

}

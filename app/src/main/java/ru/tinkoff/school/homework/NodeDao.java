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
public interface NodeDao {

    @Query("SELECT * FROM node")
    List<Node> getAll();

    @Query("SELECT * FROM node " +
            "WHERE id =:id")
    Node getItem(long id);

    @Insert
    long insert(Node node);

    @Delete
    void delete(Node node);
}

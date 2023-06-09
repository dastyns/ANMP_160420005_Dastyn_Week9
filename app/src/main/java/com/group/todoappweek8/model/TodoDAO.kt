package com.group.todoappweek8.model

import androidx.room.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo:Todo)

    @Query("SELECT * FROM todo WHERE is_done=0 ORDER BY priority DESC")
    suspend fun selectAllTodo(): List<Todo>

    @Query("SELECT * FROM todo WHERE uuid= :id")
    fun selectTodo(id:Int): Todo

    @Query("UPDATE todo SET is_done=1 WHERE uuid=:id")
    fun updateTodo(id:Int)
    // Kenapa menggunakan INTEGER daripada BOOLEAN dikarenakan SQLlite hanya memiliki INTEGER
    // tidak memiliki tipe BOOLEAN

    @Delete
    fun deleteTodo(todo:Todo)

    @Query("UPDATE todo SET title=:title, notes=:notes, priority=:priority WHERE uuid = :id")
            suspend fun update(title:String, notes:String, priority:Int, id:Int)

}

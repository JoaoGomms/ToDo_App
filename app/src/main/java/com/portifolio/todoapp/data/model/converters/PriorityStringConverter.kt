package com.portifolio.todoapp.data.model.converters

import androidx.room.TypeConverter
import com.portifolio.todoapp.data.model.Priority

class PriorityStringConverter {

    @TypeConverter
    fun fromPriority(priority: Priority) : String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(string: String) : Priority{
        return Priority.valueOf(string)
    }

}
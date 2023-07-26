package org.astro.portfolio.mappers;

import java.util.List;

import org.astro.portfolio.dto.PageRequestDTO;
import org.astro.portfolio.dto.TodoDTO;
import org.astro.portfolio.dto.TodoImageDTO;

public interface TodoMappers {

    // String time();

    List<TodoDTO> todoSelectAll(PageRequestDTO pageRequestDTO);

    TodoDTO todoOne(Long tno);

    Long getListCount(PageRequestDTO pageRequestDTO);

    Long setTodo(TodoDTO todoDTO);
    void setTodoImage(TodoImageDTO imageDTO);

    List<String> getEmail();
    

    //테스트

}

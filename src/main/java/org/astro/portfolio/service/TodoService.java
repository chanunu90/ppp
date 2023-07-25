package org.astro.portfolio.service;

import java.util.List;

import org.astro.portfolio.dto.PageRequestDTO;
import org.astro.portfolio.dto.PageResponseDTO;
import org.astro.portfolio.dto.TodoDTO;
import org.astro.portfolio.dto.TodoImageDTO;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface TodoService {

    PageResponseDTO<TodoDTO> todoList(PageRequestDTO pageRequestDTO);

    TodoDTO todoOne(Long tno);

    Long setTodo(TodoDTO todoDTO , boolean makeThumbnail);

    
}

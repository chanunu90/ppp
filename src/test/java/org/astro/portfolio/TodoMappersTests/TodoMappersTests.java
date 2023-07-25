package org.astro.portfolio.TodoMappersTests;

import org.astro.portfolio.dto.PageRequestDTO;
import org.astro.portfolio.dto.TodoImageDTO;
import org.astro.portfolio.mappers.TodoMappers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import lombok.extern.log4j.Log4j2;


@Log4j2
@SpringBootTest
public class TodoMappersTests {

    @Autowired
    private TodoMappers mappers;

    @Test
    public void selectAll(){

        // List<TodoDTO> TodoList = mappers.todoSelectAll(PageRequestDTO pageRequestDTO);
        // log.info(TodoList);

    };

    @Test
    public void selectOne(){

        // TodoDTO Todo = mappers.todoSelectOne(13L);
        // log.info(Todo);

    };

}

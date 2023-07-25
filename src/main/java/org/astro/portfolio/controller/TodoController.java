package org.astro.portfolio.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.astro.portfolio.dto.PageRequestDTO;
import org.astro.portfolio.dto.PageResponseDTO;
import org.astro.portfolio.dto.TodoDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/portfolio/")
@RequiredArgsConstructor
@Log4j2
public class TodoController {

   private final org.astro.portfolio.service.TodoService todoService;

    @GetMapping("list")
    public String todoSelectAll(PageRequestDTO pageRequestDTO, Model model){

    PageResponseDTO<TodoDTO> pageResponseDTO = todoService.todoList(pageRequestDTO);

        model.addAttribute("TodoObj", pageResponseDTO);

        // model.addAttribute("TodoList", TodoService.TodoList());
        
        return "todo/list";
    }

    @GetMapping("read/{tno}")
    public String todoRead(@PathVariable("tno") Long tno , Model model){

        model.addAttribute("Todo", todoService.todoOne(tno));

        // model.addAttribute("TodoList", TodoService.TodoList());
        
        return "todo/read";
    }


    @GetMapping("register")
    public String todoRead(Model model){  
        return "todo/register";
    }

    @PostMapping("register")
    public String todoRead(TodoDTO todoDTO , Model model){  


        log.info("==========================================================================");
        log.info(todoDTO);
        todoService.setTodo(todoDTO , true);
        


        return "redirect:/portfolio/list";
    }
    
    
}

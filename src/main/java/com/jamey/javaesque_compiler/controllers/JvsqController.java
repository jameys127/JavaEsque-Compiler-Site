package com.jamey.javaesque_compiler.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jamey.javaesque_compiler.model.CompilationResultModel;
import com.jamey.javaesque_compiler.model.JvsqModel;

@RestController
@RequestMapping("/")
public class JvsqController {

    @PostMapping
    CompilationResultModel compilerPost(@RequestBody JvsqModel newProgram){

    }

}

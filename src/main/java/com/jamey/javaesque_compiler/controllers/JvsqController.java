package com.jamey.javaesque_compiler.controllers;

import java.io.UnsupportedEncodingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jamey.javaesque_compiler.model.CompilationResultModel;
import com.jamey.javaesque_compiler.model.JvsqModel;
import com.jamey.javaesque_compiler.service.CompilerService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:3000")
//might have to do more with this later
public class JvsqController {

    private final CompilerService compilerService;

    public JvsqController(CompilerService compilerService){
        this.compilerService = compilerService;
    }

    @PostMapping
    public ResponseEntity<CompilationResultModel> runCompilation(@RequestBody JvsqModel newProgram){
        try{
            String result = compilerService.compile(newProgram);
            return ResponseEntity.ok(new CompilationResultModel(result, null));
        }catch (Exception e){
            if(e instanceof UnsupportedEncodingException){
                return ResponseEntity.status(500).body(new CompilationResultModel(null, e.getMessage()));
            }
            return ResponseEntity.ok(new CompilationResultModel(null, e.getMessage()));
        }
    }

}

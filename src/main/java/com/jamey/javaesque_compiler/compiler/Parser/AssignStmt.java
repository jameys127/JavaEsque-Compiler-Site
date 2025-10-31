package com.jamey.javaesque_compiler.compiler.Parser;

import java.util.Optional;

public record AssignStmt(Optional<ThisExp> thisTarget, String name, Exp e) implements Stmt{
    
}

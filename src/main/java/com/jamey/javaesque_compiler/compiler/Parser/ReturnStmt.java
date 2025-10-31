package com.jamey.javaesque_compiler.compiler.Parser;

import java.util.Optional;

public record ReturnStmt(Optional<Exp> e) implements Stmt{
    
}

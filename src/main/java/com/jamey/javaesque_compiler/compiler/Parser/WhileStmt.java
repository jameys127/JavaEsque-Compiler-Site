package com.jamey.javaesque_compiler.compiler.Parser;

import java.util.List;

public record WhileStmt(Exp e, List<Stmt> stmt) implements Stmt{
    
}

package com.jamey.javaesque_compiler.compiler.Parser;

import java.util.List;
import java.util.Optional;

public record IfStmt(Exp e, List<Stmt> stmt, Optional<List<Stmt>> elseStmt) implements Stmt{
    
}

package com.jamey.javaesque_compiler.compiler.Parser;

import java.util.List;
import java.util.Optional;

public record NewExp(ClassType type, Optional<List<Exp>> exps) implements Exp{
    
}

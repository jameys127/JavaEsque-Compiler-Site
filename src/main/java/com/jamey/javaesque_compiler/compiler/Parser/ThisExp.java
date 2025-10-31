package com.jamey.javaesque_compiler.compiler.Parser;

import java.util.Optional;

public record ThisExp(Optional<String> parentVar) implements Exp{
    
}

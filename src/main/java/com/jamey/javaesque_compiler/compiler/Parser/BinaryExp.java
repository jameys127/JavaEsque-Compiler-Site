package com.jamey.javaesque_compiler.compiler.Parser;

public record BinaryExp(Exp l, Op op, Exp r) implements Exp{
    
}

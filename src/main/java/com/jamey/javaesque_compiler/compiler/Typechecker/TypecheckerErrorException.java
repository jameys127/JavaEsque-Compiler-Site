package com.jamey.javaesque_compiler.compiler.Typechecker;

public class TypecheckerErrorException extends Exception{
    public TypecheckerErrorException(final String message){
        super(message);
    }
}

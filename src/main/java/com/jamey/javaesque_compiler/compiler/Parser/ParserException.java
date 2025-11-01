package com.jamey.javaesque_compiler.compiler.Parser;

public class ParserException extends Exception{
    private int positionOfError;
    public ParserException (final String message, int positionOfError){
        super(message);
        this.positionOfError = positionOfError;
    }
    public int getPositionOfError(){
        return positionOfError;
    }
}

package com.jamey.javaesque_compiler.compiler.Main;

import java.util.ArrayList;

import com.jamey.javaesque_compiler.compiler.Lexer.TokenizerException;
import com.jamey.javaesque_compiler.compiler.CodeGenerator.CodeGen;
import com.jamey.javaesque_compiler.compiler.CodeGenerator.CodeGenException;
import com.jamey.javaesque_compiler.compiler.Lexer.Token;
import com.jamey.javaesque_compiler.compiler.Lexer.Tokenizer;
import com.jamey.javaesque_compiler.compiler.Parser.ParserException;
import com.jamey.javaesque_compiler.compiler.Parser.Parser;
import com.jamey.javaesque_compiler.compiler.Parser.Program;
import com.jamey.javaesque_compiler.compiler.Typechecker.TypecheckerErrorException;
import com.jamey.javaesque_compiler.compiler.Typechecker.Typechecker;

public class JavaEsque {
    public String runCompiler(String program) throws TokenizerException,
                                        ParserException, 
                                        TypecheckerErrorException, 
                                        CodeGenException{
        final ArrayList<Token> tokens = new Tokenizer(program).tokenize();
        final Program parsedProgram = new Parser(tokens).parseWholeProgram();
        Typechecker typechecker = new Typechecker();
        typechecker.typecheckProgram(parsedProgram);

        final String jsOutput = new CodeGen().writeProgram(parsedProgram);
        //compile javascript
        return jsOutput;
    }
}

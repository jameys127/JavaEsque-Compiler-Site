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
    // public static void usage(){
    //     System.out.println("Takes:");
    //     System.out.println("-Input JavaEsque file");
    //     System.out.println("-Output JavaScript file");
    // }
    // public static String readFileToString(final String fileName) throws IOException{
    //     return Files.readString(new File(fileName).toPath());
        
    // }
    public String runCompiler(String program) throws TokenizerException,
                                        ParserException, 
                                        TypecheckerErrorException, 
                                        CodeGenException{
        // if(args.length != 2){
        //     usage();
        // }else{
        final ArrayList<Token> tokens = new Tokenizer(program).tokenize();
        final Program parsedProgram = new Parser(tokens).parseWholeProgram();
        Typechecker.typecheckProgram(parsedProgram);

        final String jsOutput = new CodeGen().writeProgram(parsedProgram);
        //compile javascript
        return jsOutput;
    }
}

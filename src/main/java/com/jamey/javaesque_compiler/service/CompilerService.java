package com.jamey.javaesque_compiler.service;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import com.jamey.javaesque_compiler.compiler.CodeGenerator.CodeGenException;
import com.jamey.javaesque_compiler.compiler.Lexer.TokenizerException;
import com.jamey.javaesque_compiler.compiler.Main.JavaEsque;
import com.jamey.javaesque_compiler.compiler.Parser.ParserException;
import com.jamey.javaesque_compiler.compiler.Typechecker.TypecheckerErrorException;
import com.jamey.javaesque_compiler.model.JvsqModel;
import org.graalvm.polyglot.*;


@Service
public class CompilerService {

    public String compile(JvsqModel jvsqProgram) throws TokenizerException,
                                                     ParserException,
                                                     TypecheckerErrorException,
                                                     CodeGenException, 
                                                     UnsupportedEncodingException{
        String jsCode = new JavaEsque().runCompiler(jvsqProgram.program());
        return runJS(jsCode);
    }

    public String runJS(String javascript) throws UnsupportedEncodingException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Context context = Context.newBuilder("js")
                                                .out(out)
                                                .build();
        context.eval("js", javascript);
        String jsOutput = out.toString("UTF-8");
        context.close();
        return jsOutput;
    }

}

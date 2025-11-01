package com.jamey.javaesque_compiler.compiler.CodeGenerator;

import java.util.List;
import com.jamey.javaesque_compiler.compiler.Parser.*;
import com.jamey.javaesque_compiler.compiler.Parser.MethodCallExp.MethodCall;

public class CodeGen {
    private StringBuilder sb = new StringBuilder();
    private String jsOutput;

    public void writeOp(final Op op)throws CodeGenException{
        String s = null;
        if(op instanceof PlusOp){
            s = "+";
        }else if(op instanceof MinusOp){
            s = "-";
        }else if(op instanceof MultOp){
            s = "*";
        }else if(op instanceof DivOp){
            s = "/";
        }else if(op instanceof LessThanOp){
            s = "<";
        }else if(op instanceof LessThanOrEqualOp){
            s = "<=";
        }else if(op instanceof GreaterThanOp){
            s = ">";
        }else if(op instanceof GreaterThanOrEqualOp){
            s = ">=";
        }else if(op instanceof EqualityOp){
            s = "==";
        }else{
            assert(false);
            throw new CodeGenException("No such operator recognized: "+ op.toString());
        }
        sb.append(s);
    }
    /*
     * BinaryExp - done
     * BooleanExp - done
     * IntExp - done
     * MethodCallExp - done
     * NewExp - done
     * ParenExp - done
     * PrintlnExp - done
     * ThisExp - done
     * StrExp
     */
    public void writeExp(final Exp exp)throws CodeGenException{
        if(exp instanceof IntExp intexp){
            sb.append(Integer.toString(intexp.value()));
        }else if(exp instanceof BooleanExp boolexp){
            if(boolexp.bool()){
                sb.append("true");
            }else{
                sb.append("false");
            }
        }else if(exp instanceof VarExp varexp){
            sb.append(varexp.name());
        }else if(exp instanceof StrExp strexp){
            sb.append("\"");
            sb.append(strexp.string());
            sb.append("\"");
        }else if(exp instanceof BinaryExp binexp){
            sb.append("(");
            writeExp(binexp.l());
            sb.append(" ");
            writeOp(binexp.op());
            sb.append(" ");
            writeExp(binexp.r());
            sb.append(")");
        }else if(exp instanceof ParenExp paren){
            sb.append("(");
            writeExp(paren.e());
            sb.append(")");
        }else if(exp instanceof PrintlnExp print){
            sb.append("console.log(");
            writeExp(print.e());
            sb.append(")");
        }else if(exp instanceof ThisExp thisexp){
            sb.append("this.");
            sb.append(thisexp.parentVar().get());
        }else if(exp instanceof MethodCallExp method){
            int size = method.methodCalls().size();
            writeExp(method.e());
            sb.append(".");
            for(MethodCall methods : method.methodCalls()){
                sb.append(methods.name());
                sb.append("(");
                writeCommaVariable(methods.exps());
                sb.append(")");
                if(size > 1){
                    sb.append(".");
                }
                size--;
            }
        }else if(exp instanceof NewExp newexp){
            sb.append("new ");
            sb.append(newexp.type().name());
            sb.append("(");
            if(newexp.exps().isPresent()){
                writeCommaVariable(newexp.exps().get());
            }
            sb.append(")");
        }

        else{
            assert(false);
            throw new CodeGenException("No such expression recognized: " + exp.toString());
        }
    }
    /*
     * ExpStmt - done
     * VardecStmt - done
     * AssignStmt - done
     * WhileStmt - done
     * BreakStmt - done
     * ReturnStmt - done
     * IfStmt - done
     */
    public void writeStmt(final Stmt stmt)throws CodeGenException{
        //vardec: Type variable;
        //JavaScript: let variable;
        if(stmt instanceof VardecStmt vardec){
            sb.append("let ");
            sb.append(vardec.name());
            sb.append(";\n");
        }
        //while(expression){statement;}
        //
        else if(stmt instanceof WhileStmt whilestmt){
            sb.append("while (");
            writeExp(whilestmt.e());
            sb.append(") { ");
            for(Stmt other : whilestmt.stmt()){
                writeStmt(other);
            }
            sb.append("}\n");
        }
        else if(stmt instanceof AssignStmt assign){
            if(assign.thisTarget().isPresent()){
                sb.append("this.");
            }
            sb.append(assign.name());
            sb.append(" = ");
            writeExp(assign.e());
            sb.append(";\n");
        }else if(stmt instanceof ExpStmt expstmt){
            writeExp(expstmt.e());
            sb.append(";\n");
        }else if(stmt instanceof BreakStmt){
            sb.append("break;\n");
        }else if(stmt instanceof ReturnStmt returnstmt){
            if(!returnstmt.e().isPresent()){
                sb.append("return;\n");
            }else{
                sb.append("return ");
                writeExp(returnstmt.e().get());
                sb.append(";\n");
            }
        } else if(stmt instanceof IfStmt ifstmt){
            sb.append("if(");
            writeExp(ifstmt.e());
            sb.append(") {\n");
            for (Stmt body : ifstmt.stmt()){
                writeStmt(body);
            }
            sb.append("}\n");
            if(ifstmt.elseStmt().isPresent()){
                sb.append("else {\n");
                for(Stmt elsebody : ifstmt.elseStmt().get()){
                    writeStmt(elsebody);
                }
                sb.append("}\n");
            }
        }
        else{
            assert(false);
            throw new CodeGenException("No such statement recognized: " + stmt.toString());
        }
    }
    public void writeCommaVariable(List<?> list )throws CodeGenException{
        int size = list.size();
        for (Object object : list){
            if(object instanceof VardecStmt vardec){
                sb.append(vardec.name());
                if(size > 1){
                    sb.append(", ");
                }
                size--;
            }
            if(object instanceof Exp exp){
                writeExp(exp);
                if(size > 1){
                    sb.append(", ");
                }
                size--;
            }
        }
    }

    public void writeMethod(final MethodDef method) throws CodeGenException{
        sb.append(method.methodname);
        sb.append("(");
        writeCommaVariable(method.vars);
        sb.append(") {\n");
        for(Stmt stmt : method.stmts){
            writeStmt(stmt);
        }
        sb.append("}\n");

    }

    public void writeClass(final ClassDef classdef) throws CodeGenException{
        sb.append("class ");
        sb.append(classdef.classname);
        if(classdef.extend.isPresent()){
            sb.append(" extends ");
            sb.append(classdef.extend.get());
        }
        sb.append(" {\n");
        sb.append("constructor(");
        Constructor constructor = classdef.constructor;
        writeCommaVariable(constructor.vardecs);
        sb.append(") {\n");
        if(constructor.exps.isPresent()){
            sb.append("super(");
            writeCommaVariable(constructor.exps.get());
            sb.append(");\n");
        }
        for(Stmt stmt : constructor.stmts){
            writeStmt(stmt);
        }
        sb.append("}\n");
        for(MethodDef method : classdef.methoddef){
            writeMethod(method);
        }
        sb.append("}\n");
    }

    // public void close() throws IOException{
    //     writer.close();
    // }


    public String writeProgram(final Program program)throws CodeGenException{
        for(final ClassDef classdef : program.classdefs){
            writeClass(classdef);
        }
        for(final Stmt stmt : program.stmts){
            writeStmt(stmt);
        }
        jsOutput = sb.toString();
        return jsOutput;
    }


    // public static void writeProgram(final Program program, final File outputFile) throws IOException, CodeGenException{
    //     final CodeGen codegen = new CodeGen(outputFile);
    //     try{
    //         codegen.writeProgram(program);;
    //     }finally{
    //         codegen.close();
    //     }
    // }
}

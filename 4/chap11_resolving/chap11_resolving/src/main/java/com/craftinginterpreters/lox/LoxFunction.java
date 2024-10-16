package com.craftinginterpreters.lox;

import java.util.List;

// class LoxFunction implements LoxCallable {
//   private final Stmt.Function declaration;
//   private final Environment closure;

//   LoxFunction(Stmt.Function declaration, Environment closure) {
//     this.closure = closure;
//     this.declaration = declaration;
//   }
//   @Override
//   public String toString() {
//     return "<fn " + declaration.name.lexeme + ">";
//   }
//   @Override
//   public int arity() {
//     return declaration.params.size();
//   }
//   @Override
//   public Object call(Interpreter interpreter,
//                      List<Object> arguments) {
//     // To try dynamic scoping, you must use chapter 10
//     Environment environment = new Environment(closure);
//     for (int i = 0; i < declaration.params.size(); i++) {
//       environment.define(declaration.params.get(i).lexeme,
//           arguments.get(i));
//     }

//     try {
//       interpreter.executeBlock(declaration.body, environment);
//     } catch (Return returnValue) {
//       return returnValue.value;
//     }
//     return null;
//   }
// }


class LoxFunction implements LoxCallable {
  private final Expr.Function expression;  
  private final Stmt.Function declaration;  
  private final Environment closure;

  // Updated constructor to accept Expr.Function
  LoxFunction(Expr.Function expression, Environment closure) {
    this.closure = closure;
    this.expression = expression;
    this.declaration = null;  // Set to null for expression functions
  }

  LoxFunction(Stmt.Function declaration, Environment closure) {
    this.closure = closure;
    this.declaration = declaration;  // Store the declaration
    this.expression = null;  // Set to null for statement functions
  }

  @Override
  public String toString() {
    return "<fn" + (declaration != null ? declaration.name.lexeme : "") + ">";
  }

  @Override
  public int arity() {
    return (declaration != null ? declaration.params.size() : expression.params.size());
  }

  @Override
  public Object call(Interpreter interpreter, List<Object> arguments) {
    Environment environment = new Environment(closure);
    for (int i = 0; i < (declaration != null ? declaration.params.size() : expression.params.size()); i++) {
      environment.define((declaration != null ? declaration.params.get(i).lexeme : expression.params.get(i).lexeme), arguments.get(i));
    }

    try {
      interpreter.executeBlock(declaration != null ? declaration.body : expression.body, environment);
    } catch (Return returnValue) {
      return returnValue.value;
    }
    return null;
  }
}

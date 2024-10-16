package com.craftinginterpreters.lox;

class AstPrinter implements Expr.Visitor<String> {
  String print(Expr expr) {
    return expr.accept(this);
  }

  @Override
  public String visitBinaryExpr(Expr.Binary expr) {
    return parenthesize(expr.operator.lexeme,
        expr.left, expr.right);
  }

  @Override
  public String visitGroupingExpr(Expr.Grouping expr) {
    return parenthesize("group", expr.expression);
  }

  @Override
  public String visitLiteralExpr(Expr.Literal expr) {
    if (expr.value == null)
      return "nil";
    return expr.value.toString();
  }

  @Override
  public String visitUnaryExpr(Expr.Unary expr) {
    return parenthesize(expr.operator.lexeme, expr.right);
  }

  private Object evaluate(Expr expr) {
    return expr.accept(this); // Assuming the visitor returns values
  }

  @Override
  public String visitCommaExpression(Expr.Comma expr) {

    evaluate(expr.left);
    return evaluate(expr.right).toString();
  }

  private String parenthesize(String name, Expr... exprs) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name);
    for (Expr expr : exprs) {
      builder.append(" ");
      builder.append(expr.accept(this));
    }
    builder.append(")");

    System.out.println("Conditional Expression: " + builder.toString());

    return builder.toString();

  }

  @Override
  public String visitConditionalExpr(Expr.Conditional expr) {

    // Debugging purpose
    System.out.println("Inside VisitnConnditional Expr");
    StringBuilder builder = new StringBuilder();

    builder.append("(?: ");
    builder.append(expr.condition.accept(this));
    builder.append(" ");
    builder.append(expr.thenPart.accept(this));
    builder.append(" ");
    builder.append(expr.elsePart.accept(this));
    builder.append(")");

    return builder.toString();
  }
}

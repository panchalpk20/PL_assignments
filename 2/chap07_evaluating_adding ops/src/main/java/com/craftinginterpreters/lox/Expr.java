package com.craftinginterpreters.lox;

abstract class Expr {

  interface Visitor<R> {
    R visitBinaryExpr(Binary expr);

    R visitGroupingExpr(Grouping expr);

    R visitLiteralExpr(Literal expr);

    R visitUnaryExpr(Unary expr);

    R visitConditionalExpr(Conditional conditional);

    R visitCommaExpression(Comma comma);

  }

  // Nested Expr classes here...
  static class Binary extends Expr {
    Binary(Expr left, Token operator, Expr right) {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitBinaryExpr(this);
    }

    final Expr left;
    final Token operator;
    final Expr right;
  }

  static class Grouping extends Expr {
    Grouping(Expr expression) {
      this.expression = expression;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitGroupingExpr(this);
    }

    final Expr expression;
  }

  static class Literal extends Expr {
    Literal(Object value) {
      this.value = value;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitLiteralExpr(this);
    }

    final Object value;
  }

  static class Unary extends Expr {
    Unary(Token operator, Expr right) {
      this.operator = operator;
      this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitUnaryExpr(this);
    }

    final Token operator;
    final Expr right;
  }

  static class Conditional extends Expr {
    final Expr condition;
    final Expr thenPart;
    final Expr elsePart;

    public Conditional(Expr condition, Expr thenPart, Expr elsePart) {
      this.condition = condition;
      this.thenPart = thenPart;
      this.elsePart = elsePart;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitConditionalExpr(this);
    }

  }

  static class Comma extends Expr {
    public final Expr left;
    public final Expr right;

    public Comma(Expr left, Expr right) {
      this.left = left;
      this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitCommaExpression(this);
    }

  }

  abstract <R> R accept(Visitor<R> visitor);
}

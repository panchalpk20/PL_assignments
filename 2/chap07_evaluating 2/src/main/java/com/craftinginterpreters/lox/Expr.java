package com.craftinginterpreters.lox;

abstract class Expr {
  interface Visitor<R> {
    R visitBinaryExpr(Binary expr);

    R visitGroupingExpr(Grouping expr);

    R visitLiteralExpr(Literal expr);

    R visitUnaryExpr(Unary expr);

    R VisitComma(Comma comma);

    R visitTernaryExpr(Ternary expr);

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

  // added comma
  public static class Comma extends Expr {
    public final Expr left;
    public final Expr right;

    public Comma(Expr left, Expr right) {
      this.left = left;
      this.right = right;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
      return visitor.VisitComma(this);
    }
  }

  // added ternary
  public static class Ternary extends Expr {
    public final Expr condition;
    public final Expr valIfTrue;
    public final Expr valIfFalse;

    public Ternary(Expr condition, Expr valIfTrue, Expr valIfFalse) {
      this.condition = condition;
      this.valIfTrue = valIfTrue;
      this.valIfFalse = valIfFalse;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
      return visitor.visitTernaryExpr(this);
    }

  }

  abstract <R> R accept(Visitor<R> visitor);
}

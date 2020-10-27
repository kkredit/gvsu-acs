
public class ExpressionPrintingVisitor implements ExpressionVisitorInterface {

    String expressionString;
    
    public ExpressionPrintingVisitor() {

        expressionString = new String();
    }
    
    @Override
    public String toString() {

        return expressionString;
    }
    
    @Override
    public void visit(Addition addition) {

        addition.leftExpression.accept(this);
        String leftValue = expressionString;
        addition.rightExpression.accept(this);
        String rightValue = expressionString;
        
        expressionString = "(" + leftValue + "+" + rightValue + ")";
    }

    @Override
    public void visit(Subtraction subtraction) {

        subtraction.leftExpression.accept(this);
        String leftValue = expressionString;
        subtraction.rightExpression.accept(this);
        String rightValue = expressionString;
        
        expressionString = "(" + leftValue + "-" + rightValue + ")";
    }

    @Override
    public void visit(Multiplication multiplication) {

        multiplication.leftExpression.accept(this);
        String leftValue = expressionString;
        multiplication.rightExpression.accept(this);
        String rightValue = expressionString;
        
        expressionString = "(" + leftValue + "*" + rightValue + ")";
    }

    @Override
    public void visit(Division division) {

        division.leftExpression.accept(this);
        String leftValue = expressionString;
        division.rightExpression.accept(this);
        String rightValue = expressionString;
        
        expressionString = "(" + leftValue + "/" + rightValue + ")";
    }

    @Override
    public void visit(Literal literal) {

        expressionString = Double.toString(literal.value);
    }
}


public class Multiplication implements ExpressionInterface {

    ExpressionInterface leftExpression;
    ExpressionInterface rightExpression;

    public Multiplication(ExpressionInterface leftExpression, ExpressionInterface rightExpression) {
        
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public void accept(ExpressionVisitorInterface visitor) {

        visitor.visit(this);
    }

}

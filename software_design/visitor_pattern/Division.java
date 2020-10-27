
public class Division implements ExpressionInterface {

    ExpressionInterface leftExpression;
    ExpressionInterface rightExpression;

    public Division(ExpressionInterface leftExpression, ExpressionInterface rightExpression) {
        
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public void accept(ExpressionVisitorInterface visitor) {

        visitor.visit(this);
    }
    
}

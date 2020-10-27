
public class Addition implements ExpressionInterface {

    ExpressionInterface leftExpression;
    ExpressionInterface rightExpression;
    
    public Addition(ExpressionInterface leftExpression,
                    ExpressionInterface rightExpression) {
        
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public void accept(ExpressionVisitorInterface visitor) {

        visitor.visit(this);
    }

}


public class Literal implements ExpressionInterface {

    double value;
    
    public Literal(double value) {

        this.value = value;
    }

    @Override
    public void accept(ExpressionVisitorInterface visitor) {

        visitor.visit(this);
    }

}


public interface ExpressionVisitorInterface {

    void visit(Addition addition);
    void visit(Subtraction subtraction);
    void visit(Multiplication multiplication);
    void visit(Division division);
    void visit(Literal literal);
}

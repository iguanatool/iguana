private static final int RELATION_EXPRESSION_LHS_INDEX = 0;
private static final int RELATION_EXPRESSION_RHS_INDEX = 2;

public SimpleNode getLHS() {
	return getChild(RELATION_EXPRESSION_LHS_INDEX);
}

public SimpleNode getRHS() {
	return getChild(RELATION_EXPRESSION_RHS_INDEX);
}
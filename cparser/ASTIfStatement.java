private static final int IF_TRUE_CHILD_INDEX  = 1;
private static final int IF_FALSE_CHILD_INDEX = 2;

public SimpleNode getTrueChild() {
	return getChild(IF_TRUE_CHILD_INDEX);
}

public SimpleNode getFalseChild() {
	if (jjtGetNumChildren() > IF_FALSE_CHILD_INDEX) {
		return getChild(IF_FALSE_CHILD_INDEX);
	}
	return null;
}
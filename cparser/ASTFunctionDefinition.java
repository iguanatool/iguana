public String getFunctionName() {
	return new VisitorAdapter() {
		String name; 
		String extract(ASTFunctionDefinition node)  { node.childrenAccept(this); return name; }			
		public void visit(ASTDeclarator node)       { node.childrenAccept(this); }			
		public void visit(ASTDirectDeclarator node) { name = node.jjtGetFirstToken().image; }
	}.extract(this);		
}
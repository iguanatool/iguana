/* Generated By:JJTree: Do not edit this line. ASTFunctionDefinition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;


public
class ASTFunctionDefinition extends SimpleNode {
	public String getFunctionName() {
		return new VisitorAdapter() {
			String name; 
			String extract(ASTFunctionDefinition node)  { node.childrenAccept(this); return name; }			
			public void visit(ASTDeclarator node)       { node.childrenAccept(this); }			
			public void visit(ASTDirectDeclarator node) { name = node.jjtGetFirstToken().image; }
		}.extract(this);		
	}

  public ASTFunctionDefinition(int id) {
    super(id);
  }

  public ASTFunctionDefinition(CParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(CParserVisitor visitor) {
    visitor.visit(this);
  }
}
/* JavaCC - OriginalChecksum=1f07abd5a0ea9b2de2463c33b2182b51 (do not edit this line) */

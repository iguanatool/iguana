/* Generated By:JJTree: Do not edit this line. ASTSwitchExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public
class ASTSwitchExpression extends SimpleNode {
  public ASTSwitchExpression(int id) {
    super(id);
  }

  public ASTSwitchExpression(CParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(CParserVisitor visitor) {
    visitor.visit(this);
  }
}
/* JavaCC - OriginalChecksum=51b2ab286ed412b64ea16fde43a1dc73 (do not edit this line) */
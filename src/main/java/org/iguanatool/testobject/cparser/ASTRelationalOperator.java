/* Generated By:JJTree: Do not edit this line. ASTRelationalOperator.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public
class ASTRelationalOperator extends SimpleNode {
  public ASTRelationalOperator(int id) {
    super(id);
  }

  public ASTRelationalOperator(CParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(CParserVisitor visitor) {
    visitor.visit(this);
  }
}
/* JavaCC - OriginalChecksum=9a7b312965efdf70ca41e03f61508a54 (do not edit this line) */
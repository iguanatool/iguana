/* Generated By:JJTree: Do not edit this line. ASTConstant.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public
class ASTConstant extends SimpleNode {
  public ASTConstant(int id) {
    super(id);
  }

  public ASTConstant(CParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(CParserVisitor visitor) {
    visitor.visit(this);
  }
}
/* JavaCC - OriginalChecksum=fc4bf5d9f4e889fdcb933305e826a4c1 (do not edit this line) */
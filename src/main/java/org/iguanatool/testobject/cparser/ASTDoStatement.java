/* Generated By:JJTree: Do not edit this line. ASTDoStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public
class ASTDoStatement extends SimpleNode {
  public ASTDoStatement(int id) {
    super(id);
  }

  public ASTDoStatement(CParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(CParserVisitor visitor) {
    visitor.visit(this);
  }
}
/* JavaCC - OriginalChecksum=33e81f4eccd17c5109c1ffb443551e6a (do not edit this line) */

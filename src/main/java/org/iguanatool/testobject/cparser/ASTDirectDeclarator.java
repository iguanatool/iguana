/* Generated By:JJTree: Do not edit this line. ASTDirectDeclarator.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public
class ASTDirectDeclarator extends SimpleNode {
  public ASTDirectDeclarator(int id) {
    super(id);
  }

  public ASTDirectDeclarator(CParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(CParserVisitor visitor) {
    visitor.visit(this);
  }
}
/* JavaCC - OriginalChecksum=0c6644f404b8d53f8e9fa188f594d696 (do not edit this line) */

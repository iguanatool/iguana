/* Generated By:JJTree: Do not edit this line. ASTCastExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public class ASTCastExpression extends SimpleNode {
    public ASTCastExpression(int id) {
        super(id);
    }

    public ASTCastExpression(CParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor.
     **/
    public void jjtAccept(CParserVisitor visitor) {
        visitor.visit(this);
    }
}
/* JavaCC - OriginalChecksum=c59024348ac800539fd32790f50f9e15 (do not edit this line) */

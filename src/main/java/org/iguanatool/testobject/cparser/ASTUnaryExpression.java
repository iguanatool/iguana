/* Generated By:JJTree: Do not edit this line. ASTUnaryExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public class ASTUnaryExpression extends SimpleNode {
    public ASTUnaryExpression(int id) {
        super(id);
    }

    public ASTUnaryExpression(CParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor.
     **/
    public void jjtAccept(CParserVisitor visitor) {
        visitor.visit(this);
    }
}
/* JavaCC - OriginalChecksum=2d87fb053fe41bda03d2a8cf7d4b47ec (do not edit this line) */

/* Generated By:JJTree: Do not edit this line. ASTConditionalExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public class ASTConditionalExpression extends SimpleNode {
    public ASTConditionalExpression(int id) {
        super(id);
    }

    public ASTConditionalExpression(CParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor.
     **/
    public void jjtAccept(CParserVisitor visitor) {
        visitor.visit(this);
    }
}
/* JavaCC - OriginalChecksum=1296aa158bfadedba4a605777db850cd (do not edit this line) */

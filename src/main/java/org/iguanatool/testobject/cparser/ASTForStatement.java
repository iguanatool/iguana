/* Generated By:JJTree: Do not edit this line. ASTForStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public class ASTForStatement extends SimpleNode {
    public ASTForStatement(int id) {
        super(id);
    }

    public ASTForStatement(CParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor.
     **/
    public void jjtAccept(CParserVisitor visitor) {
        visitor.visit(this);
    }
}
/* JavaCC - OriginalChecksum=02db78c891cf820751bcdff9b0327cfb (do not edit this line) */

/* Generated By:JJTree: Do not edit this line. ASTBreakStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public class ASTBreakStatement extends SimpleNode {
    public ASTBreakStatement(int id) {
        super(id);
    }

    public ASTBreakStatement(CParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor.
     **/
    public void jjtAccept(CParserVisitor visitor) {
        visitor.visit(this);
    }
}
/* JavaCC - OriginalChecksum=7bcc1404e868e78c0ac2a117311402fb (do not edit this line) */

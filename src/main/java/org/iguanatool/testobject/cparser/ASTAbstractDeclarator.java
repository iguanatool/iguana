/* Generated By:JJTree: Do not edit this line. ASTAbstractDeclarator.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public class ASTAbstractDeclarator extends SimpleNode {
    public ASTAbstractDeclarator(int id) {
        super(id);
    }

    public ASTAbstractDeclarator(CParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor.
     **/
    public void jjtAccept(CParserVisitor visitor) {
        visitor.visit(this);
    }
}
/* JavaCC - OriginalChecksum=71888e0173250877946846ec990cd6fb (do not edit this line) */

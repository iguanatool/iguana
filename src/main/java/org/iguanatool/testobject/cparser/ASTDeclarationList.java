/* Generated By:JJTree: Do not edit this line. ASTDeclarationList.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public class ASTDeclarationList extends SimpleNode {
    public ASTDeclarationList(int id) {
        super(id);
    }

    public ASTDeclarationList(CParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor.
     **/
    public void jjtAccept(CParserVisitor visitor) {
        visitor.visit(this);
    }
}
/* JavaCC - OriginalChecksum=7d2c52f1c004a782526841e7d1b3698d (do not edit this line) */

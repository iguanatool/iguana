/* Generated By:JJTree: Do not edit this line. ASTEnumSpecifier.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public class ASTEnumSpecifier extends SimpleNode {
    public ASTEnumSpecifier(int id) {
        super(id);
    }

    public ASTEnumSpecifier(CParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor.
     **/
    public void jjtAccept(CParserVisitor visitor) {
        visitor.visit(this);
    }
}
/* JavaCC - OriginalChecksum=aef8ec7c32a8b78642acad0c41ae34a3 (do not edit this line) */

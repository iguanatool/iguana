/* Generated By:JJTree: Do not edit this line. ASTTypeQualifierList.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public class ASTTypeQualifierList extends SimpleNode {
    public ASTTypeQualifierList(int id) {
        super(id);
    }

    public ASTTypeQualifierList(CParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor.
     **/
    public void jjtAccept(CParserVisitor visitor) {
        visitor.visit(this);
    }
}
/* JavaCC - OriginalChecksum=d806c698e1a45b8fb12ef24673e5a9c7 (do not edit this line) */

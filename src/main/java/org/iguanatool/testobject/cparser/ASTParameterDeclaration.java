/* Generated By:JJTree: Do not edit this line. ASTParameterDeclaration.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

public class ASTParameterDeclaration extends SimpleNode {
    public ASTParameterDeclaration(int id) {
        super(id);
    }

    public ASTParameterDeclaration(CParser p, int id) {
        super(p, id);
    }


    /**
     * Accept the visitor.
     **/
    public void jjtAccept(CParserVisitor visitor) {
        visitor.visit(this);
    }
}
/* JavaCC - OriginalChecksum=6ba8ab643bbfc5e97a6bec38a1761316 (do not edit this line) */

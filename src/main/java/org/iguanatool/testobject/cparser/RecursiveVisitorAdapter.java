package org.iguanatool.testobject.cparser;

public class RecursiveVisitorAdapter implements CParserVisitor {

    public void visit(SimpleNode node) {
        node.childrenAccept(this);
    }

    public void visit(ASTParseUnit node) {
        node.childrenAccept(this);
    }

    public void visit(ASTExternalDeclaration node) {
        node.childrenAccept(this);
    }

    public void visit(ASTFunctionDefinition node) {
        node.childrenAccept(this);
    }

    public void visit(ASTDeclaration node) {
        node.childrenAccept(this);
    }

    public void visit(ASTDeclarationList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTDeclarationSpecifiers node) {
        node.childrenAccept(this);
    }

    public void visit(ASTStorageClassSpecifier node) {
        node.childrenAccept(this);
    }

    public void visit(ASTTypeSpecifier node) {
        node.childrenAccept(this);
    }

    public void visit(ASTTypeQualifier node) {
        node.childrenAccept(this);
    }

    public void visit(ASTStructOrUnionSpecifier node) {
        node.childrenAccept(this);
    }

    public void visit(ASTStructOrUnion node) {
        node.childrenAccept(this);
    }

    public void visit(ASTStructDeclarationList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTInitDeclaratorList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTInitDeclarator node) {
        node.childrenAccept(this);
    }

    public void visit(ASTStructDeclaration node) {
        node.childrenAccept(this);
    }

    public void visit(ASTSpecifierQualifierList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTStructDeclaratorList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTStructDeclarator node) {
        node.childrenAccept(this);
    }

    public void visit(ASTEnumSpecifier node) {
        node.childrenAccept(this);
    }

    public void visit(ASTEnumeratorList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTEnumerator node) {
        node.childrenAccept(this);
    }

    public void visit(ASTDeclarator node) {
        node.childrenAccept(this);
    }

    public void visit(ASTDirectDeclarator node) {
        node.childrenAccept(this);
    }

    public void visit(ASTPointer node) {
        node.childrenAccept(this);
    }

    public void visit(ASTTypeQualifierList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTParameterTypeList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTParameterList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTParameterDeclaration node) {
        node.childrenAccept(this);
    }

    public void visit(ASTIdentifierList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTInitializer node) {
        node.childrenAccept(this);
    }

    public void visit(ASTInitializerList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTTypeName node) {
        node.childrenAccept(this);
    }

    public void visit(ASTAbstractDeclarator node) {
        node.childrenAccept(this);
    }

    public void visit(ASTDirectAbstractDeclarator node) {
        node.childrenAccept(this);
    }

    public void visit(ASTTypedefName node) {
        node.childrenAccept(this);
    }

    public void visit(ASTStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTIdentifierStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTCaseStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTCaseExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTDefaultStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTExpressionStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTCompoundStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTStatementList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTIfStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTSwitchStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTSwitchExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTWhileStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTDoStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTForStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTGotoStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTContinueStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTBreakStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTReturnStatement node) {
        node.childrenAccept(this);
    }

    public void visit(ASTBranchingExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTAssignmentExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTAssignmentOperator node) {
        node.childrenAccept(this);
    }

    public void visit(ASTConditionalExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTConstantExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTLogicalORExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTLogicalANDExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTInclusiveORExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTExclusiveORExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTANDExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTRelationalExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTRelationalOperator node) {
        node.childrenAccept(this);
    }

    public void visit(ASTShiftExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTAdditiveExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTMultiplicativeExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTCastExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTUnaryExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTUnaryOperator node) {
        node.childrenAccept(this);
    }

    public void visit(ASTLogicalNOTExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTPostfixExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTPrimaryExpression node) {
        node.childrenAccept(this);
    }

    public void visit(ASTArgumentExpressionList node) {
        node.childrenAccept(this);
    }

    public void visit(ASTConstant node) {
        node.childrenAccept(this);
    }
}

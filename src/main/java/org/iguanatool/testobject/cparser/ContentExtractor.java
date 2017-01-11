package org.iguanatool.testobject.cparser;

public class ContentExtractor implements CParserVisitor {

    public StringBuilder content = new StringBuilder();

    public static String getContent(SimpleNode node) {
        ContentExtractor contentExtractor = new ContentExtractor();
        contentExtractor.append(node);
        return contentExtractor.content.toString();
    }

    protected void append(SimpleNode node) {
        Token t1 = node.jjtGetFirstToken();
        Token t = new Token();
        t.next = t1;

        for (int i = 0; i < node.jjtGetNumChildren(); i++) {
            SimpleNode child = node.getChild(i);
            while (true) {
                t = t.next;
                if (t == child.jjtGetFirstToken()) break;
                append(t);
            }
            child.jjtAccept(this);
            t = child.jjtGetLastToken();
        }

        while (t != node.jjtGetLastToken()) {
            t = t.next;
            append(t);
        }
    }

    protected void append(Token t) {
        Token tt = t.specialToken;
        if (tt != null) {
            while (tt.specialToken != null) tt = tt.specialToken;
            while (tt != null) {
                content.append(tt.image);
                tt = tt.next;
            }
        }
        content.append(t.image);
    }

    public void visit(SimpleNode node) {
        append(node);
    }

    public void visit(ASTParseUnit node) {
        append(node);
    }

    public void visit(ASTExternalDeclaration node) {
        append(node);
    }

    public void visit(ASTFunctionDefinition node) {
        append(node);
    }

    public void visit(ASTDeclaration node) {
        append(node);
    }

    public void visit(ASTDeclarationList node) {
        append(node);
    }

    public void visit(ASTDeclarationSpecifiers node) {
        append(node);
    }

    public void visit(ASTStorageClassSpecifier node) {
        append(node);
    }

    public void visit(ASTTypeSpecifier node) {
        append(node);
    }

    public void visit(ASTTypeQualifier node) {
        append(node);
    }

    public void visit(ASTStructOrUnionSpecifier node) {
        append(node);
    }

    public void visit(ASTStructOrUnion node) {
        append(node);
    }

    public void visit(ASTStructDeclarationList node) {
        append(node);
    }

    public void visit(ASTInitDeclaratorList node) {
        append(node);
    }

    public void visit(ASTInitDeclarator node) {
        append(node);
    }

    public void visit(ASTStructDeclaration node) {
        append(node);
    }

    public void visit(ASTSpecifierQualifierList node) {
        append(node);
    }

    public void visit(ASTStructDeclaratorList node) {
        append(node);
    }

    public void visit(ASTStructDeclarator node) {
        append(node);
    }

    public void visit(ASTEnumSpecifier node) {
        append(node);
    }

    public void visit(ASTEnumeratorList node) {
        append(node);
    }

    public void visit(ASTEnumerator node) {
        append(node);
    }

    public void visit(ASTDeclarator node) {
        append(node);
    }

    public void visit(ASTDirectDeclarator node) {
        append(node);
    }

    public void visit(ASTPointer node) {
        append(node);
    }

    public void visit(ASTTypeQualifierList node) {
        append(node);
    }

    public void visit(ASTParameterTypeList node) {
        append(node);
    }

    public void visit(ASTParameterList node) {
        append(node);
    }

    public void visit(ASTParameterDeclaration node) {
        append(node);
    }

    public void visit(ASTIdentifierList node) {
        append(node);
    }

    public void visit(ASTInitializer node) {
        append(node);
    }

    public void visit(ASTInitializerList node) {
        append(node);
    }

    public void visit(ASTTypeName node) {
        append(node);
    }

    public void visit(ASTAbstractDeclarator node) {
        append(node);
    }

    public void visit(ASTDirectAbstractDeclarator node) {
        append(node);
    }

    public void visit(ASTTypedefName node) {
        append(node);
    }

    public void visit(ASTStatement node) {
        append(node);
    }

    public void visit(ASTIdentifierStatement node) {
        append(node);
    }

    public void visit(ASTCaseStatement node) {
        append(node);
    }

    public void visit(ASTCaseExpression node) {
        append(node);
    }

    public void visit(ASTDefaultStatement node) {
        append(node);
    }

    public void visit(ASTExpressionStatement node) {
        append(node);
    }

    public void visit(ASTCompoundStatement node) {
        append(node);
    }

    public void visit(ASTStatementList node) {
        append(node);
    }

    public void visit(ASTIfStatement node) {
        append(node);
    }

    public void visit(ASTSwitchStatement node) {
        append(node);
    }

    public void visit(ASTSwitchExpression node) {
        append(node);
    }

    public void visit(ASTWhileStatement node) {
        append(node);
    }

    public void visit(ASTDoStatement node) {
        append(node);
    }

    public void visit(ASTForStatement node) {
        append(node);
    }

    public void visit(ASTGotoStatement node) {
        append(node);
    }

    public void visit(ASTContinueStatement node) {
        append(node);
    }

    public void visit(ASTBreakStatement node) {
        append(node);
    }

    public void visit(ASTReturnStatement node) {
        append(node);
    }

    public void visit(ASTBranchingExpression node) {
        append(node);
    }

    public void visit(ASTExpression node) {
        append(node);
    }

    public void visit(ASTAssignmentExpression node) {
        append(node);
    }

    public void visit(ASTAssignmentOperator node) {
        append(node);
    }

    public void visit(ASTConditionalExpression node) {
        append(node);
    }

    public void visit(ASTConstantExpression node) {
        append(node);
    }

    public void visit(ASTLogicalORExpression node) {
        append(node);
    }

    public void visit(ASTLogicalANDExpression node) {
        append(node);
    }

    public void visit(ASTInclusiveORExpression node) {
        append(node);
    }

    public void visit(ASTExclusiveORExpression node) {
        append(node);
    }

    public void visit(ASTANDExpression node) {
        append(node);
    }

    public void visit(ASTRelationalExpression node) {
        append(node);
    }

    public void visit(ASTRelationalOperator node) {
        append(node);
    }

    public void visit(ASTShiftExpression node) {
        append(node);
    }

    public void visit(ASTAdditiveExpression node) {
        append(node);
    }

    public void visit(ASTMultiplicativeExpression node) {
        append(node);
    }

    public void visit(ASTCastExpression node) {
        append(node);
    }

    public void visit(ASTUnaryExpression node) {
        append(node);
    }

    public void visit(ASTUnaryOperator node) {
        append(node);
    }

    public void visit(ASTLogicalNOTExpression node) {
        append(node);
    }

    public void visit(ASTPostfixExpression node) {
        append(node);
    }

    public void visit(ASTPrimaryExpression node) {
        append(node);
    }

    public void visit(ASTArgumentExpressionList node) {
        append(node);
    }

    public void visit(ASTConstant node) {
        append(node);
    }
}

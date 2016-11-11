/* Generated By:JJTree: Do not edit this line. ASTParseUnit.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package org.iguanatool.testobject.cparser;

import java.util.ArrayList;
import java.util.List;


public
class ASTParseUnit extends SimpleNode {
	
	public List<String> getFunctionNames() {
		return new RecursiveVisitorAdapter() {
			List<String> names = new ArrayList<String>();
			List<String> extract(ASTParseUnit parseUnit)  { parseUnit.jjtAccept(this); return names; }
			public void visit(ASTFunctionDefinition node) { names.add(node.getFunctionName()); }
		}.extract(this);
	}

  public ASTParseUnit(int id) {
    super(id);
  }

  public ASTParseUnit(CParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public void jjtAccept(CParserVisitor visitor) {
    visitor.visit(this);
  }
}
/* JavaCC - OriginalChecksum=0074b97b37e0e7f64cb65642bf0654a3 (do not edit this line) */
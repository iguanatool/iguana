import java.util.List;
import java.util.ArrayList;

public List<String> getFunctionNames() {
	return new RecursiveVisitorAdapter() {
		List<String> names = new ArrayList<String>();
		List<String> extract(ASTParseUnit parseUnit)  { parseUnit.jjtAccept(this); return names; }
		public void visit(ASTFunctionDefinition node) { names.add(node.getFunctionName()); }
	}.extract(this);
}
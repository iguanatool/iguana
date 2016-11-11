import java.util.regex.Matcher;
import java.util.regex.Pattern;
import iguana.testobject.structure.CFGNode;
import iguana.testobject.structure.condition.Condition;

protected CFGNode cfgNode;
protected Condition condition;
protected boolean firstSwitchClause = false;

public CFGNode getCFGNode() {
    return cfgNode;
}

public void setCFGNode(CFGNode cfgNode) {
    this.cfgNode = cfgNode;
}

public void setCondition(Condition condition) {
    this.condition = condition;
}

public Condition getCondition() {
    return condition;
}

public boolean isFirstSwitchClause() {
    return firstSwitchClause;
}

public void setFirstSwitchClause(boolean firstSwitchClause) {
    this.firstSwitchClause = firstSwitchClause;
}

public String getContent() {
    return ContentExtractor.getContent(this);
}

public String getCode() {
    String content = getContent();
       
    // remove comments
    Pattern pattern = Pattern.compile("//.*?(\r\n?|\n)|/\\*.*?\\*/", Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(content);
    String code = matcher.replaceAll("");
   
    // clear white space
    code = code.replaceAll("\\s+", " ");
    code = code.trim();
       
    return code;        
}

public SimpleNode getChild(int index) {
    return (SimpleNode) jjtGetChild(index);
}

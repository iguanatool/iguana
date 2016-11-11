import java.util.regex.Matcher;
import java.util.regex.Pattern;

public boolean isExit() {
	String code = getCode();
	Pattern pattern = Pattern.compile("exit\\([0-9]*\\);");
	Matcher matcher = pattern.matcher(code);
	return matcher.matches();
}
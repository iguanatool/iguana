package org.iguanatool.search;

/**
 * User: phil
 * Date: 14-Feb-2006
 * Time: 08:26:57
 */
public class SearchException extends RuntimeException {

	static final long serialVersionUID = -4544769666886838818L;
	
    public SearchException(String message) {
        super(message);
    }

    public SearchException(Exception e) {
        super(e);
    }

}

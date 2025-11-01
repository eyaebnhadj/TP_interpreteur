package parserANTLRv2;

public class SyntaxError extends RuntimeException {
    public SyntaxError(String msg) {
        super(msg);
    }
}

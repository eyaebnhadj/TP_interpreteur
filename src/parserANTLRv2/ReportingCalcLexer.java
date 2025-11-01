package parserANTLRv2;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;

public class ReportingCalcLexer extends PCFLexer {
    public ReportingCalcLexer(CharStream input) {
        super(input);
    }
    public void recover(LexerNoViableAltException e) {
        ErrorFlag.setFlag(); // report error
        super.recover(e);
    }
}

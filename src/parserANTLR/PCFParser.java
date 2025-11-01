// Generated from C:/Users/eyabe/Desktop/ekher_aam/Compilation-et-interpretation/TP0/TP/src/parserANTLR/PCF.g4 by ANTLR 4.13.2
package parserANTLR;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class PCFParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, NUMBER=4, OP=5, WS=6;
	public static final int
		RULE_term = 0;
	private static String[] makeRuleNames() {
		return new String[] {
			"term"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'ifz'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, "NUMBER", "OP", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "PCF.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PCFParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	 
		public TermContext() { }
		public void copyFrom(TermContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberContext extends TermContext {
		public TerminalNode NUMBER() { return getToken(PCFParser.NUMBER, 0); }
		public NumberContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCFVisitor) return ((PCFVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IfZeroContext extends TermContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public IfZeroContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCFVisitor) return ((PCFVisitor<? extends T>)visitor).visitIfZero(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BinaryTermContext extends TermContext {
		public TerminalNode OP() { return getToken(PCFParser.OP, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public BinaryTermContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PCFVisitor) return ((PCFVisitor<? extends T>)visitor).visitBinaryTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_term);
		try {
			setState(16);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				_localctx = new NumberContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(2);
				match(NUMBER);
				}
				break;
			case 2:
				_localctx = new BinaryTermContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(3);
				match(T__0);
				setState(4);
				match(OP);
				setState(5);
				term();
				setState(6);
				term();
				setState(7);
				match(T__1);
				}
				break;
			case 3:
				_localctx = new IfZeroContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(9);
				match(T__0);
				setState(10);
				match(T__2);
				setState(11);
				term();
				setState(12);
				term();
				setState(13);
				term();
				setState(14);
				match(T__1);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0006\u0013\u0002\u0000\u0007\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000"+
		"\u0003\u0000\u0011\b\u0000\u0001\u0000\u0000\u0000\u0001\u0000\u0000\u0000"+
		"\u0013\u0000\u0010\u0001\u0000\u0000\u0000\u0002\u0011\u0005\u0004\u0000"+
		"\u0000\u0003\u0004\u0005\u0001\u0000\u0000\u0004\u0005\u0005\u0005\u0000"+
		"\u0000\u0005\u0006\u0003\u0000\u0000\u0000\u0006\u0007\u0003\u0000\u0000"+
		"\u0000\u0007\b\u0005\u0002\u0000\u0000\b\u0011\u0001\u0000\u0000\u0000"+
		"\t\n\u0005\u0001\u0000\u0000\n\u000b\u0005\u0003\u0000\u0000\u000b\f\u0003"+
		"\u0000\u0000\u0000\f\r\u0003\u0000\u0000\u0000\r\u000e\u0003\u0000\u0000"+
		"\u0000\u000e\u000f\u0005\u0002\u0000\u0000\u000f\u0011\u0001\u0000\u0000"+
		"\u0000\u0010\u0002\u0001\u0000\u0000\u0000\u0010\u0003\u0001\u0000\u0000"+
		"\u0000\u0010\t\u0001\u0000\u0000\u0000\u0011\u0001\u0001\u0000\u0000\u0000"+
		"\u0001\u0010";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
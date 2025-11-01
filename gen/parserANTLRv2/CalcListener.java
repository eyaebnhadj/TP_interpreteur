// Generated from C:/Users/eyabe/Desktop/ekher_aam/Compilation-et-interpretation/TP0/TP/src/parserANTLRv2/PCF.g4 by ANTLR 4.13.2
package parserANTLRv2;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CalcParser}.
 */
public interface CalcListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code ParExp}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterParExp(CalcParser.ParExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParExp}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitParExp(CalcParser.ParExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Number}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterNumber(CalcParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Number}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitNumber(CalcParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryExp1}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExp1(CalcParser.BinaryExp1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryExp1}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExp1(CalcParser.BinaryExp1Context ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryExp2}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExp2(CalcParser.BinaryExp2Context ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryExp2}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExp2(CalcParser.BinaryExp2Context ctx);
	/**
	 * Enter a parse tree produced by the {@code IfZero}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 */
	void enterIfZero(CalcParser.IfZeroContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfZero}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 */
	void exitIfZero(CalcParser.IfZeroContext ctx);
}
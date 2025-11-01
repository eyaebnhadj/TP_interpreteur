// Generated from C:/Users/eyabe/Desktop/ekher_aam/Compilation-et-interpretation/TP0/TP/src/parserANTLR/PCF.g4 by ANTLR 4.13.2
package parserANTLR;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PCFParser}.
 */
public interface PCFListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code Number}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 */
	void enterNumber(PCFParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Number}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 */
	void exitNumber(PCFParser.NumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryExp}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExp(PCFParser.BinaryExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryExp}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExp(PCFParser.BinaryExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfZero}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 */
	void enterIfZero(PCFParser.IfZeroContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfZero}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 */
	void exitIfZero(PCFParser.IfZeroContext ctx);
}
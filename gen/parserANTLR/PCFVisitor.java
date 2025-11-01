// Generated from C:/Users/eyabe/Desktop/ekher_aam/Compilation-et-interpretation/TP0/TP/src/parserANTLR/PCF.g4 by ANTLR 4.13.2
package parserANTLR;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PCFParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PCFVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code Number}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(PCFParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryExp}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExp(PCFParser.BinaryExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfZero}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfZero(PCFParser.IfZeroContext ctx);
}
// Generated from C:/Users/eyabe/Desktop/ekher_aam/Compilation-et-interpretation/TP0/TP/src/parserANTLRv2/PCF.g4 by ANTLR 4.13.2
package parserANTLRv2;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CalcParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CalcVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code ParExp}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExp(CalcParser.ParExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Number}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(CalcParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryExp1}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExp1(CalcParser.BinaryExp1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryExp2}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExp2(CalcParser.BinaryExp2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code IfZero}
	 * labeled alternative in {@link CalcParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfZero(CalcParser.IfZeroContext ctx);
}
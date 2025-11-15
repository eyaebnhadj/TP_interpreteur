// Generated from C:/Users/eyabe/Desktop/ekher_aam/Compilation-et-interpretation/TP0/TP/src/parserANTLRv2/PCF.g4 by ANTLR 4.13.2
package parserANTLRv2;
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
	 * Visit a parse tree produced by the {@code App}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApp(PCFParser.AppContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParExp}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExp(PCFParser.ParExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Number}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(PCFParser.NumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Fix}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFix(PCFParser.FixContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryExp1}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExp1(PCFParser.BinaryExp1Context ctx);
	/**
	 * Visit a parse tree produced by the {@code Var}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(PCFParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BinaryExp2}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExp2(PCFParser.BinaryExp2Context ctx);
	/**
	 * Visit a parse tree produced by the {@code Let}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLet(PCFParser.LetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IfZero}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfZero(PCFParser.IfZeroContext ctx);
	/**
	 * Visit a parse tree produced by the {@code Fun}
	 * labeled alternative in {@link PCFParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFun(PCFParser.FunContext ctx);
}
package at.petrak.spudcasting.eval.spudcodes;

import at.petrak.spudcasting.eval.SpudEvalError;
import at.petrak.spudcasting.eval.SpudEvaluator;
import at.petrak.spudcasting.eval.Spudcode;
import net.minecraft.text.Text;

import java.util.function.ToDoubleBiFunction;

public class SpudMath {
  public static final Spudcode AddNums = dyad("Add", "Adds two doubles", (a, b) -> a + b);
  public static final Spudcode SubNums = dyad("Subtract", "Subtracts two doubles", (a, b) -> a - b);
  public static final Spudcode MulNums = dyad("Multiply", "Multiplies two doubles", (a, b) -> a * b);
  public static final Spudcode DivNums = dyad("Divide", "Divides two doubles", (a, b) -> a / b);

  public static final Spudcode NegativeNum = new AdHocSpudcode(
    "Negative", "Returns the negative of a number",
    state -> {
      double a = state.popDouble();
      state.push(-a);
      return Spudcode.Result.normal();
    });

  public static final Spudcode DoubleToInt = new AdHocSpudcode(
    Text.literal("Double to Int"),
    Text.literal("Convert a double to an int.\n" +
    "Note most things don't actually work with ints, only doubles."),
    state -> {
      double a = state.popDouble();
      state.push((int)a);
      return Spudcode.Result.normal();
    });
  public static final Spudcode Truthy = new AdHocSpudcode(
    Text.of("Truthy"),
    Text.literal("Convert everything except 0.0 to true, and 0.0 to false.\n" +
    "Note most things don't actually work with booleans, only doubles."),
    state -> {
      Object it = state.pop(Object.class);
      state.push(!it.equals(0.0d));
      return Spudcode.Result.normal();
    });

  private static Spudcode dyad(String name, String description, ToDoubleBiFunction<Double, Double> op) {
    return new AdHocSpudcode(name, description, state -> {
      double a = state.popDouble();
      double b = state.popDouble();
      state.push(op.applyAsDouble(a, b));
      return Spudcode.Result.normal();
    });
  }
}

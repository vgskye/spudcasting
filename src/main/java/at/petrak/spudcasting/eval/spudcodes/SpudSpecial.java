package at.petrak.spudcasting.eval.spudcodes;

import at.petrak.spudcasting.eval.SpudEvalError;
import at.petrak.spudcasting.eval.Spudcode;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.lang.reflect.Method;

public class SpudSpecial {
  public static final Spudcode EvalJava = new AdHocSpudcode("Mystery Science Eval 3000",
    "From top-to-bottom, pops a number of arguments, a method name, and a receiver object, " +
      "then pops that many more arguments. Returns the return value.",
    state -> {
      int count = (int)state.popDouble();
      String methodName = state.popString();
      Object recv = state.pop(Object.class);

      Object[] args = new Object[count];
      Class[] argTypes = new Class[count];
      for (int i = 0; i < count; i++) {
        Object arg = state.pop(Object.class);
        args[i] = arg;
        argTypes[i] = arg.getClass();
      }

      Class<?> clazz = recv.getClass();
      Method method;
      try {
        method = clazz.getMethod(methodName, argTypes);
        Object result = method.invoke(recv, args);
        state.push(result);
      } catch (Exception e) {
        throw new SpudEvalError("couldn't find the method", e);
      }

      return Spudcode.Result.normal();
  });

  public static final Spudcode GetWorld = new AdHocSpudcode("Get World", "Pushes a reference to the ServerLevel to the stack", state -> {
    state.push(state.world);
    return Spudcode.Result.normal();
  });

  public static final Spudcode Print = new AdHocSpudcode("Print", "Pops and prints the top of the stack", state -> {
    Object top = state.pop(Object.class);
    state.print(Text.of(top.toString()));
    return Spudcode.Result.normal();
  });
  public static final Spudcode Dump = new AdHocSpudcode("Dump", "Prints the whole stack; top is rightmost.", state -> {
    MutableText t = Text.literal("[").formatted(Formatting.GRAY);
    for (int i = 0; i < state.stack.size(); i++) {
      t.append(state.stack.get(i).toString());
      if (i < state.stack.size() - 1) {
        t.append(Text.literal(", ").formatted(Formatting.GRAY));
      }
    }
    t.append(Text.literal("]").formatted(Formatting.GRAY));
    state.print(t);
    return Spudcode.Result.normal();
  });
  public static final Spudcode Noop = new AdHocSpudcode("Noop", "Do nothing",
    (mutEvalState) -> Spudcode.Result.normal()
  );
  public static final Spudcode Test = new AdHocSpudcode("Test", "prints a helpful message", (mutEvalState) -> {
    mutEvalState.world.getServer().getPlayerManager().broadcast(Text.of("Hello, spudcasting!"), false);
    return Spudcode.Result.normal();
  });
}

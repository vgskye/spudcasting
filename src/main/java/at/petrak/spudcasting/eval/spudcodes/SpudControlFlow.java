package at.petrak.spudcasting.eval.spudcodes;

import at.petrak.spudcasting.eval.Spudcode;

public class SpudControlFlow {
  public static final Spudcode GetPC = new AdHocSpudcode("Get Program Counter", "Pushes the program counter", state -> {
    state.push((double)state.pc);
    return Spudcode.Result.normal();
  });
  public static final Spudcode Jump = new AdHocSpudcode("Jump", "Sets the program counter to the popped number", state -> {
    int pc = (int)state.popDouble();
    state.pc = pc;
    return new Spudcode.Result(false, false);
  });
  public static final Spudcode JumpRel = new AdHocSpudcode("Jump Relative",
    "Pop a number, then set the program counter forwards or backwards by that much",
    state -> {
      int jro = (int)state.popDouble();
      state.pc += jro;
      return new Spudcode.Result(false, false);
  });
  public static final Spudcode Tjmp = new AdHocSpudcode("True Jump",
    "Pops an index, then pops a value. If the value is anything but zero, jump there", state -> {
    int pc = (int)state.popDouble();
    Object obj = state.pop(Object.class);
    if (!obj.equals(0.0d)) {
      state.pc = pc;
      return new Spudcode.Result(false, false);
    } else {
      return new Spudcode.Result(true, false);
    }
  });
  public static final Spudcode Fjmp = new AdHocSpudcode("False Jump",
    "Pops an index, then pops a value. If the value is zero, jump there", state -> {
    int pc = (int)state.popDouble();
    Object obj = state.pop(Object.class);
    if (obj.equals(0.0d)) {
      state.pc = pc;
      return new Spudcode.Result(false, false);
    } else {
      return new Spudcode.Result(true, false);
    }
  });

  public static final Spudcode Halt = new AdHocSpudcode("Halt", "Halts the spudcode executor", (mutEvalState) ->
    new Spudcode.Result(true, true));
}

package at.petrak.spudcasting.eval.spudcodes;

import at.petrak.spudcasting.eval.Spudcode;

public class SpudStackManip {
  public static final Spudcode Peel = new AdHocSpudcode("Peel", "Peels off and discards the top of the stack", state -> {
    state.pop(Object.class);
    return Spudcode.Result.normal();
  });
  public static final Spudcode Sprout = new AdHocSpudcode("Sprout", "Duplicates the top of the stack", state -> {
    Object top = state.pop(Object.class);
    state.push(top);
    state.push(top);
    return Spudcode.Result.normal();
  });
  public static final Spudcode Turn = new AdHocSpudcode("Turn", "Swaps the top two elements of the stack", state -> {
    Object a = state.pop(Object.class);
    Object b = state.pop(Object.class);
    state.push(a);
    state.push(b);
    return Spudcode.Result.normal();
  });
  public static final Spudcode Rotato = new AdHocSpudcode("Rotato", "Takes the 3rd element of the stack and puts it on top", state -> {
    Object a = state.pop(Object.class);
    Object b = state.pop(Object.class);
    Object c = state.pop(Object.class);
    state.push(b);
    state.push(a);
    state.push(c);
    return Spudcode.Result.normal();
  });
  public static final Spudcode Rotato2 = new AdHocSpudcode("Poisonous Rotato", "Takes the top of the stack and puts it 3rd from the top", state -> {
    Object a = state.pop(Object.class);
    Object b = state.pop(Object.class);
    Object c = state.pop(Object.class);
    state.push(a);
    state.push(c);
    state.push(b);
    return Spudcode.Result.normal();
  });
  public static final Spudcode DigUp = new AdHocSpudcode("Dig Up", "Copies the second item of the stack and puts it on top", state -> {
    Object a = state.pop(Object.class);
    Object b = state.pop(Object.class);
    state.push(b);
    state.push(a);
    state.push(b);
    return Spudcode.Result.normal();
  });
}

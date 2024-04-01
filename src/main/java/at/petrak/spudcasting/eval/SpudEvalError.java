package at.petrak.spudcasting.eval;

public class SpudEvalError extends Error {
  public SpudEvalError(String msg) {
    super(msg);
  }

  public SpudEvalError(String msg, Throwable thr) {
    super(msg, thr);
  }
}

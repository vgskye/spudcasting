package at.petrak.spudcasting.eval.spudcodes;

import at.petrak.spudcasting.eval.SpudEvalError;
import at.petrak.spudcasting.eval.SpudEvaluator;
import at.petrak.spudcasting.eval.Spudcode;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Function;

public record AdHocSpudcode(Text name, Text description, Function<SpudEvaluator, Result> eval) implements Spudcode {
  @Override
  public Result execute(SpudEvaluator mutEvalState) throws SpudEvalError {
    return this.eval.apply(mutEvalState);
  }

  public AdHocSpudcode(String name, String desc, Function<SpudEvaluator, Result> eval) {
    this(Text.of(name), Text.literal(desc).formatted(Formatting.GRAY), eval);
  }
}

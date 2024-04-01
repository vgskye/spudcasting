package at.petrak.spudcasting.eval.spudcodes;

import at.petrak.spudcasting.eval.SpudEvalError;
import at.petrak.spudcasting.eval.SpudEvaluator;
import at.petrak.spudcasting.eval.Spudcode;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public record PushLiteral(Object lit) implements Spudcode {
  @Override
  public Result execute(SpudEvaluator mutEvalState) throws SpudEvalError {
    mutEvalState.push(this.lit);
    return Result.normal();
  }

  @Override
  public Text name() {
    return Text.of("Push Literal");
  }

  @Override
  public Text description() {
    return Text.empty()
      .append("Pushes ")
      .append(Text.literal(this.lit.toString()).formatted(Formatting.LIGHT_PURPLE))
      .append(" to the stack");
  }
}

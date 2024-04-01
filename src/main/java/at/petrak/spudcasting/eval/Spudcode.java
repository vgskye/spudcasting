package at.petrak.spudcasting.eval;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public interface Spudcode {
  Result execute(SpudEvaluator mutEvalState) throws SpudEvalError;
  Text name();
  Text description();

  record Result(boolean incrementPc, boolean quit) {
    public static Result normal() { return new Result(true, false); }
  }
}

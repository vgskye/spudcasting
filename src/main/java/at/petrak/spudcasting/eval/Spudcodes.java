package at.petrak.spudcasting.eval;

import at.petrak.spudcasting.eval.spudcodes.*;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public final class Spudcodes {
  public static Spudcode getSpudcode(ItemStack stack) {
    if (stack.isEmpty()) {
      return SpudSpecial.Noop;
    }

    if (stack.isOf(Items.HASH_BROWNS)) {
      return new PushLiteral((double)stack.getCount());
    }
    if (stack.isOf(Items.POISONOUS_POTATO_STICKS)) {
      return new PushLiteral(-(double)stack.getCount());
    }
    if (stack.isOf(Items.POTATO_EYE)) {
      return new PushLiteral(0.0d);
    }
    if (stack.isOf(Blocks.BIG_BRAIN.asItem())) {
      return SpudSpecial.EvalJava;
    }

    if (stack.isOf(Items.POISONOUS_POTATO)) {
      Text text = stack.get(DataComponentTypes.CUSTOM_NAME);
      if (text != null) {
        return new PushLiteral(text.getString());
      }

      int count = stack.getCount();
      return switch(count) {
        case 1 -> SpudMath.AddNums;
        case 2 -> SpudMath.SubNums;
        case 3 -> SpudMath.MulNums;
        case 4 -> SpudMath.DivNums;
        case 5 -> SpudMath.NegativeNum;

        case 20 -> SpudStackManip.Peel;
        case 21 -> SpudStackManip.Sprout;
        case 22 -> SpudStackManip.Turn;
        case 23 -> SpudStackManip.Rotato;
        case 24 -> SpudStackManip.Rotato2;
        case 25 -> SpudStackManip.DigUp;

        case 30 -> SpudControlFlow.Halt;
        case 31 -> SpudControlFlow.GetPC;
        case 32 -> SpudControlFlow.Jump;
        case 33 -> SpudControlFlow.JumpRel;
        case 34 -> SpudControlFlow.Tjmp;
        case 35 -> SpudControlFlow.Fjmp;

        case 40 -> SpudMath.DoubleToInt;
        case 41 -> SpudMath.Truthy;

        case 90 -> SpudSpecial.GetWorld;
        case 98 -> SpudSpecial.Dump;
        case 99 -> SpudSpecial.Print;
        default -> SpudSpecial.Noop;
      };
    }

    return SpudSpecial.Noop;
  }

  private Spudcodes() {}
}

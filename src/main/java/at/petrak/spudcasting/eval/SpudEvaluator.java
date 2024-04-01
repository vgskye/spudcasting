package at.petrak.spudcasting.eval;

import at.petrak.spudcasting.SpudcastingMod;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SpudEvaluator {
  public Inventory inventory;
  public ServerWorld world;

  public int pc;
  public List<Object> stack;

  private int opsLeft;

  private SpudEvaluator(Inventory inventory, ServerWorld world) {
    this.inventory = inventory;
    this.world = world;
    this.stack = new ArrayList<>();
    this.pc = 0;

    this.opsLeft = 100_000;
  }

  public void push(Object obj) {
    this.stack.add(obj);
  }

  @SuppressWarnings("unchecked")
  public <T> T pop(Class<T> clazz) throws SpudEvalError {
    if (this.stack.isEmpty()) {
      throw new SpudEvalError("Tried to pop a " + clazz.getSimpleName() + " from the stack but it was empty");
    }
    Object top = this.stack.remove(this.stack.size() - 1);
    if (clazz.isInstance(top)) {
      return (T) top;
    } else {
      throw new SpudEvalError("Tried to pop a " + clazz.getSimpleName() + " but got a " + top.getClass().getSimpleName());
    }
  }

  public double popDouble() throws SpudEvalError { return this.<Double>pop(Double.class); }
  public String popString() throws SpudEvalError { return this.pop(String.class); }

  public void print(Text msg) {
    this.world.getServer().getPlayerManager().broadcast(msg, false);
  }

  // return true to quit
  private boolean tick() {
    if (this.pc < 0 || this.pc >= this.inventory.size()) {
      return true;
    }

    this.opsLeft--;
    if (this.opsLeft <= 0) {
      this.print(Text.of("Ran out of evaluation steps, halting now"));
      return true;
    }

    ItemStack stack = this.inventory.getStack(this.pc);
    Spudcode spc = Spudcodes.getSpudcode(stack);
    Spudcode.Result res;
    try {
      res = spc.execute(this);
    } catch (SpudEvalError exn) {
      this.world.getServer().getPlayerManager().broadcast(Text.of(exn.toString()), false);
      SpudcastingMod.LOGGER.warn("Spud evaluator threw an error, which is probably ok", exn);
      return true;
    }

    if (res.incrementPc()) {
      this.pc++;
    }
    return res.quit();
  }

  public static void evalInventory(ServerWorld world, Inventory inventory) {
    SpudEvaluator eval = new SpudEvaluator(inventory, world);
    for (;;) {
      boolean quit = eval.tick();
      if (quit) {
        break;
      }
    }
  }
}
